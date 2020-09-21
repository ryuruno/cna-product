package com.example.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class policyHandler {

//    @StreamListener(Processor.INPUT)
//    public void onEventByString(@Payload String productChanged){
//        System.out.println(productChanged);
//    }

    @StreamListener(Processor.INPUT)
    public void onEventByObject(@Payload ProductChanged productChanged){
        if("ProductChanged".equals(productChanged.getEventType())) {
            System.out.println("getEventType = " + productChanged.getEventType());
            System.out.println("getProductName = " + productChanged.getProductName());
        }
    }

    @Autowired
    ProductRepository productRepository;

    @StreamListener(Processor.INPUT)
    public void onEventByObject(@Payload com.example.order.OrderPlaced orderPlaced){
        //OrderPlaced 데이터를 json -> 객체로 파싱 -> 해결


        //주문이 생성되었을때만
        if("OrderPlaced".equals(orderPlaced.getEventType())) {
            //상품저장
            //Product p = new Product();
            //p.setId(orderPlaced.orderId);
            //p.setStock(orderPlaced.getOrderQty());
            //productRepository.save(p);
            //System.out.println("orderPlaced = " + orderPlaced.getOrderQty());

            // 상품 ID값의 재고 변경
            Optional<Product> productById = productRepository.findById(orderPlaced.getProductId());
            Product p = productById.get();
            p.setStock(p.getStock() - orderPlaced.getOrderQty());
            productRepository.save(p);
        }


    }

}
