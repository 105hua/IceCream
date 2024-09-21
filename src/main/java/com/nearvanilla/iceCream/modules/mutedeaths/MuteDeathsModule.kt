/* Licensed under the GNU Affero General Public License. */
package com.nearvanilla.iceCream.modules.mutedeaths

import com.nearvanilla.iceCream.IceCream
import com.nearvanilla.iceCream.libs.Module
import org.bukkit.NamespacedKey

class MuteDeathsModule : Module {
    override fun shouldEnable(): Boolean {
        return IceCream.pluginInstance.config.getBoolean("mutedeaths.enabled")
    }

    override fun registerEvents() {}

    override fun unRegisterEvents() {}

    override fun registerCommands() {}

    override fun unregisterCommands() {}

    companion object {
        val TOGGLE_KEY: NamespacedKey = NamespacedKey(IceCream.pluginInstance, "mutedeaths")
    }
}
