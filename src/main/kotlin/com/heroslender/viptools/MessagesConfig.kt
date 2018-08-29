package com.heroslender.viptools

import com.heroslender.pluginwrapper.MessagesConfig
import com.heroslender.pluginwrapper.utils.config.JsonConfiguration
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent

class MessagesConfig(messageConfiguration: JsonConfiguration) : MessagesConfig(messageConfiguration) {

    init {
        println("Initializing messages")
        messageConfiguration.addDefault("loja.definida", TextComponent("&aDefeniste a localização da tua loja!").apply {
            addExtra(TextComponent("\n&aPara ir para a sua loja digite &b/loja :loja-dono:").apply {
                clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/loja :loja-dono:")
                hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("&7Clique para colocar o comando no seu chat.").create())
            })
            addExtra(TextComponent("\n&aPodes anunciar a tua loja usando o comando &b/anunciar &3<anuncio>").apply {
                clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/anunciar ")
                hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        ComponentBuilder("&cApenas é permitio usar o comando para anunciar a sua loja!")
                                .append("\n&cO uso do comando para outra finalidade resultará em punição!")
                                .append("\n\n&7Clique para colocar o comando no seu chat.")
                                .create())
            })
        })

        messageConfiguration.addDefault("loja.votar", "&aVotaste na loja de :loja-dono:! Abraços :)")
        messageConfiguration.addDefault("loja.remover-voto", "&cRemoveste o teu voto na loja de :loja-dono:! Total sadness :(")
        messageConfiguration.addDefault("loja.nao-encontrada", "&cO jogador &7:loja-dono: &cnão possui nenhuma loja!")

        messageConfiguration.addDefault("loja.ir", TextComponent("&aFoste teleportado para a loja de :loja-dono:!").apply {
            hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("&eAtualmente possui &7:loja-votos: &evotos.").create())

            addExtra(TextComponent("\n&eGostaste da loja de :loja-dono:? Clica aqui para votar nela!").apply {
                clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/votar :loja-dono:")
                hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("&7Clique para votar na loja.").create())
            })
        })
        messageConfiguration.addDefault("placa.linha-1", "&6✌ &8[&aVotar&8] &6✌")
        messageConfiguration.addDefault("placa.linha-2", "&bVotos: &a:votos:")
        messageConfiguration.addDefault("placa.linha-3", ":loja:")
        messageConfiguration.addDefault("placa.linha-4", "&cClica para votar")
        messageConfiguration.addDefault("placa.definida", "&aDefeniste uma placa de votos para a tua loja!")
        messageConfiguration.addDefault("placa.loja-nao-encontrada", "&cTu não tens nenhuma loja defenida, por isso não podes setar uma placa de votos.")
        messageConfiguration.addDefault("placa.invalida", "&cEssa placa não pertence a nenhuma loja!")
        messageConfiguration.addDefault("placa.destruida", "&aDestruiste uma placa de votos da tua loja!")
    }

    val lojaDefinida = messageConfiguration.getMessageBuilder("loja.definida", "&aDefiniste a localização da tua loja!")
    val lojaIr = messageConfiguration.getMessageBuilder("loja.ir", "&aFoste teleportado para a loja de :loja-dono:!")
    val lojaVoted = messageConfiguration.getMessageBuilder("loja.votar", "&aVotaste na loja de :loja-dono:! Abraços :)")
    val lojaUnvoted = messageConfiguration.getMessageBuilder("loja.remover-voto", "&cRemoveste o teu voto na loja de :loja-dono:! Total sadness :(")
    val lojaNotFound = messageConfiguration.getMessageBuilder("loja.nao-encontrada", "&cO jogador &7:loja-dono: &cnão possui nenhuma loja!")

    val signLine1 = messageConfiguration.getString("placa.linha-1", "&6✌ &8[&aVotar&8] &6✌")
    val signLine2 = messageConfiguration.getString("placa.linha-2", "&bVotos: &a:votos:")
    val signLine3 = messageConfiguration.getString("placa.linha-3", ":loja:")
    val signLine4 = messageConfiguration.getString("placa.linha-4", "&cClica para votar")
    val signErrorLojaNotFound = messageConfiguration.getMessageBuilder("placa.loja-nao-encontrada", "&cTu não tens nenhuma loja defenida, por isso não podes setar uma placa de votos.")
    val signInvalid = messageConfiguration.getMessageBuilder("placa.invalida", "&cEssa placa não pertence a nenhuma loja!")
    val signPlaced = messageConfiguration.getMessageBuilder("placa.definida", "&aDefeniste uma placa de votos para a tua loja!")
    val signDestroyed = messageConfiguration.getMessageBuilder("placa.destruida", "&aDestruiste uma placa de votos da tua loja!")
}