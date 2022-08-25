package com.github.hachimori.paginationsampleapplication.ui.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.hachimori.paginationsampleapplication.databinding.ItemUserBinding
import com.github.hachimori.paginationsampleapplication.io.appmodels.User
import timber.log.Timber

class UserAdapter : PagingDataAdapter<User, UserAdapter.UserViewHolder>(USER_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        user?.let {
            Glide
                .with(holder.itemView)
                .load(user.avatarUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.avatar)
            holder.login.text = user.login
        }
    }

    class UserViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val avatar: ImageView = binding.avatar
        val login: TextView = binding.login
    }

    companion object {
        private val USER_DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }
}