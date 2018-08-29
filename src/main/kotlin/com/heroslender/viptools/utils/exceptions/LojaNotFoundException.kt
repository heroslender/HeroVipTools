package com.heroslender.viptools.utils.exceptions

import com.heroslender.pluginwrapper.utils.Placeholder
import com.heroslender.pluginwrapper.utils.exceptions.CommandException
import com.heroslender.viptools.HeroVipTools

class LojaNotFoundException(lojaOwner: String) : CommandException(HeroVipTools.instance.messagesConfig.lojaNotFound, Placeholder().add("loja-dono", lojaOwner))
