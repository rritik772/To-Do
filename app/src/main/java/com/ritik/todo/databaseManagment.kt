package com.ritik.todo

import android.content.ClipDescription
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.sql.Date

private const val TAG = "DatabaseManagement"
class databaseManagment(val context: Context) : ScrollingActivity() {
    private var database: SQLiteDatabase = context.openOrCreateDatabase("maindb.db", Context.MODE_PRIVATE,null)

    fun openDatabase() {
        val sql = "CREATE TABLE IF NOT EXISTS todo(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title TEXT, description TEXT)"
        database.execSQL(sql)
    }

    fun addtodatabase(title: String, description: String){
        val sql = "INSERT INTO todo (title, description) VALUES ('$title', '$description')"
        Log.e(TAG, "********************************** Inserting database and ID is $title *************************************")
        if (title != ""){
            database.execSQL(sql)
        }else{
            Toast.makeText(context,"Nothing to add", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteFromDatabase(id: Int){
        Log.e(TAG, "Deleting from Database and ID is $id")
        val sql = "DELETE FROM todo where _id = $id"
        database.execSQL(sql)
    }

    fun updateDatabase(id: Int, title: String, description: String){
        Log.e(TAG, "********************************** Updating database and ID is $id *************************************")
        val sql = "UPDATE todo SET title='$title', description='$description' WHERE _id = $id"
        database.execSQL(sql)
    }

    fun datagetter(): List<String>{
        val dataRow = ArrayList<String>()
        val sql = "SELECT title FROM todo"
        var cursor: Cursor = database.rawQuery(sql, null)

        with(cursor){
            while (moveToNext()){
                dataRow.add(getString(0))
            }
        }
        return dataRow
    }
    fun descriptiongetter(): List<String>{
        val desRow = ArrayList<String>()
        val sql = "SELECT description FROM todo"
        var cursor: Cursor = database.rawQuery(sql, null)

        with(cursor){
            while (moveToNext()){
                desRow.add(getString(0))
            }
        }
        return desRow
    }
    fun IDgetter(): List<Int>{
        val idRow = ArrayList<Int>()
        val sql = "SELECT _id FROM todo"
        var cursor: Cursor = database.rawQuery(sql, null)

        with(cursor){
            while (moveToNext()){
                idRow.add(getInt(0))
            }
        }
        return idRow
    }

    fun tester(){
        val sql = "SELECT _id,title, description FROM todo"
        val cursor: Cursor = database.rawQuery(sql,null)

        with(cursor){
            while(moveToNext()){
                Log.e(TAG, "****************DATA is ${getInt(0)} title ${getString(1)} des ${getString(2)}")
            }
        }

    }

    fun closeDatabase(){
        database.close()
    }
}