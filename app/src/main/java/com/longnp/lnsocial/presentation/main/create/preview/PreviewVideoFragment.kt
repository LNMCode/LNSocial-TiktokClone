package com.longnp.lnsocial.presentation.main.create.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.longnp.lnsocial.R
import com.longnp.lnsocial.databinding.FragmentPreviewVideoBinding
import com.longnp.lnsocial.presentation.exoplayer.Player
import com.longnp.lnsocial.presentation.main.create.BaseCreateFragment

class PreviewVideoFragment : BaseCreateFragment() {
    private var _binding: FragmentPreviewVideoBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<PreviewVideoFragmentArgs>()
    private val localVideo by lazy { args.localVideo }
    private val player by lazy {
        Player(
            simpleExoplayerView = binding.playerView,
            playBtn = binding.btnPauseVideo,
            context = requireContext(),
            url = localVideo.filePath,
            onVideoEnded = {
                it.restartPlayer()
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreviewVideoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(player)
        player.init()
        setUpClickListeners()
        initBackPop()
        baseCommunicationListener.hideNavigation(isHide = true)
    }

    private fun initBackPop() {
        binding.buttonBackPop.setOnClickListener {
            findNavController().popBackStack(R.id.selectMediaFragment, false)
        }
    }

    private fun setUpClickListeners() {
        binding.nextBtn.setOnClickListener {
            findNavController().navigate(
                PreviewVideoFragmentDirections.actionPreviewVideoFragmentToPostVideoFragment(
                    localVideo
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        player.resumePlayer()
    }

    override fun onPause() {
        super.onPause()
        player.pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stopPlayer()
    }
}