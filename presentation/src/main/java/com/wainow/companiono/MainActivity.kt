package com.wainow.companiono

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.wainow.companiono.list.CompanyListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_layout, CompanyListFragment.newInstance(1), "CompanyListFragment")
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(CompanyListFragment.TAG, "MainActivity: onOptionsItemSelected")
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}