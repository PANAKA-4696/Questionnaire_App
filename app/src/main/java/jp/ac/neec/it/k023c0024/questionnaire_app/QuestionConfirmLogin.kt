package jp.ac.neec.it.k023c0024.questionnaire_app

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuestionConfirmLogin : AppCompatActivity() {
    private val _helper = DatabaseHelper(this@QuestionConfirmLogin)

    private val _questionList: MutableList<MutableMap<String, String>> = mutableListOf()
    //Adapterをメンバー変数として保持
    private lateinit var _adapter: SimpleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_confirm_login)

        val lvQuestion = findViewById<ListView>(R.id.lvQuestionConfirmLogin)

        //Adapterをここで初期化
        _adapter = SimpleAdapter(
            this@QuestionConfirmLogin,
            _questionList,
            android.R.layout.simple_list_item_2,
            arrayOf("id", "date"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        lvQuestion.adapter = _adapter
        lvQuestion.onItemClickListener = ListItemClickListener()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun createQuestionList(): MutableList<MutableMap<String, String>> {
        //既存のリストをクリアする
        _questionList.clear()

        //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        val db = _helper.readableDatabase
        //主キー二より検索SQL文字列の用意
        val sql = "SELECT * FROM questionnaire"
        //SQLの実行
        val cursor = db.rawQuery(sql, null)
        //データベースから取得した値を格納する変数の用意。データが無かった時のための初期値も用意
        var date = ""
        var id = ""

        //SQL実行の戻り値でカーソルオブジェクトをループさせてデータベース内のデータを取得
        while(cursor.moveToNext()){
            //カラムのインデックス値を取得
            val idxid = cursor.getColumnIndex("_id")
            val idxdate = cursor.getColumnIndex("time")
            //カラムのインデックス値を元に実際のデータを取得
            id = cursor.getLong(idxid).toString()
            date = cursor.getString(idxdate)
            //取得したデータをリストに追加
            _questionList.add(mutableMapOf("id" to id, "date" to date))
        }

        //カーソルを閉じる
        cursor.close()

        return _questionList
    }

    override fun onResume() {
        super.onResume()
        // データを再読み込み
        createQuestionList()
        // ★改善点：Adapterにデータの変更を通知する
        _adapter.notifyDataSetChanged()
    }

    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            //_questionListから、タップした行のデータを指定
            val item = _questionList[position]
            //マップから"id"というキーを使って、目的の顧客ID(文字列)を取得
            val questionID = item["id",]
            val date = item["date"]

            val intentQuestionConfirm = Intent(this@QuestionConfirmLogin, QuestionConfirm::class.java)

            intentQuestionConfirm.putExtra("date", date)
            intentQuestionConfirm.putExtra("id", questionID)

            startActivity(intentQuestionConfirm)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var returnVal = true
        if (item.itemId == android.R.id.home) {
            finish()
        } else {
            returnVal = super.onOptionsItemSelected(item)
        }
        return returnVal
    }
}