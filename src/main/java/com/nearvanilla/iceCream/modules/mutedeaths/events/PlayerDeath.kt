/* Licensed under the GNU Affero General Public License. */
package com.nearvanilla.iceCream.modules.mutedeaths.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeath : Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val deathMessage = event.deathMessage()
        event.deathMessage(null)
    }
}
