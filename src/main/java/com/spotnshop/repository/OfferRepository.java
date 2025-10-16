package com.spotnshop.repository;

import com.spotnshop.model.Offer;
import com.spotnshop.model.OfferStatus;
import com.spotnshop.model.Category;
import com.spotnshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByStatus(OfferStatus status);
    List<Offer> findByShopkeeper(User shopkeeper);
    List<Offer> findByCategory(Category category);
    List<Offer> findByCity(String city);
    List<Offer> findByCategoryAndCity(Category category, String city);
    List<Offer> findByStatusAndCity(OfferStatus status, String city);
    List<Offer> findByStatusAndCategory(OfferStatus status, Category category);
    List<Offer> findByStatusAndCategoryAndCity(OfferStatus status, Category category, String city);
    
    @Query("SELECT o FROM Offer o WHERE o.status = :status AND " +
           "(:category IS NULL OR o.category = :category) AND " +
           "(:city IS NULL OR o.city = :city)")
    List<Offer> findFilteredOffers(@Param("status") OfferStatus status,
                                  @Param("category") Category category,
                                  @Param("city") String city);
    
    @Query("SELECT o FROM Offer o WHERE o.status = :status AND " +
           "(:category IS NULL OR o.category = :category) AND " +
           "(:city IS NULL OR o.city = :city) AND " +
           "(o.validUntil IS NULL OR o.validUntil > CURRENT_TIMESTAMP)")
    List<Offer> findValidOffers(@Param("status") OfferStatus status,
                               @Param("category") Category category,
                               @Param("city") String city);
    
    @Query("SELECT o FROM Offer o WHERE o.status = 'APPROVED' AND " +
           "(o.validUntil IS NULL OR o.validUntil > CURRENT_TIMESTAMP)")
    List<Offer> findValidApprovedOffers();
}