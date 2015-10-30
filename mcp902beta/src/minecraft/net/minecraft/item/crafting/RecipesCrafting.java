package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipesCrafting
{
    private static final String __OBFID = "CL_00000095";

    /**
     * Adds the crafting recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager par1CraftingManager)
    {
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150486_ae), new Object[] {"###", "# #", "###", '#', Blocks.field_150344_f});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150447_bR), new Object[] {"#-", '#', Blocks.field_150486_ae, '-', Blocks.field_150479_bC});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150477_bB), new Object[] {"###", "#E#", "###", '#', Blocks.field_150343_Z, 'E', Items.field_151061_bv});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150460_al), new Object[] {"###", "# #", "###", '#', Blocks.field_150347_e});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150462_ai), new Object[] {"##", "##", '#', Blocks.field_150344_f});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150322_A), new Object[] {"##", "##", '#', new ItemStack(Blocks.field_150354_m, 1, 0)});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150322_A, 4, 2), new Object[] {"##", "##", '#', Blocks.field_150322_A});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150322_A, 1, 1), new Object[] {"#", "#", '#', new ItemStack(Blocks.field_150333_U, 1, 1)});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150371_ca, 1, 1), new Object[] {"#", "#", '#', new ItemStack(Blocks.field_150333_U, 1, 7)});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150371_ca, 2, 2), new Object[] {"#", "#", '#', new ItemStack(Blocks.field_150371_ca, 1, 0)});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150417_aV, 4), new Object[] {"##", "##", '#', Blocks.field_150348_b});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150411_aY, 16), new Object[] {"###", "###", '#', Items.field_151042_j});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150410_aZ, 16), new Object[] {"###", "###", '#', Blocks.field_150359_w});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150379_bu, 1), new Object[] {" R ", "RGR", " R ", 'R', Items.field_151137_ax, 'G', Blocks.field_150426_aN});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150461_bJ, 1), new Object[] {"GGG", "GSG", "OOO", 'G', Blocks.field_150359_w, 'S', Items.field_151156_bN, 'O', Blocks.field_150343_Z});
        par1CraftingManager.addRecipe(new ItemStack(Blocks.field_150385_bj, 1), new Object[] {"NN", "NN", 'N', Items.field_151130_bT});
    }
}
