/* Licensed under the GNU Affero General Public License. */
package com.nearvanilla.iceCream.modules.lightning.commands

import com.nearvanilla.iceCream.IceCream
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.CommandDescription
import org.incendo.cloud.annotations.Permission

class StrikeCommand {
    private val strikeMessage = Component.text("You have been struck by lightning!", NamedTextColor.RED)

    private val strikeSenderMessage = Component.text("Whoops, you got unlucky :(", NamedTextColor.RED)

    private val playerNotFoundMessage = Component.text("The player could not be found.", NamedTextColor.RED)

    @Command("strike <player>")
    @CommandDescription("Strike a player with lightning.")
    @Permission("icecream.lightning.strike")
    fun strikeCommand(
        sourceStack: CommandSourceStack, @Argument(value = "player") player: String?
    ) {
        val sender = sourceStack.sender
        if (sender !is Player) {
            sender.sendMessage("You must be a player to use this command.")
            return
        }
        val strikeChance = IceCream.pluginInstance.config.getInt("lightning.strike-chance")
        val strikeSender = Math.random() * 100 < strikeChance
        val target = IceCream.pluginInstance.server.getPlayer(player!!)
        if (target == null) {
            sender.sendMessage(playerNotFoundMessage)
            return
        }
        if (!strikeSender) {
            target.world.strikeLightning(target.location)
            target.sendMessage(strikeMessage)
        } else {
            sender.world.strikeLightning(sender.location)
            sender.sendMessage(strikeSenderMessage)
        }
    }
}
