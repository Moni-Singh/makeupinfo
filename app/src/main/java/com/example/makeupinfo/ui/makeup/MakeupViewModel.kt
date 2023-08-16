package com.example.makeupinfo.ui.makeup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makeupinfo.Apies.gallery.GalleryApIInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MakeupViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text

    fun getMakeupProductDetials() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = com.example.makeupinfo.Apies.gallery.GalleryApIInstance.galleryApiInterface!!.getGalleryList().execute()
                if (response.isSuccessful) {
                    val data = response.body()
                    println("photolist:$data")
                } else {
                    Log.e("GalleryViewModel", "API call failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("GalleryViewModel", "API call failed: ${e.message}")
            }
        }
    }




}