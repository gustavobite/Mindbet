package br.com.mindbet.common.checking_account.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mindbet.common.checking_account.service.CheckingAccountServiceMock
import br.com.mindbet.common.checking_account.service.model.GetCheckingAccountResponse
import br.com.mindbet.common.dependency.CheckingAccountModule
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declare
import org.mockito.Mockito.*

class CheckingAccountRespositoryTests : AutoCloseKoinTest() {
    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private val checkingAccountDao = mock(CheckingAccountDao::class.java)
    private val checkingAccountRepository: CheckingAccountRepository by inject()

    init {
        startKoin { modules(CheckingAccountModule.dependencyModule) }
        declare {
            single { checkingAccountDao }
        }
    }

    @Before
    fun reset() {
        reset(checkingAccountDao)
    }

    @Test
    fun listContacts_withError() {
        runBlocking {
            doThrow(IllegalStateException())
                .`when`(checkingAccountDao)
                .listAccounts()
            val response = checkingAccountRepository.listAccounts()
            assert(response.isEmpty())
            verify(checkingAccountDao, times(1)).listAccounts()
        }
    }

    @Test
    fun listContacts_withItems() {
        runBlocking {
            doReturn(CheckingAccountServiceMock.listAccounts())
                .`when`(checkingAccountDao)
                .listAccounts()
            val response = checkingAccountRepository.listAccounts()
            assert(response.isNotEmpty())
            assert(response.size == 6)
            assert(response[0].uid == "1")
            verify(checkingAccountDao, times(1)).listAccounts()
        }
    }

    @Test
    fun listContacts_withNoItems() {
        runBlocking {
            doReturn(emptyList<List<GetCheckingAccountResponse>>())
                .`when`(checkingAccountDao)
                .listAccounts()
            val response = checkingAccountRepository.listAccounts()
            assert(response.isEmpty())
            verify(checkingAccountDao, times(1)).listAccounts()
        }
    }

    @Test
    fun insertContacts_withError() {
        runBlocking {
            checkingAccountRepository.insertAccounts(CheckingAccountServiceMock.listAccounts())
            verify(checkingAccountDao, times(1)).insertAccounts(anyOrNull())
        }
    }

    @Test
    fun insertContacts_withSuccess() {
        runBlocking {
            `when`(checkingAccountDao.insertAccounts(anyOrNull()))
                .thenReturn(Unit)
            checkingAccountRepository.insertAccounts(CheckingAccountServiceMock.listAccounts())
            verify(checkingAccountDao, times(1)).insertAccounts(anyOrNull())
        }
    }


}