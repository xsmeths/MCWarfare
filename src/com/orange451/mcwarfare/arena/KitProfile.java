/*     */ package com.orange451.mcwarfare.arena;
/*     */ 
/*     */ import com.orange451.mcwarfare.FileIO;
/*     */ import com.orange451.mcwarfare.KitPvP;
/*     */ import com.orange451.mcwarfare.arena.kits.KitClass;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class KitProfile
/*     */ {
/*  15 */   public int level = 1;
/*     */   public int xp;
/*     */   public int gainxp;
/*     */   public int kills;
/*     */   public int deaths;
/*  20 */   public ArrayList<String> myContents = new ArrayList();
/*  21 */   public ArrayList<KitClass> classes = new ArrayList();
/*  22 */   public ArrayList<String> boughtGuns = new ArrayList();
/*     */   public ArrayList<String> compute;
/*  24 */   public int myclass = 0;
/*  25 */   public int credits = 0;
/*     */   public int xpn;
/*     */   public KitPvP plugin;
/*     */   public Player player;
/*  29 */   public String perk = "";
/*  30 */   public String tag = "";
/*  31 */   public ArrayList<String> perks = new ArrayList();
/*     */   public long lastModified;
/*     */   public String filename;
/*     */ 
/*     */   public KitProfile(KitPvP plugin, Player player, ArrayList<String> compute)
/*     */   {
/*  36 */     this.plugin = plugin;
/*  37 */     this.compute = compute;
/*  38 */     this.player = player;
/*  39 */     this.filename = (player.getName() + ".mcw");
/*  40 */     File f = getFile(player.getName());
/*  41 */     if (f != null)
/*  42 */       this.lastModified = f.lastModified();
/*     */   }
/*     */ 
/*     */   public KitProfile(KitPvP plugin, String player, ArrayList<String> compute) {
/*  46 */     this.plugin = plugin;
/*  47 */     this.compute = compute;
/*  48 */     this.player = null;
/*  49 */     this.filename = (player + ".mcw");
/*  50 */     File f = getFile(player);
/*  51 */     if (f != null)
/*  52 */       this.lastModified = f.lastModified();
/*     */   }
/*     */ 
/*     */   public File getFile(String name) {
/*  56 */     KitPvP plugin = (KitPvP)Bukkit.getPluginManager().getPlugin("MCWarfare");
/*  57 */     if (plugin != null) {
/*  58 */       String path = plugin.getUsers() + "/" + name + ".mcw";
/*  59 */       File f = new File(path);
/*  60 */       return f;
/*     */     }
/*  62 */     return null;
/*     */   }
/*     */ 
/*     */   public void save(KitPlayer p) {
/*     */     try {
/*  67 */       KitProfile kp = this.plugin.getProfile(p);
/*  68 */       if (kp != null) {
/*     */         try {
/*  70 */           int nxp = kp.xp;
/*  71 */           if ((nxp > this.xp) && (kp.lastModified > this.lastModified)) {
/*  72 */             this.xp = nxp;
/*  73 */             kp.getFile(p.player.getName());
/*     */           }
/*  75 */           addNewGuns(kp.boughtGuns);
/*  76 */           addNewPerks(kp.perks);
/*     */         }
/*     */         catch (Exception localException1) {
/*     */         }
/*  80 */         kp.CLEAR();
/*  81 */         kp = null;
/*     */       }
/*  83 */       String path = p.plugin.getUsers() + "/" + p.name + ".mcw";
/*  84 */       saveToPath(path);
/*     */     } catch (Exception e) {
/*  86 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void removeMultipleGuns()
/*     */   {
/*  92 */     for (int q = this.boughtGuns.size() - 1; q >= 0; q--)
/*  93 */       if (amtGun((String)this.boughtGuns.get(q)) > 1)
/*  94 */         this.boughtGuns.remove(q);
/*     */   }
/*     */ 
/*     */   private void addNewGuns(ArrayList<String> a)
/*     */   {
/*  99 */     for (int i = 0; i < a.size(); i++)
/* 100 */       if (!hasGun((String)a.get(i)))
/* 101 */         this.boughtGuns.add((String)a.get(i));
/*     */   }
/*     */ 
/*     */   private void addNewPerks(ArrayList<String> a)
/*     */   {
/* 107 */     for (int i = 0; i < a.size(); i++)
/* 108 */       if (!hasPerk((String)a.get(i)))
/* 109 */         this.perks.add((String)a.get(i));
/*     */   }
/*     */ 
/*     */   public void save(String player)
/*     */   {
/*     */     try
/*     */     {
/* 116 */       KitProfile kp = this.plugin.loadProfile(player);
/* 117 */       if (kp != null) {
/*     */         try {
/* 119 */           addNewGuns(kp.boughtGuns);
/* 120 */           addNewPerks(kp.perks);
/*     */ 
/* 122 */           if (kp.kills > this.kills)
/* 123 */             this.kills = kp.kills;
/* 124 */           if (kp.deaths > this.deaths)
/* 125 */             this.deaths = kp.deaths;
/* 126 */           if (kp.level > this.level) {
/* 127 */             this.level = kp.level;
/*     */           }
/* 129 */           int nxp = kp.xp;
/* 130 */           if ((nxp > this.xp) && (kp.lastModified > this.lastModified)) {
/* 131 */             this.xp = nxp;
/* 132 */             kp.getFile(player);
/*     */           }
/*     */         }
/*     */         catch (Exception localException) {
/*     */         }
/* 137 */         kp.CLEAR();
/* 138 */         kp = null;
/*     */       }
/* 140 */       KitPvP plugin = (KitPvP)Bukkit.getPluginManager().getPlugin("MCWarfare");
/* 141 */       if (plugin != null) {
/* 142 */         String path = plugin.getUsers() + "/" + player + ".mcw";
/* 143 */         saveToPath(path);
/*     */       }
/*     */     }
/*     */     catch (Exception localException1) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void saveToPath(String path) {
/* 151 */     if (this.classes.size() == 0)
/* 152 */       return; BufferedWriter out = FileIO.file_text_open_write(path);
/*     */     int i;
/*     */     try { FileIO.file_text_write_line(out, "xp=" + this.xp);
/* 156 */       FileIO.file_text_write_line(out, "level=" + this.level);
/* 157 */       FileIO.file_text_write_line(out, "credits=" + this.credits);
/* 158 */       FileIO.file_text_write_line(out, "kills=" + this.kills);
/* 159 */       FileIO.file_text_write_line(out, "deaths=" + this.deaths);
/* 160 */       FileIO.file_text_write_line(out, "perk=" + this.perk);
/* 161 */       FileIO.file_text_write_line(out, "tag=" + this.tag);
/* 162 */       FileIO.file_text_write_line(out, "--perks");
/* 163 */       for (int i = 0; i < this.perks.size(); i++) {
/* 164 */         FileIO.file_text_write_line(out, "::defperk=" + (String)this.perks.get(i));
/*     */       }
/* 166 */       FileIO.file_text_write_line(out, "--classes");
/* 167 */       boolean errorClass = false;
/* 168 */       for (int i = 0; i < this.classes.size(); i++) {
/*     */         try {
/* 170 */           ArrayList dump = ((KitClass)this.classes.get(i)).dumpClass();
/* 171 */           for (int ii = 0; ii < dump.size(); ii++)
/* 172 */             if (!FileIO.file_text_write_line(out, (String)dump.get(ii)))
/* 173 */               errorClass = true;
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 177 */           errorClass = true;
/*     */         }
/*     */       }
/* 180 */       if ((this.classes.size() == 0) || (errorClass)) {
/* 181 */         ArrayList dump = ((KitClass)((KitPvP)Bukkit.getPluginManager().getPlugin("MCWarfare")).classes.get(0)).dumpClass();
/* 182 */         for (int i = 0; i < dump.size(); i++) {
/* 183 */           FileIO.file_text_write_line(out, (String)dump.get(i));
/*     */         }
/*     */       }
/* 186 */       if (this.boughtGuns.size() == 0) {
/* 187 */         FileIO.file_text_write_line(out, "::defguns");
/* 188 */         FileIO.file_text_write_line(out, "m16");
/* 189 */         FileIO.file_text_write_line(out, "usp45");
/* 190 */         FileIO.file_text_write_line(out, "m1014");
/* 191 */         FileIO.file_text_write_line(out, "l118a");
/* 192 */         FileIO.file_text_write_line(out, "::endguns");
/*     */       } else {
/* 194 */         removeMultipleGuns();
/* 195 */         FileIO.file_text_write_line(out, "::defguns");
/* 196 */         for (int i = 0; i < this.boughtGuns.size(); i++) {
/* 197 */           FileIO.file_text_write_line(out, (String)this.boughtGuns.get(i));
/*     */         }
/* 199 */         FileIO.file_text_write_line(out, "::endguns");
/*     */       }
/*     */     } catch (Exception e) {
/* 202 */       FileIO.file_text_close(out);
/* 203 */       out = FileIO.file_text_open_write(path);
/* 204 */       i = 0; } for (; i < this.myContents.size(); i++) {
/* 205 */       FileIO.file_text_write_line(out, (String)this.myContents.get(i));
/*     */     }
/*     */ 
/* 208 */     FileIO.file_text_close(out);
/* 209 */     File f = getFile(this.player.getName());
/* 210 */     if (f != null)
/* 211 */       this.lastModified = f.lastModified();
/*     */   }
/*     */ 
/*     */   public void execute(KitPvP plugin) {
/* 215 */     boolean isloadingClass = false;
/* 216 */     boolean isloadingGuns = false;
/* 217 */     KitClass kc = null;
/* 218 */     for (int i = 0; i < this.compute.size(); i++) {
/* 219 */       String str = (String)this.compute.get(i);
/* 220 */       this.myContents.add(str);
/*     */       try {
/* 222 */         if (str.equalsIgnoreCase(":defclass:")) {
/* 223 */           kc = new KitClass(plugin, this.player);
/* 224 */           isloadingClass = true;
/*     */         }
/* 226 */         if (str.equalsIgnoreCase(":endclass:")) {
/* 227 */           this.classes.add(kc);
/* 228 */           isloadingClass = false;
/*     */         }
/* 230 */         if (str.equalsIgnoreCase("::defguns")) {
/* 231 */           isloadingGuns = true;
/*     */         }
/* 233 */         if (str.equalsIgnoreCase("::endguns")) {
/* 234 */           isloadingGuns = false;
/*     */         }
/* 236 */         if ((!isloadingClass) && (!isloadingGuns) && 
/* 237 */           (str.indexOf("=") >= 1)) {
/* 238 */           String str2 = str.substring(0, str.indexOf("="));
/* 239 */           if (str2.equalsIgnoreCase("xp")) {
/* 240 */             String line = str.substring(str.indexOf("=") + 1);
/* 241 */             this.xp = Integer.parseInt(line);
/* 242 */             if (this.xp < 0)
/* 243 */               this.xp = 0;
/*     */           }
/* 245 */           if (str2.equalsIgnoreCase("level")) {
/* 246 */             String line = str.substring(str.indexOf("=") + 1);
/* 247 */             this.level = Integer.parseInt(line);
/*     */           }
/* 249 */           if (str2.equalsIgnoreCase("kills")) {
/* 250 */             String line = str.substring(str.indexOf("=") + 1);
/* 251 */             this.kills = Integer.parseInt(line);
/*     */           }
/* 253 */           if (str2.equalsIgnoreCase("perk")) {
/* 254 */             this.perk = str.substring(str.indexOf("=") + 1);
/*     */           }
/* 256 */           if (str2.equalsIgnoreCase("::defperk")) {
/* 257 */             this.perks.add(str.substring(str.indexOf("=") + 1));
/*     */           }
/* 259 */           if (str2.equalsIgnoreCase("deaths")) {
/* 260 */             this.deaths = Integer.parseInt(str.substring(str.indexOf("=") + 1));
/*     */           }
/* 262 */           if (str2.equalsIgnoreCase("credits")) {
/* 263 */             this.credits = Integer.parseInt(str.substring(str.indexOf("=") + 1));
/*     */           }
/* 265 */           if (str2.equalsIgnoreCase("tag")) {
/* 266 */             this.tag = str.substring(str.indexOf("=") + 1);
/*     */           }
/*     */         }
/* 269 */         if (isloadingClass) {
/* 270 */           kc.computeData(str);
/*     */         }
/* 272 */         if ((isloadingGuns) && 
/* 273 */           (!str.equals("::defguns")))
/* 274 */           this.boughtGuns.add(str);
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int amtGun(String g)
/*     */   {
/* 284 */     int amt = 0;
/* 285 */     for (int i = 0; i < this.boughtGuns.size(); i++) {
/* 286 */       if (((String)this.boughtGuns.get(i)).toLowerCase().equals(g.toLowerCase())) {
/* 287 */         amt++;
/*     */       }
/*     */     }
/* 290 */     return amt;
/*     */   }
/*     */ 
/*     */   public boolean hasGun(String g) {
/* 294 */     for (int i = 0; i < this.boughtGuns.size(); i++) {
/* 295 */       if (((String)this.boughtGuns.get(i)).toLowerCase().equals(g.toLowerCase())) {
/* 296 */         return true;
/*     */       }
/*     */     }
/* 299 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean hasPerk(String g) {
/* 303 */     for (int i = 0; i < this.perks.size(); i++) {
/* 304 */       if (((String)this.perks.get(i)).equals(g)) {
/* 305 */         return true;
/*     */       }
/*     */     }
/* 308 */     return false;
/*     */   }
/*     */ 
/*     */   public KitGun getGun(String str) {
/* 312 */     if (str.equals("primary")) {
/* 313 */       return this.plugin.getGun(((KitClass)this.classes.get(this.myclass)).primary);
/*     */     }
/* 315 */     if (str.equals("secondary")) {
/* 316 */       return this.plugin.getGun(((KitClass)this.classes.get(this.myclass)).secondary);
/*     */     }
/* 318 */     return null;
/*     */   }
/*     */ 
/*     */   public void CLEAR() {
/* 322 */     for (int i = 0; i < this.classes.size(); i++) {
/* 323 */       ((KitClass)this.classes.get(i)).clear();
/*     */     }
/* 325 */     this.myContents.clear();
/* 326 */     this.compute.clear();
/* 327 */     this.player = null;
/*     */   }
/*     */ 
/*     */   public void dumpStats() {
/* 331 */     String mperk = "";
/* 332 */     String mgun = "";
/* 333 */     for (int i = 0; i < this.perks.size(); i++)
/* 334 */       mperk = mperk + (String)this.perks.get(i) + " ";
/* 335 */     for (int i = 0; i < this.boughtGuns.size(); i++)
/* 336 */       mgun = mgun + (String)this.boughtGuns.get(i) + " ";
/* 337 */     System.out.println("---------" + this.filename + "--------");
/* 338 */     System.out.println("|| level  " + this.level);
/* 339 */     System.out.println("|| xp     " + this.xp);
/* 340 */     System.out.println("|| kills  " + this.kills);
/* 341 */     System.out.println("|| deaths " + this.deaths);
/* 342 */     System.out.println("|| perks    " + mperk);
/* 343 */     System.out.println("|| guns    " + mgun);
/* 344 */     if (this.classes.size() > 0) {
/* 345 */       ArrayList clas = ((KitClass)this.classes.get(0)).dumpClass();
/* 346 */       for (int i = 0; i < clas.size(); i++)
/* 347 */         System.out.println((String)clas.get(i));
/*     */     }
/*     */     else {
/* 350 */       System.out.println("USER DOES NOT HAVE A CLASS!");
/*     */     }
/* 352 */     System.out.println("-------------------------");
/*     */   }
/*     */ 
/*     */   public void delete() {
/* 356 */     KitPvP plugin = (KitPvP)Bukkit.getPluginManager().getPlugin("MCWarfare");
/* 357 */     if (plugin != null) {
/* 358 */       String path = plugin.getUsers() + "/" + this.filename;
/* 359 */       File f = new File(path);
/* 360 */       if (f.exists())
/* 361 */         f.delete();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/shaharben-dor/Documents/Other/BUILD SERVER/plugins/MCWarfare.jar
 * Qualified Name:     com.orange451.mcwarfare.arena.KitProfile
 * JD-Core Version:    0.6.2
 */