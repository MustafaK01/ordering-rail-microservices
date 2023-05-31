package com.mustafak01.inventoryservice.repository;

import com.mustafak01.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    Optional<Inventory> findByCode(String code);

}
