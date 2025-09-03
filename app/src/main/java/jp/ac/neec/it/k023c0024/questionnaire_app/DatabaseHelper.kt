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