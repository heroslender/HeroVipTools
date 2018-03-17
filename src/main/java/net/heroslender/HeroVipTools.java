package net.heroslender;

import lombok.Getter;
import net.heroslender.DataBase.SqlLiteStorage;
import net.heroslender.DataBase.Storage;
import net.heroslender.Modulos.ModuloLoja;
import net.heroslender.Modulos.ModuloVotos;
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
    private ModuloLoja moduloLoja;
    @Getter
    private ModuloVotos moduloVotos;

    @Override
    public void onEnable() {
        instance = this;

        storage = new SqlLiteStorage();

        moduloLoja = new ModuloLoja();
        if (getConfig().getBoolean("modulo-votos.usar"))
            moduloVotos = new ModuloVotos();
    }

    public boolean isUsingModuloVotos(){
        return moduloVotos != null;
    }
}
