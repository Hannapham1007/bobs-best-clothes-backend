package com.booleanuk.requests.products;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<ProductListResponse> getAllProducts(){
        ProductListResponse productListResponse = new ProductListResponse();
        List<Product> products = this.productRepository.findAll();
        productListResponse.set(products);
        return new ResponseEntity<>(productListResponse, HttpStatus.OK);
    }
}
