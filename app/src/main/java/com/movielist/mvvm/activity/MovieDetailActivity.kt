/*
This activity was created by
Jayesh Shah on 25th May 2023
This activity Collects the Parceable data from the intent and loads in the view with banner and bottom sheet
Bottom sheet individually contains Title , Overview, productionCompany, etc*/
package com.movielist.mvvm.activity

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.movielist.mvvm.R
import com.movielist.mvvm.adapter.ProductionCompanyAdapter
import com.movielist.mvvm.model.MovieDetail
import com.movielist.mvvm.utils.Constants
import com.squareup.picasso.Picasso

class MovieDetailActivity : BaseActivity() {
    var movieDetail:MovieDetail? = null
    var screenHeight = 0
    var screenWidth = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_movie_detail,super.getContainer(),true)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels
        screenWidth = displayMetrics.widthPixels
        loadToolBar()
        loadData()
        if(movieDetail==null) {
            Toast.makeText(this@MovieDetailActivity,"Data is Blank",Toast.LENGTH_SHORT).show()
            finish()
        }
        loadBottomsheet()
    }
    //this function will load all the required data of the Movie
    private fun loadBottomsheet() {
        val bottomSheetView = findViewById<LinearLayout>(R.id.bottomsheet)
        val bottomSheteBehaviour = BottomSheetBehavior.from(bottomSheetView)
        bottomSheteBehaviour.isDraggable = true
        bottomSheteBehaviour.isHideable = true
        bottomSheteBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED
        if(bottomSheetView!=null) {
            loadBanner()
            loadTitle(bottomSheetView)
            loadGenre(bottomSheetView)
            loadOverView(bottomSheetView)
            loadTagLine(bottomSheetView)
            loadProductionCompany(bottomSheetView)
            loadCollection(bottomSheetView)
        }

        bottomSheteBehaviour.peekHeight = 80
    }

    private fun loadCollection(bottomSheetView: LinearLayout) {
        if(movieDetail?.belongs_to_collection!=null){
            val ll_collection = bottomSheetView.findViewById<LinearLayout>(R.id.ll_collection)
            ll_collection.visibility = View.VISIBLE
            val tv_collection = bottomSheetView.findViewById<TextView>(R.id.tv_collectionName)
            tv_collection.text = movieDetail?.belongs_to_collection?.name
            val iv_collection_poster = bottomSheetView.findViewById<ImageView>(R.id.iv_collection_poster)
            //iv_collection_poster.layoutParams = LinearLayout.LayoutParams(screenWidth,(screenWidth/3)*2)
            if(movieDetail?.belongs_to_collection?.poster_path!=null) {
                Picasso.get()
                    .load(Constants.IMAGE_URL + movieDetail?.belongs_to_collection?.poster_path).error(R.drawable.baseline_error_24).into(iv_collection_poster)
            }
        }
    }

    private fun loadProductionCompany(bottomSheetView: LinearLayout) {
        if(movieDetail?.production_companies!=null && movieDetail?.production_companies?.size?.compareTo(0)==1){
            val linearLayout = bottomSheetView.findViewById<LinearLayout>(R.id.ll_production_companies)
            linearLayout?.visibility = View.VISIBLE
            val rv_production_company = bottomSheetView.findViewById<RecyclerView>(R.id.rv_producer_company)
            val layoutmanager= LinearLayoutManager(this)
            layoutmanager.orientation = RecyclerView.HORIZONTAL
            rv_production_company.layoutManager = layoutmanager

            val adapter_production_company = ProductionCompanyAdapter(movieDetail?.production_companies!!)
            rv_production_company.adapter = adapter_production_company
            adapter_production_company.notifyDataSetChanged()

        }
    }

    private fun loadTagLine(view:View) {
        val tv_tagline = view.findViewById<TextView>(R.id.tv_tagline)
        if(tv_tagline!=null){
            tv_tagline.text = movieDetail?.tagline
        }
    }

    private fun loadOverView(view: View) {
        val tvOverview  = view.findViewById<TextView>(R.id.tv_overview)
        if(movieDetail?.overview!=null){
            tvOverview.text = movieDetail?.overview
        }
    }

    private fun loadGenre(view: View) {
        val tvGenre  = view.findViewById<TextView>(R.id.tv_genre)
        if(movieDetail?.genres!=null){
            var genre = ""
            movieDetail?.genres?.forEach{
                if(it?.name!=null){
                    genre+=" "+it?.name+","
                }
            }
            if(genre.length>0) {
                tvGenre.text = genre.substring(0, genre.length - 1)
            }
        }
    }

    private fun loadTitle(view: View) {
        val tvName = view?.findViewById<TextView>(R.id.tv_name)
        tvName?.text = movieDetail?.title
    }

    private fun loadBanner() {
        val ivBanner =findViewById<ImageView>(R.id.iv_banner)
        Picasso.get().load(Constants.IMAGE_URL + movieDetail?.poster_path).error(R.drawable.baseline_error_24).into(ivBanner)
    }

    private fun loadToolBar() {
        super.setToolBarTitle("MovieDetail")
        super.setToolBarBack()
    }

    private fun loadData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            movieDetail = intent.getParcelableExtra(Constants.KEY_MOVIEDETAIL,MovieDetail::class.java)
        }else{
            movieDetail = intent.getParcelableExtra<MovieDetail>(Constants.KEY_MOVIEDETAIL)!!
        }
    }
}