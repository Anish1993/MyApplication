package com.example.myapplication.List.Presenter

import android.annotation.SuppressLint
import android.content.Context
import com.example.myapplication.List.CallBack.ICallBackRepoList
import com.example.myapplication.Network.BaseService
import com.example.myapplication.Network.CommonStaticClass
import com.example.myapplication.Network.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException

class ListPresenter {

    @SuppressLint("CheckResult")
    fun repositoryList(
        contextOBJ: Context,
        page:String,
        callBackOBJ: ICallBackRepoList

    ) {
        RetrofitHelper.getRetrofit2(BaseService::class.java)
            .getRepoListNew(page= page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { responseBody ->
                responseBody
            }
            .subscribe({ response ->

                try {

                    callBackOBJ.OnSuccess(response.items)

                } catch (e: Exception) {

                }
            }, { error ->
                try {
                    val errorVal = error as HttpException

                    val jsonError = JSONObject(errorVal.response()!!.errorBody()?.string()!!)
//                    val jsonStatus = jsonError.getJSONObject("status")
                    val jsonMessage = jsonError.getString("message")
//                    val statusCode = jsonStatus.getString("statusCode")

//                    if (errorVal.code() == 401) {
//                        if (statusCode == "ER4000" || statusCode.toInt() == 401) {
//                            callBackOBJ.OnError(""+jsonMessage)
//                        } else {
//                            callBackOBJ.OnError(""+jsonMessage)
//                        }
//
//                    } else {

                        callBackOBJ.OnError(""+jsonMessage)
//                    }
                } catch (exp: Exception) {
                    val commonClass = CommonStaticClass()
                    val errorMessageOBJ = commonClass.commonCatchBlock(exp, contextOBJ)
                    callBackOBJ.OnError(""+errorMessageOBJ)
                }

            })
    }














}