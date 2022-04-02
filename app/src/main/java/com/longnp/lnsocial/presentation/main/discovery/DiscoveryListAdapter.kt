package com.longnp.lnsocial.presentation.main.discovery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.longnp.lnsocial.business.domain.models.discovery.DiscoveryModel
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.databinding.LayoutItemDiscoveryBinding
import com.longnp.lnsocial.presentation.main.discovery.item.DiscoveryListThumbnailAdapter

class DiscoveryListAdapter(
    private val interactionItemVideo: InteractionItemVideo
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DiscoveryModel>() {
        override fun areItemsTheSame(oldItem: DiscoveryModel, newItem: DiscoveryModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DiscoveryModel, newItem: DiscoveryModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ =
        AsyncListDiffer(
            DiscoveryRecyclerChangeCallBack(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DiscoveryListViewHolder(
            LayoutItemDiscoveryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interactionItemVideo = interactionItemVideo,
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DiscoveryListViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    internal inner class DiscoveryRecyclerChangeCallBack(
        private val adapter: DiscoveryListAdapter
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

    class DiscoveryListViewHolder
    constructor(
        private val binding: LayoutItemDiscoveryBinding,
        private val interactionItemVideo: InteractionItemVideo,
    ): RecyclerView.ViewHolder(binding.root),
    DiscoveryListThumbnailAdapter.Interaction{
        fun bind(item: DiscoveryModel) {
            val adapterThumbnail = DiscoveryListThumbnailAdapter(this@DiscoveryListViewHolder)
            binding.recyclerviewItemThumbnail.apply {
                layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = adapterThumbnail
            }

            binding.title.text = item.title
            binding.hashtagTitle.text = item.hashtagTitle
            adapterThumbnail.submitList(item.data.shuffled())
        }

        override fun onItemSelected(position: Int, item: List<VideoSeed>) {
            interactionItemVideo.onItemSelected(position, item)
        }
    }

    fun submitList(blogList: List<DiscoveryModel>?){
        val newList = blogList?.toMutableList()
        differ.submitList(newList)
    }

    interface InteractionItemVideo {
        fun onItemSelected(position: Int, item: List<VideoSeed>)
    }

}