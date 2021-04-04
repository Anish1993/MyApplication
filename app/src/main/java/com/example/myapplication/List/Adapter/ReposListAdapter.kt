package com.example.myapplication.List.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.List.Model.Items
import com.example.myapplication.R

class ReposListAdapter(val context: Context, var list: MutableList<Items>,val load_listener: LoadMore) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var p_count = 1


    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name_txt: AppCompatTextView = itemView.findViewById(R.id.name_txt)
        val ful_name_txt: AppCompatTextView = itemView.findViewById(R.id.ful_name_txt)
        val img: AppCompatImageView = itemView.findViewById(R.id.img)
        val crd: CardView = itemView.findViewById(R.id.crd)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return UserViewHolder(inflater.inflate(R.layout.item_repo_list, parent, false))
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position >= itemCount - 1) {

            p_count++

            load_listener.needtoLoad(""+p_count)

        }

        (holder as UserViewHolder).name_txt.text = list?.get(position).name

        holder.ful_name_txt.text = list?.get(position).full_name

        Glide.with(context).load(list?.get(position).owner.avatar_url).into(holder.img)



        holder.crd.setOnClickListener {

            Toast.makeText(context,"Tap",Toast.LENGTH_LONG).show()

            load_listener.ItemClicked(list[position])

        }




    }




    interface LoadMore {

        fun needtoLoad(page_count: String)
        fun ItemClicked(selectedItem:Items)

    }

    fun add(moreList: List<Items>) {

        list.addAll(moreList)
        notifyDataSetChanged()
    }



}