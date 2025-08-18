package jp.ac.neec.it.k023c0024.questionnaire_app

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private inner class ClickSaveListener : View.OnClickListener {
        override fun onClick(v: View?) {
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