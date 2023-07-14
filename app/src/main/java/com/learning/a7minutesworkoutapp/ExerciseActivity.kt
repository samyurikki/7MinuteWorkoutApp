package com.learning.a7minutesworkoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.learning.a7minutesworkoutapp.databinding.ActivityExerciseBinding
import com.learning.a7minutesworkoutapp.databinding.CustomDialogBinding
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding:ActivityExerciseBinding? = null
    private var countDownTimer:CountDownTimer? = null
    private var exerciseTimer:CountDownTimer? = null
    private var exerciseAdapter:ExerciseStatusAdapter? = null
    var pauseOffset = 0
    val timerDuration:Long = 10
    var pauseOffsetExercise = 0
    val timerDurationExercise:Long = 30
    private var exerciseList:ArrayList<ExerciseModel>? =  null
    var currentExercisePosition = -1

    private var tts:TextToSpeech? = null

    private var mediaPlayer:MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exerciseList = Constants.defaultExerciseList()
        binding = ActivityExerciseBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        tts = TextToSpeech(this,this)


        setRestTimer()


        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener {
            displayCustomDialog()
        }
        binding?.flProgressBarExercise?.visibility = View.GONE
    }

    private fun displayCustomDialog() {
//        TODO("Not yet implemented")
        val dialog = Dialog(this)
        val dialogBinding = CustomDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            dialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    private fun setUpExerciseRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    override fun onBackPressed() {
        displayCustomDialog()
//        super.onBackPressed()
    }
    private fun setRestTimer(){
        try{
            val soundUri = Uri.parse("android.resource://com.learning.a7minutesworkoutapp/"+R.raw.app_src_main_res_raw_press_start)
            mediaPlayer = MediaPlayer.create(this,soundUri)
            mediaPlayer?.isLooping = false
            mediaPlayer?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }

        binding?.flRestViewProgressBar?.visibility = View.VISIBLE
        binding?.flProgressBarExercise?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.ivExercise?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName!!.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName!!.text = exerciseList!![currentExercisePosition+1].getName()
        binding?.tvUpcomingTitle!!.visibility = View.VISIBLE

//        speakOut("Upcoming Exercise is ${exerciseList!![currentExercisePosition+1].getName()}")

        if (countDownTimer != null){
            countDownTimer?.cancel()
            countDownTimer = null
            pauseOffset = 0

        }
        setRestProgressView()
    }

    private fun setExerciseTimer(){
        binding?.flRestViewProgressBar?.visibility = View.INVISIBLE
        binding?.flProgressBarExercise?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.ivExercise?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName!!.visibility = View.INVISIBLE
        binding?.tvUpcomingTitle!!.visibility = View.INVISIBLE

        speakOut(exerciseList!![currentExercisePosition].getName())


        binding?.ivExercise?.setImageResource(exerciseList!![currentExercisePosition].getImage())

        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()

        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseTimer = null
            pauseOffsetExercise = 0

        }

        setUpExerciseRecyclerView()
        startExerciseTimer()
    }

    private fun setRestProgressView(){
        countDownTimer = object:CountDownTimer(timerDuration*1000,1000){
            override fun onTick(millisUntilFinished:Long){
//                pauseOffset = timerDuration - millisUntilFinished
                pauseOffset++
//                binding?.tvTimer?.text = (millisUntilFinished/1000).toString()
                binding?.tvTimer?.text = (10-pauseOffset).toString()
                binding?.ProgressBar?.progress = (10-pauseOffset)

            }

            override fun onFinish(){
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter?.notifyItemChanged(currentExercisePosition)
                setExerciseTimer()
            }
        }.start()
    }


    private fun startExerciseTimer(){
        exerciseTimer = object:CountDownTimer(timerDurationExercise*1000,1000){
            override fun onTick(millisUntilFinished:Long){
//                pauseOffset = timerDuration - millisUntilFinished
                pauseOffsetExercise++
//                binding?.tvTimer?.text = (millisUntilFinished/1000).toString()
                binding?.tvTimerExercise?.text = (30-pauseOffsetExercise).toString()
                binding?.ProgressBarExercise?.progress = (30-pauseOffsetExercise)
            }

            override fun onFinish() {
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseAdapter?.notifyItemChanged(currentExercisePosition)

                if (currentExercisePosition < exerciseList!!.size-1){
                    setRestTimer()
                }
                else{
                    finish()
                    val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(applicationContext,"Completed",Toast.LENGTH_LONG).show()
                }
            }
            }.start()
        }

    override fun onDestroy() {
        super.onDestroy()
        if(countDownTimer!=null){
            countDownTimer?.cancel()
            pauseOffset = 0
        }
        binding = null
        currentExercisePosition = 0
        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            pauseOffsetExercise = 0
        }
        if(tts!=null){
            tts?.stop()
            tts?.shutdown()
        }
        if(mediaPlayer != null){
            mediaPlayer?.stop()
        }

//        if (exerciseAdapter != null){
//            exerciseAdapter = null
//        }

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("tts","The Language Specified Is Not Supported")
            }
            else{
                Log.e("tts","Initialization Failed")
            }
        }
    }

    private fun speakOut(text:String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

}