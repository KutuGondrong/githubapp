package com.kutugondrong.github.screen.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.kutugondrong.data.model.Repo
import com.kutugondrong.data.model.User
import com.kutugondrong.github.R
import com.kutugondrong.github.databinding.AdapterItemRepoBinding

class RepoItemAdapter(private val user: User) :
    PagingDataAdapter<Repo, RepoItemAdapter.ViewHolder>(COMPARATOR) {

    inner class ViewHolder(private val binding: AdapterItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Repo, position: Int) {
            with(binding) {
                Glide.with(itemView)
                    .load(user.avatar)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_default_avatar)
                    )
                    .error(R.drawable.ic_default_avatar)
                    .into(imgIcon)
                txtTitle.text = data.title
                txtDescription.text = data.description
                txtCountStar.text = data.star.toString()
                txtUpdate.text = data.updateDate
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterItemRepoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.id == newItem.id

        }
    }


}