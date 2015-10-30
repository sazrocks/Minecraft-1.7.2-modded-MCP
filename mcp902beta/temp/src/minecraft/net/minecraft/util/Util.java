package net.minecraft.util;

import java.util.UUID;
import java.util.regex.Pattern;

public class Util {

   private static final Pattern field_147174_a = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");
   private static final String __OBFID = "CL_00001633";


   public static Util.EnumOS func_110647_a() {
      String var0 = System.getProperty("os.name").toLowerCase();
      return var0.contains("win")?Util.EnumOS.WINDOWS:(var0.contains("mac")?Util.EnumOS.MACOS:(var0.contains("solaris")?Util.EnumOS.SOLARIS:(var0.contains("sunos")?Util.EnumOS.SOLARIS:(var0.contains("linux")?Util.EnumOS.LINUX:(var0.contains("unix")?Util.EnumOS.LINUX:Util.EnumOS.UNKNOWN)))));
   }

   public static boolean func_147172_a(String p_147172_0_) {
      return field_147174_a.matcher(p_147172_0_).matches();
   }

   public static UUID func_147173_b(String p_147173_0_) {
      if(p_147173_0_ == null) {
         return null;
      } else if(func_147172_a(p_147173_0_)) {
         return UUID.fromString(p_147173_0_);
      } else {
         if(p_147173_0_.length() == 32) {
            String var1 = p_147173_0_.substring(0, 8) + "-" + p_147173_0_.substring(8, 12) + "-" + p_147173_0_.substring(12, 16) + "-" + p_147173_0_.substring(16, 20) + "-" + p_147173_0_.substring(20, 32);
            if(func_147172_a(var1)) {
               return UUID.fromString(var1);
            }
         }

         return null;
      }
   }


   public static enum EnumOS {

      LINUX("LINUX", 0),
      SOLARIS("SOLARIS", 1),
      WINDOWS("WINDOWS", 2),
      MACOS("MACOS", 3),
      UNKNOWN("UNKNOWN", 4);
      // $FF: synthetic field
      private static final Util.EnumOS[] $VALUES = new Util.EnumOS[]{LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN};
      private static final String __OBFID = "CL_00001660";


      private EnumOS(String p_i1357_1_, int p_i1357_2_) {}

   }
}
