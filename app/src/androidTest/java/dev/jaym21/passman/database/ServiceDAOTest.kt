package dev.jaym21.passman.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import dev.jaym21.passman.getOrAwaitValue
import dev.jaym21.passman.model.Service
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ServiceDAOTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ServiceDatabase
    private lateinit var dao: ServiceDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ServiceDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getServiceDAO()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertService() = runBlockingTest {
        val service = Service(1, "abc", "xyz", "1234")
        dao.insertService(service)

        val allServices = dao.getAllServices().getOrAwaitValue()

        assertThat(allServices).contains(service)
    }

    @Test
    fun deleteService() = runBlockingTest {
        val service = Service(1, "abc", "xyz", "1234")
        dao.insertService(service)
        dao.deleteService(service)

        val allService = dao.getAllServices().getOrAwaitValue()

        assertThat(allService).doesNotContain(service)
    }

    @Test
    fun updateService() = runBlockingTest {
        val service = Service(1, "abc", "xyz", "1234")
        dao.insertService(service)
        val updateService = Service(1, "xyz", "abc", "4321")
        dao.updateService(updateService)

        val allService = dao.getAllServices().getOrAwaitValue()

        assertThat(allService).contains(updateService)
    }
}