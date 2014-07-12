/*    */ package com.orange451.mcwarfare.mysql;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import com.orange451.mcwarfare.arena.KitPlayer;
/*    */ import com.orange451.mcwarfare.arena.KitProfile;
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ 
/*    */ public class MCWarPlayerSave
/*    */   implements Runnable
/*    */ {
/*    */   private KitPvP plugin;
/*    */   public boolean saveStats;
/*    */ 
/*    */   public MCWarPlayerSave(KitPvP plugin)
/*    */   {
/* 15 */     this.plugin = plugin;
/* 16 */     Thread t = new Thread(this);
/* 17 */     t.start();
/*    */   }
/*    */ 
/*    */   public void execute(ArrayList<KitPlayer> players) {
/* 21 */     if (players == null)
/* 22 */       return;
/* 23 */     String send = "";
/* 24 */     synchronized (players) {
/* 25 */       for (int i = 0; i < players.size(); i++) {
/* 26 */         KitPlayer pl = (KitPlayer)players.get(i);
/* 27 */         String username = pl.player.getName();
/* 28 */         int experience = pl.profile.xp;
/* 29 */         int i_level = pl.profile.level;
/* 30 */         int i_kills = pl.profile.kills;
/* 31 */         int i_deaths = pl.profile.deaths;
/* 32 */         send = send + username + "|" + experience + "|" + i_level + "|" + i_kills + "|" + i_deaths;
/* 33 */         if (i < players.size() - 1) {
/* 34 */           send = send + "|";
/*    */         }
/*    */       }
/*    */     }
/* 38 */     httphelper.send_stats(send);
/*    */   }
/*    */ 
/*    */   public void run()
/*    */   {
/* 43 */     while (this.plugin.isEnabled())
/*    */       try {
/* 45 */         if (this.saveStats) {
/* 46 */           long start = System.currentTimeMillis();
/* 47 */           execute(this.plugin.getKitPlayers());
/* 48 */           long newt = System.currentTimeMillis();
/* 49 */           final int seconds = (int)((newt - start) / 1000.0D);
/* 50 */           this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
/* 51 */             public void run() { MCWarPlayerSave.this.plugin.broadcastMessage(null, ChatColor.GREEN + "LEADERBOARD SAVED (" + seconds + ") seconds"); }
/*    */ 
/*    */           });
/* 53 */           this.saveStats = false;
/*    */         }
/*    */       }
/*    */       catch (Exception localException)
/*    */       {
/*    */       }
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.mysql.MCWarPlayerSave
 * JD-Core Version:    0.6.2
 */