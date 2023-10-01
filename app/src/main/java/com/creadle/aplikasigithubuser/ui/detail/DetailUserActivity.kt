package com.creadle.aplikasigithubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.creadle.aplikasigithubuser.databinding.ActivityDetailUserBinding


class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }
    private val handler = Handler(Looper.getMainLooper())
    private val interval = 1400L

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(DetailUserViewModel::class.java)

        if (username != null) {
            val fetchUserData = object : Runnable {
                override fun run() {
                    viewModel.setUserDetail(username)
                    viewModel.getUserDetailLiveData().observe(this@DetailUserActivity, { userDetail ->
                        userDetail?.let {
                            binding.apply {
                                tvName.text = it.name ?: "User ini belum mengisi nama"
                                tvUsername.text = it.login
                                tvFollowers.text = "${it.followers} Followers"
                                tvFollowing.text = "${it.following} Following"
                                Glide.with(this@DetailUserActivity)
                                    .load(it.avatar_url)
                                    .optionalCenterCrop()
                                    .into(ivProfile)
                                showLoading(false)
                            }
                        }
                    })


                    if (viewModel.getUserDetailLiveData().value == null) {
                        handler.postDelayed(this, interval)
                    }
                }
            }

            handler.post(fetchUserData)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) { binding.progressBar.visibility = View.VISIBLE }
        else { binding.progressBar.visibility = View.GONE }
    }

}