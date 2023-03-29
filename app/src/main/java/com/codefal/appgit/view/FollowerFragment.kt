@file:Suppress("DEPRECATION")

package com.codefal.appgit.view

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codefal.appgit.databinding.FragmentFollowerBinding
import com.codefal.appgit.model.ItemsUsers
import com.codefal.appgit.view.adapter.AdapterList
import com.codefal.appgit.view_model.ViewModelApp


class FollowerFragment : Fragment() {
    private var _binding : FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private lateinit var modelApp: ViewModelApp
    private lateinit var adapterFol: AdapterList
    private lateinit var shared : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        modelApp = ViewModelProvider(this)[ViewModelApp::class.java]
        _binding = FragmentFollowerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shared = requireActivity().getSharedPreferences("username", Context.MODE_PRIVATE)!!
        val username = shared.getString("username", "")
        Log.e(ContentValues.TAG, "get username Followers: $username")

        adapterFol = AdapterList(ArrayList())
        setRV()
        if (username != null) {
            setData(username)
        }

        modelApp.isLoading.observe(viewLifecycleOwner){
            loading(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setData(username: String) {
        modelApp.getFollowers(username)
        modelApp.liveFollowers.observe(viewLifecycleOwner){
            if (it != null){
                adapterFol.setData(it as ArrayList<ItemsUsers>)
                adapterFol = AdapterList(it)
                setRV()
                adapterFol.notifyDataSetChanged()
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
}