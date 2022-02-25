package com.kutugondrong.github.screen.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.kutugondrong.data.model.User
import com.kutugondrong.github.R
import com.kutugondrong.github.databinding.AdapterUserItemBinding

class UserItemAdapter(
    private val clicked: (User) -> Unit,
) :
    PagingDataAdapter<User, UserItemAdapter.ViewHolder>(COMPARATOR) {

    inner class ViewHolder(private val binding: AdapterUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: User, position: Int) {
            with(binding) {
                when (position) {
                    0 -> {
                        itemUserGithub.setBackgroundResource(
                            R.drawable.shadow_background_top_left_right)
                        lineBg.visibility = View.GONE
                    }
                    else -> {
                        itemUserGithub.setBackgroundResource(
                            R.drawable.shadow_background_left_right)
                        lineBg.visibility = View.VISIBLE
                    }
                }
                Glide.with(itemView)
                    .load(data.avatar)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_default_avatar)
                    )
                    .error(R.drawable.ic_default_avatar)
                    .into(imgIcon)

                txtFullName.text = data.fullName
                txtUserName.text = data.userNameWithLabel()
                txtDescription.text = data.description
                txtCity.text = data.city
                txtEmail.text = data.email
                itemView.setOnClickListener{
                    clicked(data)
                }
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
        val binding = AdapterUserItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

        }
    }

    fun getItemByPosition(position: Int): User? {
        return getItem(position)
    }

}