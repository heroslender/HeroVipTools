package com.heroslender.viptools.storage

import com.heroslender.viptools.Loja

open class StorageCache {
    val cache = mutableMapOf<String, Loja>()

    operator fun get(dono: String): Loja? {
        return cache[dono]
    }
}