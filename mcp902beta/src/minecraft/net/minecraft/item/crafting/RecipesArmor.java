package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesArmor
{
    private String[][] recipePatterns = new String[][] {{"XXX", "X X"}, {"X X", "XXX", "XXX"}, {"XXX", "X X", "X X"}, {"X X", "X X"}};
    private Object[][] recipeItems;
    private static final String __OBFID = "CL_00000080";

    public RecipesArmor()
    {
        this.recipeItems = new Object[][] {{Items.field_151116_aA, Blocks.field_150480_ab, Items.field_151042_j, Items.field_151045_i, Items.field_151043_k}, {Items.field_151024_Q, Items.field_151020_U, Items.field_151028_Y, Items.field_151161_ac, Items.field_151169_ag}, {Items.field_151027_R, Items.field_151023_V, Items.field_151030_Z, Items.field_151163_ad, Items.field_151171_ah}, {Items.field_151026_S, Items.field_151022_W, Items.field_151165_aa, Items.field_151173_ae, Items.field_151149_ai}, {Items.field_151021_T, Items.field_151029_X, Items.field_151167_ab, Items.field_151175_af, Items.field_151151_aj}};
    }

    /**
     * Adds the armor recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager par1CraftingManager)
    {
        for (int var2 = 0; var2 < this.recipeItems[0].length; ++var2)
        {
            Object var3 = this.recipeItems[0][var2];

            for (int var4 = 0; var4 < this.recipeItems.length - 1; ++var4)
            {
                Item var5 = (Item)this.recipeItems[var4 + 1][var2];
                par1CraftingManager.addRecipe(new ItemStack(var5), new Object[] {this.recipePatterns[var4], 'X', var3});
            }
        }
    }
}
