package com.heroslender.viptools.storage

import com.heroslender.pluginwrapper.utils.HeroLogger
import com.heroslender.viptools.HeroVipTools
import com.heroslender.viptools.loja.Loja
import com.heroslender.viptools.utils.getLocation
import org.bukkit.Location
import org.sqlite.SQLiteDataSource
import java.io.File
import java.sql.Date
import java.sql.SQLException
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
            val start = System.currentTimeMillis()
            logger.info("Inicializando...")
            dataSource.connection.use { c ->
                c.createStatement().use { st ->
                    logger.info("Inicializando as tabelas...")
                    st.executeUpdate("CREATE TABLE IF NOT EXISTS $LOJAS (" +
                            "`$LOJAS_PLAYER` varchar(32) NOT NULL," +
                            "`world` varchar(32) NOT NULL," +
                            "`x` DOUBLE NOT NULL," +
                            "`y` DOUBLE NOT NULL," +
                            "`z` DOUBLE NOT NULL," +
                            "`yaw` DOUBLE NOT NULL," +
                            "`pitch` DOUBLE NOT NULL," +
                            "PRIMARY KEY (`$LOJAS_PLAYER`)" +
                            ");")
                    st.executeUpdate("CREATE TABLE IF NOT EXISTS $PLACAS (" +
                            "`$PLACAS_ID` INTEGER PRIMARY KEY NOT NULL," +
                            "`$PLACAS_DONO` varchar(32) NOT NULL," +
                            "`world` varchar(32) NOT NULL," +
                            "`x` INT(8) NOT NULL," +
                            "`y` INT(8) NOT NULL," +
                            "`z` INT(8) NOT NULL" +
                            ");")
                    st.executeUpdate("CREATE TABLE IF NOT EXISTS $VOTOS (" +
                            "`$VOTOS_LOJA` varchar(32) NOT NULL," +
                            "`$VOTOS_VOTADOR` varchar(32) NOT NULL," +
                            "`$VOTOS_DATA` DATE NOT NULL," +
                            "PRIMARY KEY (`$VOTOS_LOJA`, `$VOTOS_VOTADOR`)" +
                            ");")
                }

                logger.info("Carregando os dados...")
                c.prepareStatement("SELECT * FROM $LOJAS").use { ps ->
                    ps.executeQuery().use { rs ->
                        cache.clear()
                        while (rs.next()) {
                            val player = rs.getString(LOJAS_PLAYER)
                            val votos = ArrayList<String>()
                            val placas = ArrayList<Location>()

                            c.prepareStatement("SELECT * FROM $VOTOS WHERE $VOTOS_LOJA = ?").use { psVotos ->
                                psVotos.setString(1, player)
                                psVotos.executeQuery().use { rsVotos ->
                                    while (rsVotos.next()) {
                                        votos.add(rsVotos.getString(VOTOS_VOTADOR))
                                    }
                                }
                            }
                            c.prepareStatement("SELECT * FROM $PLACAS WHERE $PLACAS_DONO = ?").use { psPlacas ->
                                psPlacas.setString(1, player)
                                psPlacas.executeQuery().use { rsPlacas ->
                                    while (rsPlacas.next()) placas.add(rsPlacas.getLocation())
                                }
                            }

                            cache[player] = Loja(player, rs.getLocation(), votos, placas)
                        }
                        logger.info("Foram carregadas ${cache.size} loja${if (cache.size == 1) "" else "s"}.")
                    }
                }
            }
            logger.info(start, "Inicializacao concluida!")
        } catch (e: SQLException) {
            logger.error("Ocurreu um erro ao inicializar as lojas!", e)
        }
    }

    override fun insert(loja: Loja) {
        try {
            dataSource.connection.use { c ->
                c.prepareStatement("INSERT OR REPLACE INTO $LOJAS($LOJAS_PLAYER, world, x, y, z, yaw, pitch) " +
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
                        c.prepareStatement("INSERT OR REPLACE INTO $PLACAS($PLACAS_DONO, world, x, y, z, yaw, pitch) " +
                                "VALUES(?, ?, ?, ?, ?, ?, ?)").use { psPlacas ->
                            psPlacas.setString(1, loja.owner)
                            psPlacas.setString(2, placa.world.name)
                            psPlacas.setDouble(3, placa.x)
                            psPlacas.setDouble(4, placa.y)
                            psPlacas.setDouble(5, placa.z)
                            psPlacas.setFloat(6, placa.yaw)
                            psPlacas.setFloat(7, placa.pitch)
                            psPlacas.executeUpdate()
                        }
                    }

                    for (voto in loja.votos) {
                        c.prepareStatement("INSERT OR REPLACE INTO $VOTOS($VOTOS_LOJA, $VOTOS_VOTADOR, $VOTOS_DATA) " +
                                "VALUES(?, ?, ?)").use { psVotos ->
                            psVotos.setString(1, loja.owner)
                            psVotos.setString(2, voto)
                            psVotos.setDate(3, Date(Calendar.getInstance().time.time))
                            psVotos.executeUpdate()
                        }
                    }
                }
            }
            cache[loja.owner] = loja
        } catch (e: SQLException) {
            logger.error("Ocurreu um erro ao salvar a loja!", e)
        }
    }

    override fun delete(loja: Loja) {
        try {
            dataSource.connection.use { c ->
                c.prepareStatement("DELETE FROM $LOJAS WHERE $LOJAS_PLAYER=?").use { ps ->
                    ps.setString(1, loja.owner)
                    ps.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            logger.error("Ocurreu um erro ao apagar a localizacao da loja!", e)
        }
    }

    override fun updateLocation(loja: Loja) {
        try {
            dataSource.connection.use { c ->
                c.prepareStatement("UPDATE $LOJAS SET world=?, x=?, y=?, z=?, yaw=?, pitch=? WHERE $LOJAS_PLAYER=?").use { ps ->
                    ps.setString(1, loja.location.world.name)
                    ps.setDouble(2, loja.location.x)
                    ps.setDouble(3, loja.location.y)
                    ps.setDouble(4, loja.location.z)
                    ps.setFloat(5, loja.location.yaw)
                    ps.setFloat(6, loja.location.pitch)
                    ps.setString(7, loja.owner)
                    ps.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            logger.error("Ocurreu um erro ao atualizar a localizacao da loja!", e)
        }
    }

    override fun saveVoto(loja: Loja, votador: String) {
        try {
            dataSource.connection.use { c ->
                c.prepareStatement("INSERT OR IGNORE INTO $VOTOS($VOTOS_LOJA, $VOTOS_VOTADOR, $VOTOS_DATA) " +
                        "VALUES(?, ?, ?)").use { ps ->
                    ps.setString(1, loja.owner)
                    ps.setString(2, votador)
                    ps.setDate(3, Date(Calendar.getInstance().time.time))
                    ps.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            logger.error("Ocurreu um erro ao guardar o voto na loja!", e)
        }
    }

    override fun deleteVoto(loja: Loja, votador: String) {
        try {
            dataSource.connection.use { c ->
                c.prepareStatement("DELETE FROM $VOTOS WHERE $VOTOS_LOJA=? AND $VOTOS_VOTADOR=?").use { ps ->
                    ps.setString(1, loja.owner)
                    ps.setString(2, votador)
                    ps.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            logger.error("Ocurreu um erro ao apagar o voto da loja!", e)
        }
    }

    override fun saveSign(loja: Loja, sign: Location) {
        try {
            dataSource.connection.use { c ->
                c.prepareStatement("INSERT OR REPLACE INTO $PLACAS($PLACAS_DONO, world, x, y, z) " +
                        "VALUES(?, ?, ?, ?, ?)").use { ps ->
                    ps.setString(1, loja.owner)
                    ps.setString(2, sign.world.name)
                    ps.setInt(3, sign.blockX)
                    ps.setInt(4, sign.blockY)
                    ps.setInt(5, sign.blockZ)
                    ps.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            logger.error("Ocurreu um erro ao salvar a loja!", e)
        }
    }

    override fun deleteSign(loja: Loja, sign: Location) {
        try {
            dataSource.connection.use { c ->
                c.prepareStatement("DELETE FROM $PLACAS WHERE $PLACAS_DONO=? AND world=? AND x=? AND y=? AND z=?").use { ps ->
                    ps.setString(1, loja.owner)
                    ps.setString(2, sign.world.name)
                    ps.setInt(3, sign.blockX)
                    ps.setInt(4, sign.blockY)
                    ps.setInt(5, sign.blockZ)
                    ps.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            logger.error("Ocurreu um erro ao deletar a localizacao da loja!", e)
        }
    }

    override fun onDisable() {}
}