package dev.jaym21.passman.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.jaym21.passman.databinding.ActivityChangePasscodeBinding

class ChangePasscode : AppCompatActivity() {

    private var binding: ActivityChangePasscodeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasscodeBinding.inflate(layoutInflater)
        setContentView(binding?.root)


    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}