package com.example.busecelik_hw1

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.busecelik_hw1.databinding.ActivityMainBinding
import kotlin.math.PI
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val titleTextView = findViewById<TextView>(R.id.textView1)
        startBlinkAnimation(titleTextView)
        binding.changePage.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        setupSpinner()
        setupCalculateButton()
    }
    private fun startBlinkAnimation(view: TextView) {
        val animator = ObjectAnimator.ofInt(
            view, "textColor",
            Color.TRANSPARENT, ContextCompat.getColor(this, android.R.color.black)
        )
        animator.duration = 500
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.REVERSE
        animator.start()
    }
    private fun setupSpinner() {
        val items = arrayOf("Cone", "Cylinder", "Sphere")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (items[position]) {
                    "Cone" -> {
                        binding.imageView.setImageResource(R.drawable.cone)
                        binding.textView4.visibility = View.VISIBLE
                        binding.editTextNumberDecimal2.visibility = View.VISIBLE
                        binding.editTextNumberDecimal.visibility = View.VISIBLE
                        binding.textView4.text = "Height : "
                        binding.editTextNumberDecimal2.setText("")
                        binding.editTextNumberDecimal.setText("")
                    }
                    "Cylinder" -> {
                        binding.imageView.setImageResource(R.drawable.cylinder)
                        binding.textView4.visibility = View.VISIBLE
                        binding.editTextNumberDecimal2.setText("")
                        binding.editTextNumberDecimal.setText("")
                        binding.editTextNumberDecimal2.visibility = View.VISIBLE
                        binding.editTextNumberDecimal.visibility = View.VISIBLE
                        binding.textView4.text = "Height : "

                    }
                    "Sphere" -> {
                        binding.imageView.setImageResource(R.drawable.sphere)
                        binding.textView4.visibility = View.GONE
                        binding.editTextNumberDecimal.visibility = View.GONE
                        binding.editTextNumberDecimal.setText("")
                        binding.editTextNumberDecimal.setText("")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setupCalculateButton() {
        binding.button.setOnClickListener {
            val radius = binding.editTextNumberDecimal2.text.toString().toDoubleOrNull()
            val height = binding.editTextNumberDecimal.text.toString().toDoubleOrNull()

            if (radius == null) {
                Toast.makeText(this, "Please enter a valid radius", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val (area, volume) = when (binding.spinner.selectedItem.toString()) {
                "Cone" -> Pair(calculateConeArea(radius, height ?: 0.0), calculateConeVolume(radius, height ?: 0.0))
                "Cylinder" -> Pair(calculateCylinderArea(radius, height ?: 0.0), calculateCylinderVolume(radius, height ?: 0.0))
                "Sphere" -> Pair(calculateSphereArea(radius), calculateSphereVolume(radius))
                else -> Pair(0.0, 0.0)
            }

            showResults(area, volume)
        }
    }

    private fun showResults(area: Double, volume: Double) {
        AlertDialog.Builder(this)
            .setTitle("Calculation Results")
            .setMessage("Area: $area\nVolume: $volume")
            .setIcon(R.drawable.iconcal)
            .setPositiveButton("OK", null)
            .show()
    }


    private fun calculateSphereArea(radius: Double): Double {
        return 4 * PI * radius * radius
    }

    private fun calculateSphereVolume(radius: Double): Double {
        return (4.0 / 3.0) * PI * radius * radius * radius
    }

    private fun calculateConeArea(radius: Double, height: Double): Double {
        return PI * radius * (radius + sqrt(radius * radius + height * height))
    }

    private fun calculateConeVolume(radius: Double, height: Double): Double {
        return (1.0 / 3.0) * PI * radius * radius * height
    }

    private fun calculateCylinderArea(radius: Double, height: Double): Double {
        return 2 * Math.PI * radius * (radius + height)
    }

    private fun calculateCylinderVolume(radius: Double, height: Double): Double {
        return Math.PI * radius * radius * height
    }


}
