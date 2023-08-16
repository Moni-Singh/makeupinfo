package com.example.makeupinfo.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.makeupinfo.R
import com.example.makeupinfo.model.Photos.GalleryPhotoResponseItem
import com.example.makeupinfo.model.Photos.Urls

class GalleryPhotoAdapter(
    var galleryPhotoAdapterInterface : GalleryPhotoAdapterInterface
):RecyclerView.Adapter<GalleryPhotoAdapter.GalleryListViewHolder>(){
    private var photoList = mutableListOf<GalleryPhotoResponseItem>()

    inner class GalleryListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var iv_galleryPhoto:ImageView = itemView.findViewById(R.id.iv_galleryPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery_photo,parent,false)
       return GalleryListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryListViewHolder, position: Int) {
        val photoItem = photoList[position]

        Glide.with(holder.itemView)
            .load(photoItem.urls.small)
            .into(holder.iv_galleryPhoto)

        holder.iv_galleryPhoto.setOnClickListener {
            if (photoItem != null) {
                galleryPhotoAdapterInterface.photoDetails(photoItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun updateGalleryPhotos(newPhotoList: List<GalleryPhotoResponseItem>) {
        photoList.addAll( newPhotoList)
        notifyDataSetChanged()
    }

    interface GalleryPhotoAdapterInterface{
        fun photoDetails(item :GalleryPhotoResponseItem)
    }
}
