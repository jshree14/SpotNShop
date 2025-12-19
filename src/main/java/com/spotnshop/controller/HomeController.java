package com.spotnshop.controller;

import com.spotnshop.model.Category;
import com.spotnshop.model.Offer;
import com.spotnshop.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class HomeController {
    
    @Autowired
    private OfferService offerService;
    
    @GetMapping({"/", "/home"})
    public String home(Model model,
                      @RequestParam(required = false) Category category,
                      @RequestParam(required = false) String city) {
        
        try {
            List<Offer> offers;
            
            // If search parameters are provided, use search functionality
            if (category != null || (city != null && !city.trim().isEmpty())) {
                offers = offerService.searchOffers(category, city);
                System.out.println("Search performed - Category: " + category + ", City: " + city + ", Results: " + offers.size());
            } else {
                // Get all approved offers for home page
                offers = offerService.getApprovedOffers();
                System.out.println("Showing all approved offers: " + offers.size());
            }
            
            model.addAttribute("offers", offers);
            model.addAttribute("categories", Category.values());
            model.addAttribute("selectedCategory", category);
            model.addAttribute("selectedCity", city);
            
            return "home";
        } catch (Exception e) {
            System.out.println("Home page error: " + e.getMessage());
            e.printStackTrace();
            // Return home page even if there's an error, but with empty offers
            model.addAttribute("offers", new java.util.ArrayList<>());
            model.addAttribute("categories", Category.values());
            return "home";
        }
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    

}