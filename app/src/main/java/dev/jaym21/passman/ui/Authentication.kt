package dev.jaym21.passman.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.jaym21.passman.databinding.ActivityAuthenticationBinding

class Authentication : AppCompatActivity() {

    private var binding: ActivityAuthenticationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding?.root)


    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}