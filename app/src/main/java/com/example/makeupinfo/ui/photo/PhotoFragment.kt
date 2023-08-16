package com.example.makeupinfo.ui.photo

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.makeupinfo.R
import com.example.makeupinfo.model.Photos.GalleryPhotoResponseItem
import com.google.android.material.bottomsheet.BottomSheetDialog

class PhotoFragment : Fragment() {

    companion object {
        fun newInstance() = PhotoFragment()
    }

    private lateinit var viewModel: PhotoViewModel
    private lateinit var likeButton: ImageButton
    private lateinit var downloadButton: ImageButton
    private lateinit var btnBookmark: ImageButton
    private lateinit var settingsButton: ImageButton
    private lateinit var ivSelectedPhoto: ImageView
    private lateinit var tvImageDescription: TextView
    private  var isLiked = false
    private var isBookmarked =  false
    private val REQUEST_WRITE_EXTERNAL_STORAGE = 123

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo, container, false)


    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhotoViewModel::class.java)

        ivSelectedPhoto = requireView().findViewById(R.id.ivSelectedPhoto)
        likeButton = requireView().findViewById(R.id.btnLike)
        downloadButton = requireView().findViewById(R.id.btnDownload)
        btnBookmark = requireView().findViewById(R.id.btnBookmark)
        settingsButton = requireView().findViewById(R.id.btnSetting)



        val photoItem = arguments?.getSerializable("photo") as? GalleryPhotoResponseItem
        if (photoItem != null) {
            Log.e("Photo Data " , photoItem.blur_hash)

            Glide.with(requireContext())
                .load(photoItem.urls.small)
                .into(ivSelectedPhoto)
        }


        likeButton.setOnClickListener {
            isLiked = !isLiked
            updateLikeButtonState()

        }

        downloadButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val imageUrl = photoItem?.urls?.small
                if (imageUrl != null) {
                    Log.d("DownloadButton", "Starting download of image: $imageUrl")

                    val fileName = "Image_${System.currentTimeMillis()}.jpg"
                    val request = DownloadManager.Request(Uri.parse(imageUrl))
                        .setTitle("Image Download")
                        .setDescription("Downloading image...")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setAllowedOverMetered(true)
                        .setAllowedOverRoaming(true)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, fileName)

                    val downloadManager =
                        requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    downloadManager.enqueue(request)
                }
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_EXTERNAL_STORAGE
                )
            }
        }


        btnBookmark.setOnClickListener {
          isBookmarked = !isBookmarked
            updateBookmarkedButton()
        }

        settingsButton.setOnClickListener{

            val popupMenu = PopupMenu(context, settingsButton)
            popupMenu.menuInflater.inflate(R.menu.gallery_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->

                when (menuItem.itemId) {
                    R.id.details -> {
                        val bottomSheet = BottomSheetDialog(requireContext(), R.style.MyTransparentBottomSheetDialogTheme)
                        val bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout, null)
                        bottomSheet.setContentView(bottomSheetView)
                        val tvImageDescription = bottomSheetView.findViewById<TextView>(R.id.tvImageDescription)
                        tvImageDescription.setText(photoItem!!.description)
                        bottomSheet.show()
                        true
                    }


                    else -> false
                }
            }
            popupMenu.show()
        }

    }

    private fun updateBookmarkedButton() {
        val bookmarked = if(isBookmarked)R.drawable.icon_bookmark_add else R.drawable.icon_bookmarkadded
        btnBookmark.setBackgroundResource(bookmarked)
    }

    private fun updateLikeButtonState() {
        val drawableRes = if (isLiked) R.drawable.icon_like else R.drawable.icon_favorite
        likeButton.setBackgroundResource(drawableRes)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadButton.performClick()
            } else {

            }
        }
    }

}