package de.emptyWorld.main;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class leerWelt
  extends JavaPlugin
  implements Listener
{
  einstellungen settings = einstellungen.getInstance();
  FileConfiguration data;
  private static Logger log = Logger.getLogger("ZauschCraft");
  File dfile;
  World world;

  public void onDisable()
  {
    PluginDescriptionFile desc = getDescription();
    
    log.info(desc.getName() + " " + desc.getVersion() + "  " + " Wird ausgeschaltet!");
  }
  
  public void onEnable()
  {
    this.data = getConfig();
    
    this.data.options().copyDefaults(true);
    this.settings.setup(this);
    getConfig().options().copyDefaults(true);
    saveConfig();
    getServer().getPluginManager().registerEvents(this, this);
    PluginDescriptionFile p = getDescription();
   
    log.info(p.getName() + " " + p.getVersion() + " " + "https://www.spigotmc.org/resources/multiworldsystem-create-world-cleanroom.51764/" + " ist aktiviert!");
  }

  public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
  {
    return new AirWorldGenerator();
  }
  
  public boolean onCommand(final CommandSender sender, Command cmd, String commandLabel, String[] args)
  {

    Player p = (Player)sender;
    if (cmd.getName().equalsIgnoreCase("mwsHelptp"))
    {
      sender.sendMessage(ChatColor.YELLOW + "**************" + ChatColor.GOLD + "MultiWorldSystem Spawn&Warp" + ChatColor.YELLOW + "************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpSpawnTxt1"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + " " + ChatColor.LIGHT_PURPLE + getConfig().getString("helpSpawnTxt2"));
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpSpawnTp1"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + " " + ChatColor.LIGHT_PURPLE + getConfig().getString("helpSpawnTp2"));
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("HelpSetWarpTxt1"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + " " + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("HelpSetWarpTxt2") + " " + ChatColor.GOLD + getConfig().getString("WarpNameInfo"));
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("HelpTpWarpTxt1"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + " " + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("HelpTpWarpTxt2") + " " + ChatColor.GOLD + getConfig().getString("WarpNameError"));
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("WarpDelTxt1"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + " " + ChatColor.LIGHT_PURPLE + getConfig().getString("WarpDelTxt2") + " " + ChatColor.GOLD + getConfig().getString("WarpNameError"));
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("HelpSetHomeTxt1"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + " " + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("HelpSetHomeTxt2") + " " + ChatColor.GOLD + getConfig().getString("HomeNameError"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpHomeTp1"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + " " + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("helpHomeTp2") + " " + ChatColor.GOLD + getConfig().getString("HomeTPError"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("HelpSetHubTxt1"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + " " + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("HelpSetHubTxt2"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpHubTp1"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + " " + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("helpHubTp2"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("HubDelTxt1"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + " " + ChatColor.LIGHT_PURPLE + getConfig().getString("HubDelTxt2"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EWGTpHelp1"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + " " + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("ewtphelp"));
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EWGHelp1"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("help tp2") + " " + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("ewghelp"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      
      return true;
    }

    if (cmd.getName().equalsIgnoreCase("ssp"))
    {
      if (!sender.hasPermission("ewg.spawn.set"))
      {
    	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("setspawnPermission") + " " + ChatColor.GREEN + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return false;
      }
      this.settings.getData().set("spawn." + ".world", p.getLocation().getWorld().getName());
      this.settings.getData().set("spawn" + ".x" , Double.valueOf(p.getLocation().getX()));
      this.settings.getData().set("spawn" + ".y" , Double.valueOf(p.getLocation().getY()));
      this.settings.getData().set("spawn" + ".z" , Double.valueOf(p.getLocation().getZ()));
      this.settings.saveData();
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("Spawnset"));
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("sp"))
    	
    {
      if (getConfig().getString("SpawnDelay") == "true")
    	  
      {
    	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("Delay") + ChatColor.BLUE + getConfig().getString("SpawnDelayMessage") );
        int delay = getConfig().getInt("Delay");
        String message = ChatColor.GOLD.toString() + ChatColor.GOLD.toString() + getConfig().getString("SystemName") + ChatColor.BOLD + " »" + ChatColor.GREEN + getConfig().getString("SpawnWelcome");
        String time = getConfig().getString("Delay");
        message = message.replace("%delay%", time);        
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
        {
          public void run()
          {
            Player p = (Player)sender;
            p = (Player)sender;
            World w = Bukkit.getServer().getWorld(leerWelt.this.settings.getData().getString("spawn.world"));
            double x = leerWelt.this.settings.getData().getDouble("spawn.x");
            double y = leerWelt.this.settings.getData().getDouble("spawn.y");
            double z = leerWelt.this.settings.getData().getDouble("spawn.z");
            p.teleport(new Location(w, x, y, z));
            p.sendMessage(ChatColor.GOLD.toString() + ChatColor.GOLD.toString() + leerWelt.this.getConfig().getString("SystemName") + ChatColor.BOLD + " »" + ChatColor.GREEN + leerWelt.this.getConfig().getString("SpawnWelcome"));
            p.getWorld().playEffect(p.getLocation(), Effect.PORTAL, 50);
            if (leerWelt.this.getConfig().getString("SpawnParticle") == "true")
            {             
              return ;
            }
          }
        }, delay * 20);
      }
    }
    else if (getConfig().getString("SpawnDelay") != "true")
    {
        World w = Bukkit.getServer().getWorld(leerWelt.this.settings.getData().getString("spawn.world"));
        double x = leerWelt.this.settings.getData().getDouble("spawn.x");
        double y = leerWelt.this.settings.getData().getDouble("spawn.y");
        double z = leerWelt.this.settings.getData().getDouble("spawn.z");
      p.teleport(new Location(w, x, y, z));
      p.sendMessage(ChatColor.GOLD.toString() + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.GOLD + getConfig().getString("Spawn hello"));
      if (getConfig().getString("SpawnParticle") == "true")
      {
        p.getWorld().playEffect(p.getLocation(), Effect.PORTAL, 50);
        return true;
      }
      if (getConfig().getString("SpawnParticle") != "true") {
        return true;
      }
    }
    if (cmd.getName().equalsIgnoreCase("sw"))
    {
      if (!sender.hasPermission("ewg.warp.set"))
      {
    	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("warpsetPermission") + " " + ChatColor.GREEN + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return false;
      }
      if (args.length == 0)
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("Warp name given"));
        return true;
      }
      this.settings.getData().set("warps." + args[0] + ".world", p.getLocation().getWorld().getName());
      this.settings.getData().set("warps." + args[0] + ".x", Double.valueOf(p.getLocation().getX()));
      this.settings.getData().set("warps." + args[0] + ".y", Double.valueOf(p.getLocation().getY()));
      this.settings.getData().set("warps." + args[0] + ".z", Double.valueOf(p.getLocation().getZ()));
      this.settings.saveData();
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("Warp create") + " " + ChatColor.GOLD + args[0]);
    }
    if (cmd.getName().equalsIgnoreCase("w"))
    {
      if (args.length == 0)
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + "Please choose a warp!");
        return false;
      }
      if (this.settings.getData().getConfigurationSection("warps." + args[0]) == null)
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + "The warp " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " does not exist!");
        return false;
      }
      World w = Bukkit.getServer().getWorld(this.settings.getData().getString("warps." + args[0] + ".world"));
      double x = this.settings.getData().getDouble("warps." + args[0] + ".x");
      double y = this.settings.getData().getDouble("warps." + args[0] + ".y");
      double z = this.settings.getData().getDouble("warps." + args[0] + ".z");
      p.teleport(new Location(w, x, y, z));
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + "You have been teleported to " + ChatColor.GOLD + args[0] + ChatColor.GREEN + "!");
      p.getWorld().playEffect(p.getLocation(), Effect.PORTAL, 50);
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("dw"))
    {
    	 if (!sender.hasPermission("ewg.dw.use"))    		      
    		      {
    		        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("warpdelPermission") + " " + ChatColor.GREEN + getConfig().getString("permissionErrorText"));
    		        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
    		        return false;
    		      }    	 
      if (args.length == 0)
    	 
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.RED + getConfig().getString("WarpNameError"));
        return false;
      }
      if (this.settings.getData().getConfigurationSection("warps." + args[0]) == null)
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + "The warp " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " does not exist!");
        return false;
      }
      this.settings.getData().set("warps." + args[0], null);
      this.settings.saveData();
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("WarpDelfinsh ") + ChatColor.GOLD + args[0] + ChatColor.GREEN + "!");
      p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("mwsr"))
    {
      reloadConfig();
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »"  + ChatColor.BLUE + getConfig().getString("Config reload"));
    }
    if (cmd.getName().equalsIgnoreCase("mwsHelp"))
    {
      sender.sendMessage(ChatColor.YELLOW + "**************" + ChatColor.GOLD + "Help MultiWorldSystem" + ChatColor.YELLOW + "************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("mvEwgCreateText1"));
      sender.sendMessage(ChatColor.WHITE + getConfig().getString("mvEwgCreatecmd1") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("mvEwgCreatecmd2") + " " + ChatColor.WHITE + getConfig().getString("mvEwgCreatecmd3") + " " + ChatColor.BLUE + getConfig().getString("mvEwgCreatecmd4"));
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("mvEWGautoCreate1") + ChatColor.WHITE + " " + getConfig().getString("mvEWGautoCreateName") + " " + ChatColor.GREEN + getConfig().getString("mvEWGautoCreate2"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + " " + ChatColor.LIGHT_PURPLE + getConfig().getString("mvEWGautoCreateName") + " " + ChatColor.GREEN + getConfig().getString("mvEWGautoCreateCmd"));
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EwgAutoMvUnload"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("EwgAutoMvUnloadCmd"));
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EwgAutoMvRemove"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("EwgAutoMvRemoveCmd"));
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EwgAutoMvDelete"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + getConfig().getString("EwgAutoMvDeleteCmd"));
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EwgSetBlock"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("EwgSetBlockCmd"));
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EwgGamemode"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("EwgGamemodeCmd"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EwgWeatherClear"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("EwgWeatherClearCmd"));
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("InvetoryClean"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("InvetoryCleanCmd"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("KillPlayer"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("KillPlayerCmd"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EwgWorldPlayerList"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("EwgWorldPlayerListCmd"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("WalkPlayer"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("WalkPlayerCmd") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("WalkPlayerCmd1"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("FlyPlayer"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("FlyPlayerCmd") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("FlyPlayerCmd1"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EwgSetDay"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("EwgSetDayCmd"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.YELLOW + "***********" + getConfig().getString("EwgWorldCreateTitel") + "************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EwgFlatCreate"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + getConfig().getString("EwgFlatCreateCmd"));
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EwgLargeBiomeCreate"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("EwgLargeBiomeCreateCmd"));
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EwgNormalCreate"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("EwgNormalCreateCmd"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EwgIslandCreate"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("EwgIslandCreateCmd"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EwgM1Create"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("EwgM1CreateCmd"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EwgSkyCreate"));
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("helpTxt") + ChatColor.LIGHT_PURPLE + " " + getConfig().getString("EwgSkyCreateCmd"));
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("ewca"))
    {
      if (!sender.hasPermission("ewg.use"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >"  + ChatColor.BLUE + getConfig().getString("EwgPermission") + " " +  getConfig().getString("permissionErrorText") );
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return false;
      }
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv create " + getConfig().getString("mvEWGautoCreateName") + " " + getConfig().getString("mvEWGautoCreateType") + " " + "-g emptyWorld");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("mvEWGautoCreateMessage"));
      if (getConfig().getString("SpawnParticle") == "true")
      {
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return true;
      }
    }
    if (cmd.getName().equalsIgnoreCase("block"))
    {
      if (!sender.hasPermission("ewg.block.set"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + getConfig().getString("blocksetPermission") + " " + ChatColor.BLUE + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return true;
      }
      Bukkit.dispatchCommand(p, "setblock ~ ~ ~ stone variant=granite");
      sender.sendMessage(ChatColor.GREEN + getConfig().getString("EwgSetBlockMessage"));
      
      return true;
    }
    
    if (cmd.getName().equalsIgnoreCase("ewua"))
    {
      if (!sender.hasPermission("ewg.use"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("EwgPermission"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return false;
      }
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv unload " + getConfig().getString("mvEWGautoCreateName"));
      
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("ewra"))
    {
      if (!sender.hasPermission("ewg.use"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("EwgPermission"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return false;
      }
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv remove " + getConfig().getString("mvEWGautoCreateName"));
      
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("ewda"))
    {
      if (!sender.hasPermission("ewg.use"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("EwgPermission"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return false;
      }
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv delete " + getConfig().getString("mvEWGautoCreateName"));
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvconfirm");
      
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("gm1"))
    {
      if (!sender.hasPermission("ewg.gm1.use"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("gm1Permission") + " " + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        
        return false;
      }
      p.setGameMode(GameMode.CREATIVE);           
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("gm0"))
    {
      if (!sender.hasPermission("ewg.gm0.use"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("gm0Permission") + " " + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return false;
      }
      p.setGameMode(GameMode.SURVIVAL);
      
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("gm2"))
    {
      if (!sender.hasPermission("ewg.gm2.use"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("gm2Permission") + " " + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return false;
      }
      p.setGameMode(GameMode.SPECTATOR);
      
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("c"))
    {
      if (!sender.hasPermission("ewg.inv.clear"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("invclearPermission") + " " + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return false;
      }
      PlayerInventory inventory = p.getInventory();
      inventory.clear();
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("invclearPermission") + " " + getConfig().getString("permissionErrorText"));
      p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return true;
      }
  
    if (cmd.getName().equalsIgnoreCase("sonne"))
    {
      if (!sender.hasPermission("ewg.sun.use"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("wclearPermission") + " " + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return false;
      }
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather clear");
      
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("EwgWeatherClearMessage"));
      
      return true;
    }

    if (cmd.getName().equalsIgnoreCase("walk"))
    {
        if (!sender.hasPermission("ewg.walk.use"))
        {
          sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("walkPermission") + " " + getConfig().getString("permissionErrorText"));
          p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
          return false;
        }
        if (args.length == 1)
        { 
      try
      {
        float speed = Float.parseFloat(args[0]) / 100.0F;
        if (speed > 1.0F) {
          speed = 1.0F;
        }
        if (speed < 0.0F) {
          speed = 0.0F;
        }
        p.setWalkSpeed(speed);
        p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.BLUE + getConfig().getString("PlaySpeedSet1"));
      }
      catch (Exception e)
      {
        p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.RED + getConfig().getString("WalkPlayerCmd1"));
        
        return true;}}
       
  }
    if (cmd.getName().equalsIgnoreCase("tag"))
    {
      if (!sender.hasPermission("ewg.day.use"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("dayPermission") + " " + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return false;
      }
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set 0 ");
      
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("EwGSetDayMessage"));
      
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("gset"))
	{
    	if (!sender.hasPermission("ewg.group.set")) {
    		  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("gsetPermission") + " " + getConfig().getString("permissionErrorText"));
    	        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
    	        return false;
}
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group1") + " "  + getConfig().getString("Cmd2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group2") + " "  + getConfig().getString("Cmd2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group2") + " "  + getConfig().getString("Cmd3") + " " + getConfig().getString("Group1"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group3") + " "  + getConfig().getString("Cmd2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group3") + " "  + getConfig().getString("Cmd3") + " " + getConfig().getString("Group2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group4") + " "  + getConfig().getString("Cmd2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group4") + " "  + getConfig().getString("Cmd3") + " " + getConfig().getString("Group3"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group5") + " "  + getConfig().getString("Cmd2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group5") + " "  + getConfig().getString("Cmd3") + " " + getConfig().getString("Group4"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group6") + " "  + getConfig().getString("Cmd2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group6") + " "  + getConfig().getString("Cmd3") + " " + getConfig().getString("Group5"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group7") + " "  + getConfig().getString("Cmd2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group7") + " "  + getConfig().getString("Cmd3") + " " + getConfig().getString("Group6"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group8") + " "  + getConfig().getString("Cmd2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group8") + " "  + getConfig().getString("Cmd3") + " " + getConfig().getString("Group7"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group9") + " "  + getConfig().getString("Cmd2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group9") + " "  + getConfig().getString("Cmd3") + " " + getConfig().getString("Group8"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group10") + " "  + getConfig().getString("Cmd2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group10") + " "  + getConfig().getString("Cmd3") + " " + getConfig().getString("Group9"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group11") + " "  + getConfig().getString("Cmd2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group11") + " "  + getConfig().getString("Cmd3") + " " + getConfig().getString("Group10"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group12") + " "  + getConfig().getString("Cmd2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group12") + " "  + getConfig().getString("Cmd3") + " " + getConfig().getString("Group11"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group13") + " "  + getConfig().getString("Cmd2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group13") + " "  + getConfig().getString("Cmd3") + " " + getConfig().getString("Group12"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group14") + " "  + getConfig().getString("Cmd2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group14") + " "  + getConfig().getString("Cmd3") + " " + getConfig().getString("Group13"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group15") + " "  + getConfig().getString("Cmd2"));
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getConfig().getString("Cmd1") + " " + getConfig().getString("Group15") + " "  + getConfig().getString("Cmd3") + " " + getConfig().getString("Group2"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("CmdMessage1"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("Group1"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("Group2"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("Group3"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("Group4"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("Group5"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("Group6"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("Group7"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("Group8"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("Group9"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("Group10"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("Group11"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("Group12"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("Group13"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("Group14"));
    	sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GREEN + getConfig().getString("Group15"));
    return true;	
	}
    if (cmd.getName().equalsIgnoreCase("mwsHelpEn"))
    {
      sender.sendMessage(ChatColor.YELLOW + "*********************" + ChatColor.DARK_RED + "HELP emptyWorld" + ChatColor.YELLOW + "********************");
      sender.sendMessage(ChatColor.GREEN + "to create an empty world enter this command");
      sender.sendMessage(ChatColor.WHITE + " /mv create" + ChatColor.LIGHT_PURPLE + " " + "<desire world name>" + " " + ChatColor.WHITE + "normal " + " " + ChatColor.BLUE + "-g emptyWorld");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "a world automatically named" + ChatColor.WHITE + " " + "emptyWorld");
      sender.sendMessage(ChatColor.GREEN + " " + "create with" + " " + ChatColor.LIGHT_PURPLE + "/ewca");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to unload the automatically created world from multivers");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/ewua");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to remove the automatically created world from multivers");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/ewra");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to delete the automatically created world from multivers");
      sender.sendMessage(ChatColor.GREEN + " " + "use this command" + ChatColor.LIGHT_PURPLE + "/ewda");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to spawn a stone block at your position");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/block");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to change your gamemode");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/gm0 /gm1 /gm2");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to show you World and Player");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/wlist");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to delete your inventory");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/c");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to kill a player");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/peng");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + "give Item to Player");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/gi <player> <ItemID> <amount>");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************"); 
      sender.sendMessage(ChatColor.GREEN + "to remove bad weather");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/sonne");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to start the day");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/tag");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to set 15 Pex Groups with Inherited permissions");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/gset");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");      
      sender.sendMessage(ChatColor.GREEN + "to show Discordchanel from config.yml");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/discord");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to show Homepage from config.yml");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/homepage");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to heal");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/heal or /heal <player>");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to set walkspeed");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/walk <20 or 30 or ....>");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to set flysmode on or off");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/f");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to set flyspeed");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/fs <20 or 30 or ....>");
      sender.sendMessage(ChatColor.GOLD + "**************World Create without MV**********************");
      sender.sendMessage(ChatColor.GREEN + "to create a flat World without Multiverse");
      sender.sendMessage(ChatColor.GREEN + " " + "use this command" + ChatColor.LIGHT_PURPLE + "/fcreate <Name>");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to create a large Biome World without Multiverse");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/lbCreate <Name>");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to create a normal World without Multiverse");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/nCreate <Name>");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to create a Island World without Multiverse");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/iCreate <Name>");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to create a Minecraft 1.1 World without Multiverse");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/v1Create <Name>");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to create a empty world without Multiverse");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/skyCreate <Name>");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.YELLOW + "**************" + ChatColor.GOLD + "MultiWorldSystem Spawn&Warp" + ChatColor.YELLOW + "************");
      sender.sendMessage(ChatColor.GREEN + "to set a Spawn");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/ssp");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "teleport to Spawn");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/sp");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to set a warppoint");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/sw <Name>");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "teleport to a warppoint");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/w <Name>");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "delete a warppoint");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/dw <Name>");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "to set a Home");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/sh");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + "teleport to a Home");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/home or /home <Playerame>");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + "teleport to Hub");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/Hub");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + "delete Hub");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/dhub");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      sender.sendMessage(ChatColor.GREEN + "open this menu");
      sender.sendMessage(ChatColor.GREEN + "use this command" + ChatColor.LIGHT_PURPLE + " " + "/mwsHelpEn");
      sender.sendMessage(ChatColor.YELLOW + "********************************************************");
      sender.sendMessage(ChatColor.GREEN + "I hope you have fun with this Plugin!");
      sender.sendMessage(ChatColor.GREEN + "Please inform me" + ChatColor.LIGHT_PURPLE + " " + "if you found a bug" + " " + "https://www.spigotmc.org/threads/multiworldsystem-spigot-1-8.297346/");
      sender.sendMessage(ChatColor.YELLOW + "*********************************************************");
      
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("fCreate"))
    {
      if (!sender.hasPermission("ewg.flat.create"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("flatcreatePermission") + " " + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return false;
      }
      if (args.length == 1)

      {
   	     sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("CreateWait"));    	  
      WorldCreator b = new WorldCreator(args[0]);
      b.type(WorldType.FLAT);
      this.world = b.createWorld();
      this.world.setSpawnFlags(true, true);
      this.world.setPVP(false);
      this.world.setStorm(false);
      this.world.setThundering(false);
      this.world.setKeepSpawnInMemory(false);
      this.world.setTicksPerAnimalSpawns(0);
      this.world.setTicksPerMonsterSpawns(0);
      this.world.setTicksPerAnimalSpawns(0);
      this.world.setTicksPerMonsterSpawns(0);
      this.world.setWaterAnimalSpawnLimit(0);
      this.world.setWeatherDuration(0);
      this.world.setAutoSave(true);
      this.world.setGameRuleValue("doFireTick", "false");
      this.world.setTime(6000L);
      b.generateStructures(false);
      b.generateStructures(false);getCommand("fCreate");
      World world = p.getWorld();
      Location loc = p.getLocation();
      world.setSpawnLocation(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ());
      this.settings.getData().set("warps." + args[0] + ".world", args[0]);
      this.settings.getData().set("warps." + args[0] + ".x", Double.valueOf(loc.getBlockX()));
      this.settings.getData().set("warps." + args[0] + ".y", Double.valueOf(loc.getBlockY()));
      this.settings.getData().set("warps." + args[0] + ".z", Double.valueOf(loc.getBlockZ()));
      this.settings.saveData();
     
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.BLUE + "World & folder" + " " + args[0] + " " + "create");
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("Warp create") + " " + ChatColor.GOLD + args[0]);
      return true;
    }
    if (args.length == 0)

    {
  	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("EWGErrorName"));
  	  
  	return false;
    }}
    if (cmd.getName().equalsIgnoreCase("lbCreate"))
    {
      if (!sender.hasPermission("ewg.lb.create"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("lbcreatePermissionn") + " " + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return true;
      }
      if (args.length == 1)

      {
  	     sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("CreateWait"));   	  
      WorldCreator b = new WorldCreator(args[0]);
      b.type(WorldType.LARGE_BIOMES);
      this.world = b.createWorld();
      this.world.setSpawnFlags(true, true);
      this.world.setPVP(isEnabled());
      this.world.setKeepSpawnInMemory(true);
      this.world.setTicksPerAnimalSpawns(5);
      this.world.setTicksPerMonsterSpawns(5);
      this.world.setWaterAnimalSpawnLimit(0);
      this.world.setWeatherDuration(2147483647);
      this.world.setAutoSave(true);
      this.world.setTime(6000L);
      this.world.setGameRuleValue("doDaylightCycle", "true");
      this.world.setDifficulty(Difficulty.NORMAL);
      this.world.setSpawnLocation(150, 0, 150);
      this.world.setTicksPerAnimalSpawns(1);
      this.world.setTicksPerMonsterSpawns(1);
      this.world.setGameRuleValue("doMobSpawning", "true");
      this.world.setGameRuleValue("mobGriefing", "true");
      this.world.setGameRuleValue("doFireTick", "true");
      this.world.setGameRuleValue("showDeathMessages", "true");
      b.generateStructures(true);
      b.generateStructures(true);getCommand("lbCreate");
      World world = p.getWorld();
      Location loc = p.getLocation();
      world.setSpawnLocation(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ());
      this.settings.getData().set("warps." + args[0] + ".world", args[0]);
      this.settings.getData().set("warps." + args[0] + ".x", Double.valueOf(loc.getBlockX()));
      this.settings.getData().set("warps." + args[0] + ".y", Double.valueOf(loc.getBlockY()));
      this.settings.getData().set("warps." + args[0] + ".z", Double.valueOf(loc.getBlockZ()));
      this.settings.saveData();      
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.BLUE + "World & folder" + " " + args[0] + " " + "create");
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("Warp create") + " " + ChatColor.GOLD + args[0]);
      return true;
    }
    if (args.length == 0)

    {
  	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("EWGErrorName"));
  	  
  	return false;
    }}
    if (cmd.getName().equalsIgnoreCase("nCreate"))
    {
      if (!sender.hasPermission("ewg.normal.create"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("normalcreatePermission") + " " + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return true;
      }
      if (args.length == 1)

      {
	     sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("CreateWait"));   	  
      WorldCreator b = new WorldCreator(args[0]);
      b.type(WorldType.NORMAL);
      b.generateStructures(true);
      b.generateStructures(true);getCommand("nCreate");
      this.world = b.createWorld();
      World world = p.getWorld();
      Location loc = p.getLocation();
      world.setSpawnLocation(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ());
      this.settings.getData().set("warps." + args[0] + ".world", args[0]);
      this.settings.getData().set("warps." + args[0] + ".x", Double.valueOf(loc.getBlockX()));
      this.settings.getData().set("warps." + args[0] + ".y", Double.valueOf(loc.getBlockY()));
      this.settings.getData().set("warps." + args[0] + ".z", Double.valueOf(loc.getBlockZ()));
      this.settings.saveData();
      
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.BLUE + "World & folder" + " " + args[0] + " " + "create");
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("Warp create") + " " + ChatColor.GOLD + args[0]);
      return true;
    }
    if (args.length == 0)

    {
  	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("EWGErrorName"));
  	  
  	return false;
    }}
    if (cmd.getName().equalsIgnoreCase("iCreate"))
    {
      if (!sender.hasPermission("ewg.amplified.create"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("ampcreatePermissionn") + " " + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return true;
      }
      if (args.length == 1)

      {
	     sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("CreateWait"));
      WorldCreator b = new WorldCreator(args[0]);
      b.type(WorldType.AMPLIFIED);
      b.generateStructures(true);
      b.generateStructures(true);getCommand("iCreate");
      this.world = b.createWorld();
      World world = p.getWorld();
      Location loc = p.getLocation();
      world.setSpawnLocation(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ());
      this.settings.getData().set("warps." + args[0] + ".world", args[0]);
      this.settings.getData().set("warps." + args[0] + ".x", Double.valueOf(loc.getBlockX()));
      this.settings.getData().set("warps." + args[0] + ".y", Double.valueOf(loc.getBlockY()));
      this.settings.getData().set("warps." + args[0] + ".z", Double.valueOf(loc.getBlockZ()));
      this.settings.saveData();
      
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.BLUE + "World & folder" + " " + args[0] + " " + "create");
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("Warp create") + " " + ChatColor.GOLD + args[0]);
      return true;
    }
  
  if (args.length == 0)

  {
	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("EWGErrorName"));
	  
	return false;
  }}
    Location loc;
    if (cmd.getName().equalsIgnoreCase("v1Create"))
    {
      if (!sender.hasPermission("ewg.v1.create"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("v1createPermission") + " " + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return true;
      }
      if (args.length == 1)
      {
    	     sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("CreateWait"));
      WorldCreator b = new WorldCreator(args[0]);
      b.type(WorldType.VERSION_1_1);
      b.generateStructures(true);
      b.generateStructures(true);getCommand("v1Create");
      this.world = b.createWorld();
      loc = p.getLocation();
      this.world.setSpawnLocation(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ());
      this.settings.getData().set("warps." + args[0] + ".world", args[0]);
      this.settings.getData().set("warps." + args[0] + ".x", Double.valueOf(loc.getBlockX()));
      this.settings.getData().set("warps." + args[0] + ".y", Double.valueOf(loc.getBlockY()));
      this.settings.getData().set("warps." + args[0] + ".z", Double.valueOf(loc.getBlockZ()));
      this.settings.saveData();
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv import " + args[0] + " " + getConfig().getString("mvEWGautoCreateType"));
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.BLUE + "World & folder" + " " + args[0] + " " + "create");
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("Warp create") + " " + ChatColor.GOLD + args[0]);
      return true;
    }   
    
    if (args.length == 0)
  
    {
  	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("EWGErrorName"));
  	  
  	return false;
    }}
    if (cmd.getName().equalsIgnoreCase("peng"))
    {
      if (!sender.hasPermission("ewg.kill.use"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("killPermission") + " " + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return false;
      }
      if (args.length == 1)
      {
        Player tot = Bukkit.getPlayer(args[0]);
        if ((tot != null) && (tot.isOnline()))
        {
          tot.setHealth(0.0D);
          tot.sendMessage(ChatColor.GOLD + "OP Kill von" + " " + ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " " + " aus der Ferne");
          p.sendMessage("Du hast " + ChatColor.RED + tot.getDisplayName() + " " + "getötet ");
          sender.sendMessage(ChatColor.GOLD + "Du hast" + " " + tot.getDisplayName() + " " + "getötet ");
          tot.sendMessage(ChatColor.GOLD + "OP Kill from" + " " + ChatColor.GOLD + p.getDisplayName() + " " + ChatColor.BLUE + " from far");
          p.sendMessage("you have " + ChatColor.RED + tot.getDisplayName() + " " + "killed ");
          sender.sendMessage(ChatColor.GOLD + "you has" + " " + tot.getDisplayName() + " " + "killed ");
          return true;
        }
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("Spawnset permission"));
      }
      return false;
    }
    if (cmd.getName().equalsIgnoreCase("skyCreate"))
    {
      if (!sender.hasPermission("ewg.sky.create"))
      {
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("skycreatePermission") + " " + getConfig().getString("permissionErrorText"));
        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
        return true;
      }
      if (args.length == 1)
      {
     sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("CreateWait"));
      WorldCreator b = new WorldCreator(args[0]);
      b.type(WorldType.FLAT).generatorSettings("3;minecraft:air;2").generateStructures(false);
      b.generateStructures(false);getCommand("skyCreate");       
      this.world = b.createWorld();
      loc = new Location(this.world, 0.0D, 64.0D, 0.0D);
      this.world = loc.getWorld();
      loc.setX(loc.getX() + 0);
      loc.setY(loc.getY() - 1);
      loc.setZ(loc.getZ() + 0);
      Block bl = this.world.getBlockAt(loc);
      bl.setType(Material.GLOWSTONE);      
      
      this.settings.getData().set("warps." + args[0] + ".world", args[0]);
      this.settings.getData().set("warps." + args[0] + ".x", Double.valueOf(loc.getBlockX()));
      this.settings.getData().set("warps." + args[0] + ".y", Double.valueOf(loc.getBlockY()));
      this.settings.getData().set("warps." + args[0] + ".z", Double.valueOf(loc.getBlockZ()));
      this.settings.saveData();
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("Warp create") + " " + ChatColor.GOLD + args[0]);
      
      return true;
    }
      if (args.length == 0)
    
      {
    	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("EWGErrorName"));
    	  
    	return false;
      }}
	 if (cmd.getName().equalsIgnoreCase("wlist")){					
		for(Player player : Bukkit.getOnlinePlayers())
		{ sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.BLUE + player.getWorld().getName() + " <<====>> " + ChatColor.GOLD + player.getName());
		 return true;}
		}
	    if (cmd.getName().equalsIgnoreCase("fs"))
	    {
	        if (!sender.hasPermission("ewg.fly.use"))
	        {
	          sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("fsPermission") + " " + getConfig().getString("permissionErrorText"));
	          p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
	          return false;
	        }
	        if (args.length == 1)
	        {
	      try
	      {
	        float speed = Float.parseFloat(args[0]) / 100.0F;
	        if (speed > 1.0F) {
	          speed = 1.0F;
	        }
	        if (speed < 0.0F) {
	          speed = 0.0F;
	        }
	        p.setFlySpeed(speed);
	        p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD + getConfig().getString("PlaySpeedSet1"));
	        }
	      catch (Exception e)
	      {
	        p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.RED + ChatColor.GOLD + getConfig().getString("FlyPlayerCmd1"));
	        
	        return true;
	      }
	      }
		}
	    if (cmd.getName().equalsIgnoreCase("sh"))
	    {
	    	
	      if (!sender.hasPermission("ewg.home.set"))
	      {
	    	 sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("homesetPermission") + " " + ChatColor.GREEN + getConfig().getString("permissionErrorText"));
	        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
	        return false;
	      }
	      if (args.length == 1)
	      {
	        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("homeNameError"));
	        return true;
	      }
	      this.settings.getData().set("home." + p.getName() , p.getLocation().getWorld().getName());
	      this.settings.getData().set("home.x" , Double.valueOf(p.getLocation().getX()));
	      this.settings.getData().set("home.y" , Double.valueOf(p.getLocation().getY()));
	      this.settings.getData().set("home.z" , Double.valueOf(p.getLocation().getZ()));
	      this.settings.saveData();	   
	      
	      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("homeNamegiven") + " " + ChatColor.GOLD + p.getName());
	    }
	    if (cmd.getName().equalsIgnoreCase("home"))
	    {
	      if (args.length == 0)
	      {

	      World w = Bukkit.getServer().getWorld(this.settings.getData().getString("home." + p.getName()));
          double x = leerWelt.this.settings.getData().getDouble("home.x");
          double y = leerWelt.this.settings.getData().getDouble("home.y");
          double z = leerWelt.this.settings.getData().getDouble("home.z");
	      p.teleport(new Location(w, x, y, z));
	      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + "You have been teleported to " + ChatColor.GOLD + p.getName() + "´s" + " " + ChatColor.GREEN + "home" + "!");
	      p.getWorld().playEffect(p.getLocation(), Effect.PORTAL, 50);
	      return true;
	    }
	    if (args.length == 1)
	      	if (!sender.hasPermission("ewg.hometo.use"))
		      {
		    	 sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("hometoPermission") + " " + ChatColor.GREEN + getConfig().getString("permissionErrorText"));
		        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
		        return false;}
	    {
	     World w = Bukkit.getServer().getWorld(this.settings.getData().getString("home." + args[0]));
        double x = leerWelt.this.settings.getData().getDouble("home.x");
        double y = leerWelt.this.settings.getData().getDouble("home.y");
        double z = leerWelt.this.settings.getData().getDouble("home.z");
	      p.teleport(new Location(w, x, y, z));
	      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + "You have been teleported to " + ChatColor.GOLD + p.getName() + "´s" + " " + ChatColor.GREEN + "home" + "!");
	      p.getWorld().playEffect(p.getLocation(), Effect.PORTAL, 50);
	      return true;}}
	    
	    if (cmd.getName().equalsIgnoreCase("dh"))
	    {
	    	 if (!sender.hasPermission("homedelPermission"))    		      
	    		      {
	    		        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("homedelPermission") + " " + ChatColor.GREEN + getConfig().getString("permissionErrorText"));
	    		        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
	    		        return false;
	    		      }    	 
	      if (args.length == 1)
	    	 
	      {
	        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.RED + getConfig().getString("HomeNameError"));
	        return false;
	      }
	      if (this.settings.getData().getConfigurationSection("home." + p.getName()) == null)
	      {
	        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + "home" + ChatColor.GOLD + p.getName() + ChatColor.GREEN + " noch nicht gesetzt!");
	        return false;
	      }
	      this.settings.getData().set("home." + p.getName(), null);
	      this.settings.saveData();
	      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("HomeDelfinsh ") + ChatColor.GOLD + p.getName() + ChatColor.GREEN + "!");
	      p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
	      return true;
	    }
	    if (cmd.getName().equalsIgnoreCase("sethub"))
	    {
	      if (!sender.hasPermission("ewg.hub.set"))
	      {
	    	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("sethubPermission") + " " + ChatColor.GREEN + getConfig().getString("permissionErrorText"));
	        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
	        return false;
	      }
	      this.settings.getData().set("hub.world", p.getLocation().getWorld().getName());
	      this.settings.getData().set("hub.x", Double.valueOf(p.getLocation().getX()));
	      this.settings.getData().set("hub.y", Double.valueOf(p.getLocation().getY()));
	      this.settings.getData().set("hub.z", Double.valueOf(p.getLocation().getZ()));
	      this.settings.saveData();
	      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("Hubset"));
	      return true;
	    }
	    if (cmd.getName().equalsIgnoreCase("hub"))	    	
	    {	    
		      if (args.length == 0)
		  
		      {	      
	            World w = Bukkit.getServer().getWorld(leerWelt.this.settings.getData().getString("hub.world"));
	            double x = leerWelt.this.settings.getData().getDouble("hub.x");
	            double y = leerWelt.this.settings.getData().getDouble("hub.y");
	            double z = leerWelt.this.settings.getData().getDouble("hub.z");
	            p.teleport(new Location(w, x, y, z));
	            p.sendMessage(ChatColor.GOLD.toString() + ChatColor.GOLD.toString() +  ChatColor.BOLD + " »" + ChatColor.GREEN + leerWelt.this.getConfig().getString("HubWelcome"));
	            p.getWorld().playEffect(p.getLocation(), Effect.PORTAL, 50);	              
	              return true ;	      
	        }
	    if (args.length == 1)
  		  
        {
      	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " »" + ChatColor.BLUE + getConfig().getString("EWGErrorName"));
      	  
      	return false;
        }
	    } 
	    if (cmd.getName().equalsIgnoreCase("discord"))
	    {
	      if (!sender.hasPermission("ewg.discord.use"))
	      {
	    	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("discordPermission") + " " + ChatColor.GREEN + getConfig().getString("permissionErrorText"));
	        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
	        return false;
	      }
	        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("DiscordText") + ChatColor.GREEN.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("DiscordChanel"));
	        p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
	        return true;}
   
  if (cmd.getName().equalsIgnoreCase("homepage"))
  {
    if (!sender.hasPermission("ewg.homepage.use"))
    {
  	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("homepagePermission") + " " + ChatColor.GREEN + getConfig().getString("permissionErrorText"));
      p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
      return false;
    }
      sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("homepageText") + ChatColor.GREEN.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("homepageURL"));
      p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
      return true;}
  
  if (cmd.getName().equalsIgnoreCase("heal"))  {
	    if (!sender.hasPermission("ewg.heal.use"))
	    {
	  	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("healPermission") + " " + ChatColor.GREEN + getConfig().getString("permissionErrorText"));
	      p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
	      return false;}
         if (args.length == 1) {
         
          for (Player playerToHeal : Bukkit.getServer().getOnlinePlayers()) {
             
              if (playerToHeal.getName().equalsIgnoreCase(args[0])) {
                 
                  playerToHeal.setHealth(20.0);
                  playerToHeal.setFoodLevel(20);
                  playerToHeal.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.BLUE + ChatColor.BOLD + " >" + ChatColor.GREEN.toString() + getConfig().getString("Healmsg") + ChatColor.GOLD + p.getName() + "!");
                  
                  break;        }}}
         
  if (args.length == 0) {
	  p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.BLUE + ChatColor.BOLD + " >" + ChatColor.GREEN.toString() + getConfig().getString("Healoffmsg") + ChatColor.GOLD + p.getName() + "!");
	  return true;
  }}    if (cmd.getName().equalsIgnoreCase("to")) {
	    if (!sender.hasPermission(getConfig().getString("tptoPermission")))
	    {
	  	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("tptoPermission") + " " + ChatColor.GREEN + getConfig().getString("permissionErrorText"));
	      p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
	      return false;}
    if (args.length == 0) {
            p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + " " + ChatColor.BLUE + getConfig().getString("tptoError"));
            return true;
    }
    Player target = Bukkit.getServer().getPlayer(args[0]);
    if (target == null) {
            p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + " " + ChatColor.BLUE + getConfig().getString("tptoError") + " " + args[0] + " " +"!");
            return true;
    }
    p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + " " + ChatColor.BLUE + getConfig().getString("tptoInfo") + args[0] + "!");
    p.teleport(target.getLocation());
    return true;
}
if (cmd.getName().equalsIgnoreCase("me")) {
	    if (!sender.hasPermission(getConfig().getString("tpmePermission")))
	    {
	  	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("tpmePermission") + " " + ChatColor.GREEN + getConfig().getString("permissionErrorText"));
	      p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
	      return false;}
    if (args.length == 0) {
            p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("tpmeError"));
            return true;
    }
    Player target = Bukkit.getServer().getPlayer(args[0]);
    if (target == null) {
            p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + " " + ChatColor.BLUE + getConfig().getString("tpmeError") + args[0] + "!");
           
            return true;
    }
    p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + " " + ChatColor.BLUE + getConfig().getString("tpmeInfo") + args[0] + "!");
    target.teleport(p.getLocation());
    return true;}
if (cmd.getName().equalsIgnoreCase("f")) {
	  if (!sender.hasPermission(getConfig().getString("flyPermission")))
	    {
	  	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("flyPermission") + " " + ChatColor.GREEN + getConfig().getString("permissionErrorText"));
	      p.getWorld().playEffect(p.getLocation(), Effect.GHAST_SHRIEK, 50);
	      return false;}
    if(args.length == 0){
        if (p.getAllowFlight() == (true)){
            p.setAllowFlight(false);
            p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("flyoffText"));
        } else {
              p.setAllowFlight(true);
              p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("flyonText"));
        }}}  

if (cmd.getName().equalsIgnoreCase("gi")) { // Give Player Items
    Player player = (Player) sender;

    if (!player.hasPermission(getConfig().getString("givePermission"))) {
  	  sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("givePermission") + " " + ChatColor.GREEN + getConfig().getString("permissionErrorText"));
        return true;
    } else {
        if (args.length == 2 || args.length == 3) {
        if (getServer().getPlayer(args[0]) != null) {
            Player target = getServer().getPlayer(args[0]);
            if (args.length == 3) {
                if (Material.matchMaterial(args[1]) != null) {
                Integer i = Integer.parseInt(args[2]);
                if (i > 64) {
                i = 64;
                }
                if (i < 1) {
                i = 1;
                }
                if (target.getInventory().addItem(new ItemStack(Material.matchMaterial(args[1]), i)) != null) {   
              	  player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("ItemGiven"));
                    return true;                 
            } else 
            {
                if (target.getInventory().addItem(new ItemStack(Material.matchMaterial(args[1]), 64)) != null) {
                
                } else {
                    
                    return false;               
          
            }}}} else {
          	  player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("itemAmount"));
                return false;
            }
    } else {
  	  player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("plOff"));
        }
        player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + getConfig().getString("SystemName") + ChatColor.GOLD.toString() + ChatColor.BOLD + " >" + ChatColor.BLUE + getConfig().getString("noitemName"));
        return false;
        
        }
        
        return true;
        }
}
  
  return false;}}

		
		  
 



