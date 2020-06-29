package br.com.mindbet.common.cryptography

import androidx.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AsymmetricCryptographyTest {

	private val ASYMETRIC_KEY = PublicKeyHelper.key


	@Test
	fun generateKeyPair() {
		val keyPair = AsymmetricCryptography.generateKeyPair()
		Assert.assertNotNull("Should not return null", keyPair)
	}

	@Test
	fun encryptWithPublicKey() {
		val keyPair = AsymmetricCryptography.generateKeyPair()
		val data = "Banco Original"
		val encrypted = AsymmetricCryptography.encrypt(data, keyPair.public)
		Assert.assertTrue("Should not return empty string", encrypted?.isNotEmpty() == true)
		Assert.assertNotEquals("Should have created a string differente from the original", encrypted, data)

	}


	@Test
	fun encrypt_moreThanOnce() {
		val keyPair = AsymmetricCryptography.generateKeyPair()
		val data = "Banco Original"
		val encrypted = AsymmetricCryptography.encrypt(data, keyPair.public)
		val encrypted2 = AsymmetricCryptography.encrypt(data, keyPair.public)

		Assert.assertNotEquals("Should have encrypted into different strings", encrypted, encrypted2)
	}


	@Test
	fun encryptWithString() {
		val data = "Banco Original"
		val encrypted = AsymmetricCryptography.encrypt(data, ASYMETRIC_KEY)
		Assert.assertTrue("Should not return empty string", encrypted?.isNotEmpty() == true)
		Assert.assertNotEquals("Should have created a string differente from the original", encrypted, data)
	}


	@Test
	fun encryptWithString_moreThanOnce() {
		val data = "Banco Original"
		val encrypted = AsymmetricCryptography.encrypt(data, ASYMETRIC_KEY)
		val encrypted2 = AsymmetricCryptography.encrypt(data, ASYMETRIC_KEY)

		Assert.assertNotEquals("Should have encrypted into different strings", encrypted, encrypted2)
	}

	@Test
	fun decrypt() {
		val keyPair = AsymmetricCryptography.generateKeyPair()
		val data = "Banco Original"
		val encrypted = AsymmetricCryptography.encrypt(data, keyPair.public)
		val decrypted = AsymmetricCryptography.decrypt(encrypted!!, keyPair.private)

		Assert.assertTrue("Should not return empty string", decrypted?.isNotEmpty() == true)
		Assert.assertEquals("Should have decrypted into expected data", "Banco Original", decrypted)
	}

}