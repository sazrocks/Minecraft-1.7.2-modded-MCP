package net.minecraft.client.gui;

import java.net.URI;
import net.minecraft.client.gui.GuiButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiButtonLink extends GuiButton {

   private static final Logger field_146139_o = LogManager.getLogger();
   private static final String __OBFID = "CL_00000673";


   public GuiButtonLink(int p_i1044_1_, int p_i1044_2_, int p_i1044_3_, int p_i1044_4_, int p_i1044_5_, String p_i1044_6_) {
      super(p_i1044_1_, p_i1044_2_, p_i1044_3_, p_i1044_4_, p_i1044_5_, p_i1044_6_);
   }

   public void func_146138_a(String p_146138_1_) {
      try {
         URI var2 = new URI(p_146138_1_);
         Class var3 = Class.forName("java.awt.Desktop");
         Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
         var3.getMethod("browse", new Class[]{URI.class}).invoke(var4, new Object[]{var2});
      } catch (Throwable var5) {
         field_146139_o.error("Couldn\'t open link", var5);
      }

   }

}
