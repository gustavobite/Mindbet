package br.com.about.presentation.adapter

import android.view.View
import android.widget.TextView
import br.com.about.R
import br.com.about.data.model.Member
import br.com.mindbet.common.component.recycler_view.BaseRecyclerViewAdapter
import br.com.mindbet.common.extension.showImage
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.imageview.ShapeableImageView

class MembersAdapter(listener: ((Member) -> Unit)? = null) :
    BaseRecyclerViewAdapter<Member, MembersAdapter.MembersHolder>(listener) {
    override fun getLayoutResource(viewType: Int): Int = R.layout.adapter_members
    override fun createViewHolder(view: View, viewType: Int): MembersHolder =
        MembersHolder(
            view,
            onClick
        )

    class MembersHolder(itemView: View, val listener: ((Member) -> Unit)? = null) :
        BaseViewHolder<Member>(itemView, listener) {
        private val image = itemView.findViewById<ShapeableImageView>(R.id.memberImage)
        private val name = itemView.findViewById<TextView>(R.id.name)
        private val job = itemView.findViewById<TextView>(R.id.job)

        override fun bind(item: Member) {
            name.text = item.name
            job.text = item.job

            Glide.with(itemView.context)
                .asBitmap()
                .load(item.image)
                .dontAnimate()
                .error(R.drawable.ic_pessoa)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(image)
        }
    }
}