package net.heroslender.Comandos;

import net.heroslender.HeroVipTools;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Heroslender.
 */
public class ComandoLoja implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (args.length > 0) {
                if (args.length > 1 && sender.hasPermission("viptools.admin")) {
                    HeroVipTools.getInstance().getModuloLoja().sendToLoja(Bukkit.getPlayerExact(args[0]), args[1]);
                    sender.sendMessage("");
                    return true;
                }
                if (sender instanceof Player) {
                    HeroVipTools.getInstance().getModuloLoja().sendToLoja((Player) sender, args[0]);
                    sender.sendMessage("§aFoste teleportado para a loja de §7" + args[0] + "§a!");
                    return true;
                }
                sender.sendMessage("§c/" + label + " <jogador> <loja>");
                return true;
            }
        } catch (Exception e) {
            sender.sendMessage(e.getMessage());
            return true;
        }
        sender.sendMessage("§cUso:");
        if (sender.hasPermission("viptools.admin")) {
            sender.sendMessage(" §c /" + label + " <jogador> <loja>");
            return true;
        }
        if (sender instanceof Player)
            sender.sendMessage(" §c /" + label + " <loja>");
        return true;
    }
}
