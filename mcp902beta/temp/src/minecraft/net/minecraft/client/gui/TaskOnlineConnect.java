package net.minecraft.client.gui;

import io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenDisconnectedOnline;
import net.minecraft.client.gui.GuiScreenReamlsTOS;
import net.minecraft.client.gui.TaskLongRunning;
import net.minecraft.client.mco.ExceptionMcoService;
import net.minecraft.client.mco.ExceptionRetryCall;
import net.minecraft.client.mco.McoClient;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.mco.McoServerAddress;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaskOnlineConnect extends TaskLongRunning {

   private static final AtomicInteger field_148439_a = new AtomicInteger(0);
   private static final Logger field_148438_c = LogManager.getLogger();
   private NetworkManager field_148436_d;
   private final McoServer field_148437_e;
   private final GuiScreen field_148435_f;
   private static final String __OBFID = "CL_00000790";


   public TaskOnlineConnect(GuiScreen p_i1119_1_, McoServer p_i1119_2_) {
      this.field_148435_f = p_i1119_1_;
      this.field_148437_e = p_i1119_2_;
   }

   public void run() {
      this.func_148417_b(I18n.func_135052_a("mco.connect.connecting", new Object[0]));
      Session var1 = this.func_148413_b().func_110432_I();
      McoClient var2 = new McoClient(var1.func_111286_b(), var1.func_111285_a(), "1.7.2", Minecraft.func_71410_x().func_110437_J());
      boolean var3 = false;
      boolean var4 = false;
      int var5 = 5;
      McoServerAddress var6 = null;
      boolean var7 = false;

      for(int var8 = 0; var8 < 10 && !this.func_148418_c(); ++var8) {
         try {
            var6 = var2.func_148688_b(this.field_148437_e.field_148812_a);
            var3 = true;
         } catch (ExceptionRetryCall var10) {
            var5 = var10.field_148832_d;
         } catch (ExceptionMcoService var11) {
            if(var11.field_148830_c == 6002) {
               var7 = true;
            } else {
               var4 = true;
               this.func_148416_a(var11.toString());
               field_148438_c.error("Couldn\'t connect to world", var11);
            }
            break;
         } catch (IOException var12) {
            field_148438_c.error("Couldn\'t parse response connecting to world", var12);
         } catch (Exception var13) {
            var4 = true;
            field_148438_c.error("Couldn\'t connect to world", var13);
            this.func_148416_a(var13.getLocalizedMessage());
         }

         if(var3) {
            break;
         }

         this.func_148429_a(var5);
      }

      if(var7) {
         this.func_148413_b().func_147108_a(new GuiScreenReamlsTOS(this.field_148435_f, this.field_148437_e));
      } else if(!this.func_148418_c() && !var4) {
         if(var3) {
            ServerAddress var14 = ServerAddress.func_78860_a(var6.field_148770_a);
            this.func_148432_a(var14.func_78861_a(), var14.func_78864_b());
         } else {
            this.func_148413_b().func_147108_a(this.field_148435_f);
         }
      }

   }

   private void func_148429_a(int p_148429_1_) {
      try {
         Thread.sleep((long)(p_148429_1_ * 1000));
      } catch (InterruptedException var3) {
         field_148438_c.warn(var3.getLocalizedMessage());
      }

   }

   private void func_148432_a(final String p_148432_1_, final int p_148432_2_) {
      (new Thread("MCO Connector #" + field_148439_a.incrementAndGet()) {

         private static final String __OBFID = "CL_00000791";

         public void run() {
            try {
               if(TaskOnlineConnect.this.func_148418_c()) {
                  return;
               }

               TaskOnlineConnect.this.field_148436_d = NetworkManager.func_150726_a(InetAddress.getByName(p_148432_1_), p_148432_2_);
               if(TaskOnlineConnect.this.func_148418_c()) {
                  return;
               }

               TaskOnlineConnect.this.field_148436_d.func_150719_a(new NetHandlerLoginClient(TaskOnlineConnect.this.field_148436_d, TaskOnlineConnect.this.func_148413_b(), TaskOnlineConnect.this.field_148435_f));
               if(TaskOnlineConnect.this.func_148418_c()) {
                  return;
               }

               TaskOnlineConnect.this.field_148436_d.func_150725_a(new C00Handshake(4, p_148432_1_, p_148432_2_, EnumConnectionState.LOGIN), new GenericFutureListener[0]);
               if(TaskOnlineConnect.this.func_148418_c()) {
                  return;
               }

               TaskOnlineConnect.this.field_148436_d.func_150725_a(new C00PacketLoginStart(TaskOnlineConnect.this.func_148413_b().func_110432_I().func_148256_e()), new GenericFutureListener[0]);
               TaskOnlineConnect.this.func_148417_b(I18n.func_135052_a("mco.connect.authorizing", new Object[0]));
            } catch (UnknownHostException var2) {
               if(TaskOnlineConnect.this.func_148418_c()) {
                  return;
               }

               TaskOnlineConnect.field_148438_c.error("Couldn\'t connect to world", var2);
               TaskOnlineConnect.this.func_148413_b().func_147108_a(new GuiScreenDisconnectedOnline(TaskOnlineConnect.this.field_148435_f, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[]{"Unknown host \'" + p_148432_1_ + "\'"})));
            } catch (Exception var3) {
               if(TaskOnlineConnect.this.func_148418_c()) {
                  return;
               }

               TaskOnlineConnect.field_148438_c.error("Couldn\'t connect to world", var3);
               TaskOnlineConnect.this.func_148413_b().func_147108_a(new GuiScreenDisconnectedOnline(TaskOnlineConnect.this.field_148435_f, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[]{var3.toString()})));
            }

         }
      }).start();
   }

   public void func_148414_a() {
      if(this.field_148436_d != null) {
         this.field_148436_d.func_74428_b();
      }

   }

}
