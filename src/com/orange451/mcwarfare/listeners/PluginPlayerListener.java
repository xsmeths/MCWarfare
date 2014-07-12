/*     */ package com.orange451.mcwarfare.listeners;
/*     */ 
/*     */ import com.orange451.PVPGun.PermissionInterface;
/*     */ import com.orange451.mcwarfare.FileIO;
/*     */ import com.orange451.mcwarfare.KitPvP;
/*     */ import com.orange451.mcwarfare.LaunchPad;
/*     */ import com.orange451.mcwarfare.Util;
/*     */ import com.orange451.mcwarfare.VoteForServer;
/*     */ import com.orange451.mcwarfare.arena.KitArena;
/*     */ import com.orange451.mcwarfare.arena.KitArena.GameModeModifier;
/*     */ import com.orange451.mcwarfare.arena.KitFlag;
/*     */ import com.orange451.mcwarfare.arena.KitPlayer;
/*     */ import com.orange451.mcwarfare.arena.KitProfile;
/*     */ import com.vexsoftware.votifier.model.Vote;
/*     */ import com.vexsoftware.votifier.model.VotifierEvent;
/*     */ import java.io.BufferedReader;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.Sign;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.player.PlayerChatEvent;
/*     */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*     */ import org.bukkit.event.player.PlayerDropItemEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerKickEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.event.player.PlayerRespawnEvent;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.kitteh.tag.PlayerReceiveNameTagEvent;
/*     */ 
/*     */ public class PluginPlayerListener
/*     */   implements Listener
/*     */ {
/*     */   private KitPvP plugin;
/*     */ 
/*     */   public PluginPlayerListener(KitPvP plugin)
/*     */   {
/*  45 */     this.plugin = plugin;
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onPlayerQuit(PlayerQuitEvent event) {
/*  50 */     Player pl = event.getPlayer();
/*  51 */     if (pl != null) {
/*  52 */       this.plugin.stopMakingArena(pl);
/*  53 */       event.setQuitMessage(null);
/*  54 */       this.plugin.onQuit(pl);
/*  55 */       this.plugin.broadcastMessage(null, ChatColor.YELLOW + event.getPlayer().getName() + " has quit!");
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onPlayerChat(PlayerChatEvent event) {
/*  61 */     Player p = event.getPlayer();
/*  62 */     if (this.plugin.getKitPlayer(p) != null)
/*     */     {
/*  64 */       this.plugin.sayMessage(event.getPlayer(), event.getMessage());
/*     */     }
/*  66 */     else this.plugin.sayNonWarMessage(event.getPlayer(), event.getMessage());
/*     */ 
/*  68 */     event.setCancelled(true);
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onPlayerKick(PlayerKickEvent event)
/*     */   {
/*  74 */     Player player = event.getPlayer();
/*  75 */     if ((this.plugin.isInArena(player)) && 
/*  76 */       (this.plugin.isInArena(player.getLocation())) && 
/*  77 */       (event.getReason().equals("You moved too quickly :( (Hacking?)")))
/*  78 */       event.setCancelled(true);
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGH)
/*     */   public void onPlayerReceiveNameTag(PlayerReceiveNameTagEvent event)
/*     */   {
/*  86 */     KitPlayer pl = this.plugin.getKitPlayer(event.getNamedPlayer());
/*  87 */     if (pl != null)
/*  88 */       event.setTag(pl.getTeamColor() + pl.player.getName());
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onPlayerJoin(PlayerJoinEvent event)
/*     */   {
/*  94 */     Player pl = event.getPlayer();
/*  95 */     String path = KitPvP.getMcWar() + "/banned-players.txt";
/*  96 */     BufferedReader in = FileIO.file_text_open_read(path);
/*  97 */     String strLine = null;
/*  98 */     while ((strLine = FileIO.file_text_read_line(in)) != null) {
/*  99 */       if ((strLine.toLowerCase().equals(event.getPlayer().getName().toLowerCase())) && (event.getPlayer() != null)) {
/* 100 */         pl.kickPlayer("You are banned from this server!");
/* 101 */         event.setJoinMessage(null);
/* 102 */         pl = null;
/*     */       }
/*     */     }
/* 105 */     FileIO.file_text_close(in);
/*     */ 
/* 107 */     if (pl != null) {
/* 108 */       this.plugin.onJoin(pl);
/*     */ 
/* 110 */       if (event.getJoinMessage() != null) {
/* 111 */         event.setJoinMessage(null);
/* 112 */         this.plugin.broadcastMessage(null, ChatColor.YELLOW + event.getPlayer().getName() + " has joined!");
/* 113 */         this.plugin.joinArena(event.getPlayer(), null);
/*     */       }
/*     */       try
/*     */       {
/* 117 */         int servernum = this.plugin.getServerNumber();
/* 118 */         this.plugin.giveMessage(pl, ChatColor.AQUA + "Welcome to MC-warfare #" + servernum + "!");
/*     */       }
/*     */       catch (Exception localException) {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onPlayerDeath(PlayerDeathEvent event) {
/* 127 */     event.setDeathMessage(null);
/* 128 */     event.setDroppedExp(0);
/* 129 */     KitPlayer kp = this.plugin.getKitPlayer(event.getEntity());
/* 130 */     if (kp != null)
/* 131 */       kp.arena.onDeath(kp);
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
/*     */   {
/* 137 */     Player player = event.getPlayer();
/* 138 */     String[] split = event.getMessage().split(" ");
/* 139 */     split[0] = split[0].substring(1);
/* 140 */     String label = split[0];
/* 141 */     String[] args = new String[split.length - 1];
/* 142 */     for (int i = 1; i < split.length; i++) {
/* 143 */       args[(i - 1)] = split[i];
/*     */     }
/*     */ 
/* 146 */     if (label.equalsIgnoreCase("buy")) {
/* 147 */       event.setCancelled(true);
/* 148 */       String msg = "Please visit: ";
/* 149 */       this.plugin.giveMessage(player, msg);
/* 150 */       msg = ChatColor.DARK_AQUA + "http://minecraft-multiplayer.com" + ChatColor.WHITE + " to donate!";
/* 151 */       this.plugin.giveMessage(player, msg);
/*     */     }
/* 153 */     if (label.equalsIgnoreCase("help")) {
/* 154 */       event.setCancelled(true);
/* 155 */       this.plugin.giveMessage(player, ChatColor.YELLOW + "--------MCWAR HELP--------");
/* 156 */       this.plugin.giveMessage(player, ChatColor.DARK_AQUA + "/war join" + ChatColor.WHITE + " to play!");
/* 157 */       this.plugin.giveMessage(player, ChatColor.DARK_AQUA + "/war leave" + ChatColor.WHITE + " to leave!");
/* 158 */       this.plugin.giveMessage(player, ChatColor.DARK_AQUA + "/buy" + ChatColor.WHITE + " to donate!");
/*     */     }
/* 160 */     if (label.equalsIgnoreCase("rules")) {
/* 161 */       event.setCancelled(true);
/* 162 */       this.plugin.giveMessage(player, ChatColor.RED + "1) NO CHEATING, GLITCHING, SPAMMING");
/* 163 */       this.plugin.giveMessage(player, ChatColor.RED + "2) NO EXPLOITING");
/* 164 */       this.plugin.giveMessage(player, ChatColor.RED + "3) NO ARGUING WITH MODS OR ADMINS");
/*     */     }
/* 166 */     if (label.equalsIgnoreCase("who")) {
/* 167 */       event.setCancelled(true);
/* 168 */       this.plugin.giveMessage(player, "players online: " + ChatColor.YELLOW + Util.Who().size());
/*     */     }
/* 170 */     if ((label.equalsIgnoreCase("test")) && 
/* 171 */       (PermissionInterface.checkPermission(player, "mcwar.admin"))) {
/* 172 */       this.plugin.onStartLobby();
/*     */     }
/*     */ 
/* 175 */     if (label.equalsIgnoreCase("fp")) {
/* 176 */       KitArena ka = this.plugin.getFirstKitArena("lobby");
/* 177 */       if (args.length == 0) {
/* 178 */         ka.tomap = this.plugin.getRandomKitArena(ka, ka.last, ka.getActivePlayers());
/*     */       } else {
/* 180 */         String map = args[0];
/* 181 */         KitArena gmap = this.plugin.getKitArena(map);
/* 182 */         if ((!ka.stopped) && (gmap != null)) {
/* 183 */           ka.tomap = gmap;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 188 */     if (label.equalsIgnoreCase("tag")) {
/* 189 */       String map = args[0];
/* 190 */       KitPlayer kp = this.plugin.getKitPlayer(player);
/* 191 */       if (kp != null) {
/* 192 */         if (kp.profile.level > 30) {
/* 193 */           if (map.length() > 3) {
/* 194 */             map = map.substring(0, 3);
/*     */           }
/* 196 */           String check = map.toLowerCase();
/* 197 */           if (((!PermissionInterface.checkPermission(player, "mcwar.admin")) || (!PermissionInterface.checkPermission(player, "mcwar.mod"))) && (
/* 198 */             (check.contains("op")) || (check.contains("adm")) || (check.contains("mod")) || (check.contains("onr")))) {
/* 199 */             kp.sayMessage(null, ChatColor.RED + "This tag is not allowed!");
/* 200 */             return;
/*     */           }
/*     */ 
/* 203 */           kp.profile.tag = map;
/* 204 */           kp.profile.save(kp);
/* 205 */           kp.sayMessage(null, ChatColor.GREEN + "Tag set to: " + ChatColor.WHITE);
/*     */         } else {
/* 207 */           kp.sayMessage(null, ChatColor.RED + "You need to be over level 30 to do this");
/*     */         }
/*     */       }
/* 210 */       event.setCancelled(true);
/*     */     }
/*     */ 
/* 213 */     if (label.equalsIgnoreCase("spawn")) {
/* 214 */       KitPlayer pl = this.plugin.getKitPlayer(player);
/* 215 */       if (pl != null) {
/* 216 */         this.plugin.leaveArena(pl);
/* 217 */         this.plugin.joinArena(player, null);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onPlayerMove(PlayerMoveEvent event)
/*     */   {
/* 225 */     Player pl = event.getPlayer();
/* 226 */     if ((pl != null) && (event.getTo() != null) && (event.getFrom() != null)) {
/* 227 */       double dist = event.getFrom().distanceSquared(event.getTo());
/* 228 */       if (dist > 0.0D)
/*     */       {
/*     */         try
/*     */         {
/* 232 */           Block b = pl.getLocation().add(0.0D, -1.0D, 0.0D).getBlock();
/* 233 */           Material mat = b.getType();
/* 234 */           if (mat.equals(Material.SPONGE)) {
/* 235 */             LaunchPad lp = LaunchPad.getLaunchPad(b);
/* 236 */             if (lp != null)
/* 237 */               lp.launch(pl);
/*     */           }
/*     */         }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onPlayerTeleport(PlayerTeleportEvent event)
/*     */   {
/* 250 */     Player pl = event.getPlayer();
/* 251 */     if (pl != null) {
/* 252 */       KitPlayer kp = this.plugin.getKitPlayer(pl);
/* 253 */       if (kp != null)
/* 254 */         kp.lastLoc = event.getTo();
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onPlayerRespawn(PlayerRespawnEvent event)
/*     */   {
/* 261 */     Player pl = event.getPlayer();
/* 262 */     if ((pl != null) && 
/* 263 */       (this.plugin.isInArena(pl))) {
/* 264 */       KitPlayer kp = this.plugin.getKitPlayer(pl);
/* 265 */       event.setRespawnLocation(this.plugin.getKitPlayer(pl).arena.getSpawnLocation(kp));
/* 266 */       kp.isSpawning = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onPlayerPickupItem(PlayerPickupItemEvent event)
/*     */   {
/* 273 */     event.setCancelled(true);
/* 274 */     Player pl = event.getPlayer();
/* 275 */     if ((pl != null) && 
/* 276 */       (this.plugin.isInArena(pl)) && 
/* 277 */       (this.plugin.isInArena(pl.getLocation()))) {
/* 278 */       KitPlayer kp = this.plugin.getKitPlayer(pl);
/* 279 */       if ((kp != null) && 
/* 280 */         (kp.arena.gameModifier.equals(KitArena.GameModeModifier.CTF))) {
/* 281 */         KitFlag kf = kp.arena.getFlag(event.getItem());
/* 282 */         if ((kf != null) && 
/* 283 */           (kf.teamColor != kp.getWoolColor())) {
/* 284 */           kf.carrier = pl;
/* 285 */           event.getItem().remove();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onPlayerDropItem(PlayerDropItemEvent event)
/*     */   {
/* 297 */     Player pl = event.getPlayer();
/* 298 */     if ((pl != null) && 
/* 299 */       (this.plugin.isInArena(pl.getLocation()))) {
/* 300 */       event.getItemDrop().remove();
/* 301 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onPlayerInteract(PlayerInteractEvent event)
/*     */   {
/* 309 */     Player player = event.getPlayer();
/* 310 */     if (event.hasBlock()) {
/*     */       try {
/* 312 */         ItemStack iteminhand = player.getItemInHand();
/* 313 */         String holding = iteminhand.getType().toString().toLowerCase();
/* 314 */         if (iteminhand.getTypeId() == 397)
/* 315 */           event.setCancelled(true);
/* 316 */         if (((event.getClickedBlock().getType().equals(Material.DIRT)) || (event.getClickedBlock().getType().equals(Material.GRASS)) || (event.getClickedBlock().getType().equals(Material.SOIL))) && 
/* 317 */           (holding.contains("hoe"))) {
/* 318 */           event.setCancelled(true);
/*     */         }
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */       }
/* 324 */       Block block = event.getClickedBlock();
/* 325 */       if ((block.getState() instanceof Sign)) {
/* 326 */         Sign s = (Sign)block.getState();
/* 327 */         String line1 = s.getLine(0).toLowerCase();
/* 328 */         String line2 = s.getLine(1).toLowerCase();
/* 329 */         if (this.plugin.isInArena(player)) {
/* 330 */           if (line1.contains("buy")) {
/* 331 */             player.chat("/war gun buy " + line2);
/*     */           }
/* 333 */           if (line1.contains("perk")) {
/* 334 */             this.plugin.setPerk(player, line2);
/*     */           }
/* 336 */           if (line1.contains("choose")) {
/* 337 */             player.chat("/war gun apply " + line2);
/*     */           }
/* 339 */           if (line1.contains("bought")) {
/* 340 */             player.chat("/war list");
/*     */           }
/* 342 */           if (line1.contains("unlocked")) {
/* 343 */             player.chat("/war gun list");
/*     */           }
/*     */         }
/* 346 */         if (line1.contains("givegun"))
/* 347 */           this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), "givegun " + player.getName() + " " + line2);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void onVotifierEvent(VotifierEvent event)
/*     */   {
/* 355 */     Vote vote = event.getVote();
/* 356 */     String v = vote.getUsername();
/* 357 */     new VoteForServer(v);
/* 358 */     Player pl = Util.MatchPlayer(v);
/* 359 */     if (pl != null)
/* 360 */       this.plugin.giveMessage(pl, ChatColor.BOLD + "" + ChatColor.RED + "THANKS FOR VOTING!");
/*     */   }
/*     */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.listeners.PluginPlayerListener
 * JD-Core Version:    0.6.2
 */