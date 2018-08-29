package com.heroslender.viptools.loja

import com.heroslender.pluginwrapper.utils.exceptions.InsufficientPermissionException
import com.heroslender.viptools.Config
import com.heroslender.viptools.HeroVipTools
import com.heroslender.viptools.utils.exceptions.InvalidSignException
import com.heroslender.viptools.utils.exceptions.LojaNotFoundException
import org.bukkit.Location
import org.bukkit.entity.Player

object LojaManager {

    fun getOrNull(owner: String): Loja? {
        return HeroVipTools.instance.storage[owner]
    }

    @Throws(LojaNotFoundException::class)
    operator fun get(owner: String): Loja {
        return getOrNull(owner) ?: throw LojaNotFoundException(owner)
    }

    fun getOrNull(location: Location): Loja? {
        return HeroVipTools.instance.storage[location]
    }

    @Throws(LojaNotFoundException::class)
    operator fun get(location: Location): Loja {
        return getOrNull(location) ?: throw InvalidSignException()
    }

    @Throws(InsufficientPermissionException::class)
    fun setLoja(owner: Player, location: Location): Loja {
        if (!owner.hasPermission(Config.PERMISSION_SET_LOJA))
            throw InsufficientPermissionException(Config.PERMISSION_SET_LOJA)

        val loja = getOrNull(owner.name)
        if (loja != null) {
            loja.updateLocation(location)
        } else {
            val novaLoja = Loja(owner.name, location)
            HeroVipTools.instance.storage.insert(novaLoja)
            return novaLoja
        }
        return loja
    }
}
