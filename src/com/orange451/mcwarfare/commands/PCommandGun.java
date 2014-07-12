/*     */ package com.orange451.mcwarfare.commands;
/*     */ 
/*     */ import com.orange451.mcwarfare.KitPvP;
/*     */ import com.orange451.mcwarfare.arena.KitArena;
/*     */ import com.orange451.mcwarfare.arena.KitGun;
/*     */ import com.orange451.mcwarfare.arena.KitPlayer;
/*     */ import com.orange451.mcwarfare.arena.KitProfile;
/*     */ import com.orange451.mcwarfare.arena.kits.KitClass;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class PCommandGun extends PBaseCommand
/*     */ {
/*     */   public PCommandGun(KitPvP plugin)
/*     */   {
/*  12 */     this.plugin = plugin;
/*  13 */     this.aliases.add("gun");
/*  14 */     this.aliases.add("g");
/*     */ 
/*  16 */     this.desc = (ChatColor.YELLOW + "to buy/apply/view MCWarfare guns");
/*     */   }
/*     */ 
/*     */   public void perform()
/*     */   {
/*  21 */     KitPlayer kp = this.plugin.getKitPlayer(this.player);
/*  22 */     if (kp != null) {
/*     */       try {
/*  24 */         String param = (String)this.parameters.get(1);
/*     */ 
/*  26 */         if (param.equals("list")) {
/*  27 */           listAvailableGuns(this.player, kp);
/*  28 */           return;
/*     */         }
/*     */ 
/*  31 */         if (param.equals("buy")) {
/*  32 */           String param2 = (String)this.parameters.get(2);
/*  33 */           KitGun kg = this.plugin.getGun(param2);
/*  34 */           if (kg != null) {
/*  35 */             if (!kp.profile.hasGun(param2)) {
/*  36 */               int cost = kg.cost;
/*  37 */               if (kg.isUnlocked(kp)) {
/*  38 */                 if (kp.profile.credits >= cost) {
/*  39 */                   kp.profile.credits -= cost;
/*  40 */                   kp.sayMessage(null, "bought: " + ChatColor.YELLOW + param2);
/*  41 */                   kp.profile.boughtGuns.add(param2);
/*  42 */                   kp.profile.save(kp);
/*  43 */                   return;
/*     */                 }
/*  45 */                 kp.sayMessage(null, "This gun costs: " + ChatColor.YELLOW + cost + ChatColor.WHITE + " credits!");
/*  46 */                 return;
/*     */               }
/*     */ 
/*  49 */               kp.sayMessage(null, "You cannot buy this gun yet!");
/*  50 */               return;
/*     */             }
/*     */ 
/*  53 */             kp.sayMessage(null, "You already have this gun!");
/*  54 */             return;
/*     */           }
/*     */ 
/*  57 */           kp.sayMessage(null, "This gun is not available at this time!");
/*  58 */           return;
/*     */         }
/*     */ 
/*  62 */         if (kp.arena.type.equals("lobby")) {
/*  63 */           if (param.equals("apply")) {
/*  64 */             String param2 = (String)this.parameters.get(2);
/*  65 */             KitGun kg = this.plugin.getGun(param2);
/*  66 */             if (kp.profile.hasGun(param2)) {
/*  67 */               if (kg.slot.equals("primary")) {
/*  68 */                 ((KitClass)kp.profile.classes.get(kp.profile.myclass)).primary = param2;
/*  69 */                 ((KitClass)kp.profile.classes.get(kp.profile.myclass)).update();
/*  70 */                 kp.sayMessage(null, "set primary to: " + ChatColor.YELLOW + param2);
/*     */               }
/*     */ 
/*  73 */               if (kg.slot.equals("secondary")) {
/*  74 */                 ((KitClass)kp.profile.classes.get(kp.profile.myclass)).secondary = param2;
/*  75 */                 ((KitClass)kp.profile.classes.get(kp.profile.myclass)).update();
/*  76 */                 kp.sayMessage(null, "set secondary to: " + ChatColor.YELLOW + param2);
/*     */               }
/*     */             } else {
/*  79 */               kp.sayMessage(null, "You dont have this gun!");
/*     */             }
/*     */           }
/*     */         }
/*  83 */         else kp.sayMessage(null, "You need to be in a lobby to do this!");
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  87 */         listAvailableGuns(this.player, kp);
/*     */       }
/*     */     }
/*     */     else
/*  91 */       this.player.sendMessage("type" + ChatColor.BLUE + " /war join" + ChatColor.WHITE + "first!");
/*     */   }
/*     */ 
/*     */   private void listAvailableGuns(Player player, KitPlayer kp)
/*     */   {
/*  96 */     kp.sayMessage(null, ChatColor.GRAY + "------" + ChatColor.YELLOW + "MCWAR GUNS" + ChatColor.GRAY + "------");
/*  97 */     String str = ChatColor.BLUE + "Listing available Guns: ";
/*  98 */     kp.sayMessage(null, str);
/*  99 */     str = "";
/* 100 */     for (int i = 0; i < this.plugin.loadedGuns.size(); i++) {
/* 101 */       String g = ((KitGun)this.plugin.loadedGuns.get(i)).name;
/* 102 */       boolean has = kp.profile.hasGun(g);
/* 103 */       if ((!has) && (this.plugin.getGun(g).isUnlocked(kp))) {
/* 104 */         ChatColor color = ChatColor.WHITE;
/* 105 */         String send = ChatColor.YELLOW + "[" + ((KitGun)this.plugin.loadedGuns.get(i)).cost + "]" + color + g;
/* 106 */         if (str.length() + send.length() > 42) {
/* 107 */           kp.sayMessage(null, str);
/* 108 */           str = send + " ";
/*     */         } else {
/* 110 */           str = str + send + " ";
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 115 */     if (str.length() > 0) {
/* 116 */       kp.sayMessage(null, str);
/*     */     }
/*     */ 
/* 119 */     kp.sayMessage(null, ChatColor.GRAY + "----------------------");
/*     */   }
/*     */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.commands.PCommandGun
 * JD-Core Version:    0.6.2
 */