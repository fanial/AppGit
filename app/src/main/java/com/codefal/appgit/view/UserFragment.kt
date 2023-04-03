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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.codefal.appgit.R
import com.codefal.appgit.databinding.FragmentUserBinding
import com.codefal.appgit.model.ItemsUsers
import com.codefal.appgit.room.RoomEntity
import com.codefal.appgit.view.adapter.AdapterSectionPager
import com.codefal.appgit.view_model.ViewModelApp
import com.codefal.appgit.view_model.ViewModelRoom
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment() {
    private var _binding : FragmentUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var modelApp: ViewModelApp
    private lateinit var shared : SharedPreferences
    private val modelRoom: ViewModelRoom by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        modelApp = ViewModelProvider(this)[ViewModelApp::class.java]
        _binding = FragmentUserBinding.inflate(layoutInflater)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.detail_user)

        val get = arguments?.getParcelable("detail_user") as ItemsUsers?
        val username = get?.login.toString()
        val avatar = get?.avatarUrl.toString()
        Log.i(TAG, "onViewCreated: get parceable $username")

        shared = requireActivity().getSharedPreferences("username", Context.MODE_PRIVATE)!!
        val editor = shared.edit()
        editor.putString("username", username)
        editor.apply()

        modelApp.getUsers(username)
        getData()
        setViewPager()

        binding.favorite.setOnClickListener {
            GlobalScope.launch {
                val insertData = RoomEntity(username, avatar)
                modelRoom.insert(insertData)
            }
            activity?.runOnUiThread {
                Toast.makeText(context, R.string.success, Toast.LENGTH_SHORT).show()
            }
        }

        modelApp.isLoading.observe(viewLifecycleOwner){
            loading(it)
        }
    }

    private fun setViewPager() {
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        val sectionPagerAdapter = AdapterSectionPager(childFragmentManager, lifecycle)
        viewPager.adapter = sectionPagerAdapter

        modelApp.liveUser.observe(viewLifecycleOwner){
            if (it != null){
                TabLayoutMediator(tabLayout, viewPager){ tab, position ->
                    when(position){
                        0 -> {
                            tab.text = "${it.followers} Followers"
                        }
                        1 -> {
                            tab.text = "${it.following} Following"
                        }
                    }
                }.attach()
            }
        }
    }

    private fun loading(status: Boolean?) {
        when(status){
            true -> {
                binding.loadingBar.visibility = View.VISIBLE
            }
            else -> {
                binding.loadingBar.visibility = View.GONE
            }
        }
    }

    private fun getData() {
        modelApp.liveUser.observe(viewLifecycleOwner){
            if (it != null){
                Glide.with(this).load(it.avatarUrl).into(binding.avatar)
                binding.tvUsername.text = it.login
                binding.bio.text = it.bio
                binding.tvName.text = it.name
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}