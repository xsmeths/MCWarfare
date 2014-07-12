/*    */ package com.orange451.mcwarfare.commands;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PCommandJoin extends PBaseCommand
/*    */ {
/*    */   public PCommandJoin(KitPvP plugin)
/*    */   {
/*  8 */     this.plugin = plugin;
/*  9 */     this.aliases.add("join");
/* 10 */     this.aliases.add("j");
/*    */ 
/* 12 */     this.desc = (ChatColor.YELLOW + "to join MCWARFARE");
/*    */   }
/*    */ 
/*    */   public void perform()
/*    */   {
/* 17 */     if (this.plugin.isInArena(this.player))
/* 18 */       this.player.sendMessage("You're already in MCWARFARE...");
/*    */     else
/* 20 */       this.plugin.joinArena(this.player, null);
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.commands.PCommandJoin
 * JD-Core Version:    0.6.2
 */