package jp.ac.neec.it.k023c0024.questionnaire_app

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //アクティビティがnullでないならアバダイアログオブジェクトを生成。
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            //ダイアログのタイトルを設定
            builder.setTitle(R.string.complete_dialog_title)
            //ダイアログのメッセージを設定
            builder.setMessage(R.string.complete_dialog_msg)

            //Positive ButtonとNegative Buttonにリスナーを設定
            val listener = DialogBunttonClickListener()

            //Positive Buttonを設定
            builder.setPositiveButton(R.string.complete_dialog_btn_ok, listener)
            //Negative Buttonを設定
            builder.setNegativeButton(R.string.complete_dialog_btn_ng, listener)
            //生成したAlertDialogオブジェクトを返す
            builder.create()
        }
        //生成したダイアログオブジェクトをリターン。
        return dialog ?: throw IllegalStateException("アクティビティがnullです")
    }

    //ダイアログのアクションボタンがタップされたときの処理が記述されたメンバクラス
    private inner class DialogBunttonClickListener : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, which: Int) {
            //タップされたアクションボタンで分岐
            when(which){
                //Positive Button
                DialogInterface.BUTTON_POSITIVE ->{
                    //カルテの画面に名前情報を持って遷移
                    //TODO
                }
                //Negative Button
                DialogInterface.BUTTON_NEGATIVE -> {
                    //トップ画面に遷移
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}