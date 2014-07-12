/*    */ package com.orange451.mcwarfare.arena.kits;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PerkMarathon extends Perk
/*    */ {
/*    */   public PerkMarathon(Player p)
/*    */   {
/*  7 */     super(p);
/*  8 */     this.name = "marathon";
/*    */   }
/*    */ 
/*    */   public void step()
/*    */   {
/* 13 */     this.p.setFoodLevel(20);
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.arena.kits.PerkMarathon
 * JD-Core Version:    0.6.2
 */