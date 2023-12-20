package com.example.mobile3

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class HelloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        val button = findViewById<Button>(R.id.button)
        val resultText = findViewById<TextView>(R.id.result);




        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data;
                this.setResultTextOnStatusOK(resultText, data?.getIntExtra("altitude", 0) ?: 0);
            }

            if (result.resultCode == Activity.RESULT_CANCELED) {
                this.setResultTextOnStatusCanceled(resultText);
            }
        }

        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            resultLauncher.launch(intent)
        }

    }

    private fun setResultTextOnStatusOK(entity: TextView, value: Int): Unit {
        entity.text = "Результат: $value";
        entity.visibility = View.VISIBLE;
        return;
    }

    private fun setResultTextOnStatusCanceled(entity: TextView): Unit {
        entity.visibility = View.INVISIBLE;
        return;
    }
}