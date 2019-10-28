package br.com.beertime

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.beertime.infra.factory.RepositoryFactory
import br.com.beertime.utils.isInternetAvailable
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

/**
 * Created by Artur on 28/10/2019.
 */
@RunWith(AndroidJUnit4::class)
class BeerListTest {

    @Test
    fun wifiTest() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertTrue("Deve existir conexão com alguma rede", isInternetAvailable(appContext))
    }

    @Test
    fun repositoryTest() {
        val repository = RepositoryFactory.buildBeerRepository()
        runBlocking {
            try {
                val response = repository.getBeerList().await()
                assertTrue("A requisição não deve retornar erro",
                    response.isSuccessful)
                assertTrue("O corpo do response não deve ser nulo",
                    response.body() != null)
            } catch (e: Exception) {
                fail("Ocorreu uma falha com a exceção: ${e.message}")
            }
        }
    }

    @Test
    fun punkapiServiceTest() {
        val repository = RepositoryFactory.buildBeerRepository()
        runBlocking {
            try {
                val response = repository.getPagedBeerList(1).await()
                assertTrue("A requisição não deve retornar erro",
                    response.isSuccessful)
                assertTrue("O corpo do response não deve ser nulo",
                    response.body() != null)
                assertTrue("A requisição deveria trazer 25 itens",
                    response.body()!!.size == 25)
            } catch (e: Exception) {
                fail("Ocorreu uma falha com a exceção: ${e.message}")
            }
        }
    }
}