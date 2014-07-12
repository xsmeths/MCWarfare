/*    */ package com.orange451.mcwarfare;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.FileReader;
/*    */ import java.io.FileWriter;
/*    */ 
/*    */ public class FileIO
/*    */ {
/*    */   public static BufferedReader file_text_open_read(String str)
/*    */   {
/*    */     try
/*    */     {
/* 11 */       return new BufferedReader(new FileReader(str)); } catch (Exception e) {
/*    */     }
/* 13 */     return null;
/*    */   }
/*    */ 
/*    */   public static String file_text_read_line(BufferedReader out)
/*    */   {
/*    */     try {
/* 19 */       return out.readLine();
/*    */     }
/*    */     catch (Exception localException) {
/*    */     }
/* 23 */     return null;
/*    */   }
/*    */ 
/*    */   public static BufferedWriter file_text_open_write(String str) {
/*    */     try {
/* 28 */       return new BufferedWriter(new FileWriter(str)); } catch (Exception e) {
/*    */     }
/* 30 */     return null;
/*    */   }
/*    */ 
/*    */   public static boolean file_text_write_line(BufferedWriter out, String str)
/*    */   {
/*    */     try {
/* 36 */       out.write(str);
/* 37 */       out.newLine();
/*    */     } catch (Exception e) {
/* 39 */       return false;
/*    */     }
/* 41 */     return true;
/*    */   }
/*    */ 
/*    */   public static void file_text_close(BufferedWriter out) {
/*    */     try {
/* 46 */       out.flush();
/*    */     }
/*    */     catch (Exception localException1) {
/*    */     }
/*    */     try {
/* 51 */       out.close();
/*    */     } catch (Exception e) {
/* 53 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void file_text_close(BufferedReader out) {
/*    */     try {
/* 59 */       out.close();
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.FileIO
 * JD-Core Version:    0.6.2
 */