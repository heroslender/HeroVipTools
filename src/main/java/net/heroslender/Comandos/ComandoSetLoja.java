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
public class ComandoSetLoja implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("viptools.setloja") || sender.hasPermission("viptools.admin")){
            if (args.length > 0 && sender.hasPermission("viptools.admin")){
                Player player = Bukkit.getPlayerExact(args[0]);
                if (player == null) {
                    sender.sendMessage("§cEsse jogador encontra-se offline!");
                    return true;
                }
                HeroVipTools.getInstance().getModuloLoja().setarLoja((Player) sender, args[0]);
                sender.sendMessage("§aSetas-te com sucesso a loja de §7" + args[0]);
                return true;
            }
            HeroVipTools.getInstance().getModuloLoja().setarLoja((Player) sender);
            sender.sendMessage("§aLoja setada com sucesso! Use §7" + sender.getName());
            return true;
        }
        sender.sendMessage("§cNão tens permissão para setar loja!");
        return true;
    }
}
