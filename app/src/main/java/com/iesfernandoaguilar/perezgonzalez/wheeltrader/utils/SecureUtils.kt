package com.iesfernandoaguilar.perezgonzalez.wheeltrader.utils

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom

class SecureUtils {
    private fun bytesToHex(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (b in bytes) sb.append(((b.toInt() and 0xff) + 0x100).toString(16).substring(1))
        return sb.toString()
    }

    fun generate512(passwordToHash: String, salt: ByteArray?): String? {
        // public static String generate512(String passwordToHash){
        var generatedPassword: String? = null
        try {
            val md = MessageDigest.getInstance("SHA-512")
            md.update(salt)
            val byteOfTextToHash = passwordToHash.toByteArray(StandardCharsets.UTF_8)
            val hashedByteArray = md.digest(byteOfTextToHash)

            generatedPassword = bytesToHex(hashedByteArray)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return generatedPassword
    }

    fun getSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt
    }

    fun main() {
        //Ten en cuenta que ambas contraseñas son iguales porque usamos la misma sal para generar la misma contraseña hash.
        val salt = getSalt()
        for (b in salt) {
            print(b)
        }
        println()

        val password1 = generate512("Password", salt)
        val password2 = generate512("Password", salt)
        println(" Password 1 -> $password1")
        println(" Password 2 -> $password2")
        if (password1 == password2) {
            println("passwords are equal")
        }
    }
}