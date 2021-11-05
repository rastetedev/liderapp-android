package com.liderman.mundolidermanapp.presentation.ui.fragment

import android.util.Log
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.presentation.ui.activity.MainActivity
import com.liderman.mundolidermanapp.utils.linkpewview.MetaDataKotlin
import com.liderman.mundolidermanapp.utils.linkpewview.ProcessUrl
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseFragment

class DirectoryFragment : LiderManBaseFragment() {

    override fun getFragmentView(): Int = R.layout.fragment_directory

    override fun onCreate() {
        (activity as MainActivity).setToolbarTitle("Directorio Liderman")

        ProcessUrl(object : ProcessUrl.DoStuff {
            override fun getContext() = context
            override fun done(result: MetaDataKotlin) {
                hideLoading()
                try {
                    when (result.typeError) {
                        0 -> Log.d("play____", "prueba_1")
                        else -> {
                        }
                    }
                } catch (e: Exception) {

                }
            }

        }).execute("https://www.youtube.com/watch?v=VOUz7RV_EGk")

        ProcessUrl(object : ProcessUrl.DoStuff {
            override fun getContext() = context
            override fun done(result: MetaDataKotlin) {
                hideLoading()
                try {
                    when (result.typeError) {
                        0 -> Log.d("play____", "prueba_2")
                        else -> {
                        }
                    }
                } catch (e: Exception) {

                }
            }

        }).execute("https://www.youtube.com/watch?v=VOUz7RV_EGk")

        ProcessUrl(object : ProcessUrl.DoStuff {
            override fun getContext() = context
            override fun done(result: MetaDataKotlin) {
                hideLoading()
                try {
                    when (result.typeError) {
                        0 -> Log.d("play____", "prueba_3")
                        else -> {
                        }
                    }
                } catch (e: Exception) {

                }
            }

        }).execute("https://www.youtube.com/watch?v=VOUz7RV_EGk")

        ProcessUrl(object : ProcessUrl.DoStuff {
            override fun getContext() = context
            override fun done(result: MetaDataKotlin) {
                hideLoading()
                try {
                    when (result.typeError) {
                        0 -> Log.d("play____", "prueba_4")
                        else -> {
                        }
                    }
                } catch (e: Exception) {

                }
            }

        }).execute("https://www.youtube.com/watch?v=VOUz7RV_EGk")

        ProcessUrl(object : ProcessUrl.DoStuff {
            override fun getContext() = context
            override fun done(result: MetaDataKotlin) {
                hideLoading()
                try {
                    when (result.typeError) {
                        0 -> Log.d("play____", "prueba_5")
                        else -> {
                        }
                    }
                } catch (e: Exception) {

                }
            }

        }).execute("https://www.youtube.com/watch?v=VOUz7RV_EGk")
    }

}
