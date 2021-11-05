package com.liderman.mundolidermanapp.data.repository

import com.liderman.mundolidermanapp.data.request.LoginRequest
import com.liderman.mundolidermanapp.data.request.LogoutRequest
import com.liderman.mundolidermanapp.data.retrofit.ApiService
import com.liderman.mundolidermanapp.domain.entity.login.LoginEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable

class AuthRepository(private var apiService: ApiService) {

    fun login(dni: String, password: String): Observable<LoginEntity?> {
        return apiService.signIn(LoginRequest(dni, password))
            .flatMap {
                Observable.just(it.result)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun logout(): Observable<Any> {
        return apiService.signOut(
            LogoutRequest(
                PapersManager.loginEntity.userId,
                PapersManager.loginEntity.token
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}