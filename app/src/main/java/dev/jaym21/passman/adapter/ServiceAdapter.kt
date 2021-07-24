package dev.jaym21.passman.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.jaym21.passman.R
import dev.jaym21.passman.model.Service

class ServiceAdapter(private val context: Context, private val listener: IServiceAdapter): RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    private var allServices = emptyList<Service>()

    inner class ServiceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val logo: ImageView = itemView.findViewById(R.id.ivImage)
        val layout: RelativeLayout = itemView.findViewById(R.id.rlService)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val viewHolder = ServiceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.service_item_layout, parent, false))
        viewHolder.layout.setOnClickListener {
            listener.onServiceClicked(allServices[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val currentService = allServices[position]
        holder.name.text = currentService.name
        Glid
    }

    override fun getItemCount(): Int {
        return allServices.size
    }

}

interface IServiceAdapter{
    fun onServiceClicked(service: Service)
}