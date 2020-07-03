package br.com.news.adapter

import android.view.View
import android.widget.TextView
import br.com.mindbet.common.component.recycler_view.BaseRecyclerViewAdapter
import br.com.news.R
import br.com.news.model.News
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.imageview.ShapeableImageView


class NewsAdapter(listener: ((News) -> Unit)? = null) :
    BaseRecyclerViewAdapter<News, NewsAdapter.NewsHolder>(listener) {

    override fun getLayoutResource(viewType: Int): Int = R.layout.adapter_news

    override fun createViewHolder(view: View, viewType: Int): NewsHolder =
        NewsHolder(view, onClick)

    class NewsHolder(itemView: View, val listener: ((News) -> Unit)? = null) :
        BaseViewHolder<News>(itemView, listener) {

        private val newsImage = itemView.findViewById<ShapeableImageView>(R.id.newsImage)
        private val newsTitle = itemView.findViewById<TextView>(R.id.newsTitle)
        private val newsTag = itemView.findViewById<TextView>(R.id.newsTag)
        private val rootView = itemView.findViewById<View>(R.id.root)

        override fun bind(item: News) {
           item.apply {
               newsTitle.text = this.title
               newsTag.text = this.tag

               Glide.with(newsImage.context)
                   .asBitmap()
                   .load(item.image)
                   .dontAnimate()
                   .diskCacheStrategy(DiskCacheStrategy.NONE)
                   .skipMemoryCache(true)
                   .into(newsImage)

               rootView.setOnClickListener { listener?.invoke(item) }
           }
        }

    }
}