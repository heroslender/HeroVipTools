package net.heroslender;

import lombok.Getter;
import net.heroslender.DataBase.SqlLiteStorage;
import net.heroslender.DataBase.Storage;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Heroslender.
 */
public class HeroVipTools extends JavaPlugin {

    @Getter private static HeroVipTools instance;
    @Getter private Storage storage;

    @Override
    public void onEnable() {
        instance = this;

        storage = new SqlLiteStorage();
    }
}
