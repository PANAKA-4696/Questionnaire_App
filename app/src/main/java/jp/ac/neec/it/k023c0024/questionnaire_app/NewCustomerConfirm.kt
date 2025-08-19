package jp.ac.neec.it.k023c0024.questionnaire_app

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NewCustomerConfirm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_customer_confirm)

        val bt_save = findViewById<Button>(R.id.btSave)
        val bt_cancel = findViewById<Button>(R.id.btCancel)

        bt_save.setOnClickListener(ClickSaveListener())
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

        val puKana =findViewById<TextView>(R.id.tvNewCustomerConfirmKana)
        puKana.text = kana
        val puName =findViewById<TextView>(R.id.tvNewCustomerConfirmName)
        puName.text = name
        val puSex =findViewById<TextView>(R.id.tvNewCustomerConfirmSex)
        puSex.text = sex
        val puEra =findViewById<TextView>(R.id.tvNewCustomerConfirmEra)
        puEra.text = era
        val puYear =findViewById<TextView>(R.id.tvNewCustomerConfirmYear)
        puYear.text = year
        val puMonth =findViewById<TextView>(R.id.tvNewCustomerConfirmMonth)
        puMonth.text = month
        val puDay =findViewById<TextView>(R.id.tvNewCustomerConfirmDay)
        puDay.text = day
        val puOld =findViewById<TextView>(R.id.tvNewCustomerConfirmOld)
        puOld.text = old
        val puZip1 =findViewById<TextView>(R.id.tvNewCustomerConfirmZip1)
        puZip1.text = zip1
        val puZip2 =findViewById<TextView>(R.id.tvNewCustomerConfirmZip2)
        puZip2.text = zip2
        val puTel =findViewById<TextView>(R.id.tvNewCustomerConfirmTel)
        puTel.text = tel
        val puAddress =findViewById<TextView>(R.id.tvNewCustomerConfirmAddress)
        puAddress.text = address
        val puMail =findViewById<TextView>(R.id.tvNewCustomerConfirmMail)
        puMail.text = mail
        val puRole =findViewById<TextView>(R.id.tvNewCustomerConfirmRole)
        puRole.text = role

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private inner class ClickSaveListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val dialogFragment = CompleteDialogFragment()
            dialogFragment.show(supportFragmentManager, "CompleteDialogFragment")

            val msg = "保存しました"
            Toast.makeText(this@NewCustomerConfirm, msg, Toast.LENGTH_LONG).show()
        }
    }

    private inner class ClickCancelListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val msg = "キャンセルしました"
            Toast.makeText(this@NewCustomerConfirm, msg, Toast.LENGTH_LONG).show()

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