package dev.jaym21.passman.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.jaym21.passman.components.ServiceRepo
import dev.jaym21.passman.database.ServiceDatabase
import dev.jaym21.passman.model.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ServiceViewModel(application: Application): AndroidViewModel(application) {
    private val repo: ServiceRepo
    val allService: LiveData<List<Service>>

    init {
        val dao = ServiceDatabase.getDatabase(application).getServiceDAO()
        repo = ServiceRepo(dao)
        allService = repo.getAllService
    }

    fun insertService(service: Service) = viewModelScope.launch(Dispatchers.IO) {
        repo.insertService(service)
    }

    fun deleteService(service: Service) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteService(service)
    }

    fun updateService(service: Service) = viewModelScope.launch(Dispatchers.IO) {
        repo.updateService(service)
    }
}