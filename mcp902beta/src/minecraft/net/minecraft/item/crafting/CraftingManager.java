package net.minecraft.item.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CraftingManager
{
    /** The static instance of this class */
    private static final CraftingManager instance = new CraftingManager();

    /** A list of all the recipes added */
    private List recipes = new ArrayList();
    private static final String __OBFID = "CL_00000090";

    /**
     * Returns the static instance of this class
     */
    public static final CraftingManager getInstance()
    {
        return instance;
    }

    private CraftingManager()
    {
        (new RecipesTools()).addRecipes(this);
        (new RecipesWeapons()).addRecipes(this);
        (new RecipesIngots()).addRecipes(this);
        (new RecipesFood()).addRecipes(this);
        (new RecipesCrafting()).addRecipes(this);
        (new RecipesArmor()).addRecipes(this);
        (new RecipesDyes()).addRecipes(this);
        this.recipes.add(new RecipesArmorDyes());
        this.recipes.add(new RecipeBookCloning());
        this.recipes.add(new RecipesMapCloning());
        this.recipes.add(new RecipesMapExtending());
        this.recipes.add(new RecipeFireworks());
        this.addRecipe(new ItemStack(Items.field_151121_aF, 3), new Object[] {"###", '#', Items.field_151120_aE});
        this.addShapelessRecipe(new ItemStack(Items.field_151122_aG, 1), new Object[] {Items.field_151121_aF, Items.field_151121_aF, Items.field_151121_aF, Items.field_151116_aA});
        this.addShapelessRecipe(new ItemStack(Items.field_151099_bA, 1), new Object[] {Items.field_151122_aG, new ItemStack(Items.field_151100_aR, 1, 0), Items.field_151008_G});
        this.addRecipe(new ItemStack(Blocks.field_150422_aJ, 2), new Object[] {"###", "###", '#', Items.field_151055_y});
        this.addRecipe(new ItemStack(Blocks.field_150463_bK, 6, 0), new Object[] {"###", "###", '#', Blocks.field_150347_e});
        this.addRecipe(new ItemStack(Blocks.field_150463_bK, 6, 1), new Object[] {"###", "###", '#', Blocks.field_150341_Y});
        this.addRecipe(new ItemStack(Blocks.field_150386_bk, 6), new Object[] {"###", "###", '#', Blocks.field_150385_bj});
        this.addRecipe(new ItemStack(Blocks.field_150396_be, 1), new Object[] {"#W#", "#W#", '#', Items.field_151055_y, 'W', Blocks.field_150344_f});
        this.addRecipe(new ItemStack(Blocks.field_150421_aI, 1), new Object[] {"###", "#X#", "###", '#', Blocks.field_150344_f, 'X', Items.field_151045_i});
        this.addRecipe(new ItemStack(Items.field_151058_ca, 2), new Object[] {"~~ ", "~O ", "  ~", '~', Items.field_151007_F, 'O', Items.field_151123_aH});
        this.addRecipe(new ItemStack(Blocks.field_150323_B, 1), new Object[] {"###", "#X#", "###", '#', Blocks.field_150344_f, 'X', Items.field_151137_ax});
        this.addRecipe(new ItemStack(Blocks.field_150342_X, 1), new Object[] {"###", "XXX", "###", '#', Blocks.field_150344_f, 'X', Items.field_151122_aG});
        this.addRecipe(new ItemStack(Blocks.field_150433_aE, 1), new Object[] {"##", "##", '#', Items.field_151126_ay});
        this.addRecipe(new ItemStack(Blocks.field_150431_aC, 6), new Object[] {"###", '#', Blocks.field_150433_aE});
        this.addRecipe(new ItemStack(Blocks.field_150435_aG, 1), new Object[] {"##", "##", '#', Items.field_151119_aD});
        this.addRecipe(new ItemStack(Blocks.field_150336_V, 1), new Object[] {"##", "##", '#', Items.field_151118_aC});
        this.addRecipe(new ItemStack(Blocks.field_150426_aN, 1), new Object[] {"##", "##", '#', Items.field_151114_aO});
        this.addRecipe(new ItemStack(Blocks.field_150371_ca, 1), new Object[] {"##", "##", '#', Items.field_151128_bU});
        this.addRecipe(new ItemStack(Blocks.field_150325_L, 1), new Object[] {"##", "##", '#', Items.field_151007_F});
        this.addRecipe(new ItemStack(Blocks.field_150335_W, 1), new Object[] {"X#X", "#X#", "X#X", 'X', Items.field_151016_H, '#', Blocks.field_150354_m});
        this.addRecipe(new ItemStack(Blocks.field_150333_U, 6, 3), new Object[] {"###", '#', Blocks.field_150347_e});
        this.addRecipe(new ItemStack(Blocks.field_150333_U, 6, 0), new Object[] {"###", '#', Blocks.field_150348_b});
        this.addRecipe(new ItemStack(Blocks.field_150333_U, 6, 1), new Object[] {"###", '#', Blocks.field_150322_A});
        this.addRecipe(new ItemStack(Blocks.field_150333_U, 6, 4), new Object[] {"###", '#', Blocks.field_150336_V});
        this.addRecipe(new ItemStack(Blocks.field_150333_U, 6, 5), new Object[] {"###", '#', Blocks.field_150417_aV});
        this.addRecipe(new ItemStack(Blocks.field_150333_U, 6, 6), new Object[] {"###", '#', Blocks.field_150385_bj});
        this.addRecipe(new ItemStack(Blocks.field_150333_U, 6, 7), new Object[] {"###", '#', Blocks.field_150371_ca});
        this.addRecipe(new ItemStack(Blocks.field_150376_bx, 6, 0), new Object[] {"###", '#', new ItemStack(Blocks.field_150344_f, 1, 0)});
        this.addRecipe(new ItemStack(Blocks.field_150376_bx, 6, 2), new Object[] {"###", '#', new ItemStack(Blocks.field_150344_f, 1, 2)});
        this.addRecipe(new ItemStack(Blocks.field_150376_bx, 6, 1), new Object[] {"###", '#', new ItemStack(Blocks.field_150344_f, 1, 1)});
        this.addRecipe(new ItemStack(Blocks.field_150376_bx, 6, 3), new Object[] {"###", '#', new ItemStack(Blocks.field_150344_f, 1, 3)});
        this.addRecipe(new ItemStack(Blocks.field_150376_bx, 6, 4), new Object[] {"###", '#', new ItemStack(Blocks.field_150344_f, 1, 4)});
        this.addRecipe(new ItemStack(Blocks.field_150376_bx, 6, 5), new Object[] {"###", '#', new ItemStack(Blocks.field_150344_f, 1, 5)});
        this.addRecipe(new ItemStack(Blocks.field_150468_ap, 3), new Object[] {"# #", "###", "# #", '#', Items.field_151055_y});
        this.addRecipe(new ItemStack(Items.field_151135_aq, 1), new Object[] {"##", "##", "##", '#', Blocks.field_150344_f});
        this.addRecipe(new ItemStack(Blocks.field_150415_aT, 2), new Object[] {"###", "###", '#', Blocks.field_150344_f});
        this.addRecipe(new ItemStack(Items.field_151139_aw, 1), new Object[] {"##", "##", "##", '#', Items.field_151042_j});
        this.addRecipe(new ItemStack(Items.field_151155_ap, 3), new Object[] {"###", "###", " X ", '#', Blocks.field_150344_f, 'X', Items.field_151055_y});
        this.addRecipe(new ItemStack(Items.field_151105_aU, 1), new Object[] {"AAA", "BEB", "CCC", 'A', Items.field_151117_aB, 'B', Items.field_151102_aT, 'C', Items.field_151015_O, 'E', Items.field_151110_aK});
        this.addRecipe(new ItemStack(Items.field_151102_aT, 1), new Object[] {"#", '#', Items.field_151120_aE});
        this.addRecipe(new ItemStack(Blocks.field_150344_f, 4, 0), new Object[] {"#", '#', new ItemStack(Blocks.field_150364_r, 1, 0)});
        this.addRecipe(new ItemStack(Blocks.field_150344_f, 4, 1), new Object[] {"#", '#', new ItemStack(Blocks.field_150364_r, 1, 1)});
        this.addRecipe(new ItemStack(Blocks.field_150344_f, 4, 2), new Object[] {"#", '#', new ItemStack(Blocks.field_150364_r, 1, 2)});
        this.addRecipe(new ItemStack(Blocks.field_150344_f, 4, 3), new Object[] {"#", '#', new ItemStack(Blocks.field_150364_r, 1, 3)});
        this.addRecipe(new ItemStack(Blocks.field_150344_f, 4, 4), new Object[] {"#", '#', new ItemStack(Blocks.field_150363_s, 1, 0)});
        this.addRecipe(new ItemStack(Blocks.field_150344_f, 4, 5), new Object[] {"#", '#', new ItemStack(Blocks.field_150363_s, 1, 1)});
        this.addRecipe(new ItemStack(Items.field_151055_y, 4), new Object[] {"#", "#", '#', Blocks.field_150344_f});
        this.addRecipe(new ItemStack(Blocks.field_150478_aa, 4), new Object[] {"X", "#", 'X', Items.field_151044_h, '#', Items.field_151055_y});
        this.addRecipe(new ItemStack(Blocks.field_150478_aa, 4), new Object[] {"X", "#", 'X', new ItemStack(Items.field_151044_h, 1, 1), '#', Items.field_151055_y});
        this.addRecipe(new ItemStack(Items.field_151054_z, 4), new Object[] {"# #", " # ", '#', Blocks.field_150344_f});
        this.addRecipe(new ItemStack(Items.field_151069_bo, 3), new Object[] {"# #", " # ", '#', Blocks.field_150359_w});
        this.addRecipe(new ItemStack(Blocks.field_150448_aq, 16), new Object[] {"X X", "X#X", "X X", 'X', Items.field_151042_j, '#', Items.field_151055_y});
        this.addRecipe(new ItemStack(Blocks.field_150318_D, 6), new Object[] {"X X", "X#X", "XRX", 'X', Items.field_151043_k, 'R', Items.field_151137_ax, '#', Items.field_151055_y});
        this.addRecipe(new ItemStack(Blocks.field_150408_cc, 6), new Object[] {"XSX", "X#X", "XSX", 'X', Items.field_151042_j, '#', Blocks.field_150429_aA, 'S', Items.field_151055_y});
        this.addRecipe(new ItemStack(Blocks.field_150319_E, 6), new Object[] {"X X", "X#X", "XRX", 'X', Items.field_151042_j, 'R', Items.field_151137_ax, '#', Blocks.field_150456_au});
        this.addRecipe(new ItemStack(Items.field_151143_au, 1), new Object[] {"# #", "###", '#', Items.field_151042_j});
        this.addRecipe(new ItemStack(Items.field_151066_bu, 1), new Object[] {"# #", "# #", "###", '#', Items.field_151042_j});
        this.addRecipe(new ItemStack(Items.field_151067_bt, 1), new Object[] {" B ", "###", '#', Blocks.field_150347_e, 'B', Items.field_151072_bj});
        this.addRecipe(new ItemStack(Blocks.field_150428_aP, 1), new Object[] {"A", "B", 'A', Blocks.field_150423_aK, 'B', Blocks.field_150478_aa});
        this.addRecipe(new ItemStack(Items.field_151108_aI, 1), new Object[] {"A", "B", 'A', Blocks.field_150486_ae, 'B', Items.field_151143_au});
        this.addRecipe(new ItemStack(Items.field_151109_aJ, 1), new Object[] {"A", "B", 'A', Blocks.field_150460_al, 'B', Items.field_151143_au});
        this.addRecipe(new ItemStack(Items.field_151142_bV, 1), new Object[] {"A", "B", 'A', Blocks.field_150335_W, 'B', Items.field_151143_au});
        this.addRecipe(new ItemStack(Items.field_151140_bW, 1), new Object[] {"A", "B", 'A', Blocks.field_150438_bZ, 'B', Items.field_151143_au});
        this.addRecipe(new ItemStack(Items.field_151124_az, 1), new Object[] {"# #", "###", '#', Blocks.field_150344_f});
        this.addRecipe(new ItemStack(Items.field_151133_ar, 1), new Object[] {"# #", " # ", '#', Items.field_151042_j});
        this.addRecipe(new ItemStack(Items.field_151162_bE, 1), new Object[] {"# #", " # ", '#', Items.field_151118_aC});
        this.addShapelessRecipe(new ItemStack(Items.field_151033_d, 1), new Object[] {new ItemStack(Items.field_151042_j, 1), new ItemStack(Items.field_151145_ak, 1)});
        this.addRecipe(new ItemStack(Items.field_151025_P, 1), new Object[] {"###", '#', Items.field_151015_O});
        this.addRecipe(new ItemStack(Blocks.field_150476_ad, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Blocks.field_150344_f, 1, 0)});
        this.addRecipe(new ItemStack(Blocks.field_150487_bG, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Blocks.field_150344_f, 1, 2)});
        this.addRecipe(new ItemStack(Blocks.field_150485_bF, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Blocks.field_150344_f, 1, 1)});
        this.addRecipe(new ItemStack(Blocks.field_150481_bH, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Blocks.field_150344_f, 1, 3)});
        this.addRecipe(new ItemStack(Blocks.field_150400_ck, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Blocks.field_150344_f, 1, 4)});
        this.addRecipe(new ItemStack(Blocks.field_150401_cl, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Blocks.field_150344_f, 1, 5)});
        this.addRecipe(new ItemStack(Items.field_151112_aM, 1), new Object[] {"  #", " #X", "# X", '#', Items.field_151055_y, 'X', Items.field_151007_F});
        this.addRecipe(new ItemStack(Items.field_151146_bM, 1), new Object[] {"# ", " X", '#', Items.field_151112_aM, 'X', Items.field_151172_bF}).func_92100_c();
        this.addRecipe(new ItemStack(Blocks.field_150446_ar, 4), new Object[] {"#  ", "## ", "###", '#', Blocks.field_150347_e});
        this.addRecipe(new ItemStack(Blocks.field_150389_bf, 4), new Object[] {"#  ", "## ", "###", '#', Blocks.field_150336_V});
        this.addRecipe(new ItemStack(Blocks.field_150390_bg, 4), new Object[] {"#  ", "## ", "###", '#', Blocks.field_150417_aV});
        this.addRecipe(new ItemStack(Blocks.field_150387_bl, 4), new Object[] {"#  ", "## ", "###", '#', Blocks.field_150385_bj});
        this.addRecipe(new ItemStack(Blocks.field_150372_bz, 4), new Object[] {"#  ", "## ", "###", '#', Blocks.field_150322_A});
        this.addRecipe(new ItemStack(Blocks.field_150370_cb, 4), new Object[] {"#  ", "## ", "###", '#', Blocks.field_150371_ca});
        this.addRecipe(new ItemStack(Items.field_151159_an, 1), new Object[] {"###", "#X#", "###", '#', Items.field_151055_y, 'X', Blocks.field_150325_L});
        this.addRecipe(new ItemStack(Items.field_151160_bD, 1), new Object[] {"###", "#X#", "###", '#', Items.field_151055_y, 'X', Items.field_151116_aA});
        this.addRecipe(new ItemStack(Items.field_151153_ao, 1, 0), new Object[] {"###", "#X#", "###", '#', Items.field_151043_k, 'X', Items.field_151034_e});
        this.addRecipe(new ItemStack(Items.field_151153_ao, 1, 1), new Object[] {"###", "#X#", "###", '#', Blocks.field_150340_R, 'X', Items.field_151034_e});
        this.addRecipe(new ItemStack(Items.field_151150_bK, 1, 0), new Object[] {"###", "#X#", "###", '#', Items.field_151074_bl, 'X', Items.field_151172_bF});
        this.addRecipe(new ItemStack(Items.field_151060_bw, 1), new Object[] {"###", "#X#", "###", '#', Items.field_151074_bl, 'X', Items.field_151127_ba});
        this.addRecipe(new ItemStack(Blocks.field_150442_at, 1), new Object[] {"X", "#", '#', Blocks.field_150347_e, 'X', Items.field_151055_y});
        this.addRecipe(new ItemStack(Blocks.field_150479_bC, 2), new Object[] {"I", "S", "#", '#', Blocks.field_150344_f, 'S', Items.field_151055_y, 'I', Items.field_151042_j});
        this.addRecipe(new ItemStack(Blocks.field_150429_aA, 1), new Object[] {"X", "#", '#', Items.field_151055_y, 'X', Items.field_151137_ax});
        this.addRecipe(new ItemStack(Items.field_151107_aW, 1), new Object[] {"#X#", "III", '#', Blocks.field_150429_aA, 'X', Items.field_151137_ax, 'I', Blocks.field_150348_b});
        this.addRecipe(new ItemStack(Items.field_151132_bS, 1), new Object[] {" # ", "#X#", "III", '#', Blocks.field_150429_aA, 'X', Items.field_151128_bU, 'I', Blocks.field_150348_b});
        this.addRecipe(new ItemStack(Items.field_151113_aN, 1), new Object[] {" # ", "#X#", " # ", '#', Items.field_151043_k, 'X', Items.field_151137_ax});
        this.addRecipe(new ItemStack(Items.field_151111_aL, 1), new Object[] {" # ", "#X#", " # ", '#', Items.field_151042_j, 'X', Items.field_151137_ax});
        this.addRecipe(new ItemStack(Items.field_151148_bJ, 1), new Object[] {"###", "#X#", "###", '#', Items.field_151121_aF, 'X', Items.field_151111_aL});
        this.addRecipe(new ItemStack(Blocks.field_150430_aB, 1), new Object[] {"#", '#', Blocks.field_150348_b});
        this.addRecipe(new ItemStack(Blocks.field_150471_bO, 1), new Object[] {"#", '#', Blocks.field_150344_f});
        this.addRecipe(new ItemStack(Blocks.field_150456_au, 1), new Object[] {"##", '#', Blocks.field_150348_b});
        this.addRecipe(new ItemStack(Blocks.field_150452_aw, 1), new Object[] {"##", '#', Blocks.field_150344_f});
        this.addRecipe(new ItemStack(Blocks.field_150443_bT, 1), new Object[] {"##", '#', Items.field_151042_j});
        this.addRecipe(new ItemStack(Blocks.field_150445_bS, 1), new Object[] {"##", '#', Items.field_151043_k});
        this.addRecipe(new ItemStack(Blocks.field_150367_z, 1), new Object[] {"###", "#X#", "#R#", '#', Blocks.field_150347_e, 'X', Items.field_151031_f, 'R', Items.field_151137_ax});
        this.addRecipe(new ItemStack(Blocks.field_150409_cd, 1), new Object[] {"###", "# #", "#R#", '#', Blocks.field_150347_e, 'R', Items.field_151137_ax});
        this.addRecipe(new ItemStack(Blocks.field_150331_J, 1), new Object[] {"TTT", "#X#", "#R#", '#', Blocks.field_150347_e, 'X', Items.field_151042_j, 'R', Items.field_151137_ax, 'T', Blocks.field_150344_f});
        this.addRecipe(new ItemStack(Blocks.field_150320_F, 1), new Object[] {"S", "P", 'S', Items.field_151123_aH, 'P', Blocks.field_150331_J});
        this.addRecipe(new ItemStack(Items.field_151104_aV, 1), new Object[] {"###", "XXX", '#', Blocks.field_150325_L, 'X', Blocks.field_150344_f});
        this.addRecipe(new ItemStack(Blocks.field_150381_bn, 1), new Object[] {" B ", "D#D", "###", '#', Blocks.field_150343_Z, 'B', Items.field_151122_aG, 'D', Items.field_151045_i});
        this.addRecipe(new ItemStack(Blocks.field_150467_bQ, 1), new Object[] {"III", " i ", "iii", 'I', Blocks.field_150339_S, 'i', Items.field_151042_j});
        this.addShapelessRecipe(new ItemStack(Items.field_151061_bv, 1), new Object[] {Items.field_151079_bi, Items.field_151065_br});
        this.addShapelessRecipe(new ItemStack(Items.field_151059_bz, 3), new Object[] {Items.field_151016_H, Items.field_151065_br, Items.field_151044_h});
        this.addShapelessRecipe(new ItemStack(Items.field_151059_bz, 3), new Object[] {Items.field_151016_H, Items.field_151065_br, new ItemStack(Items.field_151044_h, 1, 1)});
        this.addRecipe(new ItemStack(Blocks.field_150453_bW), new Object[] {"GGG", "QQQ", "WWW", 'G', Blocks.field_150359_w, 'Q', Items.field_151128_bU, 'W', Blocks.field_150376_bx});
        this.addRecipe(new ItemStack(Blocks.field_150438_bZ), new Object[] {"I I", "ICI", " I ", 'I', Items.field_151042_j, 'C', Blocks.field_150486_ae});
        Collections.sort(this.recipes, new Comparator()
        {
            private static final String __OBFID = "CL_00000091";
            public int compare(IRecipe par1IRecipe, IRecipe par2IRecipe)
            {
                return par1IRecipe instanceof ShapelessRecipes && par2IRecipe instanceof ShapedRecipes ? 1 : (par2IRecipe instanceof ShapelessRecipes && par1IRecipe instanceof ShapedRecipes ? -1 : (par2IRecipe.getRecipeSize() < par1IRecipe.getRecipeSize() ? -1 : (par2IRecipe.getRecipeSize() > par1IRecipe.getRecipeSize() ? 1 : 0)));
            }
            public int compare(Object par1Obj, Object par2Obj)
            {
                return this.compare((IRecipe)par1Obj, (IRecipe)par2Obj);
            }
        });
    }

    ShapedRecipes addRecipe(ItemStack par1ItemStack, Object ... par2ArrayOfObj)
    {
        String var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;

        if (par2ArrayOfObj[var4] instanceof String[])
        {
            String[] var7 = (String[])((String[])par2ArrayOfObj[var4++]);

            for (int var8 = 0; var8 < var7.length; ++var8)
            {
                String var9 = var7[var8];
                ++var6;
                var5 = var9.length();
                var3 = var3 + var9;
            }
        }
        else
        {
            while (par2ArrayOfObj[var4] instanceof String)
            {
                String var11 = (String)par2ArrayOfObj[var4++];
                ++var6;
                var5 = var11.length();
                var3 = var3 + var11;
            }
        }

        HashMap var12;

        for (var12 = new HashMap(); var4 < par2ArrayOfObj.length; var4 += 2)
        {
            Character var13 = (Character)par2ArrayOfObj[var4];
            ItemStack var15 = null;

            if (par2ArrayOfObj[var4 + 1] instanceof Item)
            {
                var15 = new ItemStack((Item)par2ArrayOfObj[var4 + 1]);
            }
            else if (par2ArrayOfObj[var4 + 1] instanceof Block)
            {
                var15 = new ItemStack((Block)par2ArrayOfObj[var4 + 1], 1, 32767);
            }
            else if (par2ArrayOfObj[var4 + 1] instanceof ItemStack)
            {
                var15 = (ItemStack)par2ArrayOfObj[var4 + 1];
            }

            var12.put(var13, var15);
        }

        ItemStack[] var14 = new ItemStack[var5 * var6];

        for (int var16 = 0; var16 < var5 * var6; ++var16)
        {
            char var10 = var3.charAt(var16);

            if (var12.containsKey(Character.valueOf(var10)))
            {
                var14[var16] = ((ItemStack)var12.get(Character.valueOf(var10))).copy();
            }
            else
            {
                var14[var16] = null;
            }
        }

        ShapedRecipes var17 = new ShapedRecipes(var5, var6, var14, par1ItemStack);
        this.recipes.add(var17);
        return var17;
    }

    void addShapelessRecipe(ItemStack par1ItemStack, Object ... par2ArrayOfObj)
    {
        ArrayList var3 = new ArrayList();
        Object[] var4 = par2ArrayOfObj;
        int var5 = par2ArrayOfObj.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            Object var7 = var4[var6];

            if (var7 instanceof ItemStack)
            {
                var3.add(((ItemStack)var7).copy());
            }
            else if (var7 instanceof Item)
            {
                var3.add(new ItemStack((Item)var7));
            }
            else
            {
                if (!(var7 instanceof Block))
                {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }

                var3.add(new ItemStack((Block)var7));
            }
        }

        this.recipes.add(new ShapelessRecipes(par1ItemStack, var3));
    }

    public ItemStack findMatchingRecipe(InventoryCrafting par1InventoryCrafting, World par2World)
    {
        int var3 = 0;
        ItemStack var4 = null;
        ItemStack var5 = null;
        int var6;

        for (var6 = 0; var6 < par1InventoryCrafting.getSizeInventory(); ++var6)
        {
            ItemStack var7 = par1InventoryCrafting.getStackInSlot(var6);

            if (var7 != null)
            {
                if (var3 == 0)
                {
                    var4 = var7;
                }

                if (var3 == 1)
                {
                    var5 = var7;
                }

                ++var3;
            }
        }

        if (var3 == 2 && var4.getItem() == var5.getItem() && var4.stackSize == 1 && var5.stackSize == 1 && var4.getItem().isDamageable())
        {
            Item var11 = var4.getItem();
            int var13 = var11.getMaxDamage() - var4.getItemDamageForDisplay();
            int var8 = var11.getMaxDamage() - var5.getItemDamageForDisplay();
            int var9 = var13 + var8 + var11.getMaxDamage() * 5 / 100;
            int var10 = var11.getMaxDamage() - var9;

            if (var10 < 0)
            {
                var10 = 0;
            }

            return new ItemStack(var4.getItem(), 1, var10);
        }
        else
        {
            for (var6 = 0; var6 < this.recipes.size(); ++var6)
            {
                IRecipe var12 = (IRecipe)this.recipes.get(var6);

                if (var12.matches(par1InventoryCrafting, par2World))
                {
                    return var12.getCraftingResult(par1InventoryCrafting);
                }
            }

            return null;
        }
    }

    /**
     * returns the List<> of all recipes
     */
    public List getRecipeList()
    {
        return this.recipes;
    }
}
