package com.spotnshop.model;

public enum Category {
    COSMETICS("Cosmetics"),
    FOOD("Food"),
    FRUITS("Fruits"),
    CLOTHING("Clothing"),
    GROCERY("Grocery"),
    CLEANING_EQUIPMENT("Cleaning Equipment"),
    ELECTRONICS("Electronics"),
    BOOKS("Books"),
    SPORTS("Sports"),
    OTHER("Other");
    
    private final String displayName;
    
    Category(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}