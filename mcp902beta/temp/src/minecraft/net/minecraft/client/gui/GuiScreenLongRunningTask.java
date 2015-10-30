package net.minecraft.client.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.TaskLongRunning;
import net.minecraft.client.resources.I18n;

public class GuiScreenLongRunningTask extends GuiScreen {

   private static final AtomicInteger field_146908_f = new AtomicInteger(0);
   private final int field_146910_g = 666;
   private final int field_146917_h = 667;
   private final GuiScreen field_146919_i;
   private final Thread field_146914_r;
   private volatile String field_146913_s = "";
   private volatile boolean field_146912_t;
   private volatile String field_146911_u;
   private volatile boolean field_146909_v;
   private int field_146907_w;
   private TaskLongRunning field_146918_x;
   private int field_146916_y = 212;
   public static final String[] field_146915_a = new String[]{"\u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583", "_ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584", "_ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585", "_ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586", "_ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587", "_ _ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588", "_ _ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587", "_ _ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586", "_ _ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585", "_ \u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584", "\u2583 \u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583", "\u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _", "\u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _", "\u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _", "\u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _", "\u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _ _", "\u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _ _", "\u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _ _", "\u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _ _", "\u2584 \u2585 \u2586 \u2587 \u2588 \u2587 \u2586 \u2585 \u2584 \u2583 _"};
   private static final String __OBFID = "CL_00000783";


   public GuiScreenLongRunningTask(Minecraft p_i1112_1_, GuiScreen p_i1112_2_, TaskLongRunning p_i1112_3_) {
      super.field_146292_n = Collections.synchronizedList(new ArrayList());
      this.field_146297_k = p_i1112_1_;
      this.field_146919_i = p_i1112_2_;
      this.field_146918_x = p_i1112_3_;
      p_i1112_3_.func_148412_a(this);
      this.field_146914_r = new Thread(p_i1112_3_, "MCO Task #" + field_146908_f.incrementAndGet());
   }

   public void func_146902_g() {
      this.field_146914_r.start();
   }

   public void func_73876_c() {
      super.func_73876_c();
      ++this.field_146907_w;
      this.field_146918_x.func_148414_a();
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {}

   public void func_73866_w_() {
      this.field_146918_x.func_148411_d();
      this.field_146292_n.add(new GuiButton(666, this.field_146294_l / 2 - this.field_146916_y / 2, 170, this.field_146916_y, 20, I18n.func_135052_a("gui.cancel", new Object[0])));
   }

   protected void func_146284_a(GuiButton p_146284_1_) {
      if(p_146284_1_.field_146127_k == 666 || p_146284_1_.field_146127_k == 667) {
         this.field_146909_v = true;
         this.field_146297_k.func_147108_a(this.field_146919_i);
      }

      this.field_146918_x.func_148415_a(p_146284_1_);
   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, this.field_146913_s, this.field_146294_l / 2, this.field_146295_m / 2 - 50, 16777215);
      this.func_73732_a(this.field_146289_q, "", this.field_146294_l / 2, this.field_146295_m / 2 - 10, 16777215);
      if(!this.field_146912_t) {
         this.func_73732_a(this.field_146289_q, field_146915_a[this.field_146907_w % field_146915_a.length], this.field_146294_l / 2, this.field_146295_m / 2 + 15, 8421504);
      }

      if(this.field_146912_t) {
         this.func_73732_a(this.field_146289_q, this.field_146911_u, this.field_146294_l / 2, this.field_146295_m / 2 + 15, 16711680);
      }

      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
   }

   public void func_146905_a(String p_146905_1_) {
      this.field_146912_t = true;
      this.field_146911_u = p_146905_1_;
      this.field_146292_n.clear();
      this.field_146292_n.add(new GuiButton(667, this.field_146294_l / 2 - this.field_146916_y / 2, this.field_146295_m / 4 + 120 + 12, I18n.func_135052_a("gui.back", new Object[0])));
   }

   public Minecraft func_146903_h() {
      return this.field_146297_k;
   }

   public void func_146906_b(String p_146906_1_) {
      this.field_146913_s = p_146906_1_;
   }

   public boolean func_146904_i() {
      return this.field_146909_v;
   }

}
