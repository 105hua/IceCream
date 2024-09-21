/* Licensed under the GNU Affero General Public License. */
package com.nearvanilla.iceCream.commands

import com.nearvanilla.iceCream.IceCream
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.CommandDescription
import org.incendo.cloud.annotations.Permission

class ModuleCommand {
    private val enabledComponent = Component.text("Module enabled.", NamedTextColor.GREEN)

    private val disableComponent = Component.text("Module disabled.", NamedTextColor.RED)

    private val invalidActionComponent =
        Component.text("Invalid action. Expecting either 'enable' or 'disable'.", NamedTextColor.RED)

    private val invalidModuleComponent = Component.text(
        "Invalid Module. Please check the documentation for a list of modules and try again.",
        NamedTextColor.RED
    )

    @Command("module <action> <module>")
    @CommandDescription("Enable or disable a module.")
    @Permission("icecream.module")
    fun moduleCommand(
        sourceStack: CommandSourceStack,
        @Argument(value = "action") action: String,
        @Argument(value = "module") module: String?
    ) {
        val sender = sourceStack.sender
        if (sender !is Player) {
            sender.sendMessage("This command can only be run by a player.")
            return
        }
        when (module) {
            "lightning" -> {
                run {
                    if (action == "enable") {
                        IceCream.lightningModule.register()
                        sender.sendMessage(enabledComponent)
                    } else if (action == "disable") {
                        IceCream.lightningModule.unregister()
                        sender.sendMessage(disableComponent)
                    } else {
                        sender.sendMessage(invalidActionComponent)
                    }
                }
                sender.sendMessage(invalidModuleComponent)
            }

            else -> sender.sendMessage(invalidModuleComponent)
        }
    }
}
