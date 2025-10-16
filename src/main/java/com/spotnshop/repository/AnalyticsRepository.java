package com.spotnshop.repository;

import com.spotnshop.model.Analytics;
import com.spotnshop.model.AnalyticsType;
import com.spotnshop.model.Offer;
import com.spotnshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {
    
    List<Analytics> findByTypeAndCreatedAtBetween(AnalyticsType type, LocalDateTime start, LocalDateTime end);
    
    List<Analytics> findByOfferAndType(Offer offer, AnalyticsType type);
    
    @Query("SELECT COUNT(a) FROM Analytics a WHERE a.type = :type AND a.createdAt BETWEEN :start AND :end")
    Long countByTypeAndDateRange(@Param("type") AnalyticsType type, 
                                @Param("start") LocalDateTime start, 
                                @Param("end") LocalDateTime end);
    
    List<Analytics> findByUserAndType(User user, AnalyticsType type);
    
    @Query("SELECT a.offer, COUNT(a) as viewCount FROM Analytics a WHERE a.type = 'OFFER_VIEW' GROUP BY a.offer ORDER BY viewCount DESC")
    List<Object[]> findMostViewedOffers();
    
    @Query("SELECT COUNT(a) FROM Analytics a WHERE a.type = :type")
    Long countByType(@Param("type") AnalyticsType type);
}