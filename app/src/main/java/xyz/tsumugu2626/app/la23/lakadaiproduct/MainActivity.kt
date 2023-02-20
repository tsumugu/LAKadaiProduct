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

        binding.addItemButton.setOnClickListener {
            val addItemIntent: Intent = Intent(this, AddItemActivity::class.java)
            startActivity(addItemIntent)
        }
        binding.memoListView.setOnItemClickListener { _, _, i, _ ->
            val editItemIntent: Intent = Intent(this, EditItemActivity::class.java)
            editItemIntent.putExtra("index", i)
            startActivity(editItemIntent)
        }
    }

    override fun onStart() {
        super.onStart()

        loadMemoItemList()
        binding.memoListView.adapter = ArrayAdapter(
            this,
            R.layout.simple_list_item_1,
            memoItemList
        )
    }

    override fun onStop() {
        super.onStop()

        saveMemoItemList()
    }

    private fun loadMemoItemList() {
        memoItemList = Json.decodeFromString(pref.getString("memoItemList", "[]") ?: "[]")
    }

    private fun saveMemoItemList() {
        val jsonStringfiedMemoItemList = Json.encodeToString(memoItemList)

        val editor = pref.edit()
        editor.putString("memoItemList", jsonStringfiedMemoItemList)
        editor.apply()
    }
}