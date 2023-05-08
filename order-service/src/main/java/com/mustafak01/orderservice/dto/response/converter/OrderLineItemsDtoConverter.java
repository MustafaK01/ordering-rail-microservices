package com.mustafak01.orderservice.dto.response.converter;

import com.mustafak01.orderservice.dto.request.OrderLineItemsDto;
import com.mustafak01.orderservice.model.OrderLineItems;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderLineItemsDtoConverter {

    public OrderLineItemsDto mapOrderLineItems(OrderLineItems orderLineItems){
        return OrderLineItemsDto.builder()
                .id(orderLineItems.getId())
                .code(orderLineItems.getCode())
                .price(orderLineItems.getPrice())
                .quantity(orderLineItems.getQuantity())
                .build();
    }

    public List<OrderLineItemsDto> mapOrderLineItemsToList(List<OrderLineItems> orderLineItems){
        return orderLineItems.stream().map(this::mapOrderLineItems).toList();
    }

}
