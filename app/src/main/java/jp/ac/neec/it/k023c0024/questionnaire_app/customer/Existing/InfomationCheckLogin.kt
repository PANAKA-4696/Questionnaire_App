package jp.ac.neec.it.k023c0024.questionnaire_app.customer.Existing

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import jp.ac.neec.it.k023c0024.questionnaire_app.R
import jp.ac.neec.it.k023c0024.questionnaire_app.data.db.DatabaseHelper

class InfomationCheckLogin : AppCompatActivity() {
    //データベースヘルパーオブジェクト
    private  val _helper = DatabaseHelper(this@InfomationCheckLogin)

    private var _customerList: MutableList<MutableMap<String, String>> = mutableListOf()

    //Adapterをメンバー変数にしておく
    private lateinit var _adapter: SimpleAdapter

    //EditTextもメンバー変数に
    private lateinit var etKana: EditText
    private lateinit var etId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infomation_check_login)

        val lvCustomer = findViewById<ListView>(R.id.lvInformationCheckLogin)
        etKana = findViewById(R.id.etInformationCheckLoginKana)
        etId = findViewById(R.id.etInformationCheckLoginID)

        _adapter = SimpleAdapter(
            this@InfomationCheckLogin,
            _customerList,
            android.R.layout.simple_list_item_2,
            arrayOf("id", "name"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        lvCustomer.adapter = _adapter
        lvCustomer.onItemClickListener = ListItemClickListener()

        //検索リスナーの追加
        setupSearchListeners()
        //初期リスト表示
        performSearch()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()

        // 画面に戻ってきたときにリストを再検索して更新
        performSearch()

    }

    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            //_customerListから、タップした行のデータを指定
            val item = _customerList[position]
            //マップから"id"というキーを使って、目的の顧客ID(文字列)を取得
            val customerID = item["id"]

            val intentCustomerInformation = Intent(this@InfomationCheckLogin, CustomerInformationConfirm::class.java)

            intentCustomerInformation.putExtra("id", customerID)

            startActivity(intentCustomerInformation)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        if (item.itemId == android.R.id.home) {
            finish()
            return true // 処理が完了したことを示す
        }
        return super.onOptionsItemSelected(item)
    }

    //EditTextにTextWatcherを設定する関数
    private fun setupSearchListeners(){
        // TextWatcherオブジェクトを作成（中身はafterTextChangedで定義）
        val textWatcher = object : TextWatcher { // android.text.TextWatcher を指定
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                // テキストが変更されたら検索を実行
                performSearch()
            }
        }

        // 各EditTextにリスナーを設定
        etKana.addTextChangedListener(textWatcher)
        etId.addTextChangedListener(textWatcher)
    }

    //現在のEditTextの内容でデータベースを検索し、ListViewを更新する関数
    private fun performSearch() {
        val kanaQuery = etKana.text.toString()
        val idQuery = etId.text.toString()

        // データベースから検索結果を取得
        val searchResult = searchCustomers(kanaQuery, idQuery)

        // ListViewに表示しているリスト(_customerList)の中身を入れ替える
        _customerList.clear()
        _customerList.addAll(searchResult)

        // Adapterにデータが変更されたことを通知してListViewを再描画
        _adapter.notifyDataSetChanged()
    }

    //カナとIDで顧客を検索する関数 (createCustomerListの代わり)
    private fun searchCustomers(kanaQuery: String, idQuery: String): List<MutableMap<String, String>> {
        val resultList: MutableList<MutableMap<String, String>> = mutableListOf()

        // ★ DBアクセスはuseブロックを使うと安全 (自動でcloseされる)
        _helper.readableDatabase.use { db ->
            var selection = ""
            val selectionArgs = mutableListOf<String>()

            if (kanaQuery.isNotEmpty()) {
                selection += "kana LIKE ?"
                selectionArgs.add("%$kanaQuery%")
            }
            if (idQuery.isNotEmpty()) {
                if (selection.isNotEmpty()) {
                    selection += " AND "
                }
                // ★ IDは数値なので、文字列ではなく数値として比較する方が確実だが、
                //    今のリスト構造(Map<String, String>)に合わせるため文字列で検索
                selection += "_id = ?"
                selectionArgs.add(idQuery)
            }

            val sql = "SELECT _id, name FROM customer" + if (selection.isNotEmpty()) " WHERE $selection" else ""
            val cursor = db.rawQuery(sql, selectionArgs.toTypedArray())

            cursor.use { // カーソルもuseブロックで自動クローズ
                while (it.moveToNext()) {
                    // getColumnIndexOrThrow を使うとカラムが存在しない場合に例外が発生して安全
                    val id = it.getLong(it.getColumnIndexOrThrow("_id")).toString()
                    val name = it.getString(it.getColumnIndexOrThrow("name"))
                    resultList.add(mutableMapOf("id" to id, "name" to name))
                }
            }
        } // dbはここで自動的にcloseされる
        return resultList
    }
}