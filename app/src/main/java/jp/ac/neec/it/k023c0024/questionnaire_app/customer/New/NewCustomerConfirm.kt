    package jp.ac.neec.it.k023c0024.questionnaire_app.customer.New

    import android.os.Bundle
    import android.view.MenuItem
    import android.view.View
    import android.widget.Button
    import android.widget.TextView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import jp.ac.neec.it.k023c0024.questionnaire_app.data.db.DatabaseHelper
    import jp.ac.neec.it.k023c0024.questionnaire_app.R

    class NewCustomerConfirm : AppCompatActivity() {
        //データベースヘルパーオブジェクト
        private val _helper = DatabaseHelper(this@NewCustomerConfirm)

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

                //DBに保存する
                val kana = intent.getStringExtra("kana")
                val name = intent.getStringExtra("name")
                val sex = intent.getStringExtra("sex")
                val era = intent.getStringExtra("era")
                val year = intent.getStringExtra("year")?.toIntOrNull()
                val month = intent.getStringExtra("month")?.toIntOrNull()
                val day = intent.getStringExtra("day")?.toIntOrNull()
                val old = intent.getStringExtra("old")?.toIntOrNull()
                val zip1 = intent.getStringExtra("zip1")?.toIntOrNull()
                val zip2 = intent.getStringExtra("zip2")?.toIntOrNull()
                val address = intent.getStringExtra("address")
                val tel = intent.getStringExtra("tel")
                val mail = intent.getStringExtra("mail")
                val role = intent.getStringExtra("role")

                val db = _helper.writableDatabase

                try{
                    if(kana != null && name != null && sex != null && era != null && year != null && month != null && day != null &&
                        old != null && zip1 != null && zip2 != null && tel != null && address != null && mail != null && role != null){

                        //インサート用文字列の用意
                        val sqlInsert = "INSERT INTO customer (kana, name, sex, era, year, month, day, old, zip1, zip2, tel, address, mail, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                        //SQL文字列をもとにプリペアードステートメントを取得
                        var stmt = db.compileStatement(sqlInsert)
                        //変数のバインド
                        stmt.bindString(1, kana)
                        stmt.bindString(2, name)
                        stmt.bindString(3, sex)
                        stmt.bindString(4, era)
                        stmt.bindLong(5, year.toLong())
                        stmt.bindLong(6, month.toLong())
                        stmt.bindLong(7, day.toLong())
                        stmt.bindLong(8, old.toLong())
                        stmt.bindLong(9, zip1.toLong())
                        stmt.bindLong(10, zip2.toLong())
                        stmt.bindString(11, tel)
                        stmt.bindString(12, address)
                        stmt.bindString(13, mail)
                        stmt.bindString(14, role)


                        val msg = "保存しました"
                        Toast.makeText(this@NewCustomerConfirm, msg, Toast.LENGTH_LONG).show()

                        val id = stmt.executeInsert()//この戻り値がID

                        if(id != -1L){
                            //newInstanceを使ってIDを渡す
                            val dialogFragment = ConfirmDialogFragment.newInstance(id)
                            dialogFragment.show(supportFragmentManager, "ConfirmDialogFragment")
                        }else{
                            //挿入失敗処理
                            Toast.makeText(this@NewCustomerConfirm, "IDの取得に失敗しました", Toast.LENGTH_LONG).show()
                        }



                    }else{
                        val msg = "保存に失敗しました"
                        Toast.makeText(this@NewCustomerConfirm, msg, Toast.LENGTH_LONG).show()
                    }
                }finally{
                    //try-finallyブロックでデータベース接続を必ず閉じる
                    db.close()
                }
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