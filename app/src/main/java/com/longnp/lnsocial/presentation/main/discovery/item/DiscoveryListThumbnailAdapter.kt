package com.longnp.lnsocial.presentation.main.discovery.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.*
import com.bumptech.glide.request.RequestOptions
import com.example.lnsocial.R
import com.example.lnsocial.databinding.LayoutItemDiscoveryThumbnailBinding
import com.longnp.lnsocial.business.domain.models.ItemThumbnailDiscovery

class DiscoveryListThumbnailAdapter(
    private val interaction: Interaction,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val requestOptions = RequestOptions
        .placeholderOf(R.drawable.default_image)
        .error(R.drawable.default_image)

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemThumbnailDiscovery>() {
        override fun areItemsTheSame(oldItem: ItemThumbnailDiscovery, newItem: ItemThumbnailDiscovery): Boolean {
            return oldItem.videoId == newItem.videoId
        }

        override fun areContentsTheSame(oldItem: ItemThumbnailDiscovery, newItem: ItemThumbnailDiscovery): Boolean {
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
                holder.bind(differ.currentList[position])
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
        fun bind(item: ItemThumbnailDiscovery) {
            binding.root.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            Glide.with(binding.root)
                .setDefaultRequestOptions(requestOptions)
                .load(item.thumbnail)
                .transition(withCrossFade())
                .into(binding.imageThumbnail)
        }
    }

    fun submitList(blogList: List<ItemThumbnailDiscovery>?, ){
        val newList = blogList?.toMutableList()
        differ.submitList(newList)
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: ItemThumbnailDiscovery)
    }
}