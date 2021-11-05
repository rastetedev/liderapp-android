package com.liderman.mundolidermanapp.presentation.ui.activity

import android.annotation.SuppressLint
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.amapay.AmaPayEntity
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_ama_pay.*
import java.text.SimpleDateFormat
import java.util.*

class AmaPayActivity : LiderManBaseActivity() {
    private lateinit var listAmaPay: ArrayList<AmaPayEntity>
    private var listAmaPayc: ArrayList<Calendar> = arrayListOf()
    private val events: MutableList<EventDay> = ArrayList()
    private var monthNames = arrayOf(
        "Ene",
        "Feb",
        "Mar",
        "Abr",
        "May",
        "Jun",
        "Jul",
        "Ago",
        "Sep",
        "Oct",
        "Nov",
        "Dic"
    )

    var current: Int = 0

    override fun getView(): Int = R.layout.activity_ama_pay

    @Suppress("UNCHECKED_CAST")
    override fun onCreate() {
        setSupportActionBar(resources.getString(R.string.ama_pago), R.color.white)
        listAmaPay = intent.getSerializableExtra("extra0") as ArrayList<AmaPayEntity>
        setCalendar()
        listAmaPay.forEach {
            events.add(EventDay(it.getCalendarDate(), R.drawable.ic_cash, R.color.text_color))
            listAmaPayc.add(it.getCalendarDate())
        }
        calendarView.setEvents(events)
        calendarView.setOnPreviousPageChangeListener(object : OnCalendarPageChangeListener {
            override fun onChange() {
                current -= 1
                getDayStarEndMonth(current)
            }
        })

        calendarView.setOnForwardPageChangeListener(object : OnCalendarPageChangeListener {
            override fun onChange() {
                current += 1
                getDayStarEndMonth(current)
            }
        })
        getDayStarEndMonth(current)
        super.onCreate()
    }

    @SuppressLint("SetTextI18n")
    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun getDayStarEndMonth(c: Int) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, c)
        val cMin = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        val cMax = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val dateMin = SimpleDateFormat(
            "yyyy-MM-dd hh:mm:ss",
            Locale.US
        ).parse("${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-$cMin 00:00:00")
        val dateMax = SimpleDateFormat(
            "yyyy-MM-dd hh:mm:ss",
            Locale.US
        ).parse("${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-$cMax 23:59:59")
        linear_days_pay.removeAllViews()

        listAmaPay.forEach {
            if (dateMin.time <= it.date.time ?: 0 && dateMax.time >= it.date.time ?: 0) {
                val txt: TextView = TextView(this)
                val day = it.getCalendarDate().get(Calendar.DAY_OF_MONTH)
                val dayString = if (day.toString().length == 1) "0$day" else day
                txt.apply {
                    text =
                        "$dayString ${
                            monthNames[it.getCalendarDate().get(Calendar.MONTH)]
                        } - ${it.event}"
                    this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_cash, 0, 0, 0)
                    this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
                    this.compoundDrawablePadding = 8
                    this.setPadding(0, 0, 0, 8)
                }
                linear_days_pay.addView(txt)
            }
        }
    }

    private fun setCalendar() {
        val color = ContextCompat.getColor(this, R.color.black)
        val drawable = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left)
        val drawable2 = ContextCompat.getDrawable(this, R.drawable.ic_arrow_right)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable?.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
            drawable2?.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            drawable?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            drawable2?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }

        calendarView.setPreviousButtonImage(drawable!!)
        calendarView.setForwardButtonImage(drawable2!!)
    }
}
