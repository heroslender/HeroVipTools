package com.heroslender.viptools

import com.heroslender.pluginwrapper.PluginWrapper

class HeroVipTools : PluginWrapper() {
    companion object {
        val instance: HeroVipTools
            get() = PluginWrapper.instance as HeroVipTools
    }

    override fun onEnable() {
        super.onEnable()

        logger.info("In dev...")
    }
}