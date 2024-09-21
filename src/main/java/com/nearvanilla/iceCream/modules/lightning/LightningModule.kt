/* Licensed under the GNU Affero General Public License. */
package com.nearvanilla.iceCream.modules.lightning

import com.nearvanilla.iceCream.IceCream
import com.nearvanilla.iceCream.libs.Module
import com.nearvanilla.iceCream.modules.lightning.commands.StrikeCommand

class LightningModule : Module {
    override fun shouldEnable(): Boolean {
        val enabled = IceCream.pluginInstance.config.getBoolean("modules.lightning.enabled")
        IceCream.pluginInstance.logger.info("Lightning Module enabled: $enabled")
        return enabled
    }

    override fun registerEvents() {}

    override fun unRegisterEvents() {}

    override fun registerCommands() {
        IceCream.annotationParser.parse(StrikeCommand())
    }

    override fun unregisterCommands() {
        IceCream.commandManager.deleteRootCommand("strike")
    }
}
