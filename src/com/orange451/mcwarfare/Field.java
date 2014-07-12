/*    */ package com.orange451.mcwarfare;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ 
/*    */ public class Field
/*    */ {
/*    */   public int minx;
/*    */   public int miny;
/*    */   public int maxx;
/*    */   public int maxy;
/*    */   public int width;
/*    */   public int length;
/*    */ 
/*    */   public Field(double x, double z, double x2, double z2)
/*    */   {
/* 14 */     setParam(x, z, x2, z2);
/*    */   }
/*    */ 
/*    */   public Field() {
/*    */   }
/*    */ 
/*    */   public void setParam(double x, double z, double x2, double z2) {
/* 21 */     this.minx = ((int)x);
/* 22 */     this.miny = ((int)z);
/* 23 */     this.maxx = ((int)x2);
/* 24 */     this.maxy = ((int)z2);
/*    */ 
/* 26 */     if (this.minx > this.maxx) {
/* 27 */       this.maxx = this.minx;
/* 28 */       this.minx = ((int)x2);
/*    */     }
/*    */ 
/* 31 */     if (this.miny > this.maxy) {
/* 32 */       this.maxy = this.miny;
/* 33 */       this.miny = ((int)z2);
/*    */     }
/*    */ 
/* 36 */     this.width = (this.maxx - this.minx);
/* 37 */     this.length = (this.maxy - this.miny);
/*    */   }
/*    */ 
/*    */   public boolean isInside(Location loc) {
/* 41 */     int locx = loc.getBlockX();
/* 42 */     int locz = loc.getBlockZ();
/* 43 */     if ((locx >= this.minx) && (locx <= this.maxx) && 
/* 44 */       (locz >= this.miny) && (locz <= this.maxy)) {
/* 45 */       return true;
/*    */     }
/*    */ 
/* 48 */     return false;
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.Field
 * JD-Core Version:    0.6.2
 */