/*    */ package com.orange451.mcwarfare.arena;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class KitSpawn
/*    */ {
/*    */   public Location location;
/*  9 */   public int lastSpawn = 400;
/*    */ 
/*    */   public KitSpawn(Location location) {
/* 12 */     this.location = location.clone().add(0.0D, 1.25D, 0.0D);
/*    */   }
/*    */ 
/*    */   public void spawn(KitPlayer kp) {
/* 16 */     this.lastSpawn = 9999;
/* 17 */     kp.player.teleport(this.location.clone());
/*    */   }
/*    */ 
/*    */   public void tick() {
/* 21 */     this.lastSpawn -= 1;
/* 22 */     if (this.lastSpawn < 0)
/* 23 */       this.lastSpawn += 400;
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.arena.KitSpawn
 * JD-Core Version:    0.6.2
 */