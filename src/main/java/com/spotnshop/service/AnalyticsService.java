package com.spotnshop.service;

import com.spotnshop.model.Analytics;
import com.spotnshop.model.AnalyticsType;
import com.spotnshop.model.Offer;
import com.spotnshop.model.User;
import com.spotnshop.repository.AnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class AnalyticsService {
    
    @Autowired
    private AnalyticsRepository analyticsRepository;
    
    public void trackEvent(AnalyticsType type, User user, Offer offer, HttpServletRequest request) {
        Analytics analytics = new Analytics(type, offer, user, request.getSession().getId());
        analytics.setIpAddress(getClientIpAddress(request));
        analytics.setUserAgent(request.getHeader("User-Agent"));
        analytics.setReferrer(request.getHeader("Referer"));
        analyticsRepository.save(analytics);
    }
    
    public void trackOfferView(Offer offer, User user, HttpServletRequest request) {
        trackEvent(AnalyticsType.OFFER_VIEW, user, offer, request);
    }
    
    public void trackOfferClick(Offer offer, User user, HttpServletRequest request) {
        trackEvent(AnalyticsType.OFFER_CLICK, user, offer, request);
    }
    
    public void trackSearch(String query, User user, HttpServletRequest request) {
        trackEvent(AnalyticsType.SEARCH, user, null, request);
    }
    
    public void trackFavoriteAdd(Offer offer, User user, HttpServletRequest request) {
        trackEvent(AnalyticsType.FAVORITE_ADD, user, offer, request);
    }
    
    public void trackRatingSubmit(Offer offer, User user, HttpServletRequest request) {
        trackEvent(AnalyticsType.RATING_SUBMIT, user, offer, request);
    }
    
    public Map<String, Long> getDashboardStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalViews", analyticsRepository.countByType(AnalyticsType.OFFER_VIEW));
        stats.put("totalClicks", analyticsRepository.countByType(AnalyticsType.OFFER_CLICK));
        stats.put("totalSearches", analyticsRepository.countByType(AnalyticsType.SEARCH));
        stats.put("totalFavorites", analyticsRepository.countByType(AnalyticsType.FAVORITE_ADD));
        stats.put("totalRatings", analyticsRepository.countByType(AnalyticsType.RATING_SUBMIT));
        stats.put("totalChatMessages", analyticsRepository.countByType(AnalyticsType.CHAT_MESSAGE));
        return stats;
    }
    
    public List<Analytics> getRecentActivity(int limit) {
        return analyticsRepository.findAll().stream()
            .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
            .limit(limit)
            .toList();
    }
    
    public List<Object[]> getMostViewedOffers() {
        return analyticsRepository.findMostViewedOffers();
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
}