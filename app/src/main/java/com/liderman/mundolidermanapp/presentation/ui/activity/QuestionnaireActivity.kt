package com.liderman.mundolidermanapp.presentation.ui.activity

import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.hk.kbottomnavigation.verticalstepperform.VerticalStepperFormLayout
import com.hk.kbottomnavigation.verticalstepperform.interfaces.VerticalStepperForm
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.lidernet.LiderNetEntity
import com.liderman.mundolidermanapp.data.request.LiderNetRequest
import com.liderman.mundolidermanapp.domain.entity.lidernet.AnswerLidernetEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.extensions.inflate
import com.liderman.mundolidermanapp.presentation.extensions.toInt
import com.liderman.mundolidermanapp.presentation.presenter.LiderNetPresenter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_questionnaire.*
import javax.inject.Inject

class QuestionnaireActivity : LiderManBaseActivity(), VerticalStepperForm, LiderNetPresenter.View {

    @Inject
    lateinit var liderNetPresenter: LiderNetPresenter
    private lateinit var liderNetEntity: LiderNetEntity

    private val options = mutableListOf<LinearLayout>()

    override fun getView(): Int = R.layout.activity_questionnaire

    override fun onCreate() {
        super.onCreate()
        setSupportActionBar("LiderNet", R.color.white)
        component.inject(this)

        liderNetEntity = intent.getSerializableExtra("extra0") as LiderNetEntity

        stepper.visibility = View.GONE
        addItem()
        yes.setOnClickListener {
            first.visibility = View.GONE
            questions.visibility = View.VISIBLE
            val titles = liderNetEntity.lidernetQuestions.map { it.question }

            val colorPrimary = ContextCompat.getColor(this, R.color.colorPrimary2)
            val colorPrimaryDark = ContextCompat.getColor(this, R.color.black)

            stepper.visibility = View.VISIBLE
            VerticalStepperFormLayout.Builder.newInstance(
                stepper,
                titles.toTypedArray(),
                this,
                this
            )
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(false)
                .init()
        }
    }

    override fun onResume() {
        liderNetPresenter.attachView(this)
        super.onResume()
    }

    override fun onPause() {
        liderNetPresenter.detachView()
        super.onPause()
    }

    private fun addItem() {
    }

    override fun createStepContentView(stepNumber: Int): View {
        val view = createSingleChoiceView(stepNumber)
        options.add(view)
        return view
    }

    override fun onStepOpening(stepNumber: Int) {
        checkOptionView(stepNumber)
    }

    @Suppress("UNCHECKED_CAST")
    override fun sendData() {
        val answers = arrayListOf<AnswerLidernetEntity>()
        for (index in liderNetEntity.lidernetQuestions.indices) {
            answers.add(
                AnswerLidernetEntity(
                    (options[index].tag as Map<String, Any>)["id"].toInt(),
                    (options[index].tag as Map<String, Any>)["index"].toInt()
                )
            )
        }

        val request = LiderNetRequest(
            PapersManager.loginEntity.userId ,
            liderNetEntity.lidernet.id,
            answers
        )
        liderNetPresenter.responseLiderNet(request)
        //finish()
    }

    private fun createSingleChoiceView(position: Int): LinearLayout {
        val content = LinearLayout(this)
        content.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        content.orientation = LinearLayout.VERTICAL

        val radios = mutableListOf<RadioButton>()

        val question = liderNetEntity.lidernetQuestions[position]

        val list = mutableListOf<String>()
        if (!question.answerOne.isNullOrEmpty()) list.add(question.answerOne)
        if (!question.answerTwo.isNullOrEmpty()) list.add(question.answerTwo)
        if (!question.answerThree.isNullOrEmpty()) list.add(question.answerThree)
        if (!question.answerFour.isNullOrEmpty()) list.add(question.answerFour)

        for (index in list.indices) {
            val item = list[index]

            val itemView = content.inflate(R.layout.item_question) as LinearLayout

            val itemTitle = itemView.findViewById(R.id.title) as TextView
            val itemRadio = itemView.findViewById(R.id.radio) as RadioButton
            itemTitle.textSize = 18F

            radios.add(itemRadio)
            itemTitle.text = item

            itemView.setOnClickListener {
                for (radio in radios) radio.isChecked = false
                itemRadio.isChecked = true
                content.tag = mapOf(
                    "id" to question.id,
                    "index" to index
                )
                checkOptionView(position)
            }

            content.addView(itemView)
        }

        return content
    }

    private fun checkOptionView(position: Int) {
        if (position < options.size) {
            if (options[position].tag != null) {
                stepper.setActiveStepAsCompleted()
            }
        }
    }

    override fun successLiderNet(lidernet: LiderNetEntity) {
        finish()
    }
}
