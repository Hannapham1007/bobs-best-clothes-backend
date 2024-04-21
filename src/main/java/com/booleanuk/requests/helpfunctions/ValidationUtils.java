package com.booleanuk.requests.helpfunctions;

import com.booleanuk.requests.category.Category;
import com.booleanuk.requests.order.Order;
import com.booleanuk.requests.product.Product;
import com.booleanuk.requests.user.User;
import com.booleanuk.requests.roles.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public class ValidationUtils {

    public static boolean isInvalidProduct(Product product) {
        return product.getTitle() == null || product.getDescription() == null || product.getPrice() <= 0 || product.getImageURL() == null;
    }

    public static boolean isInvalidCategory(Category category) {
        // Add category-specific validation logic here
        return category.getName() == null || category.getDescription() == null;
    }

    public static boolean isInvalidUser(User user) {
        // Add user-specific validation logic here
        return user.getFirstname() == null || user.getLastname() == null || user.getPhone() == null || user.getEmail() == null;
    }

    public static boolean isInvalidOrder(Order order) {
        // Add category-specific validation logic here
        return order.getTotal() <=0 || order.getCartItems() == null;
    }

    public static boolean isInvalidRole(Role role){
        return role.getName() == null;

    }

    public static <T> T getById(int id, JpaRepository<T, Integer> repository) {
        return repository.findById(id).orElse(null);
    }
}
