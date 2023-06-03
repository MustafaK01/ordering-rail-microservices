package com.mustafak01.inventoryservice.service;

import com.mustafak01.inventoryservice.dto.InventoryAddProductRequest;
import com.mustafak01.inventoryservice.dto.InventoryAddProductResponse;
import com.mustafak01.inventoryservice.dto.InventoryStockResponse;
import com.mustafak01.inventoryservice.exception.CouldNotCreateException;
import com.mustafak01.inventoryservice.model.Inventory;
import com.mustafak01.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryStockResponse> isInStock(List<String> code){
        return inventoryRepository.findByCodeIn(code)
                .stream()
                .map(inventory ->
                    InventoryStockResponse.builder().code(inventory.getCode())
                            .isInStock(inventory.getQuantity()>0).build()
                ).toList();
    }

    public InventoryAddProductResponse addProductToInventory(InventoryAddProductRequest inventoryAddProductRequest){
        if(inventoryAddProductRequest!=null){
            Inventory inventory = Inventory.builder()
                    .id(0L)
                    .code(inventoryAddProductRequest.getCode())
                    .quantity(inventoryAddProductRequest.getQuantity())
                    .build();
            inventoryRepository.save(inventory);
            return new InventoryAddProductResponse(true, inventoryAddProductRequest.getQuantity());
        }
        else throw new CouldNotCreateException("Product couldn't add in inventory");
    }


}
