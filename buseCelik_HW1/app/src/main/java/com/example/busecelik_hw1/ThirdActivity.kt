package com.example.busecelik_hw1

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ThirdActivity : AppCompatActivity() {

    private lateinit var closeButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        setContentView(R.layout.thirdactivity_main)

        val bmr = intent.getDoubleExtra("BMR", 0.0)
        val gender = intent.getIntExtra("Gender", 0)

        val imageView = findViewById<ImageView>(R.id.imageView)

        if (gender == 0) {
            if (bmr <= 49) {
                imageView.setImageResource(R.drawable.fitmen)
            } else {
                imageView.setImageResource(R.drawable.obeseman)
            }
        } else {
            if (bmr <= 53) {
                imageView.setImageResource(R.drawable.fitwoman)
            } else {
                imageView.setImageResource(R.drawable.obesewoman)
            }
        }

        val bmrTextView = findViewById<TextView>(R.id.bmrTextView)
        bmrTextView.text = "BMR: $bmr"

        val closeButton = findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            finish()
        }


    }
}

