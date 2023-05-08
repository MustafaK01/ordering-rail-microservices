package com.mustafak01.inventoryservice.controller;

import com.mustafak01.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/isInStock/{code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String code){
        return inventoryService.isInStock(code);
    }

}
