package net.heroslender;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Created by Heroslender.
 */
@Data
@AllArgsConstructor
public class Loja {
    private String player;
    private Location location;
    private int votos;

    public Loja(String player, String location, int votos) {
        this.player = player;
        this.location = locationFromString(location);
        this.votos = votos;
    }

    public String getLocationString(){
        return locationToString(location);
    }

    private Location locationFromString(String location) {
        String[] s = location.split("\\|");
        return new Location(Bukkit.getWorld(s[0]), (double) Float.parseFloat(s[1]), (double) Float.parseFloat(s[2]), (double) Float.parseFloat(s[3]), Float.parseFloat(s[5]), Float.parseFloat(s[4]));
    }

    private String locationToString(Location location) {
        return location.getWorld().getName() + "|" + location.getX() + "|" + location.getY() + "|" + location.getZ() + "|" + location.getPitch() + "|" + location.getYaw();
    }
}
