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
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.codefal.appgit.databinding.FragmentUserBinding
import com.codefal.appgit.model.ItemsSearch
import com.codefal.appgit.view.adapter.AdapterSectionPager
import com.codefal.appgit.view_model.ViewModelApp
import com.google.android.material.tabs.TabLayoutMediator

class UserFragment : Fragment() {
    private var _binding : FragmentUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var modelApp: ViewModelApp
    private lateinit var shared : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        modelApp = ViewModelProvider(this)[ViewModelApp::class.java]
        _binding = FragmentUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val get = arguments?.getParcelable("detail_user") as ItemsSearch?
        val username = get?.login.toString()
        Log.e(TAG, "onViewCreated: get parceable $username")

        shared = requireActivity().getSharedPreferences("username", Context.MODE_PRIVATE)!!
        val editor = shared.edit()
        editor.putString("username", username)
        editor.apply()

        modelApp.getUsers(username)
        getData()
        setViewPager()

        modelApp.isLoading.observe(viewLifecycleOwner){
            loading(it)
        }
    }

    private fun setViewPager() {
        val sectionPagerAdapter = AdapterSectionPager(childFragmentManager, lifecycle)
        binding.viewPager.adapter = sectionPagerAdapter
        modelApp.liveUser.observe(viewLifecycleOwner){
            if (it != null){
                TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
                    when(position){
                        0 -> {
                            tab.text = "Following : ${it.following}"

                        }
                        1 -> {
                            tab.text = "Followers : ${it.followers}"
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