package com.booleanuk.requests.category;

import com.booleanuk.requests.helpfunctions.Responses;
import com.booleanuk.requests.helpfunctions.ValidationUtils;
import com.booleanuk.requests.product.ProductRepository;
import com.booleanuk.requests.reponses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<CategoryListResponse> getAllCategories(){
        CategoryListResponse categoryListResponse = new CategoryListResponse();
        List<Category> categories = categoryRepository.findAll();
        categoryListResponse.set(categories);
        return new ResponseEntity<>(categoryListResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createCategory(@RequestBody Category category) {
        if(ValidationUtils.isInvalidCategory(category)){
            return Responses.badRequest("create", category.getClass().getSimpleName());
        }
        Category createdCategory = this.categoryRepository.save(category);
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.set(createdCategory);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> deleteCategoryById(@PathVariable int id) {
        Category categoryToDelete = ValidationUtils.getById(id, categoryRepository);
        if (categoryToDelete == null){
            return Responses.notFound("category");
        }
        this.categoryRepository.delete(categoryToDelete);
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.set(categoryToDelete);
        return ResponseEntity.ok(categoryResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateCategory (@PathVariable int id, @RequestBody Category category){
        if(ValidationUtils.isInvalidCategory(category)){
            return Responses.badRequest("update", category.getClass().getSimpleName());
        }
        Category categoryToUpdate = ValidationUtils.getById(id, categoryRepository);
        if(categoryToUpdate == null){
            return Responses.notFound("category");
        }
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setDescription(category.getDescription());

        this.categoryRepository.save(categoryToUpdate);
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.set(categoryToUpdate);
        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }
}
