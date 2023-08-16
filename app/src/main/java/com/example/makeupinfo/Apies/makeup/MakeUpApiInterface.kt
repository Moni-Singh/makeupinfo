package com.example.makeupinfo.Apies.makeup

import com.example.makeupinfo.model.Makeups.MakeUpResposne
import retrofit2.Call
import retrofit2.http.GET

interface MakeUpApiInterface {


    @GET("v1/products.json")
     fun getProductDetails(): Call<MakeUpResposne>
}