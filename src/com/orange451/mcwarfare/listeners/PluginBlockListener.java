/*    */ package com.orange451.mcwarfare.listeners;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import com.orange451.mcwarfare.permissions.PermissionInterface;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.block.BlockBreakEvent;
/*    */ import org.bukkit.event.block.BlockPlaceEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class PluginBlockListener
/*    */   implements Listener
/*    */ {
/*    */   KitPvP plugin;
/*    */ 
/*    */   public PluginBlockListener(KitPvP plugin)
/*    */   {
/* 18 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   @EventHandler(priority=EventPriority.HIGHEST)
/*    */   public void onBlockBreak(BlockBreakEvent event) {
/* 23 */     if (!event.isCancelled()) {
/* 24 */       Player player = event.getPlayer();
/* 25 */       if ((player != null) && 
/* 26 */         (!PermissionInterface.hasPermission(player, "mcwar.admin")))
/* 27 */         event.setCancelled(true);
/*    */     }
/*    */   }
/*    */ 
/*    */   @EventHandler(priority=EventPriority.HIGHEST)
/*    */   public void onBlockPlace(BlockPlaceEvent event)
/*    */   {
/*    */     try
/*    */     {
/* 36 */       Player player = event.getPlayer();
/* 37 */       if ((player != null) && 
/* 38 */         (!PermissionInterface.hasPermission(player, "mcwar.admin"))) {
/* 39 */         ItemStack itm = event.getItemInHand();
/* 40 */         if ((itm != null) && (
/* 41 */           (itm.getTypeId() < 256) || (itm.getTypeId() == 397))) {
/* 42 */           event.setCancelled(true);
/* 43 */           event.getPlayer().damage(9999);
/* 44 */           this.plugin.giveMessage(event.getPlayer(), ChatColor.RED + "NO BLOCK JUMPING");
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.listeners.PluginBlockListener
 * JD-Core Version:    0.6.2
 */