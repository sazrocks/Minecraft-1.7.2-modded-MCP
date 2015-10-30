package net.minecraft.client.mco;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiScreenClientOutdated extends GuiScreen {

   private final GuiScreen field_146901_a;
   private static final String __OBFID = "CL_00000772";


   public GuiScreenClientOutdated(GuiScreen p_i2323_1_) {
      this.field_146901_a = p_i2323_1_;
   }

   public void func_73866_w_() {
      this.field_146292_n.clear();
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120 + 12, "Back"));
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      String var4 = I18n.func_135052_a("mco.client.outdated.title", new Object[0]);
      String var5 = I18n.func_135052_a("mco.client.outdated.msg", new Object[0]);
      this.func_73732_a(this.field_146289_q, var4, this.field_146294_l / 2, this.field_146295_m / 2 - 50, 16711680);
      this.func_73732_a(this.field_146289_q, var5, this.field_146294_l / 2, this.field_146295_m / 2 - 30, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   protected void func_146284_a(GuiButton p_146284_1_) {
      if(p_146284_1_.field_146127_k == 0) {
         this.field_146297_k.func_147108_a(this.field_146901_a);
      }

   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {
      if(p_73869_2_ == 28 || p_73869_2_ == 156) {
         this.field_146297_k.func_147108_a(this.field_146901_a);
      }

   }
}
