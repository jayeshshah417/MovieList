package com.movielist.mvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.movielist.mvvm.R
import com.movielist.mvvm.model.ProductionCompany
import com.movielist.mvvm.utils.Constants
import com.squareup.picasso.Picasso

class ProductionCompanyAdapter(var productioncompanyList: List<ProductionCompany>): RecyclerView.Adapter<ProductionCompanyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductionCompanyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_producercompany_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductionCompanyAdapter.ViewHolder, position: Int) {
        val productioncompanyItem = productioncompanyList.get(position)
        Picasso.get().load(Constants.IMAGE_URL+productioncompanyItem.logo_path).error(R.drawable.baseline_error_24).into(holder.iv_banner)
    }

    override fun getItemCount(): Int {
        return this.productioncompanyList.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val iv_banner = itemView.findViewById<ImageView>(R.id.iv_banner)
    }

    fun updateMovieList(list: List<ProductionCompany>){
        this.productioncompanyList = list
    }

}