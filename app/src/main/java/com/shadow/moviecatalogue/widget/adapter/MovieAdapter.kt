package com.shadow.moviecatalogue.widget.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shadow.moviecatalogue.R
import com.shadow.moviecatalogue.data.model.response.ItemMovie
import com.shadow.moviecatalogue.data.remote.api.ApiKeys
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_movie_item.view.*

class MovieAdapter :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var clickListener: OnResultClickListener? = null
    private var movie: ArrayList<ItemMovie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movie.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movie[position])

    }

    fun clearItem() {
        var size = movie.size
        movie.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun setItem(movie: ArrayList<ItemMovie>) {
        this.movie = movie
    }

    fun addItem(movie: ArrayList<ItemMovie>) {
        val index = this.movie.lastIndex + 1
        this.movie.addAll(index, movie)
        notifyItemInserted(index)
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun bind(movie: ItemMovie) {

            val urlImage = ApiKeys.IMAGE_LOW + movie.poster_path

            Picasso.get()
                .load(urlImage)
                .into(itemView.itemPoster)

            itemView.itemTitle.text = movie.original_title
            itemView.itemDate.text = movie.release_date
            itemView.itemDesc.text = movie.overview

            itemView.setOnClickListener { clickListener?.onClick(movie) }
        }
    }


    fun setClickListener(listener: OnResultClickListener) {
        clickListener = listener
    }

    interface OnResultClickListener {
        fun onClick(item: ItemMovie)
    }

}