/*    */ package com.orange451.mcwarfare.commands;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import com.orange451.mcwarfare.permissions.PermissionInterface;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PCommandReload extends PBaseCommand
/*    */ {
/*    */   public PCommandReload(KitPvP plugin)
/*    */   {
/* 10 */     this.plugin = plugin;
/* 11 */     this.aliases.add("reload");
/* 12 */     this.aliases.add("r");
/*    */ 
/* 14 */     this.mode = "hidden";
/*    */ 
/* 16 */     this.desc = (ChatColor.YELLOW + "to reload");
/*    */   }
/*    */ 
/*    */   public void perform()
/*    */   {
/* 21 */     if (PermissionInterface.hasPermission(this.player, "kitpvp.admin")) {
/* 22 */       this.plugin.onDisable();
/* 23 */       this.plugin.onEnable();
/*    */     } else {
/* 25 */       this.player.sendMessage("You need perms brah!");
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.commands.PCommandReload
 * JD-Core Version:    0.6.2
 */