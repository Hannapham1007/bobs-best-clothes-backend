package com.booleanuk.requests.user;

import com.booleanuk.requests.helpfunctions.Responses;
import com.booleanuk.requests.helpfunctions.ValidationUtils;
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>>getUserById(@PathVariable int id){
        User user = ValidationUtils.getById(id, userRepository);
        if (user == null){
            return Responses.notFound("user");
        }
        UserResponse userResponse = new UserResponse();
        userResponse.set(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createUser(@RequestBody User user){
        if(ValidationUtils.isInvalidUser(user)){
            return Responses.badRequest("create", user.getClass().getSimpleName());
        }
        User createdUser = this.userRepository.save(user);
        UserResponse response = new UserResponse();
        response.set(createdUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String>deleteAllUsers(){
        try {
            // Delete all users from the database
            userRepository.deleteAll();
            // Return a success response
            return ResponseEntity.ok().body("All users deleted successfully.");
        } catch (Exception e) {
            // Return an error response if an exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete users: " + e.getMessage());
        }
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateUserById(@PathVariable int id, @RequestBody User user){
        if(ValidationUtils.isInvalidUser((user))){
            return Responses.badRequest("update", user.getClass().getSimpleName());
        }
        User userToUpdate = ValidationUtils.getById(id, userRepository);
        if(userToUpdate == null){
            return Responses.notFound("user");
        }
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setFirstname(user.getFirstname());
        userToUpdate.setLastname(user.getLastname());
        userToUpdate.setPhone(user.getPhone());

        this.userRepository.save(userToUpdate);

        UserResponse response = new UserResponse();
        response.set(userToUpdate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
