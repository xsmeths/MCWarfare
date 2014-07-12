/*    */ package com.orange451.mcwarfare.commands;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import com.orange451.mcwarfare.arena.KitPlayer;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PCommandChat extends PBaseCommand
/*    */ {
/*    */   public PCommandChat(KitPvP plugin)
/*    */   {
/*  9 */     this.plugin = plugin;
/* 10 */     this.aliases.add("chat");
/*    */ 
/* 12 */     this.desc = (ChatColor.YELLOW + "to turn off/on your chat");
/*    */   }
/*    */ 
/*    */   public void perform()
/*    */   {
/* 17 */     KitPlayer kp = this.plugin.getKitPlayer(this.player);
/* 18 */     if (kp != null) {
/* 19 */       kp.receiveChat = (!kp.receiveChat);
/* 20 */       kp.clearChat();
/* 21 */       kp.setChat();
/* 22 */       kp.sayMessage(null, ChatColor.GREEN + "Chat is on?  " + Boolean.toString(kp.receiveChat));
/*    */     } else {
/* 24 */       this.player.sendMessage("type" + ChatColor.BLUE + " /war join" + ChatColor.WHITE + "first!");
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.commands.PCommandChat
 * JD-Core Version:    0.6.2
 */