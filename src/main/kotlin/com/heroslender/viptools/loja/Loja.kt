package com.heroslender.viptools.loja

import com.heroslender.pluginwrapper.utils.Placeholder
import com.heroslender.viptools.HeroVipTools
import org.bukkit.Location
import org.bukkit.block.Sign

class Loja(val owner: String,
           var location: Location,
           val votos: MutableList<String> = mutableListOf(),
           val placas: MutableList<Location> = mutableListOf()) {

    val placeholder: Placeholder
        get() = Placeholder().add("loja-dono", owner).add("loja-votos", votos.size)

    fun updateLocation(location: Location) {
        this.location = location
        HeroVipTools.instance.storage.updateLocation(this)
    }

    fun addSign(signLocation: Location): Boolean {
        if (!placas.contains(signLocation)) {
            placas.add(signLocation)
            HeroVipTools.instance.storage.saveSign(this, signLocation)
            return true
        }
        return false
    }

    fun removeSign(signLocation: Location): Boolean {
        return placas.remove(signLocation).also { if (it) HeroVipTools.instance.storage.deleteSign(this, signLocation) }
    }

    fun addVoto(votador: String): Boolean {
        if (!votos.contains(votador)) {
            votos.add(votador)
            HeroVipTools.instance.storage.saveVoto(this, votador)

            // Atualizar outras placas de votos
            atualizarVotos()
            return true
        }
        return false
    }

    private fun atualizarVotos() {
        for (signLocation in placas) {
            if (signLocation.block.state !is Sign) continue

            val sign = signLocation.block.state as Sign
            val msgConfig = HeroVipTools.instance.messagesConfig
            val placeholder = this.placeholder

            if (sign.getLine(0) == msgConfig.signLine1) {
                sign.setLine(1, placeholder.load(msgConfig.signLine2))
                sign.setLine(2, placeholder.load(msgConfig.signLine3))
                sign.setLine(3, placeholder.load(msgConfig.signLine4))
                sign.update()
            }
        }
    }

    fun removeVoto(votador: String): Boolean {
        return votos.remove(votador).also {
            if (it) {
                HeroVipTools.instance.storage.deleteVoto(this, votador)
                atualizarVotos()
            }
        }
    }
}
