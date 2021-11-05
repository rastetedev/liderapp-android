package com.liderman.mundolidermanapp.presentation.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.lidermanplay.LidermanPlayEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.utils.linkpewview.MetaDataKotlin
import com.liderman.mundolidermanapp.utils.linkpewview.ProcessUrl
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrl
import com.liderman.mundolidermanapp.presentation.presenter.PlayPresenter
import com.liderman.mundolidermanapp.presentation.ui.activity.MainActivity
import com.liderman.mundolidermanapp.presentation.ui.adapter.LiderPlayAdapter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseFragment
import kotlinx.android.synthetic.main.fragment_play.*
import javax.inject.Inject

class PlayFragment : LiderManBaseFragment(), PlayPresenter.View {

    @Inject
    lateinit var playPresenter: PlayPresenter

    private lateinit var adapter: LiderPlayAdapter

    override fun getFragmentView(): Int = R.layout.fragment_play

    override fun onCreate() {
        (activity as MainActivity).setToolbarTitle(getString(R.string.lidermanplay))

        lider_boy.text = getString(R.string.user_greeting, PapersManager.loginEntity.names)
        component.inject(this)
        playPresenter.attachView(this)
        playPresenter.getLidermanPlay()

        adapter = LiderPlayAdapter { loadEntity ->
            startActivityUrl(loadEntity.videoUrl)
        }

        recycler.removeAllViews()
        recycler.removeAllViewsInLayout()
        recycler.adapter = adapter
        adapter.data = mutableListOf()

        btn_twitter?.setOnClickListener {
            startActivityUrl(getString(R.string.twitter_link))
        }

        btn_facebook?.setOnClickListener {
            startActivity(getOpenFacebookIntent())
        }

        btn_linkedin?.setOnClickListener {
            startActivityUrl(getString(R.string.linkedin_link))
        }

        btn_youtube.setOnClickListener {
            startActivityUrl(getString(R.string.youtube_link))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        playPresenter.detachView()
    }

    private fun getOpenFacebookIntent(): Intent? {
        return try {
            context.packageManager.getPackageInfo("com.facebook.katana", 0)
            Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/1355012104618772"))
        } catch (e: java.lang.Exception) {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/LidermanOficial")
            )
        }
    }

    override fun successLidermaPlay(lidermanPlays: List<LidermanPlayEntity>) {
        if (lidermanPlays.isNotEmpty()) {

            lbl_play_empty?.visibility = View.GONE

            var lidermanPlayMaster = LidermanPlayEntity()

            for (lidermanPlay in lidermanPlays) {
                if (lidermanPlay.isMain == 1) {
                    lidermanPlayMaster = lidermanPlay
                }
            }

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
                            else -> {
                            }
                        }
                    } catch (e: Exception) {
                //
                    }
                }

            }).execute(lidermanPlayMaster.videoUrl)
            card_main?.setOnClickListener {
                startActivityUrl(lidermanPlayMaster.videoUrl)
            }

            adapter.data = lidermanPlays
            adapter.notifyDataSetChanged()

        } else {
            lbl_play_empty?.visibility = View.VISIBLE
        }
    }
}
