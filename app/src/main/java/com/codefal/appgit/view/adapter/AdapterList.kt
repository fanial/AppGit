package com.codefal.appgit.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codefal.appgit.databinding.ItemListBinding
import com.codefal.appgit.model.ItemsUsers

class AdapterList: RecyclerView.Adapter<AdapterList.HomeViewHolder>() {

    var onClick: ((ItemsUsers) -> Unit)? = null
    private val differCallback = object : DiffUtil.ItemCallback<ItemsUsers>(){
        override fun areItemsTheSame(oldItem: ItemsUsers, newItem: ItemsUsers): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemsUsers, newItem: ItemsUsers): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)
    class HomeViewHolder(val binding : ItemListBinding): RecyclerView.ViewHolder(binding.root){
        fun setItem(item : ItemsUsers){
            binding.apply {
                username.text = item.login
                Glide.with(itemView).load(item.avatarUrl).into(avatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.setItem(item)
        holder.setIsRecyclable(false)
        holder.binding.cardList.setOnClickListener {
            onClick?.invoke(item)
        }
    }
    fun setData(data: MutableList<ItemsUsers>) = differ.submitList(data)
}