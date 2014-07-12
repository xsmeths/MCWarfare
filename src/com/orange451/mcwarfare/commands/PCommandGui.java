/*    */ package com.orange451.mcwarfare.commands;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import com.orange451.mcwarfare.arena.KitPlayer;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PCommandGui extends PBaseCommand
/*    */ {
/*    */   public PCommandGui(KitPvP plugin)
/*    */   {
/*  9 */     this.plugin = plugin;
/* 10 */     this.aliases.add("gui");
/*    */ 
/* 12 */     this.desc = (ChatColor.YELLOW + "to turn off/on your display GUI");
/*    */   }
/*    */ 
/*    */   public void perform()
/*    */   {
/* 17 */     KitPlayer kp = this.plugin.getKitPlayer(this.player);
/* 18 */     if (kp != null) {
/* 19 */       kp.displayGUI = (!kp.displayGUI);
/* 20 */       kp.setChat();
/*    */     } else {
/* 22 */       this.player.sendMessage("type" + ChatColor.BLUE + " /war join" + ChatColor.WHITE + "first!");
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.commands.PCommandGui
 * JD-Core Version:    0.6.2
 */