package com.longnp.lnsocial.presentation.main.inbox.message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.longnp.lnsocial.business.domain.models.inbox.Message
import com.longnp.lnsocial.databinding.LayoutItemChatMeBinding
import com.longnp.lnsocial.databinding.LayoutItemChatOtherBinding
import com.longnp.lnsocial.presentation.util.TypeMessage.Companion.VIEW_TYPE_MESSAGE_RECEIVED
import com.longnp.lnsocial.presentation.util.TypeMessage.Companion.VIEW_TYPE_MESSAGE_SENT
import com.longnp.lnsocial.presentation.util.loadCenterCropImageFromUrl

class InboxMessageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }

    internal inner class InboxMessageChangeCallBack(
        private val adapter: InboxMessageAdapter
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

    private val differ =
        AsyncListDiffer(
            InboxMessageChangeCallBack(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    override fun getItemViewType(position: Int): Int {
        val message = differ.currentList[position]
        return message.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            return SendMessageViewHolder(
                LayoutItemChatMeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
        return ReceivedMessageViewHolder(
            LayoutItemChatOtherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = differ.currentList[position]
        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> {
                (holder as SendMessageViewHolder).bind(message)
            }
            VIEW_TYPE_MESSAGE_RECEIVED -> {
                (holder as ReceivedMessageViewHolder).bind(message)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    fun submitList(messages: List<Message>?) {
        val newList = messages?.toMutableList()
        differ.submitList(newList)
    }

    class SendMessageViewHolder
    constructor(
        private val binding: LayoutItemChatMeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.textGchatMessageMe.text = item.value
        }
    }

    class ReceivedMessageViewHolder
    constructor(
        private val binding: LayoutItemChatOtherBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.textGchatMessageOther.text = item.value
            if (item.type == VIEW_TYPE_MESSAGE_RECEIVED)
                binding.imageGchatProfileOther.loadCenterCropImageFromUrl(item.ava)
        }
    }
}