package jp.ac.neec.it.k023c0024.questionnaire_app

import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginCustomer : AppCompatActivity() {
    //データベースヘルパーオブジェクト
    private  val _helper = DatabaseHelper(this@LoginCustomer)

    private var _customerList: MutableList<MutableMap<String, String>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_customer)

        _customerList = createCustomerList()
        val lvCustomer = findViewById<ListView>(R.id.lvCustomer)

        var adapter = SimpleAdapter(
            this@LoginCustomer,
            _customerList,
            android.R.layout.simple_list_item_2,
            arrayOf("id", "name"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        lvCustomer.adapter = adapter
    }

    private fun createCustomerList(): MutableList<MutableMap<String, String>> {
        //既存のリストをクリアする
        _customerList.clear()

        //データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        val db = _helper.readableDatabase
        //主キーにより検索SQL文字列の用意
        val sql = "SELECT * FROM customer"
        //SQLの実行
        val cursor = db.rawQuery(sql, null)
        //データベースから取得した値を格納する変数の用意。データが無かった時のための初期値も用意
        var name = ""
        var id = ""

        //SQL実行の戻り値で圧カーソルオブジェクトをループさせてデータベース内のデータを取得
        while(cursor.moveToNext()){
            //カラムのインデックス値を取得
            val idxid = cursor.getColumnIndex("_id")
            val idxname = cursor.getColumnIndex("name")
            //カラムのインデックス値を元に実際のデータを取得
            id = cursor.getString(idxid)
            name = cursor.getString(idxname)
            //取得したデータをリストに追加
            _customerList.add(mutableMapOf("id" to id, "name" to name))
        }

        //カーソルを閉じる
        cursor.close()

        return _customerList
    }

    override fun onResume() {
        super.onResume()

        //最新のデータベース内容を取得
        _customerList = createCustomerList()

        //リストビューに新しいリストをセットし直す
        val lvCustomer = findViewById<ListView>(R.id.lvCustomer)
        var adapter = SimpleAdapter(
            this@LoginCustomer,
            _customerList,
            android.R.layout.simple_list_item_2,
            arrayOf("id", "name"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        lvCustomer.adapter = adapter

    }
}