package com.dhandev.storyapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dhandev.storyapp.databinding.StoryItemBinding
import com.dhandev.storyapp.model.getAllStory

class storyItemAdapter(private val listStoryItem: ArrayList<getAllStory.ListStoryItem>) : RecyclerView.Adapter<storyItemAdapter.StoryViewHolder>(){

    private var onItemClickCallback : OnItemClickCallback? = null

    fun getListStory(responseItem: List<getAllStory.ListStoryItem>){
        listStoryItem.clear()
        listStoryItem.addAll(responseItem)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class StoryViewHolder(val binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var context = itemView.context
        fun bind(item : getAllStory.ListStoryItem){
            binding.apply {
                root.setOnClickListener{
                    onItemClickCallback?.onItemClicked(item)
                }
                Glide.with(itemView)
                    .load(item.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerInside()
                    .into(imgItemPhoto)
                tvItemUsername.text = item.name
                tvItemDate.text = item.createdAt
                tvItemDesc.text = item.description
            }
        }

    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): StoryViewHolder {
        val view = StoryItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return StoryViewHolder((view))    }

    override fun onBindViewHolder(viewHolder: storyItemAdapter.StoryViewHolder, position: Int) {
        viewHolder.bind(listStoryItem[position])
    }

    override fun getItemCount() = listStoryItem.size

    interface OnItemClickCallback{
        fun onItemClicked(data: getAllStory.ListStoryItem)
    }
}