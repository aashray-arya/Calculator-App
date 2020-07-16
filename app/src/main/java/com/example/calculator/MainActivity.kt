package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var lastNumeric: Boolean = false
    var lastPoint: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View){
        tvInput.append((view as Button).text)
        lastNumeric=true
    }
    fun onClear(view: View){
        tvInput.text = ""
         lastNumeric = false
         lastPoint = false
    }
    fun onDecimal(view: View){
        if (lastNumeric && !lastPoint){
            tvInput.append(".")
            lastNumeric=false
            lastPoint=true
        }
    }
    private fun isOperator(value: String):Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("+") || value.contains("-") ||value.contains("*")
        }
    }
    fun onOperator(view: View){
        if (lastNumeric && !isOperator(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastNumeric=false
            lastPoint=false
        }
    }
    fun onEqual(view: View){
        if (lastNumeric){
            var tvValue=tvInput.text.toString()
            var prefix=""
            try{
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue=tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    var splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two =splitValue[1]
                    if(!prefix.isEmpty()){
                        one=prefix + one
                    }
                    tvInput.text = removeZeros((one.toDouble() - two.toDouble()).toString())
                }
                else if (tvValue.contains("+")){
                    var splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    tvInput.text = removeZeros((one.toDouble() + two.toDouble()).toString())
                }
                else if (tvValue.contains("*")){
                    var splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    tvInput.text = removeZeros((one.toDouble() * two.toDouble()).toString())
                }
                else if (tvValue.contains("/")){
                    var splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    tvInput.text = removeZeros((one.toDouble() / two.toDouble()).toString())
                }

            }
            catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    private fun removeZeros(result:String):String{
        var value = result
        if(result.contains("0"))
            value=result.substring(0,result.length-2)
        return value
    }
}
