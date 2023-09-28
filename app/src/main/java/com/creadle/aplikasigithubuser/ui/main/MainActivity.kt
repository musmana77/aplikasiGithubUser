package com.creadle.aplikasigithubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.creadle.aplikasigithubuser.data.response.User
import com.creadle.aplikasigithubuser.databinding.ActivityMainBinding
import com.creadle.aplikasigithubuser.ui.detail.DetailUserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemCliked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also{
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }

        })
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        binding.apply {
            rvGithub.layoutManager = LinearLayoutManager(this@MainActivity)
            rvGithub.setHasFixedSize(true)
            rvGithub.adapter = adapter

            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchView.hide()
                    searchBar.text = searchView.text
                    searchUser()
                    false
                }

        }
        initialSearch()

        viewModel.getSearchUsers().observe(this,{
            if (it != null){
                adapter.setList(it)
                showLoading(false)

        }
        })

     }

    private fun initialSearch(){
        binding.apply {
            val query = ("musmana")
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }
    private fun searchUser(){
        binding.apply {
            val query = searchView.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }

    }
}

