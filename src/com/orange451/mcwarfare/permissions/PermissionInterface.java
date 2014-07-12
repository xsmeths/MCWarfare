/*    */ package com.orange451.mcwarfare.permissions;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import java.io.File;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PermissionInterface
/*    */ {
/*    */   public static void Initialize()
/*    */   {
/* 12 */     String path = KitPvP.getFTP() + "/perms";
/* 13 */     File t = new File(path);
/* 14 */     if (!t.exists())
/* 15 */       t.mkdir();
/*    */   }
/*    */ 
/*    */   public static boolean hasPermission(Player p, String node)
/*    */   {
/* 20 */     PermissionPlayer pp = getPermissions(p);
/* 21 */     if ((pp.has(node)) || (p.isOp())) {
/* 22 */       pp.dispose();
/* 23 */       pp = null;
/* 24 */       return true;
/*    */     }
/* 26 */     pp = null;
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   public static void addPermission(String p, String node) {
/* 31 */     PermissionPlayer pp = getPermissions(p);
/* 32 */     pp.give(node);
/* 33 */     pp.save();
/* 34 */     pp.dispose();
/* 35 */     pp = null;
/*    */   }
/*    */ 
/*    */   public static void removePermission(String p, String node) {
/* 39 */     PermissionPlayer pp = getPermissions(p);
/* 40 */     pp.remove(node);
/* 41 */     pp.save();
/* 42 */     pp.dispose();
/* 43 */     pp = null;
/*    */   }
/*    */ 
/*    */   public static PermissionPlayer getPermissions(Player p) {
/* 47 */     return getPermissions(p.getName());
/*    */   }
/*    */ 
/*    */   public static PermissionPlayer getPermissions(String p) {
/* 51 */     String path = KitPvP.getFTP() + "/perms/" + p;
/* 52 */     File t = new File(path);
/* 53 */     if (t.exists()) {
/* 54 */       return new PermissionPlayer(p, path);
/*    */     }
/* 56 */     return new PermissionPlayer(p);
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.permissions.PermissionInterface
 * JD-Core Version:    0.6.2
 */