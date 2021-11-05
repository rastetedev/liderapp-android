package com.liderman.mundolidermanapp.presentation.ui.activity

import android.annotation.SuppressLint
import android.view.View
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.borrow.BorrowEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.presenter.LoanPresenter
import com.liderman.mundolidermanapp.presentation.ui.adapter.LoanAdapter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_loan.*

import javax.inject.Inject

class LoansActivity : LiderManBaseActivity(), LoanPresenter.View {
    val list: MutableList<BorrowEntity> = mutableListOf()
    private lateinit var adapter: LoanAdapter

    override fun getView(): Int = R.layout.activity_loan

    @Inject
    lateinit var loanPresenter: LoanPresenter

    @SuppressLint("SetTextI18n")
    override fun onCreate() {
        super.onCreate()
        setSupportActionBar("Préstamos", R.color.white)
        component.inject(this)

        loanPresenter.getLoans()

        lider_boy.text = "Hola, ${PapersManager.loginEntity.names.split(" ")[0]}"
    }

    override fun onResume() {
        super.onResume()
        loanPresenter.attachView(this)
    }

    override fun successLoanPresenter(loans: List<BorrowEntity>) {

        if(loans.isNotEmpty()) {
            recycler?.visibility = View.VISIBLE
            lbl_no_loans?.visibility = View.GONE

            list.clear()

            loans.forEach {
                list.add(
                    it
                )
            }
            progress_total.text = "ESTADO ACTUAL: ${list.size}"
            adapter = LoanAdapter()
            recycler.removeAllViews()
            recycler.removeAllViewsInLayout()
            recycler.adapter = adapter
            adapter.data = list
        } else {
            progress_total.text = "ESTADO ACTUAL: 0"
            recycler?.visibility = View.GONE
            lbl_no_loans?.visibility = View.VISIBLE
        }

    }
}