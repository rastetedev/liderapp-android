package com.liderman.mundolidermanapp.presentation.ui.activity

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import com.amulyakhare.textdrawable.TextDrawable
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.task.TaskEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.presenter.TareoPresenter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_tareo.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class TareoActivity : LiderManBaseActivity(), TareoPresenter.View {

    var eventsLetters = HashMap<String, Drawable>()

    @Inject
    lateinit var taskPresenter: TareoPresenter

    override fun getView(): Int = R.layout.activity_tareo

    override fun onCreate() {
        super.onCreate()
        setSupportActionBar("Tareo", R.color.white)
        component.inject(this)

        lider_boy.text =
            getString(R.string.user_greeting, PapersManager.loginEntity.names.split(" ")[0])

        taskPresenter.getTasks()

        btn_chat.setOnClickListener {
            startActivityE(ContactTareoActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        taskPresenter.attachView(this)
    }

    override fun successTasks(tasks: List<TaskEntity>) {

        val events: MutableList<EventDay> = ArrayList()

        tasks.forEach { task ->
            val calendar = Calendar.getInstance()
            calendar.time = task.toDate()

            if (!eventsLetters.containsKey(task.event)) {
                val drawable = TextDrawable.builder()
                    .buildRect(task.event, task.color())

                eventsLetters[task.event] = drawable
                events.add(EventDay(calendar, drawable))
            } else {
                val drawable = eventsLetters[task.event]

                events.add(EventDay(calendar, drawable!!))
            }


        }

        calendarView.setEvents(events)

        calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val clickedDayCalendar = eventDay.calendar
                Log.d("event_______", "${clickedDayCalendar.time}")
            }

        })

        val min = Calendar.getInstance()
        min.add(Calendar.MONTH, -11)

        val max = Calendar.getInstance()

        calendarView.setMinimumDate(min)
        calendarView.setMaximumDate(max)

    }
}