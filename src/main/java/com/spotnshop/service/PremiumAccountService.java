package com.spotnshop.service;

import com.spotnshop.model.PremiumAccount;
import com.spotnshop.model.PremiumPlan;
import com.spotnshop.model.User;
import com.spotnshop.repository.PremiumAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PremiumAccountService {
    
    @Autowired
    private PremiumAccountRepository premiumAccountRepository;
    
    public PremiumAccount createPremiumAccount(User user, PremiumPlan plan) {
        // Check if user already has a premium account
        Optional<PremiumAccount> existing = premiumAccountRepository.findByUser(user);
        if (existing.isPresent()) {
            throw new RuntimeException("User already has a premium account");
        }
        
        PremiumAccount premiumAccount = new PremiumAccount(user, plan);
        premiumAccount.setAmountPaid(plan.getPrice());
        return premiumAccountRepository.save(premiumAccount);
    }
    
    public Optional<PremiumAccount> getPremiumAccount(User user) {
        return premiumAccountRepository.findByUser(user);
    }
    
    public boolean hasPremiumAccount(User user) {
        return premiumAccountRepository.existsByUser(user);
    }
    
    public boolean hasActivePremium(User user) {
        Optional<PremiumAccount> account = premiumAccountRepository.findByUser(user);
        return account.isPresent() && account.get().isActive();
    }
    
    public PremiumAccount upgradePlan(User user, PremiumPlan newPlan) {
        Optional<PremiumAccount> existing = premiumAccountRepository.findByUser(user);
        if (existing.isEmpty()) {
            throw new RuntimeException("No premium account found for user");
        }
        
        PremiumAccount account = existing.get();
        account.setPlan(newPlan);
        account.setExpiresAt(LocalDateTime.now().plusDays(newPlan.getDurationDays()));
        account.setAmountPaid(newPlan.getPrice());
        return premiumAccountRepository.save(account);
    }
    
    public void cancelPremiumAccount(User user) {
        Optional<PremiumAccount> account = premiumAccountRepository.findByUser(user);
        if (account.isPresent()) {
            account.get().setActive(false);
            premiumAccountRepository.save(account.get());
        }
    }
    
    public void renewPremiumAccount(User user) {
        Optional<PremiumAccount> account = premiumAccountRepository.findByUser(user);
        if (account.isPresent()) {
            PremiumAccount premiumAccount = account.get();
            premiumAccount.setStartedAt(LocalDateTime.now());
            premiumAccount.setExpiresAt(LocalDateTime.now().plusDays(premiumAccount.getPlan().getDurationDays()));
            premiumAccount.setActive(true);
            premiumAccountRepository.save(premiumAccount);
        }
    }
    
    public PremiumPlan[] getAllPlans() {
        return PremiumPlan.values();
    }
}