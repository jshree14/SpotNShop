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
    
    @GetMapping("/")
    public String home(Model model,
                      @RequestParam(required = false) Category category,
                      @RequestParam(required = false) String city) {
        
        List<Offer> offers = offerService.searchOffers(category, city);
        model.addAttribute("offers", offers);
        model.addAttribute("categories", Category.values());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedCity", city);
        
        return "home";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    

}