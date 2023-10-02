package com.creadle.aplikasigithubuser.ui.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.creadle.aplikasigithubuser.R
import com.creadle.aplikasigithubuser.databinding.FragmentFollowBinding
import com.creadle.aplikasigithubuser.ui.main.UserAdapter

class FollowingFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    private var isViewCreated = false

    private val handler = Handler(Looper.getMainLooper())
    private val interval = 1500L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)

        isViewCreated = true

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvGithub.setHasFixedSize(true)
            rvGithub.layoutManager = LinearLayoutManager(activity)
            rvGithub.adapter = adapter
        }
        showLoading(true)
        viewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)

        val fetchFollowingData = object : Runnable {
            override fun run() {
                if (isViewCreated) {
                    viewModel.setListFollowing(username)
                    viewModel.getListFollowing().observe(viewLifecycleOwner, { followers ->
                        followers?.let {
                            adapter.setList(it)
                            showLoading(false)
                        }
                    })


                    if (viewModel.getListFollowing().value == null && isViewCreated) {

                        handler.postDelayed(this, interval)
                    }
                }
            }
        }
        if (isViewCreated){
            handler.post(fetchFollowingData)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        isViewCreated = false
    }

    private fun showLoading(state: Boolean) {
        if (state) { binding.progressBar.visibility = View.VISIBLE }
        else { binding.progressBar.visibility = View.GONE }
    }
}
