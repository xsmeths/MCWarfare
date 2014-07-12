/*     */ package com.orange451.mcwarfare.arena.create;
/*     */ 
/*     */ import com.orange451.mcwarfare.KitPvP;
/*     */ import com.orange451.mcwarfare.Util;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class KitArenaCreator
/*     */ {
/*     */   public Player player;
/*     */   public Location corner1;
/*     */   public Location corner2;
/*     */   public Location spawnloc;
/*     */   public Location spawnloc2;
/*     */   public KitPvP plugin;
/*     */   public String arenaName;
/*     */   public String arenaType;
/*     */   public String modifier;
/*  25 */   public ArrayList<Location> spawns = new ArrayList();
/*     */ 
/*     */   public KitArenaCreator(KitPvP plugin, Player player, String arenaName2, String arenaType) {
/*  28 */     this.player = player;
/*  29 */     this.plugin = plugin;
/*  30 */     this.arenaType = arenaType;
/*  31 */     this.arenaName = arenaName2;
/*  32 */     this.modifier = "";
/*  33 */     player.sendMessage(ChatColor.GRAY + "STARTING TO CREATE ARENA!");
/*  34 */     player.sendMessage(ChatColor.GRAY + "  PLEASE SET CORNER 1 LOCATION");
/*  35 */     player.sendMessage(ChatColor.LIGHT_PURPLE + "    /kit setpoint");
/*     */   }
/*     */ 
/*     */   public void setPoint() {
/*  39 */     Location ploc = this.player.getLocation();
/*  40 */     boolean changed = false;
/*  41 */     if (this.corner1 == null) {
/*  42 */       this.corner1 = ploc;
/*  43 */       changed = true;
/*  44 */       this.player.sendMessage(ChatColor.GRAY + "CORNER 1 LOCATION SET");
/*  45 */       this.player.sendMessage(ChatColor.GRAY + "  SET CORNER 2 LOCATION");
/*  46 */       return;
/*     */     }
/*  48 */     if (this.corner2 == null) {
/*  49 */       this.corner2 = ploc;
/*  50 */       changed = true;
/*  51 */       this.player.sendMessage(ChatColor.GRAY + "CORNER 2 LOCATION SET");
/*  52 */       this.player.sendMessage(ChatColor.GRAY + "  SET SPAWN LOCATION (for blue)");
/*  53 */       return;
/*     */     }
/*  55 */     if (this.spawnloc == null) {
/*  56 */       this.spawnloc = ploc;
/*  57 */       changed = true;
/*  58 */       this.player.sendMessage(ChatColor.GRAY + "blue SPAWN LOCATION SET");
/*  59 */       this.player.sendMessage(ChatColor.GRAY + "  SET SPAWN LOCATION (for red)");
/*  60 */       return;
/*     */     }
/*  62 */     if (this.spawnloc2 == null) {
/*  63 */       this.spawnloc2 = ploc;
/*  64 */       changed = false;
/*  65 */       this.player.sendMessage(ChatColor.GRAY + "red SPAWN LOCATION SET");
/*  66 */       this.player.sendMessage(ChatColor.GRAY + "  ...ATTEMPTING TO SAVE ARENA...");
/*     */     }
/*     */ 
/*  69 */     if (!changed) {
/*  70 */       finish();
/*     */     }
/*     */ 
/*  73 */     this.plugin.loadArena(this.arenaName);
/*     */   }
/*     */ 
/*     */   public void finish() {
/*  77 */     this.plugin.stopMakingArena(this.player);
/*  78 */     saveArena();
/*     */   }
/*     */ 
/*     */   private void saveArena()
/*     */   {
/*  83 */     if (this.arenaType.equals("onein")) {
/*  84 */       this.arenaType = "ffa";
/*  85 */       this.modifier = "OneInChamber";
/*     */     }
/*     */ 
/*  88 */     if (this.arenaType.equals("ctf")) {
/*  89 */       this.arenaType = "tdm";
/*  90 */       this.modifier = "ctf";
/*     */     }
/*     */ 
/*  93 */     if (this.arenaType.equals("infect")) {
/*  94 */       this.arenaType = "tdm";
/*  95 */       this.modifier = "infect";
/*     */     }
/*     */ 
/*  98 */     if (this.arenaType.equals("gungame")) {
/*  99 */       this.arenaType = "ffa";
/* 100 */       this.modifier = "gungame";
/*     */     }
/*     */ 
/* 103 */     String path = this.plugin.getRoot().getAbsolutePath() + "/arenas/" + this.arenaName;
/* 104 */     FileWriter outFile = null;
/* 105 */     PrintWriter out = null;
/*     */     try {
/* 107 */       outFile = new FileWriter(path);
/* 108 */       out = new PrintWriter(outFile);
/* 109 */       out.println(this.corner1.getBlockX() + "," + this.corner1.getBlockZ());
/* 110 */       out.println(this.corner2.getBlockX() + "," + this.corner2.getBlockZ());
/* 111 */       out.println(this.spawnloc.getBlockX() + "," + this.spawnloc.getBlockY() + "," + this.spawnloc.getBlockZ());
/* 112 */       out.println(this.spawnloc2.getBlockX() + "," + this.spawnloc2.getBlockY() + "," + this.spawnloc2.getBlockZ());
/*     */ 
/* 114 */       out.println("--config--");
/* 115 */       out.println("type=" + this.arenaType);
/* 116 */       out.println("maxPlayers=" + Util.server.getMaxPlayers());
/* 117 */       out.println("minPlayers=" + (Util.server.getMaxPlayers() - 40));
/* 118 */       out.println("modifier=" + this.modifier);
/* 119 */       if (this.spawns.size() > 0) {
/* 120 */         for (int i = 0; i < this.spawns.size(); i++) {
/* 121 */           out.println("addspawn=" + ((Location)this.spawns.get(i)).getBlockX() + "," + ((Location)this.spawns.get(i)).getBlockY() + "," + ((Location)this.spawns.get(i)).getBlockZ());
/*     */         }
/*     */       }
/*     */ 
/* 125 */       System.out.println("KITARENA: " + this.arenaName + " SUCCESFULLY SAVED!");
/* 126 */       this.player.sendMessage(ChatColor.YELLOW + "KitArena Saved!");
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*     */     }
/*     */     try {
/* 132 */       out.close();
/* 133 */       outFile.close();
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addSpawn() {
/* 140 */     this.spawns.add(this.player.getLocation().clone());
/*     */   }
/*     */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.arena.create.KitArenaCreator
 * JD-Core Version:    0.6.2
 */