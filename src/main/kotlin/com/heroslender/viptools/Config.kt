package com.heroslender.viptools

import com.heroslender.pluginwrapper.utils.config.JsonConfiguration

class Config(config: JsonConfiguration) {
    companion object {
        const val PERMISSION_ADMIN = "viptools.admin"
        const val PERMISSION_SET_LOJA = "viptools.setloja"
        const val PERMISSION_CREATE_SIGN = "viptools.placa.criar"
    }

    val PLACA_LIMIT = config.getInteger("sign.limit")
}