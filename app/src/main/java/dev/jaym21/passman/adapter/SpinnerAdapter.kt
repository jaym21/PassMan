package dev.jaym21.passman.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import dev.jaym21.passman.R
import dev.jaym21.passman.model.ServiceSpinner

class SpinnerAdapter(context: Context): ArrayAdapter<ServiceSpinner>(context, 0, ServiceSpinner.values()) {

    val layoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.spinner_item, parent, false)
        } else {
            view = convertView
        }

        getItem(position)?.let {
            setIte
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view:View
        if (position == 0){
            view = layoutInflater.inflate(R.layout.)
        }
    }
}