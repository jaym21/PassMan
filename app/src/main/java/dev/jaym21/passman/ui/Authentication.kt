package dev.jaym21.passman.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import dev.jaym21.passman.databinding.ActivityAuthenticationBinding
import dev.jaym21.passman.utils.GenericTextWatcher
import dev.jaym21.passman.utils.Helper

class Authentication : AppCompatActivity() {

    lateinit var binding: ActivityAuthenticationBinding

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

        binding.btnConfirmAuth.setOnClickListener {
            if (binding.etPass1.text.isNullOrEmpty() || binding.etPass2.text.isNullOrEmpty() || binding.etPass3.text.isNullOrEmpty() || binding.etPass4.text.isNullOrEmpty()) {
                Toast.makeText(this, "Enter the password", Toast.LENGTH_SHORT).show()
            } else {
                val enteredPassword = binding.etPass1.text.toString() + binding.etPass2.text.toString() + binding.etPass3.text.toString() + binding.etPass4.text.toString()
                if (enteredPassword == Helper.getLockPassword(this)) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    binding.etPass1.setText("")
                    binding.etPass2.setText("")
                    binding.etPass3.setText("")
                    binding.etPass4.setText("")
                    Toast.makeText(this, "Incorrect password, try again", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}