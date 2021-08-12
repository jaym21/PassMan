package dev.jaym21.passman.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.jaym21.passman.R
import dev.jaym21.passman.model.Service
import dev.jaym21.passman.utils.ServiceDiffUtil

class ServiceAdapter(private val listener: IServiceAdapter): RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

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
        when(currentService.name){
            "Amazon" -> Glide.with(holder.itemView.context).load(R.drawable.ic_amazon).into(holder.logo)
            "Apple" -> Glide.with(holder.itemView.context).load(R.drawable.ic_apple).into(holder.logo)
            "Dropbox" -> Glide.with(holder.itemView.context).load(R.drawable.ic_dropbox).into(holder.logo)
            "Facebook" -> Glide.with(holder.itemView.context).load(R.drawable.ic_facebook).into(holder.logo)
            "Flipkart" -> Glide.with(holder.itemView.context).load(R.drawable.ic_flipkart).into(holder.logo)
            "Github" -> Glide.with(holder.itemView.context).load(R.drawable.ic_github).into(holder.logo)
            "Google" -> Glide.with(holder.itemView.context).load(R.drawable.ic_google).into(holder.logo)
            "Google+" -> Glide.with(holder.itemView.context).load(R.drawable.ic_google_plus).into(holder.logo)
            "Instagram" -> Glide.with(holder.itemView.context).load(R.drawable.ic_instagram).into(holder.logo)
            "LinkedIn" -> Glide.with(holder.itemView.context).load(R.drawable.ic_linkedin).into(holder.logo)
            "Myntra" -> Glide.with(holder.itemView.context).load(R.drawable.ic_myntra).into(holder.logo)
            "Pinterest" -> Glide.with(holder.itemView.context).load(R.drawable.ic_pinterest).into(holder.logo)
            "Reddit" -> Glide.with(holder.itemView.context).load(R.drawable.ic_reddit).into(holder.logo)
            "Snapchat" -> Glide.with(holder.itemView.context).load(R.drawable.ic_snapchat).into(holder.logo)
            "Soundcloud" -> Glide.with(holder.itemView.context).load(R.drawable.ic_soundcloud).into(holder.logo)
            "Spotify" -> Glide.with(holder.itemView.context).load(R.drawable.ic_spotify).into(holder.logo)
            "Tumblr" -> Glide.with(holder.itemView.context).load(R.drawable.ic_tumblr).into(holder.logo)
            "Twitch" -> Glide.with(holder.itemView.context).load(R.drawable.ic_twitch).into(holder.logo)
            "Twitter" -> Glide.with(holder.itemView.context).load(R.drawable.ic_twitter).into(holder.logo)
            "Youtube" -> Glide.with(holder.itemView.context).load(R.drawable.ic_youtube).into(holder.logo)
            else -> Glide.with(holder.itemView.context).load(R.drawable.ic_check).into(holder.logo)
        }
    }

    override fun getItemCount(): Int {
        return allServices.size
    }

    //this function is to update the recycler by diffUtil(only changing the changes made observed through viewModel, and not the whole list again)
    fun updateList(newList: List<Service>) {
        val diffUtilCallback = ServiceDiffUtil(allServices, newList)
        //Calculates the list of update operations that can covert old list into new list
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        //adding the new updated list
        allServices = newList
        //updating the adapter
        diffResult.dispatchUpdatesTo(this)
    }
}

interface IServiceAdapter{
    fun onServiceClicked(service: Service)
}