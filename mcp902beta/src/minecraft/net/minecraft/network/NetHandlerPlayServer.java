package net.minecraft.network;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.block.material.Material;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.BanEntry;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerPlayServer implements INetHandlerPlayServer
{
    private static final Logger field_147370_c = LogManager.getLogger();
    public final NetworkManager field_147371_a;
    private final MinecraftServer field_147367_d;
    public EntityPlayerMP field_147369_b;
    private int field_147368_e;
    private int field_147365_f;
    private boolean field_147366_g;
    private int field_147378_h;
    private long field_147379_i;
    private static Random field_147376_j = new Random();
    private long field_147377_k;
    private int field_147374_l;
    private int field_147375_m;
    private IntHashMap field_147372_n = new IntHashMap();
    private double field_147373_o;
    private double field_147382_p;
    private double field_147381_q;
    private boolean field_147380_r = true;
    private static final String __OBFID = "CL_00001452";

    public NetHandlerPlayServer(MinecraftServer par1MinecraftServer, NetworkManager par2INetworkManager, EntityPlayerMP par3EntityPlayerMP)
    {
        this.field_147367_d = par1MinecraftServer;
        this.field_147371_a = par2INetworkManager;
        par2INetworkManager.func_150719_a(this);
        this.field_147369_b = par3EntityPlayerMP;
        par3EntityPlayerMP.playerNetServerHandler = this;
    }

    public void func_147233_a()
    {
        this.field_147366_g = false;
        ++this.field_147368_e;
        this.field_147367_d.theProfiler.startSection("keepAlive");

        if ((long)this.field_147368_e - this.field_147377_k > 40L)
        {
            this.field_147377_k = (long)this.field_147368_e;
            this.field_147379_i = this.func_147363_d();
            this.field_147378_h = (int)this.field_147379_i;
            this.func_147359_a(new S00PacketKeepAlive(this.field_147378_h));
        }

        if (this.field_147374_l > 0)
        {
            --this.field_147374_l;
        }

        if (this.field_147375_m > 0)
        {
            --this.field_147375_m;
        }

        this.field_147367_d.theProfiler.endStartSection("playerTick");
        this.field_147367_d.theProfiler.endSection();
    }

    public NetworkManager func_147362_b()
    {
        return this.field_147371_a;
    }

    public void func_147360_c(String p_147360_1_)
    {
        final ChatComponentText var2 = new ChatComponentText(p_147360_1_);
        this.field_147371_a.func_150725_a(new S40PacketDisconnect(var2), new GenericFutureListener[] {new GenericFutureListener()
            {
                private static final String __OBFID = "CL_00001453";
                public void operationComplete(Future p_operationComplete_1_)
                {
                    NetHandlerPlayServer.this.field_147371_a.func_150718_a(var2);
                }
            }
        });
        this.field_147371_a.func_150721_g();
    }

    public void func_147358_a(C0CPacketInput p_147358_1_)
    {
        this.field_147369_b.setEntityActionState(p_147358_1_.func_149620_c(), p_147358_1_.func_149616_d(), p_147358_1_.func_149618_e(), p_147358_1_.func_149617_f());
    }

    public void func_147347_a(C03PacketPlayer p_147347_1_)
    {
        WorldServer var2 = this.field_147367_d.worldServerForDimension(this.field_147369_b.dimension);
        this.field_147366_g = true;

        if (!this.field_147369_b.playerConqueredTheEnd)
        {
            double var3;

            if (!this.field_147380_r)
            {
                var3 = p_147347_1_.func_149467_d() - this.field_147382_p;

                if (p_147347_1_.func_149464_c() == this.field_147373_o && var3 * var3 < 0.01D && p_147347_1_.func_149472_e() == this.field_147381_q)
                {
                    this.field_147380_r = true;
                }
            }

            if (this.field_147380_r)
            {
                double var5;
                double var7;
                double var9;

                if (this.field_147369_b.ridingEntity != null)
                {
                    float var34 = this.field_147369_b.rotationYaw;
                    float var4 = this.field_147369_b.rotationPitch;
                    this.field_147369_b.ridingEntity.updateRiderPosition();
                    var5 = this.field_147369_b.posX;
                    var7 = this.field_147369_b.posY;
                    var9 = this.field_147369_b.posZ;

                    if (p_147347_1_.func_149463_k())
                    {
                        var34 = p_147347_1_.func_149462_g();
                        var4 = p_147347_1_.func_149470_h();
                    }

                    this.field_147369_b.onGround = p_147347_1_.func_149465_i();
                    this.field_147369_b.onUpdateEntity();
                    this.field_147369_b.ySize = 0.0F;
                    this.field_147369_b.setPositionAndRotation(var5, var7, var9, var34, var4);

                    if (this.field_147369_b.ridingEntity != null)
                    {
                        this.field_147369_b.ridingEntity.updateRiderPosition();
                    }

                    this.field_147367_d.getConfigurationManager().serverUpdateMountedMovingPlayer(this.field_147369_b);

                    if (this.field_147380_r)
                    {
                        this.field_147373_o = this.field_147369_b.posX;
                        this.field_147382_p = this.field_147369_b.posY;
                        this.field_147381_q = this.field_147369_b.posZ;
                    }

                    var2.updateEntity(this.field_147369_b);
                    return;
                }

                if (this.field_147369_b.isPlayerSleeping())
                {
                    this.field_147369_b.onUpdateEntity();
                    this.field_147369_b.setPositionAndRotation(this.field_147373_o, this.field_147382_p, this.field_147381_q, this.field_147369_b.rotationYaw, this.field_147369_b.rotationPitch);
                    var2.updateEntity(this.field_147369_b);
                    return;
                }

                var3 = this.field_147369_b.posY;
                this.field_147373_o = this.field_147369_b.posX;
                this.field_147382_p = this.field_147369_b.posY;
                this.field_147381_q = this.field_147369_b.posZ;
                var5 = this.field_147369_b.posX;
                var7 = this.field_147369_b.posY;
                var9 = this.field_147369_b.posZ;
                float var11 = this.field_147369_b.rotationYaw;
                float var12 = this.field_147369_b.rotationPitch;

                if (p_147347_1_.func_149466_j() && p_147347_1_.func_149467_d() == -999.0D && p_147347_1_.func_149471_f() == -999.0D)
                {
                    p_147347_1_.func_149469_a(false);
                }

                double var13;

                if (p_147347_1_.func_149466_j())
                {
                    var5 = p_147347_1_.func_149464_c();
                    var7 = p_147347_1_.func_149467_d();
                    var9 = p_147347_1_.func_149472_e();
                    var13 = p_147347_1_.func_149471_f() - p_147347_1_.func_149467_d();

                    if (!this.field_147369_b.isPlayerSleeping() && (var13 > 1.65D || var13 < 0.1D))
                    {
                        this.func_147360_c("Illegal stance");
                        field_147370_c.warn(this.field_147369_b.getCommandSenderName() + " had an illegal stance: " + var13);
                        return;
                    }

                    if (Math.abs(p_147347_1_.func_149464_c()) > 3.2E7D || Math.abs(p_147347_1_.func_149472_e()) > 3.2E7D)
                    {
                        this.func_147360_c("Illegal position");
                        return;
                    }
                }

                if (p_147347_1_.func_149463_k())
                {
                    var11 = p_147347_1_.func_149462_g();
                    var12 = p_147347_1_.func_149470_h();
                }

                this.field_147369_b.onUpdateEntity();
                this.field_147369_b.ySize = 0.0F;
                this.field_147369_b.setPositionAndRotation(this.field_147373_o, this.field_147382_p, this.field_147381_q, var11, var12);

                if (!this.field_147380_r)
                {
                    return;
                }

                var13 = var5 - this.field_147369_b.posX;
                double var15 = var7 - this.field_147369_b.posY;
                double var17 = var9 - this.field_147369_b.posZ;
                double var19 = Math.min(Math.abs(var13), Math.abs(this.field_147369_b.motionX));
                double var21 = Math.min(Math.abs(var15), Math.abs(this.field_147369_b.motionY));
                double var23 = Math.min(Math.abs(var17), Math.abs(this.field_147369_b.motionZ));
                double var25 = var19 * var19 + var21 * var21 + var23 * var23;

                if (var25 > 100.0D && (!this.field_147367_d.isSinglePlayer() || !this.field_147367_d.getServerOwner().equals(this.field_147369_b.getCommandSenderName())))
                {
                    field_147370_c.warn(this.field_147369_b.getCommandSenderName() + " moved too quickly! " + var13 + "," + var15 + "," + var17 + " (" + var19 + ", " + var21 + ", " + var23 + ")");
                    this.func_147364_a(this.field_147373_o, this.field_147382_p, this.field_147381_q, this.field_147369_b.rotationYaw, this.field_147369_b.rotationPitch);
                    return;
                }

                float var27 = 0.0625F;
                boolean var28 = var2.getCollidingBoundingBoxes(this.field_147369_b, this.field_147369_b.boundingBox.copy().contract((double)var27, (double)var27, (double)var27)).isEmpty();

                if (this.field_147369_b.onGround && !p_147347_1_.func_149465_i() && var15 > 0.0D)
                {
                    this.field_147369_b.jump();
                }

                this.field_147369_b.moveEntity(var13, var15, var17);
                this.field_147369_b.onGround = p_147347_1_.func_149465_i();
                this.field_147369_b.addMovementStat(var13, var15, var17);
                double var29 = var15;
                var13 = var5 - this.field_147369_b.posX;
                var15 = var7 - this.field_147369_b.posY;

                if (var15 > -0.5D || var15 < 0.5D)
                {
                    var15 = 0.0D;
                }

                var17 = var9 - this.field_147369_b.posZ;
                var25 = var13 * var13 + var15 * var15 + var17 * var17;
                boolean var31 = false;

                if (var25 > 0.0625D && !this.field_147369_b.isPlayerSleeping() && !this.field_147369_b.theItemInWorldManager.isCreative())
                {
                    var31 = true;
                    field_147370_c.warn(this.field_147369_b.getCommandSenderName() + " moved wrongly!");
                }

                this.field_147369_b.setPositionAndRotation(var5, var7, var9, var11, var12);
                boolean var32 = var2.getCollidingBoundingBoxes(this.field_147369_b, this.field_147369_b.boundingBox.copy().contract((double)var27, (double)var27, (double)var27)).isEmpty();

                if (var28 && (var31 || !var32) && !this.field_147369_b.isPlayerSleeping())
                {
                    this.func_147364_a(this.field_147373_o, this.field_147382_p, this.field_147381_q, var11, var12);
                    return;
                }

                AxisAlignedBB var33 = this.field_147369_b.boundingBox.copy().expand((double)var27, (double)var27, (double)var27).addCoord(0.0D, -0.55D, 0.0D);

                if (!this.field_147367_d.isFlightAllowed() && !this.field_147369_b.theItemInWorldManager.isCreative() && !var2.checkBlockCollision(var33))
                {
                    if (var29 >= -0.03125D)
                    {
                        ++this.field_147365_f;

                        if (this.field_147365_f > 80)
                        {
                            field_147370_c.warn(this.field_147369_b.getCommandSenderName() + " was kicked for floating too long!");
                            this.func_147360_c("Flying is not enabled on this server");
                            return;
                        }
                    }
                }
                else
                {
                    this.field_147365_f = 0;
                }

                this.field_147369_b.onGround = p_147347_1_.func_149465_i();
                this.field_147367_d.getConfigurationManager().serverUpdateMountedMovingPlayer(this.field_147369_b);
                this.field_147369_b.updateFlyingState(this.field_147369_b.posY - var3, p_147347_1_.func_149465_i());
            }
            else if (this.field_147368_e % 20 == 0)
            {
                this.func_147364_a(this.field_147373_o, this.field_147382_p, this.field_147381_q, this.field_147369_b.rotationYaw, this.field_147369_b.rotationPitch);
            }
        }
    }

    public void func_147364_a(double p_147364_1_, double p_147364_3_, double p_147364_5_, float p_147364_7_, float p_147364_8_)
    {
        this.field_147380_r = false;
        this.field_147373_o = p_147364_1_;
        this.field_147382_p = p_147364_3_;
        this.field_147381_q = p_147364_5_;
        this.field_147369_b.setPositionAndRotation(p_147364_1_, p_147364_3_, p_147364_5_, p_147364_7_, p_147364_8_);
        this.field_147369_b.playerNetServerHandler.func_147359_a(new S08PacketPlayerPosLook(p_147364_1_, p_147364_3_ + 1.6200000047683716D, p_147364_5_, p_147364_7_, p_147364_8_, false));
    }

    public void func_147345_a(C07PacketPlayerDigging p_147345_1_)
    {
        WorldServer var2 = this.field_147367_d.worldServerForDimension(this.field_147369_b.dimension);
        this.field_147369_b.func_143004_u();

        if (p_147345_1_.func_149506_g() == 4)
        {
            this.field_147369_b.dropOneItem(false);
        }
        else if (p_147345_1_.func_149506_g() == 3)
        {
            this.field_147369_b.dropOneItem(true);
        }
        else if (p_147345_1_.func_149506_g() == 5)
        {
            this.field_147369_b.stopUsingItem();
        }
        else
        {
            boolean var3 = false;

            if (p_147345_1_.func_149506_g() == 0)
            {
                var3 = true;
            }

            if (p_147345_1_.func_149506_g() == 1)
            {
                var3 = true;
            }

            if (p_147345_1_.func_149506_g() == 2)
            {
                var3 = true;
            }

            int var4 = p_147345_1_.func_149505_c();
            int var5 = p_147345_1_.func_149503_d();
            int var6 = p_147345_1_.func_149502_e();

            if (var3)
            {
                double var7 = this.field_147369_b.posX - ((double)var4 + 0.5D);
                double var9 = this.field_147369_b.posY - ((double)var5 + 0.5D) + 1.5D;
                double var11 = this.field_147369_b.posZ - ((double)var6 + 0.5D);
                double var13 = var7 * var7 + var9 * var9 + var11 * var11;

                if (var13 > 36.0D)
                {
                    return;
                }

                if (var5 >= this.field_147367_d.getBuildLimit())
                {
                    return;
                }
            }

            if (p_147345_1_.func_149506_g() == 0)
            {
                if (!this.field_147367_d.isBlockProtected(var2, var4, var5, var6, this.field_147369_b))
                {
                    this.field_147369_b.theItemInWorldManager.onBlockClicked(var4, var5, var6, p_147345_1_.func_149501_f());
                }
                else
                {
                    this.field_147369_b.playerNetServerHandler.func_147359_a(new S23PacketBlockChange(var4, var5, var6, var2));
                }
            }
            else if (p_147345_1_.func_149506_g() == 2)
            {
                this.field_147369_b.theItemInWorldManager.uncheckedTryHarvestBlock(var4, var5, var6);

                if (var2.func_147439_a(var4, var5, var6).func_149688_o() != Material.field_151579_a)
                {
                    this.field_147369_b.playerNetServerHandler.func_147359_a(new S23PacketBlockChange(var4, var5, var6, var2));
                }
            }
            else if (p_147345_1_.func_149506_g() == 1)
            {
                this.field_147369_b.theItemInWorldManager.cancelDestroyingBlock(var4, var5, var6);

                if (var2.func_147439_a(var4, var5, var6).func_149688_o() != Material.field_151579_a)
                {
                    this.field_147369_b.playerNetServerHandler.func_147359_a(new S23PacketBlockChange(var4, var5, var6, var2));
                }
            }
        }
    }

    public void func_147346_a(C08PacketPlayerBlockPlacement p_147346_1_)
    {
        WorldServer var2 = this.field_147367_d.worldServerForDimension(this.field_147369_b.dimension);
        ItemStack var3 = this.field_147369_b.inventory.getCurrentItem();
        boolean var4 = false;
        int var5 = p_147346_1_.func_149576_c();
        int var6 = p_147346_1_.func_149571_d();
        int var7 = p_147346_1_.func_149570_e();
        int var8 = p_147346_1_.func_149568_f();
        this.field_147369_b.func_143004_u();

        if (p_147346_1_.func_149568_f() == 255)
        {
            if (var3 == null)
            {
                return;
            }

            this.field_147369_b.theItemInWorldManager.tryUseItem(this.field_147369_b, var2, var3);
        }
        else if (p_147346_1_.func_149571_d() >= this.field_147367_d.getBuildLimit() - 1 && (p_147346_1_.func_149568_f() == 1 || p_147346_1_.func_149571_d() >= this.field_147367_d.getBuildLimit()))
        {
            ChatComponentTranslation var9 = new ChatComponentTranslation("build.tooHigh", new Object[] {Integer.valueOf(this.field_147367_d.getBuildLimit())});
            var9.func_150256_b().func_150238_a(EnumChatFormatting.RED);
            this.field_147369_b.playerNetServerHandler.func_147359_a(new S02PacketChat(var9));
            var4 = true;
        }
        else
        {
            if (this.field_147380_r && this.field_147369_b.getDistanceSq((double)var5 + 0.5D, (double)var6 + 0.5D, (double)var7 + 0.5D) < 64.0D && !this.field_147367_d.isBlockProtected(var2, var5, var6, var7, this.field_147369_b))
            {
                this.field_147369_b.theItemInWorldManager.activateBlockOrUseItem(this.field_147369_b, var2, var3, var5, var6, var7, var8, p_147346_1_.func_149573_h(), p_147346_1_.func_149569_i(), p_147346_1_.func_149575_j());
            }

            var4 = true;
        }

        if (var4)
        {
            this.field_147369_b.playerNetServerHandler.func_147359_a(new S23PacketBlockChange(var5, var6, var7, var2));

            if (var8 == 0)
            {
                --var6;
            }

            if (var8 == 1)
            {
                ++var6;
            }

            if (var8 == 2)
            {
                --var7;
            }

            if (var8 == 3)
            {
                ++var7;
            }

            if (var8 == 4)
            {
                --var5;
            }

            if (var8 == 5)
            {
                ++var5;
            }

            this.field_147369_b.playerNetServerHandler.func_147359_a(new S23PacketBlockChange(var5, var6, var7, var2));
        }

        var3 = this.field_147369_b.inventory.getCurrentItem();

        if (var3 != null && var3.stackSize == 0)
        {
            this.field_147369_b.inventory.mainInventory[this.field_147369_b.inventory.currentItem] = null;
            var3 = null;
        }

        if (var3 == null || var3.getMaxItemUseDuration() == 0)
        {
            this.field_147369_b.playerInventoryBeingManipulated = true;
            this.field_147369_b.inventory.mainInventory[this.field_147369_b.inventory.currentItem] = ItemStack.copyItemStack(this.field_147369_b.inventory.mainInventory[this.field_147369_b.inventory.currentItem]);
            Slot var10 = this.field_147369_b.openContainer.getSlotFromInventory(this.field_147369_b.inventory, this.field_147369_b.inventory.currentItem);
            this.field_147369_b.openContainer.detectAndSendChanges();
            this.field_147369_b.playerInventoryBeingManipulated = false;

            if (!ItemStack.areItemStacksEqual(this.field_147369_b.inventory.getCurrentItem(), p_147346_1_.func_149574_g()))
            {
                this.func_147359_a(new S2FPacketSetSlot(this.field_147369_b.openContainer.windowId, var10.slotNumber, this.field_147369_b.inventory.getCurrentItem()));
            }
        }
    }

    public void func_147231_a(IChatComponent p_147231_1_)
    {
        field_147370_c.info(this.field_147369_b.getCommandSenderName() + " lost connection: " + p_147231_1_);
        this.field_147367_d.func_147132_au();
        ChatComponentTranslation var2 = new ChatComponentTranslation("multiplayer.player.left", new Object[] {this.field_147369_b.func_145748_c_()});
        var2.func_150256_b().func_150238_a(EnumChatFormatting.YELLOW);
        this.field_147367_d.getConfigurationManager().func_148539_a(var2);
        this.field_147369_b.mountEntityAndWakeUp();
        this.field_147367_d.getConfigurationManager().playerLoggedOut(this.field_147369_b);

        if (this.field_147367_d.isSinglePlayer() && this.field_147369_b.getCommandSenderName().equals(this.field_147367_d.getServerOwner()))
        {
            field_147370_c.info("Stopping singleplayer server as player logged out");
            this.field_147367_d.initiateShutdown();
        }
    }

    public void func_147359_a(final Packet p_147359_1_)
    {
        if (p_147359_1_ instanceof S02PacketChat)
        {
            S02PacketChat var2 = (S02PacketChat)p_147359_1_;
            EntityPlayer.EnumChatVisibility var3 = this.field_147369_b.func_147096_v();

            if (var3 == EntityPlayer.EnumChatVisibility.HIDDEN)
            {
                return;
            }

            if (var3 == EntityPlayer.EnumChatVisibility.SYSTEM && !var2.func_148916_d())
            {
                return;
            }
        }

        try
        {
            this.field_147371_a.func_150725_a(p_147359_1_, new GenericFutureListener[0]);
        }
        catch (Throwable var5)
        {
            CrashReport var6 = CrashReport.makeCrashReport(var5, "Sending packet");
            CrashReportCategory var4 = var6.makeCategory("Packet being sent");
            var4.addCrashSectionCallable("Packet class", new Callable()
            {
                private static final String __OBFID = "CL_00001454";
                public String call()
                {
                    return p_147359_1_.getClass().getCanonicalName();
                }
            });
            throw new ReportedException(var6);
        }
    }

    public void func_147355_a(C09PacketHeldItemChange p_147355_1_)
    {
        if (p_147355_1_.func_149614_c() >= 0 && p_147355_1_.func_149614_c() < InventoryPlayer.getHotbarSize())
        {
            this.field_147369_b.inventory.currentItem = p_147355_1_.func_149614_c();
            this.field_147369_b.func_143004_u();
        }
        else
        {
            field_147370_c.warn(this.field_147369_b.getCommandSenderName() + " tried to set an invalid carried item");
        }
    }

    public void func_147354_a(C01PacketChatMessage p_147354_1_)
    {
        if (this.field_147369_b.func_147096_v() == EntityPlayer.EnumChatVisibility.HIDDEN)
        {
            ChatComponentTranslation var4 = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
            var4.func_150256_b().func_150238_a(EnumChatFormatting.RED);
            this.func_147359_a(new S02PacketChat(var4));
        }
        else
        {
            this.field_147369_b.func_143004_u();
            String var2 = p_147354_1_.func_149439_c();
            var2 = StringUtils.normalizeSpace(var2);

            for (int var3 = 0; var3 < var2.length(); ++var3)
            {
                if (!ChatAllowedCharacters.isAllowedCharacter(var2.charAt(var3)))
                {
                    this.func_147360_c("Illegal characters in chat");
                    return;
                }
            }

            if (var2.startsWith("/"))
            {
                this.func_147361_d(var2);
            }
            else
            {
                ChatComponentTranslation var5 = new ChatComponentTranslation("chat.type.text", new Object[] {this.field_147369_b.func_145748_c_(), var2});
                this.field_147367_d.getConfigurationManager().func_148544_a(var5, false);
            }

            this.field_147374_l += 20;

            if (this.field_147374_l > 200 && !this.field_147367_d.getConfigurationManager().isPlayerOpped(this.field_147369_b.getCommandSenderName()))
            {
                this.func_147360_c("disconnect.spam");
            }
        }
    }

    private void func_147361_d(String p_147361_1_)
    {
        this.field_147367_d.getCommandManager().executeCommand(this.field_147369_b, p_147361_1_);
    }

    public void func_147350_a(C0APacketAnimation p_147350_1_)
    {
        this.field_147369_b.func_143004_u();

        if (p_147350_1_.func_149421_d() == 1)
        {
            this.field_147369_b.swingItem();
        }
    }

    public void func_147357_a(C0BPacketEntityAction p_147357_1_)
    {
        this.field_147369_b.func_143004_u();

        if (p_147357_1_.func_149513_d() == 1)
        {
            this.field_147369_b.setSneaking(true);
        }
        else if (p_147357_1_.func_149513_d() == 2)
        {
            this.field_147369_b.setSneaking(false);
        }
        else if (p_147357_1_.func_149513_d() == 4)
        {
            this.field_147369_b.setSprinting(true);
        }
        else if (p_147357_1_.func_149513_d() == 5)
        {
            this.field_147369_b.setSprinting(false);
        }
        else if (p_147357_1_.func_149513_d() == 3)
        {
            this.field_147369_b.wakeUpPlayer(false, true, true);
            this.field_147380_r = false;
        }
        else if (p_147357_1_.func_149513_d() == 6)
        {
            if (this.field_147369_b.ridingEntity != null && this.field_147369_b.ridingEntity instanceof EntityHorse)
            {
                ((EntityHorse)this.field_147369_b.ridingEntity).setJumpPower(p_147357_1_.func_149512_e());
            }
        }
        else if (p_147357_1_.func_149513_d() == 7 && this.field_147369_b.ridingEntity != null && this.field_147369_b.ridingEntity instanceof EntityHorse)
        {
            ((EntityHorse)this.field_147369_b.ridingEntity).openGUI(this.field_147369_b);
        }
    }

    public void func_147340_a(C02PacketUseEntity p_147340_1_)
    {
        WorldServer var2 = this.field_147367_d.worldServerForDimension(this.field_147369_b.dimension);
        Entity var3 = p_147340_1_.func_149564_a(var2);
        this.field_147369_b.func_143004_u();

        if (var3 != null)
        {
            boolean var4 = this.field_147369_b.canEntityBeSeen(var3);
            double var5 = 36.0D;

            if (!var4)
            {
                var5 = 9.0D;
            }

            if (this.field_147369_b.getDistanceSqToEntity(var3) < var5)
            {
                if (p_147340_1_.func_149565_c() == C02PacketUseEntity.Action.INTERACT)
                {
                    this.field_147369_b.interactWith(var3);
                }
                else if (p_147340_1_.func_149565_c() == C02PacketUseEntity.Action.ATTACK)
                {
                    if (var3 instanceof EntityItem || var3 instanceof EntityXPOrb || var3 instanceof EntityArrow || var3 == this.field_147369_b)
                    {
                        this.func_147360_c("Attempting to attack an invalid entity");
                        this.field_147367_d.logWarning("Player " + this.field_147369_b.getCommandSenderName() + " tried to attack an invalid entity");
                        return;
                    }

                    this.field_147369_b.attackTargetEntityWithCurrentItem(var3);
                }
            }
        }
    }

    public void func_147342_a(C16PacketClientStatus p_147342_1_)
    {
        this.field_147369_b.func_143004_u();
        C16PacketClientStatus.EnumState var2 = p_147342_1_.func_149435_c();

        switch (NetHandlerPlayServer.SwitchEnumState.field_151290_a[var2.ordinal()])
        {
            case 1:
                if (this.field_147369_b.playerConqueredTheEnd)
                {
                    this.field_147369_b = this.field_147367_d.getConfigurationManager().respawnPlayer(this.field_147369_b, 0, true);
                }
                else if (this.field_147369_b.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled())
                {
                    if (this.field_147367_d.isSinglePlayer() && this.field_147369_b.getCommandSenderName().equals(this.field_147367_d.getServerOwner()))
                    {
                        this.field_147369_b.playerNetServerHandler.func_147360_c("You have died. Game over, man, it\'s game over!");
                        this.field_147367_d.deleteWorldAndStopServer();
                    }
                    else
                    {
                        BanEntry var3 = new BanEntry(this.field_147369_b.getCommandSenderName());
                        var3.setBanReason("Death in Hardcore");
                        this.field_147367_d.getConfigurationManager().getBannedPlayers().put(var3);
                        this.field_147369_b.playerNetServerHandler.func_147360_c("You have died. Game over, man, it\'s game over!");
                    }
                }
                else
                {
                    if (this.field_147369_b.getHealth() > 0.0F)
                    {
                        return;
                    }

                    this.field_147369_b = this.field_147367_d.getConfigurationManager().respawnPlayer(this.field_147369_b, 0, false);
                }

                break;

            case 2:
                this.field_147369_b.func_147099_x().func_150876_a(this.field_147369_b);
                break;

            case 3:
                this.field_147369_b.triggerAchievement(AchievementList.openInventory);
        }
    }

    public void func_147356_a(C0DPacketCloseWindow p_147356_1_)
    {
        this.field_147369_b.closeContainer();
    }

    public void func_147351_a(C0EPacketClickWindow p_147351_1_)
    {
        this.field_147369_b.func_143004_u();

        if (this.field_147369_b.openContainer.windowId == p_147351_1_.func_149548_c() && this.field_147369_b.openContainer.isPlayerNotUsingContainer(this.field_147369_b))
        {
            ItemStack var2 = this.field_147369_b.openContainer.slotClick(p_147351_1_.func_149544_d(), p_147351_1_.func_149543_e(), p_147351_1_.func_149542_h(), this.field_147369_b);

            if (ItemStack.areItemStacksEqual(p_147351_1_.func_149546_g(), var2))
            {
                this.field_147369_b.playerNetServerHandler.func_147359_a(new S32PacketConfirmTransaction(p_147351_1_.func_149548_c(), p_147351_1_.func_149547_f(), true));
                this.field_147369_b.playerInventoryBeingManipulated = true;
                this.field_147369_b.openContainer.detectAndSendChanges();
                this.field_147369_b.updateHeldItem();
                this.field_147369_b.playerInventoryBeingManipulated = false;
            }
            else
            {
                this.field_147372_n.addKey(this.field_147369_b.openContainer.windowId, Short.valueOf(p_147351_1_.func_149547_f()));
                this.field_147369_b.playerNetServerHandler.func_147359_a(new S32PacketConfirmTransaction(p_147351_1_.func_149548_c(), p_147351_1_.func_149547_f(), false));
                this.field_147369_b.openContainer.setPlayerIsPresent(this.field_147369_b, false);
                ArrayList var3 = new ArrayList();

                for (int var4 = 0; var4 < this.field_147369_b.openContainer.inventorySlots.size(); ++var4)
                {
                    var3.add(((Slot)this.field_147369_b.openContainer.inventorySlots.get(var4)).getStack());
                }

                this.field_147369_b.sendContainerAndContentsToPlayer(this.field_147369_b.openContainer, var3);
            }
        }
    }

    public void func_147338_a(C11PacketEnchantItem p_147338_1_)
    {
        this.field_147369_b.func_143004_u();

        if (this.field_147369_b.openContainer.windowId == p_147338_1_.func_149539_c() && this.field_147369_b.openContainer.isPlayerNotUsingContainer(this.field_147369_b))
        {
            this.field_147369_b.openContainer.enchantItem(this.field_147369_b, p_147338_1_.func_149537_d());
            this.field_147369_b.openContainer.detectAndSendChanges();
        }
    }

    public void func_147344_a(C10PacketCreativeInventoryAction p_147344_1_)
    {
        if (this.field_147369_b.theItemInWorldManager.isCreative())
        {
            boolean var2 = p_147344_1_.func_149627_c() < 0;
            ItemStack var3 = p_147344_1_.func_149625_d();
            boolean var4 = p_147344_1_.func_149627_c() >= 1 && p_147344_1_.func_149627_c() < 36 + InventoryPlayer.getHotbarSize();
            boolean var5 = var3 == null || var3.getItem() != null;
            boolean var6 = var3 == null || var3.getItemDamage() >= 0 && var3.stackSize <= 64 && var3.stackSize > 0;

            if (var4 && var5 && var6)
            {
                if (var3 == null)
                {
                    this.field_147369_b.inventoryContainer.putStackInSlot(p_147344_1_.func_149627_c(), (ItemStack)null);
                }
                else
                {
                    this.field_147369_b.inventoryContainer.putStackInSlot(p_147344_1_.func_149627_c(), var3);
                }

                this.field_147369_b.inventoryContainer.setPlayerIsPresent(this.field_147369_b, true);
            }
            else if (var2 && var5 && var6 && this.field_147375_m < 200)
            {
                this.field_147375_m += 20;
                EntityItem var7 = this.field_147369_b.dropPlayerItemWithRandomChoice(var3, true);

                if (var7 != null)
                {
                    var7.setAgeToCreativeDespawnTime();
                }
            }
        }
    }

    public void func_147339_a(C0FPacketConfirmTransaction p_147339_1_)
    {
        Short var2 = (Short)this.field_147372_n.lookup(this.field_147369_b.openContainer.windowId);

        if (var2 != null && p_147339_1_.func_149533_d() == var2.shortValue() && this.field_147369_b.openContainer.windowId == p_147339_1_.func_149532_c() && !this.field_147369_b.openContainer.isPlayerNotUsingContainer(this.field_147369_b))
        {
            this.field_147369_b.openContainer.setPlayerIsPresent(this.field_147369_b, true);
        }
    }

    public void func_147343_a(C12PacketUpdateSign p_147343_1_)
    {
        this.field_147369_b.func_143004_u();
        WorldServer var2 = this.field_147367_d.worldServerForDimension(this.field_147369_b.dimension);

        if (var2.blockExists(p_147343_1_.func_149588_c(), p_147343_1_.func_149586_d(), p_147343_1_.func_149585_e()))
        {
            TileEntity var3 = var2.func_147438_o(p_147343_1_.func_149588_c(), p_147343_1_.func_149586_d(), p_147343_1_.func_149585_e());

            if (var3 instanceof TileEntitySign)
            {
                TileEntitySign var4 = (TileEntitySign)var3;

                if (!var4.func_145914_a() || var4.func_145911_b() != this.field_147369_b)
                {
                    this.field_147367_d.logWarning("Player " + this.field_147369_b.getCommandSenderName() + " just tried to change non-editable sign");
                    return;
                }
            }

            int var6;
            int var8;

            for (var8 = 0; var8 < 4; ++var8)
            {
                boolean var5 = true;

                if (p_147343_1_.func_149589_f()[var8].length() > 15)
                {
                    var5 = false;
                }
                else
                {
                    for (var6 = 0; var6 < p_147343_1_.func_149589_f()[var8].length(); ++var6)
                    {
                        if (!ChatAllowedCharacters.isAllowedCharacter(p_147343_1_.func_149589_f()[var8].charAt(var6)))
                        {
                            var5 = false;
                        }
                    }
                }

                if (!var5)
                {
                    p_147343_1_.func_149589_f()[var8] = "!?";
                }
            }

            if (var3 instanceof TileEntitySign)
            {
                var8 = p_147343_1_.func_149588_c();
                int var9 = p_147343_1_.func_149586_d();
                var6 = p_147343_1_.func_149585_e();
                TileEntitySign var7 = (TileEntitySign)var3;
                System.arraycopy(p_147343_1_.func_149589_f(), 0, var7.field_145915_a, 0, 4);
                var7.onInventoryChanged();
                var2.func_147471_g(var8, var9, var6);
            }
        }
    }

    public void func_147353_a(C00PacketKeepAlive p_147353_1_)
    {
        if (p_147353_1_.func_149460_c() == this.field_147378_h)
        {
            int var2 = (int)(this.func_147363_d() - this.field_147379_i);
            this.field_147369_b.ping = (this.field_147369_b.ping * 3 + var2) / 4;
        }
    }

    private long func_147363_d()
    {
        return System.nanoTime() / 1000000L;
    }

    public void func_147348_a(C13PacketPlayerAbilities p_147348_1_)
    {
        this.field_147369_b.capabilities.isFlying = p_147348_1_.func_149488_d() && this.field_147369_b.capabilities.allowFlying;
    }

    public void func_147341_a(C14PacketTabComplete p_147341_1_)
    {
        ArrayList var2 = Lists.newArrayList();
        Iterator var3 = this.field_147367_d.getPossibleCompletions(this.field_147369_b, p_147341_1_.func_149419_c()).iterator();

        while (var3.hasNext())
        {
            String var4 = (String)var3.next();
            var2.add(var4);
        }

        this.field_147369_b.playerNetServerHandler.func_147359_a(new S3APacketTabComplete((String[])var2.toArray(new String[var2.size()])));
    }

    public void func_147352_a(C15PacketClientSettings p_147352_1_)
    {
        this.field_147369_b.func_147100_a(p_147352_1_);
    }

    public void func_147349_a(C17PacketCustomPayload p_147349_1_)
    {
        ItemStack var2;
        ItemStack var3;

        if ("MC|BEdit".equals(p_147349_1_.func_149559_c()))
        {
            try
            {
                var2 = (new PacketBuffer(Unpooled.wrappedBuffer(p_147349_1_.func_149558_e()))).func_150791_c();

                if (!ItemWritableBook.func_150930_a(var2.getTagCompound()))
                {
                    throw new IOException("Invalid book tag!");
                }

                var3 = this.field_147369_b.inventory.getCurrentItem();

                if (var2.getItem() == Items.field_151099_bA && var2.getItem() == var3.getItem())
                {
                    var3.setTagInfo("pages", var2.getTagCompound().func_150295_c("pages", 8));
                }
            }
            catch (Exception var12)
            {
                field_147370_c.error("Couldn\'t handle book info", var12);
            }
        }
        else if ("MC|BSign".equals(p_147349_1_.func_149559_c()))
        {
            try
            {
                var2 = (new PacketBuffer(Unpooled.wrappedBuffer(p_147349_1_.func_149558_e()))).func_150791_c();

                if (!ItemEditableBook.validBookTagContents(var2.getTagCompound()))
                {
                    throw new IOException("Invalid book tag!");
                }

                var3 = this.field_147369_b.inventory.getCurrentItem();

                if (var2.getItem() == Items.field_151164_bB && var3.getItem() == Items.field_151099_bA)
                {
                    var3.setTagInfo("author", new NBTTagString(this.field_147369_b.getCommandSenderName()));
                    var3.setTagInfo("title", new NBTTagString(var2.getTagCompound().getString("title")));
                    var3.setTagInfo("pages", var2.getTagCompound().func_150295_c("pages", 8));
                    var3.func_150996_a(Items.field_151164_bB);
                }
            }
            catch (Exception var11)
            {
                field_147370_c.error("Couldn\'t sign book", var11);
            }
        }
        else
        {
            DataInputStream var13;
            int var15;

            if ("MC|TrSel".equals(p_147349_1_.func_149559_c()))
            {
                try
                {
                    var13 = new DataInputStream(new ByteArrayInputStream(p_147349_1_.func_149558_e()));
                    var15 = var13.readInt();
                    Container var4 = this.field_147369_b.openContainer;

                    if (var4 instanceof ContainerMerchant)
                    {
                        ((ContainerMerchant)var4).setCurrentRecipeIndex(var15);
                    }
                }
                catch (Exception var10)
                {
                    field_147370_c.error("Couldn\'t select trade", var10);
                }
            }
            else if ("MC|AdvCdm".equals(p_147349_1_.func_149559_c()))
            {
                if (!this.field_147367_d.isCommandBlockEnabled())
                {
                    this.field_147369_b.func_145747_a(new ChatComponentTranslation("advMode.notEnabled", new Object[0]));
                }
                else if (this.field_147369_b.canCommandSenderUseCommand(2, "") && this.field_147369_b.capabilities.isCreativeMode)
                {
                    try
                    {
                        PacketBuffer var14 = new PacketBuffer(Unpooled.wrappedBuffer(p_147349_1_.func_149558_e()));
                        byte var17 = var14.readByte();
                        CommandBlockLogic var18 = null;

                        if (var17 == 0)
                        {
                            TileEntity var5 = this.field_147369_b.worldObj.func_147438_o(var14.readInt(), var14.readInt(), var14.readInt());

                            if (var5 instanceof TileEntityCommandBlock)
                            {
                                var18 = ((TileEntityCommandBlock)var5).func_145993_a();
                            }
                        }
                        else if (var17 == 1)
                        {
                            Entity var20 = this.field_147369_b.worldObj.getEntityByID(var14.readInt());

                            if (var20 instanceof EntityMinecartCommandBlock)
                            {
                                var18 = ((EntityMinecartCommandBlock)var20).func_145822_e();
                            }
                        }

                        String var22 = var14.func_150789_c(var14.readableBytes());

                        if (var18 != null)
                        {
                            var18.func_145752_a(var22);
                            var18.func_145756_e();
                            this.field_147369_b.func_145747_a(new ChatComponentTranslation("advMode.setCommand.success", new Object[] {var22}));
                        }
                    }
                    catch (Exception var9)
                    {
                        field_147370_c.error("Couldn\'t set command block", var9);
                    }
                }
                else
                {
                    this.field_147369_b.func_145747_a(new ChatComponentTranslation("advMode.notAllowed", new Object[0]));
                }
            }
            else if ("MC|Beacon".equals(p_147349_1_.func_149559_c()))
            {
                if (this.field_147369_b.openContainer instanceof ContainerBeacon)
                {
                    try
                    {
                        var13 = new DataInputStream(new ByteArrayInputStream(p_147349_1_.func_149558_e()));
                        var15 = var13.readInt();
                        int var21 = var13.readInt();
                        ContainerBeacon var23 = (ContainerBeacon)this.field_147369_b.openContainer;
                        Slot var6 = var23.getSlot(0);

                        if (var6.getHasStack())
                        {
                            var6.decrStackSize(1);
                            TileEntityBeacon var7 = var23.func_148327_e();
                            var7.func_146001_d(var15);
                            var7.func_146004_e(var21);
                            var7.onInventoryChanged();
                        }
                    }
                    catch (Exception var8)
                    {
                        field_147370_c.error("Couldn\'t set beacon", var8);
                    }
                }
            }
            else if ("MC|ItemName".equals(p_147349_1_.func_149559_c()) && this.field_147369_b.openContainer instanceof ContainerRepair)
            {
                ContainerRepair var16 = (ContainerRepair)this.field_147369_b.openContainer;

                if (p_147349_1_.func_149558_e() != null && p_147349_1_.func_149558_e().length >= 1)
                {
                    String var19 = ChatAllowedCharacters.filerAllowedCharacters(new String(p_147349_1_.func_149558_e(), Charsets.UTF_8));

                    if (var19.length() <= 30)
                    {
                        var16.updateItemName(var19);
                    }
                }
                else
                {
                    var16.updateItemName("");
                }
            }
        }
    }

    public void func_147232_a(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_)
    {
        if (p_147232_2_ != EnumConnectionState.PLAY)
        {
            throw new IllegalStateException("Unexpected change in protocol!");
        }
    }

    static final class SwitchEnumState
    {
        static final int[] field_151290_a = new int[C16PacketClientStatus.EnumState.values().length];
        private static final String __OBFID = "CL_00001455";

        static
        {
            try
            {
                field_151290_a[C16PacketClientStatus.EnumState.PERFORM_RESPAWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                field_151290_a[C16PacketClientStatus.EnumState.REQUEST_STATS.ordinal()] = 2;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                field_151290_a[C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT.ordinal()] = 3;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }
        }
    }
}
