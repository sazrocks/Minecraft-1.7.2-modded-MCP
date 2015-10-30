package net.minecraft.server.management;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.server.management.BanEntry;
import net.minecraft.server.management.LowerStringMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BanList {

   private static final Logger field_151356_a = LogManager.getLogger();
   private final LowerStringMap field_73715_a = new LowerStringMap();
   private final File field_73713_b;
   private boolean field_73714_c = true;
   private static final String __OBFID = "CL_00001396";


   public BanList(File p_i1490_1_) {
      this.field_73713_b = p_i1490_1_;
   }

   public boolean func_73710_b() {
      return this.field_73714_c;
   }

   public void func_73708_a(boolean p_73708_1_) {
      this.field_73714_c = p_73708_1_;
   }

   public Map func_73712_c() {
      this.func_73705_d();
      return this.field_73715_a;
   }

   public boolean func_73704_a(String p_73704_1_) {
      if(!this.func_73710_b()) {
         return false;
      } else {
         this.func_73705_d();
         return this.field_73715_a.containsKey(p_73704_1_);
      }
   }

   public void func_73706_a(BanEntry p_73706_1_) {
      this.field_73715_a.put(p_73706_1_.func_73684_a(), p_73706_1_);
      this.func_73711_f();
   }

   public void func_73709_b(String p_73709_1_) {
      this.field_73715_a.remove(p_73709_1_);
      this.func_73711_f();
   }

   public void func_73705_d() {
      Iterator var1 = this.field_73715_a.values().iterator();

      while(var1.hasNext()) {
         BanEntry var2 = (BanEntry)var1.next();
         if(var2.func_73682_e()) {
            var1.remove();
         }
      }

   }

   public void func_73711_f() {
      this.func_73703_b(true);
   }

   public void func_73703_b(boolean p_73703_1_) {
      this.func_73705_d();

      try {
         PrintWriter var2 = new PrintWriter(new FileWriter(this.field_73713_b, false));
         if(p_73703_1_) {
            var2.println("# Updated " + (new SimpleDateFormat()).format(new Date()) + " by Minecraft " + "1.7.2");
            var2.println("# victim name | ban date | banned by | banned until | reason");
            var2.println();
         }

         Iterator var3 = this.field_73715_a.values().iterator();

         while(var3.hasNext()) {
            BanEntry var4 = (BanEntry)var3.next();
            var2.println(var4.func_73685_g());
         }

         var2.close();
      } catch (IOException var5) {
         field_151356_a.error("Could not save ban list", var5);
      }

   }

}
