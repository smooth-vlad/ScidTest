package com.android.scidtest.data.network

import com.android.scidtest.data.network.classes.Book
import com.android.scidtest.data.network.classes.Link
import com.google.gson.annotations.SerializedName
import retrofit2.http.Header

data class BooksResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("result") val result: Result,
    @SerializedName("error") val error: Object?,
) {
    data class Result(
        @SerializedName("current_page") val currentPage: Int,
        @SerializedName("data") val data: List<Book>,
        @SerializedName("first_page_url") val firstPageUrl: String,
        @SerializedName("from") val from: Int,
        @SerializedName("last_page") val lastPage: Int,
        @SerializedName("last_page_url") val lastPageUrl: String,
        @SerializedName("links") val links: List<Link>,
        @SerializedName("next_page_url") val nextPageUrl: String?,
        @SerializedName("path") val path: String,
        @SerializedName("per_page") val perPage: Int,
        @SerializedName("prev_page_url") val prevPageUrl: String?,
        @SerializedName("to") val to: Int,
        @SerializedName("total") val total: Int,
    )
}