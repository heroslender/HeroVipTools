package com.heroslender.viptools.loja

import com.heroslender.pluginwrapper.utils.Placeholder
import com.heroslender.pluginwrapper.utils.event
import com.heroslender.pluginwrapper.utils.exceptions.CommandException
import com.heroslender.pluginwrapper.utils.exceptions.InsufficientPermissionException
import com.heroslender.viptools.Config
import com.heroslender.viptools.HeroVipTools
import org.bukkit.block.Sign
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPhysicsEvent
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.player.PlayerInteractEvent

class SignController : Listener {

    init {
        /**
         * Evento ao colocar uma placa de votos
         */
        event<SignChangeEvent> {
            if (isCancelled) return@event

            if (getLine(0).equals("[votar]", true)) {
                try {
                    if (!player.hasPermission(Config.PERMISSION_CREATE_SIGN)) {
                        isCancelled = true
                        throw InsufficientPermissionException(Config.PERMISSION_CREATE_SIGN)
                    }

                    val msgConfig = HeroVipTools.instance.messagesConfig
                    val placeholder = Placeholder()
                            .add("loja-dono", player.name)

                    val loja = LojaManager.getOrNull(player.name)
                            ?: throw CommandException(msgConfig.signErrorLojaNotFound, placeholder)
                    placeholder.add(loja.placeholder)

                    // TODO prevenir a criação de mais placas baseado num limite configuravel

                    setLine(0, placeholder.load(msgConfig.signLine1))
                    setLine(1, placeholder.load(msgConfig.signLine2))
                    setLine(2, placeholder.load(msgConfig.signLine3))
                    setLine(3, placeholder.load(msgConfig.signLine4))

                    loja.addSign(block.location)
                    player.sendMessage(msgConfig.signPlaced.build(placeholder))
                } catch (e: CommandException) {
                    // Este evento não instanceia o `PlayerEvent` por isso que tenho de fazer assim manual o try catch
                    player.sendMessage(e.messageBuilder.build(e.placeholder))
                }
            }
        }

        /**
         * Evento de quebrar a placa de votos
         */
        event<BlockBreakEvent> {
            if (isCancelled || block.state !is Sign) return@event

            val sign = block.state as Sign

            // Verificar se a placa é valida pela "key" que é o texto da primeira linha
            if (sign.getLine(0) == HeroVipTools.instance.messagesConfig.signLine1) {
                val loja = LojaManager.getOrNull(block.location)
                        ?: return@event // A placa não é valida, pode quebrar avontade

                if (player.hasPermission(Config.PERMISSION_ADMIN) || player.name == loja.owner) {
                    loja.removeSign(block.location)
                    player.sendMessage(HeroVipTools.instance.messagesConfig.signDestroyed.build(loja.placeholder))
                } else {
                    isCancelled = true
                }
            }
        }

        /**
         * Evento de quando player vota na loja
         */
        event<PlayerInteractEvent> {
            if (isCancelled || action != Action.RIGHT_CLICK_BLOCK || clickedBlock.state !is Sign) return@event

            // TODO delay entre votos para prevenir spam

            val sign = clickedBlock.state as Sign
            if (sign.getLine(0) == HeroVipTools.instance.messagesConfig.signLine1) {
                val loja = LojaManager[sign.location]
                val placeholder = Placeholder()
                        .add("player", player.name)
                        .add(loja.placeholder)

                val hasVoted = loja.addVoto(player.name)
                if (!hasVoted) {
                    loja.removeVoto(player.name)
                    player.sendMessage(HeroVipTools.instance.messagesConfig.lojaUnvoted.build(placeholder))
                } else {
                    player.sendMessage(HeroVipTools.instance.messagesConfig.lojaVoted.build(placeholder))
                }
            }
        }

        /**
         * Prevenir de destruir a placa quando se remove o bloco por de traz ou baixo.
         * Pode ser usado para os players deixarem as placas a "voar", como que se fosse decoração.
         */
        event<BlockPhysicsEvent> {
            if (isCancelled || block.state !is Sign) return@event

            val sign = block.state as Sign

            if (sign.getLine(0) != HeroVipTools.instance.messagesConfig.signLine1)
                isCancelled = true
        }
    }
}