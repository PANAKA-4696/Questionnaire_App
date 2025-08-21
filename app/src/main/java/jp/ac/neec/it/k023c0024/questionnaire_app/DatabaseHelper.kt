package jp.ac.neec.it.k023c0024.questionnaire_app

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    //クラス内のprivate定数を宣言するためにcompanion objectブロックとする。
    companion object{
    }

    override fun onCreate(db: SQLiteDatabase){
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int){}
}