/*    */ package com.orange451.mcwarfare.commands;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import com.orange451.mcwarfare.arena.KitPlayer;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PCommandHelp extends PBaseCommand
/*    */ {
/*    */   public PCommandHelp(KitPvP plugin)
/*    */   {
/* 11 */     this.plugin = plugin;
/* 12 */     this.aliases.add("help");
/* 13 */     this.aliases.add("h");
/* 14 */     this.aliases.add("?");
/*    */ 
/* 16 */     this.desc = (ChatColor.YELLOW + "to view MCWARFARE help");
/*    */   }
/*    */ 
/*    */   public void perform()
/*    */   {
/* 21 */     KitPlayer kp = this.plugin.getKitPlayer(this.player);
/* 22 */     if (kp == null) {
/* 23 */       this.player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "PLEASE JOIN THE GAME FIRST! type:" + ChatColor.GOLD + "/war join");
/*    */     } else {
/* 25 */       kp.sayMessage(null, ChatColor.YELLOW + "/war buy " + ChatColor.WHITE + "to access buyable items");
/* 26 */       kp.sayMessage(null, ChatColor.YELLOW + "/war chat " + ChatColor.WHITE + "to turn chat on/off");
/* 27 */       kp.sayMessage(null, ChatColor.YELLOW + "/war gui " + ChatColor.WHITE + "to turn GUI on/off");
/* 28 */       kp.sayMessage(null, ChatColor.YELLOW + "/war gun " + ChatColor.WHITE + "to list guns you can buy");
/* 29 */       kp.sayMessage(null, ChatColor.YELLOW + "/war list " + ChatColor.WHITE + "to list guns you have");
/* 30 */       kp.sayMessage(null, ChatColor.YELLOW + "/war leave " + ChatColor.WHITE + "to leave the war");
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.commands.PCommandHelp
 * JD-Core Version:    0.6.2
 */