package net.minecraft.item.crafting;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;

public class FurnaceRecipes
{
    private static final FurnaceRecipes smeltingBase = new FurnaceRecipes();

    /** The list of smelting results. */
    private Map smeltingList = new HashMap();
    private Map experienceList = new HashMap();
    private static final String __OBFID = "CL_00000085";

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static FurnaceRecipes smelting()
    {
        return smeltingBase;
    }

    private FurnaceRecipes()
    {
        this.func_151393_a(Blocks.field_150366_p, new ItemStack(Items.field_151042_j), 0.7F);
        this.func_151393_a(Blocks.field_150352_o, new ItemStack(Items.field_151043_k), 1.0F);
        this.func_151393_a(Blocks.field_150482_ag, new ItemStack(Items.field_151045_i), 1.0F);
        this.func_151393_a(Blocks.field_150354_m, new ItemStack(Blocks.field_150359_w), 0.1F);
        this.func_151396_a(Items.field_151147_al, new ItemStack(Items.field_151157_am), 0.35F);
        this.func_151396_a(Items.field_151082_bd, new ItemStack(Items.field_151083_be), 0.35F);
        this.func_151396_a(Items.field_151076_bf, new ItemStack(Items.field_151077_bg), 0.35F);
        this.func_151393_a(Blocks.field_150347_e, new ItemStack(Blocks.field_150348_b), 0.1F);
        this.func_151396_a(Items.field_151119_aD, new ItemStack(Items.field_151118_aC), 0.3F);
        this.func_151393_a(Blocks.field_150435_aG, new ItemStack(Blocks.field_150405_ch), 0.35F);
        this.func_151393_a(Blocks.field_150434_aF, new ItemStack(Items.field_151100_aR, 1, 2), 0.2F);
        this.func_151393_a(Blocks.field_150364_r, new ItemStack(Items.field_151044_h, 1, 1), 0.15F);
        this.func_151393_a(Blocks.field_150363_s, new ItemStack(Items.field_151044_h, 1, 1), 0.15F);
        this.func_151393_a(Blocks.field_150412_bA, new ItemStack(Items.field_151166_bC), 1.0F);
        this.func_151396_a(Items.field_151174_bG, new ItemStack(Items.field_151168_bH), 0.35F);
        this.func_151393_a(Blocks.field_150424_aL, new ItemStack(Items.field_151130_bT), 0.1F);
        ItemFishFood.FishType[] var1 = ItemFishFood.FishType.values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3)
        {
            ItemFishFood.FishType var4 = var1[var3];

            if (var4.func_150973_i())
            {
                this.func_151394_a(new ItemStack(Items.field_151115_aP, 1, var4.func_150976_a()), new ItemStack(Items.field_151101_aQ, 1, var4.func_150976_a()), 0.35F);
            }
        }

        this.func_151393_a(Blocks.field_150365_q, new ItemStack(Items.field_151044_h), 0.1F);
        this.func_151393_a(Blocks.field_150450_ax, new ItemStack(Items.field_151137_ax), 0.7F);
        this.func_151393_a(Blocks.field_150369_x, new ItemStack(Items.field_151100_aR, 1, 4), 0.2F);
        this.func_151393_a(Blocks.field_150449_bY, new ItemStack(Items.field_151128_bU), 0.2F);
    }

    public void func_151393_a(Block p_151393_1_, ItemStack p_151393_2_, float p_151393_3_)
    {
        this.func_151396_a(Item.func_150898_a(p_151393_1_), p_151393_2_, p_151393_3_);
    }

    public void func_151396_a(Item p_151396_1_, ItemStack p_151396_2_, float p_151396_3_)
    {
        this.func_151394_a(new ItemStack(p_151396_1_, 1, 32767), p_151396_2_, p_151396_3_);
    }

    public void func_151394_a(ItemStack p_151394_1_, ItemStack p_151394_2_, float p_151394_3_)
    {
        this.smeltingList.put(p_151394_1_, p_151394_2_);
        this.experienceList.put(p_151394_2_, Float.valueOf(p_151394_3_));
    }

    public ItemStack func_151395_a(ItemStack p_151395_1_)
    {
        Iterator var2 = this.smeltingList.entrySet().iterator();
        Entry var3;

        do
        {
            if (!var2.hasNext())
            {
                return null;
            }

            var3 = (Entry)var2.next();
        }
        while (!this.func_151397_a(p_151395_1_, (ItemStack)var3.getKey()));

        return (ItemStack)var3.getValue();
    }

    private boolean func_151397_a(ItemStack p_151397_1_, ItemStack p_151397_2_)
    {
        return p_151397_2_.getItem() == p_151397_1_.getItem() && (p_151397_2_.getItemDamage() == 32767 || p_151397_2_.getItemDamage() == p_151397_1_.getItemDamage());
    }

    public Map getSmeltingList()
    {
        return this.smeltingList;
    }

    public float func_151398_b(ItemStack p_151398_1_)
    {
        Iterator var2 = this.experienceList.entrySet().iterator();
        Entry var3;

        do
        {
            if (!var2.hasNext())
            {
                return 0.0F;
            }

            var3 = (Entry)var2.next();
        }
        while (!this.func_151397_a(p_151398_1_, (ItemStack)var3.getKey()));

        return ((Float)var3.getValue()).floatValue();
    }
}
