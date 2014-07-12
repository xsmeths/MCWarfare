/*     */ package com.orange451.mcwarfare.arena;
/*     */ 
/*     */ import com.orange451.mcwarfare.KitPvP;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.material.MaterialData;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class KitFlag
/*     */ {
/*  15 */   public boolean isHolding = false;
/*     */   public KitFlagStand stand;
/*     */   public Item itemInWorld;
/*     */   public Item temp;
/*     */   public int teamColor;
/*     */   public Player carrier;
/*     */   public String flag;
/*     */ 
/*     */   public KitFlag(KitFlagStand stand, int color)
/*     */   {
/*  24 */     this.stand = stand;
/*  25 */     this.teamColor = color;
/*     */ 
/*  27 */     ChatColor c = ChatColor.BLUE;
/*  28 */     String name = "BLUE";
/*  29 */     if (this.teamColor == 14) {
/*  30 */       c = ChatColor.RED;
/*  31 */       name = "RED";
/*     */     }
/*  33 */     this.flag = (c + name);
/*     */   }
/*     */ 
/*     */   public void spawn(Location add) {
/*  37 */     MaterialData data = new MaterialData(Material.WOOL.getId());
/*  38 */     data.setData((byte)this.teamColor);
/*  39 */     ItemStack itm = data.toItemStack(1);
/*  40 */     this.itemInWorld = add.getWorld().dropItem(add.clone().add(0.5D, 0.0D, 0.5D), itm);
/*  41 */     this.itemInWorld.setVelocity(new Vector(0, 0, 0));
/*     */   }
/*     */ 
/*     */   public void tick() {
/*  45 */     if ((this.itemInWorld != null) && (!this.isHolding)) {
/*  46 */       this.itemInWorld.setTicksLived(64);
/*     */     }
/*  48 */     if (this.carrier != null) {
/*  49 */       KitPlayer k = this.stand.arena.plugin.getKitPlayer(this.carrier);
/*  50 */       if ((this.isHolding) && ((k == null) || (k.team.equals(KitArena.Teams.NEUTRAL)))) {
/*  51 */         drop();
/*     */       }
/*  53 */       if (this.carrier.isDead()) {
/*  54 */         drop();
/*     */       }
/*  56 */       else if (!this.isHolding) {
/*  57 */         Player c = this.carrier;
/*  58 */         removeFlag();
/*  59 */         this.carrier = c;
/*  60 */         this.isHolding = true;
/*  61 */         this.stand.arena.broadcastMessage(this.stand.arena.plugin.getKitPlayer(this.carrier).getTeamColor() + this.carrier.getName() + ChatColor.GOLD + " picked up the " + this.flag + ChatColor.GOLD + " flag!");
/*     */       } else {
/*  63 */         if (this.temp != null) {
/*  64 */           this.temp.remove();
/*  65 */           this.temp = null;
/*     */         }
/*  67 */         Location add = this.carrier.getLocation().add(0.0D, 1.5D, 0.0D);
/*  68 */         MaterialData data = new MaterialData(Material.WOOL.getId());
/*  69 */         data.setData((byte)this.teamColor);
/*  70 */         ItemStack itm = data.toItemStack(1);
/*  71 */         this.temp = add.getWorld().dropItem(add, itm);
/*  72 */         this.temp.setVelocity(new Vector(0.0D, 0.425D, 0.0D));
/*  73 */         this.temp.setPickupDelay(99999);
/*     */       }
/*     */ 
/*     */     }
/*  77 */     else if (this.isHolding) {
/*  78 */       drop();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void drop() {
/*  83 */     if (this.temp != null) {
/*  84 */       this.temp.remove();
/*  85 */       this.temp = null;
/*     */     }
/*  87 */     if (this.stand != null) {
/*  88 */       KitPlayer kitPlayer = this.stand.arena.plugin.getKitPlayer(this.carrier);
/*  89 */       if ((this.carrier != null) && (kitPlayer != null))
/*  90 */         this.stand.arena.broadcastMessage(kitPlayer.getTeamColor() + this.carrier.getName() + ChatColor.GOLD + " dropped the " + this.flag + ChatColor.GOLD + " flag!");
/*     */     }
/*  92 */     removeFlag();
/*  93 */     this.stand.setup();
/*     */   }
/*     */ 
/*     */   public void removeFlag() {
/*     */     try {
/*  98 */       this.itemInWorld.remove();
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/* 102 */     if (this.temp != null) {
/* 103 */       this.temp.remove();
/* 104 */       this.temp = null;
/*     */     }
/* 106 */     this.isHolding = false;
/* 107 */     this.carrier = null;
/*     */   }
/*     */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.arena.KitFlag
 * JD-Core Version:    0.6.2
 */