package com.spotnshop.controller;

import com.spotnshop.model.User;
import com.spotnshop.service.CustomUserDetailsService;
import com.spotnshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String viewProfile(Authentication auth, Model model) {
        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
        User user = principal.getUser();
        
        // Fetch fresh user data from database
        User dbUser = userService.findById(user.getId()).orElse(user);
        
        model.addAttribute("user", dbUser);
        return "profile/view";
    }
    
    @GetMapping("/edit")
    public String editProfile(Authentication auth, Model model) {
        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
        User user = principal.getUser();
        
        // Fetch fresh user data from database
        User dbUser = userService.findById(user.getId()).orElse(user);
        
        model.addAttribute("user", dbUser);
        return "profile/edit";
    }
    
    @PostMapping("/edit")
    public String updateProfile(@Valid @ModelAttribute("user") User updatedUser,
                               BindingResult result,
                               Authentication auth,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        
        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
        User currentUser = principal.getUser();
        
        // Fetch the current user from database to get the latest version
        User dbUser = userService.findById(currentUser.getId()).orElse(null);
            
        if (dbUser == null) {
            model.addAttribute("errorMessage", "User not found. Please try logging in again.");
            return "profile/edit";
        }
        
        // Check if email is being changed and if it already exists
        if (!dbUser.getEmail().equals(updatedUser.getEmail()) && 
            userService.existsByEmail(updatedUser.getEmail())) {
            result.rejectValue("email", "error.user", "Email already exists");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("user", dbUser);
            return "profile/edit";
        }
        
        try {
            // Update only allowed fields
            dbUser.setFullName(updatedUser.getFullName());
            dbUser.setEmail(updatedUser.getEmail());
            dbUser.setCity(updatedUser.getCity());
            dbUser.setPhoneNumber(updatedUser.getPhoneNumber());
            
            userService.saveUser(dbUser);
            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
            return "redirect:/profile";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to update profile: " + e.getMessage());
            model.addAttribute("user", dbUser);
            return "profile/edit";
        }
    }
    
    @GetMapping("/notifications")
    public String notificationSettings(Authentication auth, Model model) {
        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
        User user = principal.getUser();
        
        // Fetch fresh user data from database
        User dbUser = userService.findById(user.getId()).orElse(user);
        
        model.addAttribute("user", dbUser);
        return "profile/notifications";
    }
    
    @PostMapping("/notifications")
    public String updateNotificationSettings(@ModelAttribute("user") User updatedUser,
                                           Authentication auth,
                                           Model model,
                                           RedirectAttributes redirectAttributes) {
        
        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
        User currentUser = principal.getUser();
        
        // Fetch the current user from database
        User dbUser = userService.findById(currentUser.getId()).orElse(null);
            
        if (dbUser == null) {
            model.addAttribute("errorMessage", "User not found. Please try logging in again.");
            return "profile/notifications";
        }
        
        try {
            // Update notification preferences
            dbUser.setNotificationsEnabled(updatedUser.isNotificationsEnabled());
            dbUser.setLocalOffersNotification(updatedUser.isLocalOffersNotification());
            
            userService.saveUser(dbUser);
            redirectAttributes.addFlashAttribute("successMessage", "Notification settings updated successfully!");
            return "redirect:/profile/notifications";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to update notification settings: " + e.getMessage());
            model.addAttribute("user", dbUser);
            return "profile/notifications";
        }
    }
}