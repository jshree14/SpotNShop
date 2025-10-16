package com.spotnshop.controller;

import com.spotnshop.model.Offer;
import com.spotnshop.model.User;
import com.spotnshop.service.CustomUserDetailsService;
import com.spotnshop.service.FavoriteService;
import com.spotnshop.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {
    
    @Autowired
    private FavoriteService favoriteService;
    
    @Autowired
    private OfferService offerService;
    
    @GetMapping
    public String viewFavorites(Authentication auth, Model model) {
        CustomUserDetailsService.CustomUserPrincipal principal = 
            (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
        User user = principal.getUser();
        
        List<Offer> favoriteOffers = favoriteService.getUserFavorites(user);
        model.addAttribute("favoriteOffers", favoriteOffers);
        model.addAttribute("user", user);
        
        return "favorites/list";
    }
    
    @PostMapping("/toggle/{offerId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> toggleFavorite(@PathVariable Long offerId, 
                                                             Authentication auth) {
        try {
            CustomUserDetailsService.CustomUserPrincipal principal = 
                (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
            User user = principal.getUser();
            
            Offer offer = offerService.findById(offerId).orElse(null);
            if (offer == null) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Offer not found"));
            }
            
            favoriteService.toggleFavorite(user, offer);
            boolean isFavorite = favoriteService.isFavorite(user, offer);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "isFavorite", isFavorite,
                "message", isFavorite ? "Added to favorites" : "Removed from favorites"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}