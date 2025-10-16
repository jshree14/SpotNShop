package com.spotnshop.controller;

import com.spotnshop.model.PremiumPlan;
import com.spotnshop.model.User;
import com.spotnshop.service.PremiumAccountService;
import com.spotnshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/premium")
public class PremiumController {
    
    @Autowired
    private PremiumAccountService premiumAccountService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String premiumPage(Model model, Principal principal) {
        if (principal != null) {
            Optional<User> userOpt = userService.findByUsername(principal.getName());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                model.addAttribute("user", user);
                model.addAttribute("hasPremium", premiumAccountService.hasPremiumAccount(user));
                model.addAttribute("hasActivePremium", premiumAccountService.hasActivePremium(user));
                
                if (premiumAccountService.hasPremiumAccount(user)) {
                    model.addAttribute("premiumAccount", premiumAccountService.getPremiumAccount(user).orElse(null));
                }
            }
        }
        
        model.addAttribute("plans", premiumAccountService.getAllPlans());
        return "premium/plans";
    }
    
    @PostMapping("/subscribe")
    @PreAuthorize("hasRole('SHOPKEEPER')")
    public String subscribeToPremium(
            @RequestParam PremiumPlan plan,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        
        try {
            Optional<User> userOpt = userService.findByUsername(principal.getName());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                premiumAccountService.createPremiumAccount(user, plan);
                redirectAttributes.addFlashAttribute("success", "Successfully subscribed to " + plan.getDisplayName() + " plan!");
            } else {
                redirectAttributes.addFlashAttribute("error", "User not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to subscribe: " + e.getMessage());
        }
        
        return "redirect:/premium";
    }
    
    @PostMapping("/upgrade")
    @PreAuthorize("hasRole('SHOPKEEPER')")
    public String upgradePlan(
            @RequestParam PremiumPlan plan,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        
        try {
            Optional<User> userOpt = userService.findByUsername(principal.getName());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                premiumAccountService.upgradePlan(user, plan);
                redirectAttributes.addFlashAttribute("success", "Successfully upgraded to " + plan.getDisplayName() + " plan!");
            } else {
                redirectAttributes.addFlashAttribute("error", "User not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to upgrade: " + e.getMessage());
        }
        
        return "redirect:/premium";
    }
    
    @PostMapping("/cancel")
    @PreAuthorize("hasRole('SHOPKEEPER')")
    public String cancelPremium(
            Principal principal,
            RedirectAttributes redirectAttributes) {
        
        try {
            Optional<User> userOpt = userService.findByUsername(principal.getName());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                premiumAccountService.cancelPremiumAccount(user);
                redirectAttributes.addFlashAttribute("success", "Premium account cancelled successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "User not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to cancel: " + e.getMessage());
        }
        
        return "redirect:/premium";
    }
    
    @GetMapping("/features")
    public String premiumFeatures(Model model) {
        model.addAttribute("plans", premiumAccountService.getAllPlans());
        return "premium/features";
    }
}