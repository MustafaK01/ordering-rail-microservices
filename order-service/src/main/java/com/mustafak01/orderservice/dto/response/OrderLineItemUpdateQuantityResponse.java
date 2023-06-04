package com.mustafak01.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItemUpdateQuantityResponse {

    private String productCode;
    private Integer previousQuantity;
    private Integer newQuantity;


}
