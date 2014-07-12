/*    */ package com.orange451.mcwarfare.arena;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class KitArenaSpawn
/*    */ {
/*  7 */   public int lastSpawn = 999;
/*    */   public Location location;
/*    */ 
/*    */   public void spawn(Player p)
/*    */   {
/* 11 */     p.teleport(this.location.clone().add(0.0D, 0.5D, 0.0D));
/* 12 */     this.lastSpawn = 999;
/* 13 */     p = null;
/*    */   }
/*    */ 
/*    */   public void tick() {
/* 17 */     this.lastSpawn -= 1;
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.arena.KitArenaSpawn
 * JD-Core Version:    0.6.2
 */