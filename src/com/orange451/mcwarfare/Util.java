/*     */ package com.orange451.mcwarfare;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Effect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class Util
/*     */ {
/*     */   public static KitPvP plugin;
/*     */   public static World world;
/*     */   public static Server server;
/*     */ 
/*     */   public static void Initialize(KitPvP kitPvP)
/*     */   {
/*  20 */     plugin = kitPvP;
/*  21 */     server = kitPvP.getServer();
/*  22 */     world = (World)server.getWorlds().get(0);
/*     */   }
/*     */ 
/*     */   public static Player MatchPlayer(String player) {
/*  26 */     List players = server.matchPlayer(player);
/*     */ 
/*  28 */     if (players.size() == 1) {
/*  29 */       return (Player)players.get(0);
/*     */     }
/*  31 */     return null;
/*     */   }
/*     */ 
/*     */   public static List<Player> Who()
/*     */   {
/*  36 */     Player[] players = server.getOnlinePlayers();
/*  37 */     List players1 = new ArrayList();
/*  38 */     for (int i = 0; i < players.length; i++) {
/*  39 */       players1.add(players[i]);
/*     */     }
/*  41 */     return players1;
/*     */   }
/*     */ 
/*     */   public static void playEffect(Effect e, Location l, int num) {
/*  45 */     for (int i = 0; i < server.getOnlinePlayers().length; i++)
/*  46 */       server.getOnlinePlayers()[i].playEffect(l, e, num);
/*     */   }
/*     */ 
/*     */   public static double point_distance(Location loc1, Location loc2)
/*     */   {
/*  51 */     double p1x = loc1.getX();
/*  52 */     double p1y = loc1.getY();
/*  53 */     double p1z = loc1.getZ();
/*     */ 
/*  55 */     double p2x = loc2.getX();
/*  56 */     double p2y = loc2.getY();
/*  57 */     double p2z = loc2.getZ();
/*  58 */     double xdist = p1x - p2x;
/*  59 */     double ydist = p1y - p2y;
/*  60 */     double zdist = p1z - p2z;
/*  61 */     return Math.sqrt(xdist * xdist + ydist * ydist + zdist * zdist);
/*     */   }
/*     */ 
/*     */   public static int random(int x) {
/*  65 */     Random rand = new Random();
/*  66 */     return rand.nextInt(x);
/*     */   }
/*     */ 
/*     */   public static double lengthdir_x(double len, double dir) {
/*  70 */     return len * Math.cos(Math.toRadians(dir));
/*     */   }
/*     */ 
/*     */   public static double lengthdir_y(double len, double dir) {
/*  74 */     return -len * Math.sin(Math.toRadians(dir));
/*     */   }
/*     */ 
/*     */   public static double point_direction(double x1, double y1, double x2, double y2) {
/*     */     double d;
/*     */     try {
/*  80 */       d = Math.toDegrees(Math.atan((y2 - y1) / (x2 - x1)));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */       double d;
/*  82 */       d = 0.0D;
/*     */     }
/*  84 */     if ((x1 > x2) && (y1 > y2))
/*     */     {
/*  86 */       return -d + 180.0D;
/*     */     }
/*  88 */     if ((x1 < x2) && (y1 > y2))
/*     */     {
/*  90 */       return -d;
/*     */     }
/*  92 */     if (x1 == x2)
/*     */     {
/*  94 */       if (y1 > y2)
/*  95 */         return 90.0D;
/*  96 */       if (y1 < y2)
/*  97 */         return 270.0D;
/*     */     }
/*  99 */     if ((x1 > x2) && (y1 < y2))
/*     */     {
/* 101 */       return -d + 180.0D;
/*     */     }
/* 103 */     if ((x1 < x2) && (y1 < y2))
/*     */     {
/* 105 */       return -d + 360.0D;
/*     */     }
/* 107 */     if (y1 == y2)
/*     */     {
/* 109 */       if (x1 > x2)
/* 110 */         return 180.0D;
/* 111 */       if (x1 < x2)
/* 112 */         return 0.0D;
/*     */     }
/* 114 */     return 0.0D;
/*     */   }
/*     */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.Util
 * JD-Core Version:    0.6.2
 */