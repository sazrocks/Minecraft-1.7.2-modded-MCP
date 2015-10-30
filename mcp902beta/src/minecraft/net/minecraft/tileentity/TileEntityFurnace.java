package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityFurnace extends TileEntity implements ISidedInventory
{
    private static final int[] field_145962_k = new int[] {0};
    private static final int[] field_145959_l = new int[] {2, 1};
    private static final int[] field_145960_m = new int[] {1};
    private ItemStack[] field_145957_n = new ItemStack[3];
    public int field_145956_a;
    public int field_145963_i;
    public int field_145961_j;
    private String field_145958_o;
    private static final String __OBFID = "CL_00000357";

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.field_145957_n.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.field_145957_n[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.field_145957_n[par1] != null)
        {
            ItemStack var3;

            if (this.field_145957_n[par1].stackSize <= par2)
            {
                var3 = this.field_145957_n[par1];
                this.field_145957_n[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.field_145957_n[par1].splitStack(par2);

                if (this.field_145957_n[par1].stackSize == 0)
                {
                    this.field_145957_n[par1] = null;
                }

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
        if (this.field_145957_n[par1] != null)
        {
            ItemStack var2 = this.field_145957_n[par1];
            this.field_145957_n[par1] = null;
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
        this.field_145957_n[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    public String func_145825_b()
    {
        return this.func_145818_k_() ? this.field_145958_o : "container.furnace";
    }

    public boolean func_145818_k_()
    {
        return this.field_145958_o != null && this.field_145958_o.length() > 0;
    }

    public void func_145951_a(String p_145951_1_)
    {
        this.field_145958_o = p_145951_1_;
    }

    public void func_145839_a(NBTTagCompound p_145839_1_)
    {
        super.func_145839_a(p_145839_1_);
        NBTTagList var2 = p_145839_1_.func_150295_c("Items", 10);
        this.field_145957_n = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = var2.func_150305_b(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.field_145957_n.length)
            {
                this.field_145957_n[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        this.field_145956_a = p_145839_1_.getShort("BurnTime");
        this.field_145961_j = p_145839_1_.getShort("CookTime");
        this.field_145963_i = func_145952_a(this.field_145957_n[1]);

        if (p_145839_1_.func_150297_b("CustomName", 8))
        {
            this.field_145958_o = p_145839_1_.getString("CustomName");
        }
    }

    public void func_145841_b(NBTTagCompound p_145841_1_)
    {
        super.func_145841_b(p_145841_1_);
        p_145841_1_.setShort("BurnTime", (short)this.field_145956_a);
        p_145841_1_.setShort("CookTime", (short)this.field_145961_j);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.field_145957_n.length; ++var3)
        {
            if (this.field_145957_n[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.field_145957_n[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        p_145841_1_.setTag("Items", var2);

        if (this.func_145818_k_())
        {
            p_145841_1_.setString("CustomName", this.field_145958_o);
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

    public int func_145953_d(int p_145953_1_)
    {
        return this.field_145961_j * p_145953_1_ / 200;
    }

    public int func_145955_e(int p_145955_1_)
    {
        if (this.field_145963_i == 0)
        {
            this.field_145963_i = 200;
        }

        return this.field_145956_a * p_145955_1_ / this.field_145963_i;
    }

    public boolean func_145950_i()
    {
        return this.field_145956_a > 0;
    }

    public void func_145845_h()
    {
        boolean var1 = this.field_145956_a > 0;
        boolean var2 = false;

        if (this.field_145956_a > 0)
        {
            --this.field_145956_a;
        }

        if (!this.field_145850_b.isRemote)
        {
            if (this.field_145956_a == 0 && this.func_145948_k())
            {
                this.field_145963_i = this.field_145956_a = func_145952_a(this.field_145957_n[1]);

                if (this.field_145956_a > 0)
                {
                    var2 = true;

                    if (this.field_145957_n[1] != null)
                    {
                        --this.field_145957_n[1].stackSize;

                        if (this.field_145957_n[1].stackSize == 0)
                        {
                            Item var3 = this.field_145957_n[1].getItem().getContainerItem();
                            this.field_145957_n[1] = var3 != null ? new ItemStack(var3) : null;
                        }
                    }
                }
            }

            if (this.func_145950_i() && this.func_145948_k())
            {
                ++this.field_145961_j;

                if (this.field_145961_j == 200)
                {
                    this.field_145961_j = 0;
                    this.func_145949_j();
                    var2 = true;
                }
            }
            else
            {
                this.field_145961_j = 0;
            }

            if (var1 != this.field_145956_a > 0)
            {
                var2 = true;
                BlockFurnace.func_149931_a(this.field_145956_a > 0, this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e);
            }
        }

        if (var2)
        {
            this.onInventoryChanged();
        }
    }

    private boolean func_145948_k()
    {
        if (this.field_145957_n[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack var1 = FurnaceRecipes.smelting().func_151395_a(this.field_145957_n[0]);
            return var1 == null ? false : (this.field_145957_n[2] == null ? true : (!this.field_145957_n[2].isItemEqual(var1) ? false : (this.field_145957_n[2].stackSize < this.getInventoryStackLimit() && this.field_145957_n[2].stackSize < this.field_145957_n[2].getMaxStackSize() ? true : this.field_145957_n[2].stackSize < var1.getMaxStackSize())));
        }
    }

    public void func_145949_j()
    {
        if (this.func_145948_k())
        {
            ItemStack var1 = FurnaceRecipes.smelting().func_151395_a(this.field_145957_n[0]);

            if (this.field_145957_n[2] == null)
            {
                this.field_145957_n[2] = var1.copy();
            }
            else if (this.field_145957_n[2].getItem() == var1.getItem())
            {
                ++this.field_145957_n[2].stackSize;
            }

            --this.field_145957_n[0].stackSize;

            if (this.field_145957_n[0].stackSize <= 0)
            {
                this.field_145957_n[0] = null;
            }
        }
    }

    public static int func_145952_a(ItemStack p_145952_0_)
    {
        if (p_145952_0_ == null)
        {
            return 0;
        }
        else
        {
            Item var1 = p_145952_0_.getItem();

            if (var1 instanceof ItemBlock && Block.func_149634_a(var1) != Blocks.field_150350_a)
            {
                Block var2 = Block.func_149634_a(var1);

                if (var2 == Blocks.field_150376_bx)
                {
                    return 150;
                }

                if (var2.func_149688_o() == Material.field_151575_d)
                {
                    return 300;
                }

                if (var2 == Blocks.field_150402_ci)
                {
                    return 16000;
                }
            }

            return var1 instanceof ItemTool && ((ItemTool)var1).getToolMaterialName().equals("WOOD") ? 200 : (var1 instanceof ItemSword && ((ItemSword)var1).func_150932_j().equals("WOOD") ? 200 : (var1 instanceof ItemHoe && ((ItemHoe)var1).getMaterialName().equals("WOOD") ? 200 : (var1 == Items.field_151055_y ? 100 : (var1 == Items.field_151044_h ? 1600 : (var1 == Items.field_151129_at ? 20000 : (var1 == Item.func_150898_a(Blocks.field_150345_g) ? 100 : (var1 == Items.field_151072_bj ? 2400 : 0)))))));
        }
    }

    public static boolean func_145954_b(ItemStack p_145954_0_)
    {
        return func_145952_a(p_145954_0_) > 0;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e) != this ? false : par1EntityPlayer.getDistanceSq((double)this.field_145851_c + 0.5D, (double)this.field_145848_d + 0.5D, (double)this.field_145849_e + 0.5D) <= 64.0D;
    }

    public void openChest() {}

    public void closeChest() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return par1 == 2 ? false : (par1 == 1 ? func_145954_b(par2ItemStack) : true);
    }

    /**
     * Returns an array containing the indices of the slots that can be accessed by automation on the given side of this
     * block.
     */
    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 0 ? field_145959_l : (par1 == 1 ? field_145962_k : field_145960_m);
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(par1, par2ItemStack);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return par3 != 0 || par1 != 1 || par2ItemStack.getItem() == Items.field_151133_ar;
    }
}
