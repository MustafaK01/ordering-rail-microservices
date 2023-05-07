package com.mustafak01.productservice.service;

import com.mustafak01.productservice.dto.request.CreateProductRequest;
import com.mustafak01.productservice.dto.response.ProductResponse;
import com.mustafak01.productservice.model.Product;
import com.mustafak01.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(CreateProductRequest createProductInput){
        if (createProductInput!=null){
            Product product = Product.builder()
                    .name(createProductInput.getName())
                    .description(createProductInput.getDescription())
                    .price(createProductInput.getPrice())
                    .build();
            this.productRepository.save(product);
        }
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = this.productRepository.findAll();
        return products.stream().map(this::mapProduct).toList();
    }

    private ProductResponse mapProduct(Product p){
        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .build();
    }




}
