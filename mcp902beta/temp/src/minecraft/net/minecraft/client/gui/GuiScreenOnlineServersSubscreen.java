package net.minecraft.client.gui;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiScreenOnlineServersSubscreen {

   private final int field_148400_g;
   private final int field_148407_h;
   private final int field_148408_i;
   private final int field_148406_j;
   List field_148405_a = new ArrayList();
   String[] field_148403_b;
   String[] field_148404_c;
   String[][] field_148401_d;
   int field_148402_e;
   int field_148399_f;
   private static final String __OBFID = "CL_00000796";


   public GuiScreenOnlineServersSubscreen(int p_i1124_1_, int p_i1124_2_, int p_i1124_3_, int p_i1124_4_, int p_i1124_5_, int p_i1124_6_) {
      this.field_148400_g = p_i1124_1_;
      this.field_148407_h = p_i1124_2_;
      this.field_148408_i = p_i1124_3_;
      this.field_148406_j = p_i1124_4_;
      this.field_148402_e = p_i1124_5_;
      this.field_148399_f = p_i1124_6_;
      this.func_148395_a();
   }

   private void func_148395_a() {
      this.func_148396_b();
      this.field_148405_a.add(new GuiButton(5005, this.field_148408_i, this.field_148406_j + 1, 212, 20, this.func_148398_c()));
      this.field_148405_a.add(new GuiButton(5006, this.field_148408_i, this.field_148406_j + 25, 212, 20, this.func_148393_d()));
   }

   private void func_148396_b() {
      this.field_148403_b = new String[]{I18n.func_135052_a("options.difficulty.peaceful", new Object[0]), I18n.func_135052_a("options.difficulty.easy", new Object[0]), I18n.func_135052_a("options.difficulty.normal", new Object[0]), I18n.func_135052_a("options.difficulty.hard", new Object[0])};
      this.field_148404_c = new String[]{I18n.func_135052_a("selectWorld.gameMode.survival", new Object[0]), I18n.func_135052_a("selectWorld.gameMode.creative", new Object[0]), I18n.func_135052_a("selectWorld.gameMode.adventure", new Object[0])};
      this.field_148401_d = new String[][]{{I18n.func_135052_a("selectWorld.gameMode.survival.line1", new Object[0]), I18n.func_135052_a("selectWorld.gameMode.survival.line2", new Object[0])}, {I18n.func_135052_a("selectWorld.gameMode.creative.line1", new Object[0]), I18n.func_135052_a("selectWorld.gameMode.creative.line2", new Object[0])}, {I18n.func_135052_a("selectWorld.gameMode.adventure.line1", new Object[0]), I18n.func_135052_a("selectWorld.gameMode.adventure.line2", new Object[0])}};
   }

   private String func_148398_c() {
      String var1 = I18n.func_135052_a("options.difficulty", new Object[0]);
      return var1 + ": " + this.field_148403_b[this.field_148402_e];
   }

   private String func_148393_d() {
      String var1 = I18n.func_135052_a("selectWorld.gameMode", new Object[0]);
      return var1 + ": " + this.field_148404_c[this.field_148399_f];
   }

   void func_148397_a(GuiButton p_148397_1_) {
      if(p_148397_1_.field_146124_l) {
         if(p_148397_1_.field_146127_k == 5005) {
            this.field_148402_e = (this.field_148402_e + 1) % this.field_148403_b.length;
            p_148397_1_.field_146126_j = this.func_148398_c();
         } else if(p_148397_1_.field_146127_k == 5006) {
            this.field_148399_f = (this.field_148399_f + 1) % this.field_148404_c.length;
            p_148397_1_.field_146126_j = this.func_148393_d();
         }

      }
   }

   public void func_148394_a(GuiScreen p_148394_1_, FontRenderer p_148394_2_) {
      p_148394_1_.func_73731_b(p_148394_2_, this.field_148401_d[this.field_148399_f][0], this.field_148408_i, this.field_148406_j + 50, 10526880);
      p_148394_1_.func_73731_b(p_148394_2_, this.field_148401_d[this.field_148399_f][1], this.field_148408_i, this.field_148406_j + 60, 10526880);
   }
}
