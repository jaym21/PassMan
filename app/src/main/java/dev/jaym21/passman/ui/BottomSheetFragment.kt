package dev.jaym21.passman.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.jaym21.passman.R

class BottomSheetFragment: BottomSheetDialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val passcodeChange = view.findViewById<LinearLayout>(R.id.llChangePasscode)
        val generatePassword = view.findViewById<LinearLayout>(R.id.llGeneratePassword)

        passcodeChange.setOnClickListener {
            val intentChange = Intent(context, ChangePasscode::class.java)
            startActivity(intentChange)
        }


        generatePassword.setOnClickListener {
            Toast.makeText(context, "GeneratePassword", Toast.LENGTH_SHORT).show()
        }
    }
}