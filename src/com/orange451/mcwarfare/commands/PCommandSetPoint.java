/*    */ package com.orange451.mcwarfare.commands;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import com.orange451.mcwarfare.arena.create.KitArenaCreator;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PCommandSetPoint extends PBaseCommand
/*    */ {
/*    */   public PCommandSetPoint(KitPvP plugin)
/*    */   {
/* 10 */     this.plugin = plugin;
/* 11 */     this.aliases.add("setpoint");
/* 12 */     this.aliases.add("sp");
/*    */ 
/* 14 */     this.mode = "hidden";
/*    */ 
/* 16 */     this.desc = (ChatColor.YELLOW + "to set a MCWARFARE point");
/*    */   }
/*    */ 
/*    */   public void perform()
/*    */   {
/* 21 */     KitArenaCreator kac = this.plugin.getKitArenaCreator(this.player);
/* 22 */     if (kac == null)
/* 23 */       this.player.sendMessage("You need to be creating a kit arena!");
/*    */     else
/* 25 */       this.plugin.getKitArenaCreator(this.player).setPoint();
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.commands.PCommandSetPoint
 * JD-Core Version:    0.6.2
 */