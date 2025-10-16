package com.spotnshop.controller;

import com.spotnshop.model.Category;
import com.spotnshop.model.Offer;
import com.spotnshop.model.User;
import com.spotnshop.service.CustomUserDetailsService;
import com.spotnshop.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/customer")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerController {
    
    @Autowired
    private OfferService offerService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth,
                           @RequestParam(required = false) Category category,
                           @RequestParam(required = false) String city) {
        
        try {
            CustomUserDetailsService.CustomUserPrincipal principal = 
                (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
            User customer = principal.getUser();
            
            // Get offers based on filters - allow searching all cities
            List<Offer> offers = offerService.searchOffers(category, city);
            
            model.addAttribute("offers", offers);
            model.addAttribute("categories", Category.values());
            model.addAttribute("selectedCategory", category);
            model.addAttribute("selectedCity", city);
            model.addAttribute("customer", customer);
            
            return "customer/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading customer dashboard: " + e.getMessage());
            return "error";
        }
    }
    
    @GetMapping("/offers")
    public String browseOffers(Model model,
                              @RequestParam(required = false) Category category,
                              @RequestParam(required = false) String city) {
        
        List<Offer> offers = offerService.searchOffers(category, city);
        
        model.addAttribute("offers", offers);
        model.addAttribute("categories", Category.values());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedCity", city);
        
        return "customer/offers";
    }
}