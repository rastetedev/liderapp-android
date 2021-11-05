package com.liderman.mundolidermanapp.data.repository

import com.liderman.mundolidermanapp.data.request.UpdateContractRequest
import com.liderman.mundolidermanapp.data.retrofit.ApiService
import com.liderman.mundolidermanapp.domain.entity.contract.ContractEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContractRepository(private var apiService: ApiService) {

    fun getContract(): Observable<ContractEntity?> {
        return apiService.getContract(
            PapersManager.loginEntity.userId,
            PapersManager.loginEntity.token
        )
            .flatMap {
                Observable.just(it.result)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateContractState(
        id: Int,
        state: Int
    ): Observable<ContractEntity?> {
        return apiService.updateStateContract(
            UpdateContractRequest(
                id,
                PapersManager.loginEntity.userId,
                PapersManager.loginEntity.token,
                state
            )
        )
            .flatMap {
                Observable.just(it.result)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

