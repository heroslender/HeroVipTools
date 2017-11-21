package net.heroslender;

import lombok.Getter;
import net.heroslender.DataBase.Storage;
import net.heroslender.DataBase.SqlLiteStorage;
import net.heroslender.Modulos.Loja;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Heroslender.
 */
public class HeroVipTools extends JavaPlugin {

    @Getter
    private static HeroVipTools instance;
    @Getter
    private Storage storage;
    @Getter
    private Loja moduloLoja;

    @Override
    public void onEnable() {
        instance = this;

        storage = new SqlLiteStorage();

        moduloLoja = new Loja();

    }
}
