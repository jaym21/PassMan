package dev.jaym21.passman.utils

import android.app.Activity
import android.content.Context
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object Helper {
    private var FIRST_RUN = "first_run"
    private val KEY = "iCFZRX37SBGIfQc2"

    fun setIsFirstRun(context: Context, isFirstRun: Boolean) {
        val sharedPreferences = context.getSharedPreferences("Helper", Activity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(FIRST_RUN, isFirstRun)
        editor.apply()
    }

    fun getIsFirstRun(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("Helper", Activity.MODE_PRIVATE)
        return sharedPreferences.getBoolean(FIRST_RUN, true)
    }

    fun encrypt(input: String): String{
        //creating a cipher object
        val cipher = Cipher.getInstance("AES")
        //entering key
        val keySpec = SecretKeySpec(KEY.toByteArray(),"AES")
        //initializing cipher
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        //encryption
        val encrypt = cipher.doFinal(input.toByteArray())

        return Base64.encode(encrypt, 0).toString()
    }

    fun decrypt(input: String): String {
        //creating a cipher object
        val cipher = Cipher.getInstance("AES")
        //entering key
        val keySpec = SecretKeySpec(KEY.toByteArray(),"AES")
        //initializing cipher
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        //decryption
        val decrypt = cipher.doFinal(input.toByteArray())

        return decrypt.toString()
    }
}