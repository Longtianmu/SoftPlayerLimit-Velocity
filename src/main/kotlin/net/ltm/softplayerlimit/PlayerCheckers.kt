package net.ltm.softplayerlimit

import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.ResultedEvent
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.LoginEvent
import net.kyori.adventure.text.Component

class PlayerCheckers {
    @Subscribe(order = PostOrder.FIRST)
    fun onPlayerJoin(event: LoginEvent) {
        val nowPlayers = SoftPlayerLimit.server.playerCount
        val maxPlayer = SoftPlayerLimit.Properties.Configs.playerLimits
        if (nowPlayers >= maxPlayer) {
            if (!event.player.hasPermission("net.ltm.softplayerlimit.bypass")) {
                val res = SoftPlayerLimit.Properties.Configs.kickMessage
                event.result = ResultedEvent.ComponentResult.denied(Component.text(res))
            }else{
                SoftPlayerLimit.logger.info("Player ${event.player.username} Bypass the Limit.")
            }
        }
    }
}