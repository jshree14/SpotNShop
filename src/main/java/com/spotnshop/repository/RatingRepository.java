package com.spotnshop.repository;

import com.spotnshop.model.Rating;
import com.spotnshop.model.Offer;
import com.spotnshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByOffer(Offer offer);
    List<Rating> findByCustomer(User customer);
    Optional<Rating> findByOfferAndCustomer(Offer offer, User customer);
    
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.offer = :offer")
    Double getAverageRatingForOffer(@Param("offer") Offer offer);
    
    @Query("SELECT COUNT(r) FROM Rating r WHERE r.offer = :offer")
    Long getReviewCountForOffer(@Param("offer") Offer offer);
    
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.offer.shopkeeper = :shopkeeper")
    Double getAverageRatingForShopkeeper(@Param("shopkeeper") User shopkeeper);
}