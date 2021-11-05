package com.liderman.mundolidermanapp.presentation.presenter

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.announcements.AnnouncementEntity
import com.liderman.mundolidermanapp.domain.entity.contact.ContactEntity
import com.liderman.mundolidermanapp.domain.useCase.*
import com.liderman.mundolidermanapp.utils.Methods
import com.liderman.mundolidermanapp.presentation.presenter.base.BasePresenter
import com.liderman.mundolidermanapp.utils.PapersManager
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

class MasterPresenter(
    private var getAnnouncements: GetAnnouncements,
    private var getContactAreas: GetContactAreas,
    private var methods: Methods
) : BasePresenter<MasterPresenter.View>() {

    fun getAnnouncements() {
        if (!methods.isInternetConnected()) return

        getAnnouncements.execute(object : DisposableObserver<List<AnnouncementEntity>?>() {
            override fun onComplete() {
                //
            }

            override fun onNext(t: List<AnnouncementEntity>) {
                PapersManager.announcementsEntity = t
                view?.successAnnouncements(t)
            }

            override fun onError(e: Throwable) {
                when (e) {
                    is HttpException -> {
                        when {
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

    fun getContactAreas() {
        if (!methods.isInternetConnected()) return

        getContactAreas.execute(object : DisposableObserver<List<ContactEntity>?>() {
            override fun onComplete() {
            }

            override fun onNext(t: List<ContactEntity>) {
                PapersManager.contactsEntity = t
            }

            override fun onError(e: Throwable) {
                when (e) {
                    is HttpException -> {
                        when {
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


    interface View : BasePresenter.View {
        fun successAnnouncements(announcements: List<AnnouncementEntity>)
    }
}