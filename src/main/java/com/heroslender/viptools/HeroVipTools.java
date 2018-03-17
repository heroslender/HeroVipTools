package com.heroslender.viptools;

import com.heroslender.viptools.DataBase.SqlLiteStorage;
import com.heroslender.viptools.DataBase.Storage;
import lombok.Getter;
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
