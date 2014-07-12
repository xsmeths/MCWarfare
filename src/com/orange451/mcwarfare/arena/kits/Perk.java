/*    */ package com.orange451.mcwarfare.arena.kits;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ import org.bukkit.potion.PotionEffectType;
/*    */ 
/*    */ public class Perk
/*    */ {
/*    */   public String name;
/* 11 */   public ArrayList<PotionEffect> pots = new ArrayList();
/*    */   public Player p;
/*    */ 
/*    */   public Perk(Player p)
/*    */   {
/* 15 */     this.p = p;
/*    */   }
/*    */ 
/*    */   public Perk addPotion(String news, int level) {
/* 19 */     PotionEffectType pet = PotionEffectType.getByName(news);
/* 20 */     if (pet != null) {
/* 21 */       PotionEffect po = new PotionEffect(PotionEffectType.getByName(news), 9999, level);
/* 22 */       this.pots.add(po);
/*    */     }
/* 24 */     return this;
/*    */   }
/*    */ 
/*    */   public Perk giveToPlayer(Player p) {
/* 28 */     for (int i = 0; i < this.pots.size(); i++) {
/* 29 */       p.addPotionEffect((PotionEffect)this.pots.get(i));
/*    */     }
/* 31 */     return this;
/*    */   }
/*    */ 
/*    */   public void step() {
/*    */   }
/*    */ 
/*    */   public void clear() {
/* 38 */     this.name = null;
/* 39 */     this.p = null;
/* 40 */     this.pots.clear();
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.arena.kits.Perk
 * JD-Core Version:    0.6.2
 */