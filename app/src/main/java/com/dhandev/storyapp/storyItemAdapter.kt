package com.dhandev.storyapp

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dhandev.storyapp.databinding.StoryItemBinding
import com.dhandev.storyapp.model.getAllStory
import com.dhandev.storyapp.view.detail.DetailActivity

class storyItemAdapter(private val listStoryItem: ArrayList<getAllStory.ListStoryItem>) : RecyclerView.Adapter<storyItemAdapter.StoryViewHolder>(){

    private var onItemClickCallback : OnItemClickCallback? = null

    fun getListStory(responseItem: List<getAllStory.ListStoryItem>){
        listStoryItem.clear()
        listStoryItem.addAll(responseItem)
        notifyDataSetChanged()
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
                    .centerCrop()
                    .into(imgItemPhoto)
                tvItemUsername.text = item.name
                tvItemDate.text = item.createdAt.withDateFormat()
                tvItemDesc.text = item.description
                itemView.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("Story", item)
                    val optionsCompat : ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(imgItemPhoto, "gambar"),
                            Pair(tvItemUsername, "username"),
                            Pair(tvItemDesc, "desc"),
                            Pair(tvItemDate, "date")
                        )
                    context.startActivity(intent, optionsCompat.toBundle())
                }
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