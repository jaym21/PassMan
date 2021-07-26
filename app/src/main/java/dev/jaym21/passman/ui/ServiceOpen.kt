
package dev.jaym21.passman.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dev.jaym21.passman.databinding.ActivityServiceOpenBinding
import dev.jaym21.passman.model.Service

class ServiceOpen : AppCompatActivity() {

    private var binding: ActivityServiceOpenBinding? = null
    private var selectedService: Service? = null
    lateinit var viewModel: ServiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceOpenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                ServiceViewModel::class.java
            )

        selectedService = intent.getSerializableExtra("selected_service") as Service

        binding?.tvServiceName?.text = selectedService?.name
        binding?.tvServiceUserName?.text = selectedService?.username
        binding?.tvServicePassword?.text = selectedService?.password

        binding?.btnDelete?.setOnClickListener {
            viewModel.deleteService(selectedService!!)
            //making a snackbar to show that zone is deleted and giving an option to undo the delete
            Snackbar.make(binding?.root!!, "Service deleted", Snackbar.LENGTH_LONG).apply {
                //giving an option to undo
                setAction("Undo") {
                    //storing the zone again in db
                    viewModel.insertService(selectedService!!)
                }
                show()
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}