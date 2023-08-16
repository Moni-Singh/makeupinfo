package com.example.makeupinfo.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.makeupinfo.R
import com.example.makeupinfo.databinding.FragmentGalleryBinding
import com.example.makeupinfo.model.Photos.GalleryPhotoResponseItem


class GalleryFragment : Fragment() , GalleryPhotoAdapter.GalleryPhotoAdapterInterface{

    private var _binding: FragmentGalleryBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var galleryAdapter : GalleryPhotoAdapter
    private lateinit var galleryViewModel: GalleryViewModel
    private  var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val progressBar: ProgressBar = binding.progressBar
        var rvGallry : RecyclerView =  binding.rvGallery
        galleryAdapter = GalleryPhotoAdapter(this)
        rvGallry.adapter = galleryAdapter
        rvGallry.setLayoutManager(GridLayoutManager(context, 2))

        rvGallry.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!galleryViewModel.isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    currentPage++
//                    galleryViewModel.getGalleryDetailsList(currentPage)
                }
            }
        })

        galleryViewModel.galleryPhotos.observe(viewLifecycleOwner) { galleryPhotos ->
            galleryAdapter.updateGalleryPhotos(galleryPhotos)
            progressBar.visibility = View.GONE
        }
        galleryViewModel.getGalleryDetailsList(currentPage)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun photoDetails(item: GalleryPhotoResponseItem) {
        val bundle = Bundle()
        bundle.putSerializable("photo",item)
        findNavController().navigate(R.id.action_nav_gallery_to_photoFragment,bundle)

    }
}