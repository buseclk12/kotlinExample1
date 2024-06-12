package com.example.busecelik_hw1

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var ageSeekBar: SeekBar
    private lateinit var genderSwitch: Switch
    private lateinit var ageTextView: TextView
    private lateinit var calculateButton: Button
    private lateinit var genderLabel: TextView
    private var age = 10
    private var gender = 0
    private var activityMultiplier = 1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        setContentView(R.layout.secondactivity_main)

        heightEditText = findViewById(R.id.heightEditText)
        weightEditText = findViewById(R.id.weightEditText)
        ageSeekBar = findViewById(R.id.seekBar2)
        genderSwitch = findViewById(R.id.genderSwitch)
        genderLabel = findViewById(R.id.genderLabel)

        findViewById<Button>(R.id.calculateButton).setOnClickListener { calculateBMR() }

        genderLabel.text = "Gender (Male) "
        ageSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                age = progress
                Toast.makeText(applicationContext, "Age: $age", Toast.LENGTH_SHORT).show()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        genderSwitch = findViewById(R.id.genderSwitch)
        val genderLabel = findViewById<TextView>(R.id.genderLabel)

        genderSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                gender = 1
                genderLabel.text = "Gender (Female) "
            } else {
                gender = 0
                genderLabel.text = "Gender (Male) "
            }
        }
        val nextActivityButton = findViewById<Button>(R.id.nextActivityButton)
        nextActivityButton.setOnClickListener {
            if (heightEditText.text.toString().trim().isEmpty() || weightEditText.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter both height and weight", Toast.LENGTH_LONG).show()
            } else {
                val bmr = calculateBMR()
                if (bmr != null) {
                    val intent = Intent(this, ThirdActivity::class.java)
                    intent.putExtra("BMR", bmr)
                    intent.putExtra("Gender", gender)
                    startActivity(intent)
                }
            }
        }
    }


    private fun calculateBMR(): Double? {
        val weight = weightEditText.text.toString().toDoubleOrNull()
        val height = heightEditText.text.toString().toDoubleOrNull()

        if (weight == null || height == null) {
            Toast.makeText(this, "Please enter valid height and weight.", Toast.LENGTH_LONG).show()
            return null
        }
        var result = 0.0
        if (gender == 0) {
            result = (weight/height) * 100
        } else {
            result = (weight/height) * 100
        }

        showResultDialog(result)
        return result

    }

    private fun showResultDialog(bmr: Double) {
        val message = if (gender == 0) {
            "Erkek için BMR (Yaş: $age): $bmr"
        } else {
            "Kadın için BMR (Yaş: $age): $bmr"
        }

        AlertDialog.Builder(this)
            .setTitle("BMR Sonucunuz")
            .setMessage(message)
            .setPositiveButton("Tamam") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
}