package com.longnp.lnsocial.presentation.main.create.select

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.longnp.lnsocial.R
import com.longnp.lnsocial.business.datasource.local.media.LocalVideo
import com.longnp.lnsocial.databinding.FragmentSelectMediaBinding
import com.longnp.lnsocial.presentation.main.create.BaseCreateFragment

class SelectMediaFragment : BaseCreateFragment(), SelectMediaAdapter.InteractionVideo {

    private var _binding: FragmentSelectMediaBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SelectMediaViewModel by viewModels()

    private lateinit var selectMediaAdapter: SelectMediaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectMediaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onTriggerEvents(SelectMediaEvents.GetAllImageAndVideo(requireContext()))
        initRecyclerView()
        subscribeObservers()
    }

    private fun initRecyclerView() {
        selectMediaAdapter = SelectMediaAdapter(this)
        binding.recyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = selectMediaAdapter
        }
    }

    private fun subscribeObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.mediaLocalInteraction?.let { mediaLocalInteraction ->
                mediaLocalInteraction.listOfLocalVideo.observe(viewLifecycleOwner) { listVideos ->
                    selectMediaAdapter.submitList(listVideos)
                }
            }
        }
    }

    override fun onItemSelected(position: Int, item: LocalVideo) {
        Log.d("TAG", "onItemSelected: Choose item upload ${item.filePath}")
        findNavController().navigate(
            SelectMediaFragmentDirections.actionSelectMediaFragmentToPreviewVideoFragment(item)
        )
    }

}