package com.spotnshop.service;

import com.spotnshop.model.Offer;
import com.spotnshop.model.User;
import com.spotnshop.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {
    
    @Autowired
    private UserService userService;
    
    public void notifyUsersOfNewOffer(Offer offer) {
        if (offer == null || offer.getCity() == null) {
            return;
        }
        
        try {
            // Find all customers in the same city who have notifications enabled
            List<User> usersToNotify = userService.getAllUsers().stream()
                .filter(user -> user.getRole() == UserRole.CUSTOMER)
                .filter(User::isNotificationsEnabled)
                .filter(User::isLocalOffersNotification)
                .filter(user -> offer.getCity().equalsIgnoreCase(user.getCity()))
                .toList();
            
            for (User user : usersToNotify) {
                sendNotificationEmail(user, offer);
            }
            
            System.out.println("✓ Notifications sent to " + usersToNotify.size() + " users for offer: " + offer.getTitle());
            
        } catch (Exception e) {
            System.out.println("⚠ Failed to send notifications: " + e.getMessage());
        }
    }
    
    private void sendNotificationEmail(User user, Offer offer) {
        // In a real application, you would integrate with an email service like:
        // - SendGrid, Mailgun, AWS SES, etc.
        // For now, we'll just log the notification
        
        String subject = "New Offer in " + offer.getCity() + " - " + offer.getTitle();
        String message = String.format(
            "Hi %s,\n\n" +
            "A new offer has been posted in your city!\n\n" +
            "Offer: %s\n" +
            "Shop: %s\n" +
            "Category: %s\n" +
            "Details: %s\n\n" +
            "Visit SpotNShop to see more details!\n\n" +
            "Best regards,\n" +
            "SpotNShop Team",
            user.getFullName(),
            offer.getTitle(),
            offer.getShopName(),
            offer.getDisplayCategory(),
            offer.getOfferDisplayText()
        );
        
        // Log the email (in production, send actual email)
        System.out.println("📧 EMAIL NOTIFICATION:");
        System.out.println("To: " + user.getEmail());
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
        System.out.println("---");
    }
}