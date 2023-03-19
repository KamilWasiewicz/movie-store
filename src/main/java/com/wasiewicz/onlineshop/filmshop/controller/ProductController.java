package com.wasiewicz.onlineshop.filmshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/products")
    public ResponseEntity<String> getProducts(){
        return ResponseEntity.ok("list of product");
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("secured endpoint");
    }
}
