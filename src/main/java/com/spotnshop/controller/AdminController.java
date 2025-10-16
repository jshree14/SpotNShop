package com.spotnshop.controller;

import com.spotnshop.model.Offer;
import com.spotnshop.model.User;
import com.spotnshop.service.OfferService;
import com.spotnshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    @Autowired
    private OfferService offerService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Offer> pendingOffers = offerService.getPendingOffers();
        List<User> allUsers = userService.getAllUsers();
        
        model.addAttribute("pendingOffers", pendingOffers);
        model.addAttribute("totalUsers", allUsers.size());
        model.addAttribute("pendingCount", pendingOffers.size());
        
        return "admin/dashboard";
    }
    
    @GetMapping("/offers")
    public String manageOffers(Model model) {
        List<Offer> allOffers = offerService.getAllOffers();
        model.addAttribute("offers", allOffers);
        return "admin/offers";
    }
    
    @PostMapping("/offers/{id}/approve")
    public String approveOffer(@PathVariable Long id, 
                              @RequestParam(required = false) String comments) {
        offerService.approveOffer(id, comments);
        return "redirect:/admin/offers";
    }
    
    @PostMapping("/offers/{id}/reject")
    public String rejectOffer(@PathVariable Long id, 
                             @RequestParam(required = false) String comments) {
        offerService.rejectOffer(id, comments);
        return "redirect:/admin/offers";
    }
    
    @GetMapping("/users")
    public String manageUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }
}