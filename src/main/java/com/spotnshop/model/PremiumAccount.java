package com.spotnshop.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "premium_accounts")
public class PremiumAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @Enumerated(EnumType.STRING)
    private PremiumPlan plan;
    
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    
    private boolean active = true;
    
    private Double amountPaid;
    private String paymentId;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (startedAt == null) {
            startedAt = LocalDateTime.now();
        }
    }
    
    // Constructors
    public PremiumAccount() {}
    
    public PremiumAccount(User user, PremiumPlan plan) {
        this.user = user;
        this.plan = plan;
        this.startedAt = LocalDateTime.now();
        this.expiresAt = startedAt.plusDays(plan.getDurationDays());
    }
    
    // Business methods
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
    
    public boolean isActive() {
        return active && !isExpired();
    }
    
    public long getDaysRemaining() {
        if (isExpired()) return 0;
        return java.time.Duration.between(LocalDateTime.now(), expiresAt).toDays();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public PremiumPlan getPlan() { return plan; }
    public void setPlan(PremiumPlan plan) { this.plan = plan; }
    
    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    
    public boolean getActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public Double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(Double amountPaid) { this.amountPaid = amountPaid; }
    
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

