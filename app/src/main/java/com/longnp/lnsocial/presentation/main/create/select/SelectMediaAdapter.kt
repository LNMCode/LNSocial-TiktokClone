package com.longnp.lnsocial.presentation.main.create.select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.longnp.lnsocial.business.datasource.local.media.LocalVideo
import com.longnp.lnsocial.databinding.LayoutItemLocalVideoBinding
import com.longnp.lnsocial.presentation.util.TimeUtils
import com.longnp.lnsocial.presentation.util.loadCenterCropImageFromUrl
import com.longnp.lnsocial.presentation.util.loadThumbnailImageFromVideoPath

class SelectMediaAdapter(
    private val interactionVideo: InteractionVideo,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LocalVideo>() {
        override fun areItemsTheSame(oldItem: LocalVideo, newItem: LocalVideo): Boolean {
            return oldItem.filePath == newItem.filePath
        }

        override fun areContentsTheSame(oldItem: LocalVideo, newItem: LocalVideo): Boolean {
            return oldItem == newItem
        }
    }

    internal inner class SelectMediaAdapterCallBack(
        val adapter: SelectMediaAdapter
    ) : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemChanged(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }
    }

    val differ =
        AsyncListDiffer(
            SelectMediaAdapterCallBack(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SelectMediaViewHolder(
            LayoutItemLocalVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interactionVideo = interactionVideo,
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SelectMediaViewHolder) {
            holder.bind(position, differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class SelectMediaViewHolder
    constructor(
        private val binding: LayoutItemLocalVideoBinding,
        private val interactionVideo: InteractionVideo,
    ) : RecyclerView.ViewHolder(binding.root), InteractionVideo {
        fun bind(position: Int, localVideo: LocalVideo) {
            binding.localVideoThumbnail.loadThumbnailImageFromVideoPath(
                binding.root.context,
                localVideo.filePath
            )
            binding.localVideoDuration.text =
                TimeUtils.convertTimeToDisplayTime(localVideo.duration ?: return)
            binding.root.setOnClickListener {
                interactionVideo.onItemSelected(position, localVideo)
            }
        }

        override fun onItemSelected(position: Int, item: LocalVideo) {
            interactionVideo.onItemSelected(position, item)
        }
    }

    interface InteractionVideo {
        fun onItemSelected(position: Int, item: LocalVideo)
    }

    fun submitList(videos: List<LocalVideo>) {
        val videosMut = videos.toMutableList()
        differ.submitList(videosMut)
    }
}