package com.example.makeupinfo.ui.gallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makeupinfo.model.Photos.GalleryPhotoResponseItem
import com.example.makeupinfo.model.Photos.SearchPhotoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text
    private val _galleryPhotos = MutableLiveData<List<GalleryPhotoResponseItem>>()
    val galleryPhotos: LiveData<List<GalleryPhotoResponseItem>> = _galleryPhotos


    var isLoading = false

    fun changeTittle(){
        _text.value = "Heelloo"
    }

    fun getGalleryDetailsList(page:Int) {

        if (isLoading) return

        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                com.example.makeupinfo.Apies.gallery.GalleryApIInstance.galleryApiInterface!!.getMakeUpPhotos("-QOacAK-qA_4IM9TndEqgbb3hX78MrQ_mJaDwNNMaQ4","landscape",page).enqueue(object : Callback<SearchPhotoResponse?> {
                    override fun onResponse(
                        call: Call<SearchPhotoResponse?>,
                        response: Response<SearchPhotoResponse?>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            println("photolist:$data")
                            _galleryPhotos.value = data!!.results
                        }
                    }
                    override fun onFailure(call: Call<SearchPhotoResponse?>, t: Throwable) {
                        // Handle failure
                        Log.e("GalleryViewModel", "API call failed: ${t.message}")
                    }
                })
            } catch (e: Exception) {
                Log.e("GalleryViewModel", "API call failed: ${e.message}")
            }
            finally {
                isLoading = false
            }
        }
    }


}