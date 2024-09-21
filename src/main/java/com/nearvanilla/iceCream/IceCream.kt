/* Licensed under the GNU Affero General Public License. */
package com.nearvanilla.iceCream

import com.nearvanilla.iceCream.commands.ModuleCommand
import com.nearvanilla.iceCream.modules.lightning.LightningModule
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.plugin.java.JavaPlugin
import org.incendo.cloud.annotations.AnnotationParser
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.paper.PaperCommandManager

class IceCream : JavaPlugin() {
    override fun onEnable() {
        // Define public plugin instance and save the default config, to ensure one exists before using
        // it.
        pluginInstance = this
        saveDefaultConfig()
        // Initialize the command manager and annotation parser.
        commandManager =
            PaperCommandManager.builder()
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(this)
        annotationParser = AnnotationParser(
            commandManager, CommandSourceStack::class.java
        )
        // Check each module and enable if needed.
        lightningModule = LightningModule()
        if (lightningModule.shouldEnable()) {
            logger.info("Enabling the Lightning Module...")
            lightningModule.register()
            logger.info("Lightning Module enabled!")
        }
        // Register module command.
        annotationParser.parse(ModuleCommand())
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    companion object {
        lateinit var pluginInstance: IceCream
        lateinit var commandManager: PaperCommandManager<CommandSourceStack>
        lateinit var annotationParser: AnnotationParser<CommandSourceStack>
        lateinit var lightningModule: LightningModule
    }
}
