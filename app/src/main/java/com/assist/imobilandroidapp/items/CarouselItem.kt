package com.assist.imobilandroidapp.items

class CarouselItem(carouselTitle: String, listingItemList: List<ListingItem>) {

    private var carouselTitle: String
    private var listingItemList: List<ListingItem>

    init {
        this.carouselTitle = carouselTitle
        this.listingItemList = listingItemList
    }

    fun getCarouselTitle(): String {
        return carouselTitle
    }

    fun setCarouselTitle(newTitle: String) {
        carouselTitle = newTitle
    }

    fun getListingItemList(): List<ListingItem> {
        return listingItemList
    }

    fun setListingItemList(newList: List<ListingItem>) {
        listingItemList = newList
    }
}