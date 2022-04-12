package com.longnp.lnsocial.presentation.main.seeds.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.longnp.lnsocial.business.domain.models.Comment
import com.longnp.lnsocial.databinding.LayoutItemCommentBinding
import com.longnp.lnsocial.presentation.util.loadCenterCropImageFromUrl

class CommentAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }

    }

    internal
    class CommentAdapterChangeCallback(
        private val adapter: CommentAdapter
    ): ListUpdateCallback{
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

    private val differ = AsyncListDiffer(
        CommentAdapterChangeCallback(this),
        AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommentAdapterViewHolder(
            binding = LayoutItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CommentAdapterViewHolder) {
            holder.bind(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class CommentAdapterViewHolder(
        val binding: LayoutItemCommentBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Comment) {
            binding.titleName.text = item.nameUser
            binding.comment.text = item.comment
            binding.imageViewProfilePic.loadCenterCropImageFromUrl(item.avatarLink)
        }
    }

    fun submitList(list: List<Comment>) {
        val newList = list.toMutableList()
        newList.sortBy { it.order }
        differ.submitList(newList)
    }
}