package net.heroslender.Modulos;

import net.heroslender.HeroVipTools;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Created by Heroslender.
 */
public class ModuloLoja {

    private final Map<String, Location> lojas;

    public ModuloLoja() {
        lojas = HeroVipTools.getInstance().getStorage().getLojas();
    }

    public void sendToLoja(Player player, String loja) throws Exception {
        if (player == null || !player.isOnline())
            throw new Exception("§cEsse jogador encontra-se offline!");
        if (!lojas.containsKey(loja) || lojas.get(loja) == null)
            throw new Exception("§cEsse jogador não possui uma loja!");

        player.teleport(lojas.get(loja));
    }

    public void setarLoja(Player player){
        setarLoja(player, player.getName());
    }

    public void setarLoja(Player player, String target){
        lojas.put(target, player.getLocation());
        HeroVipTools.getInstance().getStorage().setLoja(target, player.getLocation());
    }

    public void delLoja(CommandSender player) throws Exception {
        delLoja(player, player.getName());
    }

    public void delLoja(CommandSender player, String target) throws Exception {
        if (!lojas.containsKey(target))
            throw new Exception(player.getName().equals(target) ? "§cNão tens nenhuma loja defenida!" : "§cO jogador §7\"" + target + "\" §cnão tem nenhuma loja defenida!");

        lojas.remove(target);

        HeroVipTools.getInstance().getStorage().delLoja(target);
    }
}
