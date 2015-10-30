package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagEnd;
import net.minecraft.util.ReportedException;

public class CompressedStreamTools {

   private static final String __OBFID = "CL_00001226";


   public static NBTTagCompound func_74796_a(InputStream p_74796_0_) throws IOException {
      DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(p_74796_0_)));

      NBTTagCompound var2;
      try {
         var2 = func_74794_a(var1);
      } finally {
         var1.close();
      }

      return var2;
   }

   public static void func_74799_a(NBTTagCompound p_74799_0_, OutputStream p_74799_1_) throws IOException {
      DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(p_74799_1_));

      try {
         func_74800_a(p_74799_0_, var2);
      } finally {
         var2.close();
      }

   }

   public static NBTTagCompound func_74792_a(byte[] p_74792_0_) throws IOException {
      DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(p_74792_0_))));

      NBTTagCompound var2;
      try {
         var2 = func_74794_a(var1);
      } finally {
         var1.close();
      }

      return var2;
   }

   public static byte[] func_74798_a(NBTTagCompound p_74798_0_) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(var1));

      try {
         func_74800_a(p_74798_0_, var2);
      } finally {
         var2.close();
      }

      return var1.toByteArray();
   }

   public static void func_74793_a(NBTTagCompound p_74793_0_, File p_74793_1_) throws IOException {
      File var2 = new File(p_74793_1_.getAbsolutePath() + "_tmp");
      if(var2.exists()) {
         var2.delete();
      }

      func_74795_b(p_74793_0_, var2);
      if(p_74793_1_.exists()) {
         p_74793_1_.delete();
      }

      if(p_74793_1_.exists()) {
         throw new IOException("Failed to delete " + p_74793_1_);
      } else {
         var2.renameTo(p_74793_1_);
      }
   }

   public static void func_74795_b(NBTTagCompound p_74795_0_, File p_74795_1_) throws IOException {
      DataOutputStream var2 = new DataOutputStream(new FileOutputStream(p_74795_1_));

      try {
         func_74800_a(p_74795_0_, var2);
      } finally {
         var2.close();
      }

   }

   public static NBTTagCompound func_74797_a(File p_74797_0_) throws IOException {
      if(!p_74797_0_.exists()) {
         return null;
      } else {
         DataInputStream var1 = new DataInputStream(new FileInputStream(p_74797_0_));

         NBTTagCompound var2;
         try {
            var2 = func_74794_a(var1);
         } finally {
            var1.close();
         }

         return var2;
      }
   }

   public static NBTTagCompound func_74794_a(DataInput p_74794_0_) throws IOException {
      NBTBase var1 = func_150664_a(p_74794_0_, 0);
      if(var1 instanceof NBTTagCompound) {
         return (NBTTagCompound)var1;
      } else {
         throw new IOException("Root tag must be a named compound tag");
      }
   }

   public static void func_74800_a(NBTTagCompound p_74800_0_, DataOutput p_74800_1_) throws IOException {
      func_150663_a(p_74800_0_, p_74800_1_);
   }

   private static void func_150663_a(NBTBase p_150663_0_, DataOutput p_150663_1_) throws IOException {
      p_150663_1_.writeByte(p_150663_0_.func_74732_a());
      if(p_150663_0_.func_74732_a() != 0) {
         p_150663_1_.writeUTF("");
         p_150663_0_.func_74734_a(p_150663_1_);
      }
   }

   private static NBTBase func_150664_a(DataInput p_150664_0_, int p_150664_1_) throws IOException {
      byte var2 = p_150664_0_.readByte();
      if(var2 == 0) {
         return new NBTTagEnd();
      } else {
         p_150664_0_.readUTF();
         NBTBase var3 = NBTBase.func_150284_a(var2);

         try {
            var3.func_74735_a(p_150664_0_, p_150664_1_);
            return var3;
         } catch (IOException var7) {
            CrashReport var5 = CrashReport.func_85055_a(var7, "Loading NBT data");
            CrashReportCategory var6 = var5.func_85058_a("NBT Tag");
            var6.func_71507_a("Tag name", "[UNNAMED TAG]");
            var6.func_71507_a("Tag type", Byte.valueOf(var2));
            throw new ReportedException(var5);
         }
      }
   }
}
