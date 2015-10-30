package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBuyRealms;
import net.minecraft.client.gui.GuiScreenConfigureWorld;
import net.minecraft.client.gui.GuiScreenConfirmation;
import net.minecraft.client.gui.GuiScreenLongRunningTask;
import net.minecraft.client.gui.GuiScreenRealmsPinger;
import net.minecraft.client.gui.GuiScreenSelectLocation;
import net.minecraft.client.gui.TaskOnlineConnect;
import net.minecraft.client.gui.mco.GuiScreenCreateOnlineWorld;
import net.minecraft.client.gui.mco.GuiScreenPendingInvitation;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.mco.McoServerList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiScreenOnlineServers extends GuiScreen {

   private static final AtomicInteger field_146701_a = new AtomicInteger(0);
   private static final Logger field_146695_f = LogManager.getLogger();
   private static final ResourceLocation field_146697_g = new ResourceLocation("textures/gui/widgets.png");
   private static final ResourceLocation field_146702_h = new ResourceLocation("textures/gui/title/minecraft.png");
   private static McoServerList field_146703_i = new McoServerList();
   private static GuiScreenRealmsPinger field_146709_r = new GuiScreenRealmsPinger();
   private static final ThreadPoolExecutor field_146708_s = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());
   private GuiScreen field_146707_t;
   private GuiScreenOnlineServers.OnlineServerList field_146706_u;
   private long field_146705_v = -1L;
   private GuiButton field_146704_w;
   private GuiButton field_146712_x;
   private GuiButtonLink field_146711_y;
   private GuiButton field_146710_z;
   private String field_146698_A;
   private boolean field_146699_B;
   private List field_146700_C = Lists.newArrayList();
   private volatile int field_146694_D = 0;
   private int field_146696_E;
   private static final String __OBFID = "CL_00000792";


   public GuiScreenOnlineServers(GuiScreen p_i1123_1_) {
      this.field_146707_t = p_i1123_1_;
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_146292_n.clear();
      field_146703_i.func_148475_a(this.field_146297_k.func_110432_I());
      if(!this.field_146699_B) {
         this.field_146699_B = true;
         this.field_146706_u = new GuiScreenOnlineServers.OnlineServerList();
      } else {
         this.field_146706_u.func_148346_a(this.field_146294_l, this.field_146295_m, 32, this.field_146295_m - 64);
      }

      this.func_146688_g();
   }

   public void func_146688_g() {
      this.field_146292_n.add(this.field_146710_z = new GuiButton(1, this.field_146294_l / 2 - 154, this.field_146295_m - 52, 100, 20, I18n.func_135052_a("mco.selectServer.play", new Object[0])));
      String var1 = this.field_146694_D > 0?I18n.func_135052_a("mco.selectServer.create", new Object[0]):I18n.func_135052_a("mco.selectServer.buy", new Object[0]);
      this.field_146292_n.add(this.field_146712_x = new GuiButton(2, this.field_146294_l / 2 - 48, this.field_146295_m - 52, 100, 20, var1));
      this.field_146292_n.add(this.field_146704_w = new GuiButton(3, this.field_146294_l / 2 + 58, this.field_146295_m - 52, 100, 20, I18n.func_135052_a("mco.selectServer.configure", new Object[0])));
      this.field_146292_n.add(this.field_146711_y = new GuiButtonLink(4, this.field_146294_l / 2 - 154, this.field_146295_m - 28, 154, 20, I18n.func_135052_a("mco.selectServer.moreinfo", new Object[0])));
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 + 6, this.field_146295_m - 28, 153, 20, I18n.func_135052_a("gui.cancel", new Object[0])));
      McoServer var2 = this.func_146691_a(this.field_146705_v);
      this.field_146710_z.field_146124_l = var2 != null && var2.field_148808_d.equals(McoServer.State.OPEN.name()) && !var2.field_148819_h;
      this.field_146704_w.field_146124_l = var2 != null && !var2.field_148808_d.equals(McoServer.State.ADMIN_LOCK.name());
      if(var2 != null && !var2.field_148809_e.equals(this.field_146297_k.func_110432_I().func_111285_a())) {
         this.field_146704_w.field_146126_j = I18n.func_135052_a("mco.selectServer.leave", new Object[0]);
      }

   }

   public void func_73876_c() {
      super.func_73876_c();
      ++this.field_146696_E;
      if(field_146703_i.func_148472_a()) {
         List var1 = field_146703_i.func_148473_c();
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            McoServer var3 = (McoServer)var2.next();
            Iterator var4 = this.field_146700_C.iterator();

            while(var4.hasNext()) {
               McoServer var5 = (McoServer)var4.next();
               if(var3.field_148812_a == var5.field_148812_a) {
                  var3.func_148799_a(var5);
                  break;
               }
            }
         }

         this.field_146694_D = field_146703_i.func_148469_e();
         this.field_146700_C = var1;
         field_146703_i.func_148479_b();
      }

      this.field_146712_x.field_146126_j = this.field_146694_D > 0?I18n.func_135052_a("mco.selectServer.create", new Object[0]):I18n.func_135052_a("mco.selectServer.buy", new Object[0]);
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void func_146284_a(GuiButton p_146284_1_) {
      if(p_146284_1_.field_146124_l) {
         if(p_146284_1_.field_146127_k == 1) {
            this.func_146656_d(this.field_146705_v);
         } else if(p_146284_1_.field_146127_k == 3) {
            this.func_146667_u();
         } else if(p_146284_1_.field_146127_k == 0) {
            this.func_146669_s();
            this.field_146297_k.func_147108_a(this.field_146707_t);
         } else if(p_146284_1_.field_146127_k == 2) {
            this.func_146669_s();
            this.field_146297_k.func_147108_a((GuiScreen)(this.field_146694_D > 0?new GuiScreenCreateOnlineWorld(this):new GuiScreenBuyRealms(this)));
         } else if(p_146284_1_.field_146127_k == 4) {
            this.func_146660_t();
         } else {
            this.field_146706_u.func_148357_a(p_146284_1_);
         }

      }
   }

   private void func_146669_s() {
      field_146703_i.func_148476_f();
      field_146709_r.func_148507_b();
   }

   private void func_146660_t() {
      String var1 = I18n.func_135052_a("mco.more.info.question.line1", new Object[0]);
      String var2 = I18n.func_135052_a("mco.more.info.question.line2", new Object[0]);
      this.field_146297_k.func_147108_a(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Info, var1, var2, 4));
   }

   private void func_146667_u() {
      McoServer var1 = this.func_146691_a(this.field_146705_v);
      if(var1 != null) {
         if(this.field_146297_k.func_110432_I().func_111285_a().equals(var1.field_148809_e)) {
            McoServer var2 = this.func_146677_c(var1.field_148812_a);
            if(var2 != null) {
               this.func_146669_s();
               this.field_146297_k.func_147108_a(new GuiScreenConfigureWorld(this, var2));
            }
         } else {
            String var4 = I18n.func_135052_a("mco.configure.world.leave.question.line1", new Object[0]);
            String var3 = I18n.func_135052_a("mco.configure.world.leave.question.line2", new Object[0]);
            this.field_146297_k.func_147108_a(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Info, var4, var3, 3));
         }
      }

   }

   private McoServer func_146691_a(long p_146691_1_) {
      Iterator var3 = this.field_146700_C.iterator();

      McoServer var4;
      do {
         if(!var3.hasNext()) {
            return null;
         }

         var4 = (McoServer)var3.next();
      } while(var4.field_148812_a != p_146691_1_);

      return var4;
   }

   private int func_146672_b(long p_146672_1_) {
      for(int var3 = 0; var3 < this.field_146700_C.size(); ++var3) {
         if(((McoServer)this.field_146700_C.get(var3)).field_148812_a == p_146672_1_) {
            return var3;
         }
      }

      return -1;
   }

   public void func_73878_a(boolean p_73878_1_, int p_73878_2_) {
      if(p_73878_2_ == 3 && p_73878_1_) {
         (new Thread("MCO Configure Requester #" + field_146701_a.incrementAndGet()) {

            private static final String __OBFID = "CL_00000793";

            public void run() {
               try {
                  McoServer var1 = GuiScreenOnlineServers.this.func_146691_a(GuiScreenOnlineServers.this.field_146705_v);
                  if(var1 != null) {
                     Session var2 = GuiScreenOnlineServers.this.field_146297_k.func_110432_I();
                     McoClient var3 = new McoClient(var2.func_111286_b(), var2.func_111285_a(), "1.7.2", Minecraft.func_71410_x().func_110437_J());
                     GuiScreenOnlineServers.field_146703_i.func_148470_a(var1);
                     GuiScreenOnlineServers.this.field_146700_C.remove(var1);
                     var3.func_148698_c(var1.field_148812_a);
                     GuiScreenOnlineServers.field_146703_i.func_148470_a(var1);
                     GuiScreenOnlineServers.this.field_146700_C.remove(var1);
                     GuiScreenOnlineServers.this.func_146685_v();
                  }
               } catch (ExceptionMcoService var4) {
                  GuiScreenOnlineServers.field_146695_f.error("Couldn\'t configure world");
               }

            }
         }).start();
      } else if(p_73878_2_ == 4 && p_73878_1_) {
         this.field_146711_y.func_146138_a("http://realms.minecraft.net/");
      }

      this.field_146297_k.func_147108_a(this);
   }

   private void func_146685_v() {
      int var1 = this.func_146672_b(this.field_146705_v);
      if(this.field_146700_C.size() - 1 == var1) {
         --var1;
      }

      if(this.field_146700_C.size() == 0) {
         var1 = -1;
      }

      if(var1 >= 0 && var1 < this.field_146700_C.size()) {
         this.field_146705_v = ((McoServer)this.field_146700_C.get(var1)).field_148812_a;
      }

   }

   public void func_146670_h() {
      this.field_146705_v = -1L;
   }

   private McoServer func_146677_c(long p_146677_1_) {
      Session var3 = this.field_146297_k.func_110432_I();
      McoClient var4 = new McoClient(var3.func_111286_b(), var3.func_111285_a(), "1.7.2", Minecraft.func_71410_x().func_110437_J());

      try {
         return var4.func_148709_a(p_146677_1_);
      } catch (ExceptionMcoService var6) {
         field_146695_f.error("Couldn\'t get own world");
      } catch (IOException var7) {
         field_146695_f.error("Couldn\'t parse response getting own world");
      }

      return null;
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {
      if(p_73869_2_ == 59) {
         this.field_146297_k.field_71474_y.field_80005_w = !this.field_146297_k.field_71474_y.field_80005_w;
         this.field_146297_k.field_71474_y.func_74303_b();
      } else {
         if(p_73869_2_ != 28 && p_73869_2_ != 156) {
            super.func_73869_a(p_73869_1_, p_73869_2_);
         } else {
            this.func_146284_a((GuiButton)this.field_146292_n.get(0));
         }

      }
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.field_146698_A = null;
      this.func_146276_q_();
      this.field_146706_u.func_148350_a(p_73863_1_, p_73863_2_, p_73863_3_);
      this.func_146665_b(this.field_146294_l / 2 - 50, 7);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      if(this.field_146698_A != null) {
         this.func_146658_b(this.field_146698_A, p_73863_1_, p_73863_2_);
      }

      this.func_146659_c(p_73863_1_, p_73863_2_);
   }

   private void func_146665_b(int p_146665_1_, int p_146665_2_) {
      this.field_146297_k.func_110434_K().func_110577_a(field_146702_h);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      this.func_73729_b(p_146665_1_ * 2, p_146665_2_ * 2, 0, 97, 200, 50);
      GL11.glPopMatrix();
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      if(this.func_146662_d(p_73864_1_, p_73864_2_) && field_146703_i.func_148468_d() != 0) {
         this.func_146669_s();
         GuiScreenPendingInvitation var4 = new GuiScreenPendingInvitation(this.field_146707_t);
         this.field_146297_k.func_147108_a(var4);
      }

   }

   private void func_146659_c(int p_146659_1_, int p_146659_2_) {
      int var3 = field_146703_i.func_148468_d();
      boolean var4 = this.func_146662_d(p_146659_1_, p_146659_2_);
      this.field_146297_k.func_110434_K().func_110577_a(field_146697_g);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPushMatrix();
      this.func_73729_b(this.field_146294_l / 2 + 58, 12, var4?166:182, 22, 16, 16);
      GL11.glPopMatrix();
      int var5;
      int var6;
      if(var3 != 0) {
         var5 = 198 + (Math.min(var3, 6) - 1) * 8;
         var6 = (int)(Math.max(0.0F, Math.max(MathHelper.func_76126_a((float)(10 + this.field_146696_E) * 0.57F), MathHelper.func_76134_b((float)this.field_146696_E * 0.35F))) * -6.0F);
         this.field_146297_k.func_110434_K().func_110577_a(field_146697_g);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPushMatrix();
         this.func_73729_b(this.field_146294_l / 2 + 58 + 4, 16 + var6, var5, 22, 8, 8);
         GL11.glPopMatrix();
      }

      if(var4) {
         var5 = p_146659_1_ + 12;
         var6 = p_146659_2_ - 12;
         String var7 = "";
         if(var3 != 0) {
            var7 = I18n.func_135052_a("mco.invites.pending", new Object[0]);
         } else {
            var7 = I18n.func_135052_a("mco.invites.nopending", new Object[0]);
         }

         int var8 = this.field_146289_q.func_78256_a(var7);
         this.func_73733_a(var5 - 3, var6 - 3, var5 + var8 + 3, var6 + 8 + 3, -1073741824, -1073741824);
         this.field_146289_q.func_78261_a(var7, var5, var6, -1);
      }

   }

   private boolean func_146662_d(int p_146662_1_, int p_146662_2_) {
      int var3 = this.field_146294_l / 2 + 56;
      int var4 = this.field_146294_l / 2 + 78;
      byte var5 = 13;
      byte var6 = 27;
      return var3 <= p_146662_1_ && p_146662_1_ <= var4 && var5 <= p_146662_2_ && p_146662_2_ <= var6;
   }

   private void func_146656_d(long p_146656_1_) {
      McoServer var3 = this.func_146691_a(p_146656_1_);
      if(var3 != null) {
         this.func_146669_s();
         GuiScreenLongRunningTask var4 = new GuiScreenLongRunningTask(this.field_146297_k, this, new TaskOnlineConnect(this, var3));
         var4.func_146902_g();
         this.field_146297_k.func_147108_a(var4);
      }

   }

   private void func_146661_c(int p_146661_1_, int p_146661_2_, int p_146661_3_, int p_146661_4_) {
      this.field_146297_k.func_110434_K().func_110577_a(field_146697_g);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      this.func_73729_b(p_146661_1_ * 2, p_146661_2_ * 2, 191, 0, 16, 15);
      GL11.glPopMatrix();
      if(p_146661_3_ >= p_146661_1_ && p_146661_3_ <= p_146661_1_ + 9 && p_146661_4_ >= p_146661_2_ && p_146661_4_ <= p_146661_2_ + 9) {
         this.field_146698_A = I18n.func_135052_a("mco.selectServer.expired", new Object[0]);
      }

   }

   private void func_146687_b(int p_146687_1_, int p_146687_2_, int p_146687_3_, int p_146687_4_, int p_146687_5_) {
      if(this.field_146696_E % 20 < 10) {
         this.field_146297_k.func_110434_K().func_110577_a(field_146697_g);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glPushMatrix();
         GL11.glScalef(0.5F, 0.5F, 0.5F);
         this.func_73729_b(p_146687_1_ * 2, p_146687_2_ * 2, 207, 0, 16, 15);
         GL11.glPopMatrix();
      }

      if(p_146687_3_ >= p_146687_1_ && p_146687_3_ <= p_146687_1_ + 9 && p_146687_4_ >= p_146687_2_ && p_146687_4_ <= p_146687_2_ + 9) {
         if(p_146687_5_ == 0) {
            this.field_146698_A = I18n.func_135052_a("mco.selectServer.expires.soon", new Object[0]);
         } else if(p_146687_5_ == 1) {
            this.field_146698_A = I18n.func_135052_a("mco.selectServer.expires.day", new Object[0]);
         } else {
            this.field_146698_A = I18n.func_135052_a("mco.selectServer.expires.days", new Object[]{Integer.valueOf(p_146687_5_)});
         }
      }

   }

   private void func_146683_d(int p_146683_1_, int p_146683_2_, int p_146683_3_, int p_146683_4_) {
      this.field_146297_k.func_110434_K().func_110577_a(field_146697_g);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      this.func_73729_b(p_146683_1_ * 2, p_146683_2_ * 2, 207, 0, 16, 15);
      GL11.glPopMatrix();
      if(p_146683_3_ >= p_146683_1_ && p_146683_3_ <= p_146683_1_ + 9 && p_146683_4_ >= p_146683_2_ && p_146683_4_ <= p_146683_2_ + 9) {
         this.field_146698_A = I18n.func_135052_a("mco.selectServer.open", new Object[0]);
      }

   }

   private void func_146671_e(int p_146671_1_, int p_146671_2_, int p_146671_3_, int p_146671_4_) {
      this.field_146297_k.func_110434_K().func_110577_a(field_146697_g);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      this.func_73729_b(p_146671_1_ * 2, p_146671_2_ * 2, 223, 0, 16, 15);
      GL11.glPopMatrix();
      if(p_146671_3_ >= p_146671_1_ && p_146671_3_ <= p_146671_1_ + 9 && p_146671_4_ >= p_146671_2_ && p_146671_4_ <= p_146671_2_ + 9) {
         this.field_146698_A = I18n.func_135052_a("mco.selectServer.closed", new Object[0]);
      }

   }

   private void func_146666_f(int p_146666_1_, int p_146666_2_, int p_146666_3_, int p_146666_4_) {
      this.field_146297_k.func_110434_K().func_110577_a(field_146697_g);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      this.func_73729_b(p_146666_1_ * 2, p_146666_2_ * 2, 223, 0, 16, 15);
      GL11.glPopMatrix();
      if(p_146666_3_ >= p_146666_1_ && p_146666_3_ <= p_146666_1_ + 9 && p_146666_4_ >= p_146666_2_ && p_146666_4_ <= p_146666_2_ + 9) {
         this.field_146698_A = I18n.func_135052_a("mco.selectServer.locked", new Object[0]);
      }

   }

   protected void func_146658_b(String p_146658_1_, int p_146658_2_, int p_146658_3_) {
      if(p_146658_1_ != null) {
         int var4 = p_146658_2_ + 12;
         int var5 = p_146658_3_ - 12;
         int var6 = this.field_146289_q.func_78256_a(p_146658_1_);
         this.func_73733_a(var4 - 3, var5 - 3, var4 + var6 + 3, var5 + 8 + 3, -1073741824, -1073741824);
         this.field_146289_q.func_78261_a(p_146658_1_, var4, var5, -1);
      }
   }


   class OnlineServerList extends GuiScreenSelectLocation {

      private static final String __OBFID = "CL_00000794";


      public OnlineServerList() {
         super(GuiScreenOnlineServers.this.field_146297_k, GuiScreenOnlineServers.this.field_146294_l, GuiScreenOnlineServers.this.field_146295_m, 32, GuiScreenOnlineServers.this.field_146295_m - 64, 36);
      }

      protected int func_148355_a() {
         return GuiScreenOnlineServers.this.field_146700_C.size() + 1;
      }

      protected void func_148352_a(int p_148352_1_, boolean p_148352_2_) {
         if(p_148352_1_ < GuiScreenOnlineServers.this.field_146700_C.size()) {
            McoServer var3 = (McoServer)GuiScreenOnlineServers.this.field_146700_C.get(p_148352_1_);
            GuiScreenOnlineServers.this.field_146705_v = var3.field_148812_a;
            if(!GuiScreenOnlineServers.this.field_146297_k.func_110432_I().func_111285_a().equals(var3.field_148809_e)) {
               GuiScreenOnlineServers.this.field_146704_w.field_146126_j = I18n.func_135052_a("mco.selectServer.leave", new Object[0]);
            } else {
               GuiScreenOnlineServers.this.field_146704_w.field_146126_j = I18n.func_135052_a("mco.selectServer.configure", new Object[0]);
            }

            GuiScreenOnlineServers.this.field_146704_w.field_146124_l = !var3.field_148808_d.equals(McoServer.State.ADMIN_LOCK.name());
            GuiScreenOnlineServers.this.field_146710_z.field_146124_l = var3.field_148808_d.equals(McoServer.State.OPEN.name()) && !var3.field_148819_h;
            if(p_148352_2_ && GuiScreenOnlineServers.this.field_146710_z.field_146124_l) {
               GuiScreenOnlineServers.this.func_146656_d(GuiScreenOnlineServers.this.field_146705_v);
            }

         }
      }

      protected boolean func_148356_a(int p_148356_1_) {
         return p_148356_1_ == GuiScreenOnlineServers.this.func_146672_b(GuiScreenOnlineServers.this.field_146705_v);
      }

      protected boolean func_148349_b(int p_148349_1_) {
         try {
            return p_148349_1_ >= 0 && p_148349_1_ < GuiScreenOnlineServers.this.field_146700_C.size() && ((McoServer)GuiScreenOnlineServers.this.field_146700_C.get(p_148349_1_)).field_148809_e.toLowerCase().equals(GuiScreenOnlineServers.this.field_146297_k.func_110432_I().func_111285_a());
         } catch (Exception var3) {
            return false;
         }
      }

      protected int func_148351_b() {
         return this.func_148355_a() * 36;
      }

      protected void func_148358_c() {
         GuiScreenOnlineServers.this.func_146276_q_();
      }

      protected void func_148348_a(int p_148348_1_, int p_148348_2_, int p_148348_3_, int p_148348_4_, Tessellator p_148348_5_) {
         if(p_148348_1_ < GuiScreenOnlineServers.this.field_146700_C.size()) {
            this.func_148390_b(p_148348_1_, p_148348_2_, p_148348_3_, p_148348_4_, p_148348_5_);
         }

      }

      private void func_148390_b(int p_148390_1_, int p_148390_2_, int p_148390_3_, int p_148390_4_, Tessellator p_148390_5_) {
         final McoServer var6 = (McoServer)GuiScreenOnlineServers.this.field_146700_C.get(p_148390_1_);
         if(!var6.field_148814_o) {
            var6.field_148814_o = true;
            GuiScreenOnlineServers.field_146708_s.submit(new Runnable() {

               private static final String __OBFID = "CL_00000795";

               public void run() {
                  try {
                     GuiScreenOnlineServers.field_146709_r.func_148506_a(var6);
                  } catch (UnknownHostException var2) {
                     GuiScreenOnlineServers.field_146695_f.error("Pinger: Could not resolve host");
                  }

               }
            });
         }

         GuiScreenOnlineServers.this.func_73731_b(GuiScreenOnlineServers.this.field_146289_q, var6.func_148801_b(), p_148390_2_ + 2, p_148390_3_ + 1, 16777215);
         short var7 = 207;
         byte var8 = 1;
         if(var6.field_148819_h) {
            GuiScreenOnlineServers.this.func_146661_c(p_148390_2_ + var7, p_148390_3_ + var8, this.field_148365_e, this.field_148362_f);
         } else if(var6.field_148808_d.equals(McoServer.State.CLOSED.name())) {
            GuiScreenOnlineServers.this.func_146671_e(p_148390_2_ + var7, p_148390_3_ + var8, this.field_148365_e, this.field_148362_f);
         } else if(var6.field_148809_e.equals(GuiScreenOnlineServers.this.field_146297_k.func_110432_I().func_111285_a()) && var6.field_148818_k < 7) {
            this.func_148389_a(p_148390_1_, p_148390_2_ - 14, p_148390_3_, var6);
            GuiScreenOnlineServers.this.func_146687_b(p_148390_2_ + var7, p_148390_3_ + var8, this.field_148365_e, this.field_148362_f, var6.field_148818_k);
         } else if(var6.field_148808_d.equals(McoServer.State.OPEN.name())) {
            GuiScreenOnlineServers.this.func_146683_d(p_148390_2_ + var7, p_148390_3_ + var8, this.field_148365_e, this.field_148362_f);
            this.func_148389_a(p_148390_1_, p_148390_2_ - 14, p_148390_3_, var6);
         } else if(var6.field_148808_d.equals(McoServer.State.ADMIN_LOCK.name())) {
            GuiScreenOnlineServers.this.func_146666_f(p_148390_2_ + var7, p_148390_3_ + var8, this.field_148365_e, this.field_148362_f);
         }

         GuiScreenOnlineServers.this.func_73731_b(GuiScreenOnlineServers.this.field_146289_q, var6.field_148813_n, p_148390_2_ + 200 - GuiScreenOnlineServers.this.field_146289_q.func_78256_a(var6.field_148813_n), p_148390_3_ + 1, 8421504);
         GuiScreenOnlineServers.this.func_73731_b(GuiScreenOnlineServers.this.field_146289_q, var6.func_148800_a(), p_148390_2_ + 2, p_148390_3_ + 12, 7105644);
         GuiScreenOnlineServers.this.func_73731_b(GuiScreenOnlineServers.this.field_146289_q, var6.field_148809_e, p_148390_2_ + 2, p_148390_3_ + 12 + 11, 5000268);
      }

      private void func_148389_a(int p_148389_1_, int p_148389_2_, int p_148389_3_, McoServer p_148389_4_) {
         if(p_148389_4_.field_148807_g != null) {
            if(p_148389_4_.field_148816_m != null) {
               GuiScreenOnlineServers.this.func_73731_b(GuiScreenOnlineServers.this.field_146289_q, p_148389_4_.field_148816_m, p_148389_2_ + 215 - GuiScreenOnlineServers.this.field_146289_q.func_78256_a(p_148389_4_.field_148816_m), p_148389_3_ + 1, 8421504);
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GuiScreenOnlineServers.this.field_146297_k.func_110434_K().func_110577_a(Gui.field_110324_m);
         }
      }
   }
}
