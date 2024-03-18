package com.grupo5.vinylSound.order.controller;

import com.grupo5.vinylSound.order.exception.BadRequestException;
import com.grupo5.vinylSound.order.exception.InternalServerErrorException;
import com.grupo5.vinylSound.order.exception.NotFoundException;
import com.grupo5.vinylSound.order.model.dto.cart.CartRequestDTO;
import com.grupo5.vinylSound.order.model.dto.cart.CartResponseDTO;
import com.grupo5.vinylSound.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/cart")
public class CartController {
    private final CartService service;

    @PostMapping("/create")
    public ResponseEntity<String> addProductToCart(@RequestBody CartRequestDTO dto) throws BadRequestException {
        service.addProductToCart(dto);
        return new ResponseEntity<>("Producto agregado exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<CartResponseDTO> getByIdUser(@PathVariable String idUser)
            throws NotFoundException, InternalServerErrorException {
        return ResponseEntity.ok(service.getByIdUser(idUser));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteById(@RequestParam("idCart") Long idCart,@RequestParam("idProduct") Long idProduct)
            throws NotFoundException {
        service.deleteProductOfCart(idCart,idProduct);
        return new ResponseEntity<>("Se elimino el producto del carrito", HttpStatus.OK);
    }

}
