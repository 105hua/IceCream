/* Licensed under the GNU Affero General Public License. */
package com.nearvanilla.iceCream.libs

interface Module {
    fun shouldEnable(): Boolean

    fun registerEvents()

    fun unRegisterEvents()

    fun registerCommands()

    fun unregisterCommands()

    fun register() {
        registerEvents()
        registerCommands()
    }

    fun unregister() {
        unRegisterEvents()
        unregisterCommands()
    }

    companion object {
        var isEnabled: Boolean = false
    }
}
