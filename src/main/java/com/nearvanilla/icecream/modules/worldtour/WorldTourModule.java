/* Licensed under GNU Affero General Public License v3.0 */
package com.nearvanilla.icecream.modules.worldtour;

import com.nearvanilla.icecream.IceCream;
import com.nearvanilla.icecream.modules.ModuleInterface;
import com.nearvanilla.icecream.modules.worldtour.commands.*;
import org.bukkit.event.Listener;

public class WorldTourModule implements ModuleInterface {

  private boolean isEnabled = false;

  @Override
  public boolean shouldEnable() {
    return IceCream.getInstance().getCustomConfig().getBoolean("modules.world-tour");
  }

  @Override
  public void registerEvents(Listener listener) { // Kept as false return as no events are required.
    IceCream.getInstance().getLogger().warning("WorldTourModule does not have any events.");
  }

  @Override
  public void unregisterEvents(Listener listener) { // Same with unregisterEvents.
    IceCream.getInstance().getLogger().warning("WorldTourModule does not have any events.");
  }

  @Override
  public void registerCommands() {
    IceCream.getInstance().getCommand("tour").setExecutor(new TourCommand());
  }

  @Override
  public void unregisterCommands() {
    IceCream.getInstance().getCommand("tour").setExecutor(null);
  }

  @Override
  public void enable() {
    if (!isEnabled) {
      registerCommands();
      isEnabled = true;
    }
  }

  @Override
  public void disable() {
    if (isEnabled) {
      unregisterCommands();
      isEnabled = false;
    }
  }
}
