package com.liderman.mundolidermanapp.presentation.ui.activity

import android.view.View
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.sgidoc.SgiDocEntity
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrl
import com.liderman.mundolidermanapp.presentation.presenter.SgiDocPresenter
import com.liderman.mundolidermanapp.presentation.ui.adapter.SgiDocAdapter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_sgi_doc.*
import javax.inject.Inject

class SgiDocActivity : LiderManBaseActivity(), SgiDocPresenter.View {

    @Inject
    lateinit var sgiDocPresenter: SgiDocPresenter

    private lateinit var adapter: SgiDocAdapter

    override fun getView(): Int = R.layout.activity_sgi_doc

    override fun onCreate() {
        super.onCreate()
        setSupportActionBar(resources.getString(R.string.sgi_title), R.color.white)
        component.inject(this)
        sgiDocPresenter.attachView(this)
        sgiDocPresenter.getSgiDoc()
        adapter = SgiDocAdapter { loadEntity ->
            startActivityUrl(loadEntity.webRedirectUrl)
        }
        recycler?.adapter = adapter
        adapter.data = mutableListOf()
    }

    override fun successSgiDocs(sgiDocs: List<SgiDocEntity>) {

        if (sgiDocs.isNotEmpty()) {
            recycler?.visibility = View.VISIBLE
            lbl_sgi_empty?.visibility = View.GONE
            adapter.data = sgiDocs
            adapter.notifyDataSetChanged()
        } else {
            recycler?.visibility = View.GONE
            lbl_sgi_empty?.visibility = View.VISIBLE
        }
    }
}