package net.minecraft.server.management;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BanEntry {

   private static final Logger field_151362_b = LogManager.getLogger();
   public static final SimpleDateFormat field_73698_a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   private final String field_73697_c;
   private Date field_73694_d = new Date();
   private String field_73695_e = "(Unknown)";
   private Date field_73692_f;
   private String field_73693_g = "Banned by an operator.";
   private static final String __OBFID = "CL_00001395";


   public BanEntry(String p_i1489_1_) {
      this.field_73697_c = p_i1489_1_;
   }

   public String func_73684_a() {
      return this.field_73697_c;
   }

   public Date func_73683_b() {
      return this.field_73694_d;
   }

   public String func_73690_c() {
      return this.field_73695_e;
   }

   public void func_73687_a(String p_73687_1_) {
      this.field_73695_e = p_73687_1_;
   }

   public Date func_73680_d() {
      return this.field_73692_f;
   }

   public boolean func_73682_e() {
      return this.field_73692_f == null?false:this.field_73692_f.before(new Date());
   }

   public String func_73686_f() {
      return this.field_73693_g;
   }

   public void func_73689_b(String p_73689_1_) {
      this.field_73693_g = p_73689_1_;
   }

   public String func_73685_g() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.func_73684_a());
      var1.append("|");
      var1.append(field_73698_a.format(this.func_73683_b()));
      var1.append("|");
      var1.append(this.func_73690_c());
      var1.append("|");
      var1.append(this.func_73680_d() == null?"Forever":field_73698_a.format(this.func_73680_d()));
      var1.append("|");
      var1.append(this.func_73686_f());
      return var1.toString();
   }

}
