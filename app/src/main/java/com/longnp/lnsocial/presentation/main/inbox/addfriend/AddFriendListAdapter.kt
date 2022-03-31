package com.longnp.lnsocial.presentation.main.inbox.addfriend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.longnp.lnsocial.business.domain.models.inbox.Friend
import com.longnp.lnsocial.business.domain.models.inbox.InboxModel
import com.longnp.lnsocial.databinding.LayoutItemAddFriendBinding
import com.longnp.lnsocial.presentation.util.loadImageFromUrl

class AddFriendListAdapter(
    private val interactionAddFriend: InteractionAddFriend
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Friend>() {
        override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean {
            return oldItem == newItem
        }
    }

    private val differ =
        AsyncListDiffer(
            AddFriendChangeCallBack(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    internal inner class AddFriendChangeCallBack(
        private val adapter: AddFriendListAdapter
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
        return AddFriendViewHolder(
            LayoutItemAddFriendBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction = interactionAddFriend,
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AddFriendViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class AddFriendViewHolder(
        private val binding: LayoutItemAddFriendBinding,
        private val interaction: InteractionAddFriend
    ) : RecyclerView.ViewHolder(binding.root), InteractionAddFriend {
        fun bind(item: Friend) {
            binding.btnSendMessage.setOnClickListener {
                interaction.onItemSelected(position = adapterPosition, item = item)
            }

            binding.imageViewProfilePic.loadImageFromUrl(item.avatar)
            binding.titleName.text = item.username
        }

        override fun onItemSelected(position: Int, item: Friend) {
            interaction.onItemSelected(position, item)
        }
    }

    fun submitList(blogList: List<Friend>?) {
        val newList = blogList?.toMutableList()
        differ.submitList(newList)
    }

    interface InteractionAddFriend {
        fun onItemSelected(position: Int, item: Friend)
    }
}