package com.android.scidtest.data.network

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

const val PAGE_SIZE = 10

interface CandidateScidService {

    @GET("books/")
    suspend fun getBooks(
        @Query("page") page: Int
    ): BooksResponse
}