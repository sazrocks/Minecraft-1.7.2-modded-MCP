package net.minecraft.client.gui;

import java.io.UnsupportedEncodingException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenConfigureWorld;
import net.minecraft.client.gui.GuiScreenOnlineServersSubscreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.mco.GuiScreenResetWorld;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenEditOnlineWorld extends GuiScreen {

   private static final Logger field_146862_a = LogManager.getLogger();
   private GuiScreen field_146855_f;
   private GuiScreen field_146857_g;
   private GuiTextField field_146863_h;
   private GuiTextField field_146864_i;
   private McoServer field_146861_r;
   private GuiButton field_146860_s;
   private int field_146859_t;
   private int field_146858_u;
   private int field_146856_v;
   private GuiScreenOnlineServersSubscreen field_146854_w;
   private static final String __OBFID = "CL_00000779";


   public GuiScreenEditOnlineWorld(GuiScreen p_i1109_1_, GuiScreen p_i1109_2_, McoServer p_i1109_3_) {
      this.field_146855_f = p_i1109_1_;
      this.field_146857_g = p_i1109_2_;
      this.field_146861_r = p_i1109_3_;
   }

   public void func_73876_c() {
      this.field_146864_i.func_146178_a();
      this.field_146863_h.func_146178_a();
   }

   public void func_73866_w_() {
      this.field_146859_t = this.field_146294_l / 4;
      this.field_146858_u = this.field_146294_l / 4 - 2;
      this.field_146856_v = this.field_146294_l / 2 + 4;
      Keyboard.enableRepeatEvents(true);
      this.field_146292_n.clear();
      this.field_146292_n.add(this.field_146860_s = new GuiButton(0, this.field_146859_t, this.field_146295_m / 4 + 120 + 22, this.field_146858_u, 20, I18n.func_135052_a("mco.configure.world.buttons.done", new Object[0])));
      this.field_146292_n.add(new GuiButton(1, this.field_146856_v, this.field_146295_m / 4 + 120 + 22, this.field_146858_u, 20, I18n.func_135052_a("gui.cancel", new Object[0])));
      this.field_146864_i = new GuiTextField(this.field_146289_q, this.field_146859_t, 56, 212, 20);
      this.field_146864_i.func_146195_b(true);
      this.field_146864_i.func_146203_f(32);
      this.field_146864_i.func_146180_a(this.field_146861_r.func_148801_b());
      this.field_146863_h = new GuiTextField(this.field_146289_q, this.field_146859_t, 96, 212, 20);
      this.field_146863_h.func_146203_f(32);
      this.field_146863_h.func_146180_a(this.field_146861_r.func_148800_a());
      this.field_146854_w = new GuiScreenOnlineServersSubscreen(this.field_146294_l, this.field_146295_m, this.field_146859_t, 122, this.field_146861_r.field_148820_i, this.field_146861_r.field_148817_j);
      this.field_146292_n.addAll(this.field_146854_w.field_148405_a);
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void func_146284_a(GuiButton p_146284_1_) {
      if(p_146284_1_.field_146124_l) {
         if(p_146284_1_.field_146127_k == 1) {
            this.field_146297_k.func_147108_a(this.field_146855_f);
         } else if(p_146284_1_.field_146127_k == 0) {
            this.func_146853_g();
         } else if(p_146284_1_.field_146127_k == 2) {
            this.field_146297_k.func_147108_a(new GuiScreenResetWorld(this, this.field_146861_r));
         } else {
            this.field_146854_w.func_148397_a(p_146284_1_);
         }

      }
   }

   private void func_146853_g() {
      Session var1 = this.field_146297_k.func_110432_I();
      McoClient var2 = new McoClient(var1.func_111286_b(), var1.func_111285_a(), "1.7.2", Minecraft.func_71410_x().func_110437_J());

      try {
         String var3 = this.field_146863_h.func_146179_b() != null && !this.field_146863_h.func_146179_b().trim().equals("")?this.field_146863_h.func_146179_b():null;
         var2.func_148689_a(this.field_146861_r.field_148812_a, this.field_146864_i.func_146179_b(), var3, this.field_146854_w.field_148402_e, this.field_146854_w.field_148399_f);
         this.field_146861_r.func_148803_a(this.field_146864_i.func_146179_b());
         this.field_146861_r.func_148804_b(this.field_146863_h.func_146179_b());
         this.field_146861_r.field_148820_i = this.field_146854_w.field_148402_e;
         this.field_146861_r.field_148817_j = this.field_146854_w.field_148399_f;
         this.field_146297_k.func_147108_a(new GuiScreenConfigureWorld(this.field_146857_g, this.field_146861_r));
      } catch (ExceptionMcoService var4) {
         field_146862_a.error("Couldn\'t edit world");
      } catch (UnsupportedEncodingException var5) {
         field_146862_a.error("Couldn\'t edit world");
      }

   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {
      this.field_146864_i.func_146201_a(p_73869_1_, p_73869_2_);
      this.field_146863_h.func_146201_a(p_73869_1_, p_73869_2_);
      if(p_73869_2_ == 15) {
         this.field_146864_i.func_146195_b(!this.field_146864_i.func_146206_l());
         this.field_146863_h.func_146195_b(!this.field_146863_h.func_146206_l());
      }

      if(p_73869_2_ == 28 || p_73869_2_ == 156) {
         this.func_146853_g();
      }

      this.field_146860_s.field_146124_l = this.field_146864_i.func_146179_b() != null && !this.field_146864_i.func_146179_b().trim().equals("");
   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      this.field_146863_h.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
      this.field_146864_i.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("mco.configure.world.edit.title", new Object[0]), this.field_146294_l / 2, 17, 16777215);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("mco.configure.world.name", new Object[0]), this.field_146859_t, 43, 10526880);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("mco.configure.world.description", new Object[0]), this.field_146859_t, 84, 10526880);
      this.field_146864_i.func_146194_f();
      this.field_146863_h.func_146194_f();
      this.field_146854_w.func_148394_a(this, this.field_146289_q);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

}
