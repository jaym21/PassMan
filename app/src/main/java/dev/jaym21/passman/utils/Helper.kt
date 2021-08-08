package dev.jaym21.passman.utils

import android.app.Activity
import android.content.Context
import android.util.Base64
import android.util.Log
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object Helper {

    private val TAG = "Helper"
    private var FIRST_RUN = "first_run"
    private const val secretKey = "tK5UTui+DPh8lIlBxya5XVsmeDCoUl6vHhdIESMB6sQ="
    private const val salt = "QWlGNHNhMTJTQWZ2bGhpV3U=" // base64 decode => AiF4sa12SAfvlhiWu
    private const val iv = "bVQzNFNhRkQ1Njc4UUFaWA==" // base64 decode => mT34SaFD5678QAZX

    val servicesArray = mutableListOf("Amazon", "Apple", "Dropbox", "Facebook", "Flipkart",
            "Github", "Google", "Google+", "Instagram", "LinkedIn", "Myntra", "Pinterest", "Reddit",
            "Snapchat", "Soundcloud", "Spotify", "Tumblr", "Twitch", "Twitter", "Youtube")


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




    fun encrypt(strToEncrypt: String):  String?
    {
        try
        {
            val ivParameterSpec = IvParameterSpec(Base64.decode(iv, Base64.DEFAULT))

            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val spec =  PBEKeySpec(secretKey.toCharArray(), Base64.decode(salt, Base64.DEFAULT), 10000, 256)
            val tmp = factory.generateSecret(spec)
            val secretKey =  SecretKeySpec(tmp.encoded, "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
            return Base64.encodeToString(cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)), Base64.DEFAULT)
        }
        catch (e: Exception)
        {
            Log.e(TAG, "Error while encrypting: $e")
        }
        return null
    }

    fun decrypt(strToDecrypt : String): String? {
        try
        {

            val ivParameterSpec =  IvParameterSpec(Base64.decode(iv, Base64.DEFAULT))

            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val spec =  PBEKeySpec(secretKey.toCharArray(), Base64.decode(salt, Base64.DEFAULT), 10000, 256)
            val tmp = factory.generateSecret(spec);
            val secretKey =  SecretKeySpec(tmp.encoded, "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            return  String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)))
        }
        catch (e : Exception) {
            Log.e(TAG, "Error while encrypting: $e")
        }
        return null
    }
}
