package com.assist.imobilandroidapp.items

class ListingItem(
    listingImage: Int,
    listingTitle: String,
    listingLocation: String,
    listingPrice: String
) {

    private var listingImage: Int
    private var listingTitle: String
    private var listingLocation: String
    private var listingPrice: String

    init {
        this.listingImage = listingImage
        this.listingTitle = listingTitle
        this.listingLocation = listingLocation
        this.listingPrice = listingPrice
    }

    fun getListingImage(): Int {
        return listingImage
    }

    fun setImageListing(newImage: Int) {
        listingImage = newImage
    }

    fun getListingTitle(): String {
        return listingTitle
    }

    fun setListingTitle(newTitle: String) {
        listingTitle = newTitle
    }

    fun getListingLocation(): String {
        return listingLocation
    }

    fun setListingLocation(newLocation: String) {
        listingLocation = newLocation
    }

    fun getListingPrice(): String {
        return listingPrice
    }

    fun setListingPrice(newPrice: String) {
        listingPrice = newPrice
    }
}