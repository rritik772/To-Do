package com.ritik.todo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.scroller.*

private const val TAG = "Scrorling activut"

var titleIs = ""
var descriptionIs = ""
var __id = 0
var SaveMode = "com.ritik.todo"
var theme_store ="stored theme"
var theme_ = R.style.AppTheme
var defaultTheme_ = R.style.AppTheme

open class ScrollingActivity : AppCompatActivity(), RecycleViewClickListener.OnRecycleClickListener {


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("SaveMode", Context.MODE_PRIVATE)
        theme_ = sharedPreferences.getInt(theme_store, theme_ )
        Log.e(TAG, "Theme is $theme_")

        setTheme(theme_)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title

        val databaseManagment = databaseManagment(baseContext)
        databaseManagment.openDatabase()
        val rowData = databaseManagment.datagetter()
        databaseManagment.tester()

        val RecycleViewAdapter = RecycleViewAdapter(rowData)
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.addOnItemTouchListener(RecycleViewClickListener(this, recycleView,this))
        recycleView.adapter = RecycleViewAdapter



        findViewById<FloatingActionButton>(R.id.add_todo).setOnClickListener { view ->

            val intent = Intent(this, activtity_todo::class.java)
            startActivity(intent)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        var thme: MenuItem = menu.findItem(R.id.theme_changer)

        if (theme_ == R.style.AppTheme){
            thme.setTitle("Dark mode ON")
        }else{
            thme.setTitle("Dark mode OFF")
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        when (item.itemId) {
            R.id.about_me ->{
                val intent = Intent(this, about_me_activity::class.java)
                startActivity(intent)
            }
            R.id.theme_changer -> {
                if (theme_ == R.style.AppTheme){
                    theme_ = R.style.DDarkTheme

                }else{
                    theme_ = R.style.AppTheme
                }

                val sharedPreferences: SharedPreferences = this.getSharedPreferences("SaveMode", Context.MODE_PRIVATE)
                val pref: SharedPreferences.Editor = sharedPreferences.edit()
                pref.putInt(theme_store, theme_)
                Log.e(TAG,"THEME Stored is $theme_store and $theme_")
                pref.apply()

                recreate()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onResume() {
        super.onResume()

        val databaseManagment = databaseManagment(baseContext)
        databaseManagment.openDatabase()
        val rowData = databaseManagment.datagetter()

        titleIs = ""
        descriptionIs = ""

        val RecycleViewAdapter = RecycleViewAdapter(rowData)
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = RecycleViewAdapter
    }

    override fun OnItemClick(view: View, position: Int) {
        Log.d(TAG, "OnItemclick called and position is $position")

        val databaseManagment = databaseManagment(baseContext)
        val titles = databaseManagment.datagetter()
        val descriptions = databaseManagment.descriptiongetter()
        val ids = databaseManagment.IDgetter()

        val rowData = databaseManagment.datagetter()
        val recycleViewAdapter = RecycleViewAdapter(rowData)
        val details = recycleViewAdapter.getDetails(ids ,titles, descriptions, position)

        titleIs = details[0]
        descriptionIs = details[1]

        val intent = Intent(this, activtity_todo::class.java)
        startActivity(intent)

    }

    override fun OnLongClick(view: View, position: Int) {
        Log.d(TAG, "OnLongItemclick called and position is $position")
    }

    override fun onDestroy() {
        Log.e(TAG, "On distroy called")
        super.onDestroy()

        val database = databaseManagment(this)
        database.closeDatabase()

    }

}