package net.heroslender.Modulos;

import net.heroslender.HeroVipTools;
import net.heroslender.Loja;
import org.bukkit.Bukkit;

import java.util.List;

/**
 * Created by Heroslender.
 */
public class ModuloVotos {

    private List<Loja> topLojas;

    public ModuloVotos() {
        topLojas = HeroVipTools.getInstance().getStorage().getTopLojas();

        for (Loja l : topLojas) {
            Bukkit.getLogger().info(l.getPlayer() + " - " + l.getVotos());
        }
    }
}
