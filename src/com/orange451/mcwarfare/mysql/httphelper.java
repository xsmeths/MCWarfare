/*    */ package com.orange451.mcwarfare.mysql;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ 
/*    */ public class httphelper
/*    */ {
/*    */   public static boolean send_stats(String send)
/*    */   {
/*    */     try
/*    */     {
/* 11 */       String url = "http://globalanarchy.net84.net/MCWar/updateStats.php/?msg=" + send;
/* 12 */       URL myurl = new URL(url);
/* 13 */       URLConnection yc = myurl.openConnection();
/* 14 */       BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
/* 15 */       String answer = in.readLine();
/* 16 */       in.close();
/* 17 */       if (answer.contains("true"))
/* 18 */         return true;
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/* 23 */     return false;
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.mysql.httphelper
 * JD-Core Version:    0.6.2
 */