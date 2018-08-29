package com.heroslender.viptools.commands

import com.heroslender.pluginwrapper.utils.command.Command
import com.heroslender.viptools.Config
import com.heroslender.viptools.HeroVipTools

class ViptoolsCommand : Command("viptools") {
    init {
        setAliases("heroviptools")
        permission = Config.PERMISSION_ADMIN
        usoCorreto = "<reload>"

        addSubCommand("reload") {
            onCommand {
                HeroVipTools.instance.reloadConfig()
                sender.sendMessage("§aConfig reiniciada com sucesso!")
                return@onCommand true
            }
        }
    }
}
