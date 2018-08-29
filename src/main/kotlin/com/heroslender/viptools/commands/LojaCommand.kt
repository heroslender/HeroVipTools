package com.heroslender.viptools.commands

import com.heroslender.pluginwrapper.utils.command.Command
import com.heroslender.viptools.HeroVipTools
import com.heroslender.viptools.loja.LojaManager
import org.bukkit.entity.Player

class LojaCommand : Command("loja") {
    init {
        isPlayerOnly = true
        usoCorreto = "<player>"
    }

    override fun onCommand(sender: Player, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false

        val loja = LojaManager[args[0]]

        sender.teleport(loja.location)
        sender.sendMessage(HeroVipTools.instance.messagesConfig.lojaIr.build(loja.placeholder))
        return true
    }
}
