package com.spotnshop.controller;

import com.spotnshop.model.User;
import com.spotnshop.service.CustomUserDetailsService;
import com.spotnshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping
    public String viewProfile(Model model, Authentication auth) {
        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
        User user = principal.getUser();
        
        // Get fresh user data from database to show latest changes
        User freshUser = userService.findById(user.getId()).orElse(user);
        model.addAttribute("user", freshUser);
        return "profile/view";
    }
    
    @GetMapping("/edit")
    public String editProfile(Model model, Authentication auth) {
        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
        User user = principal.getUser();
        
        // Get fresh user data from database for editing
        User freshUser = userService.findById(user.getId()).orElse(user);
        model.addAttribute("user", freshUser);
        return "profile/edit";
    }
    
    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute User updatedUser, 
                               Authentication auth, 
                               RedirectAttributes redirectAttributes,
                               Model model) {
        try {
            CustomUserDetailsService.CustomUserPrincipal principal = 
                (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
            User currentUser = principal.getUser();
            
            // Get fresh user from database to avoid stale data
            User dbUser = userService.findById(currentUser.getId()).orElse(currentUser);
            
            // Update only allowed fields
            dbUser.setFullName(updatedUser.getFullName());
            dbUser.setEmail(updatedUser.getEmail());
            dbUser.setCity(updatedUser.getCity());
            dbUser.setPhoneNumber(updatedUser.getPhoneNumber());
            
            // Save to database
            User savedUser = userService.updateUser(dbUser);
            
            // Update the principal's user object with fresh data
            principal.setUser(savedUser);
            
            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
            
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating profile: " + e.getMessage());
            // Return to edit form with error
            model.addAttribute("user", updatedUser);
            model.addAttribute("errorMessage", "Error updating profile: " + e.getMessage());
            return "profile/edit";
        }
        
        return "redirect:/profile";
    }
    
    @GetMapping("/notifications")
    public String notificationSettings(Model model, Authentication auth) {
        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
        User user = principal.getUser();
        
        // Get fresh user data from database
        User freshUser = userService.findById(user.getId()).orElse(user);
        model.addAttribute("user", freshUser);
        return "profile/notifications";
    }
    
    @PostMapping("/notifications")
    public String updateNotifications(@RequestParam(defaultValue = "false") boolean notificationsEnabled,
                                    @RequestParam(defaultValue = "false") boolean localOffersNotification,
                                    Authentication auth,
                                    RedirectAttributes redirectAttributes) {
        try {
            CustomUserDetailsService.CustomUserPrincipal principal = 
                (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
            User user = principal.getUser();
            
            // Get fresh user from database
            User dbUser = userService.findById(user.getId()).orElse(user);
            
            dbUser.setNotificationsEnabled(notificationsEnabled);
            dbUser.setLocalOffersNotification(localOffersNotification);
            
            User savedUser = userService.updateUser(dbUser);
            
            // Update the principal's user object
            principal.setUser(savedUser);
            
            redirectAttributes.addFlashAttribute("successMessage", "Notification settings updated!");
            
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating settings: " + e.getMessage());
        }
        
        return "redirect:/profile/notifications";
    }
}