package dev.jaym21.passman.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dev.jaym21.passman.databinding.ActivityNewLoginBinding
import dev.jaym21.passman.utils.Helper

class NewLogin : AppCompatActivity() {

    private var binding: ActivityNewLoginBinding? = null
    private val servicesArray = arrayListOf<String?>("Amazon", "Apple", "Dropbox", "Facebook", "Flipkart",
        "Github", "Google", "Google+", "Instagram", "LinkedIn", "Myntra", "Pinterest", "Quora", "Reddit", "Skype",
        "Snapchat", "Soundcloud", "Spotify", "TikTok", "Tumblr", "Twitch", "Twitter", "Vimeo", "Yahoo", "Youtube", "Other")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnAddPassword?.setOnClickListener {
            if (checkPasswordEntered()) {
                if (binding?.etNewPassword?.text.toString() == binding?.etConfirmPassword?.text.toString()) {
                    Helper.setLockPassword(this, binding?.etNewPassword?.text.toString())
                    //making first time run as false
                    Helper.setIsFirstRun(this, false)
                    //adding the service list to the shared preferences
                    Helper.saveServiceList(this, servicesArray)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    binding?.etConfirmPassword?.setText("")
                    Toast.makeText(this, "Password not matching", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkPasswordEntered(): Boolean {
        var isCorrect = true
        if (binding?.etNewPassword?.text.isNullOrEmpty()) {
            Toast.makeText(this, "Add a password", Toast.LENGTH_SHORT).show()
            isCorrect = false
        } else if (binding?.etConfirmPassword?.text.isNullOrEmpty()) {
            Toast.makeText(this, "Confirm the password", Toast.LENGTH_SHORT).show()
            isCorrect = false
        }
        return isCorrect
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}