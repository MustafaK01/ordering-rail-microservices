package com.mustafak01.orderservice.service;

import com.mustafak01.orderservice.dto.request.OrderLineItemsDto;
import com.mustafak01.orderservice.dto.request.OrderRequest;
import com.mustafak01.orderservice.model.Order;
import com.mustafak01.orderservice.model.OrderLineItems;
import com.mustafak01.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;


    public void placeOrder(OrderRequest orderRequest){
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

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemDto){
        OrderLineItems orderLineItem = new OrderLineItems();
        orderLineItem.setCode(orderLineItemDto.getCode());
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setId(orderLineItemDto.getId());
        return orderLineItem;
    }

}
