package com.longnp.lnsocial.presentation.main.discovery

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.lnsocial.databinding.LayoutItemDiscoveryBinding
import com.longnp.lnsocial.business.domain.models.ItemDiscovery
import com.longnp.lnsocial.business.domain.models.ItemThumbnailDiscovery
import com.longnp.lnsocial.presentation.main.discovery.item.DiscoveryListThumbnailAdapter

class DiscoveryListAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemDiscovery>() {
        override fun areItemsTheSame(oldItem: ItemDiscovery, newItem: ItemDiscovery): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ItemDiscovery, newItem: ItemDiscovery): Boolean {
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
    ): RecyclerView.ViewHolder(binding.root),
    DiscoveryListThumbnailAdapter.Interaction{
        fun bind(item: ItemDiscovery) {
            val adapterThumbnail = DiscoveryListThumbnailAdapter(this@DiscoveryListViewHolder)
            binding.recyclerviewItemThumbnail.apply {
                layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = adapterThumbnail
            }

            binding.title.text = item.title
            binding.hashtagTitle.text = item.hashtagTitle
            adapterThumbnail.submitList(item.data)
        }

        override fun onItemSelected(position: Int, item: ItemThumbnailDiscovery) {
            Log.d("Hahahaha", "onItemSelected: " + position)
        }
    }

    fun submitList(blogList: List<ItemDiscovery>?, ){
        val newList = blogList?.toMutableList()
        differ.submitList(newList)
    }

}