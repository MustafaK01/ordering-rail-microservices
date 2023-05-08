package com.mustafak01.orderservice.dto.response;

import com.mustafak01.orderservice.dto.request.OrderLineItemsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    private String orderNumber;
    private List<OrderLineItemsDto> orderLineItemsDto;

}
