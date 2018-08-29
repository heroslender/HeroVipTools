package com.heroslender.viptools.storage

import com.heroslender.viptools.loja.Loja
import org.bukkit.Location

open class StorageCache {
    val cache = mutableMapOf<String, Loja>()

    operator fun get(dono: String): Loja? {
        return cache[dono]
    }

    operator fun get(signLocation: Location): Loja? {
        return cache.values.firstOrNull { it.placas.contains(signLocation) }
    }
}