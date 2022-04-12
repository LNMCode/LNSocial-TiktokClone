package com.longnp.lnsocial.presentation.main.seeds.comment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.longnp.lnsocial.R
import com.longnp.lnsocial.databinding.FragmentCommentBinding
import com.longnp.lnsocial.presentation.BaseCommunicationListener
import com.longnp.lnsocial.presentation.main.seeds.BaseSeedFragment

//Special fragment
class CommentFragment : BaseSeedFragment() {
    //val TAG: String = "AppDebug"

    private var _biding: FragmentCommentBinding? = null
    private val binding get() = _biding!!

    //lateinit var baseCommunicationListener: BaseCommunicationListener

    private val viewModel: CommentViewModel by viewModels()

    private lateinit var commentAdapter: CommentAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            baseCommunicationListener = context as BaseCommunicationListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement UICommunicationListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _biding = FragmentCommentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseCommunicationListener.changeColorNavigation(isHomePage = true)
        val videoId = arguments?.getString("Constants.KEY_VIDEO_ID") ?: ""
        viewModel.setVideoId(videoId)
        viewModel.onTriggerEvents(CommentEvents.GetComment)
        hideBottomNavigation()
        initEventBackPop()
        initRecyclerView()
        initEventAddComment()
        subcrialeObserver()
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            commentAdapter = CommentAdapter()
            adapter = commentAdapter
        }
    }

    private fun initEventAddComment() {
        binding.btnSend.setOnClickListener {
            cacheState()
            viewModel.onTriggerEvents(CommentEvents.AddComment)
        }
    }

    private fun initEventBackPop() {
        binding.btnClose.setOnClickListener {
            findNavController().popBackStack(R.id.seedFragment, false)
            baseCommunicationListener.hideSoftKeyboard()
            baseCommunicationListener.hideNavigation(isHide = false)
        }
    }

    private fun hideBottomNavigation() {
        baseCommunicationListener.hideNavigation(isHide = true)
    }

    private fun subcrialeObserver() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            commentAdapter.submitList(state.comments)
            binding.numberCommentsTitle.text = "${state.comments.size} bình luận"
        }
    }

    private fun cacheState() {
        val comment = binding.editComment.text.toString()
        viewModel.onTriggerEvents(CommentEvents.OnChangeComment(comment))
        binding.editComment.setText("")
    }

}