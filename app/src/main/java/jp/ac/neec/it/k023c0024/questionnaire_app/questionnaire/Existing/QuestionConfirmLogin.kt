package jp.ac.neec.it.k023c0024.questionnaire_app.questionnaire.Existing

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import jp.ac.neec.it.k023c0024.questionnaire_app.R
import jp.ac.neec.it.k023c0024.questionnaire_app.data.db.DatabaseHelper

class QuestionConfirmLogin : AppCompatActivity() {
    private val _helper = DatabaseHelper(this@QuestionConfirmLogin)

    private val _questionList: MutableList<MutableMap<String, String>> = mutableListOf()
    //Adapterをメンバー変数として保持
    private lateinit var _adapter: SimpleAdapter
    //日付検索用のEditTextをメンバー変数に追加
    private lateinit var etDate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_confirm_login)

        val lvQuestion = findViewById<ListView>(R.id.lvQuestionConfirmLogin)
        etDate = findViewById(R.id.etQuestionLoginDate)

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

        //検索リスナーと初期リスト表示を追加
        setupSearchListeners()
        performSearch()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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