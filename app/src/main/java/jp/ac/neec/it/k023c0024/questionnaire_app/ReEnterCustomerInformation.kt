package jp.ac.neec.it.k023c0024.questionnaire_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import jp.ac.neec.it.k023c0024.questionnaire_app.data.db.DatabaseHelper

class ReEnterCustomerInformation : AppCompatActivity() {
    private val _helper = DatabaseHelper(this@ReEnterCustomerInformation)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_re_enter_customer_information)

        val id = intent.getStringExtra("id")

        val db = _helper.readableDatabase
        val sql = "SELECT * FROM customer WHERE _id = ?"

        val cursor = db.rawQuery(sql, arrayOf(id))//idを安全な方法で渡す

        var kana = ""
        var name = ""
        var sex = ""
        var era = ""
        var year = 0
        var month = 0
        var day = 0
        var old = 0
        var zip1 = 0
        var zip2 = 0
        var tel = 0
        var address = ""
        var mail = ""
        var role = ""

        try {
            if (cursor.moveToFirst()) {
                val idxKana = cursor.getColumnIndex("kana")
                val idxName = cursor.getColumnIndex("name")
                val idxSex = cursor.getColumnIndex("sex")
                val idxEra = cursor.getColumnIndex("era")
                val idxYear = cursor.getColumnIndex("year")
                val idxMonth = cursor.getColumnIndex("month")
                val idxDay = cursor.getColumnIndex("day")
                val idxOld = cursor.getColumnIndex("old")
                val idxZip1 = cursor.getColumnIndex("zip1")
                val idxZip2 = cursor.getColumnIndex("zip2")
                val idxTel = cursor.getColumnIndex("tel")
                val idxAddress = cursor.getColumnIndex("address")
                val idxMail = cursor.getColumnIndex("mail")
                val idxRole = cursor.getColumnIndex("role")

                if (idxKana >= 0) {
                    kana = cursor.getString(idxKana)
                }
                if (idxName >= 0) {
                    name = cursor.getString(idxName)
                }
                if (idxSex >= 0) {
                    sex = cursor.getString(idxSex)
                }
                if (idxEra >= 0) {
                    era = cursor.getString(idxEra)
                }
                if (idxYear >= 0) {
                    year = cursor.getInt(idxYear)
                }
                if (idxMonth >= 0) {
                    month = cursor.getInt(idxMonth)
                }
                if (idxDay >= 0) {
                    day = cursor.getInt(idxDay)
                }
                if (idxOld >= 0) {
                    old = cursor.getInt(idxOld)
                }
                if (idxZip1 >= 0) {
                    zip1 = cursor.getInt(idxZip1)
                }
                if (idxZip2 >= 0) {
                    zip2 = cursor.getInt(idxZip2)
                }
                if (idxTel >= 0) {
                    tel = cursor.getInt(idxTel)
                }
                if (idxAddress >= 0) {
                    address = cursor.getString(idxAddress)
                }
                if (idxMail >= 0) {
                    mail = cursor.getString(idxMail)
                }
                if (idxRole >= 0) {
                    role = cursor.getString(idxRole)
                }
            }else {
                Log.w("tag", "データが見つかりませんでした")
            }
        } catch (e: Exception) {
            Log.e("tag", "例外が発生しました", e)
        }finally {
            cursor.close()
        }

        val etKana = findViewById<EditText>(R.id.etReEnterKana)
        etKana.setText(kana)
        val etName = findViewById<EditText>(R.id.etReEnterName)
        etName.setText(name)
        val etSex = findViewById<RadioGroup>(R.id.rgReEnterSex)
        if(sex == "男"){
            etSex.check(R.id.rbReEnterMale)
        }else if(sex == "女"){
            etSex.check(R.id.rbReEnterFemale)
        }else{
            etSex.check(R.id.rbReEnterOther)
        }
        val etEra = findViewById<RadioGroup>(R.id.rgReEnterEra)
        if(era == "R"){
            etEra.check(R.id.rbReEnterReiwa)
        }else if(era == "H"){
            etEra.check(R.id.rbReEnterHeisei)
        }else if (era == "S"){
            etEra.check(R.id.rbReEnterShowa)
        }else{
            etEra.check(R.id.rbReEnterTaisho)
        }
        val etYear = findViewById<EditText>(R.id.etReEnterYear)
        etYear.setText(year.toString())
        val etMonth = findViewById<EditText>(R.id.etReEnterMonth)
        etMonth.setText(month.toString())
        val etDay = findViewById<EditText>(R.id.etReEnterDay)
        etDay.setText(day.toString())
        val etOld = findViewById<EditText>(R.id.etReEnterOld)
        etOld.setText(old.toString())
        val etZip1 = findViewById<EditText>(R.id.etReEnterZip1)
        etZip1.setText(zip1.toString())
        val etZip2 = findViewById<EditText>(R.id.etReEnterZip2)
        etZip2.setText(zip2.toString())
        val etTel = findViewById<EditText>(R.id.etReEnterTel)
        etTel.setText(tel.toString())
        val etAddress = findViewById<EditText>(R.id.etReEnterAddress)
        etAddress.setText(address)
        val etMail = findViewById<EditText>(R.id.etReEnterMail)
        etMail.setText(mail)
        val etRole = findViewById<EditText>(R.id.etReEnterRole)
        etRole.setText(role)

        val bt_clear = findViewById<Button>(R.id.btClear)
        bt_clear.setOnClickListener(ClickClearListener())

        val bt_put_information = findViewById<Button>(R.id.btPutInfomation)
        bt_put_information.setOnClickListener(ClickPutInformationListener())

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private inner class ClickClearListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val etKana = findViewById<EditText>(R.id.etReEnterKana)
            val etName = findViewById<EditText>(R.id.etReEnterName)
            val etYear = findViewById<EditText>(R.id.etReEnterYear)
            val etMonth = findViewById<EditText>(R.id.etReEnterMonth)
            val etDay = findViewById<EditText>(R.id.etReEnterDay)
            val etOld = findViewById<EditText>(R.id.etReEnterOld)
            val etZip1 = findViewById<EditText>(R.id.etReEnterZip1)
            val etZip2 = findViewById<EditText>(R.id.etReEnterZip2)
            val etTel = findViewById<EditText>(R.id.etReEnterTel)
            val etAddress = findViewById<EditText>(R.id.etReEnterAddress)
            val etMail = findViewById<EditText>(R.id.etReEnterMail)
            val etRole = findViewById<EditText>(R.id.etReEnterRole)

            val rgSex = findViewById<RadioGroup>(R.id.rgReEnterSex)
            val rgEra = findViewById<RadioGroup>(R.id.rgReEnterEra)

            etKana.setText("")
            etName.setText("")
            etYear.setText("")
            etMonth.setText("")
            etDay.setText("")
            etOld.setText("")
            etZip1.setText("")
            etZip2.setText("")
            etTel.setText("")
            etAddress.setText("")
            etMail.setText("")
            etRole.setText("")

            rgSex.clearCheck()
            rgEra.clearCheck()

            val msg = "クリアしました"
            Toast.makeText(this@ReEnterCustomerInformation, msg, Toast.LENGTH_LONG).show()
        }
    }

    private inner class ClickPutInformationListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val id = intent.getStringExtra("id")

            val kana = findViewById<EditText>(R.id.etReEnterKana).text.toString()
            val name = findViewById<EditText>(R.id.etReEnterName).text.toString()
            val preSex = findViewById<RadioGroup>(R.id.rgReEnterSex)
            val preEra = findViewById<RadioGroup>(R.id.rgReEnterEra)
            val year = findViewById<EditText>(R.id.etReEnterYear).text.toString()
            val month = findViewById<EditText>(R.id.etReEnterMonth).text.toString()
            val day = findViewById<EditText>(R.id.etReEnterDay).text.toString()
            val old = findViewById<EditText>(R.id.etReEnterOld).text.toString()
            val zip1 = findViewById<EditText>(R.id.etReEnterZip1).text.toString()
            val zip2 = findViewById<EditText>(R.id.etReEnterZip2).text.toString()
            val tel = findViewById<EditText>(R.id.etReEnterTel).text.toString()
            val address = findViewById<EditText>(R.id.etReEnterAddress).text.toString()
            val mail = findViewById<EditText>(R.id.etReEnterMail).text.toString()
            val role = findViewById<EditText>(R.id.etReEnterRole).text.toString()

            //選択されたRadioButtonのIDを取得
            val sexId = preSex.checkedRadioButtonId
            val eraId = preEra.checkedRadioButtonId

            //IDからRadioButtonのビューを取得し、テキストを代入
            val sex = findViewById<RadioButton>(sexId).text.toString()
            val era = findViewById<RadioButton>(eraId).text.toString()

            val PutInformation = Intent(this@ReEnterCustomerInformation, CustomerConfirm::class.java)

            PutInformation.putExtra("kana", kana)
            PutInformation.putExtra("name", name)
            PutInformation.putExtra("sex", sex)
            PutInformation.putExtra("era", era)
            PutInformation.putExtra("year", year)
            PutInformation.putExtra("month", month)
            PutInformation.putExtra("day", day)
            PutInformation.putExtra("old", old)
            PutInformation.putExtra("zip1", zip1)
            PutInformation.putExtra("zip2", zip2)
            PutInformation.putExtra("tel", tel)
            PutInformation.putExtra("address", address)
            PutInformation.putExtra("mail", mail)
            PutInformation.putExtra("role", role)

            PutInformation.putExtra("id", id)

            startActivity(PutInformation)
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