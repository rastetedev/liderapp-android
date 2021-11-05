package com.liderman.mundolidermanapp.presentation.ui.application

import android.app.Activity
import android.app.Application
import com.liderman.mundolidermanapp.di.componet.AppComponent
import com.liderman.mundolidermanapp.di.componet.DaggerAppComponent
import com.liderman.mundolidermanapp.di.module.AppModule
import com.liderman.mundolidermanapp.utils.Methods
import io.paperdb.Paper
import java.lang.Exception
import java.util.*

open class LiderManApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
        Methods.init(this)

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var appComponent: AppComponent

        private val activities = Stack<Activity>()

        fun exitsActivities() : Boolean {
            return activities.isEmpty()
        }

        fun addActivity(activity: Activity) {
            activities.add(activity)
        }

        fun removeActivity(activity: Activity) {
            activities.remove(activity)
        }

        fun closeAll() {
            for (activity in activities) {
                try {
                    activity.finish()
                } catch (ignore: Exception) {
                }
            }
            activities.clear()
        }
    }

}