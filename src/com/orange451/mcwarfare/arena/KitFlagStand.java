/*    */ package com.orange451.mcwarfare.arena;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class KitFlagStand
/*    */ {
/*    */   public Location location;
/*    */   public int color;
/*    */   public KitFlag flag;
/*    */   public KitArena arena;
/* 14 */   public int capcheck = 0;
/*    */ 
/*    */   public KitFlagStand(KitArena arena, int color, Location loc) {
/* 17 */     this.arena = arena;
/* 18 */     this.location = loc;
/* 19 */     this.color = color;
/* 20 */     this.flag = new KitFlag(this, color);
/* 21 */     setup();
/*    */   }
/*    */ 
/*    */   public void setup() {
/* 25 */     this.location.getBlock().setType(Material.FENCE);
/* 26 */     this.location.clone().add(0.0D, -1.0D, 1.0D).getBlock().setType(Material.STONE);
/* 27 */     this.location.clone().add(0.0D, -1.0D, -1.0D).getBlock().setType(Material.STONE);
/* 28 */     this.location.clone().add(1.0D, -1.0D, 0.0D).getBlock().setType(Material.STONE);
/* 29 */     this.location.clone().add(-1.0D, -1.0D, 0.0D).getBlock().setType(Material.STONE);
/* 30 */     this.location.clone().add(0.0D, -1.0D, 0.0D).getBlock().setType(Material.GOLD_BLOCK);
/* 31 */     this.flag.spawn(this.location.clone().add(0.0D, 2.0D, 0.0D));
/*    */   }
/*    */ 
/*    */   public void stop() {
/*    */     try {
/* 36 */       this.flag.removeFlag();
/*    */     }
/*    */     catch (Exception localException) {
/*    */     }
/*    */   }
/*    */ 
/*    */   public KitFlagStand getOtherFlagStand() {
/* 43 */     for (int i = 0; i < this.arena.flags.size(); i++) {
/* 44 */       if (((KitFlagStand)this.arena.flags.get(i)).color != this.color) {
/* 45 */         return (KitFlagStand)this.arena.flags.get(i);
/*    */       }
/*    */     }
/* 48 */     return null;
/*    */   }
/*    */ 
/*    */   public KitFlag getOtherFlag() {
/* 52 */     return getOtherFlagStand().flag;
/*    */   }
/*    */ 
/*    */   public void score() {
/* 56 */     if (this.flag.teamColor == 11)
/* 57 */       this.arena.bluescore += 1;
/*    */     else
/* 59 */       this.arena.redscore += 1;
/*    */   }
/*    */ 
/*    */   public void tick()
/*    */   {
/* 64 */     this.flag.tick();
/* 65 */     if (getOtherFlag().isHolding)
/* 66 */       for (int i = 0; i < Bukkit.getServer().getOnlinePlayers().length; i++) {
/* 67 */         Player p = Bukkit.getServer().getOnlinePlayers()[i];
/* 68 */         if ((p != null) && 
/* 69 */           (!p.isDead())) {
/* 70 */           KitPlayer kp = this.arena.plugin.getKitPlayer(p);
/* 71 */           if ((kp != null) && 
/* 72 */             (kp.getWoolColor() == this.flag.teamColor))
/*    */             try {
/* 74 */               if ((getOtherFlag().carrier.getName().equals(kp.player.getName())) && 
/* 75 */                 (p.getLocation().distanceSquared(this.location) < 3.5D)) {
/* 76 */                 this.arena.broadcastMessage(this.arena.plugin.getKitPlayerName(p) + ChatColor.AQUA + " captured the" + getOtherFlag().flag + ChatColor.AQUA + "flag!");
/* 77 */                 kp.giveXp(75);
/* 78 */                 kp.profile.credits += 2;
/* 79 */                 score();
/* 80 */                 getOtherFlag().removeFlag();
/* 81 */                 getOtherFlagStand().setup();
/*    */               }
/*    */             }
/*    */             catch (Exception localException)
/*    */             {
/*    */             }
/*    */         }
/*    */       }
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.arena.KitFlagStand
 * JD-Core Version:    0.6.2
 */