package jp.ac.neec.it.k023c0024.questionnaire_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Questionnaire : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire)

        val id = intent.getStringExtra("id")

        val tvTime = findViewById<TextView>(R.id.tvQuestionnaireNowDayTime)
        //現在時刻を取得
        val now = LocalDateTime.now()
        //フォーマットを定義
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH時")
        //フォーマットを適用
        val formattedDateTime = now.format(formatter)

        tvTime.text = formattedDateTime

        val bt_QuestionnaireNext = findViewById<Button>(R.id.btQuestionnaireNext)
        bt_QuestionnaireNext.setOnClickListener(ClickQuestionnaireNextListener())
    }

    private inner class ClickQuestionnaireNextListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val id = intent.getStringExtra("id")

            val Question1 = findViewById<EditText>(R.id.etQuestionnaireQuestion1).text.toString()
            val Question2 = findViewById<EditText>(R.id.etQuestionnaireQuestion2).text.toString()
            val Question3 = findViewById<EditText>(R.id.etQuestionnaireQuestion3).text.toString()
            val Question4 = findViewById<EditText>(R.id.etQuestionnaireQuestion4).text.toString()
            val Question5 = findViewById<EditText>(R.id.etQuestionnaireQuestion5).text.toString()

            val rgQuestion6 = findViewById<RadioGroup>(R.id.rgQuestionnaireQuestion6)
            val question6Id = rgQuestion6.checkedRadioButtonId
            val Question6 = findViewById<RadioButton>(question6Id).text.toString()

            val etComment = findViewById<EditText>(R.id.etQuestionnaireComment).text.toString()

            val ClickQuestionnaireNextListener = Intent(this@Questionnaire, Questionnaire2::class.java)

            ClickQuestionnaireNextListener.putExtra("id", id)
            ClickQuestionnaireNextListener.putExtra("Question1", Question1)
            ClickQuestionnaireNextListener.putExtra("Question2", Question2)
            ClickQuestionnaireNextListener.putExtra("Question3", Question3)
            ClickQuestionnaireNextListener.putExtra("Question4", Question4)
            ClickQuestionnaireNextListener.putExtra("Question5", Question5)
            ClickQuestionnaireNextListener.putExtra("Question6", Question6)
            ClickQuestionnaireNextListener.putExtra("etComment", etComment)

            startActivity(ClickQuestionnaireNextListener)
        }
    }
}