package com.spotnshop.controller;

import com.spotnshop.model.Rating;
import com.spotnshop.model.Offer;
import com.spotnshop.model.User;
import com.spotnshop.service.CustomUserDetailsService;
import com.spotnshop.service.RatingService;
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
@RequestMapping("/ratings")
public class RatingController {
    
    @Autowired
    private RatingService ratingService;
    
    @Autowired
    private OfferService offerService;
    
    @GetMapping("/offer/{offerId}")
    public String viewOfferRatings(@PathVariable Long offerId, Model model, Authentication auth) {
        Offer offer = offerService.findById(offerId).orElse(null);
        if (offer == null) {
            return "redirect:/";
        }
        
        List<Rating> ratings = ratingService.getRatingsForOffer(offer);
        Double averageRating = ratingService.getAverageRatingForOffer(offer);
        Long reviewCount = ratingService.getReviewCountForOffer(offer);
        
        model.addAttribute("offer", offer);
        model.addAttribute("ratings", ratings);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("reviewCount", reviewCount);
        
        if (auth != null) {
            CustomUserDetailsService.CustomUserPrincipal principal = 
                (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
            User user = principal.getUser();
            
            Rating userRating = ratingService.findUserRatingForOffer(user, offer).orElse(null);
            model.addAttribute("userRating", userRating);
            model.addAttribute("hasRated", userRating != null);
        }
        
        return "ratings/offer-ratings";
    }
    
    @PostMapping("/submit")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitRating(@RequestParam Long offerId,
                                                           @RequestParam Integer rating,
                                                           @RequestParam(required = false) String review,
                                                           Authentication auth) {
        try {
            CustomUserDetailsService.CustomUserPrincipal principal = 
                (CustomUserDetailsService.CustomUserPrincipal) auth.getPrincipal();
            User user = principal.getUser();
            
            Offer offer = offerService.findById(offerId).orElse(null);
            if (offer == null) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Offer not found"));
            }
            
            // Check if user already rated this offer
            Rating existingRating = ratingService.findUserRatingForOffer(user, offer).orElse(null);
            
            if (existingRating != null) {
                // Update existing rating
                existingRating.setRating(rating);
                existingRating.setReview(review);
                ratingService.saveRating(existingRating);
            } else {
                // Create new rating
                Rating newRating = new Rating(rating, review, offer, user);
                ratingService.saveRating(newRating);
            }
            
            // Return updated statistics
            Double newAverage = ratingService.getAverageRatingForOffer(offer);
            Long newCount = ratingService.getReviewCountForOffer(offer);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Rating submitted successfully",
                "averageRating", newAverage,
                "reviewCount", newCount
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}