package dev.jaym21.passman.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.Toast
import dev.jaym21.passman.databinding.ActivityAuthenticationBinding
import dev.jaym21.passman.utils.GenericTextWatcher
import dev.jaym21.passman.utils.Helper

class Authentication : AppCompatActivity() {

    lateinit var binding: ActivityAuthenticationBinding
    lateinit var mainHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //getting focus to first edit text
        binding.etPass1.requestFocus()
        //bringing up keyboard
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        val edit = arrayListOf(binding.etPass1, binding.etPass2, binding.etPass3, binding.etPass4)

        binding.etPass1.addTextChangedListener(GenericTextWatcher(binding.etPass1, edit))
        binding.etPass2.addTextChangedListener(GenericTextWatcher(binding.etPass2, edit))
        binding.etPass3.addTextChangedListener(GenericTextWatcher(binding.etPass3, edit))
        binding.etPass4.addTextChangedListener(GenericTextWatcher(binding.etPass4, edit))


        //initializing a handler
        mainHandler = Handler(Looper.getMainLooper())
        //checking if the password every one second
        mainHandler.post(object: Runnable {
            override fun run() {
                if (!checkPassword())
                    mainHandler.postDelayed(this, 1000)
            }
        })
    }

    //checking if the entered password matches the set password
    fun checkPassword(): Boolean {
        if (binding.etPass1.text.isNotEmpty() && binding.etPass2.text.isNotEmpty() && binding.etPass3.text.isNotEmpty() && binding.etPass4.text.isNotEmpty()) {
            val enteredPassword = binding.etPass1.text.toString() + binding.etPass2.text.toString() + binding.etPass3.text.toString() + binding.etPass4.text.toString()
            if (enteredPassword == Helper.getLockPassword(this)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                return true
            } else {
                binding.etPass1.setText("")
                binding.etPass2.setText("")
                binding.etPass3.setText("")
                binding.etPass4.setText("")
                binding.etPass1.requestFocus()
                Toast.makeText(this, "Incorrect password, try again", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return false
    }

}