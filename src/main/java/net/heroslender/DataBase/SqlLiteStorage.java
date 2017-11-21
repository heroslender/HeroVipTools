package net.heroslender.DataBase;

import net.heroslender.HeroVipTools;
import net.heroslender.Loja;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by Heroslender.
 */
public class SqlLiteStorage implements Storage {

    SQLiteDataSource dataSource;

    public SqlLiteStorage() {
        dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + new File(HeroVipTools.getInstance().getDataFolder(), "DataBase.db"));

        createDatabase();
    }

    public Map<String, Loja> getLojas() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement("SELECT " +
                    DataBase.LOJA + "." + DataBase.LOJA_PLAYER + ", " +
                    DataBase.LOJA + "." + DataBase.LOJA_LOCATION + ", " +
                    "(SELECT COUNT(*) FROM " + DataBase.VOTOS + " WHERE " +
                    DataBase.VOTOS + "." + DataBase.VOTOS_LOJA + " = " + DataBase.LOJA + "." + DataBase.LOJA_PLAYER + ") AS votos" +
                    " FROM " + DataBase.LOJA + ";");
            rs = ps.executeQuery();

            Map<String, Loja> lojas = new HashMap<String, Loja>();
            while (rs.next()) {
                String p = rs.getString(DataBase.LOJA + "." + DataBase.LOJA_PLAYER);
                lojas.put(p, new Loja(
                        p,
                        rs.getString(DataBase.LOJA + "." + DataBase.LOJA_LOCATION),
                        rs.getInt("votos")));
            }
            return lojas;
        } catch (SQLException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "[HeroVipTools] Ocurreu um erro ao executar a query de pegar as lojas!", ex);
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                Bukkit.getLogger().log(Level.SEVERE, "[HeroVipTools] Ocurreu um erro ao fechar a conexao!", ex);
            }
        }
        return new HashMap<String, Loja>();
    }

    public void setLoja(String player, Location location) {

    }

    public void delLoja(String player) {

    }

    public int getVotos(String player) {
        return 0;
    }

    public int votar(String loja, String votador) {
        return 0;
    }

    private void createDatabase() {
        try {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = dataSource.getConnection();

                preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + DataBase.LOJA + " (" +
//                        "`" + DataBase.BAUS_ID + "` INTEGER PRIMARY KEY," +
                        "`" + DataBase.LOJA_PLAYER + "` varchar(32) NOT NULL," +
                        "`" + DataBase.LOJA_LOCATION + "` varchar(128) NOT NULL," +
                        "PRIMARY KEY (`" + DataBase.LOJA_PLAYER + "`)" +
                        ");");
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + DataBase.PLACAS + " (" +
//                        "`" + DataBase.BAUS_ID + "` INTEGER PRIMARY KEY," +
                        "`" + DataBase.PLACAS_LOCATION + "` varchar(128) NOT NULL," +
                        "`" + DataBase.PLACAS_PLAYER + "` varchar(32) NOT NULL," +
                        "PRIMARY KEY (`" + DataBase.PLACAS_LOCATION + "`)" +
                        ");");
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + DataBase.VOTOS + " (" +
                        "`" + DataBase.VOTOS_LOJA + "` varchar(32) NOT NULL ," +
                        "`" + DataBase.VOTOS_VOTADOR + "`  varchar(32) NOT NULL," +
                        "`" + DataBase.VOTOS_DATA + "` datetime(32) NOT NULL," +
                        "PRIMARY KEY (`" + DataBase.VOTOS_LOJA + "`, `" + DataBase.VOTOS_VOTADOR + "`)" +
                        ");");
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                Bukkit.getLogger().info("[HeroBaus] SQL - Ocurreu um erro ao criar a tabela.");
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
