package net.minecraft.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiScreenConfirmation extends GuiScreen {

   private final GuiScreenConfirmation.ConfirmationType field_146937_i;
   private final String field_146934_r;
   private final String field_146933_s;
   protected final GuiScreen field_146935_a;
   protected final String field_146931_f;
   protected final String field_146932_g;
   protected final int field_146936_h;
   private static final String __OBFID = "CL_00000781";


   public GuiScreenConfirmation(GuiScreen p_i2325_1_, GuiScreenConfirmation.ConfirmationType p_i2325_2_, String p_i2325_3_, String p_i2325_4_, int p_i2325_5_) {
      this.field_146935_a = p_i2325_1_;
      this.field_146936_h = p_i2325_5_;
      this.field_146937_i = p_i2325_2_;
      this.field_146934_r = p_i2325_3_;
      this.field_146933_s = p_i2325_4_;
      this.field_146931_f = I18n.func_135052_a("gui.yes", new Object[0]);
      this.field_146932_g = I18n.func_135052_a("gui.no", new Object[0]);
   }

   public void func_73866_w_() {
      this.field_146292_n.add(new GuiOptionButton(0, this.field_146294_l / 2 - 155, this.field_146295_m / 6 + 112, this.field_146931_f));
      this.field_146292_n.add(new GuiOptionButton(1, this.field_146294_l / 2 - 155 + 160, this.field_146295_m / 6 + 112, this.field_146932_g));
   }

   protected void func_146284_a(GuiButton p_146284_1_) {
      this.field_146935_a.func_73878_a(p_146284_1_.field_146127_k == 0, this.field_146936_h);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, this.field_146937_i.field_148515_d, this.field_146294_l / 2, 70, this.field_146937_i.field_148518_c);
      this.func_73732_a(this.field_146289_q, this.field_146934_r, this.field_146294_l / 2, 90, 16777215);
      this.func_73732_a(this.field_146289_q, this.field_146933_s, this.field_146294_l / 2, 110, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   public static enum ConfirmationType {

      Warning("Warning", 0, "Warning!", 16711680),
      Info("Info", 1, "Info!", 8226750);
      public final int field_148518_c;
      public final String field_148515_d;
      // $FF: synthetic field
      private static final GuiScreenConfirmation.ConfirmationType[] $VALUES = new GuiScreenConfirmation.ConfirmationType[]{Warning, Info};
      private static final String __OBFID = "CL_00000782";


      private ConfirmationType(String p_i2324_1_, int p_i2324_2_, String p_i2324_3_, int p_i2324_4_) {
         this.field_148515_d = p_i2324_3_;
         this.field_148518_c = p_i2324_4_;
      }

   }
}
