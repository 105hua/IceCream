/* Licensed under GNU Affero General Public License v3.0 */
package com.nearvanilla.icecream.modules;

import org.bukkit.event.Listener;

public interface ModuleInterface {
  boolean shouldEnable(); // Returns true if config has module enabled, false otherwise.

  void registerEvents(Listener listener); // Registers events for the module.

  void unregisterEvents(Listener listener); // Opposite of registerEvents.

  void registerCommands(); // Registers commands for the module.

  void unregisterCommands(); // Opposite of registerCommands.

  void enable(); // Enables the module.

  void disable(); // Disables the module.
}
