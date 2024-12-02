//package com.example.webshopapi.controllers;
//
//import com.example.webshopapi.dataTransferObjects.ProductDTO;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class WebSocketController {
//
//    @MessageMapping("/product/update")
//    @SendTo("/topic/products")
//    public ProductDTO sendProductUpdate(ProductDTO productDTO) {
//        return productDTO;
//    }
//}