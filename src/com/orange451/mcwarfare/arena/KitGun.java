/*    */ package com.orange451.mcwarfare.arena;
/*    */ 
/*    */ public class KitGun
/*    */ {
/*    */   public String name;
/*    */   public String desc;
/*    */   public String slot;
/*    */   public int cost;
/*    */   public int level;
/*    */   public int type;
/*    */ 
/*    */   public KitGun(String name, String desc, int cost, int level, int type)
/*    */   {
/* 12 */     this.name = name;
/* 13 */     this.desc = desc;
/* 14 */     this.cost = cost;
/* 15 */     this.level = level;
/* 16 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public KitGun()
/*    */   {
/*    */   }
/*    */ 
/*    */   public boolean isUnlocked(KitPlayer kp) {
/* 24 */     return kp.profile.level >= this.level;
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.arena.KitGun
 * JD-Core Version:    0.6.2
 */