package jp.ac.neec.it.k023c0024.questionnaire_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    val _helper = DatabaseHelper(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bt_new = findViewById<Button>(R.id.btNew)
        val bt_member = findViewById<Button>(R.id.btMember)
        val bt_check = findViewById<Button>(R.id.btCheck)
        val bt_read_google_drive = findViewById<Button>(R.id.btReadGoogleDrive)
        val bt_write_google_drive = findViewById<Button>(R.id.btWriteGoogleDrive)

        bt_new.setOnClickListener(ClickNewCustomerListener())
        bt_member.setOnClickListener(ClickLoginCustomerListener())
        bt_check.setOnClickListener(ClickCheckCustomerListener())
        bt_read_google_drive.setOnClickListener(ClickReadGoogleDriveListener())
        bt_write_google_drive.setOnClickListener(ClickWriteGoogleDriveListener())
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

    private inner class ClickCheckCustomerListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val ClickCheckCustomerListener = Intent(this@MainActivity, InfomationCheckLogin::class.java)
            startActivity(ClickCheckCustomerListener)
        }
    }

    private inner class ClickReadGoogleDriveListener : View.OnClickListener {
        override fun onClick(v: View?) {

        }
    }

    private inner class ClickWriteGoogleDriveListener : View.OnClickListener {
        override fun onClick(v: View?) {

        }
    }
}