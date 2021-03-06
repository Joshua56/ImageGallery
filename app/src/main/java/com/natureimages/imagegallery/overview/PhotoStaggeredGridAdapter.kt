package com.natureimages.imagegallery.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.natureimages.imagegallery.databinding.GridViewItemBinding
import com.natureimages.imagegallery.network.Hit

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class PhotoStaggeredGridAdapter(private val onClickListener: OnClickListener)
    : PagedListAdapter<Hit, PhotoStaggeredGridAdapter.HitViewHolder>(DiffCallback) {

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HitViewHolder {
        return HitViewHolder(GridViewItemBinding.inflate(LayoutInflater.from((parent.context))))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: HitViewHolder, position: Int) {
        val hit = getItem(position)
        holder.itemView.setOnClickListener {
            hit?.let {
                    hit -> onClickListener.onClick(hit)
            }
        }
        hit?.let {
            holder.bind(it)
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Hit]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Hit]
     */
    class OnClickListener(val clickListener: (hit:Hit) -> Unit) {
        fun onClick(hit: Hit) = clickListener(hit)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Hit]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Hit>() {

        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * The HitViewHolder constructor takes the binding variable from the associated GridViewItem,
     * which nicely gives it access to the full [Hit] information.
     */
    class HitViewHolder(private var binding: GridViewItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(hit: Hit) {
            binding.hit = hit
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }
}