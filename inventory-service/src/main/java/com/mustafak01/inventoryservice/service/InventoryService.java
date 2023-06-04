package com.mustafak01.inventoryservice.service;

import com.mustafak01.inventoryservice.dto.*;
import com.mustafak01.inventoryservice.exception.CouldNotCreateException;
import com.mustafak01.inventoryservice.exception.CouldNotFoundException;
import com.mustafak01.inventoryservice.model.Inventory;
import com.mustafak01.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    public InventoryQuantityResponse getQuantityOfProduct(String code){
        Optional<Inventory> inventory = inventoryRepository.findByCode(code);
        if(inventory.isPresent()){
            return InventoryQuantityResponse.builder()
                    .code(inventory.get().getCode())
                    .quantity(inventory.get().getQuantity())
                    .build();
        } else throw new CouldNotFoundException();
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

    public void updateQuantityOfProduct(InventoryProductRequest inventoryProductDto){
        if(inventoryProductDto!=null){
            Optional<Inventory> productInInventory = this.inventoryRepository.findByCode(inventoryProductDto.getCode());
            if(productInInventory.isPresent()){
                productInInventory.get().setQuantity(inventoryProductDto.getQuantity());
                this.inventoryRepository.save(productInInventory.get());
            }else throw new CouldNotFoundException();
        }else throw new CouldNotCreateException("Missing data");

    }



}
