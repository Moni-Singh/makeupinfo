package com.example.makeupinfo.model.Photos

import java.io.Serializable

data class SearchPhotoResponse(
    var total:Number,
    var total_pages:Number,
    var results : ArrayList<GalleryPhotoResponseItem>
): Serializable