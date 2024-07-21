/* Licensed under GNU Affero General Public License v3.0 */
package com.nearvanilla.icecream.modules.lightning;

import com.nearvanilla.icecream.IceCream;
import com.nearvanilla.icecream.modules.ModuleInterface;
import com.nearvanilla.icecream.modules.lightning.commands.Lightning;
import org.bukkit.event.Listener;

public class LightningModule implements ModuleInterface {

  private boolean isEnabled = false;

  @Override
  public boolean shouldEnable() {
    return IceCream.getInstance().getCustomConfig().getBoolean("modules.lightning");
  }

  @Override
  public void registerEvents(Listener listener) {
    IceCream.getInstance().getLogger().warning("LightningModule does not have any events.");
  }

  @Override
  public void unregisterEvents(Listener listener) {
    IceCream.getInstance().getLogger().warning("LightningModule does not have any events.");
  }

  @Override
  public void registerCommands() {
    IceCream.getInstance().getCommand("lightning").setExecutor(new Lightning());
  }

  @Override
  public void unregisterCommands() {
    IceCream.getInstance().getCommand("lightning").setExecutor(null);
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
