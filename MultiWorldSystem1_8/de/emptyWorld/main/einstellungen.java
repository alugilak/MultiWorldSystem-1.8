package de.emptyWorld.main;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class einstellungen
{
  static einstellungen instanz = new einstellungen();
  Plugin p;
  FileConfiguration data;
  File dfile;
  
  public static einstellungen getInstance()
  {
    return instanz;
  }
  
  public void setup(Plugin p)
  {
    if (!p.getDataFolder().exists()) {
      p.getDataFolder().mkdir();
    }
    this.dfile = new File(p.getDataFolder(), "warpsAndspawns.yml");
    if (!this.dfile.exists()) {
      try
      {
        this.dfile.createNewFile();
      }
      catch (IOException d)
      {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Kann die datei nicht lesen data.yml!");
      }
    }
    this.data = YamlConfiguration.loadConfiguration(this.dfile);
  }
  
  public FileConfiguration getData()
  {
    return this.data;
  }
  
  public void saveData()
  {
    try
    {
      this.data.save(this.dfile);
    }
    catch (IOException d)
    {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Kann Datei nicht speichern data.yml!");
    }
  }
  
  public PluginDescriptionFile getDesc()
  {
    return this.p.getDescription();
  }
  
  public void reloadData()
  {
    this.data = YamlConfiguration.loadConfiguration(this.dfile);
  }
}
