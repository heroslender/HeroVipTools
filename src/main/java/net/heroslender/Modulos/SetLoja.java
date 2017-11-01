package net.heroslender.Modulos;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Heroslender.
 */
public class SetLoja {

    private final Map<String, Location> lojas;

    public SetLoja() {
        lojas = new HashMap<String, Location>();
    }

    public void setarLoja(Player player){
        setarLoja(player, player.getName());
    }

    public void setarLoja(Player player, String target){
        lojas.put(target, player.getLocation());
        //TODO - Adicionar a base de dados
    }

    public void delLoja(Player player) throws Exception {
        delLoja(player, player.getName());
    }

    public void delLoja(Player player, String target) throws Exception {
        if (!lojas.containsKey(target))
            throw new Exception(player.getName().equals(target) ? "§cNão tens nenhuma loja defenida!" : "§cO jogador §7\"" + target + "\" §cnão tem nenhuma loja defenida!");

        lojas.remove(target);

        //TODO - remover na base de dados
    }
}
