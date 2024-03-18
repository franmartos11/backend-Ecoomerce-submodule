package com.grupo5.vinylSound.order.controller;

import com.grupo5.vinylSound.order.exception.BadRequestException;
import com.grupo5.vinylSound.order.exception.NotFoundException;
import com.grupo5.vinylSound.order.model.PageRequestDTO;
import com.grupo5.vinylSound.order.model.dto.order.OrderRequestDTO;
import com.grupo5.vinylSound.order.model.dto.order.OrderResponseDTO;
import com.grupo5.vinylSound.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class OrderController {
    private final OrderService service;

    @PostMapping("/user/order/create")
    public ResponseEntity<?> create(@RequestBody OrderRequestDTO dto)
            throws BadRequestException, NotFoundException {
        return service.create(dto);
    }

    @GetMapping("/user/order/all")
    public ResponseEntity<Page<OrderResponseDTO>> getAll(@RequestParam("page") Integer page,
                                                         @RequestParam("size") Integer size,
                                                         @RequestParam("sort") Sort.Direction sort,
                                                         @RequestParam("column") String column) {
        return ResponseEntity.ok(service.getAll(new PageRequestDTO(page,size,sort,column)));
    }

    @GetMapping("/user/order/all/{idUser}")
    public ResponseEntity<Page<OrderResponseDTO>> getAllByIdUser(@RequestParam("page") Integer page,
                                                                 @RequestParam("size") Integer size,
                                                                 @RequestParam("sort") Sort.Direction sort,
                                                                 @RequestParam("column") String column,
                                                                 @PathVariable String idUser)
            throws NotFoundException {
        return ResponseEntity.ok(service.getAllByIdUser(new PageRequestDTO(page,size,sort,column),idUser));
    }

    @Transactional
    @PutMapping("/user/order/successful={id}")
    public ResponseEntity<String> successful(@PathVariable Long id) throws NotFoundException {
        service.successful(id);
        return new ResponseEntity<>("Se realizó correctamente el pago del pedido  " + id,HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/user/order/declined={id}")
    public ResponseEntity<String> declined(@PathVariable Long id) throws NotFoundException {
        service.declined(id);
        return new ResponseEntity<>("Se rechazo el pago del pedido  " + id,HttpStatus.OK);
    }
}
