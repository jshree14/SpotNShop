package com.spotnshop.repository;

import com.spotnshop.model.PremiumAccount;
import com.spotnshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PremiumAccountRepository extends JpaRepository<PremiumAccount, Long> {
    
    Optional<PremiumAccount> findByUser(User user);
    
    Optional<PremiumAccount> findByUserId(Long userId);
    
    boolean existsByUser(User user);
    
    @Query("SELECT p FROM PremiumAccount p WHERE p.active = true AND p.expiresAt > :now")
    List<PremiumAccount> findActiveAccounts(LocalDateTime now);
    
    @Query("SELECT p FROM PremiumAccount p WHERE p.active = true AND p.expiresAt <= :expiryDate")
    List<PremiumAccount> findExpiringAccounts(LocalDateTime expiryDate);
    
    @Query("SELECT COUNT(p) FROM PremiumAccount p WHERE p.active = true AND p.expiresAt > :now")
    Long countActiveAccounts(LocalDateTime now);
}