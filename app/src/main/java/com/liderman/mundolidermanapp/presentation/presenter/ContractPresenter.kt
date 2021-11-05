package com.liderman.mundolidermanapp.presentation.presenter

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.contract.ContractEntity
import com.liderman.mundolidermanapp.domain.useCase.GetContract
import com.liderman.mundolidermanapp.domain.useCase.UpdateContract
import com.liderman.mundolidermanapp.utils.Methods
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.presenter.base.BasePresenter
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

class ContractPresenter(
    private var getContract: GetContract,
    private var updateContract: UpdateContract,
    private var methods: Methods
) :
    BasePresenter<ContractPresenter.View>() {

    fun getContract() {
        if (!methods.isInternetConnected()) return
        view?.showLoading()

        getContract.execute(object : DisposableObserver<ContractEntity?>() {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onNext(t: ContractEntity) {
                view?.hideLoading()
                PapersManager.contractEntity = t
                view?.successContract(t)
            }

            override fun onError(e: Throwable) {
                view?.hideLoading()
                when (e) {
                    is HttpException -> {
                        when {
                            e.code() == 410 -> view?.showError(
                                view?.getContext()!!.getString(R.string.error_contract_load)
                            )
                            e.code() == 500 -> view?.showError(
                                view?.getContext()!!.getString(R.string.service_error)
                            )
                        }
                    }
                    else -> view?.showError(
                        view?.getContext()!!.getString(R.string.unknown_error)
                    )
                }
            }

        })
    }

    fun updateContractState(id: Int, state: Int) {
        if (!methods.isInternetConnected()) return

        updateContract.apply {
            this.id = id
            this.state = state
        }.run {
            execute(object : DisposableObserver<ContractEntity?>() {
                override fun onComplete() {
                    //
                }

                override fun onNext(t: ContractEntity) {
                    PapersManager.contractEntity = t
                    view?.successUpdate(t)
                }

                override fun onError(e: Throwable) {
                    when (e) {
                        is HttpException -> {
                            when {
                                e.code() == 410 -> view?.showError(
                                    view?.getContext()!!.getString(R.string.try_error)
                                )
                                e.code() == 500 -> view?.showError(
                                    view?.getContext()!!.getString(R.string.service_error)
                                )
                            }
                        }
                        else -> view?.showError(
                            view?.getContext()!!.getString(R.string.unknown_error)
                        )
                    }
                }
            })
        }
    }

    interface View : BasePresenter.View {
        fun successContract(contractEntity: ContractEntity)

        fun successUpdate(contractEntity: ContractEntity)
    }
}