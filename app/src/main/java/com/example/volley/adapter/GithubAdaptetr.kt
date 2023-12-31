package com.example.volley.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.volley.databinding.GithubItemBinding
import com.example.volley.model.GithubUser

class GithubAdaptetr: ListAdapter<GithubUser, GithubAdaptetr.UserViewHolder>(DiffCallBack()) {
    private class DiffCallBack : DiffUtil.ItemCallback<GithubUser>(){

        override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(  parent: ViewGroup,viewType: Int ):UserViewHolder {
        return UserViewHolder(
            GithubItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }





    override fun onBindViewHolder(holder: GithubAdaptetr.UserViewHolder, position: Int) {
          holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binding: GithubItemBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind (user: GithubUser){
                    binding.apply {
                        Glide.with(imageView)
                            .load(user.avatar_url)
                            .into(imageView)
                        textLogin.text = user.login
                    }
                }
            }
}