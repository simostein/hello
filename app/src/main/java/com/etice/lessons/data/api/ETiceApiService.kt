package com.etice.lessons.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ETiceApiService {
    @GET("lecons-explicites/fetch.php")
    suspend fun fetchDocument(
        @Query("level") level: Int,
        @Query("phase") phase: Int,
        @Query("week") week: String,
        @Query("subject") subject: String
    ): Response<ResponseBody>
}
