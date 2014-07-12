/*      */ package com.orange451.mcwarfare;
/*      */ 
/*      */ import com.orange451.PVPGun.Gun;
/*      */ import com.orange451.PVPGun.main;
/*      */ import com.orange451.mcwarfare.arena.KitArena;
/*      */ import com.orange451.mcwarfare.arena.KitArena.GameModeModifier;
/*      */ import com.orange451.mcwarfare.arena.KitGun;
/*      */ import com.orange451.mcwarfare.arena.KitPlayer;
/*      */ import com.orange451.mcwarfare.arena.KitProfile;
/*      */ import com.orange451.mcwarfare.arena.create.KitArenaCreator;
/*      */ import com.orange451.mcwarfare.arena.kits.KitClass;
/*      */ import com.orange451.mcwarfare.arena.kits.Perk;
/*      */ import com.orange451.mcwarfare.arena.kits.PerkJuggernaut;
/*      */ import com.orange451.mcwarfare.arena.kits.PerkMarathon;
/*      */ import com.orange451.mcwarfare.arena.kits.PerkScavenger;
/*      */ import com.orange451.mcwarfare.arena.kits.PerkSpeed;
/*      */ import com.orange451.mcwarfare.arena.kits.PerkStoppingPower;
/*      */ import com.orange451.mcwarfare.commands.PBaseCommand;
/*      */ import com.orange451.mcwarfare.commands.PCommandAddSpawn;
/*      */ import com.orange451.mcwarfare.commands.PCommandBuy;
/*      */ import com.orange451.mcwarfare.commands.PCommandChat;
/*      */ import com.orange451.mcwarfare.commands.PCommandCreate;
/*      */ import com.orange451.mcwarfare.commands.PCommandGui;
/*      */ import com.orange451.mcwarfare.commands.PCommandGun;
/*      */ import com.orange451.mcwarfare.commands.PCommandHelp;
/*      */ import com.orange451.mcwarfare.commands.PCommandJoin;
/*      */ import com.orange451.mcwarfare.commands.PCommandLeave;
/*      */ import com.orange451.mcwarfare.commands.PCommandList;
/*      */ import com.orange451.mcwarfare.commands.PCommandReload;
/*      */ import com.orange451.mcwarfare.commands.PCommandSetPoint;
/*      */ import com.orange451.mcwarfare.commands.PCommandStop;
/*      */ import com.orange451.mcwarfare.listeners.PluginBlockListener;
/*      */ import com.orange451.mcwarfare.listeners.PluginEntityListener;
/*      */ import com.orange451.mcwarfare.listeners.PluginPlayerListener;
/*      */ import com.orange451.mcwarfare.mysql.MCWarPlayerSave;
/*      */ import com.orange451.mcwarfare.permissions.PermissionInterface;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.TimeZone;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.ChatColor;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Server;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.block.Block;
/*      */ import org.bukkit.command.Command;
/*      */ import org.bukkit.command.CommandSender;
/*      */ import org.bukkit.command.ConsoleCommandSender;
/*      */ import org.bukkit.entity.LivingEntity;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.bukkit.plugin.java.JavaPlugin;
/*      */ import org.bukkit.potion.PotionEffect;
/*      */ import org.bukkit.scheduler.BukkitScheduler;
/*      */ 
/*      */ public class KitPvP extends JavaPlugin
/*      */ {
/*   62 */   private PluginBlockListener blockListener = new PluginBlockListener(this);
/*   63 */   private PluginPlayerListener playerListener = new PluginPlayerListener(this);
/*   64 */   private PluginEntityListener entityListener = new PluginEntityListener(this);
/*   65 */   public List<KitArena> activeArena = new ArrayList();
/*   66 */   public List<KitClass> classes = new ArrayList();
/*   67 */   public List<KitArenaCreator> creatingArena = new ArrayList();
/*   68 */   public ArrayList<KitGun> loadedGuns = new ArrayList();
/*   69 */   private List<PBaseCommand> commands = new ArrayList();
/*   70 */   private List<String> messages = new ArrayList();
/*      */   private int timer;
/*      */   private int playerTimer;
/*   73 */   private int announceTimer = 60;
/*      */   private int lastAnnouncement;
/*   75 */   private boolean loaded = false;
/*   76 */   public KitArena currentMap = null;
/*   77 */   public boolean stopped = false;
/*   78 */   public Random r = new Random();
/*      */   public MCWarPlayerSave lbsave;
/*   80 */   public int timerunning = 0;
/*      */ 
/*      */   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
/*      */   {
/*   84 */     List parameters = new ArrayList(Arrays.asList(args));
/*   85 */     Player player = null;
/*   86 */     boolean fromConsole = false;
/*   87 */     boolean candocommand = false;
/*   88 */     boolean ismod = false;
/*   89 */     String fullCMD = commandLabel;
/*   90 */     for (int i = 0; i < args.length; i++) {
/*   91 */       fullCMD = fullCMD + " " + args[i];
/*      */     }
/*   93 */     if ((sender instanceof ConsoleCommandSender)) {
/*   94 */       candocommand = true;
/*   95 */       ismod = true;
/*   96 */       fromConsole = true;
/*      */     }
/*   98 */     if ((sender instanceof Player)) {
/*   99 */       player = (Player)sender;
/*  100 */       if (PermissionInterface.hasPermission((Player)sender, "mcwar.admin")) {
/*  101 */         candocommand = true;
/*  102 */         ismod = true;
/*      */       }
/*  104 */       if (PermissionInterface.hasPermission((Player)sender, "mcwar.mod")) {
/*  105 */         ismod = true;
/*      */       }
/*      */     }
/*      */ 
/*  109 */     if (commandLabel.equals("spawn")) {
/*  110 */       if (player != null) {
/*  111 */         player.teleport(((World)getServer().getWorlds().get(0)).getSpawnLocation().clone().add(0.0D, 1.0D, 0.0D));
/*      */       }
/*  113 */       return false;
/*      */     }
/*      */ 
/*  116 */     if ((candocommand) || (ismod)) {
/*  117 */       if ((commandLabel.equals("profile")) && (candocommand)) {
/*      */         try {
/*  119 */           String name = args[0];
/*  120 */           KitProfile kp = readProfile(name);
/*  121 */           kp.dumpStats();
/*  122 */           kp.CLEAR();
/*      */         } catch (Exception e) {
/*  124 */           e.printStackTrace();
/*      */         }
/*  126 */         giveMessage(sender, "Incorrect use of command!");
/*  127 */         return false;
/*      */       }
/*  129 */       if ((commandLabel.equals("dprofile")) && (candocommand)) {
/*      */         try {
/*  131 */           String name = args[0];
/*  132 */           KitProfile kp = readProfile(name);
/*  133 */           kp.delete();
/*  134 */           kp.CLEAR();
/*      */         } catch (Exception e) {
/*  136 */           e.printStackTrace();
/*      */         }
/*  138 */         giveMessage(sender, "Incorrect use of command!");
/*  139 */         return false;
/*      */       }
/*  141 */       if ((commandLabel.equals("opex")) && (candocommand))
/*      */         try {
/*  143 */           if (args[0].equals("user")) {
/*  144 */             logDonation(fullCMD);
/*  145 */             if (args[2].equals("add")) {
/*  146 */               PermissionInterface.addPermission(args[1], args[3]);
/*  147 */               giveMessage(sender, "Permission added!");
/*  148 */               return true;
/*      */             }
/*  150 */             if (args[2].equals("remove")) {
/*  151 */               PermissionInterface.removePermission(args[1], args[3]);
/*  152 */               giveMessage(sender, "Permission removed!");
/*  153 */               return true;
/*      */             }
/*      */           }
/*      */         } catch (Exception e) {
/*  157 */           e.printStackTrace();
/*      */ 
/*  159 */           giveMessage(sender, "Incorrect use of command!");
/*  160 */           return false;
/*      */         }
/*  162 */       if ((commandLabel.equals("mod")) && (candocommand)) {
/*      */         try {
/*  164 */           Player tomod = Util.MatchPlayer(args[0]);
/*  165 */           if (tomod != null)
/*  166 */             if (PermissionInterface.hasPermission(tomod, "mcwar.mod")) {
/*  167 */               PermissionInterface.removePermission(args[0], "mcwar.mod");
/*      */               try {
/*  169 */                 getKitPlayer(tomod).sayMessage(null, "NO LONGER MOD");
/*  170 */                 giveMessage(sender, "NO LONGER MOD");
/*      */               } catch (Exception localException1) {
/*      */               }
/*      */             }
/*      */             else {
/*  175 */               PermissionInterface.addPermission(args[0], "mcwar.mod");
/*      */               try {
/*  177 */                 getKitPlayer(tomod).sayMessage(null, "You have been modded");
/*  178 */                 giveMessage(sender, "modded: " + tomod.getName());
/*      */               }
/*      */               catch (Exception localException2) {
/*      */               }
/*      */             }
/*      */         }
/*      */         catch (Exception e) {
/*  185 */           e.printStackTrace();
/*      */         }
/*  187 */         giveMessage(sender, "Incorrect use of command!");
/*  188 */         return false;
/*      */       }
/*  190 */       if (commandLabel.equals("kick"))
/*      */         try {
/*  192 */           Player tomod = Util.MatchPlayer(args[0]);
/*  193 */           if (tomod != null) {
/*  194 */             tomod.kickPlayer("kicked by moderator/admin (" + getPlayerName(sender) + ")");
/*  195 */             return true;
/*      */           }
/*      */         } catch (Exception e) {
/*  198 */           e.printStackTrace();
/*      */ 
/*  200 */           giveMessage(sender, "Incorrect use of command!");
/*  201 */           return false;
/*      */         }
/*  203 */       if ((commandLabel.equals("ban")) && (candocommand)) {
/*      */         try {
/*  205 */           Player tomod = Util.MatchPlayer(args[0]);
/*  206 */           if (tomod != null) {
/*  207 */             banPlayer(tomod, "banned by admin");
/*  208 */             return true;
/*      */           }
/*  210 */           banPlayer(args[0], "banned by admin");
/*  211 */           return true;
/*      */         }
/*      */         catch (Exception e) {
/*  214 */           e.printStackTrace();
/*      */ 
/*  216 */           giveMessage(sender, "Incorrect use of command!");
/*  217 */           return false;
/*      */         }
/*      */       }
/*  220 */       if ((commandLabel.equals("unban")) && (candocommand)) {
/*      */         try {
/*  222 */           logDonation(fullCMD);
/*  223 */           unbanPlayer(args[0]);
/*      */         } catch (Exception e) {
/*  225 */           e.printStackTrace();
/*      */         }
/*  227 */         giveMessage(sender, "Incorrect use of command!");
/*  228 */         return false;
/*      */       }
/*      */ 
/*  231 */       if (((commandLabel.equals("banip")) || (commandLabel.equals("eban")) || (commandLabel.equals("ban-ip"))) && (candocommand)) {
/*  232 */         giveMessage(sender, "NOT ALLOWED!");
/*  233 */         return false;
/*      */       }
/*      */ 
/*  236 */       if ((commandLabel.equals("givexp")) && (candocommand)) {
/*  237 */         if (args.length == 2) {
/*  238 */           String pl = args[0];
/*  239 */           String amt = args[1];
/*      */           try {
/*  241 */             if (fromConsole) {
/*  242 */               KitProfile kp = readProfile(pl);
/*  243 */               if (kp != null) {
/*  244 */                 logDonation(fullCMD);
/*  245 */                 kp.xp += Integer.parseInt(amt);
/*  246 */                 kp.save(pl);
/*  247 */                 kp.CLEAR();
/*      */               } else {
/*  249 */                 logDonation(fullCMD + "[ERROR] NO PROFILE");
/*      */               }
/*      */             } else {
/*  252 */               Player p = Util.MatchPlayer(pl);
/*  253 */               if (p.isOnline()) {
/*  254 */                 int xp = Integer.parseInt(amt);
/*  255 */                 KitPlayer kp = getKitPlayer(p);
/*  256 */                 if (kp == null) {
/*  257 */                   kp = joinArena(p, null);
/*      */                 }
/*  259 */                 if (kp != null) {
/*  260 */                   kp.giveXp(xp);
/*  261 */                   kp.sayMessage(null, ChatColor.DARK_RED + "You have been given the xp amount: " + ChatColor.BOLD + ChatColor.RED + amt);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */           catch (Exception localException3)
/*      */           {
/*      */           }
/*      */         }
/*      */ 
/*  271 */         return false;
/*      */       }
/*      */ 
/*  274 */       if ((commandLabel.equals("setkills")) && (candocommand)) {
/*  275 */         if (args.length == 2) {
/*  276 */           String pl = args[0];
/*  277 */           String amt = args[1];
/*      */           try {
/*  279 */             if (fromConsole) {
/*  280 */               KitProfile kp = readProfile(pl);
/*  281 */               if (kp != null) {
/*  282 */                 kp.kills = Integer.parseInt(amt);
/*  283 */                 kp.save(pl);
/*  284 */                 kp.CLEAR();
/*      */               }
/*      */             } else {
/*  287 */               Player p = Util.MatchPlayer(pl);
/*  288 */               if (p.isOnline()) {
/*  289 */                 int xp = Integer.parseInt(amt);
/*  290 */                 KitPlayer kp = getKitPlayer(p);
/*  291 */                 if (kp != null) {
/*  292 */                   kp.profile.kills = xp;
/*  293 */                   kp.profile.save(kp);
/*  294 */                   kp.sayMessage(null, ChatColor.DARK_RED + "Your kills are now: " + ChatColor.BOLD + ChatColor.RED + amt);
/*      */                 }
/*      */               }
/*      */             }
/*      */           } catch (Exception localException4) {  }
/*      */ 
/*      */         }
/*  300 */         return false;
/*      */       }
/*      */ 
/*  303 */       if ((commandLabel.equals("setdeaths")) && (candocommand)) {
/*  304 */         if (args.length == 2) {
/*  305 */           String pl = args[0];
/*  306 */           String amt = args[1];
/*      */           try {
/*  308 */             if (fromConsole) {
/*  309 */               KitProfile kp = readProfile(pl);
/*  310 */               if (kp != null) {
/*  311 */                 kp.deaths = Integer.parseInt(amt);
/*  312 */                 kp.save(pl);
/*  313 */                 kp.CLEAR();
/*      */               }
/*      */             } else {
/*  316 */               Player p = Util.MatchPlayer(pl);
/*  317 */               if (p.isOnline()) {
/*  318 */                 int xp = Integer.parseInt(amt);
/*  319 */                 KitPlayer kp = getKitPlayer(p);
/*  320 */                 if (kp != null) {
/*  321 */                   kp.profile.deaths = xp;
/*  322 */                   kp.profile.save(kp);
/*  323 */                   kp.sayMessage(null, ChatColor.DARK_RED + "Your deaths are now: " + ChatColor.BOLD + ChatColor.RED + amt);
/*      */                 }
/*      */               }
/*      */             }
/*      */           } catch (Exception localException5) {  }
/*      */ 
/*      */         }
/*  329 */         return false;
/*      */       }
/*      */ 
/*  332 */       if ((commandLabel.equals("day")) && (candocommand)) {
/*  333 */         ((World)getServer().getWorlds().get(0)).setTime(0L);
/*  334 */         return false;
/*      */       }
/*      */ 
/*  337 */       if ((commandLabel.equals("weather")) && (candocommand)) {
/*  338 */         ((World)getServer().getWorlds().get(0)).setStorm(false);
/*  339 */         return false;
/*      */       }
/*      */ 
/*  342 */       if ((commandLabel.equals("setspawn")) && (candocommand)) {
/*  343 */         if (player != null) {
/*  344 */           ((World)getServer().getWorlds().get(0)).setSpawnLocation(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
/*      */         }
/*  346 */         return false;
/*      */       }
/*      */ 
/*  349 */       if ((commandLabel.equals("givegun")) && (candocommand)) {
/*  350 */         if (args.length == 2) {
/*  351 */           String pl = args[0];
/*  352 */           String gun = args[1];
/*      */           try {
/*  354 */             if (fromConsole) {
/*  355 */               KitProfile kp = readProfile(pl);
/*  356 */               if (kp != null) {
/*  357 */                 logDonation(fullCMD);
/*  358 */                 kp.boughtGuns.add(gun);
/*  359 */                 kp.save(pl);
/*  360 */                 kp.CLEAR();
/*      */               } else {
/*  362 */                 logDonation(fullCMD + "[ERROR] NO PROFILE");
/*      */               }
/*      */             } else {
/*  365 */               Player p = Util.MatchPlayer(pl);
/*  366 */               if ((p != null) && (p.isOnline())) {
/*  367 */                 KitPlayer kp = getKitPlayer(p);
/*  368 */                 if (kp == null) {
/*  369 */                   kp = joinArena(p, null);
/*      */                 }
/*  371 */                 if (kp != null) {
/*  372 */                   kp.profile.boughtGuns.add(gun);
/*  373 */                   kp.sayMessage(null, ChatColor.DARK_RED + "You have been given the gun: " + ChatColor.BOLD + ChatColor.RED + gun);
/*      */                 }
/*      */               }
/*  376 */               else if (player != null) {
/*  377 */                 KitProfile kp = loadProfile(pl);
/*  378 */                 kp.boughtGuns.add(gun);
/*  379 */                 kp.save(pl);
/*  380 */                 kp.CLEAR();
/*      */               }
/*      */             }
/*      */           }
/*      */           catch (Exception localException6)
/*      */           {
/*      */           }
/*      */         }
/*  388 */         return false;
/*      */       }
/*      */ 
/*  391 */       if ((commandLabel.equals("giveperk")) && (candocommand)) {
/*  392 */         if (args.length == 2) {
/*  393 */           String pl = args[0];
/*  394 */           String gun = args[1];
/*      */           try {
/*  396 */             if (fromConsole) {
/*  397 */               KitProfile kp = readProfile(pl);
/*  398 */               if (kp != null) {
/*  399 */                 logDonation(fullCMD);
/*  400 */                 kp.perks.add(gun);
/*  401 */                 kp.save(pl);
/*  402 */                 kp.CLEAR();
/*      */               } else {
/*  404 */                 logDonation(fullCMD + "[ERROR] NO PROFILE");
/*      */               }
/*      */             } else {
/*  407 */               Player p = Util.MatchPlayer(pl);
/*  408 */               if (p.isOnline()) {
/*  409 */                 KitPlayer kp = getKitPlayer(p);
/*  410 */                 if (kp == null) {
/*  411 */                   kp = joinArena(p, null);
/*      */                 }
/*  413 */                 if (kp != null) {
/*  414 */                   kp.profile.perks.add(gun);
/*  415 */                   kp.sayMessage(null, ChatColor.DARK_RED + "You have been given the perk: " + ChatColor.BOLD + ChatColor.RED + gun);
/*  416 */                   kp.profile.save(kp);
/*      */                 }
/*      */               }
/*      */             }
/*      */           } catch (Exception e) {
/*  421 */             e.printStackTrace();
/*      */           }
/*      */         }
/*  424 */         return false;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  429 */     handleCommand(sender, parameters);
/*  430 */     return true;
/*      */   }
/*      */ 
/*      */   private void logDonation(String string) {
/*  434 */     ArrayList g = new ArrayList();
/*  435 */     String path = getMcWar() + "/donation_log.txt";
/*  436 */     BufferedReader in = FileIO.file_text_open_read(path);
/*      */     try {
/*  438 */       String strLine = null;
/*  439 */       while ((strLine = FileIO.file_text_read_line(in)) != null) {
/*  440 */         g.add(strLine);
/*      */       }
/*  442 */       FileIO.file_text_close(in);
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*  447 */     BufferedWriter out = FileIO.file_text_open_write(path);
/*      */     try {
/*  449 */       for (int i = 0; i < g.size(); i++) {
/*  450 */         FileIO.file_text_write_line(out, (String)g.get(i));
/*      */       }
/*  452 */       FileIO.file_text_write_line(out, string);
/*      */     }
/*      */     catch (Exception localException1) {
/*      */     }
/*  456 */     FileIO.file_text_close(out);
/*      */   }
/*      */ 
/*      */   private void banPlayer(Player tomod, String string) {
/*  460 */     banPlayer(tomod.getName(), string);
/*  461 */     tomod.kickPlayer(string);
/*      */   }
/*      */ 
/*      */   private void banPlayer(String player, String string) {
/*  465 */     String path = getMcWar() + "/banned-players.txt";
/*  466 */     BufferedReader in = FileIO.file_text_open_read(path);
/*  467 */     String strLine = null;
/*  468 */     ArrayList banned = new ArrayList();
/*  469 */     while ((strLine = FileIO.file_text_read_line(in)) != null) {
/*  470 */       banned.add(strLine);
/*      */     }
/*  472 */     FileIO.file_text_close(in);
/*  473 */     BufferedWriter out = FileIO.file_text_open_write(path);
/*  474 */     for (int i = 0; i < banned.size(); i++) {
/*  475 */       FileIO.file_text_write_line(out, (String)banned.get(i));
/*      */     }
/*  477 */     FileIO.file_text_write_line(out, player);
/*  478 */     FileIO.file_text_close(out);
/*  479 */     banned.clear();
/*      */   }
/*      */ 
/*      */   private void unbanPlayer(String player) {
/*  483 */     String path = getMcWar() + "/banned-players.txt";
/*  484 */     BufferedReader in = FileIO.file_text_open_read(path);
/*  485 */     String strLine = null;
/*  486 */     ArrayList banned = new ArrayList();
/*  487 */     while ((strLine = FileIO.file_text_read_line(in)) != null) {
/*  488 */       banned.add(strLine);
/*      */     }
/*  490 */     FileIO.file_text_close(in);
/*      */ 
/*  492 */     boolean found = false;
/*  493 */     for (int i = banned.size() - 1; i >= 0; i--) {
/*  494 */       String s = (String)banned.get(i);
/*  495 */       if (s.toLowerCase().equals(player.toLowerCase())) {
/*  496 */         banned.remove(i);
/*  497 */         found = true;
/*      */       }
/*      */     }
/*  500 */     if (found) {
/*  501 */       System.out.println("[UNBANNED PLAYER] " + player);
/*      */     }
/*  503 */     BufferedWriter out = FileIO.file_text_open_write(path);
/*  504 */     for (int i = 0; i < banned.size(); i++) {
/*  505 */       FileIO.file_text_write_line(out, (String)banned.get(i));
/*      */     }
/*  507 */     FileIO.file_text_close(out);
/*      */   }
/*      */ 
/*      */   public void handleCommand(CommandSender sender, List<String> parameters) {
/*  511 */     if (parameters.size() == 0) {
/*  512 */       ((PBaseCommand)this.commands.get(0)).execute(sender, parameters);
/*  513 */       return;
/*      */     }
/*      */ 
/*  516 */     String commandName = ((String)parameters.get(0)).toLowerCase();
/*      */ 
/*  518 */     for (PBaseCommand fcommand : this.commands) {
/*  519 */       if (fcommand.getAliases().contains(commandName)) {
/*  520 */         fcommand.execute(sender, parameters);
/*  521 */         return;
/*      */       }
/*      */     }
/*      */ 
/*  525 */     sender.sendMessage(ChatColor.YELLOW + "Unknown MCWarfare command \"" + commandName + "\". Try /war help");
/*      */   }
/*      */ 
/*      */   public List<PBaseCommand> getCommands() {
/*  529 */     return this.commands;
/*      */   }
/*      */ 
/*      */   public File getRoot() {
/*  533 */     return getDataFolder();
/*      */   }
/*      */ 
/*      */   public static String getMcWar() {
/*  537 */     return getFTP();
/*      */   }
/*      */ 
/*      */   public String getUsers()
/*      */   {
/*  542 */     return getFTP() + "/users";
/*      */   }
/*      */ 
/*      */   public static String getFTP()
/*      */   {
/*  547 */     if (OSValidator.isWindows()) {
/*  548 */       File f = new File("I:\\MCWarfare");
/*  549 */       if (f.exists()) {
/*  550 */         return "I:\\MCWarfare";
/*      */       }
/*  552 */       return "/root/mc/shared/MCWarfare";
/*      */     }
/*  554 */     return "/Shared/MCWarfare";
/*      */   }
/*      */ 
/*      */   public void makeDirectories() {
/*  558 */     new File(getMcWar()).mkdirs();
/*  559 */     new File(getUsers()).mkdir();
/*  560 */     new File(getMcWar() + "/voted").mkdirs();
/*      */   }
/*      */ 
/*      */   public void onEnable()
/*      */   {
/*  565 */     System.out.println("KitPvP Enabled");
/*      */ 
/*  567 */     File dir = getDataFolder();
/*  568 */     if (!dir.exists()) {
/*  569 */       dir.mkdir();
/*      */     }
/*      */ 
/*  572 */     File dir2 = new File(getDataFolder().getAbsolutePath() + "/arenas");
/*  573 */     if (!dir2.exists()) {
/*  574 */       dir2.mkdir();
/*      */     }
/*      */ 
/*  577 */     Util.Initialize(this);
/*  578 */     PermissionInterface.Initialize();
/*      */ 
/*  581 */     this.commands.add(new PCommandHelp(this));
/*      */ 
/*  583 */     this.commands.add(new PCommandList(this));
/*  584 */     this.commands.add(new PCommandGun(this));
/*  585 */     this.commands.add(new PCommandBuy(this));
/*  586 */     this.commands.add(new PCommandJoin(this));
/*  587 */     this.commands.add(new PCommandGui(this));
/*  588 */     this.commands.add(new PCommandChat(this));
/*  589 */     this.commands.add(new PCommandLeave(this));
/*      */ 
/*  591 */     this.commands.add(new PCommandCreate(this));
/*  592 */     this.commands.add(new PCommandReload(this));
/*  593 */     this.commands.add(new PCommandSetPoint(this));
/*  594 */     this.commands.add(new PCommandStop(this));
/*  595 */     this.commands.add(new PCommandAddSpawn(this));
/*      */ 
/*  598 */     if (!this.loaded)
/*      */     {
/*  600 */       PluginManager pm = getServer().getPluginManager();
/*  601 */       pm.registerEvents(this.entityListener, this);
/*  602 */       pm.registerEvents(this.blockListener, this);
/*  603 */       pm.registerEvents(this.playerListener, this);
/*      */     }
/*      */ 
/*  607 */     loadArenas();
/*  608 */     loadClasses();
/*  609 */     loadWeapons();
/*      */ 
/*  611 */     this.messages.add("Visit: " + ChatColor.RED + "http://bit.ly/PhLYYK" + ChatColor.GREEN + " To vote for grenades!");
/*  612 */     this.messages.add("Niick makes great maps! " + ChatColor.RED + "http://bit.ly/Tncw5I");
/*  613 */     this.messages.add("Want armor? Type " + ChatColor.RED + "/war buy");
/*  614 */     this.messages.add("Found a nasty bug? report it " + ChatColor.RED + "http://bit.ly/SLtPNG");
/*  615 */     this.messages.add("MCWar global leaderboards: " + ChatColor.RED + "http://bit.ly/Rcpsii");
/*      */ 
/*  618 */     this.timer = getServer().getScheduler().scheduleSyncRepeatingTask(this, new ArenaUpdater(), 20L, 20L);
/*  619 */     this.playerTimer = getServer().getScheduler().scheduleSyncRepeatingTask(this, new ArenaUpdaterPlayer(), 20L, 1L);
/*      */ 
/*  621 */     this.loaded = true;
/*  622 */     this.stopped = false;
/*      */ 
/*  624 */     makeDirectories();
/*      */ 
/*  626 */     this.lbsave = new MCWarPlayerSave(this);
/*      */ 
/*  629 */     getServer().getScheduler().scheduleSyncDelayedTask(this, new PlayerJoiner(), 80L);
/*      */ 
/*  632 */     String path = getRoot().getAbsolutePath() + "/lastVote";
/*  633 */     File f = new File(path);
/*  634 */     if (!f.exists()) {
/*  635 */       Date date = new Date();
/*  636 */       Calendar calendar = GregorianCalendar.getInstance();
/*  637 */       calendar.setTimeZone(TimeZone.getTimeZone("EST"));
/*  638 */       calendar.setTime(date);
/*  639 */       int i = calendar.get(5);
/*  640 */       BufferedWriter wr = FileIO.file_text_open_write(path);
/*  641 */       FileIO.file_text_write_line(wr, Integer.toString(i));
/*  642 */       FileIO.file_text_close(wr);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void onDisable()
/*      */   {
/*  648 */     System.out.println("KitPvP Disabled");
/*  649 */     for (int i = this.activeArena.size() - 1; i >= 0; i--) {
/*      */       try {
/*  651 */         ((KitArena)this.activeArena.get(i)).stop();
/*      */       } catch (Exception e) {
/*  653 */         e.printStackTrace();
/*      */       }
/*      */     }
/*  656 */     clearMemory();
/*      */   }
/*      */ 
/*      */   public void clearMemory() {
/*  660 */     this.commands.clear();
/*  661 */     this.creatingArena.clear();
/*  662 */     this.classes.clear();
/*  663 */     this.activeArena.clear();
/*  664 */     this.loadedGuns.clear();
/*  665 */     this.lbsave = null;
/*  666 */     this.messages.clear();
/*  667 */     this.timerunning = 0;
/*  668 */     getServer().getScheduler().cancelTask(this.playerTimer);
/*  669 */     getServer().getScheduler().cancelTask(this.timer);
/*      */   }
/*      */ 
/*      */   public void loadArena(String arenaName) {
/*  673 */     String path = getRoot().getAbsolutePath() + "/arenas";
/*  674 */     File f = new File(path + "/" + arenaName);
/*  675 */     if (f.exists()) {
/*  676 */       KitArena ka = new KitArena(this, arenaName);
/*  677 */       this.activeArena.add(ka);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void loadArenas() {
/*  682 */     String path = getRoot().getAbsolutePath() + "/arenas";
/*  683 */     File dir = new File(path);
/*  684 */     String[] children = dir.list();
/*  685 */     if (children != null)
/*  686 */       for (int i = 0; i < children.length; i++) {
/*  687 */         String filename = children[i];
/*  688 */         KitArena ka = new KitArena(this, filename);
/*  689 */         this.activeArena.add(ka);
/*      */       }
/*      */   }
/*      */ 
/*      */   public void loadClasses()
/*      */   {
/*  695 */     String path = getRoot().getAbsolutePath() + "/classes";
/*  696 */     File dir = new File(path);
/*  697 */     String[] children = dir.list();
/*  698 */     if (children != null)
/*  699 */       for (int i = 0; i < children.length; i++) {
/*  700 */         String filename = children[i];
/*  701 */         KitClass kc = new KitClass(this, new File(path + "/" + filename));
/*  702 */         kc.name = filename;
/*  703 */         this.classes.add(kc);
/*      */       }
/*      */   }
/*      */ 
/*      */   public void loadWeapons()
/*      */   {
/*  709 */     String path = getMcWar() + "/buyable/weapons";
/*  710 */     BufferedReader out = FileIO.file_text_open_read(path);
/*  711 */     boolean reading = true;
/*  712 */     boolean isReadingGun = false;
/*  713 */     KitGun gun = null;
/*  714 */     int line = 0;
/*  715 */     while ((reading) && (line < 512)) {
/*  716 */       line++;
/*  717 */       String read = FileIO.file_text_read_line(out);
/*  718 */       if (read == null) {
/*  719 */         reading = false;
/*  720 */         return;
/*      */       }
/*  722 */       if (read.equalsIgnoreCase("::defgun")) {
/*  723 */         isReadingGun = true;
/*  724 */         gun = new KitGun();
/*      */       }
/*  726 */       if (read.equalsIgnoreCase("::endgun")) {
/*  727 */         isReadingGun = false;
/*  728 */         this.loadedGuns.add(gun);
/*      */       }
/*      */ 
/*  731 */       if (isReadingGun) {
/*  732 */         if (read.contains("name")) {
/*  733 */           gun.name = read.substring(read.indexOf("=") + 1);
/*      */         }
/*  735 */         if (read.contains("desc"))
/*  736 */           gun.desc = read.substring(read.indexOf("=") + 1);
/*  737 */         if (read.contains("cost"))
/*  738 */           gun.cost = Integer.parseInt(read.substring(read.indexOf("=") + 1));
/*  739 */         if (read.contains("level"))
/*  740 */           gun.level = Integer.parseInt(read.substring(read.indexOf("=") + 1));
/*  741 */         if (read.contains("type"))
/*  742 */           gun.type = Integer.parseInt(read.substring(read.indexOf("=") + 1));
/*  743 */         if (read.contains("slot")) {
/*  744 */           gun.slot = read.substring(read.indexOf("=") + 1);
/*      */         }
/*      */       }
/*      */     }
/*  748 */     FileIO.file_text_close(out);
/*      */   }
/*      */ 
/*      */   public KitArenaCreator getKitArenaCreator(Player p) {
/*  752 */     for (int i = this.creatingArena.size() - 1; i >= 0; i--) {
/*  753 */       if (((KitArenaCreator)this.creatingArena.get(i)).player.getName().equals(p.getName())) {
/*  754 */         return (KitArenaCreator)this.creatingArena.get(i);
/*      */       }
/*      */     }
/*  757 */     return null;
/*      */   }
/*      */ 
/*      */   public boolean isInArena(Location location) {
/*  761 */     boolean ret = false;
/*  762 */     for (int i = 0; i < this.activeArena.size(); i++) {
/*  763 */       if (((KitArena)this.activeArena.get(i)).field.isInside(location)) {
/*  764 */         ret = true;
/*      */       }
/*      */     }
/*  767 */     return ret;
/*      */   }
/*      */ 
/*      */   public boolean isInArena(LivingEntity attacker) {
/*  771 */     return getKitPlayer((Player)attacker) != null;
/*      */   }
/*      */ 
/*      */   public boolean isInArena(Block block) {
/*  775 */     return isInArena(block.getLocation());
/*      */   }
/*      */ 
/*      */   public void onQuit(Player pl) {
/*  779 */     KitPlayer kp = getKitPlayer(pl);
/*  780 */     if (kp != null)
/*  781 */       leaveArena(kp);
/*      */   }
/*      */ 
/*      */   public void clearPlayer(Player pl)
/*      */   {
/*  786 */     if (pl == null)
/*  787 */       return;
/*  788 */     InventoryHelper.clearInventory(pl.getInventory());
/*  789 */     for (PotionEffect effect : pl.getActivePotionEffects())
/*  790 */       pl.removePotionEffect(effect.getType());
/*      */   }
/*      */ 
/*      */   public void onJoin(Player pl) {
/*  794 */     if (isInArena(pl.getLocation()))
/*  795 */       clearPlayer(pl);
/*      */   }
/*      */ 
/*      */   public KitPlayer getKitPlayer(Player player)
/*      */   {
/*  800 */     for (int i = this.activeArena.size() - 1; i >= 0; i--) {
/*  801 */       KitPlayer find = ((KitArena)this.activeArena.get(i)).getPlayer(player);
/*  802 */       if (find != null) {
/*  803 */         return find;
/*      */       }
/*      */     }
/*  806 */     return null;
/*      */   }
/*      */ 
/*      */   public ArrayList<KitPlayer> getKitPlayers() {
/*  810 */     ArrayList players = new ArrayList();
/*  811 */     for (int i = this.activeArena.size() - 1; i >= 0; i--) {
/*  812 */       ArrayList myplayers = ((KitArena)this.activeArena.get(i)).getPlayers();
/*  813 */       for (int ii = 0; ii < myplayers.size(); ii++) {
/*  814 */         players.add((KitPlayer)myplayers.get(ii));
/*      */       }
/*      */     }
/*  817 */     return players;
/*      */   }
/*      */ 
/*      */   public KitClass getArenaClass(String line1) {
/*  821 */     for (int i = 0; i < this.classes.size(); i++) {
/*  822 */       if (((KitClass)this.classes.get(i)).name.equals(line1)) {
/*  823 */         return (KitClass)this.classes.get(i);
/*      */       }
/*      */     }
/*  826 */     return null;
/*      */   }
/*      */ 
/*      */   public KitGun getGun(String name)
/*      */   {
/*  880 */     for (int i = 0; i < this.loadedGuns.size(); i++) {
/*  881 */       if (((KitGun)this.loadedGuns.get(i)).name.equals(name)) {
/*  882 */         return (KitGun)this.loadedGuns.get(i);
/*      */       }
/*      */     }
/*  885 */     return null;
/*      */   }
/*      */ 
/*      */   public KitGun getGunNameFromItem(int id) {
/*  889 */     for (int i = 0; i < this.loadedGuns.size(); i++) {
/*  890 */       if (((KitGun)this.loadedGuns.get(i)).type == id) {
/*  891 */         return (KitGun)this.loadedGuns.get(i);
/*      */       }
/*      */     }
/*  894 */     return null;
/*      */   }
/*      */ 
/*      */   public KitArena getKitArena(String arenaName) {
/*  898 */     for (int i = this.activeArena.size() - 1; i >= 0; i--) {
/*  899 */       if (((KitArena)this.activeArena.get(i)).name.toLowerCase().equals(arenaName.toLowerCase())) {
/*  900 */         return (KitArena)this.activeArena.get(i);
/*      */       }
/*      */     }
/*  903 */     return null;
/*      */   }
/*      */ 
/*      */   public KitClass getKitClass(String cls) {
/*  907 */     for (int i = 0; i < this.classes.size(); i++) {
/*  908 */       if (((KitClass)this.classes.get(i)).name.equals(cls)) {
/*  909 */         return (KitClass)this.classes.get(i);
/*      */       }
/*      */     }
/*  912 */     return null;
/*      */   }
/*      */ 
/*      */   public void leaveArena(KitPlayer kp) {
/*  916 */     kp.profile.save(kp);
/*  917 */     for (int i = 0; i < this.activeArena.size(); i++) {
/*  918 */       KitArena arena = (KitArena)this.activeArena.get(i);
/*  919 */       arena.leaveArena(kp);
/*      */     }
/*  921 */     kp.CLEARPLAYER();
/*      */   }
/*      */ 
/*      */   public KitPlayer joinArena(Player player, KitClass kclass) {
/*  925 */     if (this.stopped) {
/*  926 */       return null;
/*      */     }
/*  928 */     InventoryHelper.clearInventory(player.getInventory());
/*  929 */     KitArena k = getFirstKitArena("lobby");
/*  930 */     if (k != null) {
/*  931 */       if (!k.isFull()) {
/*  932 */         return k.join(player);
/*      */       }
/*  934 */       player.sendMessage("FULL " + k.players.size());
/*  935 */       return null;
/*      */     }
/*      */ 
/*  938 */     player.sendMessage("Could not join a kit arena, perhaps there is no free space?");
/*  939 */     return null;
/*      */   }
/*      */ 
/*      */   public ChatColor getPlayerTagColor(Player player) {
/*  943 */     if (PermissionInterface.hasPermission(player, "mcwar.admin"))
/*  944 */       return ChatColor.GOLD;
/*  945 */     if (PermissionInterface.hasPermission(player, "mcwar.mod"))
/*  946 */       return ChatColor.LIGHT_PURPLE;
/*  947 */     if (PermissionInterface.hasPermission(player, "mcwar.mvp"))
/*  948 */       return ChatColor.AQUA;
/*  949 */     if (PermissionInterface.hasPermission(player, "mcwar.vip"))
/*  950 */       return ChatColor.GREEN;
/*  951 */     KitPlayer kp = getKitPlayer(player);
/*  952 */     if ((kp != null) && 
/*  953 */       (kp.profile.level > 30)) {
/*  954 */       return ChatColor.YELLOW;
/*      */     }
/*      */ 
/*  957 */     return ChatColor.WHITE;
/*      */   }
/*      */ 
/*      */   public void sayMessage(Player player, String say) {
/*      */     try {
/*  962 */       String msg = "<" + getKitPlayerName(player) + "> " + say;
/*  963 */       broadcastMessage(player, msg);
/*      */     } catch (Exception localException) {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void sayNonWarMessage(Player player, String message) {
/*      */     try {
/*  970 */       String msg = "<" + player.getDisplayName() + "> " + message;
/*  971 */       List players = Util.Who();
/*  972 */       for (int i = players.size() - 1; i >= 0; i--) {
/*  973 */         KitPlayer kp = getKitPlayer((Player)players.get(i));
/*  974 */         if (kp == null)
/*  975 */           ((Player)players.get(i)).sendMessage(msg);
/*      */       }
/*      */     }
/*      */     catch (Exception localException) {
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getKitPlayerName(Player player) {
/*  983 */     return getPlayerTagColor(player) + getKitPlayer(player).getTag() + ChatColor.WHITE;
/*      */   }
/*      */ 
/*      */   public void broadcastMessage(Player from, String msg) {
/*      */     try {
/*  988 */       for (int i = 0; i < this.activeArena.size(); i++)
/*  989 */         for (int ii = ((KitArena)this.activeArena.get(i)).players.size() - 1; ii >= 0; ii--) {
/*  990 */           ((KitPlayer)((KitArena)this.activeArena.get(i)).players.get(ii)).sayMessage(from, msg);
/*  991 */           ((KitPlayer)((KitArena)this.activeArena.get(i)).players.get(ii)).setChat();
/*      */         }
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void giveMessage(Player player, String msg)
/*      */   {
/* 1002 */     KitPlayer kp = getKitPlayer(player);
/* 1003 */     if (kp != null)
/* 1004 */       kp.sayMessage(player, msg);
/*      */     else
/* 1006 */       player.sendMessage(msg);
/*      */   }
/*      */ 
/*      */   public void giveMessage(CommandSender sender, String msg)
/*      */   {
/* 1011 */     if ((sender instanceof Player))
/* 1012 */       giveMessage((Player)sender, msg);
/*      */   }
/*      */ 
/*      */   private String getPlayerName(CommandSender sender)
/*      */   {
/* 1018 */     if ((sender instanceof Player)) {
/* 1019 */       return ((Player)sender).getName();
/*      */     }
/* 1021 */     return "Console";
/*      */   }
/*      */ 
/*      */   public KitProfile getProfile(KitPlayer kitPlayer) {
/* 1025 */     String path = getUsers() + "/" + kitPlayer.name + ".mcw";
/* 1026 */     File f = new File(path);
/* 1027 */     KitProfile k = null;
/* 1028 */     if (f.exists()) {
/* 1029 */       k = readProfile(kitPlayer);
/*      */     } else {
/* 1031 */       blankProfile(kitPlayer.name);
/* 1032 */       k = readProfile(kitPlayer);
/*      */     }
/* 1034 */     if (k != null) {
/* 1035 */       return k;
/*      */     }
/* 1037 */     return null;
/*      */   }
/*      */ 
/*      */   public KitProfile loadProfile(String str) {
/* 1041 */     String path = getUsers() + "/" + str + ".mcw";
/* 1042 */     File f = new File(path);
/* 1043 */     KitProfile k = null;
/* 1044 */     if (f.exists()) {
/* 1045 */       k = readProfile(str);
/*      */     }
/* 1047 */     if (k != null) {
/* 1048 */       return k;
/*      */     }
/* 1050 */     return null;
/*      */   }
/*      */ 
/*      */   private KitProfile readProfile(String player) {
/* 1054 */     String path = getUsers() + "/" + player + ".mcw";
/* 1055 */     File f = new File(path);
/* 1056 */     if (f.exists()) {
/* 1057 */       BufferedReader in = FileIO.file_text_open_read(path);
/* 1058 */       ArrayList compute = new ArrayList();
/*      */       try {
/* 1060 */         boolean can = true;
/* 1061 */         int line = 0;
/*      */         do {
/* 1063 */           line++;
/* 1064 */           String str = FileIO.file_text_read_line(in);
/* 1065 */           if (str == null)
/* 1066 */             can = false;
/*      */           else
/* 1068 */             compute.add(str);
/* 1062 */           if (!can) break;  } while (line < 512);
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/*      */ 
/* 1074 */       FileIO.file_text_close(in);
/* 1075 */       KitProfile kp = new KitProfile(this, player, compute);
/* 1076 */       kp.execute(this);
/* 1077 */       return kp;
/*      */     }
/* 1079 */     KitProfile take2 = blankProfile(player);
/* 1080 */     return take2;
/*      */   }
/*      */ 
/*      */   private KitProfile readProfile(KitPlayer p) {
/* 1084 */     String path = getUsers() + "/" + p.name + ".mcw";
/* 1085 */     File f = new File(path);
/* 1086 */     if (f.exists()) {
/* 1087 */       BufferedReader in = FileIO.file_text_open_read(path);
/* 1088 */       ArrayList compute = new ArrayList();
/* 1089 */       boolean can = true;
/* 1090 */       int line = 0;
/*      */       try {
/*      */         do {
/* 1093 */           line++;
/* 1094 */           String str = FileIO.file_text_read_line(in);
/* 1095 */           if (str == null)
/* 1096 */             can = false;
/*      */           else
/* 1098 */             compute.add(str);
/* 1092 */           if (!can) break;  } while (line < 512);
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/*      */ 
/* 1104 */       FileIO.file_text_close(in);
/* 1105 */       KitProfile kp = new KitProfile(this, p.player, compute);
/* 1106 */       kp.execute(this);
/* 1107 */       return kp;
/*      */     }
/* 1109 */     p.player.kickPlayer("Couldn't create user profile, please contact server Admins!");
/*      */ 
/* 1111 */     return null;
/*      */   }
/*      */ 
/*      */   private KitProfile blankProfile(String p) {
/* 1115 */     String path = getUsers() + "/" + p + ".mcw";
/* 1116 */     BufferedWriter out = FileIO.file_text_open_write(path);
/*      */     try {
/* 1118 */       FileIO.file_text_write_line(out, "xp=0");
/* 1119 */       FileIO.file_text_write_line(out, "level=1");
/* 1120 */       FileIO.file_text_write_line(out, "credits=0");
/* 1121 */       FileIO.file_text_write_line(out, "--classes");
/*      */       try {
/* 1123 */         ArrayList dump = ((KitClass)this.classes.get(0)).dumpClass();
/* 1124 */         for (int i = 0; i < dump.size(); i++)
/* 1125 */           FileIO.file_text_write_line(out, (String)dump.get(i));
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/* 1130 */       FileIO.file_text_write_line(out, "::defguns");
/* 1131 */       FileIO.file_text_write_line(out, "m16");
/* 1132 */       FileIO.file_text_write_line(out, "usp45");
/* 1133 */       FileIO.file_text_write_line(out, "m1014");
/* 1134 */       FileIO.file_text_write_line(out, "l118a");
/* 1135 */       FileIO.file_text_write_line(out, "::endguns");
/*      */     }
/*      */     catch (Exception localException1) {
/*      */     }
/* 1139 */     FileIO.file_text_close(out);
/* 1140 */     return readProfile(p);
/*      */   }
/*      */ 
/*      */   public int getGunAmmo(String str)
/*      */   {
/* 1145 */     Plugin p = Bukkit.getPluginManager().getPlugin("PVPGun");
/* 1146 */     if (p != null) {
/* 1147 */       main pv = (main)p;
/* 1148 */       if (pv != null) {
/* 1149 */         Gun g = pv.getGun(str);
/* 1150 */         return g.ammoMat;
/*      */       }
/*      */     }
/* 1153 */     return 0;
/*      */   }
/*      */ 
/*      */   public KitArena getRandomKitArena(KitArena excluding, KitArena last, int amtPlayers)
/*      */   {
/* 1158 */     KitArena ret = last;
/* 1159 */     int rep = 0;
/* 1160 */     int max = 128;
/* 1161 */     boolean loop = true;
/* 1162 */     if (last != null)
/* 1163 */       while (loop) {
/* 1164 */         if (rep > max)
/* 1165 */           loop = false;
/* 1166 */         rep++;
/* 1167 */         if ((!ret.equals(excluding)) && 
/* 1168 */           (!ret.equals(last)) && 
/* 1169 */           ((!ret.gameModifier.equals(last.gameModifier)) || (!ret.type.equals(last.type))) && 
/* 1170 */           (amtPlayers <= ret.maxPlayers) && (amtPlayers >= ret.minPlayers)) {
/* 1171 */           return ret;
/*      */         }
/*      */ 
/* 1176 */         int i = this.activeArena.size();
/* 1177 */         ret = (KitArena)this.activeArena.get(this.r.nextInt(i));
/*      */       }
/*      */     else {
/* 1180 */       ret = getRandomKitArena(excluding);
/*      */     }
/* 1182 */     if ((!loop) || (ret == last)) {
/* 1183 */       ret = getRandomKitArena(excluding);
/*      */     }
/* 1185 */     return ret;
/*      */   }
/*      */ 
/*      */   public KitArena getRandomKitArena(KitArena excluding)
/*      */   {
/* 1190 */     KitArena ret = excluding;
/* 1191 */     int rep = 0;
/* 1192 */     int max = 128;
/* 1193 */     boolean loop = true;
/* 1194 */     while ((ret.equals(excluding)) && (loop)) {
/* 1195 */       if (rep >= max)
/* 1196 */         loop = false;
/* 1197 */       int i = this.activeArena.size();
/* 1198 */       ret = (KitArena)this.activeArena.get(this.r.nextInt(i));
/* 1199 */       rep++;
/*      */     }
/* 1201 */     if (!loop) {
/* 1202 */       ret = getRandomKitArena();
/*      */     }
/* 1204 */     return ret;
/*      */   }
/*      */ 
/*      */   public KitArena getRandomKitArena() {
/* 1208 */     int i = this.activeArena.size();
/* 1209 */     return (KitArena)this.activeArena.get(this.r.nextInt(i));
/*      */   }
/*      */ 
/*      */   public KitArena getFirstKitArena(String string) {
/* 1213 */     for (int i = 0; i < this.activeArena.size(); i++) {
/* 1214 */       if (((KitArena)this.activeArena.get(i)).type.equals(string)) {
/* 1215 */         return (KitArena)this.activeArena.get(i);
/*      */       }
/*      */     }
/* 1218 */     return null;
/*      */   }
/*      */ 
/*      */   public int getAmmo(Player player, String string) {
/* 1222 */     int amt = 56;
/* 1223 */     if (PermissionInterface.hasPermission(player, "mcwar.vip")) {
/* 1224 */       amt = 96;
/*      */     }
/* 1226 */     if (PermissionInterface.hasPermission(player, "mcwar.mvp")) {
/* 1227 */       amt = 128;
/*      */     }
/* 1229 */     if (string.equals("primary"))
/* 1230 */       return amt;
/* 1231 */     return amt / 2;
/*      */   }
/*      */ 
/*      */   public int getKillXP(Player player)
/*      */   {
/* 1236 */     int ret = 25;
/* 1237 */     if (PermissionInterface.hasPermission(player, "mcwar.doublexp")) {
/* 1238 */       ret += 25;
/*      */     }
/* 1240 */     if (PermissionInterface.hasPermission(player, "mcwar.vip")) {
/* 1241 */       ret += 10;
/*      */     }
/* 1243 */     if (PermissionInterface.hasPermission(player, "mcwar.mvp")) {
/* 1244 */       ret += 20;
/*      */     }
/* 1246 */     return ret;
/*      */   }
/*      */ 
/*      */   public int getKillCredits(Player player) {
/* 1250 */     if (PermissionInterface.hasPermission(player, "mcwar.mvp")) {
/* 1251 */       return 3;
/*      */     }
/* 1253 */     if (PermissionInterface.hasPermission(player, "mcwar.vip")) {
/* 1254 */       return 2;
/*      */     }
/* 1256 */     return 1;
/*      */   }
/*      */ 
/*      */   public void stopMakingArena(Player player) {
/* 1260 */     for (int i = this.creatingArena.size() - 1; i >= 0; i--)
/* 1261 */       if (((KitArenaCreator)this.creatingArena.get(i)).player.getName().equals(player.getName()))
/* 1262 */         this.creatingArena.remove(i);
/*      */   }
/*      */ 
/*      */   public void setPerk(Player player, String line2)
/*      */   {
/* 1268 */     KitPlayer kp = getKitPlayer(player);
/* 1269 */     if (kp != null) {
/* 1270 */       boolean has = false;
/* 1271 */       for (int i = 0; i < kp.profile.perks.size(); i++) {
/* 1272 */         if (((String)kp.profile.perks.get(i)).equals(line2)) {
/* 1273 */           kp.profile.perk = line2;
/* 1274 */           has = true;
/* 1275 */           giveMessage(player, "Perk set to: " + ChatColor.YELLOW + line2);
/*      */         }
/*      */       }
/* 1278 */       if (!has)
/* 1279 */         giveMessage(player, "You do not have this class!");
/*      */     }
/*      */   }
/*      */ 
/*      */   public ArrayList<Perk> getPerks(KitPlayer k)
/*      */   {
/* 1285 */     ArrayList perks = new ArrayList();
/* 1286 */     for (int i = 0; i < k.profile.perks.size(); i++) {
/* 1287 */       Perk p = getPerk(k.player, (String)k.profile.perks.get(i));
/* 1288 */       if (p != null) {
/* 1289 */         perks.add(p);
/*      */       }
/*      */     }
/* 1292 */     return perks;
/*      */   }
/*      */ 
/*      */   public ArrayList<Perk> getActivePerks(KitPlayer k) {
/* 1296 */     ArrayList ret = new ArrayList();
/* 1297 */     ArrayList perks = getPerks(k);
/* 1298 */     for (int i = 0; i < perks.size(); i++) {
/* 1299 */       if (((Perk)perks.get(i)).name.toLowerCase().equals(k.profile.perk.toLowerCase())) {
/* 1300 */         ret.add((Perk)perks.get(i));
/*      */       }
/*      */     }
/* 1303 */     return ret;
/*      */   }
/*      */ 
/*      */   public Perk getPerk(Player p, String s) {
/* 1307 */     if (s.toLowerCase().equals("marathon"))
/* 1308 */       return new PerkMarathon(p);
/* 1309 */     if (s.toLowerCase().equals("scavenger"))
/* 1310 */       return new PerkScavenger(p);
/* 1311 */     if (s.toLowerCase().equals("speed"))
/* 1312 */       return new PerkSpeed(p);
/* 1313 */     if (s.toLowerCase().equals("stoppingpower"))
/* 1314 */       return new PerkStoppingPower(p);
/* 1315 */     if (s.toLowerCase().equals("juggernaut"))
/* 1316 */       return new PerkJuggernaut(p);
/* 1317 */     return null;
/*      */   }
/*      */ 
/*      */   public void newDay() {
/* 1321 */     String path = getFTP() + "/voted";
/* 1322 */     File dir = new File(path);
/* 1323 */     String[] children = dir.list();
/* 1324 */     if (children != null)
/* 1325 */       for (int i = 0; i < children.length; i++) {
/* 1326 */         String filename = children[i];
/* 1327 */         File f = new File(path + "/" + filename);
/* 1328 */         if (f.exists())
/* 1329 */           f.delete();
/*      */       }
/*      */   }
/*      */ 
/*      */   public boolean hasVoted(Player player)
/*      */   {
/* 1336 */     String path = getFTP() + "/voted/" + player.getName();
/* 1337 */     File f = new File(path);
/* 1338 */     return f.exists();
/*      */   }
/*      */ 
/*      */   public boolean canDamagePlayer(Player attacker, Player defender) {
/* 1342 */     if ((!isInArena(attacker)) && (isInArena(defender))) {
/* 1343 */       return false;
/*      */     }
/*      */ 
/* 1346 */     if ((isInArena(attacker)) && (isInArena(defender))) {
/* 1347 */       boolean isOnTeam = false;
/* 1348 */       KitPlayer shootKP = getKitPlayer(attacker);
/* 1349 */       KitPlayer defendKP = getKitPlayer(defender);
/* 1350 */       KitArena ka = shootKP.arena;
/*      */ 
/* 1352 */       if (ka.killType.equals("ffa")) {
/* 1353 */         isOnTeam = false;
/*      */       }
/* 1355 */       else if (shootKP.team == defendKP.team) {
/* 1356 */         isOnTeam = true;
/*      */       }
/*      */ 
/* 1360 */       if (ka.type.equals("lobby")) {
/* 1361 */         isOnTeam = true;
/*      */       }
/*      */ 
/* 1364 */       return !isOnTeam;
/*      */     }
/* 1366 */     return false;
/*      */   }
/*      */ 
/*      */   public void onStartLobby()
/*      */   {
/* 1371 */     if (this.timerunning > 8)
/* 1372 */       this.lbsave.saveStats = true;
/*      */   }
/*      */ 
/*      */   public int getServerNumber()
/*      */   {
/* 1377 */     int servernum = 1;
/*      */     try {
/* 1379 */       String motd = Bukkit.getServer().getMotd();
/* 1380 */       boolean has = motd.contains("#");
/* 1381 */       if (has) {
/* 1382 */         int ind = motd.indexOf("#");
/* 1383 */         String t = motd.substring(ind + 1, ind + 3);
/* 1384 */         if (t.contains(" "))
/* 1385 */           t = t.replaceAll(" ", "");
/* 1386 */         servernum = Integer.parseInt(t);
/*      */       }
/*      */     } catch (Exception localException) {
/*      */     }
/* 1390 */     return servernum;
/*      */   }
/*      */ 
/*      */   public class ArenaUpdater
/*      */     implements Runnable
/*      */   {
/*      */     public ArenaUpdater()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void run()
/*      */     {
/*  831 */       if (KitPvP.this.currentMap == null)
/*  832 */         return;
/*  833 */       KitPvP.this.announceTimer -= 1;
/*  834 */       KitPvP.this.timerunning += 1;
/*  835 */       for (int i = KitPvP.this.activeArena.size() - 1; i >= 0; i--) {
/*  836 */         ((KitArena)KitPvP.this.activeArena.get(i)).tick();
/*      */       }
/*  838 */       if (KitPvP.this.announceTimer < 0) {
/*  839 */         if (KitPvP.this.messages.size() == 0)
/*  840 */           return;
/*  841 */         KitPvP.this.broadcastMessage(null, ChatColor.AQUA + "[BROADCAST]");
/*  842 */         KitPvP.this.broadcastMessage(null, ChatColor.GREEN + " " + (String)KitPvP.this.messages.get(KitPvP.this.lastAnnouncement));
/*  843 */         KitPvP.this.announceTimer = 20;
/*  844 */         KitPvP.this.lastAnnouncement += 1;
/*  845 */         if (KitPvP.this.lastAnnouncement + 1 > KitPvP.this.messages.size())
/*  846 */           KitPvP.this.lastAnnouncement = 0;
/*      */       }
/*  848 */       Player[] g = Bukkit.getServer().getOnlinePlayers();
/*  849 */       for (int i = 0; i < g.length; i++) {
/*  850 */         KitPlayer kp = KitPvP.this.getKitPlayer(g[i]);
/*  851 */         if (kp == null)
/*  852 */           InventoryHelper.clearInventory(g[i].getInventory()); 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public class ArenaUpdaterPlayer implements Runnable { public ArenaUpdaterPlayer() {  }
/*      */ 
/*      */ 
/*  859 */     public void run() { for (int i = KitPvP.this.activeArena.size() - 1; i >= 0; i--)
/*  860 */         ((KitArena)KitPvP.this.activeArena.get(i)).playerTick(); } }
/*      */ 
/*      */   public class PlayerJoiner implements Runnable {
/*      */     public PlayerJoiner() {
/*      */     }
/*      */ 
/*      */     public void run() {
/*  867 */       for (int i = 0; i < KitPvP.this.activeArena.size(); i++) {
/*  868 */         if (((KitArena)KitPvP.this.activeArena.get(i)).type.equals("lobby")) {
/*  869 */           ((KitArena)KitPvP.this.activeArena.get(i)).onStart();
/*      */         }
/*      */       }
/*  872 */       Player[] g = Bukkit.getServer().getOnlinePlayers();
/*  873 */       for (int i = 0; i < g.length; i++)
/*  874 */         KitPvP.this.joinArena(g[i], null);
/*      */     }
/*      */   }
/*      */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.KitPvP
 * JD-Core Version:    0.6.2
 */