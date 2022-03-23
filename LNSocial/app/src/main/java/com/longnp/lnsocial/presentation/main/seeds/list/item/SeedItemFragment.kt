package com.longnp.lnsocial.presentation.main.seeds.list.item

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.isVisible
import com.example.lnsocial.R
import com.example.lnsocial.databinding.FragmentSeedItemBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.util.Util
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.presentation.main.seeds.BaseSeedFragment
import com.longnp.lnsocial.presentation.util.loadCenterCropImageFromUrl
import kotlinx.android.synthetic.main.layout_story_view.view.*

class SeedItemFragment : BaseSeedFragment() {

    private var storyUrl: String? = null
    private var storiesDataModel: VideoSeed? = null

    private var _binding: FragmentSeedItemBinding? = null
    private val binding get() = _binding!!

    private var simplePlayer: ExoPlayer? = null
    private var cacheDataSourceFactory: DataSource.Factory? = null

    private lateinit var btnPauseVideo: ImageView

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

        storiesDataModel = arguments?.getParcelable("Constants.KEY_STORY_DATA")
        setData()
    }

    private fun setData() {
        binding.root.text_view_account_handle.text = storiesDataModel?.nickName
        binding.root.text_view_video_description.text = storiesDataModel?.description
        binding.root.text_view_music_title.text = storiesDataModel?.soundTitle

        binding.root.image_view_option_comment_title?.text = storiesDataModel?.numberComments
        binding.root.image_view_option_like_title?.text = storiesDataModel?.numberLike

        binding.root.image_view_profile_pic?.loadCenterCropImageFromUrl(storiesDataModel?.avatarLink)
        binding.root.text_view_music_title.isSelected = true

        binding.root.player_view_story.player = initSimplePlayer()
        initEventPlayerView()

        btnPauseVideo = binding.root.btn_pause_video

        storyUrl = storiesDataModel?.videoLink
        storyUrl?.let { prepareMedia(it) }
    }

    private fun initSimplePlayer(): ExoPlayer? {
        if (simplePlayer == null) {
            prepareVideoPlayer()
        }
        return simplePlayer
    }

    private fun initEventPlayerView(){
        binding.root.options_container.setOnClickListener {
            if (simplePlayer?.playWhenReady == true) {
                btnPauseVideo.isVisible = true
                pauseVideo()
            } else {
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
        simplePlayer?.addListener(object: Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == Player.STATE_ENDED) {
                    restartVideo()
                }
            }
        })
        simplePlayer?.playWhenReady = true
    }

    private fun restartVideo() {
        if (simplePlayer == null) {
            storyUrl?.let { prepareMedia(it) }
        } else {
            btnPauseVideo.isVisible = false
            simplePlayer?.seekToDefaultPosition()
            simplePlayer?.playWhenReady = true
        }
    }

    private fun pauseVideo() {
        simplePlayer?.playWhenReady = false
    }

    private fun continueVideo() {
        btnPauseVideo.isVisible = false
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