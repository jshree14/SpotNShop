package com.spotnshop.model;

public enum OfferStatus {
    PENDING("Pending Review"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    EXPIRED("Expired");
    
    private final String displayName;
    
    OfferStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}