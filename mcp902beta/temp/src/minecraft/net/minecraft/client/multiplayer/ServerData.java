package net.minecraft.client.multiplayer;

import net.minecraft.nbt.NBTTagCompound;

public class ServerData {

   public String field_78847_a;
   public String field_78845_b;
   public String field_78846_c;
   public String field_78843_d;
   public long field_78844_e;
   public int field_82821_f = 4;
   public String field_82822_g = "1.7.2";
   public boolean field_78841_f;
   public String field_147412_i;
   private boolean field_78842_g = true;
   private boolean field_78848_h;
   private boolean field_82823_k;
   private String field_147411_m;
   private static final String __OBFID = "CL_00000890";


   public ServerData(String p_i1193_1_, String p_i1193_2_) {
      this.field_78847_a = p_i1193_1_;
      this.field_78845_b = p_i1193_2_;
   }

   public NBTTagCompound func_78836_a() {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.func_74778_a("name", this.field_78847_a);
      var1.func_74778_a("ip", this.field_78845_b);
      var1.func_74757_a("hideAddress", this.field_82823_k);
      if(this.field_147411_m != null) {
         var1.func_74778_a("icon", this.field_147411_m);
      }

      if(!this.field_78842_g) {
         var1.func_74757_a("acceptTextures", this.field_78848_h);
      }

      return var1;
   }

   public boolean func_147408_b() {
      return this.field_78848_h;
   }

   public boolean func_147410_c() {
      return this.field_78842_g;
   }

   public void func_78838_a(boolean p_78838_1_) {
      this.field_78848_h = p_78838_1_;
      this.field_78842_g = false;
   }

   public boolean func_82820_d() {
      return this.field_82823_k;
   }

   public void func_82819_b(boolean p_82819_1_) {
      this.field_82823_k = p_82819_1_;
   }

   public static ServerData func_78837_a(NBTTagCompound p_78837_0_) {
      ServerData var1 = new ServerData(p_78837_0_.func_74779_i("name"), p_78837_0_.func_74779_i("ip"));
      var1.field_82823_k = p_78837_0_.func_74767_n("hideAddress");
      if(p_78837_0_.func_150297_b("icon", 8)) {
         var1.func_147407_a(p_78837_0_.func_74779_i("icon"));
      }

      if(p_78837_0_.func_150297_b("acceptTextures", 99)) {
         var1.func_78838_a(p_78837_0_.func_74767_n("acceptTextures"));
      }

      return var1;
   }

   public String func_147409_e() {
      return this.field_147411_m;
   }

   public void func_147407_a(String p_147407_1_) {
      this.field_147411_m = p_147407_1_;
   }
}
