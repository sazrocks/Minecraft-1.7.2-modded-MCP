package net.minecraft.tileentity;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityChest extends TileEntity implements IInventory
{
    private ItemStack[] field_145985_p = new ItemStack[36];
    public boolean field_145984_a;
    public TileEntityChest field_145992_i;
    public TileEntityChest field_145990_j;
    public TileEntityChest field_145991_k;
    public TileEntityChest field_145988_l;
    public float field_145989_m;
    public float field_145986_n;
    public int field_145987_o;
    private int field_145983_q;
    private int field_145982_r;
    private String field_145981_s;
    private static final String __OBFID = "CL_00000346";

    public TileEntityChest()
    {
        this.field_145982_r = -1;
    }

    public TileEntityChest(int par1)
    {
        this.field_145982_r = par1;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 27;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.field_145985_p[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.field_145985_p[par1] != null)
        {
            ItemStack var3;

            if (this.field_145985_p[par1].stackSize <= par2)
            {
                var3 = this.field_145985_p[par1];
                this.field_145985_p[par1] = null;
                this.onInventoryChanged();
                return var3;
            }
            else
            {
                var3 = this.field_145985_p[par1].splitStack(par2);

                if (this.field_145985_p[par1].stackSize == 0)
                {
                    this.field_145985_p[par1] = null;
                }

                this.onInventoryChanged();
                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.field_145985_p[par1] != null)
        {
            ItemStack var2 = this.field_145985_p[par1];
            this.field_145985_p[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.field_145985_p[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

    public String func_145825_b()
    {
        return this.func_145818_k_() ? this.field_145981_s : "container.chest";
    }

    public boolean func_145818_k_()
    {
        return this.field_145981_s != null && this.field_145981_s.length() > 0;
    }

    public void func_145976_a(String p_145976_1_)
    {
        this.field_145981_s = p_145976_1_;
    }

    public void func_145839_a(NBTTagCompound p_145839_1_)
    {
        super.func_145839_a(p_145839_1_);
        NBTTagList var2 = p_145839_1_.func_150295_c("Items", 10);
        this.field_145985_p = new ItemStack[this.getSizeInventory()];

        if (p_145839_1_.func_150297_b("CustomName", 8))
        {
            this.field_145981_s = p_145839_1_.getString("CustomName");
        }

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = var2.func_150305_b(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < this.field_145985_p.length)
            {
                this.field_145985_p[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }

    public void func_145841_b(NBTTagCompound p_145841_1_)
    {
        super.func_145841_b(p_145841_1_);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.field_145985_p.length; ++var3)
        {
            if (this.field_145985_p[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.field_145985_p[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        p_145841_1_.setTag("Items", var2);

        if (this.func_145818_k_())
        {
            p_145841_1_.setString("CustomName", this.field_145981_s);
        }
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) != this ? false : par1EntityPlayer.getDistanceSq((double)this.field_145851_c + 0.5D, (double)this.field_145848_d + 0.5D, (double)this.field_145849_e + 0.5D) <= 64.0D;
    }

    public void func_145836_u()
    {
        super.func_145836_u();
        this.field_145984_a = false;
    }

    private void func_145978_a(TileEntityChest p_145978_1_, int p_145978_2_)
    {
        if (p_145978_1_.func_145837_r())
        {
            this.field_145984_a = false;
        }
        else if (this.field_145984_a)
        {
            switch (p_145978_2_)
            {
                case 0:
                    if (this.field_145988_l != p_145978_1_)
                    {
                        this.field_145984_a = false;
                    }

                    break;

                case 1:
                    if (this.field_145991_k != p_145978_1_)
                    {
                        this.field_145984_a = false;
                    }

                    break;

                case 2:
                    if (this.field_145992_i != p_145978_1_)
                    {
                        this.field_145984_a = false;
                    }

                    break;

                case 3:
                    if (this.field_145990_j != p_145978_1_)
                    {
                        this.field_145984_a = false;
                    }
            }
        }
    }

    public void func_145979_i()
    {
        if (!this.field_145984_a)
        {
            this.field_145984_a = true;
            this.field_145992_i = null;
            this.field_145990_j = null;
            this.field_145991_k = null;
            this.field_145988_l = null;

            if (this.func_145977_a(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e))
            {
                this.field_145991_k = (TileEntityChest)this.field_145850_b.func_147438_o(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e);
            }

            if (this.func_145977_a(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e))
            {
                this.field_145990_j = (TileEntityChest)this.field_145850_b.func_147438_o(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e);
            }

            if (this.func_145977_a(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1))
            {
                this.field_145992_i = (TileEntityChest)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1);
            }

            if (this.func_145977_a(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1))
            {
                this.field_145988_l = (TileEntityChest)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1);
            }

            if (this.field_145992_i != null)
            {
                this.field_145992_i.func_145978_a(this, 0);
            }

            if (this.field_145988_l != null)
            {
                this.field_145988_l.func_145978_a(this, 2);
            }

            if (this.field_145990_j != null)
            {
                this.field_145990_j.func_145978_a(this, 1);
            }

            if (this.field_145991_k != null)
            {
                this.field_145991_k.func_145978_a(this, 3);
            }
        }
    }

    private boolean func_145977_a(int p_145977_1_, int p_145977_2_, int p_145977_3_)
    {
        Block var4 = this.field_145850_b.func_147439_a(p_145977_1_, p_145977_2_, p_145977_3_);
        return var4 instanceof BlockChest && ((BlockChest)var4).field_149956_a == this.func_145980_j();
    }

    public void func_145845_h()
    {
        super.func_145845_h();
        this.func_145979_i();
        ++this.field_145983_q;
        float var1;

        if (!this.field_145850_b.isRemote && this.field_145987_o != 0 && (this.field_145983_q + this.field_145851_c + this.field_145848_d + this.field_145849_e) % 200 == 0)
        {
            this.field_145987_o = 0;
            var1 = 5.0F;
            List var2 = this.field_145850_b.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB((double)((float)this.field_145851_c - var1), (double)((float)this.field_145848_d - var1), (double)((float)this.field_145849_e - var1), (double)((float)(this.field_145851_c + 1) + var1), (double)((float)(this.field_145848_d + 1) + var1), (double)((float)(this.field_145849_e + 1) + var1)));
            Iterator var3 = var2.iterator();

            while (var3.hasNext())
            {
                EntityPlayer var4 = (EntityPlayer)var3.next();

                if (var4.openContainer instanceof ContainerChest)
                {
                    IInventory var5 = ((ContainerChest)var4.openContainer).getLowerChestInventory();

                    if (var5 == this || var5 instanceof InventoryLargeChest && ((InventoryLargeChest)var5).isPartOfLargeChest(this))
                    {
                        ++this.field_145987_o;
                    }
                }
            }
        }

        this.field_145986_n = this.field_145989_m;
        var1 = 0.1F;
        double var11;

        if (this.field_145987_o > 0 && this.field_145989_m == 0.0F && this.field_145992_i == null && this.field_145991_k == null)
        {
            double var8 = (double)this.field_145851_c + 0.5D;
            var11 = (double)this.field_145849_e + 0.5D;

            if (this.field_145988_l != null)
            {
                var11 += 0.5D;
            }

            if (this.field_145990_j != null)
            {
                var8 += 0.5D;
            }

            this.field_145850_b.playSoundEffect(var8, (double)this.field_145848_d + 0.5D, var11, "random.chestopen", 0.5F, this.field_145850_b.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.field_145987_o == 0 && this.field_145989_m > 0.0F || this.field_145987_o > 0 && this.field_145989_m < 1.0F)
        {
            float var9 = this.field_145989_m;

            if (this.field_145987_o > 0)
            {
                this.field_145989_m += var1;
            }
            else
            {
                this.field_145989_m -= var1;
            }

            if (this.field_145989_m > 1.0F)
            {
                this.field_145989_m = 1.0F;
            }

            float var10 = 0.5F;

            if (this.field_145989_m < var10 && var9 >= var10 && this.field_145992_i == null && this.field_145991_k == null)
            {
                var11 = (double)this.field_145851_c + 0.5D;
                double var6 = (double)this.field_145849_e + 0.5D;

                if (this.field_145988_l != null)
                {
                    var6 += 0.5D;
                }

                if (this.field_145990_j != null)
                {
                    var11 += 0.5D;
                }

                this.field_145850_b.playSoundEffect(var11, (double)this.field_145848_d + 0.5D, var6, "random.chestclosed", 0.5F, this.field_145850_b.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.field_145989_m < 0.0F)
            {
                this.field_145989_m = 0.0F;
            }
        }
    }

    public boolean func_145842_c(int p_145842_1_, int p_145842_2_)
    {
        if (p_145842_1_ == 1)
        {
            this.field_145987_o = p_145842_2_;
            return true;
        }
        else
        {
            return super.func_145842_c(p_145842_1_, p_145842_2_);
        }
    }

    public void openChest()
    {
        if (this.field_145987_o < 0)
        {
            this.field_145987_o = 0;
        }

        ++this.field_145987_o;
        this.field_145850_b.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.func_145838_q(), 1, this.field_145987_o);
        this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.func_145838_q());
        this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, this.func_145838_q());
    }

    public void closeChest()
    {
        if (this.func_145838_q() instanceof BlockChest)
        {
            --this.field_145987_o;
            this.field_145850_b.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.func_145838_q(), 1, this.field_145987_o);
            this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.func_145838_q());
            this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, this.func_145838_q());
        }
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return true;
    }

    public void func_145843_s()
    {
        super.func_145843_s();
        this.func_145836_u();
        this.func_145979_i();
    }

    public int func_145980_j()
    {
        if (this.field_145982_r == -1)
        {
            if (this.field_145850_b == null || !(this.func_145838_q() instanceof BlockChest))
            {
                return 0;
            }

            this.field_145982_r = ((BlockChest)this.func_145838_q()).field_149956_a;
        }

        return this.field_145982_r;
    }
}
