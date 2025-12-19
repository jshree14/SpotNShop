package com.spotnshop.controller;

import com.spotnshop.model.Favorite;
import com.spotnshop.model.Offer;
import com.spotnshop.model.User;
import com.spotnshop.service.CustomUserDetailsService;
import com.spotnshop.service.FavoriteService;
import com.spotnshop.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/favorites")
public class FavoritesController {
    
    @Autowired
    private FavoriteService favoriteService;
    
    @Autowired
    private OfferService offerService;
    
    @GetMapping
    public String listFavorites(Model model, Authentication auth) {
        try {
            CustomUserDetailsService.CustomUserPrincipal principal = 
                (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
            User user = principal.getUser();
            
            List<Favorite> favorites = favoriteService.getFavoritesByUser(user);
            model.addAttribute("favorites", favorites);
            
            return "favorites/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading favorites: " + e.getMessage());
            return "favorites/list";
        }
    }
    
    @PostMapping("/add/{offerId}")
    @ResponseBody
    public String addToFavorites(@PathVariable Long offerId, Authentication auth) {
        try {
            CustomUserDetailsService.CustomUserPrincipal principal = 
                (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
            User user = principal.getUser();
            
            Offer offer = offerService.findById(offerId).orElse(null);
            if (offer == null) {
                return "error";
            }
            
            // Toggle favorite instead of just adding
            favoriteService.toggleFavorite(user, offer);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    @PostMapping("/remove/{offerId}")
    @ResponseBody
    public String removeFromFavorites(@PathVariable Long offerId, Authentication auth) {
        try {
            CustomUserDetailsService.CustomUserPrincipal principal = 
                (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
            User user = principal.getUser();
            
            Offer offer = offerService.findById(offerId).orElse(null);
            if (offer == null) {
                return "error";
            }
            
            favoriteService.removeFromFavorites(user, offer);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }
    
    @PostMapping("/remove/{favoriteId}/redirect")
    public String removeFromFavoritesRedirect(@PathVariable Long favoriteId, 
                                            Authentication auth,
                                            RedirectAttributes redirectAttributes) {
        try {
            favoriteService.removeFavoriteById(favoriteId);
            redirectAttributes.addFlashAttribute("successMessage", "Removed from favorites!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error removing favorite: " + e.getMessage());
        }
        
        return "redirect:/favorites";
    }
}