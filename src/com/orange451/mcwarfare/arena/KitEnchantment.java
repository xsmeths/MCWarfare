/*    */ package com.orange451.mcwarfare.arena;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.enchantments.Enchantment;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class KitEnchantment
/*    */ {
/*  9 */   public ArrayList<Enchantment> enchantments = new ArrayList();
/* 10 */   public ArrayList<Integer> levels = new ArrayList();
/*    */ 
/*    */   public void add(ItemStack itm) {
/* 13 */     for (int i = 0; i < this.enchantments.size(); i++) {
/* 14 */       Enchantment e = (Enchantment)this.enchantments.get(i);
/* 15 */       itm.addUnsafeEnchantment(e, ((Integer)this.levels.get(i)).intValue());
/*    */     }
/*    */   }
/*    */ 
/*    */   public String dumpEnchant() {
/* 20 */     String str = ",";
/* 21 */     for (int i = 0; i < this.enchantments.size(); i++) {
/* 22 */       String add = "";
/* 23 */       String name = ((Enchantment)this.enchantments.get(i)).getName().toUpperCase();
/* 24 */       String level = Integer.toString(((Integer)this.levels.get(i)).intValue());
/* 25 */       if (i + 1 < this.enchantments.size())
/* 26 */         add = ",";
/* 27 */       str = str + "e:" + name + "*" + level + add;
/*    */     }
/* 29 */     return str;
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.arena.KitEnchantment
 * JD-Core Version:    0.6.2
 */