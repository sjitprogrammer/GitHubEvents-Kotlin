package com.aodev.githubevents.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aodev.githubevents.R
import com.aodev.githubevents.listener.OnItemClickListener
import com.aodev.githubevents.network.data.EventsItem
import com.bumptech.glide.Glide

class EventsItemAdapter(
    val items: List<EventsItem>,
    val context: Context,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<EventsItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size ?: 0


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.transitionName = "image_${item.id}"
        val imageUrl: String = item.actor.avatarUrl
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_github)
            .into(holder.imageView);
        holder.text_title.text = item.actor.login

        holder.itemView.setOnClickListener {
            onItemClickListener.onClickedItem(it, item.id, position)
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.icon_image)
        val text_title: TextView = view.findViewById(R.id.textView_title)


    }
}