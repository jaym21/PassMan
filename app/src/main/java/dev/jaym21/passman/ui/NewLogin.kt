package dev.jaym21.passman.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.jaym21.passman.databinding.ActivityNewLoginBinding

class NewLogin : AppCompatActivity() {

    private var binding: ActivityNewLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)


    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}