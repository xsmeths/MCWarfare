/*    */ package com.orange451.mcwarfare.commands;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import com.orange451.mcwarfare.arena.KitItem;
/*    */ import com.orange451.mcwarfare.arena.KitPlayer;
/*    */ import com.orange451.mcwarfare.arena.KitProfile;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class PCommandBuy extends PBaseCommand
/*    */ {
/*    */   public PCommandBuy(KitPvP plugin)
/*    */   {
/* 14 */     this.plugin = plugin;
/* 15 */     this.aliases.add("buy");
/* 16 */     this.aliases.add("b");
/*    */ 
/* 18 */     this.desc = (ChatColor.YELLOW + "to buy MCWarfare items");
/*    */   }
/*    */ 
/*    */   public void perform()
/*    */   {
/* 23 */     KitPlayer kp = this.plugin.getKitPlayer(this.player);
/* 24 */     if (kp != null)
/*    */       try {
/* 26 */         String param = (String)this.parameters.get(1);
/*    */ 
/* 28 */         if (param.equals("list")) {
/* 29 */           listAvailableItems(this.player, kp);
/* 30 */           return;
/*    */         }
/*    */ 
/* 33 */         if (param.equals("grenade")) {
/* 34 */           int credits = kp.profile.credits;
/* 35 */           if (credits > 75) {
/* 36 */             kp.profile.credits -= 75;
/* 37 */             kp.boughtItems.add(new KitItem(new ItemStack(Material.SLIME_BALL, 3)));
/* 38 */             kp.sayMessage(null, ChatColor.GREEN + "BOUGHT ITEM: " + ChatColor.WHITE + " grenades");
/*    */           } else {
/* 40 */             kp.sayMessage(null, "NOT ENOUGH CREDITS!");
/*    */           }
/*    */         }
/*    */ 
/* 44 */         if (!param.equals("armor")) return;
/* 45 */         int credits = kp.profile.credits;
/* 46 */         if (credits > 100) {
/* 47 */           kp.profile.credits -= 100;
/* 48 */           kp.boughtItems.add(new KitItem(new ItemStack(Material.IRON_CHESTPLATE, 1)));
/* 49 */           kp.boughtItems.add(new KitItem(new ItemStack(Material.IRON_BOOTS, 1)));
/* 50 */           kp.sayMessage(null, ChatColor.GREEN + "BOUGHT ITEM: " + ChatColor.WHITE + " armor (iron chestplate/boots)");
/*    */         } else {
/* 52 */           kp.sayMessage(null, "NOT ENOUGH CREDITS!");
/*    */         }
/*    */       }
/*    */       catch (Exception e) {
/* 56 */         listAvailableItems(this.player, kp);
/*    */       }
/*    */     else
/* 59 */       this.player.sendMessage("type" + ChatColor.BLUE + " /war join" + ChatColor.WHITE + "first!");
/*    */   }
/*    */ 
/*    */   private void listAvailableItems(Player player, KitPlayer kp)
/*    */   {
/* 64 */     kp.sayMessage(null, ChatColor.GRAY + "------" + ChatColor.YELLOW + "MCWAR ITEMS" + ChatColor.GRAY + "-----");
/* 65 */     kp.sayMessage(null, ChatColor.DARK_GREEN + "grenades(3) " + ChatColor.YELLOW + "[75]");
/* 66 */     kp.sayMessage(null, ChatColor.DARK_GREEN + "armor  " + ChatColor.YELLOW + "[100]");
/* 67 */     kp.sayMessage(null, ChatColor.GRAY + "----------------------");
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.commands.PCommandBuy
 * JD-Core Version:    0.6.2
 */