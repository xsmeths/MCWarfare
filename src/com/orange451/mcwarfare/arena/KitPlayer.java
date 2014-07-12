/*     */ package com.orange451.mcwarfare.arena;
/*     */ 
/*     */ import com.orange451.mcwarfare.InventoryHelper;
/*     */ import com.orange451.mcwarfare.KitPvP;
/*     */ import com.orange451.mcwarfare.Util;
/*     */ import com.orange451.mcwarfare.arena.kits.KitClass;
/*     */ import com.orange451.mcwarfare.arena.kits.Perk;
/*     */ import com.orange451.mcwarfare.permissions.PermissionInterface;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.server.NBTTagCompound;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.craftbukkit.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.material.MaterialData;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.kitteh.tag.TagAPI;
/*     */ 
/*     */ public class KitPlayer
/*     */ {
/*  32 */   public int myclass = 0;
/*     */   public KitArena.Teams team;
/*     */   public int kills;
/*  35 */   public int killStreak = 0;
/*     */   public int deaths;
/*  37 */   public int timeOffline = 0;
/*  38 */   public int timedead = 0;
/*     */   public int alive;
/*     */   public int lives;
/*  41 */   public boolean dead = false;
/*  42 */   public boolean displayGUI = true;
/*  43 */   public boolean receiveChat = true;
/*  44 */   public boolean isSpawning = false;
/*     */   public KitProfile profile;
/*     */   public KitClass kclass;
/*     */   public KitArena arena;
/*     */   public Player player;
/*     */   public String name;
/*  50 */   public String[] chat = new String[6];
/*     */   public Location returnto;
/*     */   public Location lastLoc;
/*     */   public KitPvP plugin;
/*  54 */   public ArrayList<KitItem> boughtItems = new ArrayList();
/*  55 */   public ArrayList<Perk> perks = new ArrayList();
/*     */   private int afkTicks;
/*     */ 
/*     */   public void start(KitPvP plugin, KitArena arena, Player p, KitArena.Teams team)
/*     */   {
/*  62 */     this.plugin = plugin;
/*  63 */     this.player = p;
/*  64 */     this.name = p.getName();
/*  65 */     this.team = team;
/*  66 */     this.arena = arena;
/*  67 */     this.returnto = p.getLocation().clone();
/*     */ 
/*  69 */     for (int i = 0; i < this.chat.length; i++) {
/*  70 */       this.chat[i] = "";
/*     */     }
/*  72 */     this.profile = arena.plugin.getProfile(this);
/*  73 */     if (this.profile == null) {
/*  74 */       p.kickPlayer("COULD NOT LOCATE PLAYER PROFILE!, PLEASE CONTACT SERVER ADMINS");
/*     */     }
/*     */ 
/*  77 */     if (this.profile.classes.size() > 0) {
/*  78 */       this.kclass = ((KitClass)this.profile.classes.get(this.profile.myclass));
/*     */     } else {
/*  80 */       this.player.kickPlayer("CORRUPTED PROFILE " + ChatColor.GRAY + "visit \n" + ChatColor.BLUE + "www.minecraft-multiplayer.com");
/*  81 */       System.out.println("[NOTICE] " + this.player.getName() + " has a corrupted profile!");
/*     */     }
/*  83 */     this.profile.xpn = getXpto(this.profile.level);
/*     */ 
/*  85 */     this.perks = plugin.getActivePerks(this);
/*     */   }
/*     */ 
/*     */   public void tick() {
/*  89 */     this.kclass = ((KitClass)this.profile.classes.get(this.profile.myclass));
/*  90 */     this.player = Util.MatchPlayer(this.name);
/*  91 */     if (this.player == null)
/*  92 */       return;
/*  93 */     if (this.player.isDead())
/*  94 */       this.timedead += 1;
/*     */     else {
/*  96 */       this.timedead = 0;
/*     */     }
/*  98 */     if ((!this.player.getGameMode().equals(GameMode.CREATIVE)) && (!this.player.isOp())) {
/*  99 */       this.player.setGameMode(GameMode.ADVENTURE);
/*     */     }
/* 101 */     if (this.profile.level <= 0) {
/* 102 */       this.profile.level = 1;
/*     */     }
/* 104 */     if ((this.timedead > 12) && (!this.player.isDead())) {
/* 105 */       Player rejoin = this.player;
/* 106 */       this.plugin.leaveArena(this);
/* 107 */       this.plugin.joinArena(rejoin, null);
/*     */     }
/*     */ 
/* 110 */     if (!this.player.isDead()) {
/* 111 */       if (this.player.isSprinting()) {
/* 112 */         this.player.setFoodLevel(this.player.getFoodLevel() - 1);
/*     */       }
/* 114 */       else if (this.player.getFoodLevel() < 19) {
/* 115 */         this.player.setFoodLevel(this.player.getFoodLevel() + 2);
/*     */       }
/*     */ 
/* 119 */       if (this.player.getHealth() < 20) {
/* 120 */         this.player.setHealth(this.player.getHealth() + 1);
/*     */       }
/*     */     }
/*     */ 
/* 124 */     for (int i = 0; i < this.perks.size(); i++) {
/* 125 */       ((Perk)this.perks.get(i)).step();
/*     */     }
/*     */ 
/* 128 */     decideHat();
/* 129 */     setChat();
/* 130 */     calculate();
/* 131 */     if (this.timedead <= 0) {
/* 132 */       this.alive += 1;
/*     */     }
/*     */ 
/* 135 */     if (this.isSpawning) {
/* 136 */       this.alive = 0;
/* 137 */       this.isSpawning = false;
/* 138 */       spawn();
/*     */     }
/*     */ 
/* 141 */     if (this.lastLoc != null) {
/* 142 */       double moved = this.player.getLocation().distance(this.lastLoc);
/* 143 */       if (moved < 0.25D) {
/* 144 */         this.afkTicks += 1;
/* 145 */         if (this.afkTicks > 240)
/* 146 */           this.player.kickPlayer(ChatColor.RED + "Kicked for being AFK while in war!");
/*     */       }
/*     */       else {
/* 149 */         this.afkTicks -= 40;
/*     */       }
/* 151 */       if (this.afkTicks < 0)
/* 152 */         this.afkTicks = 0;
/*     */     }
/* 154 */     this.lastLoc = this.player.getLocation();
/*     */   }
/*     */ 
/*     */   private int sendStats() {
/* 158 */     if (this.player == null)
/* 159 */       return 0;
/* 160 */     if (!this.player.isOnline())
/* 161 */       return 0;
/* 162 */     String myteam = getTeamName();
/* 163 */     String otherteam = "white";
/* 164 */     if (myteam.contains("ffa"))
/* 165 */       myteam = "white";
/* 166 */     if (myteam.contains("blue"))
/* 167 */       otherteam = "red";
/* 168 */     if (myteam.contains("red"))
/* 169 */       otherteam = "blue";
/* 170 */     String toSend = "©®";
/* 171 */     toSend = toSend + "ch,";
/* 172 */     toSend = toSend + myteam + ",";
/* 173 */     toSend = toSend + otherteam + ",";
/* 174 */     if (this.arena.type.equals("tdm")) {
/* 175 */       toSend = toSend + "TEAM 1  " + Integer.toString(this.arena.redkills) + ",";
/* 176 */       toSend = toSend + "TEAM 2  " + Integer.toString(this.arena.bluekills) + ",";
/* 177 */     } else if (this.arena.type.equals("ffa")) {
/* 178 */       KitPlayer mostkills = this.arena.getPlayerWithMostKills();
/* 179 */       if (mostkills != null) {
/* 180 */         toSend = toSend + "LEADER:  " + mostkills.player.getName() + ",";
/* 181 */         toSend = toSend + "0,";
/*     */       }
/*     */     } else {
/* 184 */       toSend = toSend + "ERR,";
/* 185 */       toSend = toSend + "ERR,";
/*     */     }
/* 187 */     toSend = toSend + Integer.toString(getAmtAmmo("primary")) + ",";
/* 188 */     toSend = toSend + Integer.toString(getAmtAmmo("secondary")) + ",";
/*     */ 
/* 190 */     toSend = toSend + "K  " + Integer.toString(this.kills) + ",";
/* 191 */     toSend = toSend + "D    " + Integer.toString(this.deaths) + ",";
/* 192 */     toSend = toSend + "R      " + Double.toString(getKDR()) + ",";
/* 193 */     toSend = toSend + "S      " + Integer.toString(this.killStreak) + ",";
/*     */ 
/* 195 */     toSend = toSend + Integer.toString(this.arena.timer) + "s,";
/*     */ 
/* 197 */     toSend = toSend + "I,";
/* 198 */     toSend = toSend + "Like,";
/* 199 */     toSend = toSend + "Dicks,";
/*     */ 
/* 201 */     this.player.sendMessage(toSend);
/* 202 */     return 2;
/*     */   }
/*     */ 
/*     */   public org.bukkit.inventory.ItemStack setColor(org.bukkit.inventory.ItemStack item, int color) {
/* 206 */     CraftItemStack craftStack = null;
/* 207 */     net.minecraft.server.ItemStack itemStack = null;
/* 208 */     if ((item instanceof CraftItemStack)) {
/* 209 */       craftStack = (CraftItemStack)item;
/* 210 */       itemStack = craftStack.getHandle();
/*     */     }
/* 212 */     else if ((item instanceof org.bukkit.inventory.ItemStack)) {
/* 213 */       craftStack = new CraftItemStack(item);
/* 214 */       itemStack = craftStack.getHandle();
/*     */     }
/* 216 */     NBTTagCompound tag = itemStack.tag;
/* 217 */     if (tag == null) {
/* 218 */       tag = new NBTTagCompound();
/* 219 */       tag.setCompound("display", new NBTTagCompound());
/* 220 */       itemStack.tag = tag;
/*     */     }
/*     */ 
/* 223 */     tag = itemStack.tag.getCompound("display");
/* 224 */     tag.setInt("color", color);
/* 225 */     itemStack.tag.setCompound("display", tag);
/* 226 */     return craftStack;
/*     */   }
/*     */ 
/*     */   public int getStringLength(String str)
/*     */   {
/* 231 */     if (str == null)
/* 232 */       return 0;
/* 233 */     int ret = str.length();
/* 234 */     for (int i = 0; i < str.length(); i++) {
/* 235 */       char c = str.charAt(i);
/* 236 */       if (c == '§') {
/* 237 */         ret -= 2;
/*     */       }
/*     */     }
/* 240 */     return ret;
/*     */   }
/*     */ 
/*     */   public int getColorLength(String str) {
/* 244 */     int ret = 0;
/* 245 */     if (str == null)
/* 246 */       return ret;
/* 247 */     for (int i = 0; i < str.length(); i++) {
/* 248 */       char c = str.charAt(i);
/* 249 */       if (c == '§') {
/* 250 */         ret += 2;
/*     */       }
/*     */     }
/* 253 */     return ret;
/*     */   }
/*     */ 
/*     */   public void sayMessage(Player from, String msg) {
/* 257 */     if ((from != null) && 
/* 258 */       (!this.receiveChat) && (!PermissionInterface.hasPermission(from, "mcwar.admin"))) {
/* 259 */       return;
/*     */     }
/* 261 */     int maxLength = 50;
/* 262 */     int extracolorlength = getColorLength(msg);
/* 263 */     ArrayList messages = new ArrayList();
/* 264 */     if (getStringLength(msg) > maxLength) {
/* 265 */       messages.add(msg.substring(0, maxLength + extracolorlength) + "--");
/* 266 */       msg = msg.substring(maxLength + extracolorlength - 1, msg.length());
/* 267 */       if (msg.length() > maxLength - 1)
/* 268 */         messages.add("  " + msg.substring(1, maxLength));
/*     */       else
/* 270 */         messages.add("  " + msg.substring(1, msg.length()));
/*     */     }
/*     */     else {
/* 273 */       messages.add(msg);
/*     */     }
/* 275 */     for (int i = 0; i < messages.size(); i++)
/* 276 */       doMessage((String)messages.get(i));
/*     */   }
/*     */ 
/*     */   public void doMessage(String msg)
/*     */   {
/* 281 */     for (int i = this.chat.length - 2; i >= 0; i--) {
/* 282 */       this.chat[(i + 1)] = this.chat[i];
/*     */     }
/* 284 */     this.chat[0] = msg;
/*     */   }
/*     */ 
/*     */   public void clearChat() {
/* 288 */     for (int i = 1; i <= 32; i++) {
/* 289 */       this.player.sendMessage("");
/*     */     }
/* 291 */     for (int i = this.chat.length - 1; i >= 0; i--)
/* 292 */       this.chat[i] = "";
/*     */   }
/*     */ 
/*     */   public void setChat()
/*     */   {
/* 297 */     String message = "";
/* 298 */     int sent = sendStats();
/* 299 */     int amt = 10 - sent;
/* 300 */     for (int c = 0; c < amt; c++) {
/* 301 */       message = message + "\n ";
/*     */     }
/* 303 */     this.player.sendMessage(message);
/*     */ 
/* 305 */     String kdr = Double.toString(getKDR()) + ChatColor.WHITE;
/* 306 */     if (this.displayGUI) {
/* 307 */       this.player.sendMessage("║ " + drawXpBar());
/* 308 */       this.player.sendMessage("║ " + 
/* 309 */         ChatColor.AQUA + "kills:" + 
/* 310 */         ChatColor.GREEN + Integer.toString(getKills()) + 
/* 311 */         ChatColor.AQUA + " deaths:" + 
/* 312 */         ChatColor.RED + Integer.toString(getDeaths()) + 
/* 313 */         ChatColor.AQUA + " kdr:" + ChatColor.GREEN + kdr + 
/* 314 */         ChatColor.AQUA + " credits:" + ChatColor.GREEN + this.profile.credits + 
/* 315 */         ChatColor.AQUA + " level:" + ChatColor.GREEN + this.profile.level);
/* 316 */       this.player.sendMessage("║ " + 
/* 317 */         ChatColor.AQUA + "Team:" + getTeamColor() + getTeamName() + 
/* 318 */         ChatColor.AQUA + " leader:" + getLeader() + 
/* 319 */         ChatColor.AQUA + " " + getTeams() + 
/* 320 */         ChatColor.GREEN + "         (" + Integer.toString(this.killStreak) + ")");
/* 321 */       this.player.sendMessage("╚═══════════════════════════════════════════════════════════════");
/*     */     } else {
/* 323 */       this.player.sendMessage("");
/* 324 */       this.player.sendMessage("");
/* 325 */       this.player.sendMessage("");
/* 326 */       this.player.sendMessage("");
/*     */     }
/*     */ 
/* 330 */     for (int i = this.chat.length - 1; i >= 0; i--)
/* 331 */       this.player.sendMessage(this.chat[i]);
/*     */   }
/*     */ 
/*     */   public int getKills()
/*     */   {
/* 336 */     if (this.arena.type.equals("lobby")) {
/* 337 */       return this.profile.kills;
/*     */     }
/* 339 */     return this.kills;
/*     */   }
/*     */ 
/*     */   public int getDeaths() {
/* 343 */     if (this.arena.type.equals("lobby")) {
/* 344 */       return this.profile.deaths;
/*     */     }
/* 346 */     return this.deaths;
/*     */   }
/*     */ 
/*     */   public void decideHat() {
/* 350 */     checkInventory();
/* 351 */     if (this.alive < 4) {
/* 352 */       return;
/*     */     }
/* 354 */     int teamcolor = getWoolColor();
/* 355 */     int hexColor = 255;
/* 356 */     if (this.team.equals(KitArena.Teams.RED)) {
/* 357 */       hexColor = 16711680;
/* 358 */       if (this.arena.gameModifier.equals(KitArena.GameModeModifier.INFECT)) {
/* 359 */         hexColor = 3381504;
/*     */       }
/*     */     }
/* 362 */     if (this.team.equals(KitArena.Teams.FFA)) {
/* 363 */       hexColor = 16711935;
/*     */     }
/* 365 */     org.bukkit.inventory.ItemStack c0 = setColor(new org.bukkit.inventory.ItemStack(Material.LEATHER_HELMET, 1), hexColor);
/* 366 */     org.bukkit.inventory.ItemStack c1 = setColor(new org.bukkit.inventory.ItemStack(Material.LEATHER_CHESTPLATE, 1), hexColor);
/* 367 */     org.bukkit.inventory.ItemStack c2 = setColor(new org.bukkit.inventory.ItemStack(Material.LEATHER_LEGGINGS, 1), hexColor);
/* 368 */     org.bukkit.inventory.ItemStack c3 = setColor(new org.bukkit.inventory.ItemStack(Material.LEATHER_BOOTS, 1), hexColor);
/*     */ 
/* 370 */     if ((teamcolor != -1) && 
/* 371 */       (this.player.getInventory().getHelmet() == null)) {
/* 372 */       this.player.getInventory().setHelmet(c0);
/*     */     }
/*     */ 
/* 376 */     if (this.player.getInventory().getChestplate() == null)
/* 377 */       this.player.getInventory().setChestplate(c1);
/* 378 */     if (this.player.getInventory().getLeggings() == null)
/* 379 */       this.player.getInventory().setLeggings(c2);
/* 380 */     if (this.player.getInventory().getBoots() == null)
/* 381 */       this.player.getInventory().setBoots(c3);
/*     */   }
/*     */ 
/*     */   public int getWoolColor() {
/* 385 */     int arenacolor = this.arena.getWoolColor(this);
/* 386 */     if (arenacolor == 999)
/* 387 */       return -1;
/* 388 */     if (arenacolor == -1) {
/* 389 */       int ret = 11;
/* 390 */       if (this.team.equals(KitArena.Teams.RED))
/* 391 */         ret = 14;
/* 392 */       return ret;
/*     */     }
/* 394 */     return arenacolor;
/*     */   }
/*     */ 
/*     */   public String drawXpBar() {
/* 398 */     int max = 22;
/* 399 */     double ratio = this.profile.xp / this.profile.xpn;
/* 400 */     int have = (int)(max * ratio);
/* 401 */     int need = max - have;
/* 402 */     if (have > max) {
/* 403 */       return "";
/*     */     }
/* 405 */     String ret = "";
/* 406 */     for (int i = 0; i < have; i++) {
/* 407 */       ret = ret + ChatColor.AQUA + "▓";
/*     */     }
/*     */ 
/* 410 */     for (int i = 0; i < need; i++) {
/* 411 */       ret = ret + ChatColor.GRAY + "░";
/*     */     }
/* 413 */     ret = ret + ChatColor.WHITE + " xp: (" + this.profile.xp + "/" + this.profile.xpn + ")";
/* 414 */     return ret;
/*     */   }
/*     */ 
/*     */   public String getTeams() {
/* 418 */     if (this.arena.getMode().equals("tdm")) {
/* 419 */       return ChatColor.BLUE + Integer.toString(this.arena.getPlayersOnTeam(KitArena.Teams.BLUE).size()) + ChatColor.AQUA + "/" + ChatColor.RED + Integer.toString(this.arena.getPlayersOnTeam(KitArena.Teams.RED).size()) + ChatColor.AQUA + " players";
/*     */     }
/* 421 */     return ChatColor.YELLOW + Integer.toString(this.arena.getPlayersOnTeam(KitArena.Teams.ALL).size()) + ChatColor.AQUA + " players";
/*     */   }
/*     */ 
/*     */   public String getLeader()
/*     */   {
/* 426 */     String ret = ChatColor.GRAY + "none  ";
/* 427 */     if (this.arena.type.equals("tdm")) {
/* 428 */       if (this.arena.gameModifier.equals(KitArena.GameModeModifier.CTF))
/*     */       {
/* 430 */         if (this.arena.bluescore > this.arena.redscore)
/* 431 */           return ChatColor.BLUE + "blue(" + Integer.toString(this.arena.bluescore - this.arena.redscore) + ")  " + ChatColor.WHITE;
/* 432 */         if (this.arena.bluescore < this.arena.redscore)
/* 433 */           return ChatColor.RED + "red(" + Integer.toString(this.arena.redscore - this.arena.bluescore) + ")   " + ChatColor.WHITE;
/*     */       }
/*     */       else
/*     */       {
/* 437 */         if (this.arena.bluekills > this.arena.redkills)
/* 438 */           return ChatColor.BLUE + "blue(" + Integer.toString(this.arena.bluekills - this.arena.redkills) + ")  " + ChatColor.WHITE;
/* 439 */         if (this.arena.bluekills < this.arena.redkills) {
/* 440 */           return ChatColor.RED + "red(" + Integer.toString(this.arena.redkills - this.arena.bluekills) + ")   " + ChatColor.WHITE;
/*     */         }
/*     */       }
/*     */ 
/* 444 */       return ChatColor.YELLOW + "tie(0)  " + ChatColor.WHITE;
/* 445 */     }if (this.arena.type.equals("ffa")) {
/* 446 */       KitPlayer mostkills = this.arena.getPlayerWithMostKills();
/* 447 */       if (mostkills != null) {
/* 448 */         return ChatColor.RED + mostkills.player.getName() + "(" + mostkills.kills + ")  ";
/*     */       }
/*     */     }
/* 451 */     return ret;
/*     */   }
/*     */ 
/*     */   public ChatColor getTeamColor() {
/* 455 */     if (this.team.equals(KitArena.Teams.BLUE))
/* 456 */       return ChatColor.BLUE;
/* 457 */     if (this.team.equals(KitArena.Teams.RED))
/* 458 */       return ChatColor.RED;
/* 459 */     if (this.team.equals(KitArena.Teams.NEUTRAL)) {
/* 460 */       return ChatColor.GRAY;
/*     */     }
/* 462 */     return ChatColor.LIGHT_PURPLE;
/*     */   }
/*     */ 
/*     */   public String getTeamName() {
/* 466 */     if (this.team.equals(KitArena.Teams.BLUE))
/* 467 */       return "blue" + ChatColor.WHITE;
/* 468 */     if (this.team.equals(KitArena.Teams.RED)) {
/* 469 */       return "red" + ChatColor.WHITE;
/*     */     }
/* 471 */     return "ffa" + ChatColor.WHITE;
/*     */   }
/*     */ 
/*     */   public int getHealth() {
/* 475 */     return (int)(this.player.getHealth() / 20.0D * 100.0D);
/*     */   }
/*     */ 
/*     */   public double getKDR() {
/* 479 */     double big = getKills();
/* 480 */     if (getDeaths() > 0)
/* 481 */       big = getKills() / getDeaths() * 100.0D;
/*     */     else {
/* 483 */       big *= 100.0D;
/*     */     }
/* 485 */     double round = Math.round(big);
/* 486 */     return round / 100.0D;
/*     */   }
/*     */ 
/*     */   public void spawn() {
/* 490 */     if (!this.player.isOnline())
/* 491 */       return;
/* 492 */     TagAPI.refreshPlayer(this.player);
/* 493 */     this.dead = false;
/* 494 */     this.player.teleport(this.arena.getSpawnLocation(this), PlayerTeleportEvent.TeleportCause.PLUGIN);
/*     */     try
/*     */     {
/* 497 */       String msg = ChatColor.DARK_AQUA + "http://minecraft-multiplayer.com";
/* 498 */       sayMessage(null, msg);
/* 499 */       sayMessage(null, ChatColor.GRAY + "  to donate, learn tips, tricks, and more!");
/*     */     } catch (Exception e) {
/* 501 */       e.printStackTrace();
/*     */     }
/*     */     try {
/* 504 */       Player p = Util.MatchPlayer(this.player.getName());
/* 505 */       p.getInventory().clear();
/* 506 */       if (this.kclass == null) {
/* 507 */         if (!this.arena.checkSpawn(this)) {
/* 508 */           p.getInventory().setChestplate(new org.bukkit.inventory.ItemStack(Material.IRON_CHESTPLATE, 1));
/* 509 */           p.getInventory().setLeggings(new org.bukkit.inventory.ItemStack(Material.IRON_LEGGINGS, 1));
/* 510 */           p.getInventory().setBoots(new org.bukkit.inventory.ItemStack(Material.IRON_BOOTS, 1));
/* 511 */           p.getInventory().setItem(0, new org.bukkit.inventory.ItemStack(Material.DIAMOND_SWORD, 1));
/*     */         }
/*     */       }
/*     */       else
/*     */         try {
/* 516 */           for (int i = 0; i < this.kclass.pots.size(); i++) {
/* 517 */             this.player.addPotionEffect((PotionEffect)this.kclass.pots.get(i));
/*     */           }
/*     */ 
/* 520 */           if (this.kclass.armor0 > 0) p.getInventory().setHelmet(getItemStack(this.kclass.armor0, this.kclass.enchanthelmet));
/* 521 */           if (this.kclass.armor1 > 0) p.getInventory().setChestplate(getItemStack(this.kclass.armor1, this.kclass.enchantchest));
/* 522 */           if (this.kclass.armor2 > 0) p.getInventory().setLeggings(getItemStack(this.kclass.armor2, this.kclass.enchantlegs));
/* 523 */           if (this.kclass.armor3 > 0) p.getInventory().setBoots(getItemStack(this.kclass.armor3, this.kclass.enchantboots));
/*     */ 
/* 525 */           int amtGrenade = 0;
/*     */ 
/* 527 */           if (!this.arena.checkSpawn(this)) {
/* 528 */             giveItem(p, this.kclass.weapon1, this.kclass.enchant1, this.kclass.special1, this.kclass.amt1, 0);
/* 529 */             giveItem(p, this.kclass.weapon2, this.kclass.enchant2, this.kclass.special2, this.kclass.amt2, 1);
/* 530 */             giveItem(p, this.kclass.weapon3, this.kclass.enchant3, this.kclass.special3, this.kclass.amt3, 2);
/* 531 */             giveItem(p, this.kclass.weapon4, this.kclass.enchant4, this.kclass.special4, this.kclass.amt4, 3);
/* 532 */             giveItem(p, this.kclass.weapon5, this.kclass.enchant5, this.kclass.special5, this.kclass.amt5, 4);
/* 533 */             giveItem(p, this.kclass.weapon6, this.kclass.enchant6, this.kclass.special6, this.kclass.amt6, 5);
/* 534 */             giveItem(p, this.kclass.weapon7, this.kclass.enchant7, this.kclass.special7, this.kclass.amt7, 6);
/* 535 */             giveItem(p, this.kclass.weapon8, this.kclass.enchant8, this.kclass.special8, this.kclass.amt8, 7);
/* 536 */             giveItem(p, this.kclass.weapon9, this.kclass.enchant9, this.kclass.special9, this.kclass.amt9, 8);
/*     */ 
/* 538 */             if (this.plugin.hasVoted(this.player)) {
/* 539 */               amtGrenade++;
/*     */             }
/*     */           }
/* 542 */           if (PermissionInterface.hasPermission(this.player, "mcwar.grenade")) {
/* 543 */             amtGrenade += 2;
/*     */           }
/* 545 */           if (amtGrenade > 0) {
/* 546 */             new KitItem(new org.bukkit.inventory.ItemStack(Material.SLIME_BALL, amtGrenade)).give(this);
/*     */           }
/* 548 */           if (PermissionInterface.hasPermission(this.player, "mcwar.flashbang")) {
/* 549 */             new KitItem(new org.bukkit.inventory.ItemStack(Material.REDSTONE, 2)).give(this);
/*     */           }
/*     */ 
/* 552 */           if (PermissionInterface.hasPermission(this.player, "mcwar.tomahawk")) {
/* 553 */             new KitItem(new org.bukkit.inventory.ItemStack(Material.STONE_AXE, 1)).give(this);
/*     */           }
/*     */ 
/* 556 */           for (int i = 0; i < this.boughtItems.size(); i++) {
/* 557 */             ((KitItem)this.boughtItems.get(i)).give(this);
/*     */           }
/*     */ 
/* 560 */           for (int i = 0; i < this.perks.size(); i++) {
/* 561 */             ((Perk)this.perks.get(i)).giveToPlayer(this.player);
/*     */           }
/*     */ 
/* 564 */           p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 160, 14));
/*     */         } catch (Exception e) {
/* 566 */           e.printStackTrace();
/*     */         }
/*     */     }
/*     */     catch (Exception e) {
/* 570 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean hasTag(String tag) {
/* 575 */     for (int i = 0; i < this.boughtItems.size(); i++) {
/* 576 */       if (((KitItem)this.boughtItems.get(i)).tag.toLowerCase().equals(tag.toLowerCase())) {
/* 577 */         return true;
/*     */       }
/*     */     }
/* 580 */     return false;
/*     */   }
/*     */ 
/*     */   public org.bukkit.inventory.ItemStack getItemStack(int id, KitEnchantment e) {
/* 584 */     org.bukkit.inventory.ItemStack itm = new org.bukkit.inventory.ItemStack(Material.getMaterial(id), 1);
/* 585 */     if (e != null) {
/* 586 */       e.add(itm);
/*     */     }
/* 588 */     return itm;
/*     */   }
/*     */ 
/*     */   public void giveItem(Player p, int weapon1, KitEnchantment ench, byte dat, int amt, int slot) {
/* 592 */     if (weapon1 > 0) {
/* 593 */       Material mat = Material.getMaterial(weapon1);
/* 594 */       if ((mat != null) && 
/* 595 */         (!mat.equals(Material.AIR))) {
/* 596 */         MaterialData data = new MaterialData(mat);
/* 597 */         data.setData(dat);
/* 598 */         org.bukkit.inventory.ItemStack itm = data.toItemStack(amt);
/* 599 */         if (ench != null) {
/* 600 */           ench.add(itm);
/*     */         }
/* 602 */         p.getInventory().setItem(slot, itm);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getRank()
/*     */   {
/* 609 */     if ((this.profile.tag != null) && 
/* 610 */       (this.profile.tag.length() > 0)) {
/* 611 */       return this.profile.tag;
/*     */     }
/*     */ 
/* 614 */     if (this.profile.level > 30) {
/* 615 */       return "GOD";
/*     */     }
/* 617 */     switch (this.profile.level) { case 1:
/* 618 */       return "PVT";
/*     */     case 2:
/* 619 */       return "PV2";
/*     */     case 3:
/* 620 */       return "PFC";
/*     */     case 4:
/* 621 */       return "SPC";
/*     */     case 5:
/* 622 */       return "CPL";
/*     */     case 6:
/* 623 */       return "SGT";
/*     */     case 7:
/* 624 */       return "SSG";
/*     */     case 8:
/* 625 */       return "SFC";
/*     */     case 9:
/* 626 */       return "MSG";
/*     */     case 10:
/* 627 */       return "1SG";
/*     */     case 11:
/* 628 */       return "SGM";
/*     */     case 12:
/* 629 */       return "CSM";
/*     */     case 13:
/* 630 */       return "SMA";
/*     */     case 14:
/* 631 */       return "WO1";
/*     */     case 15:
/* 632 */       return "CW2";
/*     */     case 16:
/* 633 */       return "CW3";
/*     */     case 17:
/* 634 */       return "CW4";
/*     */     case 18:
/* 635 */       return "CW5";
/*     */     case 19:
/* 636 */       return "2LT";
/*     */     case 20:
/* 637 */       return "1LT";
/*     */     case 21:
/* 638 */       return "CPT";
/*     */     case 22:
/* 639 */       return "MAJ";
/*     */     case 23:
/* 640 */       return "LTC";
/*     */     case 24:
/* 641 */       return "COL";
/*     */     case 25:
/* 642 */       return "BG";
/*     */     case 26:
/* 643 */       return "MG";
/*     */     case 27:
/* 644 */       return "LTG";
/*     */     case 28:
/* 645 */       return "GEN";
/*     */     case 29:
/* 646 */       return "MLG";
/*     */     case 30:
/* 647 */       return "PRO";
/*     */     }
/* 649 */     return "NUB";
/*     */   }
/*     */ 
/*     */   public String getTag() {
/* 653 */     ChatColor color = getTeamColor();
/* 654 */     String prefix = getRank();
/* 655 */     String lol = this.player.getName();
/* 656 */     String n = prefix + color + lol;
/* 657 */     return n;
/*     */   }
/*     */ 
/*     */   public void calculate()
/*     */   {
/* 662 */     if (this.profile.xp >= this.profile.xpn) {
/* 663 */       this.profile.xp -= this.profile.xpn;
/* 664 */       this.profile.level += 1;
/*     */ 
/* 666 */       if (this.profile.level < 1)
/* 667 */         this.profile.level = 1;
/* 668 */       this.profile.xpn = getXpto(this.profile.level);
/* 669 */       if (this.profile.xpn <= 0) {
/* 670 */         this.profile.xpn = 125;
/*     */       }
/* 672 */       this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 20.0F, 1.0F);
/*     */ 
/* 674 */       sayMessage(null, ChatColor.GREEN + "Level Gained!");
/* 675 */       sayMessage(null, ChatColor.GRAY + "        You are now level " + ChatColor.YELLOW + this.profile.level);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getXpto(int level) {
/* 680 */     return (int)Math.floor(Math.pow(50 * level, 1.41D) / 10.0D) * 10;
/*     */   }
/*     */ 
/*     */   private int getAmountXpToLevel(int level)
/*     */   {
/* 685 */     int count = 0;
/* 686 */     for (int i = 0; i < level; i++) {
/* 687 */       count += getXpto(i);
/*     */     }
/* 689 */     return count;
/*     */   }
/*     */ 
/*     */   public void giveXp(int i) {
/* 693 */     this.profile.xp += i;
/* 694 */     this.profile.gainxp += i;
/*     */   }
/*     */ 
/*     */   public void onDamaged(EntityDamageByEntityEvent event) {
/* 698 */     if (hasPerk("juggernaut"))
/* 699 */       event.setDamage((int)(event.getDamage() / 2.0D));
/*     */   }
/*     */ 
/*     */   public void onAttack(EntityDamageByEntityEvent event) {
/* 703 */     if (hasPerk("stoppingpower"))
/* 704 */       event.setDamage(event.getDamage() + 2);
/*     */   }
/*     */ 
/*     */   public void onKill(KitPlayer kp) {
/* 708 */     this.killStreak += 1;
/* 709 */     if (hasPerk("scavenger")) {
/* 710 */       addAmmo(10);
/*     */     }
/* 712 */     doKillStreak();
/*     */   }
/*     */ 
/*     */   private int getAmtAmmo(String string) {
/* 716 */     if (this.player == null)
/* 717 */       return 0;
/* 718 */     if (!this.player.isOnline())
/* 719 */       return 0;
/* 720 */     if (string.equals("primary")) {
/* 721 */       int itemid = this.plugin.getGunAmmo(this.kclass.primary);
/* 722 */       return InventoryHelper.amtItem(this.player.getInventory(), itemid);
/*     */     }
/* 724 */     int itemid = this.plugin.getGunAmmo(this.kclass.secondary);
/* 725 */     return InventoryHelper.amtItem(this.player.getInventory(), itemid);
/*     */   }
/*     */ 
/*     */   public void addAmmo(int amt)
/*     */   {
/*     */     try {
/* 731 */       org.bukkit.inventory.ItemStack itm1 = new org.bukkit.inventory.ItemStack(this.plugin.getGunAmmo(this.kclass.primary), amt);
/* 732 */       org.bukkit.inventory.ItemStack itm2 = new org.bukkit.inventory.ItemStack(this.plugin.getGunAmmo(this.kclass.secondary), (int)(amt * 1.5D));
/* 733 */       int slot = InventoryHelper.getItemPosition(this.player.getInventory(), itm1.getType());
/* 734 */       int slot2 = InventoryHelper.getItemPosition(this.player.getInventory(), itm2.getType());
/*     */ 
/* 736 */       if (slot > -1)
/* 737 */         this.player.getInventory().getItem(slot).setAmount(this.player.getInventory().getItem(slot).getAmount() + itm1.getAmount());
/*     */       else
/* 739 */         this.player.getInventory().setItem(InventoryHelper.getFirstFreeSlot(this.player.getInventory()), itm1);
/* 740 */       if (slot2 > -1)
/* 741 */         this.player.getInventory().getItem(slot2).setAmount(this.player.getInventory().getItem(slot2).getAmount() + itm2.getAmount());
/*     */       else
/* 743 */         this.player.getInventory().setItem(InventoryHelper.getFirstFreeSlot(this.player.getInventory()), itm2);
/*     */     } catch (Exception e) {
/* 745 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onDeath() {
/* 750 */     this.killStreak = 0;
/* 751 */     this.alive = 0;
/* 752 */     this.lives -= 1;
/* 753 */     this.dead = true;
/*     */   }
/*     */ 
/*     */   public void doKillStreak() {
/* 757 */     if ((this.killStreak == 5) && (!this.arena.gameModifier.equals(KitArena.GameModeModifier.ONEINCHAMBER))) {
/* 758 */       for (int i = 0; i < 4; i++)
/* 759 */         sayMessage(null, ChatColor.AQUA + "5 Killstreak! Unlocked Ammo!");
/* 760 */       addAmmo(20);
/*     */     }
/*     */ 
/* 763 */     if ((this.killStreak == 21) && (!this.arena.gameModifier.equals(KitArena.GameModeModifier.ONEINCHAMBER)) && (!this.arena.gameModifier.equals(KitArena.GameModeModifier.INFECT))) {
/* 764 */       this.arena.broadcastMessage(getTag() + ChatColor.BOLD + ChatColor.RED + " LAUNCHER TACTICAL NUKE!");
/* 765 */       for (int i = this.arena.players.size() - 1; i >= 0; i--) {
/* 766 */         KitPlayer kp = (KitPlayer)this.arena.players.get(i);
/* 767 */         if ((kp != null) && 
/* 768 */           (kp.player != null) && (kp.player.isOnline()) && 
/* 769 */           (kp.team != this.team))
/* 770 */           kp.player.damage(9999, this.player);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean hasPerk(String string)
/*     */   {
/* 779 */     for (int i = 0; i < this.perks.size(); i++) {
/* 780 */       Perk p = (Perk)this.perks.get(i);
/* 781 */       if (p.name.toLowerCase().equals(string.toLowerCase())) {
/* 782 */         return true;
/*     */       }
/*     */     }
/* 785 */     return false;
/*     */   }
/*     */ 
/*     */   public void checkInventory() {
/* 789 */     if (this.player.getItemOnCursor().getTypeId() == Material.WOOL.getId())
/* 790 */       this.player.setItemOnCursor(null);
/*     */   }
/*     */ 
/*     */   public void CLEARPLAYER()
/*     */   {
/* 795 */     this.profile.CLEAR();
/* 796 */     this.kclass = null;
/* 797 */     this.player = null;
/* 798 */     this.boughtItems.clear();
/* 799 */     for (int i = 0; i < this.perks.size(); i++) {
/* 800 */       ((Perk)this.perks.get(i)).clear();
/*     */     }
/* 802 */     this.perks.clear();
/*     */   }
/*     */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.arena.KitPlayer
 * JD-Core Version:    0.6.2
 */