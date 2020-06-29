package br.com.mindbet.common.base

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

class ResourceTest {

	@Test
	fun success_withData() {
		val data = Resource.success(10)
		assertResource(data, Resource.Status.SUCCESS, 10)
	}

	@Test
	fun success_withNull() {
		val data = Resource.success(null)
		assertResource(data, Resource.Status.SUCCESS)
	}

	@Test
	fun cache_withData() {
		val data = Resource.cache(10)
		assertResource(data, Resource.Status.CACHE, 10)
	}

	@Test
	fun cache_withNull() {
		val data = Resource.cache(null)
		assertResource(data, Resource.Status.CACHE)
	}

	@Test
	fun loading_withData() {
		val data = Resource.loading(10)
		assertResource(data, Resource.Status.LOADING, 10)
	}

	@Test
	fun loading_withNull() {
		val data : Resource<Int> = Resource.loading()
		assertResource(data, Resource.Status.LOADING)
	}

	@Test
	fun error_withData() {
		val exception = Exception()
		val data : Resource<Int> = Resource.error(exception)
		assertResource(data, Resource.Status.ERROR, null, exception)
	}

	@Test
	fun error_withNull() {
		val data : Resource<Int> = Resource.error(null)
		assertResource(data, Resource.Status.ERROR, null, null)
	}

	@Test
	fun transform() {
		val data = Resource.success(10)
		val newData = data.transform(50)
		assertEquals("Should keep status", data.status, newData.status)
		assertEquals("Should keep error", data.exception, newData.exception)
	}

	private fun <T> assertResource(resource: Resource<T>, status: Resource.Status, data: T? = null, error: Exception? = null) {
		assertNotNull(resource)
		assertEquals("Should return expected status", resource.status, status)
		assertEquals("Should return expected data", resource.data, data)
		assertEquals("Should return expected error", resource.exception, error)
	}

}