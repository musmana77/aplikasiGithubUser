package com.creadle.aplikasigithubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.creadle.aplikasigithubuser.R
import com.creadle.aplikasigithubuser.data.response.User
import com.creadle.aplikasigithubuser.databinding.ActivityMainBinding
import com.creadle.aplikasigithubuser.ui.detail.DetailUserActivity
import com.creadle.aplikasigithubuser.ui.favorite.FavoriteActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.progressBarSV.visibility = View.GONE

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also{
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }

        })
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(MainViewModel::class.java)


        binding.apply {
            rvGithub.layoutManager = LinearLayoutManager(this@MainActivity)
            rvGithub.setHasFixedSize(true)
            rvGithub.adapter = adapter

            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    showLoadingSV(true)
                    searchUser()
                    searchBar.text = searchView.text

                    viewModel.getSearchUsers().observe(this@MainActivity, { searchResult ->
                        if (searchResult != null) {
                            Handler().postDelayed({
                                searchView.hide()
                            }, 400)
                        }
                    })

                    false
                }

        }


        viewModel.getSearchUsers().observe(this,{
            if (it != null){
                adapter.setList(it)
                showLoading(false)

        }
        })

     }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tekan sekali lagi untuk keluar",Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 1700)
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
            viewModel.setSearchUsers(query)
            showLoadingSV(false)
        }
    }


    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showLoadingSV(state: Boolean){
        if (state){
            binding.progressBarSV.visibility = View.VISIBLE
        }else{
            viewModel.getSearchUsers().observe(this@MainActivity, { searchResult ->
                if (searchResult != null) {
                    Handler().postDelayed({
                        binding.progressBarSV.visibility = View.GONE
                    }, 300)
                }
            })

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        initialSearch()
        Toast.makeText(applicationContext, "Develop by Github musmana77", Toast.LENGTH_SHORT).show()

        lifecycleScope.launch {
            val isChecked = viewModel.getUIMode.first()
            val item = menu?.findItem(R.id.action_night_mode)
            item?.isChecked = isChecked
            if (item != null) {
                delay(55)
                viewModel.setUIMode(item, isChecked)
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_menu ->{
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.action_night_mode ->{
                item.isChecked = !item.isChecked
                viewModel.setUIMode(item, item.isChecked)
                true
            }else -> false
        }
        return super.onOptionsItemSelected(item)
    }

}
