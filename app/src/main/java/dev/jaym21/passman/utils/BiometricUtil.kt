package dev.jaym21.passman.utils

import android.content.Context
import androidx.biometric.BiometricManager

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
}