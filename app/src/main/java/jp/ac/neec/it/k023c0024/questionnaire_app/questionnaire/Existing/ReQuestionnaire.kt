package jp.ac.neec.it.k023c0024.questionnaire_app.questionnaire.Existing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import jp.ac.neec.it.k023c0024.questionnaire_app.R
import jp.ac.neec.it.k023c0024.questionnaire_app.data.db.DatabaseHelper
import jp.ac.neec.it.k023c0024.questionnaire_app.questionnaire.New.Questionnaire2

class ReQuestionnaire : AppCompatActivity() {
    val _helper = DatabaseHelper(this@ReQuestionnaire)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_re_questionnaire)

        val id = intent.getStringExtra("id")
        val date = intent.getStringExtra("date")

        val tvTime = findViewById<TextView>(R.id.tvReQuestionnaireNowDayTime)
        tvTime.text = date

        //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        var db = _helper.readableDatabase
        //主キーによる検索SQL文字列の用意
        var sql = "SELECT * FROM customer WHERE _id = ?"
        //SQLの実行
        var cursor = db.rawQuery(sql, arrayOf(id))
        //データベースから取得した値を格納する変数の用意。データが無かった時のための初期値も用意
        var kana = ""
        var name = ""
        var old = 0
        var role = ""

        try {//tryブロックで例外発生に備える
            //カーソルを最初の行に移動させる
            if (cursor.moveToFirst()) {
                //カラムのインデックス値を取得(カラム名が正しいかを確認)
                val idxKana = cursor.getColumnIndex("kana")
                val idxName = cursor.getColumnIndex("name")
                val idxOld = cursor.getColumnIndex("old")
                val idxRole = cursor.getColumnIndex("role")

                //カラムが存在するか確認してからデータを取得(より安全)
                if (idxKana >= 0) {
                    kana = cursor.getString(idxKana)
                }
                if (idxName >= 0) {
                    name = cursor.getString(idxName)
                }
                if (idxOld >= 0) {
                    old = cursor.getInt(idxOld)
                }
                if (idxRole >= 0) {
                    role = cursor.getString(idxRole)
                }
            } else {
                //データが見つからなかった場合の処理
                Log.w("tag", "データが見つかりませんでした")
            }
        } catch (e: Exception) {
            Log.e("tag", "例外が発生しました", e)
            //エラー発生時の処理
        } finally {
            //必ずカーソルをクローズする
            cursor.close()
        }
        //取得した値を画面に表示
        val tvKana = findViewById<TextView>(R.id.tvReQuestionnairePutKana)
        tvKana.text = kana
        val tvName = findViewById<TextView>(R.id.tvReQuestionnairePutName)
        tvName.text = name
        val tvOld = findViewById<TextView>(R.id.tvReQuestionnairePutOld)
        tvOld.text = old.toString()
        val tvRole = findViewById<TextView>(R.id.tvReQuestionnairePutRole)
        tvRole.text = role

        db = _helper.readableDatabase
        sql = "SELECT * FROM questionnaire WHERE _id = ? AND time = ?"

        cursor = db.rawQuery(sql, arrayOf(id,date))

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

        val puQuestion1 = findViewById<EditText>(R.id.etReQuestionnaireQuestion1)
        puQuestion1.setText(question1)
        val puQuestion2 = findViewById<EditText>(R.id.etReQuestionnaireQuestion2)
        puQuestion2.setText(question2)
        val puQuestion3 = findViewById<EditText>(R.id.etReQuestionnaireQuestion3)
        puQuestion3.setText(question3)
        val puQuestion4 = findViewById<EditText>(R.id.etReQuestionnaireQuestion4)
        puQuestion4.setText(question4)
        val puQuestion5 = findViewById<EditText>(R.id.etReQuestionnaireQuestion5)
        puQuestion5.setText(question5)
        val puQuestion6 = findViewById<RadioGroup>(R.id.rgReQuestionnaireQuestion6)
        if(question6 == "ホームぺージ"){
            puQuestion6.check(R.id.rbReQuestionnaireHomepage)
        }
        if(question6 == "口コミ"){
            puQuestion6.check(R.id.rbReQuestionnaireMouth)
        }
        if(question6 == "通りがかり"){
            puQuestion6.check(R.id.rbReQuestionnaireWay)
        }
        if(question6 == "タウンページ"){
            puQuestion6.check(R.id.rbReQuestionnaireTownpage)
        }
        if(question6 == "紹介"){
            puQuestion6.check(R.id.rbReQuestionnaireIntroduce)
        }
        if(question6 == "その他"){
            puQuestion6.check(R.id.rbReQuestionnaireOther)
        }

        val puQuestionComment = findViewById<EditText>(R.id.etReQuestionnaireComment)
        puQuestionComment.setText(questionComment)

        val bt_QuestionnaireNext = findViewById<Button>(R.id.btReQuestionnaireNext)
        bt_QuestionnaireNext.setOnClickListener(ClickQuestionnaireNextListener())

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private inner class ClickQuestionnaireNextListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val id = intent.getStringExtra("id")

            val Question1 = findViewById<EditText>(R.id.etReQuestionnaireQuestion1).text.toString()
            val Question2 = findViewById<EditText>(R.id.etReQuestionnaireQuestion2).text.toString()
            val Question3 = findViewById<EditText>(R.id.etReQuestionnaireQuestion3).text.toString()
            val Question4 = findViewById<EditText>(R.id.etReQuestionnaireQuestion4).text.toString()
            val Question5 = findViewById<EditText>(R.id.etReQuestionnaireQuestion5).text.toString()

            // --- ラジオボタンの安全な取得 ---
            var Question6 = "" // 初期値を設定
            val rgQuestion6 = findViewById<RadioGroup>(R.id.rgReQuestionnaireQuestion6)
            val checkedRadioButtonId = rgQuestion6.checkedRadioButtonId
            if (checkedRadioButtonId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(checkedRadioButtonId)
                Question6 = selectedRadioButton.text.toString()
            } else {
                //エラー表示して return する
                Toast.makeText(this@ReQuestionnaire, "Q6を選択してください", Toast.LENGTH_SHORT).show()
                return
            }

            val etComment = findViewById<EditText>(R.id.etReQuestionnaireComment).text.toString()

            val time = findViewById<TextView>(R.id.tvReQuestionnaireNowDayTime).text.toString()


            val ClickQuestionnaireNextListener = Intent(this@ReQuestionnaire, ReQuestionnaire2::class.java)

            ClickQuestionnaireNextListener.putExtra("time", time)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //戻り値用の変数を初期値trueで用意
        var returnVal = true
        //選択されたメニューが「戻る」の場合、アクティビティを終了
        if (item.itemId == android.R.id.home) {
            finish()
        } else {
            //それ以外の場合、戻り値用の変数をfalseに設定
            returnVal = super.onOptionsItemSelected(item)
        }
        return returnVal
    }
}