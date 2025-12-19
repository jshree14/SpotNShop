package com.spotnshop.controller;

import com.spotnshop.model.Category;
import com.spotnshop.model.Offer;
import com.spotnshop.model.User;
import com.spotnshop.service.OfferService;
import com.spotnshop.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/shopkeeper")
@PreAuthorize("hasRole('SHOPKEEPER')")
public class ShopkeeperController {
    
    @Autowired
    private OfferService offerService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
        User shopkeeper = principal.getUser();
        List<Offer> myOffers = offerService.getOffersByShopkeeper(shopkeeper);
        
        model.addAttribute("offers", myOffers);
        model.addAttribute("totalOffers", myOffers.size());
        
        return "shopkeeper/dashboard";
    }
    
    @GetMapping("/offers/new")
    public String newOfferForm(Model model) {
        model.addAttribute("offer", new Offer());
        model.addAttribute("categories", Category.values());
        model.addAttribute("offerTypes", com.spotnshop.model.OfferType.values());
        return "shopkeeper/new-offer";
    }
    
    @PostMapping("/offers")
    public String createOffer(@Valid @ModelAttribute Offer offer,
                             BindingResult result,
                             Authentication auth,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", Category.values());
            model.addAttribute("offerTypes", com.spotnshop.model.OfferType.values());
            return "shopkeeper/new-offer";
        }
        
        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
        User shopkeeper = principal.getUser();
        offer.setShopkeeper(shopkeeper);
        offerService.createOffer(offer);
        
        return "redirect:/shopkeeper/dashboard";
    }
    
    @GetMapping("/offers")
    public String myOffers(Model model, Authentication auth) {
        try {
            System.out.println("=== Shopkeeper Offers Page Accessed ===");
            CustomUserDetailsService.CustomUserPrincipal principal = 
                (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
            User shopkeeper = principal.getUser();
            System.out.println("Shopkeeper: " + shopkeeper.getUsername() + " (ID: " + shopkeeper.getId() + ")");
            
            List<Offer> offers = offerService.getOffersByShopkeeper(shopkeeper);
            System.out.println("Found " + offers.size() + " offers for shopkeeper");
            
            // Debug each offer
            for (Offer offer : offers) {
                System.out.println("Offer: " + offer.getTitle() + " - Status: " + offer.getStatus() + " - Category: " + offer.getCategory());
            }
            
            model.addAttribute("offers", offers);
            model.addAttribute("success", null);
            model.addAttribute("error", null);
            
            System.out.println("Returning shopkeeper/offers template");
            return "shopkeeper/offers";
        } catch (Exception e) {
            System.out.println("ERROR in shopkeeper offers: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error loading offers: " + e.getMessage());
            model.addAttribute("offers", new java.util.ArrayList<>());
            return "shopkeeper/offers";
        }
    }
    
    @PostMapping("/offers/{id}/delete")
    public String deleteOffer(@PathVariable Long id, Authentication auth, Model model) {
        try {
            CustomUserDetailsService.CustomUserPrincipal principal = 
                (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
            User shopkeeper = principal.getUser();
            
            // Verify the offer belongs to this shopkeeper
            Offer offer = offerService.findById(id).orElse(null);
            if (offer != null && offer.getShopkeeper().getId().equals(shopkeeper.getId())) {
                offerService.deleteOffer(id);
                model.addAttribute("success", "Offer deleted successfully!");
            } else {
                model.addAttribute("error", "You can only delete your own offers!");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete offer: " + e.getMessage());
        }
        
        return "redirect:/shopkeeper/offers";
    }
}