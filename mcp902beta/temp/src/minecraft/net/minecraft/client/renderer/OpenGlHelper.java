package net.minecraft.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;

public class OpenGlHelper {

   public static boolean field_148827_a;
   public static int field_77478_a;
   public static int field_77476_b;
   public static boolean field_148825_d;
   public static int field_148826_e;
   private static boolean field_77477_c;
   private static boolean field_148828_i;
   public static boolean field_148823_f;
   public static boolean field_148824_g;
   private static final String __OBFID = "CL_00001179";


   public static void func_77474_a() {
      field_77477_c = GLContext.getCapabilities().GL_ARB_multitexture && !GLContext.getCapabilities().OpenGL13;
      if(field_77477_c) {
         field_77478_a = '\u84c0';
         field_77476_b = '\u84c1';
      } else {
         field_77478_a = '\u84c0';
         field_77476_b = '\u84c1';
      }

      field_148828_i = GLContext.getCapabilities().OpenGL14;
      field_148823_f = field_148828_i && GLContext.getCapabilities().GL_ARB_framebuffer_object;
      field_148825_d = GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic;
      field_148826_e = (int)(field_148825_d?GL11.glGetFloat('\u84ff'):0.0F);
      GameSettings.Options.ANISOTROPIC_FILTERING.func_148263_a((float)field_148826_e);
      field_148827_a = GLContext.getCapabilities().OpenGL21;
      field_148824_g = field_148823_f && field_148827_a;
   }

   public static void func_77473_a(int p_77473_0_) {
      if(field_77477_c) {
         ARBMultitexture.glActiveTextureARB(p_77473_0_);
      } else {
         GL13.glActiveTexture(p_77473_0_);
      }

   }

   public static void func_77472_b(int p_77472_0_) {
      if(field_77477_c) {
         ARBMultitexture.glClientActiveTextureARB(p_77472_0_);
      } else {
         GL13.glClientActiveTexture(p_77472_0_);
      }

   }

   public static void func_77475_a(int p_77475_0_, float p_77475_1_, float p_77475_2_) {
      if(field_77477_c) {
         ARBMultitexture.glMultiTexCoord2fARB(p_77475_0_, p_77475_1_, p_77475_2_);
      } else {
         GL13.glMultiTexCoord2f(p_77475_0_, p_77475_1_, p_77475_2_);
      }

   }

   public static void func_148821_a(int p_148821_0_, int p_148821_1_, int p_148821_2_, int p_148821_3_) {
      if(field_148828_i) {
         GL14.glBlendFuncSeparate(p_148821_0_, p_148821_1_, p_148821_2_, p_148821_3_);
      } else {
         GL11.glBlendFunc(p_148821_0_, p_148821_1_);
      }

   }

   public static boolean func_148822_b() {
      return field_148823_f && Minecraft.func_71410_x().field_71474_y.field_151448_g;
   }
}
