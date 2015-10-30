package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemLilyPad extends ItemColored
{
    private static final String __OBFID = "CL_00000074";

    public ItemLilyPad(Block p_i45357_1_)
    {
        super(p_i45357_1_, false);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);

        if (var4 == null)
        {
            return par1ItemStack;
        }
        else
        {
            if (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int var5 = var4.blockX;
                int var6 = var4.blockY;
                int var7 = var4.blockZ;

                if (!par2World.canMineBlock(par3EntityPlayer, var5, var6, var7))
                {
                    return par1ItemStack;
                }

                if (!par3EntityPlayer.canPlayerEdit(var5, var6, var7, var4.sideHit, par1ItemStack))
                {
                    return par1ItemStack;
                }

                if (par2World.func_147439_a(var5, var6, var7).func_149688_o() == Material.field_151586_h && par2World.getBlockMetadata(var5, var6, var7) == 0 && par2World.func_147437_c(var5, var6 + 1, var7))
                {
                    par2World.func_147449_b(var5, var6 + 1, var7, Blocks.field_150392_bi);

                    if (!par3EntityPlayer.capabilities.isCreativeMode)
                    {
                        --par1ItemStack.stackSize;
                    }
                }
            }

            return par1ItemStack;
        }
    }

    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        return Blocks.field_150392_bi.func_149741_i(par1ItemStack.getItemDamage());
    }
}
