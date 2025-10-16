package com.spotnshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;
    
    @Column(length = 1000)
    private String review;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id")
    private Offer offer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public Rating() {}
    
    public Rating(Integer rating, String review, Offer offer, User customer) {
        this.rating = rating;
        this.review = review;
        this.offer = offer;
        this.customer = customer;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    
    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }
    
    public Offer getOffer() { return offer; }
    public void setOffer(Offer offer) { this.offer = offer; }
    
    public User getCustomer() { return customer; }
    public void setCustomer(User customer) { this.customer = customer; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}