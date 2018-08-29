package com.heroslender.viptools.utils.exceptions

import com.heroslender.pluginwrapper.utils.Placeholder
import com.heroslender.pluginwrapper.utils.exceptions.CommandException
import com.heroslender.viptools.HeroVipTools

class InvalidSignException() : CommandException(HeroVipTools.instance.messagesConfig.signInvalid, Placeholder())
