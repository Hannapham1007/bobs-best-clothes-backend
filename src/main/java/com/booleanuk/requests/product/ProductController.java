package com.booleanuk.requests.product;


import com.booleanuk.requests.category.Category;
import com.booleanuk.requests.category.CategoryRepository;
import com.booleanuk.requests.helpfunctions.Responses;
import com.booleanuk.requests.helpfunctions.ValidationUtils;
import com.booleanuk.requests.payload.request.AddProductRequest;
import com.booleanuk.requests.payload.request.LoginRequest;
import com.booleanuk.requests.reponses.ApiResponse;
import com.booleanuk.requests.reponses.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<ProductListResponse> getAllProducts(){
        ProductListResponse productListResponse = new ProductListResponse();
        List<Product> products = this.productRepository.findAll();
        productListResponse.set(products);
        return new ResponseEntity<>(productListResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createProduct(@Valid @RequestBody AddProductRequest addProductRequest){

        Product productToAdd = new Product(addProductRequest.getTitle(), addProductRequest.getPrice(), addProductRequest.getImageURL(), addProductRequest.getDescription());
        Product createdProduct = this.productRepository.save(productToAdd);

        Category category = ValidationUtils.getById(addProductRequest.getCategoryId(), categoryRepository);

        if(ValidationUtils.isInvalidCategory(category)){
            return Responses.badRequest("find", "category");
        }

        createdProduct.setCategory(category);

        createdProduct = this.productRepository.save(productToAdd);

        ProductResponse response = new ProductResponse();
        response.set(createdProduct);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteProductById(@PathVariable int id) {
        Product productToDelete = ValidationUtils.getById(id, productRepository);
        if (productToDelete == null){
            return Responses.notFound("product");
        }
        this.productRepository.delete(productToDelete);
        ProductResponse response = new ProductResponse();
        response.set(productToDelete);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateProductById(@PathVariable int id, @RequestBody Product product){

        if(ValidationUtils.isInvalidProduct(product)){
            return Responses.badRequest("update", product.getClass().getSimpleName());
        }

        Product productToUpdate = ValidationUtils.getById(id, productRepository);
        if(productToUpdate == null){
            return Responses.notFound("product");
        }

        productToUpdate.setTitle(product.getTitle());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setImageURL(product.getImageURL());

        this.productRepository.save(productToUpdate);

        ProductResponse response = new ProductResponse();
        response.set(productToUpdate);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
