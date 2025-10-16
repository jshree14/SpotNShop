package com.spotnshop.model;

public enum OfferType {
    DISCOUNT("Price Discount"),
    FREEBIE("Free Item/Gift"),
    BOGO("Buy One Get One"),
    PERCENTAGE_OFF("Percentage Off"),
    MINIMUM_PURCHASE("Minimum Purchase Offer"),
    SPECIAL("Special Offer");
    
    private final String displayName;
    
    OfferType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}