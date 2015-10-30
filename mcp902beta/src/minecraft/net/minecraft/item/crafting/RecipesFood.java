package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipesFood
{
    private static final String __OBFID = "CL_00000084";

    /**
     * Adds the food recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager par1CraftingManager)
    {
        par1CraftingManager.addShapelessRecipe(new ItemStack(Items.field_151009_A), new Object[] {Blocks.field_150338_P, Blocks.field_150337_Q, Items.field_151054_z});
        par1CraftingManager.addRecipe(new ItemStack(Items.field_151106_aX, 8), new Object[] {"#X#", 'X', new ItemStack(Items.field_151100_aR, 1, 3), '#', Items.field_151015_O});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150440_ba), new Object[] {"MMM", "MMM", "MMM", 'M', Items.field_151127_ba});
        par1CraftingManager.addRecipe(new ItemStack(Items.field_151081_bc), new Object[] {"M", 'M', Items.field_151127_ba});
        par1CraftingManager.addRecipe(new ItemStack(Items.field_151080_bb, 4), new Object[] {"M", 'M', Blocks.field_150423_aK});
        par1CraftingManager.addShapelessRecipe(new ItemStack(Items.field_151158_bO), new Object[] {Blocks.field_150423_aK, Items.field_151102_aT, Items.field_151110_aK});
        par1CraftingManager.addShapelessRecipe(new ItemStack(Items.field_151071_bq), new Object[] {Items.field_151070_bp, Blocks.field_150338_P, Items.field_151102_aT});
        par1CraftingManager.addShapelessRecipe(new ItemStack(Items.field_151065_br, 2), new Object[] {Items.field_151072_bj});
        par1CraftingManager.addShapelessRecipe(new ItemStack(Items.field_151064_bs), new Object[] {Items.field_151065_br, Items.field_151123_aH});
    }
}
