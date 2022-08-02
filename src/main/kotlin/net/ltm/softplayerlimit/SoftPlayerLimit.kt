package net.ltm.softplayerlimit

import com.google.inject.Inject
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import java.nio.file.Path
import kotlin.properties.Delegates


@Plugin(
    id = "softplayerlimit",
    name = "SoftPlayerLimit",
    version = "0.1.0-SNAPSHOT",
    description = "Use a soft way to control player count",
    authors = ["Longtianmu"]
)
class SoftPlayerLimit {
    companion object Properties {
        lateinit var server: ProxyServer
        lateinit var logger: Logger
        lateinit var configParser: ConfigParser

        object Configs {
            var playerLimits by Delegates.notNull<Int>()
            lateinit var kickMessage: String
        }
    }

    @Inject
    fun init(server: ProxyServer, logger: Logger, @DataDirectory dataDirectory: Path) {
        Properties.server = server
        Properties.logger = logger
        configParser = ConfigParser(dataDirectory)
        configParser.reload()
    }

    @Subscribe
    fun onInitialize(event: ProxyInitializeEvent?) {
        server.eventManager.register(this,PlayerCheckers())
    }
}