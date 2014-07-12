/*    */ package com.orange451.mcwarfare.commands;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PCommandLeave extends PBaseCommand
/*    */ {
/*    */   public PCommandLeave(KitPvP plugin)
/*    */   {
/*  9 */     this.plugin = plugin;
/* 10 */     this.aliases.add("leave");
/* 11 */     this.aliases.add("l");
/*    */ 
/* 13 */     this.desc = (ChatColor.YELLOW + "to leave MCWARFARE");
/*    */   }
/*    */ 
/*    */   public void perform()
/*    */   {
/* 18 */     if (this.plugin.isInArena(this.player))
/* 19 */       this.plugin.leaveArena(this.plugin.getKitPlayer(this.player));
/*    */     else
/* 21 */       this.player.sendMessage("Honestly... fuck you");
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.commands.PCommandLeave
 * JD-Core Version:    0.6.2
 */