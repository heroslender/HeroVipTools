package net.heroslender.Comandos;

import net.heroslender.HeroVipTools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Heroslender.
 */
public class ComandoDelLoja implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (args.length > 0 && sender.hasPermission("viptools.admin")) {
                HeroVipTools.getInstance().getModuloLoja().delLoja(sender, args[0]);
                sender.sendMessage("§aApagaste com sucesso a loja de §7" + args[0] + "§a!");
                return true;
            }
            if (sender instanceof Player) {
                HeroVipTools.getInstance().getModuloLoja().delLoja(sender);
                sender.sendMessage("§aApagaste com sucesso a tua loja!");
                return true;
            }
        } catch (Exception e) {
            sender.sendMessage(e.getMessage());
            return true;
        }
        sender.sendMessage("§c/" + label + " <jogador>");
        return true;
    }
}
