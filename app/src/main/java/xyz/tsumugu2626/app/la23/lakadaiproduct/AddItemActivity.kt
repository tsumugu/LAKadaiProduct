package xyz.tsumugu2626.app.la23.lakadaiproduct

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import xyz.tsumugu2626.app.la23.lakadaiproduct.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding
    private var memoItemList = mutableListOf<String>()
    private val pref by lazy {
        getSharedPreferences("xyz.tsumugu2626.app.la23.lakadaiproduct", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        loadMemoItemList()

        binding.addButton.setOnClickListener {
            saveMemoItemList()

            val mainIntent: Intent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }

    }

    private fun loadMemoItemList() {
        memoItemList = Json.decodeFromString(pref.getString("memoItemList", "[]") ?: "[]")
    }

    private fun saveMemoItemList() {
        val textInEditText = binding.addMemoEditText.text.toString()
        memoItemList.add(textInEditText)

        val jsonStringfiedMemoItemList = Json.encodeToString(memoItemList)

        val editor = pref.edit()
        editor.putString("memoItemList", jsonStringfiedMemoItemList)
        editor.apply()
    }
}