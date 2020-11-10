/*
 * Creator: Kamran Noorinejad on 5/19/20 12:11 PM
 * Last modified: 5/19/20 12:11 PM
 * Copyright: All rights reserved Ⓒ 2020
 * http://www.itskamran.ir/
 */

package com.kamran.cats.data.api

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.kamran.cats.data.model.Cat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Kamran Noorinejad on 5/19/2020 AD 12:11.
 * Edited by Kamran Noorinejad on 5/19/2020 AD 12:11.
 */
interface ApiService {

    @GET("images/search")
    suspend fun getAllPublicImages(
        @Query("apiKey", encoded = true) api_key: String,
        @Query("size", encoded = true) size: String,
        @Query("limit", encoded = true) limit: Int
    ): Response<List<Cat>>

    @GET("images/")
    suspend fun getImage(
        @Query("apiKey", encoded = true) api_key: String,
        @Query("image_id", encoded = true) size: String
    ): Response<Cat>

}

@Keep
data class ServiceResponse<T>(
    @SerializedName("response") val response: T
)