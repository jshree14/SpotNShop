package com.spotnshop.config;

import com.spotnshop.model.User;
import com.spotnshop.model.UserRole;
import com.spotnshop.model.Offer;
import com.spotnshop.model.Category;
import com.spotnshop.model.OfferStatus;
import com.spotnshop.service.UserService;
import com.spotnshop.service.OfferService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private OfferService offerService;
    
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
        
        // Create sample offers
        createSampleOffers();
        
        System.out.println("=== DataInitializer completed ===");
    }
    
    private void createSampleOffers() {
        try {
            Optional<User> shopkeeperOpt = userService.findByUsername("shopkeeper1");
            if (shopkeeperOpt.isPresent()) {
                User shopkeeper = shopkeeperOpt.get();
                
                // Check if sample offers already exist
                if (offerService.getOffersByShopkeeper(shopkeeper).isEmpty()) {
                    // Sample offer 1
                    Offer offer1 = new Offer();
                    offer1.setTitle("50% Off Electronics Sale");
                    offer1.setDescription("Huge discount on smartphones, laptops, and accessories. Limited time offer!");
                    offer1.setCategory(Category.ELECTRONICS);
                    offer1.setShopName("John's Electronics");
                    offer1.setShopAddress("123 Main Street, Downtown");
                    offer1.setCity("New York");
                    offer1.setContactNumber("555-0123");
                    offer1.setOriginalPrice(800.00);
                    offer1.setOfferPrice(400.00);
                    offer1.setValidUntil(java.time.LocalDateTime.now().plusDays(30));
                    offer1.setShopkeeper(shopkeeper);
                    offer1.setStatus(OfferStatus.APPROVED);
                    
                    // Sample offer 2
                    Offer offer2 = new Offer();
                    offer2.setTitle("Fresh Organic Fruits - Buy 2 Get 1 Free");
                    offer2.setDescription("Premium quality organic fruits. Perfect for healthy living!");
                    offer2.setCategory(Category.FRUITS);
                    offer2.setShopName("John's Electronics");
                    offer2.setShopAddress("123 Main Street, Downtown");
                    offer2.setCity("New York");
                    offer2.setContactNumber("555-0123");
                    offer2.setOriginalPrice(15.00);
                    offer2.setOfferPrice(10.00);
                    offer2.setValidUntil(java.time.LocalDateTime.now().plusDays(7));
                    offer2.setShopkeeper(shopkeeper);
                    offer2.setStatus(OfferStatus.APPROVED);
                    
                    Offer savedOffer1 = offerService.createOffer(offer1);
                    Offer savedOffer2 = offerService.createOffer(offer2);
                    
                    // Manually approve them
                    offerService.approveOffer(savedOffer1.getId(), "Auto-approved sample offer");
                    offerService.approveOffer(savedOffer2.getId(), "Auto-approved sample offer");
                    
                    System.out.println("✓ Sample offers created and approved");
                } else {
                    System.out.println("✓ Sample offers already exist");
                }
            }
        } catch (Exception e) {
            System.out.println("⚠ Could not create sample offers: " + e.getMessage());
            e.printStackTrace();
        }
    }
}