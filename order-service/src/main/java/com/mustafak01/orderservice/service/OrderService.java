package com.mustafak01.orderservice.service;

import com.mustafak01.orderservice.dto.request.OrderLineItemsDto;
import com.mustafak01.orderservice.dto.request.OrderRequest;
import com.mustafak01.orderservice.dto.request.UpdateQuantityOfProductInOrderLine;
import com.mustafak01.orderservice.dto.response.InventoryQuantityResponse;
import com.mustafak01.orderservice.dto.response.InventoryResponse;
import com.mustafak01.orderservice.dto.response.OrderLineItemUpdateQuantityResponse;
import com.mustafak01.orderservice.dto.response.OrderResponse;
import com.mustafak01.orderservice.dto.response.converter.OrderLineItemsDtoConverter;
import com.mustafak01.orderservice.exception.CouldNotFoundException;
import com.mustafak01.orderservice.exception.CouldNotFoundProductException;
import com.mustafak01.orderservice.exception.CouldNotUpdateException;
import com.mustafak01.orderservice.model.Order;
import com.mustafak01.orderservice.model.OrderLineItems;
import com.mustafak01.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {


    private final OrderRepository orderRepository;
    private final OrderLineItemsDtoConverter orderLineItemsDtoConverter;
    private final WebClient.Builder webClientBuilder;


    public void saveOrder(OrderRequest orderRequest){
        if(orderRequest!=null){
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            List<OrderLineItems> orderLineItems
                    = orderRequest.getOrderLineItemsDto().stream()
                    .map(this::mapToDto).toList();
            order.setOrderLineItems(orderLineItems);

            //check product if in inventory service. And save it if in stock.
            if(this.checkProductsIfInStock(order)){
                this.orderRepository.save(order);
            }else throw new CouldNotFoundProductException();
        }
    }

    public List<OrderResponse> getAllOrders(){
        List<Order> orders = this.orderRepository.findAll();
        return orders.stream().map(this::mapOrder).toList();
    }

    public OrderResponse getOrderByOrderNumber(String orderNumber){
        Optional<Order> order = this.orderRepository.findOrderByOrderNumber(orderNumber);
        if(order.isPresent()) return this.mapOrder(order.get());
        else throw new CouldNotFoundException();
    }

    public void deleteOrderByOrderNumber(String orderNumber){
        Optional<Order> order = this.orderRepository.findOrderByOrderNumber(orderNumber);
        if(order.isPresent()){
            this.orderRepository.delete(order.get());
        } else throw new CouldNotFoundException();
    }

    // TODO: 5.06.2023
    //If new quantity is 0, delete item from orderLine
    //Change quantity data in inventory when quantity in orderLine is changed (as orders add or removes from orderLine).
    public OrderLineItemUpdateQuantityResponse updateQuantityOfProductInOrderLine(UpdateQuantityOfProductInOrderLine updateReq){
        Optional<Order> order = this.orderRepository.findOrderByOrderNumber(updateReq.getOrderNumber());
        Integer quantityOfProduct = this.checkProductIfInStock(updateReq.getProductCode()).getQuantity();
        if(updateReq.getNewQuantity()>quantityOfProduct){
            throw new CouldNotUpdateException("Yeterli Stok Bulunamadı");
        }
        if(order.isPresent()){
            for (OrderLineItems orderLineItem:order.get().getOrderLineItems()) {
                if(orderLineItem.getCode().equals(updateReq.getProductCode())
                && !orderLineItem.getQuantity().equals(updateReq.getNewQuantity())){
                    orderLineItem.setQuantity(updateReq.getNewQuantity());
                }else throw new CouldNotUpdateException("Missing Data");
            }
            this.orderRepository.save(order.get());
        }else throw new CouldNotFoundProductException();
        return OrderLineItemUpdateQuantityResponse.builder()
                .productCode(this.checkProductIfInStock(updateReq.getProductCode()).getCode())
                .previousQuantity(quantityOfProduct)
                .newQuantity(updateReq.getNewQuantity())
                .build();
    }


    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemDto){
        OrderLineItems orderLineItem = new OrderLineItems();
        orderLineItem.setCode(orderLineItemDto.getCode());
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setId(orderLineItemDto.getId());
        return orderLineItem;
    }

    private OrderResponse mapOrder(Order o){
        return OrderResponse.builder()
                .orderLineItemsDto(this.orderLineItemsDtoConverter
                        .mapOrderLineItemsToList(o.getOrderLineItems()))
                .orderNumber(o.getOrderNumber())
                .build();
    }

    private boolean checkProductsIfInStock(Order order){
        List<String> codes = order.getOrderLineItems().stream().map(OrderLineItems::getCode).toList();
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get().uri("http://inventory-service/api/inventory/isInStock"
                        ,uriBuilder -> uriBuilder.queryParam("code",codes).build())
                .retrieve().bodyToMono(InventoryResponse[].class)
                .block();
        if(inventoryResponses!=null){
            return Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
        }else return false;
    }

    private InventoryQuantityResponse checkProductIfInStock(String productCode){
         return webClientBuilder.build().get().uri("http://inventory-service/api/inventory/getQuantity"
                        ,uriBuilder -> uriBuilder.queryParam("code",productCode).build())
                .retrieve().bodyToMono(InventoryQuantityResponse.class)
                .block();
    }


}
