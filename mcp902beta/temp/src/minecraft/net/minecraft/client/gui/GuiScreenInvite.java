package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenConfigureWorld;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiScreenInvite extends GuiScreen {

   private static final Logger field_146928_a = LogManager.getLogger();
   private GuiTextField field_146921_f;
   private McoServer field_146923_g;
   private final GuiScreen field_146929_h;
   private final GuiScreenConfigureWorld field_146930_i;
   private final int field_146927_r = 0;
   private final int field_146926_s = 1;
   private String field_146925_t = "Could not invite the provided name";
   private String field_146924_u;
   private boolean field_146922_v;
   private static final String __OBFID = "CL_00000780";


   public GuiScreenInvite(GuiScreen p_i1110_1_, GuiScreenConfigureWorld p_i1110_2_, McoServer p_i1110_3_) {
      this.field_146929_h = p_i1110_1_;
      this.field_146930_i = p_i1110_2_;
      this.field_146923_g = p_i1110_3_;
   }

   public void func_73876_c() {
      this.field_146921_f.func_146178_a();
   }

   public void func_73866_w_() {
      Keyboard.enableRepeatEvents(true);
      this.field_146292_n.clear();
      this.field_146292_n.add(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 96 + 12, I18n.func_135052_a("mco.configure.world.buttons.invite", new Object[0])));
      this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120 + 12, I18n.func_135052_a("gui.cancel", new Object[0])));
      this.field_146921_f = new GuiTextField(this.field_146289_q, this.field_146294_l / 2 - 100, 66, 200, 20);
      this.field_146921_f.func_146195_b(true);
   }

   public void func_146281_b() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void func_146284_a(GuiButton p_146284_1_) {
      if(p_146284_1_.field_146124_l) {
         if(p_146284_1_.field_146127_k == 1) {
            this.field_146297_k.func_147108_a(this.field_146930_i);
         } else if(p_146284_1_.field_146127_k == 0) {
            Session var2 = this.field_146297_k.func_110432_I();
            McoClient var3 = new McoClient(var2.func_111286_b(), var2.func_111285_a(), "1.7.2", Minecraft.func_71410_x().func_110437_J());
            if(this.field_146921_f.func_146179_b() == null || this.field_146921_f.func_146179_b().isEmpty()) {
               return;
            }

            try {
               McoServer var4 = var3.func_148697_b(this.field_146923_g.field_148812_a, this.field_146921_f.func_146179_b());
               if(var4 != null) {
                  this.field_146923_g.field_148806_f = var4.field_148806_f;
                  this.field_146297_k.func_147108_a(new GuiScreenConfigureWorld(this.field_146929_h, this.field_146923_g));
               } else {
                  this.func_146920_a(this.field_146925_t);
               }
            } catch (ExceptionMcoService var5) {
               field_146928_a.error("Couldn\'t invite user");
               this.func_146920_a(var5.field_148829_b);
            } catch (IOException var6) {
               field_146928_a.error("Couldn\'t parse response inviting user", var6);
               this.func_146920_a(this.field_146925_t);
            }
         }

      }
   }

   private void func_146920_a(String p_146920_1_) {
      this.field_146922_v = true;
      this.field_146924_u = p_146920_1_;
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {
      this.field_146921_f.func_146201_a(p_73869_1_, p_73869_2_);
      if(p_73869_2_ == 15) {
         if(this.field_146921_f.func_146206_l()) {
            this.field_146921_f.func_146195_b(false);
         } else {
            this.field_146921_f.func_146195_b(true);
         }
      }

      if(p_73869_2_ == 28 || p_73869_2_ == 156) {
         this.func_146284_a((GuiButton)this.field_146292_n.get(0));
      }

   }

   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
      super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
      this.field_146921_f.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("mco.configure.world.invite.profile.name", new Object[0]), this.field_146294_l / 2 - 100, 53, 10526880);
      if(this.field_146922_v) {
         this.func_73732_a(this.field_146289_q, this.field_146924_u, this.field_146294_l / 2, 100, 16711680);
      }

      this.field_146921_f.func_146194_f();
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

}
