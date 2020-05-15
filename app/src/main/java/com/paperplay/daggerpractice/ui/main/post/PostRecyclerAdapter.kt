package com.paperplay.daggerpractice.ui.main.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paperplay.daggerpractice.R
import com.paperplay.daggerpractice.model.Post
import java.util.*

class PostRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var posts: List<Post> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as PostViewHolder).bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun setPosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    inner class PostViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var comment: TextView
        var progressBar: ProgressBar
        fun bind(post: Post) {
            title.text = post.title
            if(post.comment == null){
                progressBar.visibility = View.VISIBLE
                comment.text = ""
            }
            else{
                progressBar.visibility = View.GONE
                comment.text = post.comment?.size.toString()
            }
        }

        init {
            title = itemView.findViewById(R.id.title)
            comment = itemView.findViewById(R.id.num_comments)
            progressBar = itemView.findViewById(R.id.progress_bar)
        }
    }
}








