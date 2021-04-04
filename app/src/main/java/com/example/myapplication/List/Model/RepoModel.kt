package com.example.myapplication.List.Model

import java.io.Serializable

data class RepoModel(

    var items:List<Items>

):Serializable

data class Items (

    var id:Long,
    var name:String,
    var full_name:String,
    var owner:Owner,
    var description:String,
    var comments:List<String> = mutableListOf()

        ):Serializable


data class Owner(


    var avatar_url:String


):Serializable

