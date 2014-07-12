/*    */ package com.orange451.mcwarfare.listeners;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import com.orange451.mcwarfare.arena.KilledPlayer;
/*    */ import com.orange451.mcwarfare.arena.KitArena;
/*    */ import com.orange451.mcwarfare.arena.KitPlayer;
/*    */ import java.util.List;
/*    */ import org.bukkit.entity.Arrow;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
/*    */ import org.bukkit.event.entity.EntityDeathEvent;
/*    */ import org.bukkit.event.entity.EntityExplodeEvent;
/*    */ 
/*    */ public class PluginEntityListener
/*    */   implements Listener
/*    */ {
/*    */   KitPvP plugin;
/*    */ 
/*    */   public PluginEntityListener(KitPvP plugin)
/*    */   {
/* 23 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   @EventHandler(priority=EventPriority.HIGH)
/*    */   public void onEntityExplode(EntityExplodeEvent event) {
/* 28 */     if ((this.plugin.isInArena(event.getLocation())) && 
/* 29 */       (event.blockList().size() > 1))
/* 30 */       event.setCancelled(true);
/*    */   }
/*    */ 
/*    */   @EventHandler(priority=EventPriority.HIGHEST)
/*    */   public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event)
/*    */   {
/*    */     try
/*    */     {
/* 38 */       Player defender = (Player)event.getEntity();
/* 39 */       if (!(event.getEntity() instanceof Player)) {
/* 40 */         return;
/*    */       }
/* 42 */       Entity att = event.getDamager();
/* 43 */       if (((att instanceof Player)) || ((att instanceof Arrow))) {
/* 44 */         Player attacker = null;
/* 45 */         if ((event.getDamager() instanceof Arrow)) {
/* 46 */           Arrow arrow = (Arrow)att;
/* 47 */           attacker = (Player)arrow.getShooter();
/*    */         } else {
/* 49 */           attacker = (Player)att;
/*    */         }
/*    */ 
/* 52 */         if (attacker != null) {
/* 53 */           boolean canDamage = this.plugin.canDamagePlayer(attacker, defender);
/* 54 */           event.setCancelled(!canDamage);
/*    */           try {
/* 56 */             KitPlayer shootKP = this.plugin.getKitPlayer(attacker);
/* 57 */             KitPlayer defendKP = this.plugin.getKitPlayer(defender);
/* 58 */             if ((!event.isCancelled()) && (event.getDamage() < 1000))
/* 59 */               shootKP.arena.onDamage(event, shootKP, defendKP);
/*    */           }
/*    */           catch (Exception localException)
/*    */           {
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Exception localException1)
/*    */     {
/*    */     }
/*    */   }
/*    */ 
/*    */   @EventHandler(priority=EventPriority.HIGHEST)
/*    */   public void onEntityDeath(EntityDeathEvent event) {
/*    */     try {
/* 75 */       Entity died = event.getEntity();
/* 76 */       if ((died instanceof Player)) {
/* 77 */         Player pdied = (Player)died;
/* 78 */         if (this.plugin.isInArena(pdied)) {
/* 79 */           event.getDrops().clear();
/*    */ 
/* 81 */           EntityDamageEvent dev = event.getEntity().getLastDamageCause();
/* 82 */           if ((dev != null) && 
/* 83 */             (dev.getEntity() != null) && 
/* 84 */             (dev.getCause() != null)) {
/* 85 */             EntityDamageEvent.DamageCause dc = dev.getCause();
/*    */ 
/* 87 */             if (dc.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
/* 88 */               Entity damager = ((EntityDamageByEntityEvent)dev).getDamager();
/* 89 */               if ((damager instanceof Player)) {
/* 90 */                 Player killer = (Player)damager;
/* 91 */                 new KilledPlayer(this.plugin, killer, pdied);
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.listeners.PluginEntityListener
 * JD-Core Version:    0.6.2
 */