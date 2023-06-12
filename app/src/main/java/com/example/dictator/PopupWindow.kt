package com.example.dictator

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

class PopupWindow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the intent action is ACTION_PROCESS_TEXT
        if (intent.action == Intent.ACTION_PROCESS_TEXT) {
            // Retrieve the selected text from the intent extras
            val selectedText = intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT)

            // Show the dialog box with the selected text
            showDialog(selectedText)
        }
    }

    private fun showDialog(selectedText: String?) {
        // Create and configure the dialog box
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selected Text")
        builder.setMessage(selectedText)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss() // Close the dialog
            finish() // Finish the activity or perform any other necessary action
        }

        // Show the dialog box
        val dialog = builder.create()
        dialog.show()
    }
}