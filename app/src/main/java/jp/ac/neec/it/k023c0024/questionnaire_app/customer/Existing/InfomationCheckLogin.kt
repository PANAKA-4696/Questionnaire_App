package jp.ac.neec.it.k023c0024.questionnaire_app.customer.Existing

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import jp.ac.neec.it.k023c0024.questionnaire_app.R
import jp.ac.neec.it.k023c0024.questionnaire_app.data.db.DatabaseHelper

class InfomationCheckLogin : AppCompatActivity() {
    //データベースヘルパーオブジェクト
    private  val _helper = DatabaseHelper(this@InfomationCheckLogin)

    private var _customerList: MutableList<MutableMap<String, String>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infomation_check_login)

        _customerList = createCustomerList()
        val lvCustomer = findViewById<ListView>(R.id.lvInformationCheckLogin)

        var adapter = SimpleAdapter(
            this@InfomationCheckLogin,
            _customerList,
            android.R.layout.simple_list_item_2,
            arrayOf("id", "name"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        lvCustomer.adapter = adapter
        lvCustomer.onItemClickListener = ListItemClickListener()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        val lvCustomer = findViewById<ListView>(R.id.lvInformationCheckLogin)
        var adapter = SimpleAdapter(
            this@InfomationCheckLogin,
            _customerList,
            android.R.layout.simple_list_item_2,
            arrayOf("id", "name"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        lvCustomer.adapter = adapter

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
            //現在の画面を終了
            finish()
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