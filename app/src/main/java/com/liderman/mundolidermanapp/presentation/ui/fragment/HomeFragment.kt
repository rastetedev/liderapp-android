package com.liderman.mundolidermanapp.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrl
import com.liderman.mundolidermanapp.presentation.presenter.EventPresenter
import com.liderman.mundolidermanapp.presentation.ui.activity.*
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.Serializable
import javax.inject.Inject

class HomeFragment : LiderManBaseFragment(), EventPresenter.View {

    @Inject
    lateinit var eventPresenter: EventPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventPresenter.attachView(this)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        eventPresenter.detachView()
    }

    override fun getFragmentView(): Int = R.layout.fragment_home

    override fun onCreate() {

        (activity as MainActivity).setToolbarTitle(resources.getString(R.string.mundo_liderman))
        component.inject(this)

        lider_boy?.text = getString(R.string.user_greeting, PapersManager.loginEntity.names)

        consult?.setOnClickListener {
            if (PapersManager.contactsEntity.isNotEmpty()) {
                startActivityE(
                    DirectoryActivity::class.java,
                    PapersManager.contactsEntity as Serializable
                )
            } else {
                Toast.makeText(
                    context,
                    resources.getString(R.string.no_contacts),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        btn_announcements?.setOnClickListener {
            if (PapersManager.announcementsEntity.isNotEmpty()) {
                //Firebase
                val analytic = FirebaseAnalytics.getInstance(context)
                val bundle = Bundle()
                bundle.putString("zona", PapersManager.loginEntity.zona)
                bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
                bundle.putString("genero", PapersManager.loginEntity.sexo)
                bundle.putString("edad", PapersManager.loginEntity.edad)
                bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
                analytic.logEvent("InitNovedades", bundle)
                eventPresenter.sendEvent("InitNovedades")
                startActivityE(AnnouncesActivity::class.java)
            } else {
                Toast.makeText(
                    context,
                    resources.getString(R.string.no_announcements),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        btn_sgi_doc?.setOnClickListener {
            startActivityE(SgiDocActivity::class.java)
        }

        btn_liderman?.setOnClickListener {
            startActivityE(LidermaniaActivity::class.java)
        }

        btn_no_acoso?.setOnClickListener {
            startActivityUrl(getString(R.string.no_acoso_link))
        }

        btn_emergency?.setOnClickListener {
            startActivityE(EmergencyActivity::class.java)
        }

        btn_coronavirus?.setOnClickListener {
            startActivityUrl(getString(R.string.coronavirus_link))
        }
    }

}
