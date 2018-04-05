package com.jamesvanhallen.hotnews.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jamesvanhallen.hotnews.App
import com.jamesvanhallen.hotnews.R
import com.jamesvanhallen.hotnews.data.model.Article
import com.jamesvanhallen.hotnews.utils.setImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_article.view.*
import java.util.*
import javax.inject.Inject

class ArticleAdapter(var listener: (Article) -> Unit) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    var list: List<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_article, parent, false)
        return ArticleAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) {
        val article = list[position]
        holder.bind(article, listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(articles: List<Article>) {
        this.list = articles
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @Inject
        lateinit var picasso: Picasso

        init {
            App.appComponent.inject(this)
        }

        fun bind(article: Article, listener: (Article) -> Unit) {
            itemView.title.text = article.title
            itemView.description.text = article.description
            itemView.date.text = article.publishedAt
            itemView.setOnClickListener { listener(article) }

            setImage(picasso, itemView.image, article.image)
        }
    }
}