/*    */ package com.orange451.mcwarfare.commands;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import com.orange451.mcwarfare.arena.create.KitArenaCreator;
/*    */ import com.orange451.mcwarfare.permissions.PermissionInterface;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PCommandCreate extends PBaseCommand
/*    */ {
/*    */   public PCommandCreate(KitPvP plugin)
/*    */   {
/* 11 */     this.plugin = plugin;
/* 12 */     this.aliases.add("create");
/*    */ 
/* 14 */     this.mode = "build";
/*    */ 
/* 16 */     this.desc = (ChatColor.YELLOW + "to create a MCWARFARE arena");
/*    */   }
/*    */ 
/*    */   public void perform()
/*    */   {
/* 21 */     if (PermissionInterface.hasPermission(this.player, "kitpvp.admin")) {
/* 22 */       String arenaName = (String)this.parameters.get(1);
/* 23 */       String arenaType = (String)this.parameters.get(2);
/* 24 */       if (this.plugin.getKitArena(arenaName) == null) {
/* 25 */         KitArenaCreator kac = this.plugin.getKitArenaCreator(this.player);
/* 26 */         if (kac == null)
/* 27 */           this.plugin.creatingArena.add(new KitArenaCreator(this.plugin, this.player, arenaName, arenaType));
/*    */         else
/* 29 */           this.player.sendMessage("You are already creating an arena!");
/*    */       }
/*    */       else {
/* 32 */         this.player.sendMessage("A Kit Arena with this name already exists!");
/*    */       }
/*    */     } else {
/* 35 */       this.player.sendMessage("You need perms brah!");
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.commands.PCommandCreate
 * JD-Core Version:    0.6.2
 */