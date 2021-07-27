
package dev.jaym21.passman.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dev.jaym21.passman.R
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

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
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
        binding?.btnEdit?.setOnClickListener {
            showEditDialog()
        }
    }

    private fun showEditDialog() {
        val editDialogView = layoutInflater.inflate(R.layout.edit_dialog, null)
        val editDialog = AlertDialog.Builder(this).create()
        editDialog.apply {
            setView(editDialogView)
            setCancelable(true)
            window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            show()
        }

        editDialogView.findViewById<Button>(R.id.btnSave).setOnClickListener {
            if (editDialogView.findViewById<EditText>(R.id.etEditUsername).text.isNullOrEmpty()){
                Toast.makeText(this, "Add the username", Toast.LENGTH_SHORT).show()
            } else if (editDialogView.findViewById<EditText>(R.id.etEditPassword).text.isNullOrEmpty()){
                Toast.makeText(this, "Add the password", Toast.LENGTH_SHORT).show()
            } else {
                val updatedService = Service(selectedService!!.id, selectedService!!.name, editDialogView.findViewById<EditText>(R.id.etEditUsername).text.toString(), editDialogView.findViewById<EditText>(R.id.etEditPassword).text.toString())
                viewModel.updateService(updatedService)
                Toast.makeText(this, "Service updated", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}