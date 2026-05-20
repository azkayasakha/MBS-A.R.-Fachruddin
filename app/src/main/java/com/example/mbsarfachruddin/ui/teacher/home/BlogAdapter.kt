package com.example.mbsarfachruddin.ui.teacher.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.databinding.ItemBlogBinding
import com.example.mbsarfachruddin.model.remote.blog.BlogResponseItem
import dev.androidbroadcast.vbpd.viewBinding

class BlogAdapter(private val listBlog: List<BlogResponseItem>) : RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {

    var itemClickListener: ((BlogResponseItem) -> Unit)? = null

    class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemBlogBinding by viewBinding(ItemBlogBinding::bind)

        fun bindView(blog: BlogResponseItem) {
            binding.ivBlogCover.load("${blog.embedded.wpFeaturedmedia[0].mediaDetails.sizes.full.sourceUrl}")
            val title = HtmlCompat.fromHtml(blog.title.rendered, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            binding.tvBlogTitle.text = title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogAdapter.BlogViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_blog, parent, false)
        return BlogViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlogAdapter.BlogViewHolder, position: Int) {
        val blog = listBlog[position]
        holder.bindView(blog)
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(blog)
        }
    }

    override fun getItemCount(): Int {
        return listBlog.size
    }
}