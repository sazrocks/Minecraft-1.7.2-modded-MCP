package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesWeapons
{
    private String[][] recipePatterns = new String[][] {{"X", "X", "#"}};
    private Object[][] recipeItems;
    private static final String __OBFID = "CL_00000097";

    public RecipesWeapons()
    {
        this.recipeItems = new Object[][] {{Blocks.field_150344_f, Blocks.field_150347_e, Items.field_151042_j, Items.field_151045_i, Items.field_151043_k}, {Items.field_151041_m, Items.field_151052_q, Items.field_151040_l, Items.field_151048_u, Items.field_151010_B}};
    }

    /**
     * Adds the weapon recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager par1CraftingManager)
    {
        for (int var2 = 0; var2 < this.recipeItems[0].length; ++var2)
        {
            Object var3 = this.recipeItems[0][var2];

            for (int var4 = 0; var4 < this.recipeItems.length - 1; ++var4)
            {
                Item var5 = (Item)this.recipeItems[var4 + 1][var2];
                par1CraftingManager.addRecipe(new ItemStack(var5), new Object[] {this.recipePatterns[var4], '#', Items.field_151055_y, 'X', var3});
            }
        }

        par1CraftingManager.addRecipe(new ItemStack(Items.field_151031_f, 1), new Object[] {" #X", "# X", " #X", 'X', Items.field_151007_F, '#', Items.field_151055_y});
        par1CraftingManager.addRecipe(new ItemStack(Items.field_151032_g, 4), new Object[] {"X", "#", "Y", 'Y', Items.field_151008_G, 'X', Items.field_151145_ak, '#', Items.field_151055_y});
    }
}
