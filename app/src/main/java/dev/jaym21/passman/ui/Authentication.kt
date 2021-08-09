package dev.jaym21.passman.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dev.jaym21.passman.databinding.ActivityAuthenticationBinding
import dev.jaym21.passman.utils.Helper

class Authentication : AppCompatActivity() {

    private var binding: ActivityAuthenticationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnConfirmAuth?.setOnClickListener {
            if (binding?.etAuthPassword?.text.toString() == Helper.getLockPassword(this)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding?.etAuthPassword?.setText("")
                Toast.makeText(this, "Password incorrect, Try again" , Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}