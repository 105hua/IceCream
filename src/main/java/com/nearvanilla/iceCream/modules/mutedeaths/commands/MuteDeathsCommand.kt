/* Licensed under the GNU Affero General Public License. */
package com.nearvanilla.iceCream.modules.mutedeaths.commands

import com.nearvanilla.iceCream.modules.mutedeaths.MuteDeathsModule
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.CommandDescription
import org.incendo.cloud.annotations.Permission

class MuteDeathsCommand {
    private val muteOnComponent = Component.text("Death messages are now muted.", NamedTextColor.GREEN)

    private val muteOffComponent = Component.text("Death messages are now unmuted.", NamedTextColor.GREEN)

    private val errorComponent = Component.text("An error occurred while toggling death messages.", NamedTextColor.RED)

    @Command("mutedeaths|mutedeathmessages|mutedeathmsgs")
    @CommandDescription("Toggle death messages on or off.")
    @Permission("icecream.mutedeaths.toggle")
    fun muteDeathsCommand(sourceStack: CommandSourceStack) {
        val sender = sourceStack.sender
        if (sender !is Player) {
            sender.sendMessage("This command can only be run by a player.")
            return
        }
        val playerContainer: PersistentDataContainer = sender.persistentDataContainer
        if (playerContainer.has(MuteDeathsModule.TOGGLE_KEY, PersistentDataType.BOOLEAN)) {
            try {
                val currentValue =
                    playerContainer.get(MuteDeathsModule.TOGGLE_KEY, PersistentDataType.BOOLEAN)!!
                val newValue = !currentValue
                playerContainer.set(MuteDeathsModule.TOGGLE_KEY, PersistentDataType.BOOLEAN, newValue)
                sender.sendMessage(if (newValue) muteOnComponent else muteOffComponent)
            } catch (exc: NullPointerException) {
                sender.sendMessage(errorComponent)
                exc.printStackTrace()
            }
        } else {
            playerContainer.set(MuteDeathsModule.TOGGLE_KEY, PersistentDataType.BOOLEAN, false)
            sender.sendMessage(muteOffComponent)
        }
    }
}
