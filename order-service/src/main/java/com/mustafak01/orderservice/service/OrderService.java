package com.mustafak01.orderservice.service;

import com.mustafak01.orderservice.dto.request.OrderLineItemsDto;
import com.mustafak01.orderservice.dto.request.OrderRequest;
import com.mustafak01.orderservice.dto.response.OrderResponse;
import com.mustafak01.orderservice.dto.response.converter.OrderLineItemsDtoConverter;
import com.mustafak01.orderservice.exception.CouldNotFoundException;
import com.mustafak01.orderservice.model.Order;
import com.mustafak01.orderservice.model.OrderLineItems;
import com.mustafak01.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {


    private final OrderRepository orderRepository;
    private final OrderLineItemsDtoConverter orderLineItemsDtoConverter;


    public void saveOrder(OrderRequest orderRequest){
        if(orderRequest!=null){
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            List<OrderLineItems> orderLineItems
                    = orderRequest.getOrderLineItemsDto().stream()
                    .map(this::mapToDto).toList();
            order.setOrderLineItems(orderLineItems);
            this.orderRepository.save(order);
        }
    }

    public List<OrderResponse> getAllOrders(){
        List<Order> orders = this.orderRepository.findAll();
        return orders.stream().map(this::mapOrder).toList();
    }

    public OrderResponse getOrderByOrderNumber(String orderNumber){
        Optional<Order> order = this.orderRepository.findOrderByOrderNumber(orderNumber);
        if(order.isPresent()) return this.mapOrder(order.get());
        else throw new CouldNotFoundException();
    }

    public void deleteOrderByOrderNumber(String orderNumber){
        Optional<Order> order = this.orderRepository.findOrderByOrderNumber(orderNumber);
        if(order.isPresent()){
            this.orderRepository.delete(order.get());
        } else throw new CouldNotFoundException();
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemDto){
        OrderLineItems orderLineItem = new OrderLineItems();
        orderLineItem.setCode(orderLineItemDto.getCode());
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setId(orderLineItemDto.getId());
        return orderLineItem;
    }

    private OrderResponse mapOrder(Order o){
        return OrderResponse.builder()
                .orderLineItemsDto(this.orderLineItemsDtoConverter
                        .mapOrderLineItemsToList(o.getOrderLineItems()))
                .orderNumber(o.getOrderNumber())
                .build();
    }


}
