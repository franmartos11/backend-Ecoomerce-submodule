package com.grupo5.vinylSound.order.service;

import com.grupo5.vinylSound.order.exception.BadRequestException;
import com.grupo5.vinylSound.order.exception.InternalServerErrorException;
import com.grupo5.vinylSound.order.exception.NotFoundException;
import com.grupo5.vinylSound.order.model.Cart;
import com.grupo5.vinylSound.order.model.CartProduct;
import com.grupo5.vinylSound.order.model.dto.cart.CartProductDTO;
import com.grupo5.vinylSound.order.model.dto.cart.CartRequestDTO;
import com.grupo5.vinylSound.order.model.dto.cart.CartResponseDTO;
import com.grupo5.vinylSound.order.model.dto.product.ProductResponseDTO;
import com.grupo5.vinylSound.order.repository.CartProductRepository;
import com.grupo5.vinylSound.order.repository.CartRepository;
import com.grupo5.vinylSound.order.repository.feign.CatalogClientFeign;
import com.grupo5.vinylSound.order.repository.feign.UserClientFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository repository;
    private final CartProductRepository cartProductRepository;
    private final CatalogClientFeign catalogClientFeign;
    private final UserClientFeign userClientFeign;

    public void addProductToCart(CartRequestDTO dto) throws BadRequestException {
        try{
            userClientFeign.findById(dto.idUser()).getStatusCode();
        }catch (Exception e) {
            throw new BadRequestException("No existe un usuario con id: " + dto.idUser());
        }

        try{
            var response = catalogClientFeign.findById(dto.idProduct());
            if (!response.getStatusCode().isError()){
                if (dto.quantity() > Objects.requireNonNull(response.getBody()).stock()){
                    throw new BadRequestException("La cantidad es mayor que el stock");
                }
            }

        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }

        var optional = repository.findByIdUser(dto.idUser());
        if (optional.isEmpty()){
            var cart =repository.save(mapToCart(dto.idUser()));
            cartProductRepository.save(mapToCartProduct(cart.getId(), dto.idProduct(), dto.quantity()));
        }else {
            cartProductRepository.save(mapToCartProduct(optional.get().getId(), dto.idProduct(), dto.quantity()));
        }
    }

    public CartResponseDTO getByIdUser(String idUser) throws NotFoundException, InternalServerErrorException {
        try{
            userClientFeign.findById(idUser);
        }catch (Exception e) {
            throw new NotFoundException("No existe un usuario con id: " + idUser);
        }

        var cart = repository.findByIdUser(idUser);
        if (cart.isEmpty())
            throw new NotFoundException("El carrito con el id de usuario: " + idUser + " no existe.");

        var listCartProduct = cartProductRepository.findByCartId(cart.get().getId());
        if (listCartProduct.isEmpty())
            throw new InternalServerErrorException("No se ha encontrado ningun producto asociado a ese carrito");

        List<CartProductDTO> listDTO = new ArrayList<>();
        for (CartProduct cartProduct : listCartProduct) {
            var product = catalogClientFeign.findById(cartProduct.getId().getIdProduct());
            listDTO.add(mapToCartProductDTO(cartProduct, Objects.requireNonNull(product.getBody())));
        }

        return new CartResponseDTO(cart.get().getId(), idUser,listDTO);
    }

    public void deleteProductOfCart(Long idCart, Long idProduct) throws  NotFoundException {
        var optional = repository.findById(idCart);
        if (optional.isEmpty())
            throw new NotFoundException("No existe un carrito con id: " + idCart);

        try{
            catalogClientFeign.findById(idProduct);
        }catch (Exception e){
            throw new NotFoundException("No existe un producto con el id: " + idProduct);
        }

        if (cartProductRepository.findById(new CartProduct.CartProductId(idCart, idProduct)).isEmpty()){
            throw new NotFoundException("No existe un producto con el id: " + idProduct + " en el carrito con id: " + idCart);
        }

        cartProductRepository.deleteById(new CartProduct.CartProductId(idCart, idProduct));

        if (cartProductRepository.findByCartId(idCart).isEmpty()){
            repository.deleteById(idCart);
        }
    }

    private CartProductDTO mapToCartProductDTO(CartProduct cartProduct, ProductResponseDTO product) {
        return new CartProductDTO(cartProduct.getId().getIdProduct(),cartProduct.getQuantity(),
                product.title(),product.price(),product.description(),product.images(),product.subcategory(),product.category(),
                product.brand());
    }

    private Cart mapToCart(String userId){
        var cart = new Cart();
        cart.setIdUser(userId);
        return cart;
    }

    private CartProduct mapToCartProduct(Long idCart, Long idProduct, Integer quantity){
        var cartProduct = new CartProduct();
        var cart = new Cart();
        cart.setId(idCart);

        cartProduct.setId(new CartProduct.CartProductId(idCart, idProduct));
        cartProduct.setCart(cart);
        cartProduct.setQuantity(quantity);

        return cartProduct;
    }
}

