package dev.jaym21.passman.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
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
    private var savedServiceList: List<String?> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddServiceBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(ServiceViewModel::class.java)

        savedServiceList = Helper.getServiceList(this)!!.toList()

        val adapter = ArrayAdapter(this, R.layout.spinner_item, savedServiceList)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown)
        binding?.spinnerServices?.adapter = adapter


        binding?.spinnerServices?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (savedServiceList[position] == "Other") {
                    binding?.tilOtherName?.visibility = View.VISIBLE
                }else {
                    binding?.tilOtherName?.visibility = View.GONE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding?.btnBack?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


        binding?.btnAdd?.setOnClickListener {
            //checking if other is selected
            if (binding?.spinnerServices?.selectedItem.toString() == "Other") {
                if (checkUsernameAndPassword()) {
                    if (binding?.etOtherName?.text.isNullOrEmpty()) {
                        Toast.makeText(this, "Enter the service name", Toast.LENGTH_SHORT).show()
                    }else {
                        val encryptPass = Helper.encrypt(binding?.etPassword?.text.toString())
                        if (encryptPass == null) {
                            Toast.makeText(this, "Error while encrypting password", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            val service = Service(
                                0,
                                binding?.etOtherName?.text.toString(),
                                binding?.etUsername?.text.toString(),
                                encryptPass
                            )
                            viewModel.insertService(service)
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
                }
            } else {
                if (checkUsernameAndPassword()) {
                    val encryptPass = Helper.encrypt(binding?.etPassword?.text.toString())
                    if (encryptPass == null) {
                        Toast.makeText(this, "Error while encrypting password", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val service = Service(
                            0,
                            binding?.spinnerServices?.selectedItem.toString(),
                            binding?.etUsername?.text.toString(),
                            encryptPass
                        )
                        viewModel.insertService(service)

                        //getting the service list from shared preferences
                        val serviceList = Helper.getServiceList(this)
                        //removing the added service from the list
                        serviceList?.remove(binding?.spinnerServices?.selectedItem.toString())
                        //sorting the service list
                        serviceList?.sortBy { it }
                        //saving the updated service list to shared preferences
                        Helper.saveServiceList(this, serviceList)

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
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