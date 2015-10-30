package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipesIngots
{
    private Object[][] recipeItems;
    private static final String __OBFID = "CL_00000089";

    public RecipesIngots()
    {
        this.recipeItems = new Object[][] {{Blocks.field_150340_R, new ItemStack(Items.field_151043_k, 9)}, {Blocks.field_150339_S, new ItemStack(Items.field_151042_j, 9)}, {Blocks.field_150484_ah, new ItemStack(Items.field_151045_i, 9)}, {Blocks.field_150475_bE, new ItemStack(Items.field_151166_bC, 9)}, {Blocks.field_150368_y, new ItemStack(Items.field_151100_aR, 9, 4)}, {Blocks.field_150451_bX, new ItemStack(Items.field_151137_ax, 9)}, {Blocks.field_150402_ci, new ItemStack(Items.field_151044_h, 9, 0)}, {Blocks.field_150407_cf, new ItemStack(Items.field_151015_O, 9)}};
    }

    /**
     * Adds the ingot recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager par1CraftingManager)
    {
        for (int var2 = 0; var2 < this.recipeItems.length; ++var2)
        {
            Block var3 = (Block)this.recipeItems[var2][0];
            ItemStack var4 = (ItemStack)this.recipeItems[var2][1];
            par1CraftingManager.addRecipe(new ItemStack(var3), new Object[] {"###", "###", "###", '#', var4});
            par1CraftingManager.addRecipe(var4, new Object[] {"#", '#', var3});
        }

        par1CraftingManager.addRecipe(new ItemStack(Items.field_151043_k), new Object[] {"###", "###", "###", '#', Items.field_151074_bl});
        par1CraftingManager.addRecipe(new ItemStack(Items.field_151074_bl, 9), new Object[] {"#", '#', Items.field_151043_k});
    }
}
