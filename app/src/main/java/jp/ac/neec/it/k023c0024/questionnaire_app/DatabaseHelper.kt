package jp.ac.neec.it.k023c0024.questionnaire_app

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    //クラス内のprivate定数を宣言するためにcompanion objectブロックとする。
    companion object{
        //データベースファイル名の定数。
        private const val DATABASE_NAME = "customer.db"
        //バージョン情報の定数
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase){
        //テーブル作成用SQL文字列の作成
        val sb = StringBuilder()
        sb.append("CREATE TABLE customer (")
        sb.append("_id INTEGER PRIMARY KEY,")
        sb.append("name TEXT,")
        sb.append("kana TEXT,")
        sb.append("sex INTEGER,")
        sb.append("birth TEXT,")
        sb.append("zip TEXT,")
        sb.append("address TEXT,")
        sb.append("tel TEXT,")
        sb.append("mail TEXT,")
        sb.append("role INTEGER")
        sb.append(");")
        val sql = sb.toString()

        //SQLの実行
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int){}
}