package com.heroslender.viptools.commands

import com.heroslender.pluginwrapper.utils.command.Command
import com.heroslender.viptools.Config
import com.heroslender.viptools.HeroVipTools
import com.heroslender.viptools.loja.LojaManager
import org.bukkit.entity.Player

class SetlojaCommand : Command("setloja") {
    init {
        setAliases("definirloja")
        permission = Config.PERMISSION_SET_LOJA
        isPlayerOnly = true
    }

    override fun onCommand(sender: Player, label: String, args: Array<out String>): Boolean {
        val loja = LojaManager.setLoja(sender, sender.location)

        sender.sendMessage(HeroVipTools.instance.messagesConfig.lojaDefinida.build(loja.placeholder))
        return true
    }
}
