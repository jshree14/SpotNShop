package com.spotnshop.service;

import com.spotnshop.model.Favorite;
import com.spotnshop.model.Offer;
import com.spotnshop.model.User;
import com.spotnshop.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {
    
    @Autowired
    private FavoriteRepository favoriteRepository;
    
    public List<Favorite> getFavoritesByUser(User user) {
        return favoriteRepository.findByUser(user);
    }
    
    public boolean isFavorite(User user, Offer offer) {
        return favoriteRepository.findByUserAndOffer(user, offer).isPresent();
    }
    
    public Favorite addToFavorites(User user, Offer offer) {
        // Check if already exists
        Optional<Favorite> existing = favoriteRepository.findByUserAndOffer(user, offer);
        if (existing.isPresent()) {
            return existing.get();
        }
        
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setOffer(offer);
        favorite.setCreatedAt(LocalDateTime.now());
        
        return favoriteRepository.save(favorite);
    }
    
    public void removeFromFavorites(User user, Offer offer) {
        Optional<Favorite> favorite = favoriteRepository.findByUserAndOffer(user, offer);
        favorite.ifPresent(favoriteRepository::delete);
    }
    
    public void removeFavoriteById(Long favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }
    
    public long getFavoriteCountByUser(User user) {
        return favoriteRepository.countByUser(user);
    }
    
    public List<Offer> getUserFavorites(User user) {
        return favoriteRepository.findByUser(user).stream()
            .map(Favorite::getOffer)
            .collect(java.util.stream.Collectors.toList());
    }
    
    public void toggleFavorite(User user, Offer offer) {
        Optional<Favorite> existing = favoriteRepository.findByUserAndOffer(user, offer);
        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
        } else {
            addToFavorites(user, offer);
        }
    }
}