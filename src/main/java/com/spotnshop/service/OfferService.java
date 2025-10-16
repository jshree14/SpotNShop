package com.spotnshop.service;

import com.spotnshop.model.Offer;
import com.spotnshop.model.OfferStatus;
import com.spotnshop.model.Category;
import com.spotnshop.model.User;
import com.spotnshop.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    
    @Autowired
    private OfferRepository offerRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    public Offer createOffer(Offer offer) {
        offer.setStatus(OfferStatus.PENDING);
        return offerRepository.save(offer);
    }
    
    public List<Offer> getPendingOffers() {
        return offerRepository.findByStatus(OfferStatus.PENDING);
    }
    
    public List<Offer> getApprovedOffers() {
        return offerRepository.findByStatus(OfferStatus.APPROVED);
    }
    
    public List<Offer> getOffersByShopkeeper(User shopkeeper) {
        return offerRepository.findByShopkeeper(shopkeeper);
    }
    
    public List<Offer> searchOffers(Category category, String city) {
        // Always filter by APPROVED status and valid date for customer searches
        return offerRepository.findValidOffers(OfferStatus.APPROVED, category, city);
    }
    
    public List<Offer> getValidApprovedOffers() {
        return offerRepository.findValidApprovedOffers();
    }
    
    public Optional<Offer> findById(Long id) {
        return offerRepository.findById(id);
    }
    
    public Offer approveOffer(Long offerId, String adminComments) {
        Optional<Offer> offerOpt = offerRepository.findById(offerId);
        if (offerOpt.isPresent()) {
            Offer offer = offerOpt.get();
            offer.setStatus(OfferStatus.APPROVED);
            offer.setAdminComments(adminComments);
            Offer savedOffer = offerRepository.save(offer);
            
            // Send notifications to users in the same city
            notificationService.notifyUsersOfNewOffer(savedOffer);
            
            return savedOffer;
        }
        return null;
    }
    
    public Offer rejectOffer(Long offerId, String adminComments) {
        Optional<Offer> offerOpt = offerRepository.findById(offerId);
        if (offerOpt.isPresent()) {
            Offer offer = offerOpt.get();
            offer.setStatus(OfferStatus.REJECTED);
            offer.setAdminComments(adminComments);
            return offerRepository.save(offer);
        }
        return null;
    }
    
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }
    
    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }
}