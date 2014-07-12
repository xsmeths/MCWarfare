/*     */ package com.orange451.mcwarfare;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.material.MaterialData;
/*     */ 
/*     */ public class InventoryHelper
/*     */ {
/*     */   public static int amtItem(Inventory inventory, int itemid)
/*     */   {
/*  11 */     int ret = 0;
/*  12 */     if (inventory != null) {
/*  13 */       ItemStack[] items = inventory.getContents();
/*  14 */       for (int slot = 0; slot < items.length; slot++) {
/*  15 */         if (items[slot] != null) {
/*  16 */           int id = items[slot].getTypeId();
/*  17 */           int amt = items[slot].getAmount();
/*  18 */           if (id == itemid) {
/*  19 */             ret += amt;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  24 */     return ret;
/*     */   }
/*     */ 
/*     */   public static void setItem(Inventory inventory, int itemid, int amt) {
/*  28 */     if (inventory != null) {
/*  29 */       ItemStack[] items = inventory.getContents();
/*  30 */       for (int slot = 0; slot < items.length; slot++)
/*  31 */         if (items[slot] != null) {
/*  32 */           int id = items[slot].getTypeId();
/*  33 */           if (id == itemid) {
/*  34 */             items[slot].setAmount(amt);
/*  35 */             return;
/*     */           }
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void removeItem(Inventory inventory, int itemid, int ret)
/*     */   {
/*  44 */     int start = ret;
/*  45 */     if (inventory != null) {
/*  46 */       ItemStack[] items = inventory.getContents();
/*  47 */       for (int slot = 0; slot < items.length; slot++)
/*  48 */         if (items[slot] != null) {
/*  49 */           int id = items[slot].getTypeId();
/*  50 */           int amt = items[slot].getAmount();
/*  51 */           if (id == itemid) {
/*  52 */             if (ret > 0) {
/*  53 */               if (amt >= ret) {
/*  54 */                 amt -= ret;
/*  55 */                 ret = 0;
/*     */               } else {
/*  57 */                 ret = start - amt;
/*  58 */                 amt = 0;
/*     */               }
/*  60 */               if (amt > 0)
/*  61 */                 inventory.setItem(slot, new ItemStack(id, amt));
/*     */               else {
/*  63 */                 inventory.setItem(slot, null);
/*     */               }
/*     */             }
/*  66 */             if (ret <= 0)
/*  67 */               return;
/*     */           }
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void removeItem(Inventory inventory, int slot1)
/*     */   {
/*  77 */     if (inventory != null) {
/*  78 */       ItemStack[] items = inventory.getContents();
/*  79 */       items[slot1].setAmount(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean isEmpty(Inventory inventory) {
/*  84 */     if (inventory != null) {
/*  85 */       ItemStack[] items = inventory.getContents();
/*  86 */       for (int slot = 0; slot < items.length; slot++) {
/*  87 */         if ((items[slot] != null) && 
/*  88 */           (items[slot].getTypeId() > 0)) {
/*  89 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/*  95 */       PlayerInventory inventory2 = (PlayerInventory)inventory;
/*  96 */       if (inventory2.getHelmet() != null)
/*  97 */         return false;
/*  98 */       if (inventory2.getChestplate() != null)
/*  99 */         return false;
/* 100 */       if (inventory2.getLeggings() != null)
/* 101 */         return false;
/* 102 */       if (inventory2.getBoots() != null)
/* 103 */         return false;
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean clearInventory(Inventory inventory) {
/* 111 */     inventory.clear();
/*     */     try {
/* 113 */       PlayerInventory inventory2 = (PlayerInventory)inventory;
/* 114 */       inventory2.setHelmet(null);
/* 115 */       inventory2.setChestplate(null);
/* 116 */       inventory2.setLeggings(null);
/* 117 */       inventory2.setBoots(null);
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/* 121 */     return true;
/*     */   }
/*     */ 
/*     */   public static int getItemPosition(Inventory inventory, ItemStack itm) {
/* 125 */     if (inventory != null) {
/* 126 */       ItemStack[] items = inventory.getContents();
/* 127 */       for (int slot = 0; slot < items.length; slot++) {
/* 128 */         if ((items[slot] != null) && 
/* 129 */           (items[slot].equals(itm))) {
/* 130 */           return slot;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 135 */     return -1;
/*     */   }
/*     */ 
/*     */   public static int getItemPosition(Inventory inventory, Material mat) {
/* 139 */     if (inventory != null) {
/* 140 */       ItemStack[] items = inventory.getContents();
/* 141 */       for (int slot = 0; slot < items.length; slot++) {
/* 142 */         if ((items[slot] != null) && 
/* 143 */           (items[slot].getTypeId() == mat.getId())) {
/* 144 */           return slot;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 149 */     return -1;
/*     */   }
/*     */ 
/*     */   public static ItemStack getFirstItemStack(Inventory inventory, Material mat) {
/* 153 */     if (inventory != null) {
/* 154 */       ItemStack[] items = inventory.getContents();
/* 155 */       for (int slot = 0; slot < items.length; slot++) {
/* 156 */         if ((items[slot] != null) && 
/* 157 */           (items[slot].getType().equals(mat))) {
/* 158 */           return items[slot];
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 163 */     return null;
/*     */   }
/*     */ 
/*     */   public static int getFirstFreeSlot(Inventory inventory) {
/* 167 */     if (inventory != null) {
/* 168 */       ItemStack[] items = inventory.getContents();
/* 169 */       for (int slot = 0; slot < items.length; slot++) {
/* 170 */         if (items[slot] != null) {
/* 171 */           if (items[slot].getTypeId() == 0)
/* 172 */             return slot;
/*     */         }
/*     */         else {
/* 175 */           return slot;
/*     */         }
/*     */       }
/*     */     }
/* 179 */     return -1;
/*     */   }
/*     */ 
/*     */   public static ItemStack getItemStackWithData(Material mat, byte teamcolor) {
/* 183 */     MaterialData data = new MaterialData(mat.getId());
/* 184 */     data.setData(teamcolor);
/* 185 */     ItemStack ret = data.toItemStack(1);
/* 186 */     return ret;
/*     */   }
/*     */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.InventoryHelper
 * JD-Core Version:    0.6.2
 */