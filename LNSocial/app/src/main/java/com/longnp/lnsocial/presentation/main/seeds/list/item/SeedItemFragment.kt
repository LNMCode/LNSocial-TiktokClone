package com.longnp.lnsocial.presentation.main.seeds.list.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lnsocial.databinding.FragmentSeedItemBinding
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.presentation.main.seeds.BaseSeedFragment
import com.longnp.lnsocial.presentation.util.loadCenterCropImageFromUrl
import kotlinx.android.synthetic.main.layout_story_view.view.*

class SeedItemFragment : BaseSeedFragment() {
    private var storyUrl: String? = null
    private var storiesDataModel: VideoSeed? = null

    private var _binding: FragmentSeedItemBinding? = null
    private val binding get() = _binding!!

    private var simplePlayer: SimpleExoPlayer? = null
    private var cacheDataSourceFactory: CacheDataSource? = null

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

        //val simplePlayer = getPlayer()
        binding.root.player_view_story.player = simplePlayer

        storyUrl = storiesDataModel?.videoLink
        //storyUrl?.let { prepareMedia(it) }
    }

}