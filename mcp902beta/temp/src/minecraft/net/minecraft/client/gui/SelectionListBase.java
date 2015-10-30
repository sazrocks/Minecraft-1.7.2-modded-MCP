package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class SelectionListBase {

   private final Minecraft field_148456_a;
   private final int field_148453_e;
   private final int field_148450_f;
   private final int field_148451_g;
   private final int field_148461_h;
   protected final int field_148454_b;
   protected int field_148455_c;
   protected int field_148452_d;
   private float field_148462_i = -2.0F;
   private float field_148459_j;
   private float field_148460_k;
   private int field_148457_l = -1;
   private long field_148458_m;
   private static final String __OBFID = "CL_00000789";


   public SelectionListBase(Minecraft p_i1117_1_, int p_i1117_2_, int p_i1117_3_, int p_i1117_4_, int p_i1117_5_, int p_i1117_6_) {
      this.field_148456_a = p_i1117_1_;
      this.field_148450_f = p_i1117_3_;
      this.field_148461_h = p_i1117_3_ + p_i1117_5_;
      this.field_148454_b = p_i1117_6_;
      this.field_148453_e = p_i1117_2_;
      this.field_148451_g = p_i1117_2_ + p_i1117_4_;
   }

   protected abstract int func_148443_a();

   protected abstract void func_148449_a(int var1, boolean var2);

   protected abstract boolean func_148444_a(int var1);

   protected int func_148447_b() {
      return this.func_148443_a() * this.field_148454_b;
   }

   protected abstract void func_148445_c();

   protected abstract void func_148442_a(int var1, int var2, int var3, int var4, Tessellator var5);

   private void func_148448_f() {
      int var1 = this.func_148441_d();
      if(var1 < 0) {
         var1 = 0;
      }

      if(this.field_148460_k < 0.0F) {
         this.field_148460_k = 0.0F;
      }

      if(this.field_148460_k > (float)var1) {
         this.field_148460_k = (float)var1;
      }

   }

   public int func_148441_d() {
      return this.func_148447_b() - (this.field_148461_h - this.field_148450_f - 4);
   }

   public void func_148446_a(int p_148446_1_, int p_148446_2_, float p_148446_3_) {
      this.field_148455_c = p_148446_1_;
      this.field_148452_d = p_148446_2_;
      this.func_148445_c();
      int var4 = this.func_148443_a();
      int var5 = this.func_148440_e();
      int var6 = var5 + 6;
      int var9;
      int var10;
      int var11;
      int var13;
      int var20;
      if(Mouse.isButtonDown(0)) {
         if(this.field_148462_i == -1.0F) {
            boolean var7 = true;
            if(p_148446_2_ >= this.field_148450_f && p_148446_2_ <= this.field_148461_h) {
               int var8 = this.field_148453_e + 2;
               var9 = this.field_148451_g - 2;
               var10 = p_148446_2_ - this.field_148450_f + (int)this.field_148460_k - 4;
               var11 = var10 / this.field_148454_b;
               if(p_148446_1_ >= var8 && p_148446_1_ <= var9 && var11 >= 0 && var10 >= 0 && var11 < var4) {
                  boolean var12 = var11 == this.field_148457_l && Minecraft.func_71386_F() - this.field_148458_m < 250L;
                  this.func_148449_a(var11, var12);
                  this.field_148457_l = var11;
                  this.field_148458_m = Minecraft.func_71386_F();
               } else if(p_148446_1_ >= var8 && p_148446_1_ <= var9 && var10 < 0) {
                  var7 = false;
               }

               if(p_148446_1_ >= var5 && p_148446_1_ <= var6) {
                  this.field_148459_j = -1.0F;
                  var20 = this.func_148441_d();
                  if(var20 < 1) {
                     var20 = 1;
                  }

                  var13 = (int)((float)((this.field_148461_h - this.field_148450_f) * (this.field_148461_h - this.field_148450_f)) / (float)this.func_148447_b());
                  if(var13 < 32) {
                     var13 = 32;
                  }

                  if(var13 > this.field_148461_h - this.field_148450_f - 8) {
                     var13 = this.field_148461_h - this.field_148450_f - 8;
                  }

                  this.field_148459_j /= (float)(this.field_148461_h - this.field_148450_f - var13) / (float)var20;
               } else {
                  this.field_148459_j = 1.0F;
               }

               if(var7) {
                  this.field_148462_i = (float)p_148446_2_;
               } else {
                  this.field_148462_i = -2.0F;
               }
            } else {
               this.field_148462_i = -2.0F;
            }
         } else if(this.field_148462_i >= 0.0F) {
            this.field_148460_k -= ((float)p_148446_2_ - this.field_148462_i) * this.field_148459_j;
            this.field_148462_i = (float)p_148446_2_;
         }
      } else {
         while(!this.field_148456_a.field_71474_y.field_85185_A && Mouse.next()) {
            int var16 = Mouse.getEventDWheel();
            if(var16 != 0) {
               if(var16 > 0) {
                  var16 = -1;
               } else if(var16 < 0) {
                  var16 = 1;
               }

               this.field_148460_k += (float)(var16 * this.field_148454_b / 2);
            }
         }

         this.field_148462_i = -1.0F;
      }

      this.func_148448_f();
      GL11.glDisable(2896);
      GL11.glDisable(2912);
      Tessellator var18 = Tessellator.field_78398_a;
      this.field_148456_a.func_110434_K().func_110577_a(Gui.field_110325_k);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      float var17 = 32.0F;
      var18.func_78382_b();
      var18.func_78378_d(2105376);
      var18.func_78374_a((double)this.field_148453_e, (double)this.field_148461_h, 0.0D, (double)((float)this.field_148453_e / var17), (double)((float)(this.field_148461_h + (int)this.field_148460_k) / var17));
      var18.func_78374_a((double)this.field_148451_g, (double)this.field_148461_h, 0.0D, (double)((float)this.field_148451_g / var17), (double)((float)(this.field_148461_h + (int)this.field_148460_k) / var17));
      var18.func_78374_a((double)this.field_148451_g, (double)this.field_148450_f, 0.0D, (double)((float)this.field_148451_g / var17), (double)((float)(this.field_148450_f + (int)this.field_148460_k) / var17));
      var18.func_78374_a((double)this.field_148453_e, (double)this.field_148450_f, 0.0D, (double)((float)this.field_148453_e / var17), (double)((float)(this.field_148450_f + (int)this.field_148460_k) / var17));
      var18.func_78381_a();
      var9 = this.field_148453_e + 2;
      var10 = this.field_148450_f + 4 - (int)this.field_148460_k;

      int var14;
      for(var11 = 0; var11 < var4; ++var11) {
         var20 = var10 + var11 * this.field_148454_b;
         var13 = this.field_148454_b - 4;
         if(var20 + this.field_148454_b <= this.field_148461_h && var20 - 4 >= this.field_148450_f) {
            if(this.func_148444_a(var11)) {
               var14 = this.field_148453_e + 2;
               int var15 = this.field_148451_g - 2;
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glDisable(3553);
               var18.func_78382_b();
               var18.func_78378_d(8421504);
               var18.func_78374_a((double)var14, (double)(var20 + var13 + 2), 0.0D, 0.0D, 1.0D);
               var18.func_78374_a((double)var15, (double)(var20 + var13 + 2), 0.0D, 1.0D, 1.0D);
               var18.func_78374_a((double)var15, (double)(var20 - 2), 0.0D, 1.0D, 0.0D);
               var18.func_78374_a((double)var14, (double)(var20 - 2), 0.0D, 0.0D, 0.0D);
               var18.func_78378_d(0);
               var18.func_78374_a((double)(var14 + 1), (double)(var20 + var13 + 1), 0.0D, 0.0D, 1.0D);
               var18.func_78374_a((double)(var15 - 1), (double)(var20 + var13 + 1), 0.0D, 1.0D, 1.0D);
               var18.func_78374_a((double)(var15 - 1), (double)(var20 - 1), 0.0D, 1.0D, 0.0D);
               var18.func_78374_a((double)(var14 + 1), (double)(var20 - 1), 0.0D, 0.0D, 0.0D);
               var18.func_78381_a();
               GL11.glEnable(3553);
            }

            this.func_148442_a(var11, var9, var20, var13, var18);
         }
      }

      GL11.glDisable(2929);
      byte var19 = 4;
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3008);
      GL11.glShadeModel(7425);
      GL11.glDisable(3553);
      var18.func_78382_b();
      var18.func_78384_a(0, 0);
      var18.func_78374_a((double)this.field_148453_e, (double)(this.field_148450_f + var19), 0.0D, 0.0D, 1.0D);
      var18.func_78374_a((double)this.field_148451_g, (double)(this.field_148450_f + var19), 0.0D, 1.0D, 1.0D);
      var18.func_78384_a(0, 255);
      var18.func_78374_a((double)this.field_148451_g, (double)this.field_148450_f, 0.0D, 1.0D, 0.0D);
      var18.func_78374_a((double)this.field_148453_e, (double)this.field_148450_f, 0.0D, 0.0D, 0.0D);
      var18.func_78381_a();
      var18.func_78382_b();
      var18.func_78384_a(0, 255);
      var18.func_78374_a((double)this.field_148453_e, (double)this.field_148461_h, 0.0D, 0.0D, 1.0D);
      var18.func_78374_a((double)this.field_148451_g, (double)this.field_148461_h, 0.0D, 1.0D, 1.0D);
      var18.func_78384_a(0, 0);
      var18.func_78374_a((double)this.field_148451_g, (double)(this.field_148461_h - var19), 0.0D, 1.0D, 0.0D);
      var18.func_78374_a((double)this.field_148453_e, (double)(this.field_148461_h - var19), 0.0D, 0.0D, 0.0D);
      var18.func_78381_a();
      var20 = this.func_148441_d();
      if(var20 > 0) {
         var13 = (this.field_148461_h - this.field_148450_f) * (this.field_148461_h - this.field_148450_f) / this.func_148447_b();
         if(var13 < 32) {
            var13 = 32;
         }

         if(var13 > this.field_148461_h - this.field_148450_f - 8) {
            var13 = this.field_148461_h - this.field_148450_f - 8;
         }

         var14 = (int)this.field_148460_k * (this.field_148461_h - this.field_148450_f - var13) / var20 + this.field_148450_f;
         if(var14 < this.field_148450_f) {
            var14 = this.field_148450_f;
         }

         var18.func_78382_b();
         var18.func_78384_a(0, 255);
         var18.func_78374_a((double)var5, (double)this.field_148461_h, 0.0D, 0.0D, 1.0D);
         var18.func_78374_a((double)var6, (double)this.field_148461_h, 0.0D, 1.0D, 1.0D);
         var18.func_78374_a((double)var6, (double)this.field_148450_f, 0.0D, 1.0D, 0.0D);
         var18.func_78374_a((double)var5, (double)this.field_148450_f, 0.0D, 0.0D, 0.0D);
         var18.func_78381_a();
         var18.func_78382_b();
         var18.func_78384_a(8421504, 255);
         var18.func_78374_a((double)var5, (double)(var14 + var13), 0.0D, 0.0D, 1.0D);
         var18.func_78374_a((double)var6, (double)(var14 + var13), 0.0D, 1.0D, 1.0D);
         var18.func_78374_a((double)var6, (double)var14, 0.0D, 1.0D, 0.0D);
         var18.func_78374_a((double)var5, (double)var14, 0.0D, 0.0D, 0.0D);
         var18.func_78381_a();
         var18.func_78382_b();
         var18.func_78384_a(12632256, 255);
         var18.func_78374_a((double)var5, (double)(var14 + var13 - 1), 0.0D, 0.0D, 1.0D);
         var18.func_78374_a((double)(var6 - 1), (double)(var14 + var13 - 1), 0.0D, 1.0D, 1.0D);
         var18.func_78374_a((double)(var6 - 1), (double)var14, 0.0D, 1.0D, 0.0D);
         var18.func_78374_a((double)var5, (double)var14, 0.0D, 0.0D, 0.0D);
         var18.func_78381_a();
      }

      GL11.glEnable(3553);
      GL11.glShadeModel(7424);
      GL11.glEnable(3008);
      GL11.glDisable(3042);
   }

   protected int func_148440_e() {
      return this.field_148451_g - 8;
   }
}
