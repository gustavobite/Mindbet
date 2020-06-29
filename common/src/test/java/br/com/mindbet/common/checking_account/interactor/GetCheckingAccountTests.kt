package br.com.mindbet.common.checking_account.interactor

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.checking_account.interactor.list_checking_account.ListCheckingAccounts
import br.com.mindbet.common.checking_account.repository.CheckingAccountRepository
import br.com.mindbet.common.checking_account.service.CheckingAccountService
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

class GetCheckingAccountTests : AutoCloseKoinTest() {
    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private val checkingAccountService = mock(CheckingAccountService::class.java)
    private val checkingAccountRepository = mock(CheckingAccountRepository::class.java)
    private val listCheckingAccounts: ListCheckingAccounts by inject()

    init {
        startKoin { modules(CheckingAccountModule.dependencyModule) }
        declare {
            single { checkingAccountService }
            single { checkingAccountRepository }
        }
    }

    @Before
    fun reset() {
        reset(checkingAccountService, checkingAccountRepository)
    }

    @Test
    fun listFromCache_noCache_serverError() {

        runBlocking {
            doReturn(emptyList<GetCheckingAccountResponse>())
                .`when`(checkingAccountRepository)
                .listAccounts()
            doThrow(IllegalStateException())
                .`when`(checkingAccountService)
                .listAccounts()
            val response = listCheckingAccounts(false)
            assert(response.status == Resource.Status.ERROR)
            verify(checkingAccountRepository, times(1)).listAccounts()
            verify(checkingAccountService, times(1)).listAccounts()
        }
    }

    @Test
    fun listFromCache_noCache_serverSuccess() {

        runBlocking {
            doReturn(emptyList<GetCheckingAccountResponse>())
                .`when`(checkingAccountRepository)
                .listAccounts()
            `when`(checkingAccountService.listAccounts()).thenReturn(CheckingAccountServiceMock.listAccounts())

            val response = listCheckingAccounts(false)
            assert(response.status == Resource.Status.SUCCESS)
            assert(response.data?.size == 6)
            assert(response.data?.get(0)?.uid == "1")
            verify(checkingAccountRepository, times(1)).listAccounts()
            verify(checkingAccountService, times(1)).listAccounts()
            verify(checkingAccountRepository, times(1)).insertAccounts(anyOrNull())
        }
    }

    @Test
    fun listFromCache_withCache() {

        runBlocking {
            doReturn(CheckingAccountServiceMock.listAccounts())
                .`when`(checkingAccountRepository)
                .listAccounts()

            val response = listCheckingAccounts(false)
            assert(response.status == Resource.Status.CACHE)
            assert(response.data?.size == 6)
            assert(response.data?.get(0)?.uid == "1")
            verify(checkingAccountRepository, times(1)).listAccounts()
            verify(checkingAccountService, never()).listAccounts()
        }
    }

    @Test
    fun listFromService_serverError() {
        runBlocking {
            doReturn(Throwable())
                .`when`(checkingAccountService)
                .listAccounts()

            val response = listCheckingAccounts(true)
            assert(response.status == Resource.Status.ERROR)
            verify(checkingAccountService, times(1)).listAccounts()
            verify(checkingAccountRepository, never()).listAccounts()
        }
    }

    @Test
    fun listFromService_serverSuccess() {
        runBlocking {
            doReturn(CheckingAccountServiceMock.listAccounts())
                .`when`(checkingAccountService)
                .listAccounts()

            val response = listCheckingAccounts(true)
            assert(response.status == Resource.Status.SUCCESS)
            assert(response.data?.get(0)?.uid == "1")
            verify(checkingAccountService, times(1)).listAccounts()
            verify(checkingAccountRepository, never()).listAccounts()
            verify(checkingAccountRepository, times(1)).insertAccounts(anyOrNull())
        }
    }
}