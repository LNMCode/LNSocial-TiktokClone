package com.longnp.lnsocial.presentation.main.create.select

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
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
        initBtnBack()
        subscribeObservers()
        baseCommunicationListener.hideNavigation(isHide = true)
    }

    private fun initRecyclerView() {
        selectMediaAdapter = SelectMediaAdapter(this)
        binding.recyclerview.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = selectMediaAdapter
        }
    }

    private fun initBtnBack() {
        binding.closeBtn.setOnClickListener {
            findNavController().popBackStack(R.id.createFragment, false)
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