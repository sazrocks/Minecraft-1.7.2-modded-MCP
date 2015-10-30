package net.minecraft.client.renderer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadDownloadImageData extends SimpleTexture {

   private static final Logger field_147644_c = LogManager.getLogger();
   private static final AtomicInteger field_147643_d = new AtomicInteger(0);
   private final String field_110562_b;
   private final IImageBuffer field_110563_c;
   private BufferedImage field_110560_d;
   private Thread field_110561_e;
   private boolean field_110559_g;
   private static final String __OBFID = "CL_00001049";


   public ThreadDownloadImageData(String p_i1273_1_, ResourceLocation p_i1273_2_, IImageBuffer p_i1273_3_) {
      super(p_i1273_2_);
      this.field_110562_b = p_i1273_1_;
      this.field_110563_c = p_i1273_3_;
   }

   private void func_147640_e() {
      if(!this.field_110559_g) {
         if(this.field_110560_d != null) {
            if(this.field_110568_b != null) {
               this.func_147631_c();
            }

            TextureUtil.func_110987_a(super.func_110552_b(), this.field_110560_d);
            this.field_110559_g = true;
         }

      }
   }

   public int func_110552_b() {
      this.func_147640_e();
      return super.func_110552_b();
   }

   public void func_147641_a(BufferedImage p_147641_1_) {
      this.field_110560_d = p_147641_1_;
   }

   public void func_110551_a(IResourceManager p_110551_1_) throws IOException {
      if(this.field_110560_d == null && this.field_110568_b != null) {
         super.func_110551_a(p_110551_1_);
      }

      if(this.field_110561_e == null) {
         this.field_110561_e = new Thread("Texture Downloader #" + field_147643_d.incrementAndGet()) {

            private static final String __OBFID = "CL_00001050";

            public void run() {
               HttpURLConnection var1 = null;

               try {
                  var1 = (HttpURLConnection)(new URL(ThreadDownloadImageData.this.field_110562_b)).openConnection(Minecraft.func_71410_x().func_110437_J());
                  var1.setDoInput(true);
                  var1.setDoOutput(false);
                  var1.connect();
                  if(var1.getResponseCode() / 100 == 2) {
                     BufferedImage var2 = ImageIO.read(var1.getInputStream());
                     if(ThreadDownloadImageData.this.field_110563_c != null) {
                        var2 = ThreadDownloadImageData.this.field_110563_c.func_78432_a(var2);
                     }

                     ThreadDownloadImageData.this.func_147641_a(var2);
                     return;
                  }
               } catch (Exception var6) {
                  ThreadDownloadImageData.field_147644_c.error("Couldn\'t download http texture", var6);
                  return;
               } finally {
                  if(var1 != null) {
                     var1.disconnect();
                  }

               }

            }
         };
         this.field_110561_e.setDaemon(true);
         this.field_110561_e.setName("Skin downloader: " + this.field_110562_b);
         this.field_110561_e.start();
      }

   }

   public boolean func_110557_a() {
      this.func_147640_e();
      return this.field_110559_g;
   }

}
