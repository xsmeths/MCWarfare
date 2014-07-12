/*    */ package com.orange451.mcwarfare.arena;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
/*    */ 
/*    */ public class KilledPlayer
/*    */ {
/*    */   public KitPvP plugin;
/*    */   public Player killer;
/*    */   public Player killed;
/*    */ 
/*    */   public KilledPlayer(KitPvP plugin, Player killer, Player died)
/*    */   {
/* 18 */     this.plugin = plugin;
/* 19 */     this.killer = killer;
/* 20 */     this.killed = died;
/* 21 */     execute();
/*    */   }
/*    */ 
/*    */   public void execute() {
/* 25 */     EntityDamageEvent dmg = this.killed.getLastDamageCause();
/*    */ 
/* 27 */     if (dmg.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK))
/*    */     {
/* 29 */       KitPlayer kp = this.plugin.getKitPlayer(this.killer);
/* 30 */       KitPlayer dp = this.plugin.getKitPlayer(this.killed);
/* 31 */       if ((kp != null) && (dp != null)) {
/* 32 */         if (kp.arena.timeSinceStart < 10)
/* 33 */           return;
/* 34 */         kp.arena.getKill(kp);
/* 35 */         int xp = this.plugin.getKillXP(kp.player);
/* 36 */         int credits = this.plugin.getKillCredits(kp.player);
/* 37 */         kp.giveXp(xp);
/* 38 */         kp.onKill(dp);
/* 39 */         kp.profile.credits += credits;
/* 40 */         kp.profile.kills += 1;
/* 41 */         dp.profile.deaths += 1;
/* 42 */         kp.kills += 1;
/* 43 */         dp.deaths += 1;
/* 44 */         String k = kp.getTeamColor() + kp.player.getName();
/* 45 */         String d = dp.getTeamColor() + dp.player.getName();
/* 46 */         kp.sayMessage(null, k + ChatColor.GRAY + " killed " + d + " +" + 
/* 47 */           ChatColor.YELLOW + xp + " XP " + " +" + 
/* 48 */           ChatColor.YELLOW + credits + " Credits");
/*    */ 
/* 54 */         dp.sayMessage(null, ChatColor.RED + "killed by " + ChatColor.GRAY + kp.name + ChatColor.RED + "!");
/* 55 */         kp.calculate();
/*    */       }
/*    */     }
/* 58 */     this.killer = null;
/* 59 */     this.killed = null;
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.arena.KilledPlayer
 * JD-Core Version:    0.6.2
 */