package com.example.makeupinfo.Apies.gallery

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object GalleryApIInstance {
    private const val BASE_URL = "https://api.unsplash.com/"
    private const val mTag = "GALLERY_PHOTO"
    private fun <S> createService(serviceClass: Class<S>): S? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .build()

        return try {
            val retrofit: Retrofit? = Retrofit.Builder().addConverterFactory(
                GsonConverterFactory.create()
            )
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
            retrofit?.create(serviceClass)
        } catch (e: IllegalArgumentException) {
            // this exception when bad url used
            Log.e(mTag, "Error is  :: " + e.message)
            null
        } catch (e: Exception) {
            Log.e(mTag, "Error is  :: " + e.message)
            null
        }
    }


    private var apiInterface: GalleryApiInterface? = null
    val galleryApiInterface: GalleryApiInterface?
        get() {
            if (apiInterface == null) {
                apiInterface =
                   createService(GalleryApiInterface::class.java)
            }
            return apiInterface
        }

}
