package com.booleanuk.requests.cartItem;

import com.booleanuk.requests.category.Category;
import com.booleanuk.requests.category.CategoryListResponse;
import com.booleanuk.requests.category.CategoryResponse;
import com.booleanuk.requests.helpfunctions.Responses;
import com.booleanuk.requests.helpfunctions.ValidationUtils;
import com.booleanuk.requests.reponses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cartItems")
public class CartItemController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @GetMapping
    public ResponseEntity<CartItemListResponse> getAllCartItems(){
        CartItemListResponse cartItemListResponse = new CartItemListResponse();
        List<CartItem> cartItems = cartItemRepository.findAll();
        cartItemListResponse.set(cartItems);
        return new ResponseEntity<>(cartItemListResponse, HttpStatus.OK);
    }

   /* @PostMapping
    ResponseEntity<ApiResponse<?>> createCartItems(@RequestBody CartItem cartItem){
        if(ValidationUtils.isInvalidCartItem(cartItem)){
            return Responses.badRequest("create", cartItem.getClass().getSimpleName());
        }
        CartItem createCartItem = this.cartItemRepository.save(cartItem);
        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.set(createCartItem);
        return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    }
*/
    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> deleteCartItemById(@PathVariable int id) {
        CartItem cartItemToDelete = ValidationUtils.getById(id, cartItemRepository);
        if (cartItemToDelete == null){
            return Responses.notFound("cartItem");
        }
        this.cartItemRepository.delete(cartItemToDelete);
        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.set(cartItemToDelete);
        return ResponseEntity.ok(cartItemResponse);
    }




}
