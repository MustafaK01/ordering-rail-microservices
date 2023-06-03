package com.mustafak01.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryAddProductResponse {

    private boolean isAddedToInventory;
    private Integer quantity;

}
