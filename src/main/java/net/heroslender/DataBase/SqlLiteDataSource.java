package net.heroslender.DataBase;

import org.bukkit.Location;

import java.util.Map;

/**
 * Created by Heroslender.
 */
public class SqlLiteDataSource implements DataSource {

    public Map<String, Location> getLojas() {
        return null;
    }

    public int getVotos(String player) {
        return 0;
    }

    public int votar(String loja, String votador) {
        return 0;
    }

    public void setLoja(String player, Location location) {

    }

    public void delLoja(String player) {

    }
}
