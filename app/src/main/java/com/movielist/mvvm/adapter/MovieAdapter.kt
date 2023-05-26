package com.movielist.mvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.movielist.mvvm.R
import com.movielist.mvvm.model.Movie
import com.movielist.mvvm.utils.Constants
import com.squareup.picasso.Picasso

class MovieAdapter(var movieList: List<Movie>,val listener:OnItemClickListener): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        val movieItem = movieList.get(position)
        Picasso.get().load(Constants.IMAGE_URL+movieItem.poster_path).error(R.drawable.baseline_error_24).into(holder.iv_banner)
        holder.iv_banner.setOnClickListener{
            this.listener?.onItemClick(movieItem)
        }
    }

    override fun getItemCount(): Int {
        return this.movieList.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val iv_banner = itemView.findViewById<ImageView>(R.id.iv_banner)
    }

    fun updateMovieList(list: List<Movie>){
        this.movieList = list
    }

    interface OnItemClickListener{
        fun onItemClick(item:Movie)
    }
}