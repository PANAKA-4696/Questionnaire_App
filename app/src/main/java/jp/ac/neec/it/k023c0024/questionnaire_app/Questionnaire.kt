package jp.ac.neec.it.k023c0024.questionnaire_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Questionnaire : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire)

        val bt_QuestionnaireNext = findViewById<Button>(R.id.btQuestionnaireNext)
        bt_QuestionnaireNext.setOnClickListener(ClickQuestionnaireNextListener())
    }

    private inner class ClickQuestionnaireNextListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val ClickQuestionnaireNextListener =
                Intent(this@Questionnaire, LoginCustomer::class.java)
            startActivity(ClickQuestionnaireNextListener)
        }
    }
}