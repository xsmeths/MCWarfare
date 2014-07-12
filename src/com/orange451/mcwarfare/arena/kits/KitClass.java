/*     */ package com.orange451.mcwarfare.arena.kits;
/*     */ 
/*     */ import com.orange451.mcwarfare.KitPvP;
/*     */ import com.orange451.mcwarfare.arena.KitEnchantment;
/*     */ import com.orange451.mcwarfare.arena.KitGun;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ public class KitClass
/*     */ {
/*     */   public KitPvP plugin;
/*     */   public String name;
/*  24 */   public String permissionNode = "";
/*  25 */   public String primary = "m16";
/*  26 */   public String secondary = "usp45";
/*     */   public Player player;
/*  28 */   public int armor0 = 0;
/*  29 */   public int armor1 = 0;
/*  30 */   public int armor2 = 0;
/*  31 */   public int armor3 = 0;
/*  32 */   public int weapon1 = 0;
/*  33 */   public int weapon2 = 0;
/*  34 */   public int weapon3 = 0;
/*  35 */   public int weapon4 = 0;
/*  36 */   public int weapon5 = 0;
/*  37 */   public int weapon6 = 0;
/*  38 */   public int weapon7 = 0;
/*  39 */   public int weapon8 = 0;
/*  40 */   public int weapon9 = 0;
/*  41 */   public ArrayList<PotionEffect> pots = new ArrayList();
/*  42 */   public KitEnchantment enchanthelmet = new KitEnchantment();
/*  43 */   public KitEnchantment enchantchest = new KitEnchantment();
/*  44 */   public KitEnchantment enchantlegs = new KitEnchantment();
/*  45 */   public KitEnchantment enchantboots = new KitEnchantment();
/*  46 */   public KitEnchantment enchant1 = new KitEnchantment();
/*  47 */   public KitEnchantment enchant2 = new KitEnchantment();
/*  48 */   public KitEnchantment enchant3 = new KitEnchantment();
/*  49 */   public KitEnchantment enchant4 = new KitEnchantment();
/*  50 */   public KitEnchantment enchant5 = new KitEnchantment();
/*  51 */   public KitEnchantment enchant6 = new KitEnchantment();
/*  52 */   public KitEnchantment enchant7 = new KitEnchantment();
/*  53 */   public KitEnchantment enchant8 = new KitEnchantment();
/*  54 */   public KitEnchantment enchant9 = new KitEnchantment();
/*  55 */   public int amt1 = 1;
/*  56 */   public int amt2 = 1;
/*  57 */   public int amt3 = 1;
/*  58 */   public int amt4 = 1;
/*  59 */   public int amt5 = 1;
/*  60 */   public int amt6 = 1;
/*  61 */   public int amt7 = 1;
/*  62 */   public int amt8 = 1;
/*  63 */   public int amt9 = 1;
/*  64 */   public byte special1 = 0;
/*  65 */   public byte special2 = 0;
/*  66 */   public byte special3 = 0;
/*  67 */   public byte special4 = 0;
/*  68 */   public byte special5 = 0;
/*  69 */   public byte special6 = 0;
/*  70 */   public byte special7 = 0;
/*  71 */   public byte special8 = 0;
/*  72 */   public byte special9 = 0;
/*  73 */   public boolean loaded = true;
/*     */   public File file;
/*     */   public ArrayList<String> tocompute;
/*     */ 
/*     */   public KitClass(KitPvP plugin, File file)
/*     */   {
/*  78 */     this.plugin = plugin;
/*  79 */     this.file = file;
/*  80 */     load();
/*     */   }
/*     */ 
/*     */   public KitClass(KitPvP plugin, Player player) {
/*  84 */     this.plugin = plugin;
/*  85 */     this.player = player;
/*     */   }
/*     */ 
/*     */   public void load() {
/*  89 */     this.name = this.file.getName();
/*  90 */     ArrayList file = new ArrayList();
/*     */     try {
/*  92 */       FileInputStream fstream = new FileInputStream(this.file.getAbsolutePath());
/*  93 */       DataInputStream in = new DataInputStream(fstream);
/*  94 */       BufferedReader br = new BufferedReader(new InputStreamReader(in));
/*     */       String strLine;
/*  96 */       while ((strLine = br.readLine()) != null)
/*     */       {
/*     */         String strLine;
/*  97 */         file.add(strLine);
/*     */       }
/*  99 */       br.close();
/* 100 */       in.close();
/* 101 */       fstream.close();
/*     */     } catch (Exception e) {
/* 103 */       System.err.println("Error: " + e.getMessage());
/*     */     }
/*     */ 
/* 106 */     for (int i = 0; i < file.size(); i++) {
/* 107 */       computeData((String)file.get(i));
/*     */     }
/* 109 */     if (this.loaded)
/* 110 */       System.out.println("[KITPVP] CLASS LOADED: " + this.name);
/*     */     else
/* 112 */       System.out.println("[KITPVP] ERROR LOADING CLASS: " + this.name);
/*     */   }
/*     */ 
/*     */   public int readWep(String[] str)
/*     */   {
/* 117 */     int ret = 0;
/* 118 */     for (int i = 0; i < str.length; i++) {
/* 119 */       String st = str[i];
/* 120 */       if (st.startsWith("i:")) {
/* 121 */         String news = st.substring(2);
/* 122 */         int ii = Integer.parseInt(news);
/* 123 */         ret = ii;
/*     */       }
/*     */     }
/* 126 */     return ret;
/*     */   }
/*     */ 
/*     */   public int readDat(String[] str) {
/* 130 */     int ret = 0;
/* 131 */     for (int i = 0; i < str.length; i++) {
/* 132 */       String st = str[i];
/* 133 */       if (st.startsWith("d:")) {
/* 134 */         String news = st.substring(2);
/* 135 */         int ii = Integer.parseInt(news);
/* 136 */         ret = ii;
/*     */       }
/*     */     }
/* 139 */     return ret;
/*     */   }
/*     */ 
/*     */   public KitEnchantment readEnchantment(String[] str) {
/* 143 */     KitEnchantment ret = new KitEnchantment();
/* 144 */     for (int i = 0; i < str.length; i++) {
/* 145 */       String st = str[i];
/* 146 */       if (st.startsWith("e:")) {
/* 147 */         String news = st.substring(2);
/* 148 */         int level = 1;
/* 149 */         if (news.contains("*")) {
/* 150 */           String nn = news.substring(news.indexOf('*') + 1);
/* 151 */           news = news.substring(0, news.indexOf('*'));
/*     */           try {
/* 153 */             level = Integer.parseInt(nn);
/*     */           }
/*     */           catch (Exception localException) {
/*     */           }
/*     */         }
/* 158 */         Enchantment temp = Enchantment.getByName(news.toUpperCase());
/* 159 */         if (temp != null) {
/* 160 */           ret.enchantments.add(temp);
/* 161 */           ret.levels.add(Integer.valueOf(level));
/*     */         }
/*     */       }
/*     */     }
/* 165 */     return ret;
/*     */   }
/*     */ 
/*     */   public void readPotion(String[] str) {
/* 169 */     for (int i = 0; i < str.length; i++) {
/* 170 */       String news = str[i];
/* 171 */       int level = 1;
/* 172 */       if (news.contains("*")) {
/* 173 */         String nn = news.substring(news.indexOf('*') + 1);
/* 174 */         news = news.substring(0, news.indexOf('*'));
/*     */         try {
/* 176 */           level = Integer.parseInt(nn);
/*     */         }
/*     */         catch (Exception localException) {
/*     */         }
/*     */       }
/* 181 */       PotionEffectType pet = PotionEffectType.getByName(news);
/* 182 */       if (pet != null) {
/* 183 */         PotionEffect po = new PotionEffect(PotionEffectType.getByName(news), 9999, level);
/* 184 */         this.pots.add(po);
/* 185 */         System.out.println("[KITPVP] loaded potion: " + news + " into Kit Class: " + this.name);
/*     */       } else {
/* 187 */         System.out.println("[KITPVP] error loading potion: " + news + " into Kit Class: " + this.name);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int readAmt(String[] str) {
/* 193 */     int ret = 1;
/* 194 */     for (int i = 0; i < str.length; i++) {
/* 195 */       String st = str[i];
/* 196 */       if (st.startsWith("a:")) {
/* 197 */         String news = st.substring(2);
/* 198 */         int ii = Integer.parseInt(news);
/* 199 */         ret = ii;
/*     */       }
/*     */     }
/* 202 */     return ret;
/*     */   }
/*     */ 
/*     */   public void computeData(String str) {
/*     */     try {
/* 207 */       if (str.indexOf("=") >= 1) {
/* 208 */         String str2 = str.substring(0, str.indexOf("="));
/* 209 */         if (str2.equalsIgnoreCase("primary")) {
/* 210 */           String line = str.substring(str.indexOf("=") + 1);
/* 211 */           this.primary = line;
/*     */         }
/* 213 */         if (str2.equalsIgnoreCase("secondary")) {
/* 214 */           String line = str.substring(str.indexOf("=") + 1);
/* 215 */           this.secondary = line;
/*     */         }
/*     */ 
/* 218 */         if (str2.equalsIgnoreCase("helmet")) {
/* 219 */           String line = str.substring(str.indexOf("=") + 1);
/* 220 */           String[] strarr = line.split(",");
/* 221 */           this.armor0 = readWep(strarr);
/* 222 */           this.enchanthelmet = readEnchantment(strarr);
/*     */         }
/* 224 */         if (str2.equalsIgnoreCase("chestplate")) {
/* 225 */           String line = str.substring(str.indexOf("=") + 1);
/* 226 */           String[] strarr = line.split(",");
/* 227 */           this.armor1 = readWep(strarr);
/* 228 */           this.enchantchest = readEnchantment(strarr);
/*     */         }
/* 230 */         if (str2.equalsIgnoreCase("leggings")) {
/* 231 */           String line = str.substring(str.indexOf("=") + 1);
/* 232 */           String[] strarr = line.split(",");
/* 233 */           this.armor2 = readWep(strarr);
/* 234 */           this.enchantlegs = readEnchantment(strarr);
/*     */         }
/* 236 */         if (str2.equalsIgnoreCase("boots")) {
/* 237 */           String line = str.substring(str.indexOf("=") + 1);
/* 238 */           String[] strarr = line.split(",");
/* 239 */           this.armor3 = readWep(strarr);
/* 240 */           this.enchantboots = readEnchantment(strarr);
/*     */         }
/* 242 */         if (str2.equalsIgnoreCase("node")) {
/* 243 */           String line = str.substring(str.indexOf("=") + 1);
/* 244 */           this.permissionNode = line;
/*     */         }
/* 246 */         if (str2.equalsIgnoreCase("tool1")) {
/* 247 */           String line = str.substring(str.indexOf("=") + 1);
/* 248 */           String[] strarr = line.split(",");
/* 249 */           int value = readWep(strarr);
/* 250 */           int value2 = readDat(strarr);
/* 251 */           int value3 = readAmt(strarr);
/* 252 */           KitEnchantment value4 = readEnchantment(strarr);
/* 253 */           this.weapon1 = value;
/* 254 */           this.special1 = ((byte)value2);
/* 255 */           this.amt1 = value3;
/* 256 */           this.enchant1 = value4;
/*     */         }
/*     */ 
/* 259 */         if (str2.equalsIgnoreCase("tool2")) {
/* 260 */           String line = str.substring(str.indexOf("=") + 1);
/* 261 */           String[] strarr = line.split(",");
/* 262 */           int value = readWep(strarr);
/* 263 */           int value2 = readDat(strarr);
/* 264 */           int value3 = readAmt(strarr);
/* 265 */           KitEnchantment value4 = readEnchantment(strarr);
/* 266 */           this.weapon2 = value;
/* 267 */           this.special2 = ((byte)value2);
/* 268 */           this.amt2 = value3;
/* 269 */           this.enchant2 = value4;
/*     */         }
/*     */ 
/* 272 */         if (str2.equalsIgnoreCase("tool3")) {
/* 273 */           String line = str.substring(str.indexOf("=") + 1);
/* 274 */           String[] strarr = line.split(",");
/* 275 */           int value = readWep(strarr);
/* 276 */           int value2 = readDat(strarr);
/* 277 */           int value3 = readAmt(strarr);
/* 278 */           KitEnchantment value4 = readEnchantment(strarr);
/* 279 */           this.weapon3 = value;
/* 280 */           this.special3 = ((byte)value2);
/* 281 */           this.amt3 = value3;
/* 282 */           this.enchant3 = value4;
/*     */         }
/*     */ 
/* 285 */         if (str2.equalsIgnoreCase("tool4")) {
/* 286 */           String line = str.substring(str.indexOf("=") + 1);
/* 287 */           String[] strarr = line.split(",");
/* 288 */           int value = readWep(strarr);
/* 289 */           int value2 = readDat(strarr);
/* 290 */           int value3 = readAmt(strarr);
/* 291 */           KitEnchantment value4 = readEnchantment(strarr);
/* 292 */           this.weapon4 = value;
/* 293 */           this.special4 = ((byte)value2);
/* 294 */           this.amt4 = value3;
/* 295 */           this.enchant4 = value4;
/*     */         }
/*     */ 
/* 298 */         if (str2.equalsIgnoreCase("tool5")) {
/* 299 */           String line = str.substring(str.indexOf("=") + 1);
/* 300 */           String[] strarr = line.split(",");
/* 301 */           int value = readWep(strarr);
/* 302 */           int value2 = readDat(strarr);
/* 303 */           int value3 = readAmt(strarr);
/* 304 */           KitEnchantment value4 = readEnchantment(strarr);
/* 305 */           this.weapon5 = value;
/* 306 */           this.special5 = ((byte)value2);
/* 307 */           this.amt5 = value3;
/* 308 */           this.enchant5 = value4;
/*     */         }
/*     */ 
/* 311 */         if (str2.equalsIgnoreCase("tool6")) {
/* 312 */           String line = str.substring(str.indexOf("=") + 1);
/* 313 */           String[] strarr = line.split(",");
/* 314 */           int value = readWep(strarr);
/* 315 */           int value2 = readDat(strarr);
/* 316 */           int value3 = readAmt(strarr);
/* 317 */           KitEnchantment value4 = readEnchantment(strarr);
/* 318 */           this.weapon6 = value;
/* 319 */           this.special6 = ((byte)value2);
/* 320 */           this.amt6 = value3;
/* 321 */           this.enchant6 = value4;
/*     */         }
/*     */ 
/* 324 */         if (str2.equalsIgnoreCase("tool7")) {
/* 325 */           String line = str.substring(str.indexOf("=") + 1);
/* 326 */           String[] strarr = line.split(",");
/* 327 */           int value = readWep(strarr);
/* 328 */           int value2 = readDat(strarr);
/* 329 */           int value3 = readAmt(strarr);
/* 330 */           KitEnchantment value4 = readEnchantment(strarr);
/* 331 */           this.weapon7 = value;
/* 332 */           this.special7 = ((byte)value2);
/* 333 */           this.amt7 = value3;
/* 334 */           this.enchant7 = value4;
/*     */         }
/*     */ 
/* 337 */         if (str2.equalsIgnoreCase("tool8")) {
/* 338 */           String line = str.substring(str.indexOf("=") + 1);
/* 339 */           String[] strarr = line.split(",");
/* 340 */           int value = readWep(strarr);
/* 341 */           int value2 = readDat(strarr);
/*     */ 
/* 343 */           KitEnchantment value4 = readEnchantment(strarr);
/* 344 */           this.weapon8 = value;
/* 345 */           this.special8 = ((byte)value2);
/* 346 */           if (this.player != null) {
/* 347 */             this.amt8 = this.plugin.getAmmo(this.player, "primary");
/*     */           } else {
/* 349 */             int value3 = readAmt(strarr);
/* 350 */             this.amt8 = value3;
/*     */           }
/* 352 */           this.enchant8 = value4;
/*     */         }
/*     */ 
/* 355 */         if (str2.equalsIgnoreCase("tool9")) {
/* 356 */           String line = str.substring(str.indexOf("=") + 1);
/* 357 */           String[] strarr = line.split(",");
/* 358 */           int value = readWep(strarr);
/* 359 */           int value2 = readDat(strarr);
/* 360 */           KitEnchantment value4 = readEnchantment(strarr);
/* 361 */           this.weapon9 = value;
/* 362 */           this.special9 = ((byte)value2);
/* 363 */           if (this.player != null) {
/* 364 */             this.amt9 = this.plugin.getAmmo(this.player, "secondary");
/*     */           } else {
/* 366 */             int value3 = readAmt(strarr);
/* 367 */             this.amt9 = value3;
/*     */           }
/* 369 */           this.enchant9 = value4;
/*     */         }
/*     */ 
/* 372 */         if (str2.equalsIgnoreCase("addpot")) {
/* 373 */           String line = str.substring(str.indexOf("=") + 1);
/* 374 */           String[] strarr = line.split(",");
/* 375 */           readPotion(strarr);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 380 */       this.loaded = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ArrayList<String> dumpClass() {
/* 385 */     if (this.player != null) {
/* 386 */       Plugin p = Bukkit.getPluginManager().getPlugin("PVPGun");
/* 387 */       if ((p != null) && (p.isEnabled()))
/*     */       {
/* 389 */         ArrayList ret = new ArrayList();
/* 390 */         ret.add(":defclass:");
/* 391 */         ret.add("primary=" + this.primary);
/* 392 */         ret.add("secondary=" + this.secondary);
/* 393 */         ret.add("helmet=i:" + Integer.toString(this.armor0) + ",a:1,d:0" + this.enchanthelmet.dumpEnchant());
/* 394 */         ret.add("chestplate=i:" + Integer.toString(this.armor1) + ",a:1,d:0" + this.enchantchest.dumpEnchant());
/* 395 */         ret.add("leggings=i:" + Integer.toString(this.armor2) + ",a:1,d:0" + this.enchantlegs.dumpEnchant());
/* 396 */         ret.add("boots=i:" + Integer.toString(this.armor3) + ",a:1,d:0" + this.enchantboots.dumpEnchant());
/* 397 */         ret.add("tool1=i:" + Integer.toString(this.weapon1) + ",a:" + Integer.toString(this.amt1) + ",d:" + Byte.toString(this.special1) + this.enchant1.dumpEnchant());
/* 398 */         ret.add("tool2=i:" + this.plugin.getGun(this.primary).type);
/* 399 */         ret.add("tool3=i:" + this.plugin.getGun(this.secondary).type);
/*     */ 
/* 402 */         ret.add("tool4=i:" + Integer.toString(this.weapon4) + ",a:" + Integer.toString(this.amt4) + ",d:" + Byte.toString(this.special4) + this.enchant4.dumpEnchant());
/* 403 */         ret.add("tool5=i:" + Integer.toString(this.weapon5) + ",a:" + Integer.toString(this.amt5) + ",d:" + Byte.toString(this.special5) + this.enchant5.dumpEnchant());
/* 404 */         ret.add("tool6=i:" + Integer.toString(this.weapon6) + ",a:" + Integer.toString(this.amt6) + ",d:" + Byte.toString(this.special6) + this.enchant6.dumpEnchant());
/* 405 */         ret.add("tool7=i:" + Integer.toString(this.weapon7) + ",a:" + Integer.toString(this.amt7) + ",d:" + Byte.toString(this.special7) + this.enchant7.dumpEnchant());
/*     */ 
/* 407 */         ret.add("tool8=i:" + this.plugin.getGunAmmo(this.primary) + ",a:" + this.plugin.getAmmo(this.player, "primary"));
/* 408 */         ret.add("tool9=i:" + this.plugin.getGunAmmo(this.secondary) + ",a:" + this.plugin.getAmmo(this.player, "secondary"));
/*     */ 
/* 412 */         for (int i = 0; i < this.pots.size(); i++) {
/* 413 */           PotionEffect pet = (PotionEffect)this.pots.get(i);
/* 414 */           String pname = pet.getType().getName().toUpperCase();
/* 415 */           ret.add("addpot=" + pname + "*" + pet.getAmplifier());
/*     */         }
/* 417 */         ret.add(":endclass:");
/* 418 */         return ret;
/*     */       }
/*     */     }
/*     */ 
/* 422 */     System.out.println("SAVING BLANK PLAYER");
/* 423 */     ArrayList ret = new ArrayList();
/* 424 */     ret.add(":defclass:");
/* 425 */     ret.add("primary=" + this.primary);
/* 426 */     ret.add("secondary=" + this.secondary);
/* 427 */     ret.add("helmet=i:" + Integer.toString(this.armor0) + ",a:1,d:0" + this.enchanthelmet.dumpEnchant());
/* 428 */     ret.add("chestplate=i:" + Integer.toString(this.armor1) + ",a:1,d:0" + this.enchantchest.dumpEnchant());
/* 429 */     ret.add("leggings=i:" + Integer.toString(this.armor2) + ",a:1,d:0" + this.enchantlegs.dumpEnchant());
/* 430 */     ret.add("boots=i:" + Integer.toString(this.armor3) + ",a:1,d:0" + this.enchantboots.dumpEnchant());
/* 431 */     ret.add("tool1=i:" + Integer.toString(this.weapon1) + ",a:" + Integer.toString(this.amt1) + ",d:" + Byte.toString(this.special1) + this.enchant1.dumpEnchant());
/* 432 */     ret.add("tool2=i:" + this.plugin.getGun(this.primary).type);
/* 433 */     ret.add("tool3=i:" + this.plugin.getGun(this.secondary).type);
/*     */ 
/* 436 */     ret.add("tool4=i:" + Integer.toString(this.weapon4) + ",a:" + Integer.toString(this.amt4) + ",d:" + Byte.toString(this.special4) + this.enchant4.dumpEnchant());
/* 437 */     ret.add("tool5=i:" + Integer.toString(this.weapon5) + ",a:" + Integer.toString(this.amt5) + ",d:" + Byte.toString(this.special5) + this.enchant5.dumpEnchant());
/* 438 */     ret.add("tool6=i:" + Integer.toString(this.weapon6) + ",a:" + Integer.toString(this.amt6) + ",d:" + Byte.toString(this.special6) + this.enchant6.dumpEnchant());
/* 439 */     ret.add("tool7=i:" + Integer.toString(this.weapon7) + ",a:" + Integer.toString(this.amt7) + ",d:" + Byte.toString(this.special7) + this.enchant7.dumpEnchant());
/*     */ 
/* 443 */     ret.add("tool8=i:" + Integer.toString(this.weapon8) + ",a:24,d:" + Byte.toString(this.special8) + this.enchant8.dumpEnchant());
/* 444 */     ret.add("tool9=i:" + Integer.toString(this.weapon9) + ",a:24,d:" + Byte.toString(this.special9) + this.enchant9.dumpEnchant());
/* 445 */     for (int i = 0; i < this.pots.size(); i++) {
/* 446 */       PotionEffect pet = (PotionEffect)this.pots.get(i);
/* 447 */       String pname = pet.getType().getName().toUpperCase();
/* 448 */       ret.add("addpot=" + pname + "*" + pet.getAmplifier());
/*     */     }
/* 450 */     ret.add(":endclass:");
/* 451 */     return ret;
/*     */   }
/*     */ 
/*     */   public void update() {
/* 455 */     this.weapon2 = this.plugin.getGun(this.primary).type;
/* 456 */     this.weapon3 = this.plugin.getGun(this.secondary).type;
/*     */ 
/* 458 */     this.weapon8 = this.plugin.getGunAmmo(this.primary);
/* 459 */     this.weapon9 = this.plugin.getGunAmmo(this.secondary);
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 463 */     this.file = null;
/* 464 */     this.player = null;
/* 465 */     this.pots.clear();
/*     */   }
/*     */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.arena.kits.KitClass
 * JD-Core Version:    0.6.2
 */