package by.profs.bookstore.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParseObject(
    @Json(name = "price") val price: Double?,
    @Json(name = "link") val link: String,
    @Json(name = "author") val author: String,
    @Json(name = "title") val title: String,
    @Json(name = "picUrl") val picUrl: String)