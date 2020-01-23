package com.shadow.favoritecataloguemovie.util.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shadow.favoritecataloguemovie.R
import com.shadow.favoritecataloguemovie.data.model.response.ItemTv
import com.shadow.favoritecataloguemovie.data.remote.api.ApiKeys
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_tv_item.view.*

class TvAdapter :
    RecyclerView.Adapter<TvAdapter.ViewHolder>() {

    private var clickListener: OnResultClickListener? = null
    private var tv: ArrayList<ItemTv> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_tv_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tv.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tv[position])
    }

    fun clearItem() {
        var size = tv.size
        tv.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun setItem(tv: ArrayList<ItemTv>) {
        this.tv = tv
    }

    fun addItem(tv: ArrayList<ItemTv>) {
        val index = this.tv.lastIndex + 1
        this.tv.addAll(index, tv)
        notifyItemInserted(index)
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        fun bind(tv: ItemTv) {

            val urlImage = ApiKeys.IMAGE_LOW + tv.poster_path
            Picasso.get()
                .load(urlImage)
                .into(itemView.itemPoster)

            itemView.itemTitle.text = tv.original_name
            itemView.itemDate.text = tv.first_air_date
            itemView.itemDesc.text = tv.overview

            itemView.setOnClickListener { clickListener?.onClick(tv) }
        }
    }

    fun setClickListener(listener: OnResultClickListener) {
        clickListener = listener
    }

    interface OnResultClickListener {
        fun onClick(item: ItemTv)
    }
}