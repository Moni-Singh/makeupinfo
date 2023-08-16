package com.example.makeupinfo.Apies.gallery


import com.example.makeupinfo.model.Photos.GalleryPhotoResponse
import com.example.makeupinfo.model.Photos.SearchPhotoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GalleryApiInterface {

    @GET("v2/list")
    fun getGalleryList(): Call<GalleryPhotoResponse>


    @GET("search/photos")
    fun getMakeUpPhotos(
        @Query("client_id") clientId: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): Call<SearchPhotoResponse>


}