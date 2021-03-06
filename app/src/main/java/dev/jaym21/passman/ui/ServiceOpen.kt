
package dev.jaym21.passman.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dev.jaym21.passman.R
import dev.jaym21.passman.databinding.ActivityServiceOpenBinding
import dev.jaym21.passman.model.Service
import dev.jaym21.passman.utils.Helper
import java.util.*

class ServiceOpen : AppCompatActivity() {

    private var binding: ActivityServiceOpenBinding? = null
    private var selectedService: Service? = null
    lateinit var viewModel: ServiceViewModel
    lateinit var decryptPass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceOpenBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        selectedService = intent.getSerializableExtra("selected_service") as Service

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                ServiceViewModel::class.java
            )

        binding?.btnBack?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
            finish()
        }

        when(selectedService!!.name){
            "Amazon" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_amazon)
            "Apple" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_apple)
            "Dropbox" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_dropbox)
            "Facebook" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_facebook)
            "Flipkart" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_flipkart)
            "Github" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_github)
            "Google" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_google)
            "Google+" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_google_plus)
            "Instagram" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_instagram)
            "LinkedIn" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_linkedin)
            "Myntra" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_myntra)
            "Pinterest" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_pinterest)
            "Quora" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_quora)
            "Reddit" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_reddit)
            "Skype" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_skype)
            "Snapchat" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_snapchat)
            "Soundcloud" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_soundcloud)
            "Spotify" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_spotify)
            "TikTok" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_tiktok)
            "Tumblr" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_tumblr)
            "Twitch" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_twitch)
            "Twitter" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_twitter)
            "Vimeo" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_vimeo)
            "Yahoo" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_yahoo)
            "Youtube" -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_youtube)
            else -> binding?.ivServiceImage?.setImageResource(R.drawable.ic_asterisk)
        }


        binding?.tvServiceName?.text = selectedService?.name
        binding?.tvServiceUserName?.text = selectedService?.username
        decryptPass = Helper.decrypt(selectedService!!.password)!!
        binding?.tvServicePassword?.text = decryptPass


        binding?.ivClipboard?.setOnClickListener {
            copyPasswordToClipboard()
        }

        binding?.btnDelete?.setOnClickListener {
            viewModel.deleteService(selectedService!!)

            //getting the service list from shared preferences
            val serviceList = Helper.getServiceList(this)
            //adding the deleted service from the list
            serviceList?.add(selectedService!!.name)
            //sorting the service list
            serviceList?.sortBy { it }
            //saving the updated service list to shared preferences
            Helper.saveServiceList(this, serviceList)


            //making a snackbar to show that zone is deleted and giving an option to undo the delete
            Snackbar.make(binding?.root!!, "Service deleted", Snackbar.LENGTH_LONG).apply {
                //giving an option to undo
                setAction("Undo") {
                    //storing the zone again in db
                    viewModel.insertService(selectedService!!)

                    //getting the service list from shared preferences
                    val savedServices = Helper.getServiceList(context)
                    //removing the again added service from the list
                    savedServices?.remove(selectedService!!.name)
                    //sorting the service list
                    savedServices?.sortBy { it }
                    //saving the updated service list to shared preferences
                    Helper.saveServiceList(context, savedServices)
                }
                show()
            }
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 3000)
        }

        binding?.btnEdit?.setOnClickListener {
            showEditDialog()
        }
    }

    private fun copyPasswordToClipboard() {
        val passwordToCopy = Helper.decrypt(selectedService!!.password)!!
        //initializing clipboard manager
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        //copying to clipboard
        val clipData = ClipData.newPlainText("text", passwordToCopy)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun showEditDialog() {
        val editDialogView = layoutInflater.inflate(R.layout.edit_dialog, null)
        val editDialog = AlertDialog.Builder(this).create()
        editDialog.apply {
            setView(editDialogView)
            setCancelable(true)
            window?.setBackgroundDrawable(ContextCompat.getDrawable(this@ServiceOpen, R.drawable.edit_dialog_bg))
            show()
        }
        //filling up username and password with old details
        editDialogView.findViewById<EditText>(R.id.etEditUsername).setText(selectedService!!.username)
        editDialogView.findViewById<EditText>(R.id.etEditPassword).setText(decryptPass)

        editDialogView.findViewById<ImageView>(R.id.btnSave).setOnClickListener {
            if (editDialogView.findViewById<EditText>(R.id.etEditUsername).text.isNullOrEmpty()){
                Toast.makeText(this, "Add the username", Toast.LENGTH_SHORT).show()
            } else if (editDialogView.findViewById<EditText>(R.id.etEditPassword).text.isNullOrEmpty()){
                Toast.makeText(this, "Add the password", Toast.LENGTH_SHORT).show()
            } else {
                val encryptedPass = Helper.encrypt(editDialogView.findViewById<EditText>(R.id.etEditPassword).text.toString())
                if (encryptedPass == null){
                    Toast.makeText(this, "Error while encrypting password", Toast.LENGTH_SHORT).show()
                }else {
                    val updatedService = Service(selectedService!!.id, selectedService!!.name, editDialogView.findViewById<EditText>(R.id.etEditUsername).text.toString(), encryptedPass)
                    viewModel.updateService(updatedService)
                    Toast.makeText(this, "Service updated", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}