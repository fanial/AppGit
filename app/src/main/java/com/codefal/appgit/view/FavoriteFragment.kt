package com.codefal.appgit.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codefal.appgit.R
import com.codefal.appgit.databinding.FragmentFavoriteBinding
import com.codefal.appgit.model.ItemsUsers
import com.codefal.appgit.view.adapter.AdapterList
import com.codefal.appgit.view_model.ViewModelApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val modelApp : ViewModelApp by viewModels()
    private val adapterList by lazy { AdapterList() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = getString(R.string.favorite)

        setRV()
        setData()
        modelApp.isLoading.observe(viewLifecycleOwner){
            loading(it)
        }
    }

    private fun setData() {
        loading(true)
        modelApp.getAllFavorite().observe(viewLifecycleOwner){
            if (it != null){
                adapterList.setData(it as MutableList<ItemsUsers>)
                setRV()
                loading(false)
            }
        }
    }

    private fun setRV() {
        binding.rvFavorite.adapter = adapterList
        val layoutManager = LinearLayoutManager(context)
        binding.rvFavorite.layoutManager = layoutManager
        adapterList.onClick = {
            val bundle = Bundle()
            bundle.putParcelable("detail_user", it)
            findNavController().navigate(R.id.action_favoriteFragment_to_userFragment, bundle)
        }
    }

    private fun loading(status: Boolean) {
        when(status){
            true -> {
                binding.loadingBar.visibility = View.VISIBLE
                binding.noData.visibility = View.GONE
                binding.rvFavorite.visibility = View.GONE
            }
            false -> {
                binding.loadingBar.visibility = View.GONE
                binding.noData.visibility = View.GONE
                binding.rvFavorite.visibility = View.VISIBLE
            }
        }
    }

}