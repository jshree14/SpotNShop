package com.spotnshop.config;

import com.spotnshop.model.User;
import com.spotnshop.model.UserRole;
import com.spotnshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== DataInitializer running ===");
        
        // Create default admin user
        if (!userService.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@spotnshop.com");
            admin.setPassword("admin123");
            admin.setFullName("System Administrator");
            admin.setRole(UserRole.ADMIN);
            admin.setCity("System");
            userService.registerUser(admin);
            System.out.println("✓ Default admin user created: admin/admin123");
        } else {
            System.out.println("✓ Admin user already exists");
        }
        
        // Create sample shopkeeper
        if (!userService.existsByUsername("shopkeeper1")) {
            User shopkeeper = new User();
            shopkeeper.setUsername("shopkeeper1");
            shopkeeper.setEmail("shop1@example.com");
            shopkeeper.setPassword("shop123");
            shopkeeper.setFullName("John's Electronics");
            shopkeeper.setRole(UserRole.SHOPKEEPER);
            shopkeeper.setCity("New York");
            shopkeeper.setPhoneNumber("555-0123");
            userService.registerUser(shopkeeper);
            System.out.println("✓ Sample shopkeeper created: shopkeeper1/shop123");
        } else {
            System.out.println("✓ Shopkeeper user already exists");
        }
        
        // Create sample customer
        if (!userService.existsByUsername("customer1")) {
            User customer = new User();
            customer.setUsername("customer1");
            customer.setEmail("customer1@example.com");
            customer.setPassword("customer123");
            customer.setFullName("Jane Smith");
            customer.setRole(UserRole.CUSTOMER);
            customer.setCity("New York");
            userService.registerUser(customer);
            System.out.println("✓ Sample customer created: customer1/customer123");
        } else {
            System.out.println("✓ Customer user already exists");
        }
        
        System.out.println("=== DataInitializer completed ===");
    }
}