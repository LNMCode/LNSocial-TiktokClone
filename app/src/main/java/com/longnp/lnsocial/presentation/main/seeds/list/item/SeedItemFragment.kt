package com.longnp.lnsocial.presentation.main.seeds.list.item

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.util.Util
import com.longnp.lnsocial.R
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.databinding.FragmentSeedItemBinding
import com.longnp.lnsocial.presentation.main.seeds.BaseSeedFragment
import com.longnp.lnsocial.presentation.main.seeds.list.SeedBtnPauseVideo
import com.longnp.lnsocial.presentation.util.loadCenterCropImageFromUrl
import com.longnp.lnsocial.presentation.util.loadImageFromResource
import kotlinx.android.synthetic.main.layout_story_view.view.*

class SeedItemFragment : BaseSeedFragment() {

    private var storyUrl: String? = null
    private var storiesDataModel: VideoSeed? = null

    private var _binding: FragmentSeedItemBinding? = null
    private val binding get() = _binding!!

    private var simplePlayer: ExoPlayer? = null
    private var cacheDataSourceFactory: DataSource.Factory? = null

    private lateinit var btnPauseVideo: SeedBtnPauseVideo
    private var isPauseVideo = false

    private val viewModel: SeedItemViewModel by viewModels()

    companion object {
        // TODO: Nghien cuu va fix lai cho nay
        fun newInstance(videoSeed: VideoSeed) = SeedItemFragment()
            .apply {
                arguments = Bundle().apply {
                    putParcelable("Constants.KEY_STORY_DATA", videoSeed)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeedItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subcribeObserver()
        storiesDataModel = arguments?.getParcelable("Constants.KEY_STORY_DATA")
        initVideoSeed(storiesDataModel)
        setData()
        initEvents()
    }

    private fun setData() {
        binding.root.text_view_account_handle.text = storiesDataModel?.nickName
        binding.root.text_view_video_description.text = storiesDataModel?.description
        binding.root.text_view_music_title.text = storiesDataModel?.soundTitle

        binding.root.image_view_option_like_title.text = storiesDataModel?.numberLike.toString()
        binding.root.image_view_option_comment_title.text =
            storiesDataModel?.numberComments.toString()

        binding.root.image_view_profile_pic?.loadCenterCropImageFromUrl(storiesDataModel?.avatarLink)

        storyUrl = storiesDataModel?.videoLink
        binding.root.text_view_music_title.isSelected = true

        binding.root.player_view_story.player = initSimplePlayer()
        setMaxVideoSize()
        btnPauseVideo = binding.root.btn_pause_video
        initEventPlayerView()
        storyUrl?.let { prepareMedia(it) }
        initNumberLike()
    }

    private fun setMaxVideoSize() {
        binding.root.player_view_story.player?.trackSelectionParameters?.buildUpon()
            ?.setMaxVideoSize(1024, 768)?.build()
    }

    private fun initEvents() {
        binding.root.image_view_follow_option.setOnClickListener {
            viewModel.onTriggerEvents(SeedItemEvents.Follow)
        }
        binding.root.image_view_option_like.setOnClickListener {
            if (viewModel.state.value?.isLoading == false) {
                viewModel.onTriggerEvents(SeedItemEvents.LikeVideo)
            }
        }
        binding.root.image_view_option_comment.setOnClickListener {
            Log.d(TAG, "initEvents: Comments show")
            val bundle = bundleOf("Constants.KEY_VIDEO_ID" to viewModel.state.value?.videoSeed?.pk)
            findNavController().navigate(R.id.action_seedFragment_to_commentFragment, bundle)
        }
    }

    private fun subcribeObserver() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            // comments
            baseCommunicationListener.displayProgressBar(state.isLoading)

            binding.root.image_view_option_like_title.text = state.numberLike.toString()

            binding.root.image_view_option_comment_title.text = state.numberComments.toString()

            if (!state.isLike) {
                binding.root.image_view_option_like.loadImageFromResource(R.drawable.ic_heart_icon)
            } else {
                binding.root.image_view_option_like.loadImageFromResource(R.drawable.ic_heart_icon_pink)
            }
            if (state.isFollow || viewModel.checkIsAuthVideo()) {
                binding.root.image_view_follow_option.isVisible = false
            }
        }
    }

    private fun initVideoSeed(videoSeed: VideoSeed?) {
        viewModel.onTriggerEvents(SeedItemEvents.InitSeedVideo(videoSeed))
    }

    private fun initNumberLike() {
        viewModel.state.value?.let { state ->
            val videoSeed = state.videoSeed ?: return
            viewModel.onTriggerEvents(SeedItemEvents.OnChangeNumberLike(videoSeed.numberLike))
            viewModel.onTriggerEvents(SeedItemEvents.OnChangeNumberComment(videoSeed.numberComments))
        }
        viewModel.onTriggerEvents(SeedItemEvents.OnChangeIsLike)
        viewModel.onTriggerEvents(SeedItemEvents.CheckFollowing)
    }

    private fun initSimplePlayer(): ExoPlayer? {
        if (simplePlayer == null) {
            prepareVideoPlayer()
        }
        return simplePlayer
    }

    private fun initEventPlayerView() {
        binding.root.options_container.setOnClickListener {
            if (simplePlayer?.playWhenReady == true) {
                isPauseVideo = true
                pauseVideo()
            } else {
                isPauseVideo = false
                continueVideo()
            }
        }
    }

    private fun prepareVideoPlayer() {
        simplePlayer = context?.let { ExoPlayer.Builder(it).build() }
        cacheDataSourceFactory =
            CacheDataSource.Factory().setCache(simpleCache).setUpstreamDataSourceFactory(
                DefaultHttpDataSource.Factory().setUserAgent(
                    Util.getUserAgent(context!!, "SeedExoPlayer")
                )
            )
    }


    private fun prepareMedia(url: String) {
        val uri = Uri.parse(url)
        val mediaSource =
            ProgressiveMediaSource.Factory(cacheDataSourceFactory!!)
                .createMediaSource(MediaItem.fromUri(uri))
        simplePlayer?.repeatMode = Player.REPEAT_MODE_ONE
        simplePlayer?.setMediaSource(mediaSource, true)
        simplePlayer?.prepare()
        simplePlayer?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == Player.STATE_READY) {
                    binding.root.view_black.isVisible = false
                }
                if (playbackState == Player.STATE_ENDED) {
                    restartVideo()
                }
            }

            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                super.onPlayWhenReadyChanged(playWhenReady, reason)
                val timeCurrent = simplePlayer?.currentPosition
                if (timeCurrent != null) {
                    btnPauseVideo.actionPauseVideo(playWhenReady, timeCurrent, isPauseVideo)
                }
            }
        })
        simplePlayer?.playWhenReady = true
    }

    private fun restartVideo() {
        isPauseVideo = false
        if (simplePlayer == null) {
            storyUrl?.let { prepareMedia(it) }
        } else {
            simplePlayer?.seekToDefaultPosition()
            simplePlayer?.playWhenReady = true
        }
    }

    private fun pauseVideo() {
        simplePlayer?.playWhenReady = false
    }

    private fun continueVideo() {
        simplePlayer?.playWhenReady = true
    }

    private fun releasePlayer() {
        simplePlayer?.release()
    }

    override fun onPause() {
        pauseVideo()
        super.onPause()
    }

    override fun onResume() {
        restartVideo()
        super.onResume()
    }

    override fun onDestroy() {
        releasePlayer()
        super.onDestroy()
    }
}