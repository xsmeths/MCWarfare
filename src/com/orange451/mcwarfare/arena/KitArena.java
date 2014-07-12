/*      */ package com.orange451.mcwarfare.arena;
/*      */ 
/*      */ import com.orange451.PVPGun.Util;
/*      */ import com.orange451.PVPGun.main;
/*      */ import com.orange451.mcwarfare.Field;
/*      */ import com.orange451.mcwarfare.FileIO;
/*      */ import com.orange451.mcwarfare.InventoryHelper;
/*      */ import com.orange451.mcwarfare.KitPvP;
/*      */ import com.orange451.mcwarfare.arena.kits.KitClass;
/*      */ import com.orange451.mcwarfare.arena.kits.Perk;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.DataInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.TimeZone;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.ChatColor;
/*      */ import org.bukkit.Effect;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.Server;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.entity.Item;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.inventory.PlayerInventory;
/*      */ import org.bukkit.material.MaterialData;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.bukkit.scheduler.BukkitScheduler;
/*      */ import org.kitteh.tag.TagAPI;
/*      */ 
/*      */ public class KitArena
/*      */ {
/*   38 */   public boolean stopped = false;
/*   39 */   public boolean started = false;
/*      */   public boolean busy;
/*   41 */   public boolean allowTeamkill = false;
/*   42 */   public boolean test = true;
/*   43 */   public int maxPlayers = 60;
/*   44 */   public int minPlayers = -1;
/*   45 */   public int repeat = 0;
/*   46 */   public int timer = 60;
/*   47 */   public int announcemaptimer = 0;
/*      */   public int timermax;
/*   49 */   public int bluekills = 0;
/*   50 */   public int redkills = 0;
/*   51 */   public int redscore = 0;
/*   52 */   public int bluescore = 0;
/*   53 */   public int maxscore = 75;
/*      */   public int timeSinceStart;
/*   55 */   public Random r = new Random();
/*      */   public KitPvP plugin;
/*   58 */   public String type = "lobby";
/*   59 */   public String killType = "tdm";
/*      */   public String name;
/*      */   public String message;
/*      */   public Location spawnpoint;
/*      */   public Location spawnpoint2;
/*      */   public KitArena last;
/*      */   public KitArena tomap;
/*      */   public Field field;
/*   67 */   public GameModeModifier gameModifier = GameModeModifier.NONE;
/*   68 */   public ArrayList<KitSpawn> spawns = new ArrayList();
/*   69 */   public ArrayList<KitFlagStand> flags = new ArrayList();
/*   70 */   public ArrayList<KitPlayer> players = new ArrayList();
/*   71 */   public int mytimer = -1;
/*      */ 
/*   73 */   public ArrayList<String> gunList = new ArrayList();
/*      */ 
/*      */   public KitArena(KitPvP plugin, String arenaName) {
/*   76 */     this.plugin = plugin;
/*   77 */     this.name = arenaName;
/*   78 */     this.field = new Field();
/*      */ 
/*   80 */     loadArena();
/*      */ 
/*   82 */     this.timermax = 240;
/*   83 */     if (this.type.equals("lobby")) {
/*   84 */       this.started = true;
/*   85 */       plugin.currentMap = this;
/*   86 */       this.message = (ChatColor.GRAY + "To view your guns type" + ChatColor.YELLOW + " /war list");
/*   87 */       this.timermax = 70;
/*      */     }
/*      */ 
/*   90 */     if (this.type.equals("ffa")) {
/*   91 */       this.killType = "ffa";
/*   92 */       this.message = (ChatColor.AQUA + "" + ChatColor.BOLD + "Free For All! First person to 20 kills wins!");
/*   93 */       if (this.gameModifier.equals(GameModeModifier.ONEINCHAMBER))
/*   94 */         this.message = (ChatColor.AQUA + "" + ChatColor.BOLD + "OneIntheChamber! three lives! DONT DIE");
/*   95 */       if (this.gameModifier.equals(GameModeModifier.GUNGAME))
/*   96 */         this.message = (ChatColor.AQUA + "" + ChatColor.BOLD + "GunGame! each kill ranks up your gun");
/*      */     }
/*   98 */     if (this.type.equals("tdm")) {
/*   99 */       this.message = (ChatColor.AQUA + "" + ChatColor.BOLD +"Team Deathmatch!");
/*  100 */       if (this.gameModifier.equals(GameModeModifier.CTF))
/*  101 */         this.message = (ChatColor.AQUA + "" + ChatColor.BOLD + "CTF! Capture the other teams flag!");
/*  102 */       if (this.gameModifier.equals(GameModeModifier.INFECT)) {
/*  103 */         this.message = (ChatColor.AQUA + "" + ChatColor.BOLD + "Infected! Kill the other team!");
/*      */       }
/*      */     }
/*  106 */     this.gunList.clear();
/*  107 */     this.gunList.add("usp45");
/*  108 */     this.gunList.add("m9");
/*  109 */     this.gunList.add("magnum");
/*  110 */     this.gunList.add("deserteagle");
/*  111 */     this.gunList.add("m16");
/*  112 */     this.gunList.add("m4a1");
/*  113 */     this.gunList.add("ak47");
/*  114 */     this.gunList.add("l118a");
/*  115 */     this.gunList.add("dragunov");
/*  116 */     this.gunList.add("barret50c");
/*  117 */     this.gunList.add("m1014");
/*  118 */     this.gunList.add("spas12");
/*  119 */     this.gunList.add("aa12");
/*  120 */     this.gunList.add("msr");
/*  121 */     this.gunList.add("moddel1887");
/*  122 */     this.gunList.add("famas");
/*  123 */     this.gunList.add("tomahawk");
/*      */   }
/*      */ 
/*      */   public void tick() {
/*      */     try {
/*  128 */       if (this.timer > this.timermax) {
/*  129 */         this.timer = this.timermax;
/*      */       }
/*  131 */       if (this.type.equals("lobby"))
/*      */       {
/*  133 */         this.announcemaptimer -= 1;
/*  134 */         if (this.announcemaptimer <= 0) {
/*  135 */           this.announcemaptimer = 20;
/*  136 */           announceMap();
/*      */         }
/*      */       }
/*  139 */       if (!this.stopped) {
/*  140 */         for (int i = this.players.size() - 1; i >= 0; i--)
/*      */           try
/*      */           {
/*  143 */             if (((KitPlayer)this.players.get(i)).player != null) {
/*  144 */               if (this.type.equals("lobby")) {
/*  145 */                 InventoryHelper.clearInventory(((KitPlayer)this.players.get(i)).player.getInventory());
/*      */               }
/*  147 */               if (!this.field.isInside(((KitPlayer)this.players.get(i)).player.getLocation())) {
/*  148 */                 ((KitPlayer)this.players.get(i)).player.teleport(getSpawnLocation((KitPlayer)this.players.get(i)));
/*      */               }
/*      */             }
/*  151 */             ((KitPlayer)this.players.get(i)).tick();
/*      */ 
/*  153 */             String name = ((KitPlayer)this.players.get(i)).player.getName();
/*  154 */             if (name.length() + 2 >= 16)
/*  155 */               name = name.substring(0, 14);
/*      */             try {
/*  157 */               ((KitPlayer)this.players.get(i)).player.setPlayerListName(((KitPlayer)this.players.get(i)).getTeamColor() + name);
/*      */             }
/*      */             catch (Exception localException1)
/*      */             {
/*      */             }
/*      */           }
/*      */           catch (Exception localException2)
/*      */           {
/*      */           }
/*  166 */         if (((this.players.size() > 0) || (this.started)) && (this.plugin.currentMap.equals(this)))
/*      */         {
/*  169 */           if (!this.busy) {
/*  170 */             this.timer -= 1;
/*  171 */             this.timeSinceStart += 1;
/*      */           }
/*      */ 
/*  174 */           checkScores();
/*  175 */           checkTime();
/*  176 */           onTick();
/*      */ 
/*  178 */           for (int i = 0; i < this.spawns.size(); i++) {
/*  179 */             ((KitSpawn)this.spawns.get(i)).tick();
/*      */           }
/*      */         }
/*  182 */         if ((this.players.size() == 0) && (this.started))
/*  183 */           endGame("no players!");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  187 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void playerTick() {
/*  192 */     if (this.gameModifier.equals(GameModeModifier.INFECT))
/*  193 */       for (int i = 0; i < this.players.size(); i++) {
/*  194 */         KitPlayer kp = (KitPlayer)this.players.get(i);
/*  195 */         if ((kp.player != null) && (kp.player.isOnline()))
/*  196 */           kp.checkInventory();
/*      */       }
/*      */   }
/*      */ 
/*      */   public void checkTime()
/*      */   {
/*  211 */     if (((this.timer <= 10) || (this.timer == 30) || (this.timer == 20) || (this.timer == 45) || (this.timer == 60) || (this.timer == 120) || (this.timer == 300)) && (this.timer > -1)) {
/*  212 */       this.plugin.broadcastMessage(null, Integer.toString(this.timer) + ChatColor.GRAY + " seconds!");
/*  213 */       if (this.timer <= 10) {
/*  214 */         for (int i = 0; i < this.players.size(); i++) {
/*  215 */           if (this.players.get(i) != null)
/*      */             try {
/*  217 */               Util.playEffect(Effect.CLICK1, ((KitPlayer)this.players.get(i)).player.getLocation(), 0);
/*      */             }
/*      */             catch (Exception localException1)
/*      */             {
/*      */             }
/*      */         }
/*      */       }
/*      */     }
/*  225 */     if (this.timer < 0)
/*  226 */       if (this.type.equals("lobby")) {
/*  227 */         if (this.players.size() > 1) {
/*  228 */           if (this.tomap != null)
/*      */             try {
/*  230 */               this.last = this.tomap;
/*  231 */               this.tomap.started = true;
/*  232 */               mergePlayers(this.tomap, ChatColor.BOLD + "" + ChatColor.RED + "Start!  map: " + this.tomap.name, false);
/*  233 */               this.busy = true;
/*  234 */               this.started = false;
/*  235 */               this.plugin.currentMap = this.tomap;
/*  236 */               this.tomap.onStart();
/*  237 */               this.tomap.killPlayers();
/*      */             } catch (Exception e) {
/*  239 */               broadcastMessage("Error Starting Arena! Restarting Lobby!");
/*  240 */               this.timer = this.timermax;
/*  241 */               System.out.println("ERROR STARTING ARENA");
/*      */             }
/*      */         }
/*      */         else {
/*  245 */           broadcastMessage("NOT ENOUGH PEOPLE! RESTARTING!");
/*  246 */           this.timer = this.timermax;
/*      */         }
/*      */       }
/*      */       else
/*  250 */         endGame("");
/*      */   }
/*      */ 
/*      */   public void onStart()
/*      */   {
/*  256 */     this.redkills = 0;
/*  257 */     this.bluekills = 0;
/*  258 */     this.redscore = 0;
/*  259 */     this.bluescore = 0;
/*  260 */     this.timeSinceStart = 0;
/*      */     try {
/*  262 */       this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), "weather");
/*  263 */       this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), "day");
/*      */     } catch (Exception e) {
/*  265 */       e.printStackTrace();
/*      */     }
/*  267 */     this.busy = false;
/*  268 */     if (this.type.equals("lobby")) {
/*  269 */       this.tomap = this.plugin.getRandomKitArena(this, this.last, getAmountPlayers());
/*  270 */       if (this.tomap != null) {
/*      */         try {
/*  272 */           Plugin p = Bukkit.getPluginManager().getPlugin("PVPGun");
/*  273 */           if (p != null) {
/*  274 */             main pv = (main)p;
/*  275 */             if (pv != null)
/*  276 */               pv.reload();
/*      */           }
/*      */         }
/*      */         catch (Exception localException1)
/*      */         {
/*      */         }
/*      */         try {
/*  283 */           String path = this.plugin.getRoot().getAbsolutePath() + "/lastVote";
/*  284 */           Date date = new Date();
/*  285 */           Calendar calendar = GregorianCalendar.getInstance();
/*  286 */           calendar.setTimeZone(TimeZone.getTimeZone("EST"));
/*  287 */           calendar.setTime(date);
/*  288 */           BufferedReader br = FileIO.file_text_open_read(path);
/*  289 */           int i = calendar.get(5);
/*  290 */           int old = Integer.parseInt(FileIO.file_text_read_line(br));
/*  291 */           FileIO.file_text_close(br);
/*  292 */           if (i != old) {
/*  293 */             this.plugin.newDay();
/*  294 */             BufferedWriter wr = FileIO.file_text_open_write(path);
/*  295 */             FileIO.file_text_write_line(wr, Integer.toString(i));
/*  296 */             FileIO.file_text_close(wr);
/*      */           }
/*      */         }
/*      */         catch (Exception localException2) {
/*      */         }
/*  301 */         announceMap();
/*      */       }
/*      */     }
/*      */ 
/*  305 */     if (this.mytimer != -1)
/*  306 */       this.plugin.getServer().getScheduler().cancelTask(this.mytimer);
/*  307 */     this.mytimer = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new ArenaUpdater(), 2L, 2L);
/*      */ 
/*  310 */     this.maxscore = 75;
/*  311 */     this.timermax = 180;
/*  312 */     if (this.type.equals("tdm")) {
/*  313 */       if (getAmountPlayers() > 20) {
/*  314 */         this.maxscore = (75 + (getAmountPlayers() - 18) * 2);
/*  315 */         this.timermax = (180 + (getAmountPlayers() - 18) * 5);
/*  316 */         if (this.timermax > 420)
/*  317 */           this.timermax = 420;
/*      */       }
/*  319 */       if (this.gameModifier.equals(GameModeModifier.CTF)) {
/*  320 */         this.maxscore = 5;
/*  321 */         for (int i = 0; i < this.flags.size(); i++) {
/*  322 */           ((KitFlagStand)this.flags.get(i)).flag.removeFlag();
/*  323 */           ((KitFlagStand)this.flags.get(i)).setup();
/*      */         }
/*      */       }
/*  326 */       if (this.gameModifier.equals(GameModeModifier.INFECT)) {
/*  327 */         boolean loop = true;
/*  328 */         int ctr = 0;
/*  329 */         while (loop) {
/*  330 */           if (ctr > 64) {
/*  331 */             loop = false;
/*  332 */             endGame("Error!");
/*      */           }
/*  334 */           KitPlayer r = getRandomKitPlayer();
/*  335 */           if (r != null) {
/*  336 */             r.team = Teams.RED;
/*  337 */             r.boughtItems.add(new KitItem().setTag("rootZombie"));
/*  338 */             loop = false;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  343 */     if (this.type.equals("ffa")) {
/*  344 */       this.timermax = 180;
/*  345 */       if (this.gameModifier.equals(GameModeModifier.ONEINCHAMBER)) {
/*  346 */         ArrayList players = getPlayers();
/*  347 */         for (int i = 0; i < players.size(); i++) {
/*  348 */           ((KitPlayer)players.get(i)).lives = 3;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  353 */     if (this.type.equals("lobby"))
/*  354 */       this.timermax = 70;
/*  355 */     this.timer = this.timermax;
/*      */   }
/*      */ 
/*      */   public void endGame(String message)
/*      */   {
/*  360 */     if (this.mytimer != -1)
/*  361 */       this.plugin.getServer().getScheduler().cancelTask(this.mytimer);
/*  362 */     this.mytimer = -1;
/*      */     try {
/*  364 */       KitArena to = this.plugin.getFirstKitArena("lobby");
/*  365 */       if (to != null) {
/*  366 */         if (this.gameModifier.equals(GameModeModifier.CTF)) {
/*  367 */           for (int i = 0; i < this.flags.size(); i++) {
/*  368 */             ((KitFlagStand)this.flags.get(i)).flag.removeFlag();
/*      */           }
/*      */         }
/*  371 */         if ((this.timer < 0) && (message.equals(""))) {
/*  372 */           message = onOutOfTime();
/*      */         }
/*  374 */         this.bluescore = 0;
/*  375 */         this.redscore = 0;
/*  376 */         this.bluekills = 0;
/*  377 */         this.redkills = 0;
/*      */ 
/*  379 */         to.busy = false;
/*  380 */         to.started = true;
/*  381 */         this.started = false;
/*  382 */         this.plugin.currentMap = to;
/*  383 */         if (this.timermax < 10)
/*  384 */           this.timermax = 10;
/*  385 */         this.timer = this.timermax;
/*  386 */         to.timer = to.timermax;
/*  387 */         mergePlayers(to, ChatColor.BOLD + "" + ChatColor.RED + message, true);
/*  388 */         to.onStart();
/*  389 */         if (!message.equals("FORCE END"))
/*  390 */           this.plugin.onStartLobby();
/*      */       }
/*      */       else {
/*  393 */         List p = Util.Who();
/*  394 */         for (int i = 0; i < p.size(); i++)
/*  395 */           ((Player)p.get(i)).kickPlayer("SORRY THERES AN ERROR IN THE SERVER");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  399 */       e.printStackTrace();
/*  400 */       broadcastMessage("ERROR ENDING GAME!");
/*  401 */       List p = Util.Who();
/*  402 */       for (int i = 0; i < p.size(); i++)
/*  403 */         ((Player)p.get(i)).chat("/spawn");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void checkScores()
/*      */   {
/*  410 */     if (this.type.equals("tdm"))
/*      */     {
/*  412 */       if (this.gameModifier.equals(GameModeModifier.CTF)) {
/*  413 */         if (this.bluescore > this.maxscore) {
/*  414 */           multiplyXP(Teams.BLUE, 1.5D);
/*  415 */           endGame(ChatColor.BLUE + "blue " + ChatColor.WHITE + " team has won!");
/*  416 */         } else if (this.redscore > this.maxscore) {
/*  417 */           multiplyXP(Teams.RED, 1.5D);
/*  418 */           endGame(ChatColor.RED + "red " + ChatColor.WHITE + " team has won!");
/*      */         }
/*  420 */       } else if (this.gameModifier.equals(GameModeModifier.INFECT)) {
/*  421 */         int amtonBlueTeam = getAmountPlayers(Teams.BLUE);
/*  422 */         if (amtonBlueTeam < 1) {
/*  423 */           multiplyXP(Teams.RED, 2.0D);
/*  424 */           endGame(ChatColor.RED + "red " + ChatColor.WHITE + " team has won!");
/*      */         } else {
/*  426 */           for (int i = 0; i < this.players.size(); i++) {
/*  427 */             KitPlayer kp = (KitPlayer)this.players.get(i);
/*  428 */             if ((kp.team.equals(Teams.BLUE)) && 
/*  429 */               (kp.player != null) && (kp.player.isOnline())) {
/*  430 */               kp.giveXp(1);
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*  436 */       else if (this.bluekills > this.maxscore) {
/*  437 */         multiplyXP(Teams.BLUE, 1.5D);
/*  438 */         endGame(ChatColor.BLUE + "blue " + ChatColor.WHITE + " team has won!");
/*  439 */       } else if (this.redkills > this.maxscore) {
/*  440 */         multiplyXP(Teams.RED, 1.5D);
/*  441 */         endGame(ChatColor.RED + "red " + ChatColor.WHITE + " team has won!");
/*      */       }
/*      */     }
/*  444 */     else if (this.type.equals("ffa"))
/*      */     {
/*  446 */       if (this.gameModifier.equals(GameModeModifier.ONEINCHAMBER)) {
/*  447 */         int size = getActivePlayers();
/*  448 */         if (size == 1) {
/*  449 */           KitPlayer kp = getLastPlayer();
/*  450 */           multiplyXP(Teams.ALL, 2.0D);
/*  451 */           endGame(kp.player.getName() + " has won!");
/*      */         }
/*      */       } else {
/*  454 */         KitPlayer kp = getPlayerWithMostKills();
/*  455 */         if ((kp != null) && 
/*  456 */           (kp.kills >= 20)) {
/*  457 */           multiplyXP(kp, 2.0D);
/*  458 */           endGame(kp.player.getName() + " has won!");
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public String onOutOfTime()
/*      */   {
/*      */     try
/*      */     {
/*  468 */       if (this.type.equals("tdm")) {
/*  469 */         if (this.gameModifier.equals(GameModeModifier.CTF)) {
/*  470 */           if (this.bluescore > this.redscore) {
/*  471 */             multiplyXP(Teams.BLUE, 1.5D);
/*  472 */             return ChatColor.BLUE + "blue " + ChatColor.WHITE + " team has won!";
/*  473 */           }if (this.redscore > this.bluescore) {
/*  474 */             multiplyXP(Teams.RED, 1.5D);
/*  475 */             return ChatColor.RED + "red " + ChatColor.WHITE + " team has won!";
/*      */           }
/*  477 */           multiplyXP(Teams.ALL, 1.25D);
/*  478 */           return ChatColor.RED + "STALEMATE!";
/*      */         }
/*  480 */         if (this.gameModifier.equals(GameModeModifier.INFECT)) {
/*  481 */           multiplyXP(Teams.BLUE, 2.0D);
/*  482 */           return ChatColor.BLUE + "blue " + ChatColor.WHITE + " team has won!";
/*      */         }
/*  484 */         if (this.bluekills > this.redkills) {
/*  485 */           multiplyXP(Teams.BLUE, 1.5D);
/*  486 */           return ChatColor.BLUE + "blue " + ChatColor.WHITE + " team has won!";
/*  487 */         }if (this.redkills > this.bluekills) {
/*  488 */           multiplyXP(Teams.RED, 1.5D);
/*  489 */           return ChatColor.RED + "red " + ChatColor.WHITE + " team has won!";
/*      */         }
/*  491 */         multiplyXP(Teams.ALL, 1.25D);
/*  492 */         return ChatColor.RED + "STALEMATE!";
/*      */       }
/*      */ 
/*  495 */       if (this.type.equals("ffa")) {
/*  496 */         KitPlayer kp = getPlayerWithMostKills();
/*  497 */         if (kp != null) {
/*  498 */           multiplyXP(kp, 2.0D);
/*  499 */           String ret = "blank";
/*      */           try {
/*  501 */             ret = kp.player.getName();
/*      */           }
/*      */           catch (Exception localException) {
/*      */           }
/*  505 */           return ret + " has won!";
/*      */         }
/*  507 */         return "No winner!";
/*      */       }
/*      */     }
/*      */     catch (Exception localException1)
/*      */     {
/*      */     }
/*  513 */     return "Game Over!";
/*      */   }
/*      */ 
/*      */   public void onTick() {
/*  517 */     if (this.stopped) {
/*  518 */       return;
/*      */     }
/*  520 */     for (int i = this.players.size() - 1; i >= 0; i--) {
/*  521 */       KitPlayer kp = (KitPlayer)this.players.get(i);
/*  522 */       if (kp.player != null)
/*  523 */         kp.player.setLevel(this.timer);
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean onDeath(KitPlayer killed)
/*      */   {
/*  529 */     if (this.timeSinceStart < 10)
/*  530 */       return false;
/*  531 */     killed.onDeath();
/*  532 */     if ((this.gameModifier.equals(GameModeModifier.INFECT)) && 
/*  533 */       (!killed.team.equals(Teams.RED))) {
/*  534 */       killed.team = Teams.RED;
/*  535 */       broadcastMessage(ChatColor.DARK_RED + killed.player.getName() + ChatColor.RED + " is now infected!");
/*      */     }
/*      */ 
/*  538 */     return true;
/*      */   }
/*      */ 
/*      */   public void onDamage(EntityDamageByEntityEvent event, KitPlayer damager, KitPlayer attacked) {
/*  542 */     if ((attacked.dead) || (attacked.alive < 1))
/*  543 */       return;
/*  544 */     attacked.onDamaged(event);
/*  545 */     damager.onAttack(event);
/*  546 */     double dist = attacked.player.getLocation().distance(damager.player.getLocation());
/*      */ 
/*  548 */     if (this.gameModifier.equals(GameModeModifier.INFECT)) {
/*  549 */       if ((this.plugin.isInArena(damager.player)) && (this.plugin.isInArena(attacked.player))) {
/*  550 */         if (damager.team.equals(Teams.RED)) {
/*  551 */           if (dist <= 3.25D) {
/*  552 */             attacked.player.damage(9999);
/*  553 */             damager.kills += 1;
/*  554 */             if (damager.hasTag("rootZombie")) {
/*  555 */               damager.giveXp(100);
/*  556 */               damager.sayMessage(null, ChatColor.DARK_RED + "Root Zombie Xp Bonus: " + ChatColor.YELLOW + " +100");
/*      */             } else {
/*  558 */               damager.giveXp(25);
/*  559 */               damager.sayMessage(null, ChatColor.DARK_RED + "Normal Zombie Xp Bonus: " + ChatColor.YELLOW + " +25");
/*      */             }
/*      */           } else {
/*  562 */             event.setCancelled(true);
/*      */           }
/*      */         }
/*  565 */         else if (!attacked.hasTag("rootZombie"))
/*  566 */           event.setDamage(event.getDamage() * 2);
/*      */         else {
/*  568 */           event.setDamage(event.getDamage() / 2);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  573 */       ItemStack iteminhand = damager.player.getItemInHand();
/*  574 */       if (iteminhand != null) {
/*  575 */         String holding = iteminhand.getType().toString().toLowerCase();
/*  576 */         if (holding.contains("sword")) {
/*  577 */           if (holding.contains("diamond")) {
/*  578 */             if (dist <= 3.33D)
/*  579 */               event.setDamage(event.getDamage() * 3);
/*      */             else
/*  581 */               event.setCancelled(true);
/*      */           }
/*      */           else {
/*  584 */             event.setDamage((int)(event.getDamage() * 1.75D));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  590 */     if (this.gameModifier.equals(GameModeModifier.ONEINCHAMBER)) {
/*  591 */       if (this.timeSinceStart < 10)
/*  592 */         return;
/*  593 */       if ((this.plugin.isInArena(damager.player)) && (this.plugin.isInArena(attacked.player)) && 
/*  594 */         (damager.arena.equals(attacked.arena))) {
/*  595 */         String amt = "1";
/*  596 */         if (damager.hasPerk("scavenger"))
/*  597 */           amt = "2";
/*  598 */         this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), "give " + damager.player.getName() + " " + Material.getMaterial(this.plugin.getGunAmmo(damager.kclass.secondary)).getId() + " " + amt);
/*  599 */         damager.sayMessage(null, ChatColor.DARK_AQUA + "killed " + ChatColor.AQUA + attacked.player.getName());
/*  600 */         damager.sayMessage(null, ChatColor.YELLOW + "+1" + ChatColor.WHITE + " ammo" + ChatColor.YELLOW + "    +30" + ChatColor.WHITE + " xp");
/*  601 */         damager.giveXp(30);
/*  602 */         damager.kills += 1;
/*  603 */         damager.player.updateInventory();
/*  604 */         attacked.deaths += 1;
/*  605 */         attacked.onDeath();
/*      */ 
/*  607 */         if (attacked.lives <= 0) {
/*  608 */           Player rejoin = attacked.player;
/*  609 */           this.plugin.leaveArena(attacked);
/*  610 */           this.plugin.joinArena(rejoin, null);
/*      */ 
/*  612 */           this.plugin.getKitPlayer(rejoin).sayMessage(null, ChatColor.DARK_AQUA + "YOU'RE OUT!");
/*  613 */           this.plugin.getKitPlayer(rejoin).sayMessage(null, "killed by " + ChatColor.RED + damager.player.getName());
/*      */         } else {
/*  615 */           attacked.spawn();
/*  616 */           attacked.sayMessage(null, ChatColor.RED + "Lives left: " + ChatColor.DARK_RED + Integer.toString(attacked.lives));
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void mergePlayers(KitArena to, String send, boolean kill)
/*      */   {
/*      */     try
/*      */     {
/*  626 */       if (kill) {
/*  627 */         ArrayList sendto = new ArrayList();
/*  628 */         for (int i = this.players.size() - 1; i >= 0; i--) {
/*      */           try {
/*  630 */             if (((KitPlayer)this.players.get(i)).player != null) {
/*  631 */               sendto.add(((KitPlayer)this.players.get(i)).player);
/*  632 */               ((KitPlayer)this.players.get(i)).profile.gainxp = 0;
/*  633 */               ((KitPlayer)this.players.get(i)).profile.save((KitPlayer)this.players.get(i));
/*  634 */               leaveArena((KitPlayer)this.players.get(i));
/*      */             }
/*      */           } catch (Exception e) {
/*  637 */             e.printStackTrace();
/*      */           }
/*      */         }
/*  640 */         this.players.clear();
/*  641 */         for (int i = 0; i < sendto.size(); i++)
/*  642 */           to.join((Player)sendto.get(i));
/*      */       }
/*      */       else {
/*  645 */         for (int i = this.players.size() - 1; i >= 0; i--) {
/*      */           try {
/*  647 */             if ((((KitPlayer)this.players.get(i)).player != null) && 
/*  648 */               (((KitPlayer)this.players.get(i)).player.isOnline())) {
/*  649 */               ((KitPlayer)this.players.get(i)).profile.save((KitPlayer)this.players.get(i));
/*  650 */               to.join((KitPlayer)this.players.get(i), ((KitPlayer)this.players.get(i)).player);
/*      */             }
/*      */           }
/*      */           catch (Exception e) {
/*  654 */             e.printStackTrace();
/*      */           }
/*      */         }
/*  657 */         this.players.clear();
/*      */       }
/*      */ 
/*  660 */       to.broadcastMessage(send);
/*      */     } catch (Exception e) {
/*  662 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getActivePlayers()
/*      */   {
/*  668 */     int ret = 0;
/*  669 */     for (int i = this.players.size() - 1; i >= 0; i--) {
/*  670 */       if ((((KitPlayer)this.players.get(i)).player != null) && 
/*  671 */         (((KitPlayer)this.players.get(i)).player.isOnline())) {
/*  672 */         ret++;
/*      */       }
/*      */     }
/*      */ 
/*  676 */     return ret;
/*      */   }
/*      */ 
/*      */   public KitPlayer getLastPlayer()
/*      */   {
/*  681 */     for (int i = this.players.size() - 1; i >= 0; i--) {
/*  682 */       if ((((KitPlayer)this.players.get(i)).player != null) && 
/*  683 */         (((KitPlayer)this.players.get(i)).player.isOnline())) {
/*  684 */         return (KitPlayer)this.players.get(i);
/*      */       }
/*      */     }
/*      */ 
/*  688 */     return null;
/*      */   }
/*      */ 
/*      */   public void getKill(KitPlayer kp) {
/*  692 */     if (this.type.equals("tdm")) {
/*  693 */       if (kp.team.equals(Teams.BLUE))
/*  694 */         this.bluekills += 1;
/*  695 */       else if (kp.team.equals(Teams.RED))
/*  696 */         this.redkills += 1;
/*      */     }
/*  698 */     else this.type.equals("ffa");
/*      */   }
/*      */ 
/*      */   public int getAmountPlayers()
/*      */   {
/*  704 */     int ret = 0;
/*  705 */     for (int i = this.players.size() - 1; i >= 0; i--) {
/*  706 */       if ((((KitPlayer)this.players.get(i)).player != null) && 
/*  707 */         (((KitPlayer)this.players.get(i)).player.isOnline())) {
/*  708 */         ret++;
/*      */       }
/*      */     }
/*      */ 
/*  712 */     return ret;
/*      */   }
/*      */ 
/*      */   public int getAmountPlayers(Teams team) {
/*  716 */     int ret = 0;
/*  717 */     for (int i = this.players.size() - 1; i >= 0; i--) {
/*  718 */       if ((((KitPlayer)this.players.get(i)).player != null) && 
/*  719 */         (((KitPlayer)this.players.get(i)).player.isOnline()) && (
/*  720 */         (((KitPlayer)this.players.get(i)).team == team) || (team.equals(Teams.ALL)))) {
/*  721 */         ret++;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  726 */     return ret;
/*      */   }
/*      */ 
/*      */   public Teams getTeam() {
/*  730 */     if (this.type.equals("ffa")) {
/*  731 */       return Teams.FFA;
/*      */     }
/*  733 */     if (this.type.equals("lobby")) {
/*  734 */       return Teams.NEUTRAL;
/*      */     }
/*      */ 
/*  737 */     if (this.gameModifier.equals(GameModeModifier.INFECT)) {
/*  738 */       return Teams.BLUE;
/*      */     }
/*      */ 
/*  741 */     int amtred = getAmountPlayers(Teams.RED);
/*  742 */     int amtblue = getAmountPlayers(Teams.BLUE);
/*      */ 
/*  744 */     if (amtred > amtblue) {
/*  745 */       return Teams.BLUE;
/*      */     }
/*  747 */     if (amtred < amtblue) {
/*  748 */       return Teams.RED;
/*      */     }
/*  750 */     return Teams.BLUE;
/*      */   }
/*      */ 
/*      */   public void announceMap() {
/*      */     try {
/*  755 */       broadcastMessage(ChatColor.YELLOW + ">>>" + ChatColor.DARK_RED + "NEXT MAP: " + ChatColor.GOLD + this.tomap.name + ChatColor.DARK_RED + "     GAMEMODE: " + ChatColor.GOLD + this.tomap.getArenaType() + ChatColor.YELLOW + "<<<");
/*      */     }
/*      */     catch (Exception localException) {
/*      */     }
/*      */   }
/*      */ 
/*      */   private String getArenaType() {
/*  762 */     String ret = this.type;
/*  763 */     if (!this.gameModifier.equals(GameModeModifier.NONE)) {
/*  764 */       ret = this.gameModifier.toString().toLowerCase();
/*      */     }
/*  766 */     return ret;
/*      */   }
/*      */ 
/*      */   public void broadcastMessage(String send) {
/*  770 */     for (int i = this.players.size() - 1; i >= 0; i--)
/*  771 */       ((KitPlayer)this.players.get(i)).sayMessage(null, send);
/*      */   }
/*      */ 
/*      */   private void killPlayers() {
/*  775 */     for (int i = this.players.size() - 1; i >= 0; i--) {
/*  776 */       ((KitPlayer)this.players.get(i)).player.setHealth(0);
/*  777 */       ((KitPlayer)this.players.get(i)).player.damage(99, ((KitPlayer)this.players.get(i)).player);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void multiplyXP(Teams e, double d) {
/*  782 */     for (int i = 0; i < this.players.size(); i++)
/*  783 */       if ((((KitPlayer)this.players.get(i)).team == e) || (e.equals(Teams.ALL)))
/*  784 */         multiplyXP((KitPlayer)this.players.get(i), d);
/*      */   }
/*      */ 
/*      */   private void multiplyXP(KitPlayer kp, double d)
/*      */   {
/*  790 */     d -= 1.0D;
/*  791 */     if (d < 0.0D)
/*  792 */       d = 0.0D;
/*  793 */     int newxp = (int)(kp.profile.gainxp * d);
/*  794 */     kp.profile.xp += newxp;
/*      */   }
/*      */ 
/*      */   private KitPlayer getRandomKitPlayer() {
/*  798 */     return (KitPlayer)this.players.get(this.r.nextInt(getActivePlayers()));
/*      */   }
/*      */ 
/*      */   public Location getSpawnLocation(KitPlayer kp) {
/*  802 */     Location addto = new Location((World)Bukkit.getServer().getWorlds().get(0), 0.5D, 1.0D, 0.5D);
/*  803 */     if (this.type.equals("tdm")) {
/*  804 */       if (kp.team.equals(Teams.BLUE)) {
/*  805 */         return this.spawnpoint.clone().add(addto);
/*      */       }
/*  807 */       return this.spawnpoint2.clone().add(addto);
/*      */     }
/*  809 */     if (this.type.equals("ffa")) {
/*  810 */       KitSpawn ks = getKitSpawn();
/*  811 */       if (ks != null) {
/*  812 */         ks.spawn(kp);
/*  813 */         return ks.location.clone().add(addto);
/*      */       }
/*      */     }
/*  816 */     return this.spawnpoint.clone().add(addto);
/*      */   }
/*      */ 
/*      */   public KitSpawn getKitSpawn() {
/*  820 */     KitSpawn ret = null;
/*  821 */     int timeLeft = 10000;
/*  822 */     for (int i = 0; i < this.spawns.size(); i++) {
/*  823 */       if (((KitSpawn)this.spawns.get(i)).lastSpawn <= timeLeft) {
/*  824 */         timeLeft = ((KitSpawn)this.spawns.get(i)).lastSpawn;
/*  825 */         ret = (KitSpawn)this.spawns.get(i);
/*      */       }
/*      */     }
/*  828 */     return ret;
/*      */   }
/*      */ 
/*      */   public void stop() {
/*  832 */     this.stopped = true;
/*  833 */     for (int i = this.players.size() - 1; i >= 0; i--) {
/*  834 */       this.plugin.leaveArena((KitPlayer)this.players.get(i));
/*      */     }
/*  836 */     this.players.clear();
/*  837 */     endGame("FORCE END");
/*      */   }
/*      */ 
/*      */   public boolean isFull() {
/*  841 */     return getAmountPlayers() >= this.maxPlayers;
/*      */   }
/*      */ 
/*      */   public boolean checkSpawn(KitPlayer player) {
/*  845 */     player.sayMessage(null, this.message);
/*  846 */     if (this.type.equals("lobby")) {
/*  847 */       return true;
/*      */     }
/*  849 */     if ((this.gameModifier.equals(GameModeModifier.INFECT)) && 
/*  850 */       (player.team.equals(Teams.RED))) {
/*  851 */       new Perk(player.player).addPotion("SPEED", 0).giveToPlayer(player.player).clear();
/*  852 */       return true;
/*      */     }
/*      */ 
/*  855 */     if ((this.type.equals("ffa")) && 
/*  856 */       (this.gameModifier.equals(GameModeModifier.ONEINCHAMBER)))
/*      */     {
/*  858 */       player.giveItem(player.player, player.kclass.weapon1, null, (byte)0, 1, 0);
/*  859 */       player.giveItem(player.player, player.kclass.weapon3, null, (byte)0, 1, 1);
/*  860 */       player.giveItem(player.player, player.kclass.weapon9, null, (byte)0, 1, 8);
/*  861 */       return true;
/*      */     }
/*      */ 
/*  864 */     return false;
/*      */   }
/*      */ 
/*      */   public KitPlayer join(Player p) {
/*  868 */     if (p == null)
/*  869 */       return null;
/*  870 */     if (!p.isOnline())
/*  871 */       return null;
/*  872 */     KitPlayer kap = new KitPlayer();
/*  873 */     join(kap, p);
/*  874 */     return kap;
/*      */   }
/*      */ 
/*      */   public KitPlayer join(KitPlayer kap, Player p) {
/*  878 */     if (p == null)
/*  879 */       return null;
/*  880 */     if (!p.isOnline())
/*  881 */       return null;
/*  882 */     kap.start(this.plugin, this, p, getTeam());
/*  883 */     kap.spawn();
/*  884 */     this.players.add(kap);
/*      */ 
/*  886 */     TagAPI.refreshPlayer(kap.player);
/*  887 */     if (this.busy) {
/*  888 */       kap.sayMessage(null, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "" + "A game is currently taking place");
/*  889 */       kap.sayMessage(null, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "  It will end shortly! Please wait");
/*      */     }
/*  891 */     return kap;
/*      */   }
/*      */ 
/*      */   public KitPlayer getPlayer(Player player) {
/*  895 */     for (int i = this.players.size() - 1; i >= 0; i--) {
/*  896 */       if ((player != null) && (((KitPlayer)this.players.get(i)).player != null) && 
/*  897 */         (player.getName().equals(((KitPlayer)this.players.get(i)).player.getName()))) {
/*  898 */         return (KitPlayer)this.players.get(i);
/*      */       }
/*      */     }
/*      */ 
/*  902 */     return null;
/*      */   }
/*      */ 
/*      */   public void leaveArena(KitPlayer kp) {
/*      */     try {
/*  907 */       kp.player.setPlayerListName(kp.player.getName());
/*      */     }
/*      */     catch (Exception localException) {
/*      */     }
/*  911 */     for (int i = this.players.size() - 1; i >= 0; i--) {
/*      */       try {
/*  913 */         if (kp.player.getName().equals(((KitPlayer)this.players.get(i)).player.getName()))
/*  914 */           this.players.remove(i);
/*      */       }
/*      */       catch (Exception localException1)
/*      */       {
/*      */       }
/*      */     }
/*  920 */     this.plugin.clearPlayer(kp.player);
/*  921 */     kp.player.teleport(kp.returnto);
/*      */   }
/*      */ 
/*      */   public void loadArena() {
/*  925 */     String path = this.plugin.getRoot().getAbsolutePath() + "/arenas/" + this.name;
/*  926 */     FileInputStream fstream = null;
/*  927 */     DataInputStream in = null;
/*  928 */     BufferedReader br = null;
/*      */     try {
/*  930 */       fstream = new FileInputStream(path);
/*  931 */       in = new DataInputStream(fstream);
/*  932 */       br = new BufferedReader(new InputStreamReader(in));
/*      */ 
/*  934 */       Location loc1 = getLocationFromString(br.readLine());
/*  935 */       Location loc2 = getLocationFromString(br.readLine());
/*  936 */       this.spawnpoint = getLocationFromString(br.readLine());
/*  937 */       this.spawnpoint2 = getLocationFromString(br.readLine());
/*      */ 
/*  939 */       this.field.setParam(loc1.getX(), loc1.getZ(), loc2.getX(), loc2.getZ());
/*      */     } catch (Exception e) {
/*  941 */       System.err.print("ERROR READING KITPVP ARENA");
/*      */     }
/*  943 */     loadConfig(br);
/*      */     try { br.close(); } catch (Exception localException1) {
/*      */     }try { in.close(); } catch (Exception localException2) {
/*      */     }try { fstream.close(); } catch (Exception localException3) {
/*      */     }
/*      */   }
/*      */ 
/*  950 */   private void loadConfig(BufferedReader br) { ArrayList file = new ArrayList();
/*      */     try
/*      */     {
/*  953 */       String str = br.readLine();
/*  954 */       if ((str != null) && 
/*  955 */         (str.equals("--config--")))
/*      */       {
/*      */         String strLine;
/*  956 */         while ((strLine = br.readLine()) != null)
/*      */         {
/*      */           String strLine;
/*  957 */           file.add(strLine);
/*      */         }
/*  959 */         for (int i = 0; i < file.size(); i++) {
/*  960 */           computeConfigData((String)file.get(i));
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (IOException localIOException)
/*      */     {
/*      */     }
/*  967 */     if (this.minPlayers == -1) {
/*  968 */       this.minPlayers = (this.maxPlayers - 40);
/*      */     }
/*  970 */     if (this.maxPlayers >= Util.server.getMaxPlayers())
/*  971 */       this.maxPlayers = (Util.server.getMaxPlayers() * 2);
/*      */   }
/*      */ 
/*      */   private void computeConfigData(String str)
/*      */   {
/*  976 */     if (str.indexOf("=") > 0) {
/*  977 */       String str2 = str.substring(0, str.indexOf("="));
/*  978 */       if (str2.equalsIgnoreCase("type"))
/*  979 */         this.type = str.substring(str.indexOf("=") + 1);
/*  980 */       if (str2.equalsIgnoreCase("maxPlayers"))
/*  981 */         this.maxPlayers = Integer.parseInt(str.substring(str.indexOf("=") + 1));
/*  982 */       if (str2.equalsIgnoreCase("minPlayers"))
/*  983 */         this.minPlayers = Integer.parseInt(str.substring(str.indexOf("=") + 1));
/*  984 */       if (str2.equalsIgnoreCase("modifier")) {
/*  985 */         String check = str.substring(str.indexOf("=") + 1).toUpperCase();
/*  986 */         if (check.length() > 1) {
/*  987 */           this.gameModifier = GameModeModifier.valueOf(check);
/*      */         }
/*      */       }
/*  990 */       if (str2.equalsIgnoreCase("addspawn")) {
/*  991 */         Location spawnloc = getLocationFromString(str.substring(str.indexOf("=") + 1));
/*  992 */         if (spawnloc != null)
/*  993 */           if (this.gameModifier.equals(GameModeModifier.CTF)) {
/*  994 */             int color = 11;
/*  995 */             if (this.flags.size() == 1)
/*  996 */               color = 14;
/*  997 */             this.flags.add(new KitFlagStand(this, color, spawnloc));
/*      */           } else {
/*  999 */             KitSpawn ks = new KitSpawn(spawnloc);
/* 1000 */             ks.lastSpawn = (1000 + Util.random(200));
/* 1001 */             this.spawns.add(ks);
/*      */           }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Location getLocationFromString(String str)
/*      */   {
/* 1009 */     String[] arr = str.split(",");
/* 1010 */     if (arr.length == 2)
/* 1011 */       return new Location((World)this.plugin.getServer().getWorlds().get(0), Integer.parseInt(arr[0]), 0.0D, Integer.parseInt(arr[1]));
/* 1012 */     if (arr.length == 3) {
/* 1013 */       return new Location((World)this.plugin.getServer().getWorlds().get(0), Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
/*      */     }
/* 1015 */     return null;
/*      */   }
/*      */ 
/*      */   public String getMode() {
/* 1019 */     return this.type;
/*      */   }
/*      */ 
/*      */   public ArrayList<KitPlayer> getPlayersOnTeam(Teams team) {
/* 1023 */     ArrayList ret = new ArrayList();
/* 1024 */     for (int i = 0; i < this.players.size(); i++) {
/* 1025 */       if ((((KitPlayer)this.players.get(i)).team == team) || (team.equals(Teams.ALL))) {
/* 1026 */         ret.add((KitPlayer)this.players.get(i));
/*      */       }
/*      */     }
/* 1029 */     return ret;
/*      */   }
/*      */ 
/*      */   public ArrayList<KitPlayer> getPlayers() {
/* 1033 */     ArrayList ret = new ArrayList();
/* 1034 */     for (int i = 0; i < this.players.size(); i++) {
/* 1035 */       if ((this.players.get(i) != null) && 
/* 1036 */         (((KitPlayer)this.players.get(i)).player.isOnline())) {
/* 1037 */         ret.add((KitPlayer)this.players.get(i));
/*      */       }
/*      */     }
/*      */ 
/* 1041 */     return ret;
/*      */   }
/*      */ 
/*      */   public KitPlayer getPlayerWithMostKills() {
/* 1045 */     KitPlayer most = null;
/* 1046 */     int kills = 0;
/* 1047 */     for (int i = 0; i < this.players.size(); i++) {
/* 1048 */       if (((KitPlayer)this.players.get(i)).kills > kills) {
/* 1049 */         kills = ((KitPlayer)this.players.get(i)).kills;
/* 1050 */         most = (KitPlayer)this.players.get(i);
/*      */       }
/*      */     }
/* 1053 */     return most;
/*      */   }
/*      */ 
/*      */   public KitFlag getFlag(Item item) {
/* 1057 */     if (this.gameModifier.equals(GameModeModifier.CTF)) {
/* 1058 */       for (int i = 0; i < this.flags.size(); i++) {
/* 1059 */         if (((KitFlagStand)this.flags.get(i)).flag.itemInWorld.equals(item)) {
/* 1060 */           return ((KitFlagStand)this.flags.get(i)).flag;
/*      */         }
/*      */       }
/*      */     }
/* 1064 */     return null;
/*      */   }
/*      */ 
/*      */   public int getWoolColor(KitPlayer kitPlayer) {
/* 1068 */     if ((this.gameModifier.equals(GameModeModifier.INFECT)) && 
/* 1069 */       (kitPlayer.team.equals(Teams.RED))) {
/* 1070 */       MaterialData data = new MaterialData(397);
/* 1071 */       if (kitPlayer.hasTag("rootzombie"))
/* 1072 */         data.setData((byte)0);
/*      */       else {
/* 1074 */         data.setData((byte)2);
/*      */       }
/* 1076 */       ItemStack itm = data.toItemStack(1);
/* 1077 */       kitPlayer.player.getInventory().setHelmet(itm);
/* 1078 */       return 999;
/*      */     }
/*      */ 
/* 1081 */     return -1;
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
/*  204 */       for (int i = 0; i < KitArena.this.flags.size(); i++)
/*  205 */         ((KitFlagStand)KitArena.this.flags.get(i)).tick();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static enum GameModeModifier
/*      */   {
/* 1085 */     NONE, ONEINCHAMBER, CTF, INFECT, GUNGAME;
/*      */   }
/*      */ 
/*      */   public static enum Teams {
/* 1089 */     RED, BLUE, FFA, NEUTRAL, ALL;
/*      */   }
/*      */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.arena.KitArena
 * JD-Core Version:    0.6.2
 */