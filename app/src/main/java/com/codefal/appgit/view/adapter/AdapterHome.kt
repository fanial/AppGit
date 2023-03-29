package com.codefal.appgit.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codefal.appgit.R
import com.codefal.appgit.databinding.ItemListBinding
import com.codefal.appgit.model.ItemsSearch

class AdapterHome(private var itemUser : List<ItemsSearch>): RecyclerView.Adapter<AdapterHome.HomeViewHolder>() {
    class HomeViewHolder(val binding : ItemListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = itemUser.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.binding.username.text = itemUser[position].login
        Glide.with(holder.itemView).load(itemUser[position].avatarUrl).into(holder.binding.avatar)
        holder.binding.cardList.setOnClickListener {
            val data = Bundle()
            data.putParcelable("detail_user", itemUser[position])
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_userFragment, data)
        }
    }
    fun setData(data: ArrayList<ItemsSearch>){
        this.itemUser = data
    }
}