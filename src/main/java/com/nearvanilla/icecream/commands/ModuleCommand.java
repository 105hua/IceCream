/* Licensed under GNU Affero General Public License v3.0 */
package com.nearvanilla.icecream.commands;

import com.nearvanilla.icecream.IceCream;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ModuleCommand implements CommandExecutor {

  @Override
  public boolean onCommand(
      @NotNull CommandSender commandSender,
      @NotNull Command command,
      @NotNull String s,
      @NotNull String[] strings) {
    // Check if player is executing command.
    if (!(commandSender instanceof Player player)) {
      commandSender.sendMessage("Only players can use this command.");
      return true;
    }
    // Check if 2 arguments are provided.
    if (strings.length == 2) {
      player.sendMessage(
          Component.text("Usage: /module <enable|disable> <module>", NamedTextColor.RED));
    }
    String enableDisable = strings[0];
    String module = strings[1];
    // Check if enable or disable is provided.
    if (!enableDisable.equals("enable") && !enableDisable.equals("disable")) {
      player.sendMessage(
          Component.text("Usage: /module <enable|disable> <module>", NamedTextColor.RED));
      return true;
    }
    // Check if module is provided.
    if (module.isEmpty()) {
      player.sendMessage(
          Component.text("Usage: /module <enable|disable> <module>", NamedTextColor.RED));
      return true;
    }
    // Switch statement to enable or disable module.
    switch (module) {
      case "lightning":
        if (enableDisable.equals("enable")) {
          IceCream.lightningModule.enable();
          player.sendMessage(Component.text("Lightning module enabled.", NamedTextColor.GREEN));
        } else {
          IceCream.lightningModule.disable();
          player.sendMessage(Component.text("Lightning module disabled.", NamedTextColor.GREEN));
        }
        break;
      case "worldtour":
        if (enableDisable.equals("enable")) {
          IceCream.worldTourModule.enable();
          player.sendMessage(Component.text("WorldTour module enabled.", NamedTextColor.GREEN));
        } else {
          IceCream.worldTourModule.disable();
          player.sendMessage(Component.text("WorldTour module disabled.", NamedTextColor.GREEN));
        }
        break;
      default:
        player.sendMessage(
            Component.text("Usage: /module <enable|disable> <module>", NamedTextColor.RED));
        break;
    }
    return true;
  }
}
