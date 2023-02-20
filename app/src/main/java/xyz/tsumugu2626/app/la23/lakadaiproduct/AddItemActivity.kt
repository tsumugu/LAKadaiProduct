package xyz.tsumugu2626.app.la23.lakadaiproduct

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import xyz.tsumugu2626.app.la23.lakadaiproduct.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddItemBinding.inflate(layoutInflater).apply { setContentView(this.root) }
        binding.saveButton.setOnClickListener {
            val mainIntent: Intent = Intent(this, MainActivity::class.java)
            val textInEditText = binding.memoEditText.text.toString()
            mainIntent.putExtra("text", textInEditText)
            startActivity(mainIntent)
        }

    }
}