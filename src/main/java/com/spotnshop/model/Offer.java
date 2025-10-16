package com.spotnshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String title;
    
    @NotBlank
    @Column(length = 1000)
    private String description;
    
    @Enumerated(EnumType.STRING)
    private Category category;
    
    private String customCategory; // For shopkeeper's own categories
    
    @NotBlank
    private String shopName;
    
    @NotBlank
    private String shopAddress;
    
    @NotBlank
    private String city;
    
    private String contactNumber;
    
    private Double originalPrice;
    
    private Double offerPrice;
    
    @Enumerated(EnumType.STRING)
    private OfferType offerType = OfferType.DISCOUNT;
    
    private String specialOfferDetails; // For freebies, BOGO, etc.
    
    private String imagePath; // Path to uploaded image
    
    private Integer viewCount = 0;
    private Integer clickCount = 0;
    private boolean featured = false;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "valid_until")
    private LocalDateTime validUntil;
    
    @Enumerated(EnumType.STRING)
    private OfferStatus status = OfferStatus.PENDING;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopkeeper_id")
    private User shopkeeper;
    
    private String adminComments;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public Offer() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    
    public String getCustomCategory() { return customCategory; }
    public void setCustomCategory(String customCategory) { this.customCategory = customCategory; }
    
    public String getDisplayCategory() {
        if (customCategory != null && !customCategory.trim().isEmpty()) {
            return customCategory;
        }
        return category != null ? category.getDisplayName() : "Other";
    }
    
    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }
    
    public String getShopAddress() { return shopAddress; }
    public void setShopAddress(String shopAddress) { this.shopAddress = shopAddress; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    
    public Double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(Double originalPrice) { this.originalPrice = originalPrice; }
    
    public Double getOfferPrice() { return offerPrice; }
    public void setOfferPrice(Double offerPrice) { this.offerPrice = offerPrice; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getValidUntil() { return validUntil; }
    public void setValidUntil(LocalDateTime validUntil) { this.validUntil = validUntil; }
    
    public OfferStatus getStatus() { return status; }
    public void setStatus(OfferStatus status) { this.status = status; }
    
    public User getShopkeeper() { return shopkeeper; }
    public void setShopkeeper(User shopkeeper) { this.shopkeeper = shopkeeper; }
    
    public String getAdminComments() { return adminComments; }
    public void setAdminComments(String adminComments) { this.adminComments = adminComments; }
    
    public OfferType getOfferType() { return offerType; }
    public void setOfferType(OfferType offerType) { this.offerType = offerType; }
    
    public String getSpecialOfferDetails() { return specialOfferDetails; }
    public void setSpecialOfferDetails(String specialOfferDetails) { this.specialOfferDetails = specialOfferDetails; }
    
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    
    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }
    
    public Integer getClickCount() { return clickCount; }
    public void setClickCount(Integer clickCount) { this.clickCount = clickCount; }
    
    public boolean isFeatured() { return featured; }
    public void setFeatured(boolean featured) { this.featured = featured; }
    
    public Double getDiscountPercentage() {
        if (originalPrice != null && offerPrice != null && originalPrice > 0) {
            return ((originalPrice - offerPrice) / originalPrice) * 100;
        }
        return 0.0;
    }
    
    public String getOfferDisplayText() {
        switch (offerType) {
            case FREEBIE:
            case BOGO:
            case MINIMUM_PURCHASE:
            case SPECIAL:
                return specialOfferDetails != null ? specialOfferDetails : "Special Offer";
            case PERCENTAGE_OFF:
                if (originalPrice != null && offerPrice != null) {
                    return String.format("%.0f%% OFF", getDiscountPercentage());
                }
                return "Percentage Off";
            case DISCOUNT:
            default:
                if (originalPrice != null && offerPrice != null) {
                    return String.format("₹%.2f (was ₹%.2f)", offerPrice, originalPrice);
                }
                return "Special Price";
        }
    }
}