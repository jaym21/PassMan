package dev.jaym21.passman.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dev.jaym21.passman.R
import dev.jaym21.passman.databinding.ActivityAddServiceBinding
import dev.jaym21.passman.databinding.ActivityMainBinding
import dev.jaym21.passman.model.Service
import dev.jaym21.passman.utils.Helper

class AddService : AppCompatActivity() {

    private var binding: ActivityAddServiceBinding? = null
    lateinit var viewModel: ServiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddServiceBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(ServiceViewModel::class.java)

        val allServices = resources.getStringArray(R.array.services)
        val spinnerAdapter = ArrayAdapter(applicationContext, R.layout.spinner_item, allServices)
        binding?.spinnerServices?.adapter = spinnerAdapter


        binding?.btnAdd?.setOnClickListener {
            if (checkUsernameAndPassword()){
                val encryptPass = Helper.encrypt(binding?.etPassword?.text.toString())
                if (encryptPass == null){
                    Toast.makeText(this, "Error while encrypting password", Toast.LENGTH_SHORT).show()
                }else {
                    val service = Service(0, binding?.spinnerServices?.selectedItem.toString(), binding?.etUsername?.text.toString(), encryptPass)
                    viewModel.insertService(service)
                    Toast.makeText(this, " New ${binding?.spinnerServices?.selectedItem.toString()} service added", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun checkUsernameAndPassword(): Boolean {
        var isCorrect = true
        if (binding?.etUsername?.text.isNullOrEmpty()){
            Toast.makeText(this, "Add the username", Toast.LENGTH_SHORT).show()
            isCorrect = false
        } else if (binding?.etPassword?.text.isNullOrEmpty()){
            Toast.makeText(this, "Add the password", Toast.LENGTH_SHORT).show()
            isCorrect = false
        }
        return isCorrect
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}