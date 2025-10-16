package com.spotnshop.service;

import com.spotnshop.model.Favorite;
import com.spotnshop.model.Offer;
import com.spotnshop.model.User;
import com.spotnshop.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {
    
    @Autowired
    private FavoriteRepository favoriteRepository;
    
    public List<Offer> getUserFavorites(User user) {
        return favoriteRepository.findByUser(user)
                .stream()
                .map(Favorite::getOffer)
                .collect(Collectors.toList());
    }
    
    public boolean isFavorite(User user, Offer offer) {
        return favoriteRepository.existsByUserAndOffer(user, offer);
    }
    
    @Transactional
    public void addToFavorites(User user, Offer offer) {
        if (!isFavorite(user, offer)) {
            Favorite favorite = new Favorite(user, offer);
            favoriteRepository.save(favorite);
        }
    }
    
    @Transactional
    public void removeFromFavorites(User user, Offer offer) {
        favoriteRepository.deleteByUserAndOffer(user, offer);
    }
    
    @Transactional
    public void toggleFavorite(User user, Offer offer) {
        if (isFavorite(user, offer)) {
            removeFromFavorites(user, offer);
        } else {
            addToFavorites(user, offer);
        }
    }
    
    public Long getFavoriteCount(Offer offer) {
        return (long) favoriteRepository.findByOffer(offer).size();
    }
}