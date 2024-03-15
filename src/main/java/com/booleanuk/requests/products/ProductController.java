package com.booleanuk.requests.products;


import com.booleanuk.requests.reponses.ApiResponse;
import com.booleanuk.requests.reponses.ErrorResponse;
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

    @GetMapping
    public ResponseEntity<ProductListResponse> getAllProducts(){
        ProductListResponse productListResponse = new ProductListResponse();
        List<Product> products = this.productRepository.findAll();
        productListResponse.set(products);
        return new ResponseEntity<>(productListResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createCustomer(@RequestBody Product product){
        if(isInvalidRequest(product)){
            return badRequest("create");
        }
        Product createdProduct = this.productRepository.save(product);
        ProductResponse response = new ProductResponse();
        response.set(createdProduct);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteProductById(@PathVariable int id) {
        Product productToDelete = this.getAProduct(id);
        if (productToDelete == null){
            return notFound();
        }
        this.productRepository.delete(productToDelete);
        ProductResponse response = new ProductResponse();
        response.set(productToDelete);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateProductById(@PathVariable int id, @RequestBody Product product){

        if(isInvalidRequest(product)){
            return badRequest("update");
        }

        Product productToUpdate = getAProduct(id);
        if(productToUpdate == null){
            return notFound();
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


    private boolean isInvalidRequest(Product product){
        return product.getTitle() == null || product.getDescription() == null || product.getPrice() <= 0 || product.getImageURL() == null;
    }

    private Product getAProduct(int id) { return this.productRepository.findById(id).orElse(null);
    }

    private ResponseEntity<ApiResponse<?>> badRequest(String operation){
        ErrorResponse error = new ErrorResponse();
        error.set("Could not " + operation +  " product, please check all required fields are correct");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiResponse<?>> notFound(){
        ErrorResponse error = new ErrorResponse();
        error.set("No product with that id were found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
