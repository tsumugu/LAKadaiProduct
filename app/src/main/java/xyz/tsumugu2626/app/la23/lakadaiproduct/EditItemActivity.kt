package xyz.tsumugu2626.app.la23.lakadaiproduct

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import xyz.tsumugu2626.app.la23.lakadaiproduct.databinding.ActivityAddItemBinding
import xyz.tsumugu2626.app.la23.lakadaiproduct.databinding.ActivityEditItemBinding
import xyz.tsumugu2626.app.la23.lakadaiproduct.databinding.ActivityMainBinding

class EditItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditItemBinding
    private var memoItemList = mutableListOf<String>()
    private val pref by lazy {
        getSharedPreferences("xyz.tsumugu2626.app.la23.lakadaiproduct", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditItemBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        loadMemoItemList()

        val clickedListIndex: Int = intent.getIntExtra("index", 0) ?: 0

        if (memoItemList.size > 0) { // IndexOutOfBoundsException対策
            binding.editMemoEditText.setText(memoItemList.get(clickedListIndex))
        }

        binding.editDeleteButton.setOnClickListener {
            memoItemList.removeAt(clickedListIndex)

            saveMemoItemList()

            backToMainActivity()
        }

        binding.editSaveButton.setOnClickListener {
            memoItemList.removeAt(clickedListIndex)
            memoItemList.add(clickedListIndex, binding.editMemoEditText.text.toString())

            saveMemoItemList()

            backToMainActivity()
        }

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

    private fun backToMainActivity() {
        val mainActivityIntent: Intent = Intent(this, MainActivity::class.java)
        startActivity(mainActivityIntent)
    }

}