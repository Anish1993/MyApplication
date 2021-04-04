package com.example.myapplication.Detail.View

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Comment.Model.CommentModel
import com.example.myapplication.Comment.View.CommentFragment
import com.example.myapplication.Detail.Adapter.CommentAdapter
import com.example.myapplication.List.Adapter.ReposListAdapter
import com.example.myapplication.List.Model.Items
import com.example.myapplication.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DetailFragment : Fragment() {

    var data:Items? = null

    var nm_txt:TextView? = null
    var ful_nm_txt:TextView? = null
    var des_txt:TextView? = null
    var prof_img:ImageView? = null
    var cmnt_btn: Button? = null
    var cmnt_rcv: RecyclerView? = null


    var adapter: CommentAdapter? = null

    var cmntList:MutableList<CommentModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val bundle = this.getArguments()


        if(bundle != null){

            if (bundle?.getSerializable("data") !=null) {

//                data =(Items) bundle!!.getSerializable("data")
                data = bundle.getSerializable("data") as Items?
            }
        }
        else{

            if (savedInstanceState != null)
            {
                if (savedInstanceState.getSerializable("data") !=null) {

                    data = savedInstanceState.getSerializable("data") as Items?
                }
            }
        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        nm_txt = view?.findViewById(R.id.nm_txt)
        ful_nm_txt = view?.findViewById(R.id.ful_nm_txt)
        des_txt = view?.findViewById(R.id.des_txt)
        prof_img = view?.findViewById(R.id.prof_img)
        cmnt_btn = view?.findViewById(R.id.cmnt_btn)
        cmnt_rcv = view?.findViewById(R.id.cmnt_rcv)


        data?.name?.let {

            nm_txt?.setText(it)
        }

        data?.full_name?.let {


            ful_nm_txt?.setText(it)
        }


        data?.description?.let {

            des_txt?.setText(it)
        }

        data?.owner?.avatar_url?.let {


            Glide.with(context!!).load(it).into(prof_img!!)

        }

        cmnt_btn?.setOnClickListener {

            var bundle =  Bundle()
            bundle.putLong("data",data?.id!!)

            var cmntFragment = CommentFragment()
            cmntFragment.setArguments(bundle)


            getFragmentManager()
                    ?.beginTransaction()
                    ?.replace(R.id.container, cmntFragment,"comment")
                    ?.addToBackStack("comment")
                    ?.commit()

        }

        if (data != null) {


            var filterList:MutableList<CommentModel> = mutableListOf()

            filterList.addAll(getList("commentList"))


            for (i in 0 until filterList.size)
            {
                if (filterList[i].id == data?.id)
                {
                    cmntList.add(filterList[i])
                }

            }


        }

        adapter = CommentAdapter(context!!,cmntList)

        cmnt_rcv?.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
        cmnt_rcv?.adapter = adapter








    }


    fun getList(key: String): MutableList<CommentModel> {
        var arrayItems: MutableList<CommentModel> = mutableListOf()
        val sharedPreference =  context!!.getSharedPreferences("StoreComment", Context.MODE_PRIVATE)
        val serializedObject: String? = sharedPreference.getString(key, null)
        if (serializedObject != null) {
            val gson = Gson()
            val type: Type = object : TypeToken<List<CommentModel?>?>() {}.type
            arrayItems = gson.fromJson<MutableList<CommentModel>>(serializedObject, type)
        }
        return arrayItems
    }




    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable("data",data)
    }


}