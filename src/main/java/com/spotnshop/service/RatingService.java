package com.spotnshop.service;

import com.spotnshop.model.Rating;
import com.spotnshop.model.Offer;
import com.spotnshop.model.User;
import com.spotnshop.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    
    @Autowired
    private RatingRepository ratingRepository;
    
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }
    
    public List<Rating> getRatingsForOffer(Offer offer) {
        return ratingRepository.findByOffer(offer);
    }
    
    public Optional<Rating> findUserRatingForOffer(User customer, Offer offer) {
        return ratingRepository.findByOfferAndCustomer(offer, customer);
    }
    
    public Double getAverageRatingForOffer(Offer offer) {
        Double avg = ratingRepository.getAverageRatingForOffer(offer);
        return avg != null ? avg : 0.0;
    }
    
    public Long getReviewCountForOffer(Offer offer) {
        return ratingRepository.getReviewCountForOffer(offer);
    }
    
    public Double getAverageRatingForShopkeeper(User shopkeeper) {
        Double avg = ratingRepository.getAverageRatingForShopkeeper(shopkeeper);
        return avg != null ? avg : 0.0;
    }
    
    public boolean hasUserRatedOffer(User customer, Offer offer) {
        return ratingRepository.findByOfferAndCustomer(offer, customer).isPresent();
    }
}