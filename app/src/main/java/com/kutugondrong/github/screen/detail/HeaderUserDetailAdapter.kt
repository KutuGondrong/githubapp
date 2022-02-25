package com.kutugondrong.github.screen.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.kutugondrong.data.model.User
import com.kutugondrong.github.R
import com.kutugondrong.github.databinding.AdapterHeaderUserDetailBinding

class HeaderUserDetailAdapter(private val data: User) :
    RecyclerView.Adapter<HeaderUserDetailAdapter.DataViewHolder>(){

    override fun getItemCount(): Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            AdapterHeaderUserDetailBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind()
    }

    inner class DataViewHolder(private val binding: AdapterHeaderUserDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            with(binding) {
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
                txtCountFollower.text = data.followers.toString()
                txtCountFollowing.text = data.following.toString()
            }
        }
    }


}