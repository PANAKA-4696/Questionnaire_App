package jp.ac.neec.it.k023c0024.questionnaire_app

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.content.ContentValues
import android.database.Cursor

class DatabaseHelper (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    //クラス内のprivate定数を宣言するためにcompanion objectブロックとする。
    companion object{
        //データベースファイル名の定数。
        private const val DATABASE_NAME = "customer.db"
        //バージョン情報の定数
        private const val DATABASE_VERSION = 1
    }

    // customerとquestionnaireテーブルの両方をJSON形式の文字列に変換する

    //exportToString()
    //customerテーブルとquestionnaireテーブルをそれぞれ読み込みます。
    //各テーブルの1行ごとのデータを、カラム名と値のセット（Map）に変換していきます。
    //このコードは自動的に全てのカラム名と値を取得するため、手動でkanaやnameなどを一つずつ指定する必要がなく便利です。
    //最後に、2つのテーブルのデータリストを一つにまとめ、Gsonライブラリを使って全体のデータをきれいなJSON形式の文字列に変換します。
    fun exportToString(): String {
        val db = readableDatabase
        val gson = Gson()

        // customerテーブルのデータを取得
        val customers = mutableListOf<Map<String, Any?>>()
        val customerCursor = db.rawQuery("SELECT * FROM customer", null)
        while (customerCursor.moveToNext()) {
            val row = mutableMapOf<String, Any?>()
            for (i in 0 until customerCursor.columnCount) {
                val value = when (customerCursor.getType(i)) {
                    Cursor.FIELD_TYPE_INTEGER -> customerCursor.getLong(i)
                    Cursor.FIELD_TYPE_STRING -> customerCursor.getString(i)
                    else -> null
                }
                row[customerCursor.getColumnName(i)] = value
            }
            customers.add(row)
        }
        customerCursor.close()

        // questionnaireテーブルのデータを取得
        val questionnaires = mutableListOf<Map<String, Any?>>()
        val questionnaireCursor = db.rawQuery("SELECT * FROM questionnaire", null)
        while (questionnaireCursor.moveToNext()) {
            val row = mutableMapOf<String, Any?>()
            for (i in 0 until questionnaireCursor.columnCount) {
                val value = when (questionnaireCursor.getType(i)) {
                    Cursor.FIELD_TYPE_INTEGER -> questionnaireCursor.getLong(i)
                    Cursor.FIELD_TYPE_STRING -> questionnaireCursor.getString(i)
                    else -> null
                }
                row[questionnaireCursor.getColumnName(i)] = value
            }
            questionnaires.add(row)
        }
        questionnaireCursor.close()

        // 2つのテーブルデータを1つのオブジェクトにまとめてJSONに変換
        val backupData = mapOf(
            "customers" to customers,
            "questionnaires" to questionnaires
        )

        return gson.toJson(backupData)
    }

    //JSON文字列からcustomerとquestionnaireテーブルを復元する

    //importFromString()
    //Gsonを使ってJSON文字列を元のデータ構造に戻します。
    //トランザクションを開始します。これにより、途中でエラーが発生してもデータベースが中途半端な状態になるのを防ぎます。
    //両方のテーブルから古いデータを全て削除します。
    //JSONから復元したcustomersとquestionnairesのデータを、それぞれデータベースに挿入し直します。
    //トランザクションを正常に完了させます。
    fun importFromString(jsonString: String) {
        val db = writableDatabase
        val gson = Gson()
        // JSONの型情報を定義
        val type = object : TypeToken<Map<String, List<Map<String, Any?>>>>() {}.type
        val backupData: Map<String, List<Map<String, Any?>>> = gson.fromJson(jsonString, type)

        db.beginTransaction() // データベースの処理を一つのまとまり（トランザクション）として開始
        try {
            // 1. テーブルの既存データを全削除
            db.execSQL("DELETE FROM customer")
            db.execSQL("DELETE FROM questionnaire")

            // 2. customerテーブルのデータを復元
            val customers = backupData["customers"]
            customers?.forEach { row ->
                val values = ContentValues()
                row.forEach { (key, value) ->
                    when (value) {
                        is String -> values.put(key, value)
                        // Gsonは数値をDoubleとして解釈することがあるため、Longに変換
                        is Double -> values.put(key, value.toLong())
                        is Long -> values.put(key, value)
                        is Int -> values.put(key, value)
                    }
                }
                db.insert("customer", null, values)
            }

            // 3. questionnaireテーブルのデータを復元
            val questionnaires = backupData["questionnaires"]
            questionnaires?.forEach { row ->
                val values = ContentValues()
                row.forEach { (key, value) ->
                    when (value) {
                        is String -> values.put(key, value)
                        is Double -> values.put(key, value.toLong())
                        is Long -> values.put(key, value)
                        is Int -> values.put(key, value)
                    }
                }
                db.insert("questionnaire", null, values)
            }

            db.setTransactionSuccessful() // すべての処理が成功したことをマーク
        } finally {
            db.endTransaction() // トランザクションを終了（成功していれば変更が確定、失敗なら元に戻る）
        }
    }

    override fun onCreate(db: SQLiteDatabase){
        //テーブル作成用SQL文字列の作成
        var sb = StringBuilder()
        sb.append("CREATE TABLE customer (")
        sb.append("_id INTEGER PRIMARY KEY,")
        sb.append("kana TEXT,")
        sb.append("name TEXT,")
        sb.append("sex TEXT,")
        sb.append("era TEXT,")
        sb.append("year INTEGER,")
        sb.append("month INTEGER,")
        sb.append("day INTEGER,")
        sb.append("old INTEGER,")
        sb.append("zip1 INTEGER,")
        sb.append("zip2 INTEGER,")
        sb.append("address TEXT,")
        sb.append("tel TEXT,")
        sb.append("mail TEXT,")
        sb.append("role TEXT")
        sb.append(");")
        var sql = sb.toString()

        //SQLの実行
        db.execSQL(sql)

        sb = StringBuilder()
        sb.append("CREATE TABLE questionnaire (")
        sb.append("time TEXT PRIMARY KEY,")
        sb.append("_id INTEGER,")
        sb.append("Question1 TEXT,")
        sb.append("Question2 TEXT,")
        sb.append("Question3 TEXT,")
        sb.append("Question4 TEXT,")
        sb.append("Question5 TEXT,")
        sb.append("Question6 TEXT,")
        sb.append("etComment TEXT")
        sb.append(");")
        sql = sb.toString()

        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int){}
}