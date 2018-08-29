package com.heroslender.viptools.storage

import com.heroslender.viptools.loja.Loja
import org.bukkit.Location

interface Storage {

    /**
     * Procurar a loja de um player pelo nome dele
     *
     * @param dono Nome do player
     * @return Loja do player
     */
    operator fun get(dono: String): Loja?

    operator fun get(signLocation: Location): Loja?

    fun insert(loja: Loja)

    fun delete(loja: Loja)

    fun updateLocation(loja: Loja)

    fun saveVoto(loja: Loja, votador: String)

    fun deleteVoto(loja: Loja, votador: String)

    fun saveSign(loja: Loja, location: Location)

    fun deleteSign(loja: Loja, location: Location)

    fun onDisable()
}