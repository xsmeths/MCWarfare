/*    */ package com.orange451.mcwarfare;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class VoteForServer
/*    */ {
/*    */   public VoteForServer(Player pl)
/*    */   {
/* 10 */     this(pl.getName());
/* 11 */     pl = null;
/*    */   }
/*    */ 
/*    */   public VoteForServer(String str) {
/* 15 */     String path = KitPvP.getFTP() + "/voted/" + str;
/* 16 */     BufferedWriter wr = FileIO.file_text_open_write(path);
/* 17 */     FileIO.file_text_write_line(wr, "grenade");
/* 18 */     FileIO.file_text_close(wr);
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.VoteForServer
 * JD-Core Version:    0.6.2
 */