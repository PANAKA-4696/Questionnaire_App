package jp.ac.neec.it.k023c0024.questionnaire_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NewCustomer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_customer)

        val bt_put_information = findViewById<Button>(R.id.btPutInfomation)
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

            val PutInformation = Intent(this@NewCustomer, NewCustomerConfirm::class.java)
            PutInformation.putExtra("kana", kana)
            PutInformation.putExtra("name", name)
            //RadioGroupの選択が変更されたときのリスナーを設定
            preSex.setOnCheckedChangeListener { group, checkedId ->
                //選択されたRadioButtonのidからビューを取得
                val rbSex = findViewById<RadioButton>(checkedId)

                //もしRadioButtonが選択されていれば、そのテキストを取得して表示
                if (rbSex != null) {
                    val sex = rbSex.text.toString()
                }
            }
            preEra.setOnCheckedChangeListener { group, checkedId ->
                //選択されたRadioButtonのidからビューを取得
                val rbEra = findViewById<RadioButton>(checkedId)

                //もしRadioButtonが選択されていれば、そのテキストを取得して表示
                if (rbEra != null) {
                    val era = rbEra.text.toString()
                }
            }
            PutInformation.putExtra("year", year)
            PutInformation.putExtra("month", month)
            PutInformation.putExtra("day", day)
            PutInformation.putExtra("old", old)
            PutInformation.putExtra("zip1", zip1)
            PutInformation.putExtra("zip2", zip2)
            PutInformation.putExtra("tel", tel)
            PutInformation.putExtra("address", address)
            PutInformation.putExtra("mail", mail)

            startActivity(PutInformation)
        }
    }
}