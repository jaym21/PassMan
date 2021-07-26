package dev.jaym21.passman.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jaym21.passman.R
import dev.jaym21.passman.adapter.IServiceAdapter
import dev.jaym21.passman.adapter.ServiceAdapter
import dev.jaym21.passman.databinding.ActivityMainBinding
import dev.jaym21.passman.model.Service
import android.content.Intent as Intent

class MainActivity : AppCompatActivity(), IServiceAdapter {

    private var binding: ActivityMainBinding? = null
    lateinit var viewModel: ServiceViewModel
    lateinit var serviceAdapter: ServiceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(ServiceViewModel::class.java)

        setUpRecycleView()

        viewModel.allService.observe({lifecycle}, Observer {
            serviceAdapter.updateList(it)
        })

        binding?.btnAdd?.setOnClickListener {
            startActivity(Intent(this, AddService::class.java))
        }
    }

    private fun setUpRecycleView(){
        serviceAdapter = ServiceAdapter(this, this)
        binding?.rvServices?.apply {
            adapter = serviceAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onServiceClicked(service: Service) {
        val intent = Intent(this, ServiceOpen::class.java)
        intent.putExtra("selected_service", service)
        startActivity(intent)
    }
}