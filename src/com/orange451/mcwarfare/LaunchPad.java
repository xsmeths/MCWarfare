/*    */ package com.orange451.mcwarfare;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ import org.bukkit.potion.PotionEffectType;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public class LaunchPad
/*    */ {
/*    */   public Vector direction;
/*    */   public Block me;
/* 14 */   public double speed = 3.5D;
/*    */ 
/*    */   public LaunchPad(Block b, Vector direction) {
/* 17 */     this.me = b;
/* 18 */     this.direction = direction;
/*    */   }
/*    */ 
/*    */   public static LaunchPad getLaunchPad(Block b)
/*    */   {
/* 23 */     Block b1 = checkBlock(b, Material.SPONGE, 1, -1, 0);
/* 24 */     Block b2 = checkBlock(b, Material.SPONGE, -1, -1, 0);
/* 25 */     Block b3 = checkBlock(b, Material.SPONGE, 0, -1, 1);
/* 26 */     Block b4 = checkBlock(b, Material.SPONGE, 0, -1, -1);
/* 27 */     if (b1 != null)
/* 28 */       return new LaunchPad(b, getDirection(b, b1));
/* 29 */     if (b2 != null)
/* 30 */       return new LaunchPad(b, getDirection(b, b2));
/* 31 */     if (b3 != null)
/* 32 */       return new LaunchPad(b, getDirection(b, b3));
/* 33 */     if (b4 != null)
/* 34 */       return new LaunchPad(b, getDirection(b, b4));
/* 35 */     return null;
/*    */   }
/*    */ 
/*    */   private static Vector getDirection(Block b, Block b1)
/*    */   {
/* 40 */     double xdn = b.getLocation().getBlockX() - b1.getLocation().getBlockX();
/* 41 */     double ydn = b.getLocation().getBlockY() - b1.getLocation().getBlockY();
/* 42 */     double zdn = b.getLocation().getBlockZ() - b1.getLocation().getBlockZ();
/*    */ 
/* 44 */     double a = Math.sqrt(xdn * xdn + ydn * ydn + zdn * zdn);
/* 45 */     if (a > 0.0D) {
/* 46 */       xdn /= a;
/* 47 */       ydn /= a;
/* 48 */       zdn /= a;
/*    */     }
/* 50 */     return new Vector(xdn, ydn, zdn);
/*    */   }
/*    */ 
/*    */   private static Block checkBlock(Block b, Material mat, int i, int j, int k) {
/* 54 */     Block bb = b.getLocation().add(i, j, k).getBlock();
/* 55 */     if (bb.getType().equals(mat)) {
/* 56 */       return bb;
/*    */     }
/* 58 */     return null;
/*    */   }
/*    */ 
/*    */   public void launch(Player pl) {
/* 62 */     pl.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 4));
/* 63 */     Vector nv = new Vector(this.direction.getX() * this.speed, this.direction.getY() * (this.speed / 2.0D), this.direction.getZ() * this.speed);
/* 64 */     pl.setVelocity(nv);
/* 65 */     pl = null;
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.LaunchPad
 * JD-Core Version:    0.6.2
 */