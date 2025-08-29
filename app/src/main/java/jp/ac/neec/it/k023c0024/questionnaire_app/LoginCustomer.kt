package jp.ac.neec.it.k023c0024.questionnaire_app

import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginCustomer : AppCompatActivity() {
    //データベースヘルパーオブジェクト
    private  val _helper = DatabaseHelper(this@LoginCustomer)

    private var _customerList: MutableList<MutableMap<Any, String>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_customer)

        _customerList = createCustomerList()
        val lvCustomer = findViewById<ListView>(R.id.lvCustomer)

        var adapter = SimpleAdapter(
            this@LoginCustomer,
            _customerList,
            android.R.layout.simple_list_item_2,
            arrayOf("id", "name"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        lvCustomer.adapter = adapter
    }
}