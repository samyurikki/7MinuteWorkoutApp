package com.learning.a7minutesworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learning.a7minutesworkoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var viewBinding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

//        val startButton:FrameLayout = findViewById(R.id.fl_start)

        viewBinding?.flStart?.setOnClickListener {
            val intent = Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }

        viewBinding?.flBmi?.setOnClickListener {
            val intent = Intent(this,BMICalculatorActivity::class.java)
            startActivity(intent)
        }

        viewBinding?.flHistory?.setOnClickListener {
            val intent = Intent(this,History::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}