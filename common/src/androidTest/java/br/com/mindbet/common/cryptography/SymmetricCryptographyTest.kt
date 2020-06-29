package br.com.mindbet.common.cryptography

import androidx.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest

@RunWith(AndroidJUnit4::class)
class SymmetricCryptographyTest : AutoCloseKoinTest() {

    @Test
    fun encrypt() {
        val data = "Banco Original"
        val key = "12345678901234567890123456789012"
        val encrypted = SymmetricCryptography.encrypt(data, key)
        Assert.assertTrue("Should not return empty string", encrypted.isNotEmpty())
        Assert.assertNotEquals("Should have created a string different from the original", encrypted, data)
    }

    @Test
    fun encrypt_moreThanOnce() {
        val data = "Banco Original"
        val key = "12345678901234567890123456789012"
        val encrypted = SymmetricCryptography.encrypt(data, key)
        val encrypted2 = SymmetricCryptography.encrypt(data, key)

        Assert.assertNotEquals("Should have encrypted into different strings", encrypted, encrypted2)
    }

    @Test
    fun decrypt() {
        val data = "E17V3n4ZTG4mk+Boij6ezc5h3J0mH5/zKBugZlj8SL4="
        val key = "12345678901234567890123456789012"
        val decrypted = SymmetricCryptography.decrypt(data, key)
        Assert.assertTrue("Should not return empty string", decrypted.isNotEmpty())
        Assert.assertEquals("Should have decrypted into expected data", "Banco Original", decrypted)
    }

    @Test
    fun encrypt_decrypt() {
        val data = "Banco Original"
        val key = "98765432109876543210987654321012"
        val encrypted = SymmetricCryptography.encrypt(data, key)
        val decrypted = SymmetricCryptography.decrypt(encrypted, key)

        Assert.assertEquals("Should have decrypted into expected string", data, decrypted)
    }
}