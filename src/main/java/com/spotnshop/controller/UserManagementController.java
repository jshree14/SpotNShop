package com.spotnshop.controller;

import com.spotnshop.model.User;
import com.spotnshop.model.UserRole;
import com.spotnshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class UserManagementController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/users/stats")
    public String userStats(Model model) {
        List<User> allUsers = userService.getAllUsers();
        
        long customerCount = allUsers.stream().filter(u -> u.getRole() == UserRole.CUSTOMER).count();
        long shopkeeperCount = allUsers.stream().filter(u -> u.getRole() == UserRole.SHOPKEEPER).count();
        long adminCount = allUsers.stream().filter(u -> u.getRole() == UserRole.ADMIN).count();
        
        model.addAttribute("totalUsers", allUsers.size());
        model.addAttribute("customerCount", customerCount);
        model.addAttribute("shopkeeperCount", shopkeeperCount);
        model.addAttribute("adminCount", adminCount);
        model.addAttribute("users", allUsers);
        
        return "admin/user-stats";
    }
    
    @PostMapping("/users/{id}/toggle-status")
    public String toggleUserStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getAllUsers().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
                
            if (user != null && user.getRole() != UserRole.ADMIN) {
                user.setEnabled(!user.isEnabled());
                userService.saveUser(user);
                
                String status = user.isEnabled() ? "enabled" : "disabled";
                redirectAttributes.addFlashAttribute("successMessage", 
                    "User " + user.getUsername() + " has been " + status + ".");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update user status.");
        }
        
        return "redirect:/admin/users";
    }
}