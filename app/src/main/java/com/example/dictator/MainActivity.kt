package com.example.dictator

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //findViewById<Button>(R.id.LoadPopup).setOnClickListener {
        //    val dialog = PopupWindow()
        //    dialog.show(supportFragmentManager,"123")
        //}
    }
}