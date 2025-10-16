package com.spotnshop.model;

public enum AnalyticsType {
    OFFER_VIEW("Offer View"),
    OFFER_CLICK("Offer Click"),
    SEARCH("Search"),
    FAVORITE_ADD("Favorite Added"),
    FAVORITE_REMOVE("Favorite Removed"),
    RATING_SUBMIT("Rating Submitted"),
    CHAT_MESSAGE("Chat Message"),
    PROFILE_VIEW("Profile View");
    
    private final String displayName;
    
    AnalyticsType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}