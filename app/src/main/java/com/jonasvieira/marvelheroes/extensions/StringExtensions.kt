package com.jonasvieira.marvelheroes.extensions

import java.lang.StringBuilder
import java.security.NoSuchAlgorithmException

fun String.md5(): String {
    try {
        val digest = java.security.MessageDigest.getInstance("MD5")
        digest.update(toByteArray())
        val messageDigest = digest.digest()
        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2)
                h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}