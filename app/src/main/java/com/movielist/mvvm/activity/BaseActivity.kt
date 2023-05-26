package com.movielist.mvvm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.movielist.mvvm.R

open class BaseActivity : AppCompatActivity() {
    private lateinit var contentView:ViewGroup
    private lateinit var toolBar:Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        initViews()
    }

    private fun initViews() {
        contentView = findViewById(R.id.container)
        toolBar = findViewById(R.id.toolbar)
    }

    protected fun getContainer():ViewGroup{
        return contentView
    }

    protected fun setToolBarTitle(title:String){
        toolBar.setTitle(title)
    }
    protected fun setToolBarBack(){
        toolBar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolBar.setNavigationOnClickListener(object : OnClickListener{
            override fun onClick(v: View?) {
               onBackPressed()
            }

        })
    }

}