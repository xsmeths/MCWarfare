/*    */ package com.orange451.mcwarfare;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ 
/*    */ public class Field3D extends Field
/*    */ {
/*    */   public World world;
/*    */   public int minz;
/*    */   public int maxz;
/*    */   public int height;
/*    */   public KitPvP plugin;
/*    */ 
/*    */   public Field3D(double x, double y, double z, double x2, double y2, double z2)
/*    */   {
/* 17 */     setParam(x, y, z, x2, y2, z2);
/*    */   }
/*    */ 
/*    */   public Field3D() {
/*    */   }
/*    */ 
/*    */   public Field3D(KitPvP plugin, World world) {
/* 24 */     this.plugin = plugin;
/* 25 */     this.world = world;
/*    */   }
/*    */ 
/*    */   public void setParam(double x, double y, double z, double x2, double y2, double z2) {
/* 29 */     setParam(x, y, x2, y2);
/*    */ 
/* 31 */     this.minz = ((int)z);
/* 32 */     this.maxz = ((int)z2);
/*    */ 
/* 34 */     if (this.minz > this.maxz) {
/* 35 */       this.maxz = this.minz;
/* 36 */       this.minz = ((int)z2);
/*    */     }
/*    */ 
/* 39 */     this.height = (this.maxz - this.minz);
/*    */   }
/*    */ 
/*    */   public Block getBlockAt(int i, int ii, int iii) {
/* 43 */     return this.world.getBlockAt(this.minx + i, this.minz + ii, this.miny + iii);
/*    */   }
/*    */ 
/*    */   public boolean isInside(Location loc) {
/* 47 */     if (super.isInside(loc)) {
/* 48 */       int locy = loc.getBlockY();
/* 49 */       if ((locy >= this.minz) && (locy <= this.maxz)) {
/* 50 */         return true;
/*    */       }
/*    */     }
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean isUnder(Location loc) {
/* 57 */     if ((super.isInside(loc)) && 
/* 58 */       (loc.getBlockY() < this.maxz)) {
/* 59 */       return true;
/*    */     }
/*    */ 
/* 62 */     return false;
/*    */   }
/*    */ 
/*    */   public void setType(Material mat) {
/* 66 */     setType(mat.getId());
/*    */   }
/*    */ 
/*    */   public void setType(final int id) {
/* 70 */     this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
/*    */       public void run() {
/* 72 */         for (int i = Field3D.this.minx; i <= Field3D.this.maxx; i++)
/* 73 */           for (int ii = Field3D.this.miny; ii <= Field3D.this.maxy; ii++)
/* 74 */             for (int iii = Field3D.this.minz; iii <= Field3D.this.maxz; iii++) {
/* 75 */               Block b = Field3D.this.world.getBlockAt(i, iii, ii);
/* 76 */               b.setTypeId(id);
/*    */             }
/*    */       }
/*    */     });
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.Field3D
 * JD-Core Version:    0.6.2
 */