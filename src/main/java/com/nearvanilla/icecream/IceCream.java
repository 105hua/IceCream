/* Licensed under GNU Affero General Public License v3.0 */
package com.nearvanilla.icecream;

import com.nearvanilla.icecream.commands.ModuleCommand;
import com.nearvanilla.icecream.modules.lightning.LightningModule;
import com.nearvanilla.icecream.modules.worldtour.WorldTourModule;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class IceCream extends JavaPlugin {

  // Main class variables.
  private static Logger logger;
  private static IceCream instance;
  private FileConfiguration customConfig;

  // Modules
  public static final WorldTourModule worldTourModule = new WorldTourModule();
  public static final LightningModule lightningModule = new LightningModule();

  // TODO Add functionality for modules.

  @Override
  public void onEnable() {
    // Setup logger.
    logger = getLogger();
    // Setup instance.
    instance = this;
    // Save default config, if one does not exist.
    this.createCustomConfig();
    // Load Module Command.
    this.getCommand("module").setExecutor(new ModuleCommand());
    // Load modules.
    if (worldTourModule.shouldEnable()) {
      worldTourModule.enable();
      logger.info("The WorldTour module has been enabled.");
    }
    if (lightningModule.shouldEnable()) {
      lightningModule.enable();
      logger.info("The Lightning module has been enabled.");
    }
    logger.info("IceCream has been enabled.");
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }

  public static IceCream getInstance() {
    return instance;
  }

  public FileConfiguration getCustomConfig() {
    return this.customConfig;
  }

  private void createCustomConfig() {
    File customConfigFile = new File(getDataFolder(), "configuration.yml");
    if (!customConfigFile.exists()) {
      customConfigFile.getParentFile().mkdirs(); // Result of operation doesn't need to be saved.
      saveResource("configuration.yml", false);
    }
    customConfig = new YamlConfiguration();
    try {
      customConfig.load(customConfigFile);
    } catch (IOException | InvalidConfigurationException e) {
      e.printStackTrace(); // TODO Maybe a more graceful way to handle this?
    }
  }
}
