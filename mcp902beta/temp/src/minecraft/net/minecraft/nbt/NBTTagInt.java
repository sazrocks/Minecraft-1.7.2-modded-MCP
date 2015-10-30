package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.NBTBase;

public class NBTTagInt extends NBTBase.NBTPrimitive {

   private int field_74748_a;
   private static final String __OBFID = "CL_00001223";


   NBTTagInt() {}

   public NBTTagInt(int p_i45133_1_) {
      this.field_74748_a = p_i45133_1_;
   }

   void func_74734_a(DataOutput p_74734_1_) throws IOException {
      p_74734_1_.writeInt(this.field_74748_a);
   }

   void func_74735_a(DataInput p_74735_1_, int p_74735_2_) throws IOException {
      this.field_74748_a = p_74735_1_.readInt();
   }

   public byte func_74732_a() {
      return (byte)3;
   }

   public String toString() {
      return "" + this.field_74748_a;
   }

   public NBTBase func_74737_b() {
      return new NBTTagInt(this.field_74748_a);
   }

   public boolean equals(Object p_equals_1_) {
      if(super.equals(p_equals_1_)) {
         NBTTagInt var2 = (NBTTagInt)p_equals_1_;
         return this.field_74748_a == var2.field_74748_a;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return super.hashCode() ^ this.field_74748_a;
   }

   public long func_150291_c() {
      return (long)this.field_74748_a;
   }

   public int func_150287_d() {
      return this.field_74748_a;
   }

   public short func_150289_e() {
      return (short)(this.field_74748_a & '\uffff');
   }

   public byte func_150290_f() {
      return (byte)(this.field_74748_a & 255);
   }

   public double func_150286_g() {
      return (double)this.field_74748_a;
   }

   public float func_150288_h() {
      return (float)this.field_74748_a;
   }
}
