package com.example.myapplication.List.CallBack

import com.example.myapplication.List.Model.Items
import com.example.myapplication.List.Model.RepoModel

interface ICallBackRepoList {

    fun OnSuccess(list:List<Items>)
    fun OnError(msg:String)

}