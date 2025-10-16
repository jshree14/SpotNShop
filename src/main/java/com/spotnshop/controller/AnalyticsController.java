package com.spotnshop.controller;

import com.spotnshop.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {
    
    @Autowired
    private AnalyticsService analyticsService;
    
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SHOPKEEPER')")
    public String analyticsDashboard(Model model, Principal principal) {
        Map<String, Long> stats = analyticsService.getDashboardStats();
        model.addAttribute("stats", stats);
        model.addAttribute("recentActivity", analyticsService.getRecentActivity(10));
        model.addAttribute("mostViewedOffers", analyticsService.getMostViewedOffers());
        return "analytics/dashboard";
    }
    
    @GetMapping("/api/stats")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN') or hasRole('SHOPKEEPER')")
    public ResponseEntity<Map<String, Long>> getStats() {
        return ResponseEntity.ok(analyticsService.getDashboardStats());
    }
    
    @GetMapping("/api/recent-activity")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN') or hasRole('SHOPKEEPER')")
    public ResponseEntity<?> getRecentActivity() {
        return ResponseEntity.ok(analyticsService.getRecentActivity(20));
    }
}