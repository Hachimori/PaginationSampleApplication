package com.github.hachimori.paginationsampleapplication.ui.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.hachimori.paginationsampleapplication.databinding.ItemFooterBinding
import timber.log.Timber

class LoadStateFooterAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateFooterAdapter.LoadStateFooterViewHolder>() {

     override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateFooterViewHolder {
        val binding = ItemFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateFooterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LoadStateFooterViewHolder,
        loadState: LoadState
    ) {
        holder.loading.isVisible = loadState is LoadState.Loading
        holder.retryButton.isVisible = loadState is LoadState.Error

        holder.retryButton.setOnClickListener {
            retry()
        }
    }

    class LoadStateFooterViewHolder(binding: ItemFooterBinding) : RecyclerView.ViewHolder(binding.root) {
        val retryButton: Button = binding.retry
        val loading: ProgressBar = binding.loading
    }
}
