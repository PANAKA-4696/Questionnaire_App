package jp.ac.neec.it.k023c0024.questionnaire_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import jp.ac.neec.it.k023c0024.questionnaire_app.data.db.DatabaseHelper

class CustomerInformationConfirm : AppCompatActivity() {
    private val _helper = DatabaseHelper(this@CustomerInformationConfirm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_information_confirm)

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

        val puKana =findViewById<TextView>(R.id.puCustomerInformationConfirmKana)
        puKana.text = kana
        val puName =findViewById<TextView>(R.id.puCustomerInformationConfirmName)
        puName.text = name
        val puSex =findViewById<TextView>(R.id.puCustomerInformationConfirmSex)
        puSex.text = sex
        val puEra =findViewById<TextView>(R.id.puCustomerInformationConfirmEra)
        puEra.text = era
        val puYear =findViewById<TextView>(R.id.puCustomerInformationConfirmYear)
        puYear.text = year.toString()
        val puMonth =findViewById<TextView>(R.id.puCustomerInformationConfirmMonth)
        puMonth.text = month.toString()
        val puDay =findViewById<TextView>(R.id.puCustomerInformationConfirmDay)
        puDay.text = day.toString()
        val puOld =findViewById<TextView>(R.id.puCustomerInformationConfirmOld)
        puOld.text = old.toString()
        val puZip1 =findViewById<TextView>(R.id.puCustomerInformationConfirmZip1)
        puZip1.text = zip1.toString()
        val puZip2 =findViewById<TextView>(R.id.puCustomerInformationConfirmZip2)
        puZip2.text = zip2.toString()
        val puTel =findViewById<TextView>(R.id.puCustomerInformationConfirmTel)
        puTel.text = tel.toString()
        val puAddress =findViewById<TextView>(R.id.puCustomerInformationConfirmAddress)
        puAddress.text = address
        val puMail =findViewById<TextView>(R.id.puCustomerInformationConfirmMail)
        puMail.text = mail
        val puRole =findViewById<TextView>(R.id.puCustomerInformationConfirmRole)
        puRole.text = role

        val btCustomerInformationConfirmReEnter = findViewById<Button>(R.id.btCustomerInformationConfirmReEnter)
        btCustomerInformationConfirmReEnter.setOnClickListener(ClickCustomerInformationConfirmReEnterListener())

        val btCustomerInformationCondfirmBack = findViewById<Button>(R.id.btCustomerInformationCondfirmBack)
        btCustomerInformationCondfirmBack.setOnClickListener(ClickCustomerInformationCondfirmBackListener())

        val btQuestion = findViewById<Button>(R.id.btQuestion)
        btQuestion.setOnClickListener(ClickQuestionListener())
    }

    private inner class ClickCustomerInformationConfirmReEnterListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val id = intent.getStringExtra("id")

            val reEnterIntent  = Intent(this@CustomerInformationConfirm, ReEnterCustomerInformation::class.java)

            reEnterIntent.putExtra("id", id)

            startActivity(reEnterIntent)
        }
    }

    private inner class ClickQuestionListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val id = intent.getStringExtra("id")

            val QuestionLogin = Intent(this@CustomerInformationConfirm, QuestionConfirmLogin::class.java)

            QuestionLogin.putExtra("id", id)

            startActivity(QuestionLogin)
        }
    }

    private inner class ClickCustomerInformationCondfirmBackListener : View.OnClickListener {
        override fun onClick(v: View?) {
            finish()
        }
    }
}