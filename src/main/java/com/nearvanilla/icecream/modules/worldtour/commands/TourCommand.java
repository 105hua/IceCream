/* Licensed under GNU Affero General Public License v3.0 */
package com.nearvanilla.icecream.modules.worldtour.commands;

import com.nearvanilla.icecream.IceCream;
import com.nearvanilla.icecream.libs.CommonComponents;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.jetbrains.annotations.NotNull;

public class TourCommand implements CommandExecutor {

  private final ScoreboardManager scoreboardManager =
      IceCream.getInstance().getServer().getScoreboardManager();
  private final Scoreboard mainScoreboard = scoreboardManager.getMainScoreboard();

  // Util function to check if tour scoreboard exists.
  private boolean checkScoreboard() {
    return mainScoreboard.getObjective("world_tour") != null;
  }

  // Util function to get player by name.
  private Player getPlayerByName(String name) {
    return IceCream.getInstance().getServer().getPlayer(name);
  }

  // Create scoreboard functionality.
  private boolean createScoreboard() {
    if (mainScoreboard.getObjective("world_tour") == null) {
      mainScoreboard.registerNewObjective(
          "world_tour",
          Criteria.DUMMY,
          Component.text("World Tour", NamedTextColor.AQUA, TextDecoration.BOLD));
      return true;
    } else {
      return false;
    }
  }

  // Remove scoreboard functionality.
  private boolean removeScoreboard() {
    if (mainScoreboard.getObjective("world_tour") != null) {
      mainScoreboard.getObjective("world_tour").unregister();
      return true;
    } else {
      return false;
    }
  }

  // Add all players to tour list.
  private void addAllPlayersToTourList(Player playerSender) {
    if (checkScoreboard()) {
      IceCream.getInstance()
          .getServer()
          .getOnlinePlayers()
          .forEach(
              player -> {
                if (player != null) {
                  Score playerScore =
                      mainScoreboard.getObjective("world_tour").getScore(player.getName());
                  playerScore.setScore(1);
                }
              });
      playerSender.sendMessage(
          Component.text("All players have been added to the tour list.", NamedTextColor.GREEN));
    } else {
      playerSender.sendMessage(
          Component.text("Failed to add all players to the tour list.", NamedTextColor.RED));
    }
  }

  // Add specific player to tour list.
  private void addPlayerToTourList(Player player, Player playerSender) {
    if (player == null) {
      playerSender.sendMessage(Component.text("Player does not exist.", NamedTextColor.RED));
      return;
    }

    if (checkScoreboard()) {
      Score playerScore = mainScoreboard.getObjective("world_tour").getScore(player.getName());
      playerScore.setScore(1);
      playerSender.sendMessage(
          Component.text(
              "Player " + player.getName() + " has been added to the tour list.",
              NamedTextColor.GREEN));
    } else {
      playerSender.sendMessage(
          Component.text(
              "Failed to add player " + player.getName() + " to the tour list.",
              NamedTextColor.RED));
    }
  }

  // Give glowing effect to player.
  private void glowPlayer(Player player) {
    PotionEffect glowEffect = new PotionEffect(PotionEffectType.GLOWING, 21600, 1, false, false);
    player.addPotionEffect(glowEffect);
    player.sendMessage(Component.text("You are now glowing.", NamedTextColor.GREEN));
  }

  // Remove player from tour list.
  private void removeFromTourList(Player player) {
    if (player == null) {
      return;
    }
    if (checkScoreboard()) {
      Score playerScore = mainScoreboard.getObjective("world_tour").getScore(player.getName());
      playerScore.setScore(0);
    }
  }

  // Reset scoreboard by removing and creating it again.
  private void resetScoreboard() {
    removeScoreboard();
    createScoreboard();
  }

  // Start time post-tour.
  private void startServerTime() {
    List<World> worlds = IceCream.getInstance().getServer().getWorlds();
    for (World world : worlds) {
      world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
      world.setGameRule(GameRule.DO_WEATHER_CYCLE, true);
    }
  }

  // Stop time pre-tour
  private void stopServerTime() {
    List<World> worlds = IceCream.getInstance().getServer().getWorlds();
    for (World world : worlds) {
      world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
      world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
    }
  }

  // Teleport all players in tour list to player.
  private void teleportTourListToPlayer(Player player) {
    if (checkScoreboard()) {
      IceCream.getInstance()
          .getServer()
          .getOnlinePlayers()
          .forEach(
              onlinePlayer -> {
                Score playerScore =
                    mainScoreboard.getObjective("world_tour").getScore(onlinePlayer.getName());
                if (playerScore.getScore() == 1) {
                  onlinePlayer.teleport(player);
                }
              });
    } else {
      player.sendMessage(Component.text("Failed to teleport players to you.", NamedTextColor.RED));
    }
  }

  // Remove glow from player
  private void removeGlow(Player player) {
    player.removePotionEffect(PotionEffectType.GLOWING);
    player.sendMessage(Component.text("You are no longer glowing.", NamedTextColor.GREEN));
  }

  @Override
  public boolean onCommand(
      @NotNull CommandSender commandSender,
      @NotNull Command command,
      @NotNull String s,
      @NotNull String[] strings) {
    // If sender is not a player.
    if (!(commandSender instanceof Player player)) {
      commandSender.sendMessage(CommonComponents.INVALID_PLAYER_COMPONENT);
      return true;
    }
    // If player does not have permission.
    if (!player.hasPermission("icecream.worldtour.tour")) {
      player.sendMessage(CommonComponents.INVALID_PERMISSION_COMPONENT);
      return true;
    }
    // Get subcommand
    if (strings.length == 0) {
      player.sendMessage(CommonComponents.NO_ARGS_COMPONENT);
      return true;
    }

    // Get subcommand and do a switch case to determine the necessary action.
    String subcommand = strings[0];

      switch (subcommand) {
          case "addalltolist" -> addAllPlayersToTourList(player);
          case "addtolist" -> {
              if (strings.length < 2) {
                  player.sendMessage(CommonComponents.INVALID_ARGS_COMPONENT);
                  return true;
              }
              addPlayerToTourList(getPlayerByName(strings[1]), player);
          }
          case "glow" -> glowPlayer(player);
          case "removefromlist" -> {
              if (strings.length < 2) {
                  player.sendMessage(CommonComponents.INVALID_ARGS_COMPONENT);
                  return true;
              }
              removeFromTourList(getPlayerByName(strings[1]));
              player.sendMessage(Component.text(
                      "Player " + strings[1] + " has been removed from the tour list.",
                        NamedTextColor.GREEN
              ));
          }
          case "reset" -> {
            resetScoreboard();
            player.sendMessage(Component.text("Scoreboard has been reset.", NamedTextColor.GREEN));
          }
          case "starttime" -> {
            startServerTime();
            player.sendMessage(Component.text("Server time has been started.", NamedTextColor.GREEN));
          }
          case "stoptime" -> {
            stopServerTime();
            player.sendMessage(Component.text("Server time has been stopped.", NamedTextColor.GREEN));
          }
          case "tpall" -> {
            teleportTourListToPlayer(player);
            player.sendMessage(Component.text("Players have been teleported to you.", NamedTextColor.GREEN));
          }
          case "removeglow" -> removeGlow(player);
          default -> player.sendMessage(Component.text("Invalid subcommand.", NamedTextColor.RED));
      }
    return true;
  }
}
