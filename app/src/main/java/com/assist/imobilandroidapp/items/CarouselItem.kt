package com.assist.imobilandroidapp.items

import com.assist.imobilandroidapp.apiinterface.models.ListingFromDBObject

data class CarouselItem(var carouselTitle: String, var listingItemList: List<ListingFromDBObject>) {
}