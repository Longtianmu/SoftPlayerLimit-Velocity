package net.ltm.softplayerlimit

import ninja.leaping.configurate.yaml.YAMLConfigurationLoader
import java.io.File
import java.nio.file.Path
import kotlin.io.path.exists

class ConfigParser(path: Path) {
    var dirPath: Path

    init {
        dirPath = path
        val configFile = File(path.toFile(), "config.yml")
        if(!dirPath.exists()){
            dirPath.toFile().mkdirs()
        }
        if (!configFile.exists()) {
            configFile.createNewFile()
            configFile.writeBytes(this.javaClass.classLoader.getResource("config.yml")!!.readBytes())
        }
    }

    fun reload() {
        val configFile = File(dirPath.toFile(), "config.yml")
        val configsLoader = YAMLConfigurationLoader.builder().setPath(configFile.toPath()).build()
        val config = configsLoader.load()
        SoftPlayerLimit.Properties.Configs.playerLimits = config.getNode("player-limits").int
        SoftPlayerLimit.Properties.Configs.kickMessage = config.getNode("kicked-message").string.toString()
        SoftPlayerLimit.logger.info("Now Player-Limit: ${SoftPlayerLimit.Properties.Configs.playerLimits}")
        SoftPlayerLimit.logger.info("Now Kick Message: ${SoftPlayerLimit.Properties.Configs.kickMessage}")
    }
}