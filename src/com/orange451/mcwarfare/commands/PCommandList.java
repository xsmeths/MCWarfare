/*    */ package com.orange451.mcwarfare.commands;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import com.orange451.mcwarfare.arena.KitGun;
/*    */ import com.orange451.mcwarfare.arena.KitPlayer;
/*    */ import com.orange451.mcwarfare.arena.KitProfile;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PCommandList extends PBaseCommand
/*    */ {
/*    */   public PCommandList(KitPvP plugin)
/*    */   {
/*  9 */     this.plugin = plugin;
/* 10 */     this.aliases.add("list");
/* 11 */     this.aliases.add("li");
/*    */ 
/* 13 */     this.desc = (ChatColor.YELLOW + "to view your MCWARFARE guns");
/*    */   }
/*    */ 
/*    */   public void perform()
/*    */   {
/* 18 */     KitPlayer kp = this.plugin.getKitPlayer(this.player);
/* 19 */     if (kp != null) {
/* 20 */       kp.sayMessage(null, ChatColor.GRAY + "------" + ChatColor.YELLOW + "MCWAR GUNS" + ChatColor.GRAY + "------");
/* 21 */       String str = ChatColor.BLUE + "Listing your Guns: ";
/* 22 */       kp.sayMessage(null, str);
/* 23 */       str = "";
/* 24 */       for (int i = 0; i < this.plugin.loadedGuns.size(); i++) {
/* 25 */         String g = ((KitGun)this.plugin.loadedGuns.get(i)).name;
/* 26 */         boolean has = kp.profile.hasGun(g);
/* 27 */         if (has) {
/* 28 */           ChatColor color = ChatColor.GREEN;
/* 29 */           String send = color + g;
/* 30 */           if (str.length() + send.length() > 42) {
/* 31 */             kp.sayMessage(null, str);
/* 32 */             str = send + " ";
/*    */           } else {
/* 34 */             str = str + send + " ";
/*    */           }
/*    */         }
/*    */       }
/*    */ 
/* 39 */       if (str.length() > 0) {
/* 40 */         kp.sayMessage(null, str);
/*    */       }
/*    */ 
/* 43 */       kp.sayMessage(null, ChatColor.GRAY + "----------------------");
/*    */     } else {
/* 45 */       this.player.sendMessage("you are not in the war!");
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.commands.PCommandList
 * JD-Core Version:    0.6.2
 */