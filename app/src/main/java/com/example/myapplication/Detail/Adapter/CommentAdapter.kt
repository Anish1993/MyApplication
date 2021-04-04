package com.example.myapplication.Detail.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Comment.Model.CommentModel
import com.example.myapplication.List.Adapter.ReposListAdapter
import com.example.myapplication.List.Model.Items
import com.example.myapplication.R

class CommentAdapter (val context: Context, var list: MutableList<CommentModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {





    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name_txt: AppCompatTextView = itemView.findViewById(R.id.name_txt)
        val cmnt_txt: AppCompatTextView = itemView.findViewById(R.id.cmnt_txt)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return UserViewHolder(inflater.inflate(R.layout.item_cmnt_list, parent, false))
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        (holder as UserViewHolder).name_txt.text = list?.get(position).name

        holder.cmnt_txt.text = list?.get(position).comment


    }










}