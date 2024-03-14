package com.booleanuk.requests.user;

import com.booleanuk.requests.reponses.ApiResponse;
import com.booleanuk.requests.reponses.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<UserListResponse>getAllUsers(){
        UserListResponse userListResponse = new UserListResponse();
        List<User> users = this.userRepository.findAll();
        userListResponse.set(users);
        return new ResponseEntity<>(userListResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createUser(@RequestBody User user){
        if(isInvalidRequest(user)){
            return badRequest();
        }
        User createdUser = this.userRepository.save(user);
        UserResponse response = new UserResponse();
        response.set(createdUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    private boolean isInvalidRequest(User user){
        return user.getFirstName() == null || user.getLastName() == null || user.getPhone() == null || user.getEmail() == null;
    }

    private ResponseEntity<ApiResponse<?>> badRequest(){
        ErrorResponse error = new ErrorResponse();
        error.set("Could not create user, please check all required fields are correct");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiResponse<?>> notFound(){
        ErrorResponse error = new ErrorResponse();
        error.set("No user with that id were found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
