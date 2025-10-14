package jp.ac.neec.it.k023c0024.questionnaire_app

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import jp.ac.neec.it.k023c0024.questionnaire_app.data.db.DatabaseHelper

class ReQuestionnaire2 : AppCompatActivity() {
    private val _helper = DatabaseHelper(this@ReQuestionnaire2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_re_questionnaire2)

        val bt_re_questionnaire_confirm = findViewById<Button>(R.id.btReQuestionnaireConfirm)
        bt_re_questionnaire_confirm.setOnClickListener(ReQuestionnaireConfirmUpdateListener())

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private inner class ReQuestionnaireConfirmUpdateListener : View.OnClickListener{
        override fun onClick(v: View?) {
            val cb_requestionnaire2 = findViewById<CheckBox>(R.id.cbReQuestionnaire2)
            if (cb_requestionnaire2.isChecked) {

                val timeToUpdate = intent.getStringExtra("time")
                val idToUpdate = intent.getStringExtra("id")

                //DBに保存するロジックは変更なし
                val Question1 = intent.getStringExtra("Question1")
                val Question2 = intent.getStringExtra("Question2")
                val Question3 = intent.getStringExtra("Question3")
                val Question4 = intent.getStringExtra("Question4")
                val Question5 = intent.getStringExtra("Question5")
                val Question6 = intent.getStringExtra("Question6")
                val etComment = intent.getStringExtra("etComment")

                val db = _helper.writableDatabase

                try{
                    if (timeToUpdate != null && idToUpdate != null && Question1 != null && Question2 != null && Question3 != null && Question4 != null && Question5 != null && Question6 != null){

                        val sqlUpdate = "UPDATE questionnaire SET Question1 = ?, Question2 = ?, Question3 = ?, Question4 = ?, Question5 = ?, Question6 = ?, etComment = ? WHERE time = ? AND _id = ?"
                        val stmt = db.compileStatement(sqlUpdate)

                        stmt.bindString(1, Question1)
                        stmt.bindString(2, Question2)
                        stmt.bindString(3, Question3)
                        stmt.bindString(4, Question4)
                        stmt.bindString(5, Question5)
                        stmt.bindString(6, Question6)
                        stmt.bindString(7, etComment)
                        stmt.bindString(8, timeToUpdate)
                        stmt.bindString(9, idToUpdate)

                        //UPDATEを実行し、影響のあった行数を取得
                        val affectedRows = stmt.executeUpdateDelete()

                        if(affectedRows > 0){
                            Toast.makeText(this@ReQuestionnaire2, "更新しました", Toast.LENGTH_LONG).show()
                            finish()
                        }else{
                            Toast.makeText(this@ReQuestionnaire2, "更新に失敗しました", Toast.LENGTH_LONG).show()
                        }
                    }else{
                        Toast.makeText(this@ReQuestionnaire2, "更新に失敗しました", Toast.LENGTH_LONG).show()
                    }

                }finally {
                    db.close()
                }
            }else{
                Toast.makeText(this@ReQuestionnaire2, "同意が必要です", Toast.LENGTH_LONG).show()
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