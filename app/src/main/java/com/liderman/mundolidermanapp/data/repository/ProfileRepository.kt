package com.liderman.mundolidermanapp.data.repository

import com.liderman.mundolidermanapp.data.request.UpdateProfileRequest
import com.liderman.mundolidermanapp.data.retrofit.ApiService
import com.liderman.mundolidermanapp.domain.entity.login.LoginEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class ProfileRepository(private var apiService: ApiService) {

    fun profile(): Observable<LoginEntity?> {

        return apiService.getProfile(PapersManager.loginEntity.userId)
            .flatMap {
                Observable.just(it.result)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateProfile(updateRequest: UpdateProfileRequest): Observable<LoginEntity?> {

        return apiService.updateProfile(updateRequest)
            .flatMap {
                Observable.just(it.result)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun uploadImage(filePath: String): Observable<LoginEntity?> {
        val headers = mapOf(
            "Accept" to "*/*"
        )
        val file = File(filePath)

        val body = MultipartBody.Part.createFormData(
            "image",
            file.name,
            RequestBody.create(MediaType.parse("image/*"), file)
        )

        return apiService.uploadImage(headers, body, PapersManager.loginEntity.userId)
            .flatMap {
                Observable.just(it.result)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}