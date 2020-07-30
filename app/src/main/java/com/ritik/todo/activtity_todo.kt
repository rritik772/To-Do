package com.ritik.todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import java.sql.PreparedStatement


private const val TAG = "activity"
class activtity_todo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG,"activity todo")
        setTheme(theme_)
        setContentView(R.layout.activtity_todo)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        val title = findViewById<EditText>(R.id.title_editer)
        val description = findViewById<EditText>(R.id.description_)

        title.setText(titleIs)
        description.setText(descriptionIs)

        var haveTo = ""

        if (titleIs == ""){
            haveTo = "Insert"
        }else{
            haveTo = "Update"
        }


        findViewById<FloatingActionButton>(R.id.add_to_database).setOnClickListener { view ->

            Log.e(TAG, "WE have $haveTo **********************")

            val title_is = title.text.toString()
            val description_is = description.text.toString()
            val database = databaseManagment(baseContext)

            if (haveTo.equals("Insert")) {
                database.addtodatabase(title_is, description_is)
            }else if (haveTo.equals("Update")){
                Log.e(TAG, "data to Update")
                database.updateDatabase((__id),title_is,description_is)
            }
            finish()
        }

        findViewById<FloatingActionButton>(R.id.delete_todo).setOnClickListener{ view ->

            val database = databaseManagment(baseContext)
            database.deleteFromDatabase((__id))

            finish()
        }
    }

}