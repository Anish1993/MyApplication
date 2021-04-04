package com.example.myapplication.Network

import com.example.myapplication.List.Model.RepoModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BaseService {

//    https://api.github.com/search/repositories?q=tetris+language:assembly&sort=stars&order=desc&page=2&per_page=10

    @GET("repositories")
    fun getRepoList(): Observable<List<RepoModel>>

//    @GET("search/repositories?q=tetris+language:assembly&sort=stars&order=desc&page={page_val}&per_page=10")
//    fun getRepoListNew(
//
//        @Query("page_val") page_val: String
//
//    ): Observable<RepoModel>
//
    @GET("search/repositories")
    fun getRepoListNew(

        @Query("q") q: String ="tetris+language:assembly",
        @Query("page") page: String,
        @Query("per_page") per_page: String = "15",


    ): Observable<RepoModel>
}