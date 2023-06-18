package com.example.dictator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonArrayRequest

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
        val inflater = this.layoutInflater //Inflate" simply means to turn our XML layout into a Kotlin object.

        val dialogView = inflater.inflate(R.layout.popup_window,null)
        val text = dialogView.findViewById(R.id.word) as TextView
        val closeButton = dialogView.findViewById(R.id.button) as Button
        val meaningView = dialogView.findViewById(R.id.description) as TextView


        //
        builder.setView(dialogView).setTitle("Dictator")
        text.setText(selectedText)
        meaningView.setText("Loading...")

        // Show the dialog box
        val dialog = builder.create()
        dialog.setCancelable(false) //Ensures dialog box not closed when pressed outside
        dialog.show()

        fetchData(selectedText){
            if(it != null){
                meaningView.setText(it)
            }
            else{
                Toast.makeText(this,"Error receiving ans",Toast.LENGTH_LONG).show()
            }
        }

        //when close button is pressed
        closeButton.setOnClickListener(){
            dialog.dismiss()
            finish() // Finish the activity or perform any other necessary action
        }
    }

    private fun fetchData(selectedText: String?,callback: (result: String) -> Unit){

        //api url
        val url = "https://api.dictionaryapi.dev/api/v2/entries/en/${selectedText}"

        var word : String
        var meaning : String = "Loading..."

        //Json array request
        val jsonArrayRequest = JsonArrayRequest(url, { response ->
            try {
                var jsonObj = response.getJSONObject(0)
                word = jsonObj.getString("word")

                val meaningArray = jsonObj.getJSONArray("meanings")
                val meaningObj = meaningArray.getJSONObject(0)

                val definitions = meaningObj.getJSONArray("definitions")
                val definitionsObj = definitions.getJSONObject(0)

                meaning = definitionsObj.getString("definition")
                Log.d("mean","meaning of the word is ${meaning}")
                callback(meaning)

            }
            catch ( e : Exception){
                meaning = "ERROR"
                callback(meaning)
            }

        }, {error ->
            meaning = "Error"
            callback(meaning)
        })

        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)
    }
}