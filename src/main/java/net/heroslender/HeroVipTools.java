package net.heroslender;

import lombok.Getter;
import net.heroslender.DataBase.DataSource;
import net.heroslender.DataBase.SqlLiteDataSource;
import net.heroslender.Modulos.Loja;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Heroslender.
 */
public class HeroVipTools extends JavaPlugin {

    @Getter
    private static HeroVipTools instance;
    @Getter
    private DataSource dataSource;
    @Getter
    private Loja moduloLoja;

    @Override
    public void onEnable() {
        instance = this;

        dataSource = new SqlLiteDataSource();

        moduloLoja = new Loja();
    }
}
