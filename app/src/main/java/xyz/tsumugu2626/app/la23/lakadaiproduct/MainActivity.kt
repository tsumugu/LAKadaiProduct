package xyz.tsumugu2626.app.la23.lakadaiproduct

import android.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import xyz.tsumugu2626.app.la23.lakadaiproduct.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var memoItemList = mutableListOf<String>()
    private val pref by lazy {
        getSharedPreferences("xyz.tsumugu2626.app.la23.lakadaiproduct", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        memoItemList = Json.decodeFromString(pref.getString("memoItemList", "[]") ?: "[]")

        binding.memoListView.adapter = ArrayAdapter(
            this,
            R.layout.simple_list_item_1,
            memoItemList
        )

        binding.addItemButton.setOnClickListener {
            val addItemIntent: Intent = Intent(this, AddItemActivity::class.java)
            startActivity(addItemIntent)
        }
    }

    override fun onStart() {
        super.onStart()

        val textFromAddItemActivity: String = intent.getStringExtra("text") ?: ""
        if (textFromAddItemActivity != "") {
            memoItemList.add(textFromAddItemActivity)
        }
    }

    override fun onStop() {
        super.onStop()

        val jsonStringfiedMemoItemList = Json.encodeToString(memoItemList)
        Log.d("JSON",jsonStringfiedMemoItemList)

        val editor = pref.edit()
        editor.putString("memoItemList", jsonStringfiedMemoItemList)
        editor.apply()
    }
}