package net.minecraft.client.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.net.URI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenLongRunningTask;
import net.minecraft.client.gui.TaskOnlineConnect;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenReamlsTOS extends GuiScreen {

   private static final Logger field_146773_a = LogManager.getLogger();
   private final GuiScreen field_146770_f;
   private final McoServer field_146771_g;
   private GuiButton field_146774_h;
   private boolean field_146775_i = false;
   private String field_146772_r = "https://minecraft.net/realms/terms";
   private static final String __OBFID = "CL_00000809";


   public GuiScreenReamlsTOS(GuiScreen p_i45045_1_, McoServer p_i45045_2_) {
      this.field_146770_f = p_i45045_1_;
      this.field_146771_g = p_i45045_2_;
   }

   public void func_73876_c() {}

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_146292_n.clear();
      int var1 = this.field_146294_l / 4;
      int var2 = this.field_146294_l / 4 - 2;
      int var3 = this.field_146294_l / 2 + 4;
      this.field_146292_n.add(this.field_146774_h = new GuiButton(1, var1, this.field_146295_m / 5 + 96 + 22, var2, 20, I18n.func_135052_a("mco.terms.buttons.agree", new Object[0])));
      this.field_146292_n.add(new GuiButton(2, var3, this.field_146295_m / 5 + 96 + 22, var2, 20, I18n.func_135052_a("mco.terms.buttons.disagree", new Object[0])));
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void func_146284_a(GuiButton p_146284_1_) {
      if(p_146284_1_.field_146124_l) {
         if(p_146284_1_.field_146127_k == 2) {
            this.field_146297_k.func_147108_a(this.field_146770_f);
         } else if(p_146284_1_.field_146127_k == 1) {
            this.func_146768_g();
         }

      }
   }

   private void func_146768_g() {
      Session var1 = this.field_146297_k.func_110432_I();
      McoClient var2 = new McoClient(var1.func_111286_b(), var1.func_111285_a(), "1.7.2", Minecraft.func_71410_x().func_110437_J());

      try {
         var2.func_148714_h();
         GuiScreenLongRunningTask var3 = new GuiScreenLongRunningTask(this.field_146297_k, this, new TaskOnlineConnect(this, this.field_146771_g));
         var3.func_146902_g();
         this.field_146297_k.func_147108_a(var3);
      } catch (ExceptionMcoService var4) {
         field_146773_a.error("Couldn\'t agree to TOS");
      }

   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      if(this.field_146775_i) {
         Clipboard var4 = Toolkit.getDefaultToolkit().getSystemClipboard();
         var4.setContents(new StringSelection(this.field_146772_r), (ClipboardOwner)null);
         this.func_146769_a(this.field_146772_r);
      }

   }

   private void func_146769_a(String p_146769_1_) {
      try {
         URI var2 = new URI(p_146769_1_);
         Class var3 = Class.forName("java.awt.Desktop");
         Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
         var3.getMethod("browse", new Class[]{URI.class}).invoke(var4, new Object[]{var2});
      } catch (Throwable var5) {
         field_146773_a.error("Couldn\'t open link");
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("mco.terms.title", new Object[0]), this.field_146294_l / 2, 17, 16777215);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("mco.terms.sentence.1", new Object[0]), this.field_146294_l / 2 - 120, 87, 16777215);
      int var4 = this.field_146289_q.func_78256_a(I18n.func_135052_a("mco.terms.sentence.1", new Object[0]));
      int var5 = 3368635;
      int var6 = 7107012;
      int var7 = this.field_146294_l / 2 - 121 + var4;
      byte var8 = 86;
      int var9 = var7 + this.field_146289_q.func_78256_a("mco.terms.sentence.2") + 1;
      int var10 = 87 + this.field_146289_q.field_78288_b;
      if(var7 <= p_73863_1_ && p_73863_1_ <= var9 && var8 <= p_73863_2_ && p_73863_2_ <= var10) {
         this.field_146775_i = true;
         this.func_73731_b(this.field_146289_q, " " + I18n.func_135052_a("mco.terms.sentence.2", new Object[0]), this.field_146294_l / 2 - 120 + var4, 87, var6);
      } else {
         this.field_146775_i = false;
         this.func_73731_b(this.field_146289_q, " " + I18n.func_135052_a("mco.terms.sentence.2", new Object[0]), this.field_146294_l / 2 - 120 + var4, 87, var5);
      }

      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

}
