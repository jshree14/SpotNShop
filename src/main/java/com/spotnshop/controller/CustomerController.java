package com.spotnshop.controller;

import com.spotnshop.model.Category;
import com.spotnshop.model.Offer;
import com.spotnshop.model.User;
import com.spotnshop.service.CustomUserDetailsService;
import com.spotnshop.service.OfferService;
import com.spotnshop.service.FavoriteService;
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
    
    @Autowired
    private FavoriteService favoriteService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        try {
            CustomUserDetailsService.CustomUserPrincipal principal = 
                (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
            User customer = principal.getUser();
            
            // Get recent offers (limit to 6 for dashboard)
            List<Offer> allOffers = offerService.getApprovedOffers();
            List<Offer> recentOffers = allOffers.stream()
                .limit(6)
                .collect(java.util.stream.Collectors.toList());
            
            // Get unique cities count
            long cityCount = allOffers.stream()
                .map(Offer::getCity)
                .distinct()
                .count();
            
            model.addAttribute("recentOffers", recentOffers);
            model.addAttribute("totalOffers", allOffers.size());
            model.addAttribute("favoriteCount", favoriteService.getFavoriteCountByUser(customer));
            model.addAttribute("cityCount", cityCount);
            model.addAttribute("categories", Category.values());
            
            return "customer/dashboard";
        } catch (Exception e) {
            System.out.println("Customer dashboard error: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error loading dashboard: " + e.getMessage());
            return "customer/dashboard";
        }
    }
    
    @GetMapping("/offers")
    public String browseOffers(Model model,
                              @RequestParam(required = false) Category category,
                              @RequestParam(required = false) String city) {
        
        try {
            System.out.println("=== Customer Browse Offers ===");
            System.out.println("Category: " + category);
            System.out.println("City: " + city);
            
            List<Offer> offers = offerService.searchOffers(category, city);
            System.out.println("Returning " + offers.size() + " offers to template");
            
            model.addAttribute("offers", offers);
            model.addAttribute("categories", Category.values());
            model.addAttribute("selectedCategory", category);
            model.addAttribute("selectedCity", city);
            
            return "customer/offers";
        } catch (Exception e) {
            System.out.println("Error in customer offers: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error loading offers: " + e.getMessage());
            model.addAttribute("offers", new java.util.ArrayList<>());
            model.addAttribute("categories", Category.values());
            return "customer/offers";
        }
    }
}