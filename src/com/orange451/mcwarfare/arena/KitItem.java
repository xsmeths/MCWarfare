/*    */ package com.orange451.mcwarfare.arena;
/*    */ 
/*    */ import com.orange451.mcwarfare.InventoryHelper;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.PlayerInventory;
/*    */ 
/*    */ public class KitItem
/*    */ {
/*    */   public ItemStack itm;
/*  9 */   public String tag = "";
/*    */ 
/*    */   public KitItem() {
/*    */   }
/*    */ 
/*    */   public KitItem(ItemStack itm) {
/* 15 */     this.itm = itm;
/*    */   }
/*    */ 
/*    */   public void give(KitPlayer p) {
/* 19 */     if (this.itm == null)
/* 20 */       return;
/* 21 */     String item = this.itm.getType().toString().toLowerCase();
/* 22 */     if (item.contains("chestplate")) {
/* 23 */       p.player.getInventory().setChestplate(this.itm);
/* 24 */       return;
/*    */     }
/* 26 */     if (item.contains("boots")) {
/* 27 */       p.player.getInventory().setBoots(this.itm);
/* 28 */       return;
/*    */     }
/* 30 */     int slot = InventoryHelper.getFirstFreeSlot(p.player.getInventory());
/* 31 */     if (slot > -1) {
/* 32 */       p.player.getInventory().setItem(slot, this.itm.clone());
/* 33 */       return;
/*    */     }
/*    */   }
/*    */ 
/*    */   public KitItem setTag(String string) {
/* 38 */     this.tag = string;
/* 39 */     return this;
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.arena.KitItem
 * JD-Core Version:    0.6.2
 */