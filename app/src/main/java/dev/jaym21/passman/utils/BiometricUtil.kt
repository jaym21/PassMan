package dev.jaym21.passman.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

object BiometricUtil {

    //to check if any kind of biometric is available namely fingerprint or face
    fun isBiometricsAvailable(context: Context): Boolean {
        val biometricManager = BiometricManager.from(context)

        val res =  biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        if (res == BiometricManager.BIOMETRIC_SUCCESS) {
            return true
        }
        return false
    }

    //setting up biometric prompt
    fun setBiometricPrompt(title: String, subtitle: String, allowDeviceCredential: Boolean): BiometricPrompt.PromptInfo {
        val builder = BiometricPrompt.PromptInfo.Builder()
                .setTitle(title)
                .setTitle(subtitle)

        //checking if Device Credentials are required i.e using the passcode set on device lock
        builder.apply {
            if (allowDeviceCredential)
                setDeviceCredentialAllowed(true)
            else
                setNegativeButtonText("User Passcode")
        }

        return builder.build()
    }
}