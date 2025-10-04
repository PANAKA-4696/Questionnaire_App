package jp.ac.neec.it.k023c0024.questionnaire_app

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log

class QuestionConfirm : AppCompatActivity() {
    private val _helper = DatabaseHelper(this@QuestionConfirm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_confirm)

        val id = intent.getStringExtra("id")
        val date = intent.getStringExtra("date")

        val db = _helper.readableDatabase
        val sql = "SELECT * FROM questionnaire WHERE _id = ? AND time = ?"

        val cursor = db.rawQuery(sql, arrayOf(id,date))

        var question1 = ""
        var question2 = ""
        var question3 = ""
        var question4 = ""
        var question5 = ""
        var question6 = ""
        var questionComment = ""

        try{
            if(cursor.moveToFirst()){
                val idxQuestion1 = cursor.getColumnIndex("Question1")
                val idxQuestion2 = cursor.getColumnIndex("Question2")
                val idxQuestion3 = cursor.getColumnIndex("Question3")
                val idxQuestion4 = cursor.getColumnIndex("Question4")
                val idxQuestion5 = cursor.getColumnIndex("Question5")
                val idxQuestion6 = cursor.getColumnIndex("Question6")
                val idxQuestionComment = cursor.getColumnIndex("etComment")

                if(idxQuestion1 >= 0){
                    question1 = cursor.getString(idxQuestion1)
                }
                if(idxQuestion2 >= 0){
                    question2 = cursor.getString(idxQuestion2)
                }
                if(idxQuestion3 >= 0){
                    question3 = cursor.getString(idxQuestion3)
                }
                if(idxQuestion4 >= 0){
                    question4 = cursor.getString(idxQuestion4)
                }
                if(idxQuestion5 >= 0){
                    question5 = cursor.getString(idxQuestion5)
                }
                if(idxQuestion6 >= 0){
                    question6 = cursor.getString(idxQuestion6)
                }
                if(idxQuestionComment >= 0){
                    questionComment = cursor.getString(idxQuestionComment)
                }
            }else{
                Log.w("tag", "データが見つかりませんでした。")
            }
        }catch (e:Exception){
            Log.e("tag", "例外が発生しました。", e)
        }finally {
            cursor.close()
        }

        val puQuestion1 = findViewById<TextView>(R.id.etQuestionConfirm1)
        puQuestion1.text = question1
        val puQuestion2 = findViewById<TextView>(R.id.etQuestionConfirm2)
        puQuestion2.text = question2
        val puQuestion3 = findViewById<TextView>(R.id.etQuestionConfirm3)
        puQuestion3.text = question3
        val puQuestion4 = findViewById<TextView>(R.id.etQuestionConfirm4)
        puQuestion4.text = question4
        val puQuestion5 = findViewById<TextView>(R.id.etQuestionConfirm5)
        puQuestion5.text = question5
        val puQuestion6 = findViewById<TextView>(R.id.etQuestionConfirm6)
        puQuestion6.text = question6
        val puQuestionComment = findViewById<TextView>(R.id.etQuestionConfirmComment)
        puQuestionComment.text = questionComment
    }
}