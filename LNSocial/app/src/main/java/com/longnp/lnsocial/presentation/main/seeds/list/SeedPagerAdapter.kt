package com.longnp.lnsocial.presentation.main.seeds.list

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.presentation.main.seeds.list.item.SeedItemFragment

class SeedPagerAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment)  {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VideoSeed>() {

        override fun areItemsTheSame(oldItem: VideoSeed, newItem: VideoSeed): Boolean {
            return oldItem.pk == newItem.pk
        }

        override fun areContentsTheSame(oldItem: VideoSeed, newItem: VideoSeed): Boolean {
            return oldItem == newItem
        }

    }
    private val differ =
        AsyncListDiffer(
            VideoSeedChangeCallback(this),
            AsyncDifferConfig.Builder(DIFF_CALLBACK).build()
        )

    internal inner class VideoSeedChangeCallback(
        private val adapter: SeedPagerAdapter
    ) : ListUpdateCallback {

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(position, count, payload)
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeChanged(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyDataSetChanged()
        }

        override fun onRemoved(position: Int, count: Int) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return SeedItemFragment.newInstance(differ.currentList[position])
    }

    fun submitList(blogList: List<VideoSeed>?, ){
        val newList = blogList?.toMutableList()
        differ.submitList(newList)
    }

}