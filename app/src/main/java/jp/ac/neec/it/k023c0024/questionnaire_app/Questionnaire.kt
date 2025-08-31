package jp.ac.neec.it.k023c0024.questionnaire_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
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

            val ClickQuestionnaireNextListener = Intent(this@Questionnaire, Questionnaire2::class.java)

            ClickQuestionnaireNextListener.putExtra("id", id)

            startActivity(ClickQuestionnaireNextListener)
        }
    }
}