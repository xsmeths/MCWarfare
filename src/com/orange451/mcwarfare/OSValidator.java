/*    */ package com.orange451.mcwarfare;
/*    */ 
/*    */ public class OSValidator
/*    */ {
/*  5 */   private static String OS = System.getProperty("os.name").toLowerCase();
/*    */ 
/*    */   public static boolean isWindows()
/*    */   {
/*  9 */     return OS.indexOf("win") >= 0;
/*    */   }
/*    */ 
/*    */   public static boolean isMac()
/*    */   {
/* 15 */     return OS.indexOf("mac") >= 0;
/*    */   }
/*    */ 
/*    */   public static boolean isUnix()
/*    */   {
/* 21 */     return (OS.indexOf("nix") >= 0) || (OS.indexOf("nux") >= 0) || (OS.indexOf("aix") > 0);
/*    */   }
/*    */ 
/*    */   public static boolean isSolaris()
/*    */   {
/* 27 */     return OS.indexOf("sunos") >= 0;
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.OSValidator
 * JD-Core Version:    0.6.2
 */