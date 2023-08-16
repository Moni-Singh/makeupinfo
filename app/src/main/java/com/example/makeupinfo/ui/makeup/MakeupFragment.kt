package com.example.makeupinfo.ui.makeup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.makeupinfo.databinding.FragmentMakeupBinding

class MakeupFragment : Fragment() {

    private var _binding: FragmentMakeupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var makeupAdapter: MakeupProductAdapter
    private lateinit var viewModel: MakeupViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(MakeupViewModel::class.java)

        _binding = FragmentMakeupBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }


        val recyclerView: RecyclerView = binding.rvMakeupProduct
        recyclerView.layoutManager = LinearLayoutManager(context)

        makeupAdapter = MakeupProductAdapter(ArrayList())
        recyclerView.adapter = makeupAdapter


        viewModel = ViewModelProvider(this).get(MakeupViewModel::class.java)


        viewModel.text.observe(viewLifecycleOwner) { makeupItems ->
//            makeupAdapter.makeupItem = mak
            makeupAdapter.notifyDataSetChanged()
        }

        // Trigger API call
        viewModel.getMakeupProductDetials()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}