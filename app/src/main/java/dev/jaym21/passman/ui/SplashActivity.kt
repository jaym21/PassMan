package dev.jaym21.passman.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.jaym21.passman.utils.Helper

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Helper.getIsFirstRun(this)) {
            val intent = Intent(this, NewLogin::class.java)
            startActivity(intent)
            finish()
        }else {
            val intent = Intent(this, Authentication::class.java)
            startActivity(intent)
            finish()
        }
    }
}