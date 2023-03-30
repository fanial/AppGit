package com.codefal.appgit.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codefal.appgit.databinding.FragmentHomeBinding
import com.codefal.appgit.model.ItemsUsers
import com.codefal.appgit.view.adapter.AdapterList
import com.codefal.appgit.view_model.ViewModelApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var model: ViewModelApp
    private val adapterList by lazy { AdapterList() }
    private lateinit var username : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        model = ViewModelProvider(this)[ViewModelApp::class.java]
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRV()
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                username = query.toString()
                setData(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        model.isLoading.observe(viewLifecycleOwner){
            loading(it)
        }
    }

    private fun setData(username: String?) {
        if (username != null) {
            model.getSearch(username)
        }
        model.liveSearch().observe(viewLifecycleOwner){
            if (it != null) {
                adapterList.setData(it as MutableList<ItemsUsers>)
                setRV()
                Log.i(TAG, "Response setData: $it")
            }
        }
    }

    private fun setRV() {
        binding.rvHome.adapter = adapterList
        val layoutManager = LinearLayoutManager(context)
        binding.rvHome.layoutManager = layoutManager
    }

    private fun loading(status: Boolean) {
        when(status){
            true -> {
                binding.loadingBar.visibility = View.VISIBLE
                binding.noData.visibility = View.GONE
                binding.rvHome.visibility = View.GONE
            }
            false -> {
                binding.loadingBar.visibility = View.GONE
                binding.noData.visibility = View.GONE
                binding.rvHome.visibility = View.VISIBLE
            }
        }
    }


}