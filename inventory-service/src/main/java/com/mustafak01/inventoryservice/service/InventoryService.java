package com.mustafak01.inventoryservice.service;

import com.mustafak01.inventoryservice.dto.InventoryResponse;
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
    public List<InventoryResponse> isInStock(List<String> code){
        return inventoryRepository.findByCodeIn(code)
                .stream()
                .map(inventory ->
                    InventoryResponse.builder().code(inventory.getCode())
                            .isInStock(inventory.getQuantity()>0).build()
                ).toList();
    }

}
