package com.longnp.lnsocial.presentation.main.inbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.longnp.lnsocial.business.domain.models.inbox.InboxModel
import com.longnp.lnsocial.databinding.LayoutItemInboxBinding
import com.longnp.lnsocial.presentation.util.loadCenterCropImageFromUrl

class InboxListAdapter(
    private val interactionInboxList: InteractionInboxList,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<InboxModel>() {
        override fun areItemsTheSame(oldItem: InboxModel, newItem: InboxModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: InboxModel, newItem: InboxModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ =
        AsyncListDiffer(
            IndexRecyclerChangeCallBack(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    internal inner class IndexRecyclerChangeCallBack(
        private val adapter: InboxListAdapter,
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
        return InboxListViewHolder(
            LayoutItemInboxBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interactionInboxList = interactionInboxList,
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is InboxListViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class InboxListViewHolder
    constructor(
        private val binding: LayoutItemInboxBinding,
        private val interactionInboxList: InteractionInboxList,
    ) : RecyclerView.ViewHolder(binding.root), InteractionInboxList {
        fun bind(item: InboxModel) {
            binding.root.setOnClickListener {
                interactionInboxList.onItemSelected(position = adapterPosition, item)
            }
            binding.titleName.text = item.nameReceiver
            binding.titleLastMessage.text = item.idReceiver
            binding.imageViewProfilePic.loadCenterCropImageFromUrl(item.avaReceiver)
        }

        override fun onItemSelected(position: Int, item: InboxModel) {
            interactionInboxList.onItemSelected(position, item)
        }
    }

    fun submitList(blogList: List<InboxModel>?){
        val newList = blogList?.toMutableList()
        differ.submitList(newList)
    }

    interface InteractionInboxList {
        fun onItemSelected(position: Int, item: InboxModel)
    }
}