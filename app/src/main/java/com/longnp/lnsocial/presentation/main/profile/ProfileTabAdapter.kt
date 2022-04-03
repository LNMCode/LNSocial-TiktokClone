package com.longnp.lnsocial.presentation.main.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.longnp.lnsocial.R
import com.longnp.lnsocial.databinding.LayoutItemTabProfileBinding

class ProfileTabAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProfileTabViewHolder(
            LayoutItemTabProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProfileTabViewHolder) {
            holder.bind(TabAdapter.values()[position])
        }
    }

    override fun getItemCount(): Int {
        return TabAdapter.values().size
    }

    class ProfileTabViewHolder(
        private val binding: LayoutItemTabProfileBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TabAdapter) {
            binding.haha.text = item.name
        }
    }
}

enum class TabAdapter(@DrawableRes val icon: Int, var listItem: List<String>) {
    PUBLIC(R.drawable.ic_tab_share, listOf()),
    FAVORITE(R.drawable.ic_tab_favorite, listOf()),
    PRIVATE(R.drawable.ic_tab_private, listOf()), ;

    fun setListItemTab(listItemValue: List<String>) {
        listItem = listItemValue
    }
}