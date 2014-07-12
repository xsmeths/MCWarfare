/*    */ package com.orange451.mcwarfare.commands;
/*    */ 
/*    */ import com.orange451.mcwarfare.KitPvP;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PBaseCommand
/*    */ {
/*    */   public List<String> aliases;
/*    */   public CommandSender sender;
/*    */   public Player player;
/*    */   public String desc;
/*    */   public List<String> parameters;
/*    */   public KitPvP plugin;
/* 18 */   public String mode = "";
/*    */ 
/*    */   public PBaseCommand() {
/* 21 */     this.aliases = new ArrayList();
/*    */   }
/*    */ 
/*    */   public void execute(CommandSender sender, List<String> parameters) {
/* 25 */     this.sender = sender;
/* 26 */     this.parameters = parameters;
/*    */ 
/* 28 */     if ((sender instanceof Player)) {
/* 29 */       this.player = ((Player)sender);
/*    */     }
/*    */ 
/* 32 */     perform();
/*    */   }
/*    */ 
/*    */   public String getdesc() {
/* 36 */     return this.desc;
/*    */   }
/*    */ 
/*    */   public void perform()
/*    */   {
/*    */   }
/*    */ 
/*    */   public List<String> getAliases() {
/* 44 */     return this.aliases;
/*    */   }
/*    */ 
/*    */   public void sendMessage(String message) {
/* 48 */     if (this.player != null)
/* 49 */       this.sender.sendMessage(message);
/*    */     else
/* 51 */       System.out.println(message);
/*    */   }
/*    */ 
/*    */   public void sendMessage(List<String> messages)
/*    */   {
/* 56 */     for (String message : messages)
/* 57 */       sendMessage(message);
/*    */   }
/*    */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.commands.PBaseCommand
 * JD-Core Version:    0.6.2
 */