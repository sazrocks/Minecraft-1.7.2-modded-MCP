package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public abstract class AbstractClientPlayer extends EntityPlayer {

   public static final ResourceLocation field_110314_b = new ResourceLocation("textures/entity/steve.png");
   private ThreadDownloadImageData field_110316_a;
   private ThreadDownloadImageData field_110315_c;
   private ResourceLocation field_110312_d;
   private ResourceLocation field_110313_e;
   private static final String __OBFID = "CL_00000935";


   public AbstractClientPlayer(World p_i45074_1_, GameProfile p_i45074_2_) {
      super(p_i45074_1_, p_i45074_2_);
      this.func_110302_j();
   }

   protected void func_110302_j() {
      String var1 = this.func_70005_c_();
      if(!var1.isEmpty()) {
         this.field_110312_d = func_110311_f(var1);
         this.field_110313_e = func_110299_g(var1);
         this.field_110316_a = func_110304_a(this.field_110312_d, var1);
         this.field_110315_c = func_110307_b(this.field_110313_e, var1);
      }
   }

   public ThreadDownloadImageData func_110309_l() {
      return this.field_110316_a;
   }

   public ThreadDownloadImageData func_110310_o() {
      return this.field_110315_c;
   }

   public ResourceLocation func_110306_p() {
      return this.field_110312_d;
   }

   public ResourceLocation func_110303_q() {
      return this.field_110313_e;
   }

   public static ThreadDownloadImageData func_110304_a(ResourceLocation p_110304_0_, String p_110304_1_) {
      return func_110301_a(p_110304_0_, func_110300_d(p_110304_1_), field_110314_b, new ImageBufferDownload());
   }

   public static ThreadDownloadImageData func_110307_b(ResourceLocation p_110307_0_, String p_110307_1_) {
      return func_110301_a(p_110307_0_, func_110308_e(p_110307_1_), (ResourceLocation)null, (IImageBuffer)null);
   }

   private static ThreadDownloadImageData func_110301_a(ResourceLocation p_110301_0_, String p_110301_1_, ResourceLocation p_110301_2_, IImageBuffer p_110301_3_) {
      TextureManager var4 = Minecraft.func_71410_x().func_110434_K();
      Object var5 = var4.func_110581_b(p_110301_0_);
      if(var5 == null) {
         var5 = new ThreadDownloadImageData(p_110301_1_, p_110301_2_, p_110301_3_);
         var4.func_110579_a(p_110301_0_, (ITextureObject)var5);
      }

      return (ThreadDownloadImageData)var5;
   }

   public static String func_110300_d(String p_110300_0_) {
      return String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[]{StringUtils.func_76338_a(p_110300_0_)});
   }

   public static String func_110308_e(String p_110308_0_) {
      return String.format("http://skins.minecraft.net/MinecraftCloaks/%s.png", new Object[]{StringUtils.func_76338_a(p_110308_0_)});
   }

   public static ResourceLocation func_110311_f(String p_110311_0_) {
      return new ResourceLocation("skins/" + StringUtils.func_76338_a(p_110311_0_));
   }

   public static ResourceLocation func_110299_g(String p_110299_0_) {
      return new ResourceLocation("cloaks/" + StringUtils.func_76338_a(p_110299_0_));
   }

   public static ResourceLocation func_110305_h(String p_110305_0_) {
      return new ResourceLocation("skull/" + StringUtils.func_76338_a(p_110305_0_));
   }

}
