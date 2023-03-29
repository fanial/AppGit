package com.codefal.appgit.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codefal.appgit.databinding.ItemListBinding
import com.codefal.appgit.model.ResponseFollowItem

class AdapterFollowing(private var listUser : List<ResponseFollowItem>): RecyclerView.Adapter<AdapterFollowing.FollowViewHolder>() {
    class FollowViewHolder (val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        return FollowViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.binding.username.text = listUser[position].login
        Glide.with(holder.itemView).load(listUser[position].avatarUrl).into(holder.binding.avatar)
    }

    fun setData(data: ArrayList<ResponseFollowItem>){
        this.listUser = data
    }
}