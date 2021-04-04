package com.example.myapplication.List.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Detail.View.DetailFragment
import com.example.myapplication.List.Adapter.ReposListAdapter
import com.example.myapplication.List.CallBack.ICallBackRepoList
import com.example.myapplication.List.Model.Items
import com.example.myapplication.List.Presenter.ListPresenter
import com.example.myapplication.R
import com.example.myapplication.Utility.Util


class ListingFragment : Fragment() {


    var adapter:ReposListAdapter? = null
    var rcv:RecyclerView? = null
    var progress_bar: ProgressBar? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        rcv = view?.findViewById(R.id.rcv)
        progress_bar = view?.findViewById(R.id.progress_bar)




        adapter = ReposListAdapter(context!!, mutableListOf(),object:ReposListAdapter.LoadMore{
            override fun needtoLoad(page_count: String) {

                getRepoList(page_count)

            }

            override fun ItemClicked(selectedItem: Items) {


                var bundle =  Bundle()
                bundle.putSerializable("data",selectedItem)

                var detailFragment = DetailFragment()
                detailFragment.setArguments(bundle)


                getFragmentManager()
                        ?.beginTransaction()
                        ?.replace(R.id.container, detailFragment,"detail")
                        ?.addToBackStack("detail")
                        ?.commit()


            }
        })
        rcv?.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
        rcv?.adapter = adapter

        getRepoList("1")

    }


    fun getRepoList(page:String) {

        progress_bar?.visibility = View.VISIBLE


        if (Util.isNetworkAvailable(context!!)) {

            val listPresenter = ListPresenter()


            listPresenter.repositoryList(context!!,page,object:ICallBackRepoList{
                override fun OnSuccess(list: List<Items>) {

                    progress_bar?.visibility = View.GONE

                    Log.d("data",list.toString())

                    adapter?.add(list)

                }

                override fun OnError(msg: String) {

                    progress_bar?.visibility = View.GONE

                    Toast.makeText(context!!,""+msg,Toast.LENGTH_LONG).show()
                }
            })


        } else {

            progress_bar?.visibility = View.GONE

            Toast.makeText(context!!,"No Internet",Toast.LENGTH_LONG).show()

        }
    }
}