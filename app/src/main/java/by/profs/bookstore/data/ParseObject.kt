package by.profs.bookstore.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParseObject(
    @Json(name = "shop") val shop: String,
    @Json(name = "price") val price: Double,
    @Json(name = "link") val link: String)