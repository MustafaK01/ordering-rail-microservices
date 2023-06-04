package com.mustafak01.inventoryservice.controller;

import com.mustafak01.inventoryservice.dto.*;
import com.mustafak01.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/isInStock")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryStockResponse> isInStock(@RequestParam List<String> code){
        return inventoryService.isInStock(code);
    }

    @PostMapping("/addProductToInventory")
    public InventoryAddProductResponse addProductToInventory(@RequestBody InventoryAddProductRequest inventoryAddProductRequest){
        return this.inventoryService.addProductToInventory(inventoryAddProductRequest);
    }

    @PutMapping("/setQuantity")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateQuantityOfProduct(@RequestBody InventoryProductRequest inventoryProductDto){
        inventoryService.updateQuantityOfProduct(inventoryProductDto);
    }

    @GetMapping("/getQuantity")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public InventoryQuantityResponse getQuantityOfProduct(@RequestParam String code){
        return inventoryService.getQuantityOfProduct(code);
    }


}
