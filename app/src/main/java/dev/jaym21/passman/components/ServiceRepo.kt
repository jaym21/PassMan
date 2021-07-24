package dev.jaym21.passman.components

import androidx.lifecycle.LiveData
import dev.jaym21.passman.database.ServiceDAO
import dev.jaym21.passman.model.Service

class ServiceRepo(private val serviceDAO: ServiceDAO) {

    suspend fun insertService(service: Service) = serviceDAO.insertService(service)

    suspend fun deleteService(service: Service) = serviceDAO.deleteService(service)

    suspend fun updateService(service: Service) = serviceDAO.updateService(service)

    val getAllService: LiveData<List<Service>> = serviceDAO.getAllServices()
}