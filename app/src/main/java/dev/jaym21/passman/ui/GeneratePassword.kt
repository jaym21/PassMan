package dev.jaym21.passman.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import dev.jaym21.passman.databinding.ActivityGeneratePasswordBinding
import java.lang.StringBuilder
import java.security.SecureRandom

class GeneratePassword : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private var binding: ActivityGeneratePasswordBinding? = null
    private val letters : String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val numbers : String = "0123456789"
    private val symbols : String = "@#=+!£$%&?"
    private var withLetters: Boolean = false
    private var withNumbers: Boolean = false
    private var withSymbols: Boolean = false
    private var selectedLength: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneratePasswordBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.seekBarLength?.setOnSeekBarChangeListener(this)

        //clearing generated password text view
        binding?.tvGenerated?.text = ""

        binding?.btnGenerate?.setOnClickListener {
            val password = generatePassword(binding?.swLetters?.isChecked!!, binding?.swNumbers?.isChecked!!, binding?.swSymbols?.isChecked!!, selectedLength)
            binding?.tvGenerated?.text = password
        }
    }

    private fun generatePassword(isWithLetters: Boolean, isWithNumbers: Boolean, isWithSymbols: Boolean, length: Int): String {
        var allAvailable = ""

        //adding all the selected type in a string
        if (isWithLetters)
            allAvailable += letters
        if (isWithNumbers)
            allAvailable += numbers
        if (isWithSymbols)
            allAvailable += symbols

        val rnd = SecureRandom.getInstance("SHA1PRNG")
        val result = StringBuilder(length)
        
        if (allAvailable.length <= 0){
            Toast.makeText(this, "Select at least one option from letters, numbers or symbols", Toast.LENGTH_LONG).show()
        }else {
            var i = 0
            while (i < length) {
                //getting a random position number from the total length of allAvailable
                val randomPos = rnd.nextInt(allAvailable.length)
                //adding the character at the randomPos in allAvailable to result
                result.append(allAvailable[randomPos])
                i++
            }
        }

        return result.toString()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
        binding?.tvSeekValue?.text = progress.toString()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        selectedLength = seekBar?.progress!!
    }

    override fun onRestart() {
        super.onRestart()
        //clearing generated password text view
        binding?.tvGenerated?.text = ""
    }

    override fun onResume() {
        super.onResume()
        //clearing generated password text view
        binding?.tvGenerated?.text = ""
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}