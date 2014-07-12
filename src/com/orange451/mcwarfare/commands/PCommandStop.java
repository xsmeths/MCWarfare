/*    */ package com.orange451.mcwarfare.commands;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import com.orange451.mcwarfare.arena.KitArena;
/*    */ import com.orange451.mcwarfare.permissions.PermissionInterface;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PCommandStop extends PBaseCommand
/*    */ {
/*    */   public PCommandStop(KitPvP plugin)
/*    */   {
/* 10 */     this.plugin = plugin;
/* 11 */     this.aliases.add("stop");
/*    */ 
/* 13 */     this.mode = "hidden";
/*    */ 
/* 15 */     this.desc = (ChatColor.YELLOW + "to stop MCWARFARE");
/*    */   }
/*    */ 
/*    */   public void perform()
/*    */   {
/* 20 */     if (PermissionInterface.hasPermission(this.player, "kitpvp.admin")) {
/* 21 */       for (int i = this.plugin.activeArena.size() - 1; i >= 0; i--)
/*    */         try {
/* 23 */           ((KitArena)this.plugin.activeArena.get(i)).stop();
/*    */         }
/*    */         catch (Exception localException)
/*    */         {
/*    */         }
/* 28 */       this.plugin.stopped = true;
/*    */     } else {
/* 30 */       this.player.sendMessage("You need perms brah!");
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.commands.PCommandStop
 * JD-Core Version:    0.6.2
 */