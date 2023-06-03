package com.mustafak01.productservice.controller;

import com.mustafak01.productservice.dto.request.CreateProductRequest;
import com.mustafak01.productservice.dto.response.ProductInventoryResponse;
import com.mustafak01.productservice.dto.response.ProductResponse;
import com.mustafak01.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductInventoryResponse createProduct(@RequestBody CreateProductRequest productCreateInput){
        return this.productService.createProduct(productCreateInput);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
       return ResponseEntity.ok().body(this.productService.getAllProducts());
    }

    @GetMapping("/getProduct/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String productId){
        return ResponseEntity.ok().body(this.productService.getProductById(productId));
    }

    @DeleteMapping("/deleteProduct/{productId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteProductById(@PathVariable String productId){
        this.productService.deleteProductById(productId);
    }



}
