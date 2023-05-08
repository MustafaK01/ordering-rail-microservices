package com.mustafak01.orderservice.controller;

import com.mustafak01.orderservice.dto.request.OrderRequest;
import com.mustafak01.orderservice.dto.response.OrderResponse;
import com.mustafak01.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void placeOrder(@RequestBody OrderRequest orderRequest){
        this.orderService.saveOrder(orderRequest);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderResponse>> getAllOrders(){
        return ResponseEntity.ok().body(this.orderService.getAllOrders());
    }

    @GetMapping("/getByOrderNumber/{orderNumber}")
    public ResponseEntity<OrderResponse> getOrderByOrderNumber(@PathVariable String orderNumber){
        return ResponseEntity.ok().body(this.orderService.getOrderByOrderNumber(orderNumber));
    }

    @DeleteMapping("/deleteByOrderNumber/{orderNumber}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteOrderByOrderNumber(@PathVariable String orderNumber){
        this.orderService.deleteOrderByOrderNumber(orderNumber);
    }


}
