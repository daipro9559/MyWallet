package com.example.dai_pc.android_test.ultil

import android.content.Context
import android.preference.PreferenceManager
import android.util.Base64
import android.util.Log
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.*
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


class PasswordManager {
    companion object {
        private val legacyKey = "35TheTru5tWa11ets3cr3tK3y377123!"
        private val legacyIv = "8201va0184a0md8i"

        val keyStringFromNative: String
            external get

        val ivStringFromNative: String
            external get

        @Throws(NoSuchPaddingException::class, BadPaddingException::class, NoSuchAlgorithmException::class, IllegalBlockSizeException::class, UnsupportedEncodingException::class, InvalidKeyException::class, InvalidKeySpecException::class, InvalidAlgorithmParameterException::class)
        fun setPasswordLegacy(address: String, password: String, context: Context) {
            val key = SecretKeySpec("35TheTru5tWa11ets3cr3tK3y377123!".toByteArray(charset("UTF-8")), "AES")
            val iv = IvParameterSpec("8201va0184a0md8i".toByteArray(charset("UTF-8")))
            val encryptedPassword = encrypt(password, key, iv)
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString("$address-pwd", Base64.encodeToString(encryptedPassword, 0))
            editor.commit()
        }

        @Throws(NoSuchPaddingException::class, BadPaddingException::class, NoSuchAlgorithmException::class, IllegalBlockSizeException::class, UnsupportedEncodingException::class, InvalidKeyException::class, InvalidKeySpecException::class, InvalidAlgorithmParameterException::class)
        fun setPassword(address: String, password: String, context: Context) {
            val key = SecretKeySpec(keyStringFromNative.toByteArray(charset("UTF-8")), "AES")
            val iv = IvParameterSpec(ivStringFromNative.toByteArray(charset("UTF-8")))
            val encryptedPassword = encrypt(password, key, iv)
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString("$address-pwd", Base64.encodeToString(encryptedPassword, 0))
            editor.commit()
        }

        @Throws(NoSuchPaddingException::class, UnsupportedEncodingException::class, NoSuchAlgorithmException::class, IllegalBlockSizeException::class, BadPaddingException::class, InvalidKeyException::class, InvalidKeySpecException::class, InvalidAlgorithmParameterException::class)
        fun getPassword(address: String, context: Context): String {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val encryptedPassword = Base64.decode(sharedPreferences.getString("$address-pwd", null as String?), 0)
            val oldKey = SecretKeySpec("35TheTru5tWa11ets3cr3tK3y377123!".toByteArray(charset("UTF-8")), "AES")
            val oldIv = IvParameterSpec("8201va0184a0md8i".toByteArray(charset("UTF-8")))

            try {
                return decrypt(encryptedPassword, oldKey, oldIv)
            } catch (var9: Exception) {
                Log.e("PASSMAN", var9.message)
                val key = SecretKeySpec(keyStringFromNative.toByteArray(charset("UTF-8")), "AES")
                val iv = IvParameterSpec(ivStringFromNative.toByteArray(charset("UTF-8")))
                return decrypt(encryptedPassword, key, iv)
            }

        }

        @Throws(NoSuchPaddingException::class, NoSuchAlgorithmException::class, InvalidKeyException::class, UnsupportedEncodingException::class, BadPaddingException::class, IllegalBlockSizeException::class, InvalidAlgorithmParameterException::class)
        private fun encrypt(plainText: String, key: SecretKey, iv: IvParameterSpec): ByteArray {
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(1, key, iv)
            return cipher.doFinal(plainText.toByteArray(charset("UTF-8")))
        }

        @Throws(NoSuchPaddingException::class, NoSuchAlgorithmException::class, InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class, UnsupportedEncodingException::class, InvalidAlgorithmParameterException::class)
        private fun decrypt(cipherText: ByteArray, key: SecretKey, iv: IvParameterSpec): String {
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(2, key, iv)
            return String(cipher.doFinal(cipherText), Charset.forName("UTF-8"))
        }

        private fun generateRandomIv(): IvParameterSpec {
            val random = SecureRandom()
            val ivBytes = ByteArray(16)
            random.nextBytes(ivBytes)
            return IvParameterSpec(ivBytes)
        }

        @Throws(NoSuchAlgorithmException::class)
        private fun generateKey(): SecretKey {
            val outputKeyLength: Int = 0
            val secureRandom = SecureRandom()
            val keyGenerator = KeyGenerator.getInstance("AES")
            keyGenerator.init(256, secureRandom)
            return keyGenerator.generateKey()
        }

        @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
        private fun generateKey(keyPhrase: String): SecretKey {
            val random = SecureRandom()
            val salt = ByteArray(16)
            random.nextBytes(salt)
            val spec = PBEKeySpec(keyPhrase.toCharArray(), salt, 65536, 256)
            val keyFac = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            return keyFac.generateSecret(spec)
        }

        init {
            System.loadLibrary("native-lib")
        }
    }
}