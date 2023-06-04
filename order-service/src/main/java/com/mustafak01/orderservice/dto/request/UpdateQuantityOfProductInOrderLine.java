package com.mustafak01.orderservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateQuantityOfProductInOrderLine {

    private String orderNumber;
    private Integer newQuantity;
    private String productCode;

}
