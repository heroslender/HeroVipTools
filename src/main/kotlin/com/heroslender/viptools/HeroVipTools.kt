package com.heroslender.viptools

import com.heroslender.pluginwrapper.PluginWrapper
import com.heroslender.pluginwrapper.utils.config.JsonConfiguration
import com.heroslender.viptools.loja.SignController
import com.heroslender.viptools.storage.SQLiteStorage
import com.heroslender.viptools.storage.Storage

class HeroVipTools : PluginWrapper() {
    companion object {
        val instance: HeroVipTools
            get() = PluginWrapper.instance as HeroVipTools
    }
    val configuration = JsonConfiguration(this)
    var config = Config(configuration)
    lateinit var storage: Storage
    lateinit var signController: SignController

    override fun onEnable() {
        super.onEnable()
        messagesConfiguration = MessagesConfig(messagesJsonConfig)

        // Precisa de ser no onEnable esses 2 :/
        storage = SQLiteStorage()
        signController = SignController()
    }

    override fun reloadConfig() {
        reloadMessagesConfig()
        configuration.reload()
        config = Config(configuration)
        storage.onDisable()
    }

    val messagesConfig: MessagesConfig
        get() = messagesConfiguration as MessagesConfig
}