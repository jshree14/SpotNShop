package com.spotnshop.model;

public enum PremiumPlan {
    BASIC("Basic", 30, 99.0, "Enhanced visibility, Priority support"),
    PREMIUM("Premium", 90, 249.0, "Featured listings, Advanced analytics, Priority support"),
    ENTERPRISE("Enterprise", 365, 999.0, "Unlimited listings, Full analytics, Dedicated support, Custom branding");
    
    private final String displayName;
    private final int durationDays;
    private final double price;
    private final String features;
    
    PremiumPlan(String displayName, int durationDays, double price, String features) {
        this.displayName = displayName;
        this.durationDays = durationDays;
        this.price = price;
        this.features = features;
    }
    
    public String getDisplayName() { return displayName; }
    public int getDurationDays() { return durationDays; }
    public double getPrice() { return price; }
    public String getFeatures() { return features; }
}