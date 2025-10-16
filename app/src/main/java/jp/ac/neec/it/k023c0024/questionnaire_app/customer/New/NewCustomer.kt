package jp.ac.neec.it.k023c0024.questionnaire_app.customer.New

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import jp.ac.neec.it.k023c0024.questionnaire_app.R

class NewCustomer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_customer)

        val bt_put_information = findViewById<Button>(R.id.btPutInfomation)
        val bt_clear = findViewById<Button>(R.id.btClear)
        bt_put_information.setOnClickListener(ClickPutInformationListener())
        bt_clear.setOnClickListener(ClickClearListener())

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private inner class ClickPutInformationListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val kana = findViewById<EditText>(R.id.etNewCustomerKana).text.toString()
            val name = findViewById<EditText>(R.id.etNewCustomerName).text.toString()
            val preSex = findViewById<RadioGroup>(R.id.rgNewCustomerSex)
            val preEra = findViewById<RadioGroup>(R.id.rgNewCustomerEra)
            val year = findViewById<EditText>(R.id.etNewCustomerYear).text.toString()
            val month = findViewById<EditText>(R.id.etNewCustomerMonth).text.toString()
            val day = findViewById<EditText>(R.id.etNewCustomerDay).text.toString()
            val old = findViewById<EditText>(R.id.etNewCustomerOld).text.toString()
            val zip1 = findViewById<EditText>(R.id.etNewCustomerZip1).text.toString()
            val zip2 = findViewById<EditText>(R.id.etNewCustomerZip2).text.toString()
            val tel = findViewById<EditText>(R.id.etNewCustomerTel).text.toString()
            val address = findViewById<EditText>(R.id.etNewCustomerAddress).text.toString()
            val mail = findViewById<EditText>(R.id.etNewCustomerMail).text.toString()
            val role = findViewById<EditText>(R.id.etNewCustomerRole).text.toString()

            //選択されたRadioButtonのIDを取得
            val sexId = preSex.checkedRadioButtonId
            val eraId = preEra.checkedRadioButtonId

            //IDからRadioButtonのビューを取得し、テキストを代入
            val sex = findViewById<RadioButton>(sexId).text.toString()
            val era = findViewById<RadioButton>(eraId).text.toString()

            val PutInformation = Intent(this@NewCustomer, NewCustomerConfirm::class.java)
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

            startActivity(PutInformation)
        }
    }

    private inner class ClickClearListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val etKana = findViewById<EditText>(R.id.etNewCustomerKana)
            val etName = findViewById<EditText>(R.id.etNewCustomerName)
            val etYear = findViewById<EditText>(R.id.etNewCustomerYear)
            val etMonth = findViewById<EditText>(R.id.etNewCustomerMonth)
            val etDay = findViewById<EditText>(R.id.etNewCustomerDay)
            val etOld = findViewById<EditText>(R.id.etNewCustomerOld)
            val etZip1 = findViewById<EditText>(R.id.etNewCustomerZip1)
            val etZip2 = findViewById<EditText>(R.id.etNewCustomerZip2)
            val etTel = findViewById<EditText>(R.id.etNewCustomerTel)
            val etAddress = findViewById<EditText>(R.id.etNewCustomerAddress)
            val etMail = findViewById<EditText>(R.id.etNewCustomerMail)
            val etRole = findViewById<EditText>(R.id.etNewCustomerRole)

            val rgSex = findViewById<RadioGroup>(R.id.rgNewCustomerSex)
            val rgEra = findViewById<RadioGroup>(R.id.rgNewCustomerEra)

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
            Toast.makeText(this@NewCustomer, msg, Toast.LENGTH_LONG).show()
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