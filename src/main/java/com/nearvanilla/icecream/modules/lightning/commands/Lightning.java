/* Licensed under GNU Affero General Public License v3.0 */
package com.nearvanilla.icecream.modules.lightning.commands;

import com.nearvanilla.icecream.IceCream;
import java.util.Random;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Lightning implements CommandExecutor {
  @Override
  public boolean onCommand(
      @NotNull CommandSender commandSender,
      @NotNull Command command,
      @NotNull String s,
      @NotNull String[] strings) {
    // Check if sender is a player.
    if (!(commandSender instanceof Player player)) {
      commandSender.sendMessage("Only players can use this command.");
      return true;
    }
    // Check if only 1 argument is provided.
    if (strings.length != 1) {
      player.sendMessage(
          Component.text("Usage: /lightning <player>", NamedTextColor.RED, TextDecoration.BOLD));
      return true;
    }
    // Get target player name from args and check if they're online.
    String targetPlayerName = strings[0];
    Player targetPlayer = player.getServer().getPlayer(targetPlayerName);
    if (targetPlayer == null) {
      player.sendMessage(
          Component.text("Player not found.", NamedTextColor.RED, TextDecoration.BOLD));
      return true;
    }
    // Get chance of lightning strike.
    double chance =
        IceCream.getInstance()
            .getCustomConfig()
            .getDouble("module-settings.lightning.strike-sender-chance");
    double randomNo = new Random().nextDouble();
    boolean strikeSender = randomNo <= chance;
    // Strike sender if chance is met.
    if (strikeSender) {
      player.getWorld().strikeLightning(player.getLocation());
      player.sendMessage(
          Component.text(
              "You have been struck by lightning!", NamedTextColor.RED, TextDecoration.BOLD));
    } else {
      targetPlayer.getWorld().strikeLightning(targetPlayer.getLocation());
      targetPlayer.sendMessage(
          Component.text(
              "You have been struck by lightning!", NamedTextColor.RED, TextDecoration.BOLD));
    }
    return true;
  }
}
