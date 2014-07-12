/*    */ package com.orange451.mcwarfare.permissions;
/*    */ 
/*    */ import com.orange451.mcwarfare.FileIO;
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.BufferedWriter;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class PermissionPlayer
/*    */ {
/*    */   public String name;
/* 12 */   public ArrayList<String> nodes = new ArrayList();
/*    */ 
/*    */   public PermissionPlayer(String name, String path) {
/* 15 */     this.name = name;
/* 16 */     BufferedReader in = FileIO.file_text_open_read(path);
/* 17 */     boolean can = true;
/* 18 */     while (can) {
/* 19 */       String str = FileIO.file_text_read_line(in);
/* 20 */       if (str == null)
/* 21 */         can = false;
/*    */       else {
/* 23 */         this.nodes.add(str);
/*    */       }
/*    */     }
/* 26 */     FileIO.file_text_close(in);
/* 27 */     in = null;
/*    */   }
/*    */ 
/*    */   public PermissionPlayer(String name) {
/* 31 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public boolean has(String node) {
/* 35 */     for (int i = 0; i < this.nodes.size(); i++) {
/* 36 */       if (((String)this.nodes.get(i)).equals(node)) {
/* 37 */         return true;
/*    */       }
/*    */     }
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */   public void save() {
/* 44 */     if (this.name == null)
/* 45 */       return;
/* 46 */     String path = KitPvP.getMcWar() + "/perms/" + this.name;
/* 47 */     BufferedWriter in = null;
/*    */     try {
/* 49 */       in = FileIO.file_text_open_write(path);
/* 50 */       for (int i = 0; i < this.nodes.size(); i++)
/* 51 */         FileIO.file_text_write_line(in, (String)this.nodes.get(i));
/*    */     }
/*    */     catch (Exception e) {
/* 54 */       e.printStackTrace();
/*    */     } finally {
/* 56 */       FileIO.file_text_close(in);
/*    */     }
/* 58 */     in = null;
/*    */   }
/*    */ 
/*    */   public void give(String node) {
/* 62 */     this.nodes.add(node);
/*    */   }
/*    */ 
/*    */   public void remove(String node) {
/* 66 */     this.nodes.remove(node);
/*    */   }
/*    */ 
/*    */   public void dispose() {
/* 70 */     this.nodes.clear();
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.permissions.PermissionPlayer
 * JD-Core Version:    0.6.2
 */