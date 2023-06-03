package com.mustafak01.productservice.service;

import com.mustafak01.productservice.dto.request.CreateProductRequest;
import com.mustafak01.productservice.dto.request.InventoryAddProductRequest;
import com.mustafak01.productservice.dto.response.InventoryAddProductResponse;
import com.mustafak01.productservice.dto.response.ProductInventoryResponse;
import com.mustafak01.productservice.dto.response.ProductResponse;
import com.mustafak01.productservice.exception.CouldNotCreateException;
import com.mustafak01.productservice.exception.CouldNotFoundException;
import com.mustafak01.productservice.model.Product;
import com.mustafak01.productservice.repository.ProductRepository;
import com.mustafak01.productservice.util.StringGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final WebClient.Builder webClientBuilder;
    private final StringGenerator randomStringGenerator;

    public ProductInventoryResponse createProduct(CreateProductRequest createProductInput){
        if (createProductInput!=null){
            int quantity = createProductInput.getQuantity()==null
                    || createProductInput.getQuantity()<=0 ? 0 : createProductInput.getQuantity();
            Product product = Product.builder()
                    .name(createProductInput.getName())
                    .description(createProductInput.getDescription())
                    .price(createProductInput.getPrice())
                    .build();
                InventoryAddProductRequest inventoryAddProductRequest = InventoryAddProductRequest.builder()
                        .code(createProductInput.getName()+randomStringGenerator.generateRandomString())
                        .quantity(quantity)
                        .build();
            this.productRepository.save(product);
            InventoryAddProductResponse inventoryAddProductResponse = this.addProductInInventory(inventoryAddProductRequest);
            return this.setReturnValue(inventoryAddProductResponse,product,inventoryAddProductRequest.getCode());
        }else throw new CouldNotCreateException("Fill the missing parts");
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = this.productRepository.findAll();
        return products.stream().map(this::mapProduct).toList();
    }

    public ProductResponse getProductById(String productId){
        Optional<Product> product = this.productRepository.findById(productId);
        if(product.isPresent()){
            return this.mapProduct(product.get());
        }else throw new CouldNotFoundException();
    }

    public void deleteProductById(String productId){
        Optional<Product> product = this.productRepository.findById(productId);
        if(product.isPresent()){
            this.productRepository.delete(product.get());
        } else throw new CouldNotFoundException();
    }

    private ProductResponse mapProduct(Product p){
        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .build();
    }

    private InventoryAddProductResponse addProductInInventory(InventoryAddProductRequest inventoryAddProductRequest){
        return webClientBuilder.build().post().uri("http://inventory-service/api/inventory/addProductToInventory")
                .bodyValue(inventoryAddProductRequest)
                .retrieve().bodyToMono(InventoryAddProductResponse.class)
                .block();
    }

    private ProductInventoryResponse setReturnValue(InventoryAddProductResponse inventoryAddProductResponse
            ,Product product, String code){
        ProductResponse productResponse = this.mapProduct(product);
        return ProductInventoryResponse.builder()
                .productInInventory(inventoryAddProductResponse)
                .product(productResponse)
                .productCode(code)
                .build();
    }

}
