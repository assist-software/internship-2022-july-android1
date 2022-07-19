package com.assist.imobilandroidapp.items

class ListingItemWithDesc(
    listingImage: Int,
    listingTitle: String,
    listingDescription: String,
    listingPrice: String
) {

    private var listingImage: Int
    private var listingTitle: String
    private var listingDescription: String
    private var listingPrice: String

    init {
        this.listingImage = listingImage
        this.listingTitle = listingTitle
        this.listingDescription = listingDescription
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

    fun getListingDescription(): String {
        return listingDescription
    }

    fun setListingDescription(newLocation: String) {
        listingDescription = newLocation
    }

    fun getListingPrice(): String {
        return listingPrice
    }

    fun setListingPrice(newPrice: String) {
        listingPrice = newPrice
    }
}