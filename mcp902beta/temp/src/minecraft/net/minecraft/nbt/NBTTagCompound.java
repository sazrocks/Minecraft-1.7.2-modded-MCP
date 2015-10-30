package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ReportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBTTagCompound extends NBTBase {

   private static final Logger field_150301_b = LogManager.getLogger();
   private Map field_74784_a = new HashMap();
   private static final String __OBFID = "CL_00001215";


   void func_74734_a(DataOutput p_74734_1_) throws IOException {
      Iterator var2 = this.field_74784_a.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         NBTBase var4 = (NBTBase)this.field_74784_a.get(var3);
         func_150298_a(var3, var4, p_74734_1_);
      }

      p_74734_1_.writeByte(0);
   }

   void func_74735_a(DataInput p_74735_1_, int p_74735_2_) throws IOException {
      if(p_74735_2_ > 512) {
         throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
      } else {
         this.field_74784_a.clear();

         byte var3;
         while((var3 = func_150300_a(p_74735_1_)) != 0) {
            String var4 = func_150294_b(p_74735_1_);
            NBTBase var5 = func_150293_a(var3, var4, p_74735_1_, p_74735_2_ + 1);
            this.field_74784_a.put(var4, var5);
         }

      }
   }

   public Set func_150296_c() {
      return this.field_74784_a.keySet();
   }

   public byte func_74732_a() {
      return (byte)10;
   }

   public void func_74782_a(String p_74782_1_, NBTBase p_74782_2_) {
      this.field_74784_a.put(p_74782_1_, p_74782_2_);
   }

   public void func_74774_a(String p_74774_1_, byte p_74774_2_) {
      this.field_74784_a.put(p_74774_1_, new NBTTagByte(p_74774_2_));
   }

   public void func_74777_a(String p_74777_1_, short p_74777_2_) {
      this.field_74784_a.put(p_74777_1_, new NBTTagShort(p_74777_2_));
   }

   public void func_74768_a(String p_74768_1_, int p_74768_2_) {
      this.field_74784_a.put(p_74768_1_, new NBTTagInt(p_74768_2_));
   }

   public void func_74772_a(String p_74772_1_, long p_74772_2_) {
      this.field_74784_a.put(p_74772_1_, new NBTTagLong(p_74772_2_));
   }

   public void func_74776_a(String p_74776_1_, float p_74776_2_) {
      this.field_74784_a.put(p_74776_1_, new NBTTagFloat(p_74776_2_));
   }

   public void func_74780_a(String p_74780_1_, double p_74780_2_) {
      this.field_74784_a.put(p_74780_1_, new NBTTagDouble(p_74780_2_));
   }

   public void func_74778_a(String p_74778_1_, String p_74778_2_) {
      this.field_74784_a.put(p_74778_1_, new NBTTagString(p_74778_2_));
   }

   public void func_74773_a(String p_74773_1_, byte[] p_74773_2_) {
      this.field_74784_a.put(p_74773_1_, new NBTTagByteArray(p_74773_2_));
   }

   public void func_74783_a(String p_74783_1_, int[] p_74783_2_) {
      this.field_74784_a.put(p_74783_1_, new NBTTagIntArray(p_74783_2_));
   }

   public void func_74757_a(String p_74757_1_, boolean p_74757_2_) {
      this.func_74774_a(p_74757_1_, (byte)(p_74757_2_?1:0));
   }

   public NBTBase func_74781_a(String p_74781_1_) {
      return (NBTBase)this.field_74784_a.get(p_74781_1_);
   }

   public byte func_150299_b(String p_150299_1_) {
      NBTBase var2 = (NBTBase)this.field_74784_a.get(p_150299_1_);
      return var2 != null?var2.func_74732_a():0;
   }

   public boolean func_74764_b(String p_74764_1_) {
      return this.field_74784_a.containsKey(p_74764_1_);
   }

   public boolean func_150297_b(String p_150297_1_, int p_150297_2_) {
      byte var3 = this.func_150299_b(p_150297_1_);
      if(var3 == p_150297_2_) {
         return true;
      } else if(p_150297_2_ != 99) {
         if(var3 > 0) {
            field_150301_b.warn("NBT tag {} was of wrong type; expected {}, found {}", new Object[]{p_150297_1_, func_150283_g(p_150297_2_), func_150283_g(var3)});
         }

         return false;
      } else {
         return var3 == 1 || var3 == 2 || var3 == 3 || var3 == 4 || var3 == 5 || var3 == 6;
      }
   }

   public byte func_74771_c(String p_74771_1_) {
      try {
         return !this.field_74784_a.containsKey(p_74771_1_)?0:((NBTBase.NBTPrimitive)this.field_74784_a.get(p_74771_1_)).func_150290_f();
      } catch (ClassCastException var3) {
         return (byte)0;
      }
   }

   public short func_74765_d(String p_74765_1_) {
      try {
         return !this.field_74784_a.containsKey(p_74765_1_)?0:((NBTBase.NBTPrimitive)this.field_74784_a.get(p_74765_1_)).func_150289_e();
      } catch (ClassCastException var3) {
         return (short)0;
      }
   }

   public int func_74762_e(String p_74762_1_) {
      try {
         return !this.field_74784_a.containsKey(p_74762_1_)?0:((NBTBase.NBTPrimitive)this.field_74784_a.get(p_74762_1_)).func_150287_d();
      } catch (ClassCastException var3) {
         return 0;
      }
   }

   public long func_74763_f(String p_74763_1_) {
      try {
         return !this.field_74784_a.containsKey(p_74763_1_)?0L:((NBTBase.NBTPrimitive)this.field_74784_a.get(p_74763_1_)).func_150291_c();
      } catch (ClassCastException var3) {
         return 0L;
      }
   }

   public float func_74760_g(String p_74760_1_) {
      try {
         return !this.field_74784_a.containsKey(p_74760_1_)?0.0F:((NBTBase.NBTPrimitive)this.field_74784_a.get(p_74760_1_)).func_150288_h();
      } catch (ClassCastException var3) {
         return 0.0F;
      }
   }

   public double func_74769_h(String p_74769_1_) {
      try {
         return !this.field_74784_a.containsKey(p_74769_1_)?0.0D:((NBTBase.NBTPrimitive)this.field_74784_a.get(p_74769_1_)).func_150286_g();
      } catch (ClassCastException var3) {
         return 0.0D;
      }
   }

   public String func_74779_i(String p_74779_1_) {
      try {
         return !this.field_74784_a.containsKey(p_74779_1_)?"":((NBTBase)this.field_74784_a.get(p_74779_1_)).func_150285_a_();
      } catch (ClassCastException var3) {
         return "";
      }
   }

   public byte[] func_74770_j(String p_74770_1_) {
      try {
         return !this.field_74784_a.containsKey(p_74770_1_)?new byte[0]:((NBTTagByteArray)this.field_74784_a.get(p_74770_1_)).func_150292_c();
      } catch (ClassCastException var3) {
         throw new ReportedException(this.func_82581_a(p_74770_1_, 7, var3));
      }
   }

   public int[] func_74759_k(String p_74759_1_) {
      try {
         return !this.field_74784_a.containsKey(p_74759_1_)?new int[0]:((NBTTagIntArray)this.field_74784_a.get(p_74759_1_)).func_150302_c();
      } catch (ClassCastException var3) {
         throw new ReportedException(this.func_82581_a(p_74759_1_, 11, var3));
      }
   }

   public NBTTagCompound func_74775_l(String p_74775_1_) {
      try {
         return !this.field_74784_a.containsKey(p_74775_1_)?new NBTTagCompound():(NBTTagCompound)this.field_74784_a.get(p_74775_1_);
      } catch (ClassCastException var3) {
         throw new ReportedException(this.func_82581_a(p_74775_1_, 10, var3));
      }
   }

   public NBTTagList func_150295_c(String p_150295_1_, int p_150295_2_) {
      try {
         if(this.func_150299_b(p_150295_1_) != 9) {
            return new NBTTagList();
         } else {
            NBTTagList var3 = (NBTTagList)this.field_74784_a.get(p_150295_1_);
            return var3.func_74745_c() > 0 && var3.func_150303_d() != p_150295_2_?new NBTTagList():var3;
         }
      } catch (ClassCastException var4) {
         throw new ReportedException(this.func_82581_a(p_150295_1_, 9, var4));
      }
   }

   public boolean func_74767_n(String p_74767_1_) {
      return this.func_74771_c(p_74767_1_) != 0;
   }

   public void func_82580_o(String p_82580_1_) {
      this.field_74784_a.remove(p_82580_1_);
   }

   public String toString() {
      String var1 = "{";

      String var3;
      for(Iterator var2 = this.field_74784_a.keySet().iterator(); var2.hasNext(); var1 = var1 + var3 + ':' + this.field_74784_a.get(var3) + ',') {
         var3 = (String)var2.next();
      }

      return var1 + "}";
   }

   public boolean func_82582_d() {
      return this.field_74784_a.isEmpty();
   }

   private CrashReport func_82581_a(final String p_82581_1_, final int p_82581_2_, ClassCastException p_82581_3_) {
      CrashReport var4 = CrashReport.func_85055_a(p_82581_3_, "Reading NBT data");
      CrashReportCategory var5 = var4.func_85057_a("Corrupt NBT tag", 1);
      var5.func_71500_a("Tag type found", new Callable() {

         private static final String __OBFID = "CL_00001216";

         public String call() {
            return NBTBase.field_82578_b[((NBTBase)NBTTagCompound.this.field_74784_a.get(p_82581_1_)).func_74732_a()];
         }
         // $FF: synthetic method
         public Object call() {
            return this.call();
         }
      });
      var5.func_71500_a("Tag type expected", new Callable() {

         private static final String __OBFID = "CL_00001217";

         public String call() {
            return NBTBase.field_82578_b[p_82581_2_];
         }
         // $FF: synthetic method
         public Object call() {
            return this.call();
         }
      });
      var5.func_71507_a("Tag name", p_82581_1_);
      return var4;
   }

   public NBTBase func_74737_b() {
      NBTTagCompound var1 = new NBTTagCompound();
      Iterator var2 = this.field_74784_a.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         var1.func_74782_a(var3, ((NBTBase)this.field_74784_a.get(var3)).func_74737_b());
      }

      return var1;
   }

   public boolean equals(Object p_equals_1_) {
      if(super.equals(p_equals_1_)) {
         NBTTagCompound var2 = (NBTTagCompound)p_equals_1_;
         return this.field_74784_a.entrySet().equals(var2.field_74784_a.entrySet());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return super.hashCode() ^ this.field_74784_a.hashCode();
   }

   private static void func_150298_a(String p_150298_0_, NBTBase p_150298_1_, DataOutput p_150298_2_) throws IOException {
      p_150298_2_.writeByte(p_150298_1_.func_74732_a());
      if(p_150298_1_.func_74732_a() != 0) {
         p_150298_2_.writeUTF(p_150298_0_);
         p_150298_1_.func_74734_a(p_150298_2_);
      }
   }

   private static byte func_150300_a(DataInput p_150300_0_) throws IOException {
      return p_150300_0_.readByte();
   }

   private static String func_150294_b(DataInput p_150294_0_) throws IOException {
      return p_150294_0_.readUTF();
   }

   static NBTBase func_150293_a(byte p_150293_0_, String p_150293_1_, DataInput p_150293_2_, int p_150293_3_) {
      NBTBase var4 = NBTBase.func_150284_a(p_150293_0_);

      try {
         var4.func_74735_a(p_150293_2_, p_150293_3_);
         return var4;
      } catch (IOException var8) {
         CrashReport var6 = CrashReport.func_85055_a(var8, "Loading NBT data");
         CrashReportCategory var7 = var6.func_85058_a("NBT Tag");
         var7.func_71507_a("Tag name", p_150293_1_);
         var7.func_71507_a("Tag type", Byte.valueOf(p_150293_0_));
         throw new ReportedException(var6);
      }
   }

}
