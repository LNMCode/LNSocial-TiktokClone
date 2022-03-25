package com.longnp.lnsocial.presentation.main.discovery.item

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.longnp.lnsocial.business.interactors.discovery.ItemThumbnailDiscovery

class DiscoveryListThumbnailAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: ItemThumbnailDiscovery)
    }
}