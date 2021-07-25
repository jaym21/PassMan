
package dev.jaym21.passman.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.jaym21.passman.databinding.ActivityServiceOpenBinding
import dev.jaym21.passman.model.Service

class ServiceOpen : AppCompatActivity() {

    private var binding: ActivityServiceOpenBinding? = null
    private var selectedService: Service? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceOpenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        selectedService = intent.getSerializableExtra("selected_service") as Service

        binding?.tvServiceName?.text = selectedService?.name
        binding?.tvServiceUserName?.text = selectedService?.username
        binding?.tvServicePassword?.text = selectedService?.password
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}