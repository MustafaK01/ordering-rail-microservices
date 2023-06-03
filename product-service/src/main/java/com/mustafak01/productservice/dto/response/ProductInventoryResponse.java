package com.mustafak01.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInventoryResponse {
    private String productCode;
    private InventoryAddProductResponse productInInventory;
    private ProductResponse product;
}
