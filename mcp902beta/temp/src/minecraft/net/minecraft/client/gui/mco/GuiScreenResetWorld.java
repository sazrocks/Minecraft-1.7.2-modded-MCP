package net.minecraft.client.gui.mco;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenConfirmation;
import net.minecraft.client.gui.GuiScreenLongRunningTask;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.TaskLongRunning;
import net.minecraft.client.gui.mco.GuiScreenMcoWorldTemplate;
import net.minecraft.client.gui.mco.ScreenWithCallback;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.mco.WorldTemplate;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenResetWorld extends ScreenWithCallback {

   private static final Logger field_146748_a = LogManager.getLogger();
   private GuiScreen field_146742_f;
   private McoServer field_146743_g;
   private GuiTextField field_146749_h;
   private final int field_146750_i = 1;
   private final int field_146747_r = 2;
   private static int field_146746_s = 3;
   private WorldTemplate field_146745_t;
   private GuiButton field_146744_u;
   private static final String __OBFID = "CL_00000810";


   public GuiScreenResetWorld(GuiScreen p_i1135_1_, McoServer p_i1135_2_) {
      this.field_146742_f = p_i1135_1_;
      this.field_146743_g = p_i1135_2_;
   }

   public void func_73876_c() {
      this.field_146749_h.func_146178_a();
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_146292_n.clear();
      this.field_146292_n.add(this.field_146744_u = new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120 + 12, 97, 20, I18n.func_135052_a("mco.configure.world.buttons.reset", new Object[0])));
      this.field_146292_n.add(new GuiButton(2, this.field_146294_l / 2 + 5, this.field_146295_m / 4 + 120 + 12, 97, 20, I18n.func_135052_a("gui.cancel", new Object[0])));
      this.field_146749_h = new GuiTextField(this.field_146289_q, this.field_146294_l / 2 - 100, 99, 200, 20);
      this.field_146749_h.func_146195_b(true);
      this.field_146749_h.func_146203_f(32);
      this.field_146749_h.func_146180_a("");
      if(this.field_146745_t == null) {
         this.field_146292_n.add(new GuiButton(field_146746_s, this.field_146294_l / 2 - 100, 125, 200, 20, I18n.func_135052_a("mco.template.default.name", new Object[0])));
      } else {
         this.field_146749_h.func_146180_a("");
         this.field_146749_h.func_146184_c(false);
         this.field_146749_h.func_146195_b(false);
         this.field_146292_n.add(new GuiButton(field_146746_s, this.field_146294_l / 2 - 100, 125, 200, 20, I18n.func_135052_a("mco.template.name", new Object[0]) + ": " + this.field_146745_t.field_148785_b));
      }

   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {
      this.field_146749_h.func_146201_a(p_73869_1_, p_73869_2_);
      if(p_73869_2_ == 28 || p_73869_2_ == 156) {
         this.func_146284_a(this.field_146744_u);
      }

   }

   protected void func_146284_a(GuiButton p_146284_1_) {
      if(p_146284_1_.field_146124_l) {
         if(p_146284_1_.field_146127_k == 2) {
            this.field_146297_k.func_147108_a(this.field_146742_f);
         } else if(p_146284_1_.field_146127_k == 1) {
            String var2 = I18n.func_135052_a("mco.configure.world.reset.question.line1", new Object[0]);
            String var3 = I18n.func_135052_a("mco.configure.world.reset.question.line2", new Object[0]);
            this.field_146297_k.func_147108_a(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Warning, var2, var3, 1));
         } else if(p_146284_1_.field_146127_k == field_146746_s) {
            this.field_146297_k.func_147108_a(new GuiScreenMcoWorldTemplate(this, this.field_146745_t));
         }

      }
   }

   public void func_73878_a(boolean p_73878_1_, int p_73878_2_) {
      if(p_73878_1_ && p_73878_2_ == 1) {
         this.func_146741_h();
      } else {
         this.field_146297_k.func_147108_a(this);
      }

   }

   private void func_146741_h() {
      GuiScreenResetWorld.ResetWorldTask var1 = new GuiScreenResetWorld.ResetWorldTask(this.field_146743_g.field_148812_a, this.field_146749_h.func_146179_b(), this.field_146745_t);
      GuiScreenLongRunningTask var2 = new GuiScreenLongRunningTask(this.field_146297_k, this.field_146742_f, var1);
      var2.func_146902_g();
      this.field_146297_k.func_147108_a(var2);
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      this.field_146749_h.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("mco.reset.world.title", new Object[0]), this.field_146294_l / 2, 17, 16777215);
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("mco.reset.world.warning", new Object[0]), this.field_146294_l / 2, 56, 16711680);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("mco.reset.world.seed", new Object[0]), this.field_146294_l / 2 - 100, 86, 10526880);
      this.field_146749_h.func_146194_f();
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   void func_146740_a(WorldTemplate p_146740_1_) {
      this.field_146745_t = p_146740_1_;
   }

   // $FF: synthetic method
   // $FF: bridge method
   void func_146735_a(Object p_146735_1_) {
      this.func_146740_a((WorldTemplate)p_146735_1_);
   }


   class ResetWorldTask extends TaskLongRunning {

      private final long field_148422_c;
      private final String field_148420_d;
      private final WorldTemplate field_148421_e;
      private static final String __OBFID = "CL_00000811";


      public ResetWorldTask(long p_i1134_2_, String p_i1134_4_, WorldTemplate p_i1134_5_) {
         this.field_148422_c = p_i1134_2_;
         this.field_148420_d = p_i1134_4_;
         this.field_148421_e = p_i1134_5_;
      }

      public void run() {
         Session var1 = GuiScreenResetWorld.this.field_146297_k.func_110432_I();
         McoClient var2 = new McoClient(var1.func_111286_b(), var1.func_111285_a(), "1.7.2", Minecraft.func_71410_x().func_110437_J());
         String var3 = I18n.func_135052_a("mco.reset.world.resetting.screen.title", new Object[0]);
         this.func_148417_b(var3);

         try {
            if(this.func_148418_c()) {
               return;
            }

            if(this.field_148421_e != null) {
               var2.func_148696_e(this.field_148422_c, this.field_148421_e.field_148787_a);
            } else {
               var2.func_148699_d(this.field_148422_c, this.field_148420_d);
            }

            if(this.func_148418_c()) {
               return;
            }

            GuiScreenResetWorld.this.field_146297_k.func_147108_a(GuiScreenResetWorld.this.field_146742_f);
         } catch (ExceptionMcoService var5) {
            if(this.func_148418_c()) {
               return;
            }

            GuiScreenResetWorld.field_146748_a.error("Couldn\'t reset world");
            this.func_148416_a(var5.toString());
         } catch (Exception var6) {
            if(this.func_148418_c()) {
               return;
            }

            GuiScreenResetWorld.field_146748_a.error("Couldn\'t reset world");
            this.func_148416_a(var6.toString());
         }

      }
   }
}
