package dev.jaym21.passman.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import dev.jaym21.passman.R

class GenericTextWatcher(var view: View, var editText: ArrayList<EditText>): TextWatcher {


    override fun afterTextChanged(editable: Editable?) {
        val text = editable.toString()
        when(view.id) {
            R.id.etPass1 -> {
                if (text.length == 1)
                    editText[1].requestFocus()
            }
            R.id.etPass2 -> {
                if (text.length == 1)
                    editText[2].requestFocus()
                else if (text.isEmpty())
                    editText[0].requestFocus()
            }
            R.id.etPass3 -> {
                if (text.length == 1)
                    editText[3].requestFocus()
                else if (text.isEmpty())
                    editText[1].requestFocus()
            }
            R.id.etPass4 -> {
                if (text.isEmpty())
                    editText[2].requestFocus()
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

}