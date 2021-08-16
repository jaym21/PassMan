package dev.jaym21.passman.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.jaym21.passman.R
import dev.jaym21.passman.adapter.IServiceAdapter
import dev.jaym21.passman.adapter.ServiceAdapter
import dev.jaym21.passman.databinding.ActivityMainBinding
import dev.jaym21.passman.model.Service
import java.util.*
import kotlin.system.exitProcess
import android.content.Intent as Intent

class MainActivity : AppCompatActivity(), IServiceAdapter {

    private var binding: ActivityMainBinding? = null
    lateinit var viewModel: ServiceViewModel
    lateinit var serviceAdapter: ServiceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //TODO: lock app when app is paused stopped or not killed

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(ServiceViewModel::class.java)

        setUpRecycleView()

        val bottomSheetFragment = BottomSheetFragment()

        viewModel.allService.observe({lifecycle}, Observer { services ->
            services.sortedBy {
                it.name
            }
            serviceAdapter.updateList(services)
            runRecyclerViewAnimation(binding?.rvServices!!)
        })

        binding?.btnAdd?.setOnClickListener {
            startActivity(Intent(this, AddService::class.java))
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
        }

        binding?.btnExpandSheet?.setOnClickListener {
            bottomSheetFragment.show(supportFragmentManager, "BottomSheet")
        }
    }

    private fun setUpRecycleView(){
        serviceAdapter = ServiceAdapter(this)
        binding?.rvServices?.apply {
            adapter = serviceAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun runRecyclerViewAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.rv_layout_bottom_slide_in)
        recyclerView.layoutAnimation = controller
        recyclerView.scheduleLayoutAnimation()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val homeIntent =  Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
        startActivity(homeIntent);

    }

    override fun onStart() {
        super.onStart()
        runRecyclerViewAnimation(binding?.rvServices!!)
    }

    override fun onRestart() {
        super.onRestart()
        runRecyclerViewAnimation(binding?.rvServices!!)
    }

    override fun onResume() {
        super.onResume()
        runRecyclerViewAnimation(binding?.rvServices!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onServiceClicked(service: Service) {
        val intent = Intent(this, ServiceOpen::class.java)
        intent.putExtra("selected_service", service)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
    }
}