package dev.jaym21.passman.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.jaym21.passman.databinding.ActivityGeneratePasswordBinding

class GeneratePassword : AppCompatActivity() {

    private var binding: ActivityGeneratePasswordBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneratePasswordBinding.inflate(layoutInflater)
        setContentView(binding?.root)


    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}