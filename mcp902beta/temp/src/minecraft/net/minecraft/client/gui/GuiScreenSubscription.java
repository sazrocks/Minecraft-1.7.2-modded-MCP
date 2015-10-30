package net.minecraft.client.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.mco.ValueObjectSubscription;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenSubscription extends GuiScreen {

   private static final Logger field_146786_a = LogManager.getLogger();
   private final GuiScreen field_146780_f;
   private final McoServer field_146781_g;
   private final int field_146787_h = 0;
   private final int field_146788_i = 1;
   private int field_146785_r;
   private String field_146784_s;
   private final String field_146783_t = "https://account.mojang.com";
   private final String field_146782_u = "/buy/realms";
   private static final String __OBFID = "CL_00000813";


   public GuiScreenSubscription(GuiScreen p_i1136_1_, McoServer p_i1136_2_) {
      this.field_146780_f = p_i1136_1_;
      this.field_146781_g = p_i1136_2_;
   }

   public void func_73876_c() {}

   public void func_73866_w_() {
      this.func_146778_a(this.field_146781_g.field_148812_a);
      Keyboard.enableRepeatEvents(true);
      this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96 + 12, I18n.func_135052_a("mco.configure.world.subscription.extend", new Object[0])));
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120 + 12, I18n.func_135052_a("gui.cancel", new Object[0])));
   }

   private void func_146778_a(long p_146778_1_) {
      Session var3 = this.field_146297_k.func_110432_I();
      McoClient var4 = new McoClient(var3.func_111286_b(), var3.func_111285_a(), "1.7.2", Minecraft.func_71410_x().func_110437_J());

      try {
         ValueObjectSubscription var5 = var4.func_148705_g(p_146778_1_);
         this.field_146785_r = var5.field_148789_b;
         this.field_146784_s = this.func_146776_b(var5.field_148790_a);
      } catch (ExceptionMcoService var6) {
         field_146786_a.error("Couldn\'t get subscription");
      } catch (IOException var7) {
         field_146786_a.error("Couldn\'t parse response subscribing");
      }

   }

   private String func_146776_b(long p_146776_1_) {
      GregorianCalendar var3 = new GregorianCalendar(TimeZone.getDefault());
      var3.setTimeInMillis(p_146776_1_);
      return SimpleDateFormat.getDateTimeInstance().format(var3.getTime());
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void func_146284_a(GuiButton p_146284_1_) {
      if(p_146284_1_.field_146124_l) {
         if(p_146284_1_.field_146127_k == 0) {
            this.field_146297_k.func_147108_a(this.field_146780_f);
         } else if(p_146284_1_.field_146127_k == 1) {
            String var2 = "https://account.mojang.com/buy/realms?wid=" + this.field_146781_g.field_148812_a + "?pid=" + this.func_146777_g();
            Clipboard var3 = Toolkit.getDefaultToolkit().getSystemClipboard();
            var3.setContents(new StringSelection(var2), (ClipboardOwner)null);
            this.func_146779_a(var2);
         }

      }
   }

   private String func_146777_g() {
      String var1 = this.field_146297_k.func_110432_I().func_111286_b();
      String[] var2 = var1.split(":");
      return var2.length == 3?var2[2]:"";
   }

   private void func_146779_a(String p_146779_1_) {
      try {
         URI var2 = new URI(p_146779_1_);
         Class var3 = Class.forName("java.awt.Desktop");
         Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
         var3.getMethod("browse", new Class[]{URI.class}).invoke(var4, new Object[]{var2});
      } catch (Throwable var5) {
         field_146786_a.error("Couldn\'t open link");
      }

   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {}

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("mco.configure.world.subscription.title", new Object[0]), this.field_146294_l / 2, 17, 16777215);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("mco.configure.world.subscription.start", new Object[0]), this.field_146294_l / 2 - 100, 53, 10526880);
      this.func_73731_b(this.field_146289_q, this.field_146784_s, this.field_146294_l / 2 - 100, 66, 16777215);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("mco.configure.world.subscription.daysleft", new Object[0]), this.field_146294_l / 2 - 100, 85, 10526880);
      this.func_73731_b(this.field_146289_q, String.valueOf(this.field_146785_r), this.field_146294_l / 2 - 100, 98, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

}
