package com.longnp.lnsocial.presentation.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.longnp.lnsocial.R
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.databinding.LayoutItemRecyclerviewTabProfileBinding

class ProfileAdapterTabImage : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            IndexRecyclerChangeCallBack(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    internal inner class IndexRecyclerChangeCallBack(
        private val adapter: ProfileAdapterTabImage,
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProfileAdapterTabImageViewHolder(
            LayoutItemRecyclerviewTabProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProfileAdapterTabImageViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ProfileAdapterTabImageViewHolder
    constructor(
        private val binding: LayoutItemRecyclerviewTabProfileBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoSeed) {
            Glide.with(binding.root.context)
                .load(item.videoLink)
                .apply(RequestOptions().placeholder(R.drawable.ic_loading_place))
                .into(binding.image)
        }
    }

    fun submitList(blogList: List<VideoSeed>?) {
        val newList = blogList?.toMutableList()
        differ.submitList(newList)
    }
}