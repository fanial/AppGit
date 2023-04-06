@file:Suppress("DEPRECATION")

package com.codefal.appgit.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.codefal.appgit.databinding.FragmentFollowBinding
import com.codefal.appgit.model.ItemsUsers
import com.codefal.appgit.view.adapter.AdapterList
import com.codefal.appgit.view_model.ViewModelApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowFragment() : Fragment() {
    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private val modelApp : ViewModelApp by viewModels()
    private val adapterFol by lazy { AdapterList() }
    private lateinit var shared : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shared = requireActivity().getSharedPreferences("username", Context.MODE_PRIVATE)!!
        val username = shared.getString("username", "")
        Log.i(TAG, "onViewCreated: $username")

        val page = arguments?.getInt(ARGS_KEY, 0)
        Log.i(TAG, "onViewCreated: $page")

        if (username != null){
            when (page){
                1 -> page1(username)
                2 -> page2(username)
            }
        }

        setRV()


        modelApp.isLoading.observe(viewLifecycleOwner){
            loading(it)
        }
    }

    private fun page1(username: String) {
        modelApp.getFollowers(username)
        modelApp.liveFollowers.observe(viewLifecycleOwner){
            if (it != null){
                adapterFol.setData(it as MutableList<ItemsUsers>)
                setRV()
            }
        }
    }

    private fun page2(username: String) {
        modelApp.getFollowing(username)
        modelApp.liveFollowing.observe(viewLifecycleOwner){
            if (it != null){
                adapterFol.setData(it as MutableList<ItemsUsers>)
                setRV()
            }
        }
    }

    private fun setRV() {
        binding.rvFollowing.adapter = adapterFol
        val layoutManager = LinearLayoutManager(context)
        binding.rvFollowing.layoutManager = layoutManager
    }

    private fun loading(status: Boolean) {
        when(status){
            true -> {
                binding.loadingBar.visibility = View.VISIBLE
                binding.noData.visibility = View.GONE
                binding.rvFollowing.visibility = View.GONE
            }
            false -> {
                binding.loadingBar.visibility = View.GONE
                binding.noData.visibility = View.GONE
                binding.rvFollowing.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val ARGS_KEY = "position"
    }
}