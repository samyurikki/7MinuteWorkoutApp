package com.learning.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.learning.a7minutesworkoutapp.databinding.ActivityBmicalculatorBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMICalculatorActivity : AppCompatActivity() {

    companion object{
        private  const val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        private  const val US_UNITS_VIEW = "US_UNITS_VIEW"
    }

    private var calculatorBinding:ActivityBmicalculatorBinding? = null

    private var isActiveTab = METRIC_UNITS_VIEW
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calculatorBinding = ActivityBmicalculatorBinding.inflate(layoutInflater)
        setContentView(calculatorBinding?.root)

        setSupportActionBar(calculatorBinding?.bmiToolbar)

        if (supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = "CALCULATE BMI"

        }

        calculatorBinding?.bmiToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

        calculatorBinding?.btnCalculateBMI?.setOnClickListener {
            if (validateMetrics()){
                val heightValue :Float =
                    when(isActiveTab){
                        US_UNITS_VIEW -> {
                            (calculatorBinding?.etUsUnitsFeet?.text.toString().toFloat() * 12+calculatorBinding?.etUsUnitsInches?.text.toString().toFloat())
                        }else->{
                            calculatorBinding?.etMetricUnitHeight?.text.toString().toFloat()/100
                        }

                }

//                    calculatorBinding?.etMetricUnitHeight?.text.toString().toFloat()/100
                val weightValue :Float =
                    when(isActiveTab){
                        US_UNITS_VIEW -> {
                            (calculatorBinding?.etUsUnitWeight?.text.toString().toFloat())
                        }else->{
                        calculatorBinding?.etMetricUnitWeight?.text.toString().toFloat()
                        }
                    }

//                    calculatorBinding?.etMetricUnitWeight?.text.toString().toFloat()
                val bmi =
                    when(isActiveTab){
                        US_UNITS_VIEW -> {
                            703*(weightValue/(heightValue * heightValue))
                        }else->{
                        weightValue/(heightValue*heightValue)
                    }
                    }

                displayBmiValues(bmi)
            }else{
                Toast.makeText(this,"Please enter valid values",Toast.LENGTH_LONG).show()
            }
        }

        calculatorBinding?.rgRadioGroupUnits?.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_metricUnits){
                isActiveTab = METRIC_UNITS_VIEW
                displayMetricUnits()
            }else{
                isActiveTab = US_UNITS_VIEW
                displayUsUnits()
            }
        }


        /*calculatorBinding?.rbUsUnits?.setOnClickListener{
            if (calculatorBinding?.rbUsUnits?.isChecked == true){
                calculatorBinding?.tilHeight!!.visibility = View.INVISIBLE
                calculatorBinding?.llUsUnitsTab!!.visibility = View.VISIBLE
                calculatorBinding?.tilWeight!!.visibility = View.INVISIBLE
            }
        }
        calculatorBinding?.rbMetricUnits?.setOnClickListener {
            if (calculatorBinding?.rbMetricUnits?.isChecked == true) {
                calculatorBinding?.tilHeight!!.visibility = View.VISIBLE
                calculatorBinding?.llUsUnitsTab!!.visibility = View.INVISIBLE
                calculatorBinding?.tilWeight!!.visibility = View.VISIBLE
            }
        }*/

    }

    private fun displayMetricUnits(){
        calculatorBinding?.llBmiCalculator?.visibility = View.INVISIBLE
        calculatorBinding?.tilHeight!!.visibility = View.VISIBLE
        calculatorBinding?.llUsUnitsTab!!.visibility = View.INVISIBLE
        calculatorBinding?.tilWeight!!.visibility = View.VISIBLE

        calculatorBinding?.etUsUnitWeight?.text?.clear()
        calculatorBinding?.etUsUnitsFeet?.text?.clear()
        calculatorBinding?.etUsUnitsInches?.text?.clear()
    }

    private fun displayUsUnits(){
        calculatorBinding?.llBmiCalculator?.visibility = View.INVISIBLE
        calculatorBinding?.tilHeight!!.visibility = View.GONE
        calculatorBinding?.llUsUnitsTab!!.visibility = View.VISIBLE
        calculatorBinding?.tilWeight!!.visibility = View.GONE

        calculatorBinding?.etMetricUnitWeight?.text?.clear()
        calculatorBinding?.etMetricUnitHeight?.text?.clear()

    }

    private fun displayBmiValues(bmi: Float) {
        val bmiLabel:String
        val bmiDescription:String
        if (bmi.compareTo(15f)<=0){
            bmiLabel = "Very Severely Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself. Eat more."
        }
        else if (bmi.compareTo(15f)>=0 && bmi.compareTo(16f)<=0){
            bmiLabel = "Severely Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself. Eat more."
        }else if (bmi.compareTo(16f)>0 && bmi.compareTo(18.5f)<=0){
            bmiLabel = "Under weight"
            bmiDescription = "Oops! You really need to take better care of yourself. Eat more."
        }else if (bmi.compareTo(18.5f)>0 && bmi.compareTo(25f)<=0){
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are ina good shape."
        }else if (bmi.compareTo(25f)>0 && bmi.compareTo(30f)<=0){
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take better care of yourself.Workout Daily."

        }else if (bmi.compareTo(30f)>0 && bmi.compareTo(35f)<=0){
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        }else if(bmi.compareTo(35f)>0 && bmi.compareTo(40f)<=0){
            bmiLabel = "Obese Class || (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        }else{
            bmiLabel = "Obese Class ||| (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        }
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        calculatorBinding?.tvBmi?.text = bmiValue
        calculatorBinding?.tvBmiType?.text = bmiLabel
        calculatorBinding?.tvBmiDescription?.text = bmiDescription
        calculatorBinding?.llBmiCalculator?.visibility = View.VISIBLE
    }

    private fun validateMetrics():Boolean {
        var isValid = true
        if (isActiveTab == METRIC_UNITS_VIEW){
            if (calculatorBinding?.etMetricUnitWeight?.text.toString().isEmpty()){
                isValid = false
                Toast.makeText(this,"Please enter valid weight value in kg",Toast.LENGTH_LONG).show()

            }else if (calculatorBinding?.etMetricUnitHeight?.text?.toString()!!.isEmpty()) {
                isValid = false
                Toast.makeText(this,"Please enter valid Height value in cm",Toast.LENGTH_LONG).show()

            }
        }
        else{
            if (calculatorBinding?.etUsUnitWeight?.text.toString().isEmpty()){
                isValid = false
                Toast.makeText(this,"Please enter valid weight value in pounds",Toast.LENGTH_LONG).show()

            }else if (calculatorBinding?.etUsUnitsFeet?.text.toString().isEmpty()){
                isValid = false
                Toast.makeText(this,"Please enter valid height value in Feet",Toast.LENGTH_LONG).show()

            }else if (calculatorBinding?.etUsUnitsInches?.text.toString().isEmpty()){
                isValid = false
                Toast.makeText(this,"Please enter valid height value in inches",Toast.LENGTH_LONG).show()
            }
        }
        return isValid
    }
}