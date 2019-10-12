package com.example.epoxypagingcoroutine.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.epoxypagingcoroutine.App
import com.example.epoxypagingcoroutine.R
import com.example.epoxypagingcoroutine.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity(), GithubController.CardClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: GithubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GithubViewModel::class.java)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val controller = GithubController(this)

        viewModel.apply {
            owner.observe(this@MainActivity, Observer {
                controller.owners = it
                controller.requestModelBuild()
            })
            repos.observe(this@MainActivity, Observer {
                controller.submitList(it)
            })
        }
        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.apply {
            adapter = controller.adapter
            layoutManager = linearLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    linearLayoutManager.orientation
                )
            )
        }
        viewModel.start()

        controller.requestModelBuild()
    }

    override fun onClickCard(userName: String) {
        viewModel.setUsername(userName)
    }
}
