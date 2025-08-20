package jp.ac.neec.it.k023c0024.questionnaire_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bt_new = findViewById<Button>(R.id.btNew)
        val bt_member = findViewById<Button>(R.id.btMember)

        bt_new.setOnClickListener(ClickNewCustomerListener())
        bt_member.setOnClickListener(ClickLoginCustomerListener())
    }

    private inner class ClickNewCustomerListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val NewCustomer = Intent(this@MainActivity, NewCustomer::class.java)
            startActivity(NewCustomer)
        }
    }

    private inner class ClickLoginCustomerListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val ClickLoginCustomerListener = Intent(this@MainActivity, LoginCustomer::class.java)
            startActivity(ClickLoginCustomerListener)
        }
    }
}