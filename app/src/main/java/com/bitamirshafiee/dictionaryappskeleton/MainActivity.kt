package com.bitamirshafiee.dictionaryappskeleton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val KEY = "DEFINITION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val queue = Volley.newRequestQueue(this)

        find_button.setOnClickListener {
            val url = getUrl()
            val stringRequest =
                StringRequest(Request.Method.GET, url, { response ->
                    try {
                        extractDefinitionFromJson(response)
                    } catch (exepction: Exception) {
                        exepction.printStackTrace()
                    }
                },
                    { error ->
                        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    }
                )
            queue.add(stringRequest)
        }
    }

    private fun getUrl(): String {
        val wordEditText = word_edit_text.text
        val apiKey = "915a560d-bae7-4080-9e65-085bef32c480"
        return "https://www.dictionaryapi.com/api/v3/references/learners/json/$wordEditText?key=$apiKey"
    }

    private fun extractDefinitionFromJson(response: String) {
        val jsonArray = JSONArray(response)
        val firstIndex = jsonArray.getJSONObject(0)
        val getShortDefinition = firstIndex.getJSONArray("shortdef")
        val firstShortDefinition = getShortDefinition.get(0)

        val intent = Intent(this, DefinitionActivity::class.java)
        intent.putExtra(KEY, firstShortDefinition.toString())
        startActivity(intent)
    }
    //915a560d-bae7-4080-9e65-085bef32c480

    //https://www.dictionaryapi.com/api/v3/references/learners/json/apple?key=915a560d-bae7-4080-9e65-085bef32c480
}