package net.minecraft.client;

import com.google.common.collect.Lists;
import io.netty.util.concurrent.GenericFutureListener;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMemoryErrorScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSection;
import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.util.Util;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

public class Minecraft implements IPlayerUsage {

   private static final Logger field_147123_G = LogManager.getLogger();
   private static final ResourceLocation field_110444_H = new ResourceLocation("textures/gui/title/mojang.png");
   public static final boolean field_142025_a = Util.func_110647_a() == Util.EnumOS.MACOS;
   public static byte[] field_71444_a = new byte[10485760];
   private static final List field_110445_I = Lists.newArrayList(new DisplayMode[]{new DisplayMode(2560, 1600), new DisplayMode(2880, 1800)});
   private final File field_130070_K;
   private ServerData field_71422_O;
   private TextureManager field_71446_o;
   private static Minecraft field_71432_P;
   public PlayerControllerMP field_71442_b;
   private boolean field_71431_Q;
   private boolean field_71434_R;
   private CrashReport field_71433_S;
   public int field_71443_c;
   public int field_71440_d;
   private Timer field_71428_T = new Timer(20.0F);
   private PlayerUsageSnooper field_71427_U = new PlayerUsageSnooper("client", this, MinecraftServer.func_130071_aq());
   public WorldClient field_71441_e;
   public RenderGlobal field_71438_f;
   public EntityClientPlayerMP field_71439_g;
   public EntityLivingBase field_71451_h;
   public Entity field_147125_j;
   public EffectRenderer field_71452_i;
   private final Session field_71449_j;
   private boolean field_71445_n;
   public FontRenderer field_71466_p;
   public FontRenderer field_71464_q;
   public GuiScreen field_71462_r;
   public LoadingScreenRenderer field_71461_s;
   public EntityRenderer field_71460_t;
   private int field_71429_W;
   private int field_71436_X;
   private int field_71435_Y;
   private IntegratedServer field_71437_Z;
   public GuiAchievement field_71458_u;
   public GuiIngame field_71456_v;
   public boolean field_71454_w;
   public MovingObjectPosition field_71476_x;
   public GameSettings field_71474_y;
   public MouseHelper field_71417_B;
   public final File field_71412_D;
   private final File field_110446_Y;
   private final String field_110447_Z;
   private final Proxy field_110453_aa;
   private ISaveFormat field_71469_aa;
   private static int field_71470_ab;
   private int field_71467_ac;
   private boolean field_71468_ad;
   private String field_71475_ae;
   private int field_71477_af;
   boolean field_71414_F;
   public boolean field_71415_G;
   long field_71423_H = func_71386_F();
   private int field_71457_ai;
   private final boolean field_147129_ai;
   private final boolean field_71459_aj;
   private NetworkManager field_71453_ak;
   private boolean field_71455_al;
   public final Profiler field_71424_I = new Profiler();
   private long field_83002_am = -1L;
   private IReloadableResourceManager field_110451_am;
   private final IMetadataSerializer field_110452_an = new IMetadataSerializer();
   private List field_110449_ao = Lists.newArrayList();
   private DefaultResourcePack field_110450_ap;
   private ResourcePackRepository field_110448_aq;
   private LanguageManager field_135017_as;
   private Framebuffer field_147124_at;
   private TextureMap field_147128_au;
   private SoundHandler field_147127_av;
   private MusicTicker field_147126_aw;
   volatile boolean field_71425_J = true;
   public String field_71426_K = "";
   long field_71419_L = func_71386_F();
   int field_71420_M;
   long field_71421_N = -1L;
   private String field_71465_an = "root";
   private static final String __OBFID = "CL_00000631";


   public Minecraft(Session p_i1014_1_, int p_i1014_2_, int p_i1014_3_, boolean p_i1014_4_, boolean p_i1014_5_, File p_i1014_6_, File p_i1014_7_, File p_i1014_8_, Proxy p_i1014_9_, String p_i1014_10_) {
      field_71432_P = this;
      this.field_71412_D = p_i1014_6_;
      this.field_110446_Y = p_i1014_7_;
      this.field_130070_K = p_i1014_8_;
      this.field_110447_Z = p_i1014_10_;
      this.field_110450_ap = new DefaultResourcePack(this.field_110446_Y);
      this.func_110435_P();
      this.field_110453_aa = p_i1014_9_ == null?Proxy.NO_PROXY:p_i1014_9_;
      this.func_71389_H();
      this.field_71449_j = p_i1014_1_;
      field_147123_G.info("Setting user: " + p_i1014_1_.func_111285_a());
      field_147123_G.info("(Session ID is " + p_i1014_1_.func_111286_b() + ")");
      this.field_71459_aj = p_i1014_5_;
      this.field_71443_c = p_i1014_2_;
      this.field_71440_d = p_i1014_3_;
      this.field_71436_X = p_i1014_2_;
      this.field_71435_Y = p_i1014_3_;
      this.field_71431_Q = p_i1014_4_;
      this.field_147129_ai = func_147122_X();
      ImageIO.setUseCache(false);
      Bootstrap.func_151354_b();
   }

   private static boolean func_147122_X() {
      String[] var0 = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
      String[] var1 = var0;
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String var4 = var1[var3];
         String var5 = System.getProperty(var4);
         if(var5 != null && var5.contains("64")) {
            return true;
         }
      }

      return false;
   }

   public static void func_147105_a(String p_147105_0_) {
      try {
         Toolkit var1 = Toolkit.getDefaultToolkit();
         Class var2 = var1.getClass();
         if(var2.getName().equals("sun.awt.X11.XToolkit")) {
            Field var3 = var2.getDeclaredField("awtAppClassName");
            var3.setAccessible(true);
            var3.set(var1, p_147105_0_);
         }
      } catch (Exception var4) {
         ;
      }

   }

   public Framebuffer func_147110_a() {
      return this.field_147124_at;
   }

   private void func_71389_H() {
      Thread var1 = new Thread("Timer hack thread") {

         private static final String __OBFID = "CL_00000632";

         public void run() {
            while(Minecraft.this.field_71425_J) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException var2) {
                  ;
               }
            }

         }
      };
      var1.setDaemon(true);
      var1.start();
   }

   public void func_71404_a(CrashReport p_71404_1_) {
      this.field_71434_R = true;
      this.field_71433_S = p_71404_1_;
   }

   public void func_71377_b(CrashReport p_71377_1_) {
      File var2 = new File(func_71410_x().field_71412_D, "crash-reports");
      File var3 = new File(var2, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
      System.out.println(p_71377_1_.func_71502_e());
      if(p_71377_1_.func_71497_f() != null) {
         System.out.println("#@!@# Game crashed! Crash report saved to: #@!@# " + p_71377_1_.func_71497_f());
         System.exit(-1);
      } else if(p_71377_1_.func_147149_a(var3)) {
         System.out.println("#@!@# Game crashed! Crash report saved to: #@!@# " + var3.getAbsolutePath());
         System.exit(-1);
      } else {
         System.out.println("#@?@# Game crashed! Crash report could not be saved. #@?@#");
         System.exit(-2);
      }

   }

   public void func_71367_a(String p_71367_1_, int p_71367_2_) {
      this.field_71475_ae = p_71367_1_;
      this.field_71477_af = p_71367_2_;
   }

   private void func_71384_a() throws LWJGLException {
      this.field_71474_y = new GameSettings(this, this.field_71412_D);
      if(this.field_71474_y.field_92119_C > 0 && this.field_71474_y.field_92118_B > 0) {
         this.field_71443_c = this.field_71474_y.field_92118_B;
         this.field_71440_d = this.field_71474_y.field_92119_C;
      }

      if(this.field_71431_Q) {
         Display.setFullscreen(true);
         this.field_71443_c = Display.getDisplayMode().getWidth();
         this.field_71440_d = Display.getDisplayMode().getHeight();
         if(this.field_71443_c <= 0) {
            this.field_71443_c = 1;
         }

         if(this.field_71440_d <= 0) {
            this.field_71440_d = 1;
         }
      } else {
         Display.setDisplayMode(new DisplayMode(this.field_71443_c, this.field_71440_d));
      }

      Display.setResizable(true);
      Display.setTitle("Minecraft 1.7.2");
      field_147123_G.info("LWJGL Version: " + Sys.getVersion());
      Util.EnumOS var1 = Util.func_110647_a();
      if(var1 != Util.EnumOS.MACOS) {
         try {
            Display.setIcon(new ByteBuffer[]{this.func_110439_b(new File(this.field_110446_Y, "/icons/icon_16x16.png")), this.func_110439_b(new File(this.field_110446_Y, "/icons/icon_32x32.png"))});
         } catch (IOException var6) {
            field_147123_G.error("Couldn\'t set icon", var6);
         }

         if(var1 != Util.EnumOS.WINDOWS) {
            func_147105_a("Minecraft");
         }
      }

      try {
         Display.create((new PixelFormat()).withDepthBits(24));
      } catch (LWJGLException var5) {
         field_147123_G.error("Couldn\'t set pixel format", var5);

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var4) {
            ;
         }

         if(this.field_71431_Q) {
            this.func_110441_Q();
         }

         Display.create();
      }

      OpenGlHelper.func_77474_a();
      this.field_147124_at = new Framebuffer(this.field_71443_c, this.field_71440_d, true);
      this.field_147124_at.func_147604_a(0.0F, 0.0F, 0.0F, 0.0F);
      this.field_71458_u = new GuiAchievement(this);
      this.field_110452_an.func_110504_a(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
      this.field_110452_an.func_110504_a(new FontMetadataSectionSerializer(), FontMetadataSection.class);
      this.field_110452_an.func_110504_a(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
      this.field_110452_an.func_110504_a(new PackMetadataSectionSerializer(), PackMetadataSection.class);
      this.field_110452_an.func_110504_a(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
      this.field_71469_aa = new AnvilSaveConverter(new File(this.field_71412_D, "saves"));
      this.field_110448_aq = new ResourcePackRepository(this.field_130070_K, new File(this.field_71412_D, "server-resource-packs"), this.field_110450_ap, this.field_110452_an, this.field_71474_y);
      this.field_110451_am = new SimpleReloadableResourceManager(this.field_110452_an);
      this.field_135017_as = new LanguageManager(this.field_110452_an, this.field_71474_y.field_74363_ab);
      this.field_110451_am.func_110542_a(this.field_135017_as);
      this.func_110436_a();
      this.field_71446_o = new TextureManager(this.field_110451_am);
      this.field_110451_am.func_110542_a(this.field_71446_o);
      this.field_147127_av = new SoundHandler(this.field_110451_am, this.field_71474_y);
      this.field_147126_aw = new MusicTicker(this);
      this.field_110451_am.func_110542_a(this.field_147127_av);
      this.func_71357_I();
      this.field_71466_p = new FontRenderer(this.field_71474_y, new ResourceLocation("textures/font/ascii.png"), this.field_71446_o, false);
      if(this.field_71474_y.field_74363_ab != null) {
         this.field_71466_p.func_78264_a(this.field_135017_as.func_135042_a() || this.field_71474_y.field_151455_aw);
         this.field_71466_p.func_78275_b(this.field_135017_as.func_135044_b());
      }

      this.field_71464_q = new FontRenderer(this.field_71474_y, new ResourceLocation("textures/font/ascii_sga.png"), this.field_71446_o, false);
      this.field_110451_am.func_110542_a(this.field_71466_p);
      this.field_110451_am.func_110542_a(this.field_71464_q);
      this.field_110451_am.func_110542_a(new GrassColorReloadListener());
      this.field_110451_am.func_110542_a(new FoliageColorReloadListener());
      RenderManager.field_78727_a.field_78721_f = new ItemRenderer(this);
      this.field_71460_t = new EntityRenderer(this, this.field_110451_am);
      this.field_110451_am.func_110542_a(this.field_71460_t);
      AchievementList.field_76004_f.func_75988_a(new IStatStringFormat() {

         private static final String __OBFID = "CL_00000639";

         public String func_74535_a(String p_74535_1_) {
            try {
               return String.format(p_74535_1_, new Object[]{GameSettings.func_74298_c(Minecraft.this.field_71474_y.field_151445_Q.func_151463_i())});
            } catch (Exception var3) {
               return "Error: " + var3.getLocalizedMessage();
            }
         }
      });
      this.field_71417_B = new MouseHelper();
      this.func_71361_d("Pre startup");
      GL11.glEnable(3553);
      GL11.glShadeModel(7425);
      GL11.glClearDepth(1.0D);
      GL11.glEnable(2929);
      GL11.glDepthFunc(515);
      GL11.glEnable(3008);
      GL11.glAlphaFunc(516, 0.1F);
      GL11.glCullFace(1029);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glMatrixMode(5888);
      this.func_71361_d("Startup");
      this.field_71438_f = new RenderGlobal(this);
      this.field_147128_au = new TextureMap(0, "textures/blocks");
      this.field_147128_au.func_147632_b(this.field_71474_y.field_151443_J);
      this.field_147128_au.func_147633_a(this.field_71474_y.field_151442_I);
      this.field_71446_o.func_130088_a(TextureMap.field_110575_b, this.field_147128_au);
      this.field_71446_o.func_130088_a(TextureMap.field_110576_c, new TextureMap(1, "textures/items"));
      GL11.glViewport(0, 0, this.field_71443_c, this.field_71440_d);
      this.field_71452_i = new EffectRenderer(this.field_71441_e, this.field_71446_o);
      this.func_71361_d("Post startup");
      this.field_71456_v = new GuiIngame(this);
      if(this.field_71475_ae != null) {
         this.func_147108_a(new GuiConnecting(new GuiMainMenu(), this, this.field_71475_ae, this.field_71477_af));
      } else {
         this.func_147108_a(new GuiMainMenu());
      }

      this.field_71461_s = new LoadingScreenRenderer(this);
      if(this.field_71474_y.field_74353_u && !this.field_71431_Q) {
         this.func_71352_k();
      }

      Display.setVSyncEnabled(this.field_71474_y.field_74352_v);
   }

   public void func_110436_a() {
      ArrayList var1 = Lists.newArrayList(this.field_110449_ao);
      Iterator var2 = this.field_110448_aq.func_110613_c().iterator();

      while(var2.hasNext()) {
         ResourcePackRepository.Entry var3 = (ResourcePackRepository.Entry)var2.next();
         var1.add(var3.func_110514_c());
      }

      if(this.field_110448_aq.func_148530_e() != null) {
         var1.add(this.field_110448_aq.func_148530_e());
      }

      this.field_135017_as.func_135043_a(var1);
      this.field_110451_am.func_110541_a(var1);
      if(this.field_71438_f != null) {
         this.field_71438_f.func_72712_a();
      }

   }

   private void func_110435_P() {
      this.field_110449_ao.add(this.field_110450_ap);
   }

   private ByteBuffer func_110439_b(File p_110439_1_) throws IOException {
      BufferedImage var2 = ImageIO.read(p_110439_1_);
      int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), (int[])null, 0, var2.getWidth());
      ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
      int[] var5 = var3;
      int var6 = var3.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         int var8 = var5[var7];
         var4.putInt(var8 << 8 | var8 >> 24 & 255);
      }

      var4.flip();
      return var4;
   }

   private void func_110441_Q() throws LWJGLException {
      HashSet var1 = new HashSet();
      Collections.addAll(var1, Display.getAvailableDisplayModes());
      DisplayMode var2 = Display.getDesktopDisplayMode();
      if(!var1.contains(var2) && Util.func_110647_a() == Util.EnumOS.MACOS) {
         Iterator var3 = field_110445_I.iterator();

         while(var3.hasNext()) {
            DisplayMode var4 = (DisplayMode)var3.next();
            boolean var5 = true;
            Iterator var6 = var1.iterator();

            DisplayMode var7;
            while(var6.hasNext()) {
               var7 = (DisplayMode)var6.next();
               if(var7.getBitsPerPixel() == 32 && var7.getWidth() == var4.getWidth() && var7.getHeight() == var4.getHeight()) {
                  var5 = false;
                  break;
               }
            }

            if(!var5) {
               var6 = var1.iterator();

               while(var6.hasNext()) {
                  var7 = (DisplayMode)var6.next();
                  if(var7.getBitsPerPixel() == 32 && var7.getWidth() == var4.getWidth() / 2 && var7.getHeight() == var4.getHeight() / 2) {
                     var2 = var7;
                     break;
                  }
               }
            }
         }
      }

      Display.setDisplayMode(var2);
      this.field_71443_c = var2.getWidth();
      this.field_71440_d = var2.getHeight();
   }

   private void func_71357_I() throws LWJGLException {
      GL11.glEnable(3553);
      this.field_71446_o.func_110577_a(field_110444_H);
      ScaledResolution var1 = new ScaledResolution(this.field_71474_y, this.field_71443_c, this.field_71440_d);
      int var2 = var1.func_78325_e();
      Framebuffer var3 = new Framebuffer(var1.func_78326_a() * var2, var1.func_78328_b() * var2, true);
      var3.func_147610_a(false);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glOrtho(0.0D, (double)var1.func_78326_a(), (double)var1.func_78328_b(), 0.0D, 1000.0D, 3000.0D);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
      GL11.glDisable(2896);
      GL11.glDisable(2912);
      GL11.glDisable(2929);
      GL11.glEnable(3553);
      this.field_71446_o.func_110577_a(field_110444_H);
      Tessellator var4 = Tessellator.field_78398_a;
      var4.func_78382_b();
      var4.func_78378_d(16777215);
      var4.func_78374_a(0.0D, (double)this.field_71440_d, 0.0D, 0.0D, 0.0D);
      var4.func_78374_a((double)this.field_71443_c, (double)this.field_71440_d, 0.0D, 0.0D, 0.0D);
      var4.func_78374_a((double)this.field_71443_c, 0.0D, 0.0D, 0.0D, 0.0D);
      var4.func_78374_a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
      var4.func_78381_a();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      var4.func_78378_d(16777215);
      short var5 = 256;
      short var6 = 256;
      this.func_71392_a((var1.func_78326_a() - var5) / 2, (var1.func_78328_b() - var6) / 2, 0, 0, var5, var6);
      GL11.glDisable(2896);
      GL11.glDisable(2912);
      var3.func_147609_e();
      var3.func_147615_c(var1.func_78326_a() * var2, var1.func_78328_b() * var2);
      GL11.glEnable(3008);
      GL11.glAlphaFunc(516, 0.1F);
      GL11.glFlush();
      this.func_147120_f();
   }

   public void func_71392_a(int p_71392_1_, int p_71392_2_, int p_71392_3_, int p_71392_4_, int p_71392_5_, int p_71392_6_) {
      float var7 = 0.00390625F;
      float var8 = 0.00390625F;
      Tessellator var9 = Tessellator.field_78398_a;
      var9.func_78382_b();
      var9.func_78374_a((double)(p_71392_1_ + 0), (double)(p_71392_2_ + p_71392_6_), 0.0D, (double)((float)(p_71392_3_ + 0) * var7), (double)((float)(p_71392_4_ + p_71392_6_) * var8));
      var9.func_78374_a((double)(p_71392_1_ + p_71392_5_), (double)(p_71392_2_ + p_71392_6_), 0.0D, (double)((float)(p_71392_3_ + p_71392_5_) * var7), (double)((float)(p_71392_4_ + p_71392_6_) * var8));
      var9.func_78374_a((double)(p_71392_1_ + p_71392_5_), (double)(p_71392_2_ + 0), 0.0D, (double)((float)(p_71392_3_ + p_71392_5_) * var7), (double)((float)(p_71392_4_ + 0) * var8));
      var9.func_78374_a((double)(p_71392_1_ + 0), (double)(p_71392_2_ + 0), 0.0D, (double)((float)(p_71392_3_ + 0) * var7), (double)((float)(p_71392_4_ + 0) * var8));
      var9.func_78381_a();
   }

   public ISaveFormat func_71359_d() {
      return this.field_71469_aa;
   }

   public void func_147108_a(GuiScreen p_147108_1_) {
      if(this.field_71462_r != null) {
         this.field_71462_r.func_146281_b();
      }

      if(p_147108_1_ == null && this.field_71441_e == null) {
         p_147108_1_ = new GuiMainMenu();
      } else if(p_147108_1_ == null && this.field_71439_g.func_110143_aJ() <= 0.0F) {
         p_147108_1_ = new GuiGameOver();
      }

      if(p_147108_1_ instanceof GuiMainMenu) {
         this.field_71474_y.field_74330_P = false;
         this.field_71456_v.func_146158_b().func_146231_a();
      }

      this.field_71462_r = (GuiScreen)p_147108_1_;
      if(p_147108_1_ != null) {
         this.func_71364_i();
         ScaledResolution var2 = new ScaledResolution(this.field_71474_y, this.field_71443_c, this.field_71440_d);
         int var3 = var2.func_78326_a();
         int var4 = var2.func_78328_b();
         ((GuiScreen)p_147108_1_).func_146280_a(this, var3, var4);
         this.field_71454_w = false;
      } else {
         this.field_147127_av.func_147687_e();
         this.func_71381_h();
      }

   }

   private void func_71361_d(String p_71361_1_) {
      int var2 = GL11.glGetError();
      if(var2 != 0) {
         String var3 = GLU.gluErrorString(var2);
         field_147123_G.error("########## GL ERROR ##########");
         field_147123_G.error("@ " + p_71361_1_);
         field_147123_G.error(var2 + ": " + var3);
      }

   }

   public void func_71405_e() {
      try {
         field_147123_G.info("Stopping!");

         try {
            this.func_71403_a((WorldClient)null);
         } catch (Throwable var7) {
            ;
         }

         try {
            GLAllocation.func_74525_a();
         } catch (Throwable var6) {
            ;
         }

         this.field_147127_av.func_147685_d();
      } finally {
         Display.destroy();
         if(!this.field_71434_R) {
            System.exit(0);
         }

      }

      System.gc();
   }

   public void func_99999_d() {
      this.field_71425_J = true;

      CrashReport var2;
      try {
         this.func_71384_a();
      } catch (Throwable var11) {
         var2 = CrashReport.func_85055_a(var11, "Initializing game");
         var2.func_85058_a("Initialization");
         this.func_71377_b(this.func_71396_d(var2));
         return;
      }

      while(true) {
         try {
            if(this.field_71425_J) {
               if(!this.field_71434_R || this.field_71433_S == null) {
                  try {
                     this.func_71411_J();
                  } catch (OutOfMemoryError var10) {
                     this.func_71398_f();
                     this.func_147108_a(new GuiMemoryErrorScreen());
                     System.gc();
                  }
                  continue;
               }

               this.func_71377_b(this.field_71433_S);
               return;
            }
         } catch (MinecraftError var12) {
            ;
         } catch (ReportedException var13) {
            this.func_71396_d(var13.func_71575_a());
            this.func_71398_f();
            field_147123_G.fatal("Reported exception thrown!", var13);
            this.func_71377_b(var13.func_71575_a());
         } catch (Throwable var14) {
            var2 = this.func_71396_d(new CrashReport("Unexpected error", var14));
            this.func_71398_f();
            field_147123_G.fatal("Unreported exception thrown!", var14);
            this.func_71377_b(var2);
         } finally {
            this.func_71405_e();
         }

         return;
      }
   }

   private void func_71411_J() {
      AxisAlignedBB.func_72332_a().func_72298_a();
      if(this.field_71441_e != null) {
         this.field_71441_e.func_82732_R().func_72343_a();
      }

      this.field_71424_I.func_76320_a("root");
      if(Display.isCreated() && Display.isCloseRequested()) {
         this.func_71400_g();
      }

      if(this.field_71445_n && this.field_71441_e != null) {
         float var1 = this.field_71428_T.field_74281_c;
         this.field_71428_T.func_74275_a();
         this.field_71428_T.field_74281_c = var1;
      } else {
         this.field_71428_T.func_74275_a();
      }

      if((this.field_71441_e == null || this.field_71462_r == null) && this.field_71468_ad) {
         this.field_71468_ad = false;
         this.func_110436_a();
      }

      long var5 = System.nanoTime();
      this.field_71424_I.func_76320_a("tick");

      for(int var3 = 0; var3 < this.field_71428_T.field_74280_b; ++var3) {
         this.func_71407_l();
      }

      this.field_71424_I.func_76318_c("preRenderErrors");
      long var6 = System.nanoTime() - var5;
      this.func_71361_d("Pre render");
      RenderBlocks.field_147843_b = this.field_71474_y.field_74347_j;
      this.field_71424_I.func_76318_c("sound");
      this.field_147127_av.func_147691_a(this.field_71439_g, this.field_71428_T.field_74281_c);
      this.field_71424_I.func_76319_b();
      this.field_71424_I.func_76320_a("render");
      GL11.glPushMatrix();
      GL11.glClear(16640);
      this.field_147124_at.func_147610_a(true);
      this.field_71424_I.func_76320_a("display");
      GL11.glEnable(3553);
      if(this.field_71439_g != null && this.field_71439_g.func_70094_T()) {
         this.field_71474_y.field_74320_O = 0;
      }

      this.field_71424_I.func_76319_b();
      if(!this.field_71454_w) {
         this.field_71424_I.func_76318_c("gameRenderer");
         this.field_71460_t.func_78480_b(this.field_71428_T.field_74281_c);
         this.field_71424_I.func_76319_b();
      }

      GL11.glFlush();
      this.field_71424_I.func_76319_b();
      if(!Display.isActive() && this.field_71431_Q) {
         this.func_71352_k();
      }

      if(this.field_71474_y.field_74330_P && this.field_71474_y.field_74329_Q) {
         if(!this.field_71424_I.field_76327_a) {
            this.field_71424_I.func_76317_a();
         }

         this.field_71424_I.field_76327_a = true;
         this.func_71366_a(var6);
      } else {
         this.field_71424_I.field_76327_a = false;
         this.field_71421_N = System.nanoTime();
      }

      this.field_71458_u.func_146254_a();
      this.field_147124_at.func_147609_e();
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      this.field_147124_at.func_147615_c(this.field_71443_c, this.field_71440_d);
      GL11.glPopMatrix();
      this.field_71424_I.func_76320_a("root");
      this.func_147120_f();
      Thread.yield();
      this.func_71365_K();
      this.func_71361_d("Post render");
      ++this.field_71420_M;
      this.field_71445_n = this.func_71356_B() && this.field_71462_r != null && this.field_71462_r.func_73868_f() && !this.field_71437_Z.func_71344_c();

      while(func_71386_F() >= this.field_71419_L + 1000L) {
         field_71470_ab = this.field_71420_M;
         this.field_71426_K = field_71470_ab + " fps, " + WorldRenderer.field_78922_b + " chunk updates";
         WorldRenderer.field_78922_b = 0;
         this.field_71419_L += 1000L;
         this.field_71420_M = 0;
         this.field_71427_U.func_76471_b();
         if(!this.field_71427_U.func_76468_d()) {
            this.field_71427_U.func_76463_a();
         }
      }

      this.field_71424_I.func_76319_b();
      if(this.func_147107_h()) {
         Display.sync(this.func_90020_K());
      }

   }

   public void func_147120_f() {
      Display.update();
      if(!this.field_71431_Q && Display.wasResized()) {
         int var1 = this.field_71443_c;
         int var2 = this.field_71440_d;
         this.field_71443_c = Display.getWidth();
         this.field_71440_d = Display.getHeight();
         if(this.field_71443_c != var1 || this.field_71440_d != var2) {
            if(this.field_71443_c <= 0) {
               this.field_71443_c = 1;
            }

            if(this.field_71440_d <= 0) {
               this.field_71440_d = 1;
            }

            this.func_71370_a(this.field_71443_c, this.field_71440_d);
         }
      }

   }

   public int func_90020_K() {
      return this.field_71441_e == null && this.field_71462_r != null?30:this.field_71474_y.field_74350_i;
   }

   public boolean func_147107_h() {
      return (float)this.func_90020_K() < GameSettings.Options.FRAMERATE_LIMIT.func_148267_f();
   }

   public void func_71398_f() {
      try {
         field_71444_a = new byte[0];
         this.field_71438_f.func_72728_f();
      } catch (Throwable var4) {
         ;
      }

      try {
         System.gc();
         AxisAlignedBB.func_72332_a().func_72300_b();
         this.field_71441_e.func_82732_R().func_72344_b();
      } catch (Throwable var3) {
         ;
      }

      try {
         System.gc();
         this.func_71403_a((WorldClient)null);
      } catch (Throwable var2) {
         ;
      }

      System.gc();
   }

   private void func_71365_K() {
      if(this.field_71474_y.field_151447_Z.func_151468_f()) {
         if(!this.field_71414_F) {
            this.field_71414_F = true;
            this.field_71456_v.func_146158_b().func_146227_a(ScreenShotHelper.func_148260_a(this.field_71412_D, this.field_71443_c, this.field_71440_d, this.field_147124_at));
         }
      } else {
         this.field_71414_F = false;
      }

   }

   private void func_71383_b(int p_71383_1_) {
      List var2 = this.field_71424_I.func_76321_b(this.field_71465_an);
      if(var2 != null && !var2.isEmpty()) {
         Profiler.Result var3 = (Profiler.Result)var2.remove(0);
         if(p_71383_1_ == 0) {
            if(var3.field_76331_c.length() > 0) {
               int var4 = this.field_71465_an.lastIndexOf(".");
               if(var4 >= 0) {
                  this.field_71465_an = this.field_71465_an.substring(0, var4);
               }
            }
         } else {
            --p_71383_1_;
            if(p_71383_1_ < var2.size() && !((Profiler.Result)var2.get(p_71383_1_)).field_76331_c.equals("unspecified")) {
               if(this.field_71465_an.length() > 0) {
                  this.field_71465_an = this.field_71465_an + ".";
               }

               this.field_71465_an = this.field_71465_an + ((Profiler.Result)var2.get(p_71383_1_)).field_76331_c;
            }
         }

      }
   }

   private void func_71366_a(long p_71366_1_) {
      if(this.field_71424_I.field_76327_a) {
         List var3 = this.field_71424_I.func_76321_b(this.field_71465_an);
         Profiler.Result var4 = (Profiler.Result)var3.remove(0);
         GL11.glClear(256);
         GL11.glMatrixMode(5889);
         GL11.glEnable(2903);
         GL11.glLoadIdentity();
         GL11.glOrtho(0.0D, (double)this.field_71443_c, (double)this.field_71440_d, 0.0D, 1000.0D, 3000.0D);
         GL11.glMatrixMode(5888);
         GL11.glLoadIdentity();
         GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
         GL11.glLineWidth(1.0F);
         GL11.glDisable(3553);
         Tessellator var5 = Tessellator.field_78398_a;
         short var6 = 160;
         int var7 = this.field_71443_c - var6 - 10;
         int var8 = this.field_71440_d - var6 * 2;
         GL11.glEnable(3042);
         var5.func_78382_b();
         var5.func_78384_a(0, 200);
         var5.func_78377_a((double)((float)var7 - (float)var6 * 1.1F), (double)((float)var8 - (float)var6 * 0.6F - 16.0F), 0.0D);
         var5.func_78377_a((double)((float)var7 - (float)var6 * 1.1F), (double)(var8 + var6 * 2), 0.0D);
         var5.func_78377_a((double)((float)var7 + (float)var6 * 1.1F), (double)(var8 + var6 * 2), 0.0D);
         var5.func_78377_a((double)((float)var7 + (float)var6 * 1.1F), (double)((float)var8 - (float)var6 * 0.6F - 16.0F), 0.0D);
         var5.func_78381_a();
         GL11.glDisable(3042);
         double var9 = 0.0D;

         int var13;
         for(int var11 = 0; var11 < var3.size(); ++var11) {
            Profiler.Result var12 = (Profiler.Result)var3.get(var11);
            var13 = MathHelper.func_76128_c(var12.field_76332_a / 4.0D) + 1;
            var5.func_78371_b(6);
            var5.func_78378_d(var12.func_76329_a());
            var5.func_78377_a((double)var7, (double)var8, 0.0D);

            int var14;
            float var15;
            float var16;
            float var17;
            for(var14 = var13; var14 >= 0; --var14) {
               var15 = (float)((var9 + var12.field_76332_a * (double)var14 / (double)var13) * 3.1415927410125732D * 2.0D / 100.0D);
               var16 = MathHelper.func_76126_a(var15) * (float)var6;
               var17 = MathHelper.func_76134_b(var15) * (float)var6 * 0.5F;
               var5.func_78377_a((double)((float)var7 + var16), (double)((float)var8 - var17), 0.0D);
            }

            var5.func_78381_a();
            var5.func_78371_b(5);
            var5.func_78378_d((var12.func_76329_a() & 16711422) >> 1);

            for(var14 = var13; var14 >= 0; --var14) {
               var15 = (float)((var9 + var12.field_76332_a * (double)var14 / (double)var13) * 3.1415927410125732D * 2.0D / 100.0D);
               var16 = MathHelper.func_76126_a(var15) * (float)var6;
               var17 = MathHelper.func_76134_b(var15) * (float)var6 * 0.5F;
               var5.func_78377_a((double)((float)var7 + var16), (double)((float)var8 - var17), 0.0D);
               var5.func_78377_a((double)((float)var7 + var16), (double)((float)var8 - var17 + 10.0F), 0.0D);
            }

            var5.func_78381_a();
            var9 += var12.field_76332_a;
         }

         DecimalFormat var18 = new DecimalFormat("##0.00");
         GL11.glEnable(3553);
         String var19 = "";
         if(!var4.field_76331_c.equals("unspecified")) {
            var19 = var19 + "[0] ";
         }

         if(var4.field_76331_c.length() == 0) {
            var19 = var19 + "ROOT ";
         } else {
            var19 = var19 + var4.field_76331_c + " ";
         }

         var13 = 16777215;
         this.field_71466_p.func_78261_a(var19, var7 - var6, var8 - var6 / 2 - 16, var13);
         this.field_71466_p.func_78261_a(var19 = var18.format(var4.field_76330_b) + "%", var7 + var6 - this.field_71466_p.func_78256_a(var19), var8 - var6 / 2 - 16, var13);

         for(int var21 = 0; var21 < var3.size(); ++var21) {
            Profiler.Result var20 = (Profiler.Result)var3.get(var21);
            String var22 = "";
            if(var20.field_76331_c.equals("unspecified")) {
               var22 = var22 + "[?] ";
            } else {
               var22 = var22 + "[" + (var21 + 1) + "] ";
            }

            var22 = var22 + var20.field_76331_c;
            this.field_71466_p.func_78261_a(var22, var7 - var6, var8 + var6 / 2 + var21 * 8 + 20, var20.func_76329_a());
            this.field_71466_p.func_78261_a(var22 = var18.format(var20.field_76332_a) + "%", var7 + var6 - 50 - this.field_71466_p.func_78256_a(var22), var8 + var6 / 2 + var21 * 8 + 20, var20.func_76329_a());
            this.field_71466_p.func_78261_a(var22 = var18.format(var20.field_76330_b) + "%", var7 + var6 - this.field_71466_p.func_78256_a(var22), var8 + var6 / 2 + var21 * 8 + 20, var20.func_76329_a());
         }

      }
   }

   public void func_71400_g() {
      this.field_71425_J = false;
   }

   public void func_71381_h() {
      if(Display.isActive()) {
         if(!this.field_71415_G) {
            this.field_71415_G = true;
            this.field_71417_B.func_74372_a();
            this.func_147108_a((GuiScreen)null);
            this.field_71429_W = 10000;
         }
      }
   }

   public void func_71364_i() {
      if(this.field_71415_G) {
         KeyBinding.func_74506_a();
         this.field_71415_G = false;
         this.field_71417_B.func_74373_b();
      }
   }

   public void func_71385_j() {
      if(this.field_71462_r == null) {
         this.func_147108_a(new GuiIngameMenu());
         if(this.func_71356_B() && !this.field_71437_Z.func_71344_c()) {
            this.field_147127_av.func_147689_b();
         }

      }
   }

   private void func_147115_a(boolean p_147115_1_) {
      if(!p_147115_1_) {
         this.field_71429_W = 0;
      }

      if(this.field_71429_W <= 0) {
         if(p_147115_1_ && this.field_71476_x != null && this.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
            int var2 = this.field_71476_x.field_72311_b;
            int var3 = this.field_71476_x.field_72312_c;
            int var4 = this.field_71476_x.field_72309_d;
            if(this.field_71441_e.func_147439_a(var2, var3, var4).func_149688_o() != Material.field_151579_a) {
               this.field_71442_b.func_78759_c(var2, var3, var4, this.field_71476_x.field_72310_e);
               if(this.field_71439_g.func_82246_f(var2, var3, var4)) {
                  this.field_71452_i.func_78867_a(var2, var3, var4, this.field_71476_x.field_72310_e);
                  this.field_71439_g.func_71038_i();
               }
            }

         } else {
            this.field_71442_b.func_78767_c();
         }
      }
   }

   private void func_147116_af() {
      if(this.field_71429_W <= 0) {
         this.field_71439_g.func_71038_i();
         if(this.field_71476_x == null) {
            field_147123_G.error("Null returned as \'hitResult\', this shouldn\'t happen!");
            if(this.field_71442_b.func_78762_g()) {
               this.field_71429_W = 10;
            }

         } else {
            switch(Minecraft.SwitchMovingObjectType.field_151437_a[this.field_71476_x.field_72313_a.ordinal()]) {
            case 1:
               this.field_71442_b.func_78764_a(this.field_71439_g, this.field_71476_x.field_72308_g);
               break;
            case 2:
               int var1 = this.field_71476_x.field_72311_b;
               int var2 = this.field_71476_x.field_72312_c;
               int var3 = this.field_71476_x.field_72309_d;
               if(this.field_71441_e.func_147439_a(var1, var2, var3).func_149688_o() == Material.field_151579_a) {
                  if(this.field_71442_b.func_78762_g()) {
                     this.field_71429_W = 10;
                  }
               } else {
                  this.field_71442_b.func_78743_b(var1, var2, var3, this.field_71476_x.field_72310_e);
               }
            }

         }
      }
   }

   private void func_147121_ag() {
      this.field_71467_ac = 4;
      boolean var1 = true;
      ItemStack var2 = this.field_71439_g.field_71071_by.func_70448_g();
      if(this.field_71476_x == null) {
         field_147123_G.warn("Null returned as \'hitResult\', this shouldn\'t happen!");
      } else {
         switch(Minecraft.SwitchMovingObjectType.field_151437_a[this.field_71476_x.field_72313_a.ordinal()]) {
         case 1:
            if(this.field_71442_b.func_78768_b(this.field_71439_g, this.field_71476_x.field_72308_g)) {
               var1 = false;
            }
            break;
         case 2:
            int var3 = this.field_71476_x.field_72311_b;
            int var4 = this.field_71476_x.field_72312_c;
            int var5 = this.field_71476_x.field_72309_d;
            if(this.field_71441_e.func_147439_a(var3, var4, var5).func_149688_o() != Material.field_151579_a) {
               int var6 = var2 != null?var2.field_77994_a:0;
               if(this.field_71442_b.func_78760_a(this.field_71439_g, this.field_71441_e, var2, var3, var4, var5, this.field_71476_x.field_72310_e, this.field_71476_x.field_72307_f)) {
                  var1 = false;
                  this.field_71439_g.func_71038_i();
               }

               if(var2 == null) {
                  return;
               }

               if(var2.field_77994_a == 0) {
                  this.field_71439_g.field_71071_by.field_70462_a[this.field_71439_g.field_71071_by.field_70461_c] = null;
               } else if(var2.field_77994_a != var6 || this.field_71442_b.func_78758_h()) {
                  this.field_71460_t.field_78516_c.func_78444_b();
               }
            }
         }
      }

      if(var1) {
         ItemStack var7 = this.field_71439_g.field_71071_by.func_70448_g();
         if(var7 != null && this.field_71442_b.func_78769_a(this.field_71439_g, this.field_71441_e, var7)) {
            this.field_71460_t.field_78516_c.func_78445_c();
         }
      }

   }

   public void func_71352_k() {
      try {
         this.field_71431_Q = !this.field_71431_Q;
         if(this.field_71431_Q) {
            this.func_110441_Q();
            this.field_71443_c = Display.getDisplayMode().getWidth();
            this.field_71440_d = Display.getDisplayMode().getHeight();
            if(this.field_71443_c <= 0) {
               this.field_71443_c = 1;
            }

            if(this.field_71440_d <= 0) {
               this.field_71440_d = 1;
            }
         } else {
            Display.setDisplayMode(new DisplayMode(this.field_71436_X, this.field_71435_Y));
            this.field_71443_c = this.field_71436_X;
            this.field_71440_d = this.field_71435_Y;
            if(this.field_71443_c <= 0) {
               this.field_71443_c = 1;
            }

            if(this.field_71440_d <= 0) {
               this.field_71440_d = 1;
            }
         }

         if(this.field_71462_r != null) {
            this.func_71370_a(this.field_71443_c, this.field_71440_d);
         } else {
            this.func_147119_ah();
         }

         Display.setFullscreen(this.field_71431_Q);
         Display.setVSyncEnabled(this.field_71474_y.field_74352_v);
         this.func_147120_f();
      } catch (Exception var2) {
         field_147123_G.error("Couldn\'t toggle fullscreen", var2);
      }

   }

   private void func_71370_a(int p_71370_1_, int p_71370_2_) {
      this.field_71443_c = p_71370_1_ <= 0?1:p_71370_1_;
      this.field_71440_d = p_71370_2_ <= 0?1:p_71370_2_;
      if(this.field_71462_r != null) {
         ScaledResolution var3 = new ScaledResolution(this.field_71474_y, p_71370_1_, p_71370_2_);
         int var4 = var3.func_78326_a();
         int var5 = var3.func_78328_b();
         this.field_71462_r.func_146280_a(this, var4, var5);
      }

      this.field_71461_s = new LoadingScreenRenderer(this);
      this.func_147119_ah();
   }

   private void func_147119_ah() {
      this.field_147124_at.func_147613_a(this.field_71443_c, this.field_71440_d);
      if(this.field_71460_t != null) {
         this.field_71460_t.func_147704_a(this.field_71443_c, this.field_71440_d);
      }

   }

   public void func_71407_l() {
      if(this.field_71467_ac > 0) {
         --this.field_71467_ac;
      }

      this.field_71424_I.func_76320_a("gui");
      if(!this.field_71445_n) {
         this.field_71456_v.func_73831_a();
      }

      this.field_71424_I.func_76318_c("pick");
      this.field_71460_t.func_78473_a(1.0F);
      this.field_71424_I.func_76318_c("gameMode");
      if(!this.field_71445_n && this.field_71441_e != null) {
         this.field_71442_b.func_78765_e();
      }

      this.field_71424_I.func_76318_c("textures");
      if(!this.field_71445_n) {
         this.field_71446_o.func_110550_d();
      }

      if(this.field_71462_r == null && this.field_71439_g != null) {
         if(this.field_71439_g.func_110143_aJ() <= 0.0F) {
            this.func_147108_a((GuiScreen)null);
         } else if(this.field_71439_g.func_70608_bn() && this.field_71441_e != null) {
            this.func_147108_a(new GuiSleepMP());
         }
      } else if(this.field_71462_r != null && this.field_71462_r instanceof GuiSleepMP && !this.field_71439_g.func_70608_bn()) {
         this.func_147108_a((GuiScreen)null);
      }

      if(this.field_71462_r != null) {
         this.field_71429_W = 10000;
      }

      CrashReport var2;
      CrashReportCategory var3;
      if(this.field_71462_r != null) {
         try {
            this.field_71462_r.func_146269_k();
         } catch (Throwable var6) {
            var2 = CrashReport.func_85055_a(var6, "Updating screen events");
            var3 = var2.func_85058_a("Affected screen");
            var3.func_71500_a("Screen name", new Callable() {

               private static final String __OBFID = "CL_00000640";

               public String call() {
                  return Minecraft.this.field_71462_r.getClass().getCanonicalName();
               }
               // $FF: synthetic method
               public Object call() {
                  return this.call();
               }
            });
            throw new ReportedException(var2);
         }

         if(this.field_71462_r != null) {
            try {
               this.field_71462_r.func_73876_c();
            } catch (Throwable var5) {
               var2 = CrashReport.func_85055_a(var5, "Ticking screen");
               var3 = var2.func_85058_a("Affected screen");
               var3.func_71500_a("Screen name", new Callable() {

                  private static final String __OBFID = "CL_00000642";

                  public String call() {
                     return Minecraft.this.field_71462_r.getClass().getCanonicalName();
                  }
                  // $FF: synthetic method
                  public Object call() {
                     return this.call();
                  }
               });
               throw new ReportedException(var2);
            }
         }
      }

      if(this.field_71462_r == null || this.field_71462_r.field_146291_p) {
         this.field_71424_I.func_76318_c("mouse");

         int var1;
         while(Mouse.next()) {
            var1 = Mouse.getEventButton();
            if(field_142025_a && var1 == 0 && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157))) {
               var1 = 1;
            }

            KeyBinding.func_74510_a(var1 - 100, Mouse.getEventButtonState());
            if(Mouse.getEventButtonState()) {
               KeyBinding.func_74507_a(var1 - 100);
            }

            long var9 = func_71386_F() - this.field_71423_H;
            if(var9 <= 200L) {
               int var4 = Mouse.getEventDWheel();
               if(var4 != 0) {
                  this.field_71439_g.field_71071_by.func_70453_c(var4);
                  if(this.field_71474_y.field_74331_S) {
                     if(var4 > 0) {
                        var4 = 1;
                     }

                     if(var4 < 0) {
                        var4 = -1;
                     }

                     this.field_71474_y.field_74328_V += (float)var4 * 0.25F;
                  }
               }

               if(this.field_71462_r == null) {
                  if(!this.field_71415_G && Mouse.getEventButtonState()) {
                     this.func_71381_h();
                  }
               } else if(this.field_71462_r != null) {
                  this.field_71462_r.func_146274_d();
               }
            }
         }

         if(this.field_71429_W > 0) {
            --this.field_71429_W;
         }

         this.field_71424_I.func_76318_c("keyboard");

         boolean var8;
         while(Keyboard.next()) {
            KeyBinding.func_74510_a(Keyboard.getEventKey(), Keyboard.getEventKeyState());
            if(Keyboard.getEventKeyState()) {
               KeyBinding.func_74507_a(Keyboard.getEventKey());
            }

            if(this.field_83002_am > 0L) {
               if(func_71386_F() - this.field_83002_am >= 6000L) {
                  throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
               }

               if(!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61)) {
                  this.field_83002_am = -1L;
               }
            } else if(Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
               this.field_83002_am = func_71386_F();
            }

            if(Keyboard.getEventKeyState()) {
               if(Keyboard.getEventKey() == 62 && this.field_71460_t != null) {
                  this.field_71460_t.func_147703_b();
               }

               if(Keyboard.getEventKey() == 87) {
                  this.func_71352_k();
               } else {
                  if(this.field_71462_r != null) {
                     this.field_71462_r.func_146282_l();
                  } else {
                     if(Keyboard.getEventKey() == 1) {
                        this.func_71385_j();
                     }

                     if(Keyboard.getEventKey() == 31 && Keyboard.isKeyDown(61)) {
                        this.func_110436_a();
                     }

                     if(Keyboard.getEventKey() == 20 && Keyboard.isKeyDown(61)) {
                        this.func_110436_a();
                     }

                     if(Keyboard.getEventKey() == 33 && Keyboard.isKeyDown(61)) {
                        var8 = Keyboard.isKeyDown(42) | Keyboard.isKeyDown(54);
                        this.field_71474_y.func_74306_a(GameSettings.Options.RENDER_DISTANCE, var8?-1:1);
                     }

                     if(Keyboard.getEventKey() == 30 && Keyboard.isKeyDown(61)) {
                        this.field_71438_f.func_72712_a();
                     }

                     if(Keyboard.getEventKey() == 35 && Keyboard.isKeyDown(61)) {
                        this.field_71474_y.field_82882_x = !this.field_71474_y.field_82882_x;
                        this.field_71474_y.func_74303_b();
                     }

                     if(Keyboard.getEventKey() == 48 && Keyboard.isKeyDown(61)) {
                        RenderManager.field_85095_o = !RenderManager.field_85095_o;
                     }

                     if(Keyboard.getEventKey() == 25 && Keyboard.isKeyDown(61)) {
                        this.field_71474_y.field_82881_y = !this.field_71474_y.field_82881_y;
                        this.field_71474_y.func_74303_b();
                     }

                     if(Keyboard.getEventKey() == 59) {
                        this.field_71474_y.field_74319_N = !this.field_71474_y.field_74319_N;
                     }

                     if(Keyboard.getEventKey() == 61) {
                        this.field_71474_y.field_74330_P = !this.field_71474_y.field_74330_P;
                        this.field_71474_y.field_74329_Q = GuiScreen.func_146272_n();
                     }

                     if(this.field_71474_y.field_151457_aa.func_151468_f()) {
                        ++this.field_71474_y.field_74320_O;
                        if(this.field_71474_y.field_74320_O > 2) {
                           this.field_71474_y.field_74320_O = 0;
                        }
                     }

                     if(this.field_71474_y.field_151458_ab.func_151468_f()) {
                        this.field_71474_y.field_74326_T = !this.field_71474_y.field_74326_T;
                     }
                  }

                  if(this.field_71474_y.field_74330_P && this.field_71474_y.field_74329_Q) {
                     if(Keyboard.getEventKey() == 11) {
                        this.func_71383_b(0);
                     }

                     for(var1 = 0; var1 < 9; ++var1) {
                        if(Keyboard.getEventKey() == 2 + var1) {
                           this.func_71383_b(var1 + 1);
                        }
                     }
                  }
               }
            }
         }

         for(var1 = 0; var1 < 9; ++var1) {
            if(this.field_71474_y.field_151456_ac[var1].func_151468_f()) {
               this.field_71439_g.field_71071_by.field_70461_c = var1;
            }
         }

         var8 = this.field_71474_y.field_74343_n != EntityPlayer.EnumChatVisibility.HIDDEN;

         while(this.field_71474_y.field_151445_Q.func_151468_f()) {
            if(this.field_71442_b.func_110738_j()) {
               this.field_71439_g.func_110322_i();
            } else {
               this.func_147114_u().func_147297_a(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
               this.func_147108_a(new GuiInventory(this.field_71439_g));
            }
         }

         while(this.field_71474_y.field_74316_C.func_151468_f()) {
            this.field_71439_g.func_71040_bB(GuiScreen.func_146271_m());
         }

         while(this.field_71474_y.field_74310_D.func_151468_f() && var8) {
            this.func_147108_a(new GuiChat());
         }

         if(this.field_71462_r == null && this.field_71474_y.field_74323_J.func_151468_f() && var8) {
            this.func_147108_a(new GuiChat("/"));
         }

         if(this.field_71439_g.func_71039_bw()) {
            if(!this.field_71474_y.field_74313_G.func_151470_d()) {
               this.field_71442_b.func_78766_c(this.field_71439_g);
            }

            label391:
            while(true) {
               if(!this.field_71474_y.field_74312_F.func_151468_f()) {
                  while(this.field_71474_y.field_74313_G.func_151468_f()) {
                     ;
                  }

                  while(true) {
                     if(this.field_71474_y.field_74322_I.func_151468_f()) {
                        continue;
                     }
                     break label391;
                  }
               }
            }
         } else {
            while(this.field_71474_y.field_74312_F.func_151468_f()) {
               this.func_147116_af();
            }

            while(this.field_71474_y.field_74313_G.func_151468_f()) {
               this.func_147121_ag();
            }

            while(this.field_71474_y.field_74322_I.func_151468_f()) {
               this.func_147112_ai();
            }
         }

         if(this.field_71474_y.field_74313_G.func_151470_d() && this.field_71467_ac == 0 && !this.field_71439_g.func_71039_bw()) {
            this.func_147121_ag();
         }

         this.func_147115_a(this.field_71462_r == null && this.field_71474_y.field_74312_F.func_151470_d() && this.field_71415_G);
      }

      if(this.field_71441_e != null) {
         if(this.field_71439_g != null) {
            ++this.field_71457_ai;
            if(this.field_71457_ai == 30) {
               this.field_71457_ai = 0;
               this.field_71441_e.func_72897_h(this.field_71439_g);
            }
         }

         this.field_71424_I.func_76318_c("gameRenderer");
         if(!this.field_71445_n) {
            this.field_71460_t.func_78464_a();
         }

         this.field_71424_I.func_76318_c("levelRenderer");
         if(!this.field_71445_n) {
            this.field_71438_f.func_72734_e();
         }

         this.field_71424_I.func_76318_c("level");
         if(!this.field_71445_n) {
            if(this.field_71441_e.field_73016_r > 0) {
               --this.field_71441_e.field_73016_r;
            }

            this.field_71441_e.func_72939_s();
         }
      }

      if(!this.field_71445_n) {
         this.field_147126_aw.func_73660_a();
         this.field_147127_av.func_73660_a();
      }

      if(this.field_71441_e != null) {
         if(!this.field_71445_n) {
            this.field_71441_e.func_72891_a(this.field_71441_e.field_73013_u != EnumDifficulty.PEACEFUL, true);

            try {
               this.field_71441_e.func_72835_b();
            } catch (Throwable var7) {
               var2 = CrashReport.func_85055_a(var7, "Exception in world tick");
               if(this.field_71441_e == null) {
                  var3 = var2.func_85058_a("Affected level");
                  var3.func_71507_a("Problem", "Level is null!");
               } else {
                  this.field_71441_e.func_72914_a(var2);
               }

               throw new ReportedException(var2);
            }
         }

         this.field_71424_I.func_76318_c("animateTick");
         if(!this.field_71445_n && this.field_71441_e != null) {
            this.field_71441_e.func_73029_E(MathHelper.func_76128_c(this.field_71439_g.field_70165_t), MathHelper.func_76128_c(this.field_71439_g.field_70163_u), MathHelper.func_76128_c(this.field_71439_g.field_70161_v));
         }

         this.field_71424_I.func_76318_c("particles");
         if(!this.field_71445_n) {
            this.field_71452_i.func_78868_a();
         }
      } else if(this.field_71453_ak != null) {
         this.field_71424_I.func_76318_c("pendingConnection");
         this.field_71453_ak.func_74428_b();
      }

      this.field_71424_I.func_76319_b();
      this.field_71423_H = func_71386_F();
   }

   public void func_71371_a(String p_71371_1_, String p_71371_2_, WorldSettings p_71371_3_) {
      this.func_71403_a((WorldClient)null);
      System.gc();
      ISaveHandler var4 = this.field_71469_aa.func_75804_a(p_71371_1_, false);
      WorldInfo var5 = var4.func_75757_d();
      if(var5 == null && p_71371_3_ != null) {
         var5 = new WorldInfo(p_71371_3_, p_71371_1_);
         var4.func_75761_a(var5);
      }

      if(p_71371_3_ == null) {
         p_71371_3_ = new WorldSettings(var5);
      }

      try {
         this.field_71437_Z = new IntegratedServer(this, p_71371_1_, p_71371_2_, p_71371_3_);
         this.field_71437_Z.func_71256_s();
         this.field_71455_al = true;
      } catch (Throwable var10) {
         CrashReport var7 = CrashReport.func_85055_a(var10, "Starting integrated server");
         CrashReportCategory var8 = var7.func_85058_a("Starting integrated server");
         var8.func_71507_a("Level ID", p_71371_1_);
         var8.func_71507_a("Level Name", p_71371_2_);
         throw new ReportedException(var7);
      }

      this.field_71461_s.func_73720_a(I18n.func_135052_a("menu.loadingLevel", new Object[0]));

      while(!this.field_71437_Z.func_71200_ad()) {
         String var6 = this.field_71437_Z.func_71195_b_();
         if(var6 != null) {
            this.field_71461_s.func_73719_c(I18n.func_135052_a(var6, new Object[0]));
         } else {
            this.field_71461_s.func_73719_c("");
         }

         try {
            Thread.sleep(200L);
         } catch (InterruptedException var9) {
            ;
         }
      }

      this.func_147108_a((GuiScreen)null);
      SocketAddress var11 = this.field_71437_Z.func_147137_ag().func_151270_a();
      NetworkManager var12 = NetworkManager.func_150722_a(var11);
      var12.func_150719_a(new NetHandlerLoginClient(var12, this, (GuiScreen)null));
      var12.func_150725_a(new C00Handshake(4, var11.toString(), 0, EnumConnectionState.LOGIN), new GenericFutureListener[0]);
      var12.func_150725_a(new C00PacketLoginStart(this.func_110432_I().func_148256_e()), new GenericFutureListener[0]);
      this.field_71453_ak = var12;
   }

   public void func_71403_a(WorldClient p_71403_1_) {
      this.func_71353_a(p_71403_1_, "");
   }

   public void func_71353_a(WorldClient p_71353_1_, String p_71353_2_) {
      if(p_71353_1_ == null) {
         NetHandlerPlayClient var3 = this.func_147114_u();
         if(var3 != null) {
            var3.func_147296_c();
         }

         if(this.field_71437_Z != null) {
            this.field_71437_Z.func_71263_m();
         }

         this.field_71437_Z = null;
         this.field_71458_u.func_146257_b();
         this.field_71460_t.func_147701_i().func_148249_a();
      }

      this.field_71451_h = null;
      this.field_71453_ak = null;
      if(this.field_71461_s != null) {
         this.field_71461_s.func_73721_b(p_71353_2_);
         this.field_71461_s.func_73719_c("");
      }

      if(p_71353_1_ == null && this.field_71441_e != null) {
         if(this.field_110448_aq.func_148530_e() != null) {
            this.func_147106_B();
         }

         this.field_110448_aq.func_148529_f();
         this.func_71351_a((ServerData)null);
         this.field_71455_al = false;
      }

      this.field_147127_av.func_147690_c();
      this.field_71441_e = p_71353_1_;
      if(p_71353_1_ != null) {
         if(this.field_71438_f != null) {
            this.field_71438_f.func_72732_a(p_71353_1_);
         }

         if(this.field_71452_i != null) {
            this.field_71452_i.func_78870_a(p_71353_1_);
         }

         if(this.field_71439_g == null) {
            this.field_71439_g = this.field_71442_b.func_147493_a(p_71353_1_, new StatFileWriter());
            this.field_71442_b.func_78745_b(this.field_71439_g);
         }

         this.field_71439_g.func_70065_x();
         p_71353_1_.func_72838_d(this.field_71439_g);
         this.field_71439_g.field_71158_b = new MovementInputFromOptions(this.field_71474_y);
         this.field_71442_b.func_78748_a(this.field_71439_g);
         this.field_71451_h = this.field_71439_g;
      } else {
         this.field_71469_aa.func_75800_d();
         this.field_71439_g = null;
      }

      System.gc();
      this.field_71423_H = 0L;
   }

   public String func_71393_m() {
      return this.field_71438_f.func_72735_c();
   }

   public String func_71408_n() {
      return this.field_71438_f.func_72723_d();
   }

   public String func_71388_o() {
      return this.field_71441_e.func_72827_u();
   }

   public String func_71374_p() {
      return "P: " + this.field_71452_i.func_78869_b() + ". T: " + this.field_71441_e.func_72981_t();
   }

   public void func_71354_a(int p_71354_1_) {
      this.field_71441_e.func_72974_f();
      this.field_71441_e.func_73022_a();
      int var2 = 0;
      String var3 = null;
      if(this.field_71439_g != null) {
         var2 = this.field_71439_g.func_145782_y();
         this.field_71441_e.func_72900_e(this.field_71439_g);
         var3 = this.field_71439_g.func_142021_k();
      }

      this.field_71451_h = null;
      this.field_71439_g = this.field_71442_b.func_147493_a(this.field_71441_e, this.field_71439_g == null?new StatFileWriter():this.field_71439_g.func_146107_m());
      this.field_71439_g.field_71093_bK = p_71354_1_;
      this.field_71451_h = this.field_71439_g;
      this.field_71439_g.func_70065_x();
      this.field_71439_g.func_142020_c(var3);
      this.field_71441_e.func_72838_d(this.field_71439_g);
      this.field_71442_b.func_78745_b(this.field_71439_g);
      this.field_71439_g.field_71158_b = new MovementInputFromOptions(this.field_71474_y);
      this.field_71439_g.func_145769_d(var2);
      this.field_71442_b.func_78748_a(this.field_71439_g);
      if(this.field_71462_r instanceof GuiGameOver) {
         this.func_147108_a((GuiScreen)null);
      }

   }

   public final boolean func_71355_q() {
      return this.field_71459_aj;
   }

   public NetHandlerPlayClient func_147114_u() {
      return this.field_71439_g != null?this.field_71439_g.field_71174_a:null;
   }

   public static boolean func_71382_s() {
      return field_71432_P == null || !field_71432_P.field_71474_y.field_74319_N;
   }

   public static boolean func_71375_t() {
      return field_71432_P != null && field_71432_P.field_71474_y.field_74347_j;
   }

   public static boolean func_71379_u() {
      return field_71432_P != null && field_71432_P.field_71474_y.field_74348_k != 0;
   }

   private void func_147112_ai() {
      if(this.field_71476_x != null) {
         boolean var1 = this.field_71439_g.field_71075_bZ.field_75098_d;
         int var3 = 0;
         boolean var4 = false;
         Item var2;
         int var5;
         if(this.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
            var5 = this.field_71476_x.field_72311_b;
            int var6 = this.field_71476_x.field_72312_c;
            int var7 = this.field_71476_x.field_72309_d;
            Block var8 = this.field_71441_e.func_147439_a(var5, var6, var7);
            if(var8.func_149688_o() == Material.field_151579_a) {
               return;
            }

            var2 = var8.func_149694_d(this.field_71441_e, var5, var6, var7);
            if(var2 == null) {
               return;
            }

            var4 = var2.func_77614_k();
            Block var9 = var2 instanceof ItemBlock && !var8.func_149648_K()?Block.func_149634_a(var2):var8;
            var3 = var9.func_149643_k(this.field_71441_e, var5, var6, var7);
         } else {
            if(this.field_71476_x.field_72313_a != MovingObjectPosition.MovingObjectType.ENTITY || this.field_71476_x.field_72308_g == null || !var1) {
               return;
            }

            if(this.field_71476_x.field_72308_g instanceof EntityPainting) {
               var2 = Items.field_151159_an;
            } else if(this.field_71476_x.field_72308_g instanceof EntityLeashKnot) {
               var2 = Items.field_151058_ca;
            } else if(this.field_71476_x.field_72308_g instanceof EntityItemFrame) {
               EntityItemFrame var10 = (EntityItemFrame)this.field_71476_x.field_72308_g;
               ItemStack var12 = var10.func_82335_i();
               if(var12 == null) {
                  var2 = Items.field_151160_bD;
               } else {
                  var2 = var12.func_77973_b();
                  var3 = var12.func_77960_j();
                  var4 = true;
               }
            } else if(this.field_71476_x.field_72308_g instanceof EntityMinecart) {
               EntityMinecart var11 = (EntityMinecart)this.field_71476_x.field_72308_g;
               if(var11.func_94087_l() == 2) {
                  var2 = Items.field_151109_aJ;
               } else if(var11.func_94087_l() == 1) {
                  var2 = Items.field_151108_aI;
               } else if(var11.func_94087_l() == 3) {
                  var2 = Items.field_151142_bV;
               } else if(var11.func_94087_l() == 5) {
                  var2 = Items.field_151140_bW;
               } else if(var11.func_94087_l() == 6) {
                  var2 = Items.field_151095_cc;
               } else {
                  var2 = Items.field_151143_au;
               }
            } else if(this.field_71476_x.field_72308_g instanceof EntityBoat) {
               var2 = Items.field_151124_az;
            } else {
               var2 = Items.field_151063_bx;
               var3 = EntityList.func_75619_a(this.field_71476_x.field_72308_g);
               var4 = true;
               if(var3 <= 0 || !EntityList.field_75627_a.containsKey(Integer.valueOf(var3))) {
                  return;
               }
            }
         }

         this.field_71439_g.field_71071_by.func_146030_a(var2, var3, var4, var1);
         if(var1) {
            var5 = this.field_71439_g.field_71069_bz.field_75151_b.size() - 9 + this.field_71439_g.field_71071_by.field_70461_c;
            this.field_71442_b.func_78761_a(this.field_71439_g.field_71071_by.func_70301_a(this.field_71439_g.field_71071_by.field_70461_c), var5);
         }

      }
   }

   public CrashReport func_71396_d(CrashReport p_71396_1_) {
      p_71396_1_.func_85056_g().func_71500_a("Launched Version", new Callable() {

         private static final String __OBFID = "CL_00000643";

         public String call() {
            return Minecraft.this.field_110447_Z;
         }
         // $FF: synthetic method
         public Object call() {
            return this.call();
         }
      });
      p_71396_1_.func_85056_g().func_71500_a("LWJGL", new Callable() {

         private static final String __OBFID = "CL_00000644";

         public String call() {
            return Sys.getVersion();
         }
         // $FF: synthetic method
         public Object call() {
            return this.call();
         }
      });
      p_71396_1_.func_85056_g().func_71500_a("OpenGL", new Callable() {

         private static final String __OBFID = "CL_00000645";

         public String call() {
            return GL11.glGetString(7937) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936);
         }
         // $FF: synthetic method
         public Object call() {
            return this.call();
         }
      });
      p_71396_1_.func_85056_g().func_71500_a("Is Modded", new Callable() {

         private static final String __OBFID = "CL_00000646";

         public String call() {
            String var1 = ClientBrandRetriever.getClientModName();
            return !var1.equals("vanilla")?"Definitely; Client brand changed to \'" + var1 + "\'":(Minecraft.class.getSigners() == null?"Very likely; Jar signature invalidated":"Probably not. Jar signature remains and client brand is untouched.");
         }
         // $FF: synthetic method
         public Object call() {
            return this.call();
         }
      });
      p_71396_1_.func_85056_g().func_71500_a("Type", new Callable() {

         private static final String __OBFID = "CL_00000647";

         public String call() {
            return "Client (map_client.txt)";
         }
         // $FF: synthetic method
         public Object call() {
            return this.call();
         }
      });
      p_71396_1_.func_85056_g().func_71500_a("Resource Packs", new Callable() {

         private static final String __OBFID = "CL_00000633";

         public String call() {
            return Minecraft.this.field_71474_y.field_151453_l.toString();
         }
         // $FF: synthetic method
         public Object call() {
            return this.call();
         }
      });
      p_71396_1_.func_85056_g().func_71500_a("Current Language", new Callable() {

         private static final String __OBFID = "CL_00000634";

         public String call() {
            return Minecraft.this.field_135017_as.func_135041_c().toString();
         }
         // $FF: synthetic method
         public Object call() {
            return this.call();
         }
      });
      p_71396_1_.func_85056_g().func_71500_a("Profiler Position", new Callable() {

         private static final String __OBFID = "CL_00000635";

         public String call() {
            return Minecraft.this.field_71424_I.field_76327_a?Minecraft.this.field_71424_I.func_76322_c():"N/A (disabled)";
         }
         // $FF: synthetic method
         public Object call() {
            return this.call();
         }
      });
      p_71396_1_.func_85056_g().func_71500_a("Vec3 Pool Size", new Callable() {

         private static final String __OBFID = "CL_00000636";

         public String call() {
            int var1 = Minecraft.this.field_71441_e.func_82732_R().func_82591_c();
            int var2 = 56 * var1;
            int var3 = var2 / 1024 / 1024;
            int var4 = Minecraft.this.field_71441_e.func_82732_R().func_82590_d();
            int var5 = 56 * var4;
            int var6 = var5 / 1024 / 1024;
            return var1 + " (" + var2 + " bytes; " + var3 + " MB) allocated, " + var4 + " (" + var5 + " bytes; " + var6 + " MB) used";
         }
         // $FF: synthetic method
         public Object call() {
            return this.call();
         }
      });
      p_71396_1_.func_85056_g().func_71500_a("Anisotropic Filtering", new Callable() {

         private static final String __OBFID = "CL_00000637";

         public String call() {
            return Minecraft.this.field_71474_y.field_151443_J == 1?"Off (1)":"On (" + Minecraft.this.field_71474_y.field_151443_J + ")";
         }
         // $FF: synthetic method
         public Object call() {
            return this.call();
         }
      });
      if(this.field_71441_e != null) {
         this.field_71441_e.func_72914_a(p_71396_1_);
      }

      return p_71396_1_;
   }

   public static Minecraft func_71410_x() {
      return field_71432_P;
   }

   public void func_147106_B() {
      this.field_71468_ad = true;
   }

   public void func_70000_a(PlayerUsageSnooper p_70000_1_) {
      p_70000_1_.func_76472_a("fps", Integer.valueOf(field_71470_ab));
      p_70000_1_.func_76472_a("vsync_enabled", Boolean.valueOf(this.field_71474_y.field_74352_v));
      p_70000_1_.func_76472_a("display_frequency", Integer.valueOf(Display.getDisplayMode().getFrequency()));
      p_70000_1_.func_76472_a("display_type", this.field_71431_Q?"fullscreen":"windowed");
      p_70000_1_.func_76472_a("run_time", Long.valueOf((MinecraftServer.func_130071_aq() - p_70000_1_.func_130105_g()) / 60L * 1000L));
      p_70000_1_.func_76472_a("resource_packs", Integer.valueOf(this.field_110448_aq.func_110613_c().size()));
      int var2 = 0;
      Iterator var3 = this.field_110448_aq.func_110613_c().iterator();

      while(var3.hasNext()) {
         ResourcePackRepository.Entry var4 = (ResourcePackRepository.Entry)var3.next();
         p_70000_1_.func_76472_a("resource_pack[" + var2++ + "]", var4.func_110515_d());
      }

      if(this.field_71437_Z != null && this.field_71437_Z.func_80003_ah() != null) {
         p_70000_1_.func_76472_a("snooper_partner", this.field_71437_Z.func_80003_ah().func_80006_f());
      }

   }

   public void func_70001_b(PlayerUsageSnooper p_70001_1_) {
      p_70001_1_.func_76472_a("opengl_version", GL11.glGetString(7938));
      p_70001_1_.func_76472_a("opengl_vendor", GL11.glGetString(7936));
      p_70001_1_.func_76472_a("client_brand", ClientBrandRetriever.getClientModName());
      p_70001_1_.func_76472_a("launched_version", this.field_110447_Z);
      ContextCapabilities var2 = GLContext.getCapabilities();
      p_70001_1_.func_76472_a("gl_caps[ARB_multitexture]", Boolean.valueOf(var2.GL_ARB_multitexture));
      p_70001_1_.func_76472_a("gl_caps[ARB_multisample]", Boolean.valueOf(var2.GL_ARB_multisample));
      p_70001_1_.func_76472_a("gl_caps[ARB_texture_cube_map]", Boolean.valueOf(var2.GL_ARB_texture_cube_map));
      p_70001_1_.func_76472_a("gl_caps[ARB_vertex_blend]", Boolean.valueOf(var2.GL_ARB_vertex_blend));
      p_70001_1_.func_76472_a("gl_caps[ARB_matrix_palette]", Boolean.valueOf(var2.GL_ARB_matrix_palette));
      p_70001_1_.func_76472_a("gl_caps[ARB_vertex_program]", Boolean.valueOf(var2.GL_ARB_vertex_program));
      p_70001_1_.func_76472_a("gl_caps[ARB_vertex_shader]", Boolean.valueOf(var2.GL_ARB_vertex_shader));
      p_70001_1_.func_76472_a("gl_caps[ARB_fragment_program]", Boolean.valueOf(var2.GL_ARB_fragment_program));
      p_70001_1_.func_76472_a("gl_caps[ARB_fragment_shader]", Boolean.valueOf(var2.GL_ARB_fragment_shader));
      p_70001_1_.func_76472_a("gl_caps[ARB_shader_objects]", Boolean.valueOf(var2.GL_ARB_shader_objects));
      p_70001_1_.func_76472_a("gl_caps[ARB_vertex_buffer_object]", Boolean.valueOf(var2.GL_ARB_vertex_buffer_object));
      p_70001_1_.func_76472_a("gl_caps[ARB_framebuffer_object]", Boolean.valueOf(var2.GL_ARB_framebuffer_object));
      p_70001_1_.func_76472_a("gl_caps[ARB_pixel_buffer_object]", Boolean.valueOf(var2.GL_ARB_pixel_buffer_object));
      p_70001_1_.func_76472_a("gl_caps[ARB_uniform_buffer_object]", Boolean.valueOf(var2.GL_ARB_uniform_buffer_object));
      p_70001_1_.func_76472_a("gl_caps[ARB_texture_non_power_of_two]", Boolean.valueOf(var2.GL_ARB_texture_non_power_of_two));
      p_70001_1_.func_76472_a("gl_caps[gl_max_vertex_uniforms]", Integer.valueOf(GL11.glGetInteger('\u8b4a')));
      p_70001_1_.func_76472_a("gl_caps[gl_max_fragment_uniforms]", Integer.valueOf(GL11.glGetInteger('\u8b49')));
      p_70001_1_.func_76472_a("gl_max_texture_size", Integer.valueOf(func_71369_N()));
   }

   public static int func_71369_N() {
      for(int var0 = 16384; var0 > 0; var0 >>= 1) {
         GL11.glTexImage2D('\u8064', 0, 6408, var0, var0, 0, 6408, 5121, (ByteBuffer)null);
         int var1 = GL11.glGetTexLevelParameteri('\u8064', 0, 4096);
         if(var1 != 0) {
            return var0;
         }
      }

      return -1;
   }

   public boolean func_70002_Q() {
      return this.field_71474_y.field_74355_t;
   }

   public void func_71351_a(ServerData p_71351_1_) {
      this.field_71422_O = p_71351_1_;
   }

   public ServerData func_147104_D() {
      return this.field_71422_O;
   }

   public boolean func_71387_A() {
      return this.field_71455_al;
   }

   public boolean func_71356_B() {
      return this.field_71455_al && this.field_71437_Z != null;
   }

   public IntegratedServer func_71401_C() {
      return this.field_71437_Z;
   }

   public static void func_71363_D() {
      if(field_71432_P != null) {
         IntegratedServer var0 = field_71432_P.func_71401_C();
         if(var0 != null) {
            var0.func_71260_j();
         }

      }
   }

   public PlayerUsageSnooper func_71378_E() {
      return this.field_71427_U;
   }

   public static long func_71386_F() {
      return Sys.getTime() * 1000L / Sys.getTimerResolution();
   }

   public boolean func_71372_G() {
      return this.field_71431_Q;
   }

   public Session func_110432_I() {
      return this.field_71449_j;
   }

   public Proxy func_110437_J() {
      return this.field_110453_aa;
   }

   public TextureManager func_110434_K() {
      return this.field_71446_o;
   }

   public IResourceManager func_110442_L() {
      return this.field_110451_am;
   }

   public ResourcePackRepository func_110438_M() {
      return this.field_110448_aq;
   }

   public LanguageManager func_135016_M() {
      return this.field_135017_as;
   }

   public TextureMap func_147117_R() {
      return this.field_147128_au;
   }

   public boolean func_147111_S() {
      return this.field_147129_ai;
   }

   public boolean func_147113_T() {
      return this.field_71445_n;
   }

   public SoundHandler func_147118_V() {
      return this.field_147127_av;
   }

   public MusicTicker.MusicType func_147109_W() {
      return this.field_71462_r instanceof GuiWinGame?MusicTicker.MusicType.CREDITS:(this.field_71439_g != null?(this.field_71439_g.field_70170_p.field_73011_w instanceof WorldProviderHell?MusicTicker.MusicType.NETHER:(this.field_71439_g.field_70170_p.field_73011_w instanceof WorldProviderEnd?(BossStatus.field_82827_c != null && BossStatus.field_82826_b > 0?MusicTicker.MusicType.END_BOSS:MusicTicker.MusicType.END):(this.field_71439_g.field_71075_bZ.field_75098_d && this.field_71439_g.field_71075_bZ.field_75101_c?MusicTicker.MusicType.CREATIVE:MusicTicker.MusicType.GAME))):MusicTicker.MusicType.MENU);
   }


   // $FF: synthetic class
   static final class SwitchMovingObjectType {

      // $FF: synthetic field
      static final int[] field_151437_a = new int[MovingObjectPosition.MovingObjectType.values().length];
      private static final String __OBFID = "CL_00000638";


      static {
         try {
            field_151437_a[MovingObjectPosition.MovingObjectType.ENTITY.ordinal()] = 1;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            field_151437_a[MovingObjectPosition.MovingObjectType.BLOCK.ordinal()] = 2;
         } catch (NoSuchFieldError var1) {
            ;
         }

      }
   }
}
