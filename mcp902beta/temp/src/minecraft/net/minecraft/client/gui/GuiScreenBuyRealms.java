package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenBuyRealms extends GuiScreen {

   private static final Logger field_146819_a = LogManager.getLogger();
   private GuiScreen field_146817_f;
   private static int field_146818_g = 111;
   private volatile String field_146820_h = "";
   private static final String __OBFID = "CL_00000770";


   public GuiScreenBuyRealms(GuiScreen p_i45035_1_) {
      this.field_146817_f = p_i45035_1_;
   }

   public void func_73876_c() {}

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_146292_n.clear();
      short var1 = 212;
      this.field_146292_n.add(new GuiButton(field_146818_g, this.field_146294_l / 2 - var1 / 2, 180, var1, 20, I18n.func_135052_a("gui.back", new Object[0])));
      this.func_146816_h();
   }

   private void func_146816_h() {
      Session var1 = this.field_146297_k.func_110432_I();
      final McoClient var2 = new McoClient(var1.func_111286_b(), var1.func_111285_a(), "1.7.2", Minecraft.func_71410_x().func_110437_J());
      (new Thread() {

         private static final String __OBFID = "CL_00000771";

         public void run() {
            try {
               GuiScreenBuyRealms.this.field_146820_h = var2.func_148690_i();
            } catch (ExceptionMcoService var2x) {
               GuiScreenBuyRealms.field_146819_a.error("Could not get stat");
            }

         }
      }).start();
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void func_146284_a(GuiButton p_146284_1_) {
      if(p_146284_1_.field_146124_l) {
         if(p_146284_1_.field_146127_k == field_146818_g) {
            this.field_146297_k.func_147108_a(this.field_146817_f);
         }

      }
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {}

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("mco.buy.realms.title", new Object[0]), this.field_146294_l / 2, 11, 16777215);
      String[] var4 = this.field_146820_h.split("\n");
      int var5 = 52;
      String[] var6 = var4;
      int var7 = var4.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String var9 = var6[var8];
         this.func_73732_a(this.field_146289_q, var9, this.field_146294_l / 2, var5, 10526880);
         var5 += 18;
      }

      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

}
