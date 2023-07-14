package com.learning.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.learning.a7minutesworkoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch
import java.util.ArrayList

class History : AppCompatActivity() {

    private var binding : ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val historyDao = (application as WorkOutApp).db.historyDao()

        setSupportActionBar(binding?.toolbarHistory)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "History"
        }
        binding?.toolbarHistory?.setNavigationOnClickListener {
            onBackPressed()
        }
        getAllCompletedDates(historyDao)
    }

    private fun getAllCompletedDates(dao:HistoryDao){

        lifecycleScope.launch{
            dao.fetchAllDates().collect{
//                for (i in ArrayList(it)){
//                    Log.e("Date is : ",i.toString())
//                }
                if (it.isNotEmpty()){
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.GONE
                    binding?.tvHistoryHeading?.visibility = View.VISIBLE
                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@History)

                    val dates:ArrayList<String> = ArrayList()

                    for (i in it){
                        dates.add(i.date)
                    }

                    val adapter:HistoryAdapter = HistoryAdapter(dates)
                    binding?.rvHistory?.adapter = adapter
                }else{
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE
                    binding?.tvHistoryHeading?.visibility = View.GONE
                }
            }

        }

    }

    override fun onDestroy() {
        if(binding != null){
            binding = null
        }
        super.onDestroy()
    }
}