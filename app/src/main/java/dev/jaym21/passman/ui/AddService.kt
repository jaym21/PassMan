package dev.jaym21.passman.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dev.jaym21.passman.R
import dev.jaym21.passman.databinding.ActivityAddServiceBinding
import dev.jaym21.passman.databinding.ActivityMainBinding
import dev.jaym21.passman.model.Service

class AddService : AppCompatActivity() {

    private var binding: ActivityAddServiceBinding? = null
    lateinit var viewModel: ServiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddServiceBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(ServiceViewModel::class.java)

        if (checkUsernameAndPassword()){
            val service = Service(1, binding?.spinnerServices?.selectedItem.toString(), binding?.etUsername?.text.toString(), binding?.etPassword?.text.toString())
            viewModel.insertService(service)
            Toast.makeText(this, " New ${binding?.spinnerServices?.selectedItem.toString()} service added", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkUsernameAndPassword(): Boolean {
        var isCorrect = true
        if (binding?.etUsername?.text.isNullOrEmpty()){
            Toast.makeText(this, "Add the username", Toast.LENGTH_SHORT).show()
            isCorrect = false
        }
        if (binding?.etPassword?.text.isNullOrEmpty()){
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