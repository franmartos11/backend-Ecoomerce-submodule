package com.grupo5.vinylSound.order.service;

import com.grupo5.vinylSound.order.exception.BadRequestException;
import com.grupo5.vinylSound.order.exception.NotFoundException;
import com.grupo5.vinylSound.order.model.Order;
import com.grupo5.vinylSound.order.model.OrderProduct;
import com.grupo5.vinylSound.order.model.PageRequestDTO;
import com.grupo5.vinylSound.order.model.StatusOrder;
import com.grupo5.vinylSound.order.model.dto.order.OrderRequestDTO;
import com.grupo5.vinylSound.order.model.dto.order.OrderResponseDTO;
import com.grupo5.vinylSound.order.model.dto.order.ProductOrderRequestDTO;
import com.grupo5.vinylSound.order.model.dto.order.ProductOrderResponseDTO;
import com.grupo5.vinylSound.order.repository.CatalogClientFeign;
import com.grupo5.vinylSound.order.repository.OrderProductRepository;
import com.grupo5.vinylSound.order.repository.OrderRepository;
import com.grupo5.vinylSound.order.repository.UserClientFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final UserClientFeign userClientFeign;
    private final CatalogClientFeign catalogClientFeign;
    private final OrderRepository repository;
    private final OrderProductRepository orderProductRepository;


    public void create(OrderRequestDTO dto) throws NotFoundException, BadRequestException {
        if (userClientFeign.findById(dto.idUser()).getStatusCode().isError())
            throw new NotFoundException("No existe un usuario con id: " + dto.idUser());

        var order = mapToOrder(dto);
        repository.save(order);
        for (ProductOrderRequestDTO product: dto.products()) {
            orderProductRepository.save(mapToOrderProduct(order.getId(),product.idProduct(),product.quantity()));
        }
    }

    public Page<OrderResponseDTO> getAll(PageRequestDTO pageRequestDTO){
        var orders = repository.findAll();
        if (orders.isEmpty()) {
            return Page.empty();
        }

        List<OrderResponseDTO> listDTO = new ArrayList<>();
        for (Order order : orders) {
            listDTO.add(mapToOrderDTO(order));
        }

        var holder = new PagedListHolder<>(listDTO);
        holder.setPage(pageRequestDTO.getPage());
        holder.setPageSize(pageRequestDTO.getSize());
        var slice = holder.getPageList();
        var ascending = pageRequestDTO.getSort().isAscending();
        var mutable = new MutableSortDefinition(pageRequestDTO.getSortByColumn(),true,ascending);
        PropertyComparator.sort(slice,mutable);

        return new PageImpl<>(slice,new PageRequestDTO().getPageable(pageRequestDTO),listDTO.size());
    }

    public Page<OrderResponseDTO> getAllByIdUser(PageRequestDTO pageRequestDTO,String idUser) throws NotFoundException {
        var response = userClientFeign.findById(idUser);
        if (response.getStatusCode().isError()){
            throw new NotFoundException("No hay registro de usuario con el id: " + idUser);
        }

        var pageable = pageRequestDTO.getPageable(pageRequestDTO);
        var orders = repository.findByIdUser(pageable,idUser);
        if (orders.isEmpty()) {
            return Page.empty();
        }

        List<OrderResponseDTO> listDTO = new ArrayList<>();
        for (Order order : orders) {
            listDTO.add(mapToOrderDTO(order));
        }

        var holder = new PagedListHolder<>(listDTO);
        holder.setPage(pageRequestDTO.getPage());
        holder.setPageSize(pageRequestDTO.getSize());
        var slice = holder.getPageList();
        var ascending = pageRequestDTO.getSort().isAscending();
        var mutable = new MutableSortDefinition(pageRequestDTO.getSortByColumn(),true,ascending);
        PropertyComparator.sort(slice,mutable);

        return new PageImpl<>(slice,new PageRequestDTO().getPageable(pageRequestDTO),listDTO.size());
    }

    private OrderProduct mapToOrderProduct(Long idOrder, Long idProduct, Integer quantity) throws BadRequestException {
        var response = catalogClientFeign.findById(idProduct);
        if (response.getStatusCode().isError()) {
            throw new BadRequestException("No existe un producto con el id: " + idProduct);
        }

        var orderProduct = new OrderProduct();
        var order = new Order();
        order.setId(idOrder);

        orderProduct.setId(null);
        orderProduct.setOrder(order);
        orderProduct.setIdProduct(idProduct);
        orderProduct.setQuantity(quantity);
        orderProduct.setSubtotal(Objects.requireNonNull(response.getBody()).price()*quantity);

        return orderProduct;
    }

    private Order mapToOrder(OrderRequestDTO dto) throws BadRequestException {
        var order = new Order();

        order.setIdUser(dto.idUser());
        order.setTotal(0f);
        order.setStatusOrder(StatusOrder.PENDING);

        for (ProductOrderRequestDTO productOrderRequestDTO : dto.products()) {
            var response = catalogClientFeign.findById(productOrderRequestDTO.idProduct());

            if (response.getStatusCode().isError()) {
                throw new BadRequestException("No existe un producto con el id: " + productOrderRequestDTO.idProduct());
            }
            var quantity = productOrderRequestDTO.quantity();

            var subtotal = Objects.requireNonNull(response.getBody()).price()* quantity;
            order.sumTotal(subtotal);
        }
        return order;
    }

    private OrderResponseDTO mapToOrderDTO(Order order) {
        List<ProductOrderResponseDTO> products = new ArrayList<>();
        for(OrderProduct orderProduct:order.getOrderProducts()) {
            products.add(new ProductOrderResponseDTO(orderProduct.getIdProduct(),
                    orderProduct.getQuantity(), orderProduct.getSubtotal()));
        }
        return new OrderResponseDTO(order.getId(),order.getIdUser(),products,order.getTotal(), order.getStatusOrder());
    }
}
