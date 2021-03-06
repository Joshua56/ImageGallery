package com.natureimages.imagegallery.network

import com.natureimages.imagegallery.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getImageResponse] method
 */
interface PixabayApiService {
    @GET("api")
    suspend fun getImageResponse(@Query("key") key: String,
                         @Query("page") page: Int): ImageResponse
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object PixabayApi {
    val retrofitService: PixabayApiService by lazy {
        retrofit.create(PixabayApiService::class.java)
    }
}

