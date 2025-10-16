package jp.ac.neec.it.k023c0024.questionnaire_app.questionnaire.New

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
import androidx.appcompat.app.AppCompatActivity
import jp.ac.neec.it.k023c0024.questionnaire_app.R
import jp.ac.neec.it.k023c0024.questionnaire_app.data.db.DatabaseHelper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Questionnaire : AppCompatActivity() {
    private val _helper = DatabaseHelper(this@Questionnaire)

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

        //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        val db = _helper.readableDatabase
        //主キーによる検索SQL文字列の用意
        val sql = "SELECT * FROM customer WHERE _id = ?"
        //SQLの実行
        val cursor = db.rawQuery(sql, arrayOf(id))
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
        val tvKana = findViewById<TextView>(R.id.tvQuestionnairePutKana)
        tvKana.text = kana
        val tvName = findViewById<TextView>(R.id.tvQuestionnairePutName)
        tvName.text = name
        val tvOld = findViewById<TextView>(R.id.tvQuestionnairePutOld)
        tvOld.text = old.toString()
        val tvRole = findViewById<TextView>(R.id.tvQuestionnairePutRole)
        tvRole.text = role

        val bt_QuestionnaireNext = findViewById<Button>(R.id.btQuestionnaireNext)
        bt_QuestionnaireNext.setOnClickListener(ClickQuestionnaireNextListener())

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

            val time = findViewById<TextView>(R.id.tvQuestionnaireNowDayTime).text.toString()


            val ClickQuestionnaireNextListener =
                Intent(this@Questionnaire, Questionnaire2::class.java)

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