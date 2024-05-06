package com.sachin_singh_dighan.learn_kotlin_coroutines.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sachin_singh_dighan.learn_kotlin_coroutines.R
import com.sachin_singh_dighan.learn_kotlin_coroutines.data.model.ApiUser

class ApiUserAdapter(
    private val users: ArrayList<ApiUser>
) : RecyclerView.Adapter<ApiUserAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: ApiUser) {
            val textViewUserName = itemView.findViewById<TextView>(R.id.textViewUserName)
            val textViewUserEmail = itemView.findViewById<TextView>(R.id.textViewUserEmail)
            val imageViewAvatar = itemView.findViewById<ImageView>(R.id.imageViewAvatar)
            textViewUserName.text = user.name
            textViewUserEmail.text = user.email
            Glide.with(imageViewAvatar.context).load(user.avatar).into(imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun addData(list: List<ApiUser>){
        users.addAll(list)
    }
}