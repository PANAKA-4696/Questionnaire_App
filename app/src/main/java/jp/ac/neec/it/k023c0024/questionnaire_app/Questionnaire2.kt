package jp.ac.neec.it.k023c0024.questionnaire_app

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Questionnaire2 : AppCompatActivity() {

    private val _helper = DatabaseHelper(this@Questionnaire2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire2)

        val bt_questionnaire_confirm = findViewById<Button>(R.id.btQuestionnaireConfirm)
        bt_questionnaire_confirm.setOnClickListener(QuestionnaireConfirmListener())

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private inner class QuestionnaireConfirmListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val cb_questionnaire2 = findViewById<CheckBox>(R.id.cbQuestionnaire2)
            if (cb_questionnaire2.isChecked) {

                val db = _helper.writableDatabase
                val time = intent.getStringExtra("time")
                val id = intent.getStringExtra("id")
                val Question1 = intent.getStringExtra("Question1")
                val Question2 = intent.getStringExtra("Question2")
                val Question3 = intent.getStringExtra("Question3")
                val Question4 = intent.getStringExtra("Question4")
                val Question5 = intent.getStringExtra("Question5")
                val Question6 = intent.getStringExtra("Question6")
                val etComment = intent.getStringExtra("etComment")

                try {
                    if (time != null && id != null && Question1 != null && Question2 != null && Question3 != null && Question4 != null && Question5 != null && Question6 != null && etComment != null) {
                        val sqlInsert =
                            "INSERT INTO questionnaire (time, id, Question1, Question2, Question3, Question4, Question5, Question6, etComment) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
                        val stmt = db.compileStatement(sqlInsert)

                        stmt.bindString(1, time)
                        stmt.bindString(2, id)
                        stmt.bindString(3, Question1)
                        stmt.bindString(4, Question2)
                        stmt.bindString(5, Question3)
                        stmt.bindString(6, Question4)
                        stmt.bindString(7, Question5)
                        stmt.bindString(8, Question6)
                        stmt.bindString(9, etComment)

                        stmt.executeInsert()
                        Toast.makeText(this@Questionnaire2, "回答を提出しました", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@Questionnaire2, "回答に失敗しました", Toast.LENGTH_LONG).show()
                    }
                }finally {
                    db.close()
                }

            }else{
                Toast.makeText(this@Questionnaire2, "同意が必要です", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        //戻り値用の変数を初期値trueで用意
        var returnVal = true
        //選択されたメニューが「戻る」の場合、アクティビティを終了
        if(item.itemId == android.R.id.home){
            finish()
        }else{
            //それ以外の場合、戻り値用の変数をfalseに設定
            returnVal = super.onOptionsItemSelected(item)
        }
        return returnVal
    }
}