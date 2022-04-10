package com.longnp.lnsocial.presentation.main.discovery.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.*
import com.bumptech.glide.request.RequestOptions
import com.longnp.lnsocial.R
import com.longnp.lnsocial.business.domain.models.discovery.DiscoveryModel
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.databinding.LayoutItemDiscoveryThumbnailBinding
import com.longnp.lnsocial.presentation.util.loadCenterCropImageFromUrl

class DiscoveryListThumbnailAdapter(
    private val interaction: Interaction,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val requestOptions = RequestOptions
        .placeholderOf(R.drawable.default_image)
        .error(R.drawable.default_image)

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VideoSeed>() {
        override fun areItemsTheSame(oldItem: VideoSeed, newItem: VideoSeed): Boolean {
            return oldItem.pk == newItem.pk
        }

        override fun areContentsTheSame(oldItem: VideoSeed, newItem: VideoSeed): Boolean {
            return oldItem == newItem
        }

    }

    private val differ =
        AsyncListDiffer(
            DiscoveryListThumbnailChangeCallBack(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DiscoveryListThumbnailViewHolder(
            LayoutItemDiscoveryThumbnailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            requestOptions = requestOptions,
            interaction = interaction,
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DiscoveryListThumbnailViewHolder -> {
                holder.bind(differ.currentList)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    internal inner class DiscoveryListThumbnailChangeCallBack(
        private val adapter: DiscoveryListThumbnailAdapter
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

    class DiscoveryListThumbnailViewHolder
    constructor(
        private val binding: LayoutItemDiscoveryThumbnailBinding,
        private val requestOptions: RequestOptions,
        private val interaction: Interaction?,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: List<VideoSeed>) {
            binding.root.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            binding.imageThumbnail
                .loadCenterCropImageFromUrl(item[adapterPosition].thumbnail)
        }
    }

    fun submitList(blogList: List<VideoSeed>?) {
        val newList = blogList?.toMutableList()
        differ.submitList(newList)
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: List<VideoSeed>)
    }
}