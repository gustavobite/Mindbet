package br.com.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.mindbet.common.component.recycler_view.BaseRecyclerViewAdapter
import br.com.mindbet.common.extension.showImage
import br.com.news.R
import br.com.news.model.News
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.imageview.ShapeableImageView


class NewsAdapter(val listener: ((News,ShapeableImageView, TextView) -> Unit)? = null) :
    RecyclerView.Adapter<NewsAdapter.NewsHolder>() {


    var items : List<News> = emptyList()
        set (value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_news, parent, false)
        return NewsHolder(view,listener)
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }


    class NewsHolder(itemView: View, val listener: ((News,ShapeableImageView,TextView) -> Unit)? = null) :
        RecyclerView.ViewHolder(itemView) {

        private val newsImage = itemView.findViewById<ShapeableImageView>(R.id.newsImage)
        private val newsTitle = itemView.findViewById<TextView>(R.id.newsTitle)
        private val newsTag = itemView.findViewById<TextView>(R.id.newsTag)
        private val rootView = itemView.findViewById<View>(R.id.root)

        fun bind(item: News) {
           item.apply {
               newsTitle.text = this.title
               newsTag.text = this.tag
                newsImage.showImage(item.image ?: "")
                newsImage.transitionName = itemView.context.getString(R.string.newsImageTransiction,item.id)
               newsTitle.transitionName = itemView.context.getString(R.string.newsTitleTransiction,item.id)
               rootView.setOnClickListener { listener?.invoke(item,newsImage, newsTitle) }
           }
        }

    }
}