package com.heroslender.viptools.storage

import com.heroslender.pluginwrapper.utils.HeroLogger
import com.heroslender.viptools.HeroVipTools
import com.heroslender.viptools.Loja
import com.heroslender.viptools.utils.getLocation
import org.bukkit.Location
import org.sqlite.SQLiteDataSource
import java.io.File
import java.sql.Date
import java.util.*

class SQLiteStorage : StorageCache(), Storage {
    companion object {
        const val LOJAS: String = "lojas"
        const val LOJAS_PLAYER = "player"

        const val PLACAS = "placas"
        const val PLACAS_ID = "id"
        const val PLACAS_DONO = "player"

        const val VOTOS = "votos"
        const val VOTOS_LOJA = "loja"
        const val VOTOS_VOTADOR = "votador"
        const val VOTOS_DATA = "data"

        val logger = HeroLogger(HeroVipTools.instance, "SQLite")
    }

    var dataSource: SQLiteDataSource = SQLiteDataSource().apply {
        url = "jdbc:sqlite:" + File(HeroVipTools.instance.dataFolder, "BaseDeDados.db")
    }

    init {
        try {
            dataSource.connection.use { c ->
                c.prepareStatement("SELECT * FROM $LOJAS").use { ps ->
                    ps.executeQuery().use { rs ->
                        cache.clear()
                        while (rs.next()) {
                            val player = rs.getString(LOJAS_PLAYER)
                            val votos = ArrayList<String>()
                            val placas = ArrayList<Location>()

                            c.prepareStatement("SELECT * FROM $VOTOS WHERE $VOTOS_LOJA = ?").use { ps1 ->
                                ps1.setString(1, player)
                                ps.executeQuery().use { rs1 ->
                                    while (rs1.next())
                                        votos.add(rs1.getString(VOTOS_VOTADOR))
                                }
                            }
                            c.prepareStatement("SELECT * FROM $PLACAS WHERE $PLACAS_DONO = ?").use { ps1 ->
                                ps1.setString(1, player)
                                ps.executeQuery().use { rs1 ->
                                    while (rs1.next())
                                        placas.add(rs1.getLocation())
                                }
                            }

                            cache[player] = Loja(player, rs.getLocation(), votos, placas)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            logger.error("Ocurreu um erro ao inicializar as lojas!", e)
        }
    }

    override fun insert(loja: Loja) {
        try {
            dataSource.connection.use { c ->
                c.prepareStatement("INSERT INTO $LOJAS($LOJAS_PLAYER, world, x, y, z, yaw, pitch) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)").use { ps ->
                    ps.setString(1, loja.owner)
                    ps.setString(2, loja.location.world.name)
                    ps.setDouble(3, loja.location.x)
                    ps.setDouble(4, loja.location.y)
                    ps.setDouble(5, loja.location.z)
                    ps.setFloat(6, loja.location.yaw)
                    ps.setFloat(7, loja.location.pitch)
                    ps.executeUpdate()

                    for (placa in loja.placas) {
                        c.prepareStatement("INSERT INTO $PLACAS($PLACAS_DONO, world, x, y, z, yaw, pitch) " +
                                "VALUES(?, ?, ?, ?, ?, ?, ?)").use { psLojas ->
                            psLojas.setString(1, loja.owner)
                            psLojas.setString(2, placa.world.name)
                            psLojas.setDouble(3, placa.x)
                            psLojas.setDouble(4, placa.y)
                            psLojas.setDouble(5, placa.z)
                            psLojas.setFloat(6, placa.yaw)
                            psLojas.setFloat(7, placa.pitch)
                            psLojas.executeUpdate()
                        }
                    }

                    for (voto in loja.votos) {
                        c.prepareStatement("INSERT INTO $VOTOS($VOTOS_LOJA, $VOTOS_VOTADOR, $VOTOS_DATA) " +
                                "VALUES(?, ?, ?)").use { psVotos ->
                            psVotos.setString(1, loja.owner)
                            psVotos.setString(2, voto)
                            psVotos.setDate(3, Date(Calendar.getInstance().time.time))
                            psVotos.executeUpdate()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            logger.error("Ocurreu um erro ao inicializar as lojas!", e)
        }
    }

    override fun delete(loja: Loja) {
        TODO("not implemented")
    }

    override fun updateLocation(loja: Loja) {
        TODO("not implemented")
    }

    override fun saveVoto(loja: Loja, votador: String) {
        try {
            dataSource.connection.use { c ->
                c.prepareStatement("INSERT OR IGNORE INTO $VOTOS($VOTOS_LOJA, $VOTOS_VOTADOR, $VOTOS_DATA) " +
                        "VALUES(?, ?, ?)").use { psVotos ->
                    psVotos.setString(1, loja.owner)
                    psVotos.setString(2, votador)
                    psVotos.setDate(3, Date(Calendar.getInstance().time.time))
                    psVotos.executeUpdate()
                }
            }
        } catch (e: Exception) {
            logger.error("Ocurreu um erro ao inicializar as lojas!", e)
        }
    }

    override fun deleteVoto(loja: Loja, votador: String) {
        TODO("not implemented")
    }

    override fun saveSign(loja: Loja, location: Location) {
        TODO("not implemented")
    }

    override fun deleteSign(loja: Loja, location: Location) {
        TODO("not implemented")
    }

    override fun onDisable() {
        TODO("not implemented")
    }
}