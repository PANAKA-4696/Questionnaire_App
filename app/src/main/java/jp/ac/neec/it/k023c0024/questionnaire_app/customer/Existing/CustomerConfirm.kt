package jp.ac.neec.it.k023c0024.questionnaire_app.customer.Existing

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import jp.ac.neec.it.k023c0024.questionnaire_app.R
import jp.ac.neec.it.k023c0024.questionnaire_app.data.db.DatabaseHelper

class CustomerConfirm : AppCompatActivity() {
    //データベースヘルパーオブジェクト
    private val _helper = DatabaseHelper(this@CustomerConfirm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_confirm)

        // 汎用的なIDに変更
        val bt_save = findViewById<Button>(R.id.btConfirmUpdate)
        val bt_cancel = findViewById<Button>(R.id.btConfirmCancel)

        bt_save.setOnClickListener(ClickUpdateListener())
        bt_cancel.setOnClickListener(ClickCancelListener())

        val kana = intent.getStringExtra("kana")
        val name = intent.getStringExtra("name")
        val sex = intent.getStringExtra("sex")
        val era = intent.getStringExtra("era")
        val year = intent.getStringExtra("year")
        val month = intent.getStringExtra("month")
        val day = intent.getStringExtra("day")
        val old = intent.getStringExtra("old")
        val zip1 = intent.getStringExtra("zip1")
        val zip2 = intent.getStringExtra("zip2")
        val tel = intent.getStringExtra("tel")
        val address = intent.getStringExtra("address")
        val mail = intent.getStringExtra("mail")
        val role = intent.getStringExtra("role")

        // findViewByIdのIDをすべて汎用的なものに変更
        val puKana = findViewById<TextView>(R.id.tvConfirmKana)
        puKana.text = kana
        val puName = findViewById<TextView>(R.id.tvConfirmName)
        puName.text = name
        val puSex = findViewById<TextView>(R.id.tvConfirmSex)
        puSex.text = sex
        val puEra = findViewById<TextView>(R.id.tvConfirmEra)
        puEra.text = era
        val puYear = findViewById<TextView>(R.id.tvConfirmYear)
        puYear.text = year
        val puMonth = findViewById<TextView>(R.id.tvConfirmMonth)
        puMonth.text = month
        val puDay = findViewById<TextView>(R.id.tvConfirmDay)
        puDay.text = day
        val puOld = findViewById<TextView>(R.id.tvConfirmOld)
        puOld.text = old
        val puZip1 = findViewById<TextView>(R.id.tvConfirmZip1)
        puZip1.text = zip1
        val puZip2 = findViewById<TextView>(R.id.tvConfirmZip2)
        puZip2.text = zip2
        val puTel = findViewById<TextView>(R.id.tvConfirmTel)
        puTel.text = tel
        val puAddress = findViewById<TextView>(R.id.tvConfirmAddress)
        puAddress.text = address
        val puMail = findViewById<TextView>(R.id.tvConfirmMail)
        puMail.text = mail
        val puRole = findViewById<TextView>(R.id.tvConfirmRole)
        puRole.text = role

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private inner class ClickUpdateListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val idToUpdate = intent.getStringExtra("id")

            //DBに保存するロジックは変更なし
            val kana = intent.getStringExtra("kana")
            val name = intent.getStringExtra("name")
            val sex = intent.getStringExtra("sex")
            val era = intent.getStringExtra("era")
            val year = intent.getStringExtra("year")?.toIntOrNull()
            val month = intent.getStringExtra("month")?.toIntOrNull()
            val day = intent.getStringExtra("day")?.toIntOrNull()
            val old = intent.getStringExtra("old")?.toIntOrNull()
            val zip1 = intent.getStringExtra("zip1")?.toIntOrNull()
            val zip2 = intent.getStringExtra("zip2")?.toIntOrNull()
            val address = intent.getStringExtra("address")
            val tel = intent.getStringExtra("tel")
            val mail = intent.getStringExtra("mail")
            val role = intent.getStringExtra("role")

            val db = _helper.writableDatabase

            try {
                if (kana != null && name != null && sex != null && era != null && year != null && month != null && day != null &&
                    old != null && zip1 != null && zip2 != null && tel != null && address != null && mail != null && role != null && idToUpdate != null
                ) {
                    val sqlUpdate =
                        "UPDATE customer SET kana = ?, name = ?, sex = ?, era = ?, year = ?, month = ?, day = ?, old = ?, zip1 = ?, zip2 = ?, tel = ?, address = ?, mail = ?, role = ? WHERE _id = ?"
                    val stmt = db.compileStatement(sqlUpdate)
                    stmt.bindString(1, kana)
                    stmt.bindString(2, name)
                    stmt.bindString(3, sex)
                    stmt.bindString(4, era)
                    stmt.bindLong(5, year.toLong())
                    stmt.bindLong(6, month.toLong())
                    stmt.bindLong(7, day.toLong())
                    stmt.bindLong(8, old.toLong())
                    stmt.bindLong(9, zip1.toLong())
                    stmt.bindLong(10, zip2.toLong())
                    stmt.bindString(11, tel)
                    stmt.bindString(12, address)
                    stmt.bindString(13, mail)
                    stmt.bindString(14, role)
                    stmt.bindLong(15, idToUpdate.toLong())

                    //UPDATEを実行し、影響のあった行数を取得
                    val affectedRows = stmt.executeUpdateDelete()

                    if (affectedRows > 0) {
                        Toast.makeText(this@CustomerConfirm, "更新しました", Toast.LENGTH_LONG).show()

                        //MainActivityに戻るためのIntentを作成
                        val intent = Intent(this@CustomerConfirm, CustomerInformationConfirm::class.java)
                        //途中の画面(ReEnterCustomerInformationなど)をすべて消去するフラグを設定
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        //どの顧客の画面に戻るのかを伝えるために、IDを渡す
                        intent.putExtra("id", idToUpdate)
                        startActivity(intent)
                        //この画面も閉じる
                        finish()
                    } else {
                        Toast.makeText(this@CustomerConfirm, "更新に失敗しました", Toast.LENGTH_LONG).show()
                    }

                } else {
                    Toast.makeText(this@CustomerConfirm, "更新に失敗しました", Toast.LENGTH_LONG).show()
                }
            } finally {
                db.close()
            }
        }
    }

    private inner class ClickCancelListener : View.OnClickListener {
        override fun onClick(v: View?) {
            Toast.makeText(this@CustomerConfirm, "キャンセルしました", Toast.LENGTH_LONG).show()
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
