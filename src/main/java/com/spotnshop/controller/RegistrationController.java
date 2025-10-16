package com.spotnshop.controller;

import com.spotnshop.model.User;
import com.spotnshop.model.UserRole;
import com.spotnshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
public class RegistrationController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", UserRole.values());
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                              BindingResult result,
                              @RequestParam("confirmPassword") String confirmPassword,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        // Additional validation
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            result.rejectValue("password", "error.user", "Password is required");
        } else if (!user.getPassword().equals(confirmPassword)) {
            result.rejectValue("password", "error.user", "Passwords do not match");
        }
        
        if (user.getUsername() != null && !user.getUsername().trim().isEmpty()) {
            if (userService.existsByUsername(user.getUsername())) {
                result.rejectValue("username", "error.user", "Username already exists");
            }
        }
        
        if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
            if (userService.existsByEmail(user.getEmail())) {
                result.rejectValue("email", "error.user", "Email already exists");
            }
        }
        
        // Validate role (don't allow ADMIN registration)
        if (user.getRole() == UserRole.ADMIN) {
            result.rejectValue("role", "error.user", "Cannot register as admin");
        }
        
        if (user.getRole() == null) {
            result.rejectValue("role", "error.user", "Please select a role");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("roles", UserRole.values());
            return "register";
        }
        
        try {
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Registration successful! You can now login with your credentials.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Registration failed: " + e.getMessage());
            model.addAttribute("roles", UserRole.values());
            return "register";
        }
    }
}