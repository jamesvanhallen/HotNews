package com.jamesvanhallen.hotnews.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jamesvanhallen.hotnews.R
import com.jamesvanhallen.hotnews.data.model.Source
import kotlinx.android.synthetic.main.item_source.view.*
import java.util.ArrayList

class SourceAdapter(var listener: (Source) -> Unit) : RecyclerView.Adapter<SourceAdapter.ViewHolder>() {

    var list: List<Source> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_source, parent, false)
        return SourceAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: SourceAdapter.ViewHolder, position: Int) {
        val news = list[position]
        holder.bind(news, listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(sources: List<Source>) {
        this.list = sources
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(source: Source, listener: (Source) -> Unit) {
            itemView.name.text = source.name
            itemView.description.text = source.description
            itemView.setOnClickListener { listener(source)}

        }
    }
}