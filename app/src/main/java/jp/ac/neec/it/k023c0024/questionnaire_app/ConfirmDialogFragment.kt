package jp.ac.neec.it.k023c0024.questionnaire_app

import android.app.AlertDialog
import android.app.Dialog
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
            //Positive Buttonを設定
            builder.setPositiveButton(R.string.complete_dialog_btn_ok, null)
            //Negative Buttonを設定
            builder.setNegativeButton(R.string.complete_dialog_btn_ng, null)
            //生成したAlertDialogオブジェクトを返す
            builder.create()
        }
        //生成したダイアログオブジェクトをリターン。
        return dialog ?: throw IllegalStateException("アクティビティがnullです")
    }
}