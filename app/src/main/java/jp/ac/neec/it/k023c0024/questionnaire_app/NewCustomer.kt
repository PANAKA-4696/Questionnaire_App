package jp.ac.neec.it.k023c0024.questionnaire_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
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
            val PutInformation = Intent(this@NewCustomer, NewCustomerConfirm::class.java)
            startActivity(PutInformation)
        }
    }
}