package com.example.fampaytask

import com.example.fampaytask.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface FamApi {
    @GET("04a04703-5557-4c84-a127-8c55335bb3b4")
    suspend fun getData(): Response<ApiResponse>
}