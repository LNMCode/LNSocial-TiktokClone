package com.longnp.lnsocial.presentation.main.inbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.longnp.lnsocial.business.domain.models.inbox.InboxModel
import com.longnp.lnsocial.databinding.LayoutItemInboxHorizoltalBinding
import com.longnp.lnsocial.presentation.util.loadCenterCropImageFromUrl

class InboxListHorizolAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<InboxModel>() {
        override fun areItemsTheSame(oldItem: InboxModel, newItem: InboxModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: InboxModel, newItem: InboxModel): Boolean {
            return oldItem == newItem
        }
    }

    internal inner class IndexRecyclerChangeCallBack(
        private val adapter: InboxListHorizolAdapter,
    ): ListUpdateCallback {
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

    private val differ =
        AsyncListDiffer(
            IndexRecyclerChangeCallBack(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return InboxListHorizolViewHolder(
            LayoutItemInboxHorizoltalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is InboxListHorizolViewHolder) {
            holder.bind(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(blogList: List<InboxModel>?){
        val newList = blogList?.toMutableList()
        differ.submitList(newList)
    }

    class InboxListHorizolViewHolder(
        private val binding: LayoutItemInboxHorizoltalBinding,
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: InboxModel) {
            binding.imageViewProfilePic.loadCenterCropImageFromUrl(item.avaReceiver)
        }
    }
}