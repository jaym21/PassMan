package dev.jaym21.passman.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dev.jaym21.passman.databinding.ActivityChangePasscodeBinding
import dev.jaym21.passman.utils.Helper

class ChangePasscode : AppCompatActivity() {

    private var binding: ActivityChangePasscodeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasscodeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnAddNewPasscode?.setOnClickListener {
            if (checkOldAndNewPasscode()) {
                if (binding?.etOldPasscode?.text.toString() == Helper.getLockPassword(this)) {
                    if (binding?.etOldPasscode?.text.toString() != binding?.etNewPasscode?.text.toString()) {
                        Helper.setLockPassword(this, binding?.etNewPasscode?.text.toString())
                        Toast.makeText(this, "Passcode changed", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }else {
                        binding?.etNewPasscode?.setText("")
                        Toast.makeText(this, "Cannot set new passcode same as old passcode", Toast.LENGTH_LONG).show()
                    }
                }else {
                    binding?.etOldPasscode?.setText("")
                    Toast.makeText(this, "Old passcode entered is incorrect, try again", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkOldAndNewPasscode(): Boolean {
        var isCorrect = true
        if (binding?.etOldPasscode?.text.isNullOrEmpty()){
            Toast.makeText(this, "Enter old passcode", Toast.LENGTH_SHORT).show()
            isCorrect = false
        } else if (binding?.etNewPasscode?.text.isNullOrEmpty()){
            Toast.makeText(this, "Enter new passcode", Toast.LENGTH_SHORT).show()
            isCorrect = false
        }
        return isCorrect
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}