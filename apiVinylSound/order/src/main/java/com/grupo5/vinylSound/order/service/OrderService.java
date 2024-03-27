package com.grupo5.vinylSound.order.service;

import com.grupo5.vinylSound.order.event.OrderEventProducer;
import com.grupo5.vinylSound.order.exception.BadRequestException;
import com.grupo5.vinylSound.order.exception.NotFoundException;
import com.grupo5.vinylSound.order.model.*;
import com.grupo5.vinylSound.order.model.dto.order.*;
import com.grupo5.vinylSound.order.model.dto.payment.PaymentDTO;
import com.grupo5.vinylSound.order.model.dto.payment.PaymentProductDTO;
import com.grupo5.vinylSound.order.repository.*;
import com.grupo5.vinylSound.order.repository.feign.CatalogClientFeign;
import com.grupo5.vinylSound.order.repository.feign.PaymentClientFeign;
import com.grupo5.vinylSound.order.repository.feign.UserClientFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final EmailService emailService;
    private final UserClientFeign userClientFeign;
    private final CatalogClientFeign catalogClientFeign;
    private final OrderRepository repository;
    private final OrderProductRepository orderProductRepository;
    private final PaymentClientFeign paymentClientFeign;
    private final OrderEventProducer eventProducer;


    public OrderPaymentDTO create(OrderRequestDTO dto) throws NotFoundException, BadRequestException {
        try{
            userClientFeign.findById(dto.idUser());
        }catch (Exception e) {
            throw new NotFoundException("No existe un usuario con id: " + dto.idUser());
        }

        List<PaymentProductDTO> productsPayment = new ArrayList<>();

        for (ProductOrderRequestDTO productDTO: dto.products()) {
            try{
                var response = catalogClientFeign.findById(productDTO.idProduct());
                var product = response.getBody();

                assert product != null;
                if(product.stock() < productDTO.quantity()){
                    throw new BadRequestException("La cantidad es mayor que el stock");
                }
                if (product.images().stream().findFirst().isPresent()) {
                    var image = product.images().stream().findFirst().get();
                    productsPayment.add(new PaymentProductDTO(product.id().toString(), product.title(), product.description(),
                            image.url(), product.category(),
                            productDTO.quantity(), product.price().doubleValue()));
                }else {
                    productsPayment.add(new PaymentProductDTO(product.id().toString(), product.title(), product.description(),
                            null, product.category(),
                            productDTO.quantity(), product.price().doubleValue()));
                }
            }catch (Exception e){
                throw new BadRequestException(e.getMessage());
            }
        }

        var order = mapToOrder(dto);
        var responseOrder = repository.save(order);
        for (ProductOrderRequestDTO product: dto.products()) {
            orderProductRepository.save(mapToOrderProduct(order.getId(),product.idProduct(),product.quantity()));
        }

        var responsePayment = paymentClientFeign.pay(new PaymentDTO(productsPayment));
        if(responsePayment.getStatusCode().is5xxServerError()){
            throw new BadRequestException("Hubo un error con mercadopago");
        }
        var responseBody = Objects.requireNonNull(responsePayment.getBody()).toString();

        int start = responseBody.indexOf("=") + 1;
        int end = responseBody.lastIndexOf("}");

        var result = responseBody.substring(start, end);

        return new OrderPaymentDTO(responseOrder.getId(), result);
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
        try{
            userClientFeign.findById(idUser);
        }catch (Exception e) {
            throw new NotFoundException("No existe un usuario con id: " + idUser);
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

    public void successful(Long id) throws NotFoundException, MessagingException {
        var optionalOrder = repository.findById(id);

        if (optionalOrder.isEmpty()){
            throw new NotFoundException("No hay registro de pedido con el id: " + id);
        }

        var order = optionalOrder.get();
        order.setStatusPayment(StatusPayment.SUCCESSFUL);
        repository.save(order);
        var dto = mapToOrderDTO(order);

        eventProducer.increaseQuantitySales(dto.products());

        emailService.sendEmailToUserOrder(order,order.getOrderProducts());
        emailService.sendEmailToAdminOrder(order,order.getOrderProducts());
    }

    public void declined(Long id) throws NotFoundException {
        var optionalOrder = repository.findById(id);

        if (optionalOrder.isEmpty()){
            throw new NotFoundException("No hay registro de pedido con el id: " + id);
        }

        var order = optionalOrder.get();
        order.setStatusPayment(StatusPayment.DECLINED);
        repository.save(order);
    }


    private OrderProduct mapToOrderProduct(Long idOrder, Long idProduct, Integer quantity) throws BadRequestException {
        var response = catalogClientFeign.findById(idProduct);
        if (response.getStatusCode().isError()) {
            throw new BadRequestException("No existe un producto con el id: " + idProduct);
        }

        var orderProduct = new OrderProduct();
        var order = new Order();
        order.setId(idOrder);

        orderProduct.setId(new OrderProduct.OrderProductId(idOrder, idProduct));
        orderProduct.setOrder(order);
        orderProduct.setQuantity(quantity);
        orderProduct.setSubtotal(Objects.requireNonNull(response.getBody()).price()*quantity);

        return orderProduct;
    }

    private Order mapToOrder(OrderRequestDTO dto) throws BadRequestException {
        var order = new Order();

        order.setIdUser(dto.idUser());
        order.setTotal(0f);
        order.setStatusPayment(StatusPayment.PENDING);

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
            products.add(new ProductOrderResponseDTO(orderProduct.getId().getIdProduct(),
                    orderProduct.getQuantity(), orderProduct.getSubtotal()));
        }
        return new OrderResponseDTO(order.getId(),order.getIdUser(),products,order.getTotal(), order.getStatusPayment());
    }
}
