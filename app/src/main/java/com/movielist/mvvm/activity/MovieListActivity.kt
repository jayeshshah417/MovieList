/*
This activity was created by
Jayesh Shah on 24th May 2023
 */
package com.movielist.mvvm.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movielist.mvvm.MovieApplication
import com.movielist.mvvm.R
import com.movielist.mvvm.adapter.MovieAdapter
import com.movielist.mvvm.model.Movie
import com.movielist.mvvm.utils.Constants
import com.movielist.mvvm.viewmodels.MainViewModel
import com.movielist.mvvm.viewmodels.MainViewModelFactory
import javax.inject.Inject


class MovieListActivity : BaseActivity(), MovieAdapter.OnItemClickListener {
    private lateinit var rv_list_adapter:MovieAdapter
    lateinit var mainViewModel: MainViewModel
    var page : Int = 1
    var loadMore = true

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_movie_list,super.getContainer(),true)
        (application as MovieApplication).applicationComponent.inject(this)

        initViews()

        mainViewModel = ViewModelProvider(this,mainViewModelFactory).get(MainViewModel::class.java)

        mainViewModel.movieLivedata.observe(this,{
            val listupdated = ArrayList<Movie>()
            if(rv_list_adapter.movieList!=null) {
                listupdated.addAll(rv_list_adapter.movieList)
            }
            listupdated.addAll(it)
            rv_list_adapter.updateMovieList(listupdated)
            rv_list_adapter.notifyDataSetChanged()
        })

        mainViewModel.movieDetailLivedata.observe(this,{
            if(it!=null){
                val intent = Intent(this@MovieListActivity,MovieDetailActivity::class.java)
                intent.putExtra(Constants.KEY_MOVIEDETAIL,it)
                startActivity(intent)
            }
        })
    }

    private fun initViews() {
        val rv_list:RecyclerView = findViewById(R.id.rv_list)
        rv_list.layoutManager = LinearLayoutManager(this)
        rv_list_adapter = MovieAdapter(ArrayList(),this)
        rv_list.adapter = rv_list_adapter
        rv_list_adapter.notifyDataSetChanged()

        rv_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if(loadMore) {
                        page++
                        Toast.makeText(this@MovieListActivity,"Page: "+page,Toast.LENGTH_LONG).show()
                        mainViewModel.getMovies(page)
                        loadMore = false
                        Handler().postDelayed({
                              loadMore = true
                        },3000)
                    }
                }
            }
        })
    }

    override fun onItemClick(item: Movie) {
        //Toast.makeText(this@MovieListActivity,item.title,Toast.LENGTH_LONG).show()
        mainViewModel.getMovie(item.id)
    }
}