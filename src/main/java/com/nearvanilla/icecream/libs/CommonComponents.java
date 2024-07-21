/* Licensed under GNU Affero General Public License v3.0 */
package com.nearvanilla.icecream.libs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public record CommonComponents() {

  public static final TextComponent INVALID_PERMISSION_COMPONENT =
      Component.text(
          "You do not have permission to run this command.",
          NamedTextColor.RED,
          TextDecoration.BOLD);

  public static final TextComponent INVALID_PLAYER_COMPONENT =
      Component.text("You must be a player to run this command.");

  public static final TextComponent NO_ARGS_COMPONENT =
      Component.text(
          "You have not provided any arguments.", NamedTextColor.RED, TextDecoration.BOLD);

  public static final TextComponent INVALID_ARGS_COMPONENT =
      Component.text(
          "You have provided an invalid number of arguments",
          NamedTextColor.RED,
          TextDecoration.BOLD);
}
