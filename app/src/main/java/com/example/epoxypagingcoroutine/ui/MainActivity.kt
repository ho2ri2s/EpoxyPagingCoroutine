package com.example.epoxypagingcoroutine.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.epoxypagingcoroutine.App
import com.example.epoxypagingcoroutine.R
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: GithubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GithubViewModel::class.java)

        viewModel.start()

        viewModel.owner.observe(this, Observer {
            textView.text = it[1].name
        })
    }
}
