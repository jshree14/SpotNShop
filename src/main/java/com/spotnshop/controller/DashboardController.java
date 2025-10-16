package com.spotnshop.controller;

import com.spotnshop.model.User;
import com.spotnshop.model.UserRole;
import com.spotnshop.service.CustomUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    
    @GetMapping("/dashboard")
    public String dashboard(Authentication auth) {
        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
        User user = principal.getUser();
        
        switch (user.getRole()) {
            case ADMIN:
                return "redirect:/admin/dashboard";
            case SHOPKEEPER:
                return "redirect:/shopkeeper/dashboard";
            case CUSTOMER:
                return "redirect:/customer/dashboard";
            default:
                return "redirect:/";
        }
    }
}