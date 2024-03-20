package com.booleanuk.requests.order;

import com.booleanuk.requests.cartItem.CartItem;
import com.booleanuk.requests.cartItem.CartItemRepository;
import com.booleanuk.requests.helpfunctions.Responses;
import com.booleanuk.requests.helpfunctions.ValidationUtils;
import com.booleanuk.requests.order.Order;
import com.booleanuk.requests.order.OrderListResponse;
import com.booleanuk.requests.order.OrderResponse;
import com.booleanuk.requests.product.Product;
import com.booleanuk.requests.product.ProductRepository;
import com.booleanuk.requests.reponses.ApiResponse;
import com.booleanuk.requests.user.User;
import com.booleanuk.requests.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("orders")
public class OrderController {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public ResponseEntity<OrderListResponse> getAllOrders(){
        OrderListResponse orderListResponse = new OrderListResponse();
        List<Order> orders = this.orderRepository.findAll();
        orderListResponse.set(orders);
        return new ResponseEntity<>(orderListResponse, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> getOrderByUserId(@PathVariable int userId){
        User user = ValidationUtils.getById(userId, userRepository);
        if(user == null){
            return Responses.notFound("user");
        }
        List<Order> orders = new ArrayList<>();
        for (Order order : orderRepository.findAll()){
            if (order.getUser() == user){
                orders.add(order);
            }
        }
        OrderListResponse orderListResponse = new OrderListResponse();
        orderListResponse.set(orders);
        return new ResponseEntity<>(orderListResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createOrder(@RequestBody Order order) {
        if (ValidationUtils.isInvalidOrder(order)) {
            return Responses.badRequest("create", order.getClass().getSimpleName());
        }

        // Fetch the user from the database based on the provided user ID
        User user = userRepository.findById(order.getUser().getId()).orElse(null);
        if (user == null) {
            return Responses.notFound(" user");
        }
        order.setUser(user);

        Order createdOrder = this.orderRepository.save(order);

        List<CartItem> cartItems = new ArrayList<>();

        for (CartItem cartItem : order.getCartItems()) {
            Product product = productRepository.findById(cartItem.getProduct().getId()).orElse(null);
            if (product != null) {
                cartItem.setProduct(product);
                cartItem.setOrder(createdOrder);
                cartItemRepository.save(cartItem);
                cartItems.add(cartItem);
            } else{
                return Responses.badRequest("find", "product");
            }
        }

        createdOrder.setCartItems(cartItems);

        // Fetch the order with fully populated cart items
        createdOrder = orderRepository.findById(createdOrder.getId()).orElse(null);

        // Response
        OrderResponse response = new OrderResponse();
        response.set(createdOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getOrderById(@PathVariable int id) {
       Order orderToGet = ValidationUtils.getById(id, orderRepository);
       if(orderToGet == null){
           Responses.notFound("order");
       }
       OrderResponse orderResponse = new OrderResponse();
       orderResponse.set(orderToGet);
       return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> deleteOrderById(@PathVariable int id){
        Order orderToDelete = ValidationUtils.getById(id, orderRepository);
        if(orderToDelete == null){
            return Responses.notFound("order");
        }
        this.orderRepository.delete(orderToDelete);
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.set(orderToDelete);
        return ResponseEntity.ok(orderResponse);
    }


}
