package com.longnp.lnsocial.presentation.main.create.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.longnp.lnsocial.business.datasource.local.media.LocalVideo
import com.longnp.lnsocial.databinding.LayoutItemCameraFiltersBinding
import com.longnp.lnsocial.presentation.util.loadImageFromResource

class CreateFilterCameraAdapter(
    private val interactionFilter: InteractionFilter,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CreateFilterCameraViewHolder(
            LayoutItemCameraFiltersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interactionFilter = interactionFilter,
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CreateFilterCameraViewHolder) {
            holder.bind(CreateFilterCamera.values()[position])
        }
    }

    override fun getItemCount(): Int {
        return CreateFilterCamera.values().size
    }

    class CreateFilterCameraViewHolder(
        private val binding: LayoutItemCameraFiltersBinding,
        private val interactionFilter: InteractionFilter,
    ) : RecyclerView.ViewHolder(binding.root), InteractionFilter {
        fun bind(item: CreateFilterCamera) {
            binding.root.setOnClickListener {
                interactionFilter.onItemSelected(item)
            }

            binding.titleFilter.text = item.title
            binding.imageFilter.loadImageFromResource(item.res)
        }

        override fun onItemSelected(item: CreateFilterCamera) {
            interactionFilter.onItemSelected(item)
        }
    }

    interface InteractionFilter {
        fun onItemSelected(item: CreateFilterCamera)
    }
}