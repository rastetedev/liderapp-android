package com.liderman.mundolidermanapp.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.lidernet.LiderNetEntity
import com.liderman.mundolidermanapp.domain.entity.traffic.TrafficEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.utils.linkpewview.MetaDataKotlin
import com.liderman.mundolidermanapp.utils.linkpewview.ProcessUrl
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrl
import com.liderman.mundolidermanapp.presentation.presenter.EventPresenter
import com.liderman.mundolidermanapp.presentation.presenter.LiderNetPresenter
import com.liderman.mundolidermanapp.presentation.presenter.TrafficPresenter
import com.liderman.mundolidermanapp.presentation.ui.activity.YoutubeViewerActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.MainActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.LoansActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.TareoActivity
import com.liderman.mundolidermanapp.presentation.ui.adapter.QualityAdapter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseFragment
import kotlinx.android.synthetic.main.fragment_traffic.*
import javax.inject.Inject

class TrafficFragment : LiderManBaseFragment(), LiderNetPresenter.View, TrafficPresenter.View, EventPresenter.View {

    @Inject
    lateinit var liderNetPresenter: LiderNetPresenter

    @Inject
    lateinit var eventPresenter: EventPresenter

    @Inject
    lateinit var trafficPresenter: TrafficPresenter

    private lateinit var adapter: QualityAdapter

    override fun getFragmentView(): Int = R.layout.fragment_traffic

    override fun onCreate() {
        (activity as MainActivity).setToolbarTitle(getString(R.string.lights))
        component.inject(this)

        liderNetPresenter.attachView(this)
        trafficPresenter.attachView(this)
        eventPresenter.attachView(this)
        trafficPresenter.getTraffic()
        liderNetPresenter.getLiderNet()

        lider_boy.text = getString(R.string.user_greeting, PapersManager.loginEntity.names)

        adapter = QualityAdapter()
        recycler.removeAllViews()
        recycler.removeAllViewsInLayout()
        recycler.adapter = adapter
        adapter.data = PapersManager.listTraffic

        btn_bill?.setOnClickListener {
            val analytic = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString("zona", PapersManager.loginEntity.zona)
            bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
            bundle.putString("genero", PapersManager.loginEntity.sexo)
            bundle.putString("edad", PapersManager.loginEntity.edad)
            bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
            analytic.logEvent("InitPrestamos", bundle)
            eventPresenter.sendEvent("InitPrestamos")
            startActivityE(LoansActivity::class.java)
        }

        btn_tareo?.setOnClickListener {
            val analytic = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString("zona", PapersManager.loginEntity.zona)
            bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
            bundle.putString("genero", PapersManager.loginEntity.sexo)
            bundle.putString("edad", PapersManager.loginEntity.edad)
            bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
            analytic.logEvent("InitTareo", bundle)
            eventPresenter.sendEvent("InitTareo")
            startActivityE(TareoActivity::class.java)
        }

        btn_esl?.setOnClickListener {
            val analytic = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString("zona", PapersManager.loginEntity.zona)
            bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
            bundle.putString("genero", PapersManager.loginEntity.sexo)
            bundle.putString("edad", PapersManager.loginEntity.edad)
            bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
            analytic.logEvent("InitESL", bundle)
            eventPresenter.sendEvent("InitESL")
            startActivityUrl("https://escuelaliderman.com/Vistas/SysAdmin/Login.html?token_cloudtec=${PapersManager.loginEntity.token}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        liderNetPresenter.detachView()
        trafficPresenter.detachView()
        eventPresenter.detachView()
    }

    override fun successLiderNet(lidernet: LiderNetEntity) {

        ProcessUrl(object : ProcessUrl.DoStuff {
            override fun getContext() = context
            override fun done(result: MetaDataKotlin) {
                try {
                    when (result.typeError) {
                        0 -> {
                            Glide.with(context)
                                .load(result.imageUrl)
                                .into(image_url_video)
                        }
                    }
                } catch (e: Exception) {
                    //
                }
            }

        }).execute(lidernet.lidernet.mainVideoUrl)

        btn_lidernet?.setOnClickListener {
            if (!lidernet.answered) {
                val analytic = FirebaseAnalytics.getInstance(context)
                val bundle = Bundle()
                bundle.putString("zona", PapersManager.loginEntity.zona)
                bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
                bundle.putString("genero", PapersManager.loginEntity.sexo)
                bundle.putString("edad", PapersManager.loginEntity.edad)
                bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
                analytic.logEvent("InitLidernet", bundle)
                eventPresenter.sendEvent("InitLidernet")
                val intent = Intent(context, YoutubeViewerActivity::class.java)
                intent.putExtra("extra0", lidernet)
                startActivity(intent)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.lidernet_answered),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun successTrafficPresenter(trafficEntity: TrafficEntity) {
        val list = PapersManager.listTraffic
        for (t in list) {
            when (t.id) {
                0 -> t.isGood = trafficEntity.isOnLidernet == "1"
                1 -> t.isGood = trafficEntity.isOnTasks == "1"
                2 -> t.isGood = trafficEntity.isOnBorrows == "1"
                3 -> t.isGood = trafficEntity.is_on_valor == "1"
                4 -> t.isGood = trafficEntity.isOnEsl == "3"
            }
        }
        PapersManager.listTraffic = list
        adapter.data = list
        adapter.notifyDataSetChanged()
    }
}
