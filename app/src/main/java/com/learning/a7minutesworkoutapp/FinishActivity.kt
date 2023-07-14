package com.learning.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.learning.a7minutesworkoutapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FinishActivity : AppCompatActivity() {
   private var binding:ActivityFinishBinding? = null
    private var historyDao: HistoryDao?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        historyDao = (application as WorkOutApp).db.historyDao()

        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinishActivity)


            if (supportActionBar != null){
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }

        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        if (historyDao != null){
            onCreateHistoryDatabaseEntry(historyDao!!)
        }

        binding?.btnFinishActivity?.setOnClickListener {
            finish()
        }
    }

    private fun onCreateHistoryDatabaseEntry(historyDao:HistoryDao){
        val calender = Calendar.getInstance()
        val date = calender.time
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val dateTime = sdf.format(date)


        lifecycleScope.launch {
            historyDao.createExercise(HistoryEntity(dateTime))
            Log.e("Date",dateTime)

        }

    }
}
/*

class FinishActivity : AppCompatActivity() {
    //Todo 1: Create a binding variable
    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//Todo 2: inflate the layout

        binding = ActivityFinishBinding.inflate(layoutInflater)
//Todo 3: bind the layout to this Activity

        setContentView(binding?.root)
//Todo 4: attach the layout to this activity

        setSupportActionBar(binding?.toolbarFinishActivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        //END

        //TODO(Step 6 : Adding a click event to the Finish Button.)
        //START
        binding?.btnFinishActivity?.setOnClickListener {
            finish()
        }
    }
}*/