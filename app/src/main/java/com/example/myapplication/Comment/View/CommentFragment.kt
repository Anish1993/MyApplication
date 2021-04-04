package com.example.myapplication.Comment.View

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.Comment.Model.CommentModel
import com.example.myapplication.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class CommentFragment : Fragment() {

    var item_id :Long = -1L

    var nm_txt: TextView? = null
    var cmnt_txt: TextView? = null
    var submit_btn: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val bundle = this.getArguments()

        if(bundle != null){

            if (bundle?.getLong("data") !=null) {

                item_id = bundle.getLong("data")
            }
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        nm_txt = view?.findViewById(R.id.nm_txt)
        cmnt_txt = view?.findViewById(R.id.cmnt_txt)
        submit_btn = view?.findViewById(R.id.submit_btn)


        submit_btn?.setOnClickListener {

            submit_btn?.isClickable = false


            if (item_id != -1L) {

                if (nm_txt?.text.isNullOrBlank()) {
                    Toast.makeText(context, "Please Enter Name", Toast.LENGTH_LONG).show()
                    submit_btn?.isClickable = true

                } else if (cmnt_txt?.text.isNullOrBlank()) {
                    Toast.makeText(context, "Please Enter Comment", Toast.LENGTH_LONG).show()

                    submit_btn?.isClickable = true
                } else {

                    var cmntList: MutableList<CommentModel> = mutableListOf()

                    cmntList.addAll(getList("commentList"))

//                    if (cmntList?.size == 0) {

                        var cmtdata = CommentModel(name = nm_txt?.text.toString(), comment = cmnt_txt?.text.toString(), id = item_id)
                        cmntList.add(cmtdata)
                        setList("commentList", cmntList)

                        Toast.makeText(context, "Comment Successfully added", Toast.LENGTH_LONG).show()

                        nm_txt?.setText("")
                        cmnt_txt?.setText("")

                    submit_btn?.isClickable = true


//                    } else {
//
//                        var cmtdata = CommentModel(name = nm_txt?.text.toString(), comment = cmnt_txt?.text.toString(), id = item_id)
//                        cmntList.add(cmtdata)
//                        setList("commentList", cmntList)
//                    }


                }
            } else {

                submit_btn?.isClickable = true

                Toast.makeText(context, "Cannot Submit comment Please derirect from Detail Page", Toast.LENGTH_LONG).show()
            }
        }

    }


    fun <T> setList(key: String?, list: List<T>?) {
        val gson = Gson()
        val json = gson.toJson(list)
        set(key, json)
    }

    operator fun set(key: String?, value: String?) {

        val sharedPreference =  context!!.getSharedPreferences("StoreComment", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString(key, value)
        editor.commit()
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


}