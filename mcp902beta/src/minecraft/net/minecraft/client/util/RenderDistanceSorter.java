package net.minecraft.client.util;

import com.google.common.collect.ComparisonChain;
import java.util.Comparator;
import net.minecraft.client.renderer.RenderList;

public class RenderDistanceSorter implements Comparator
{
    private static final String __OBFID = "CL_00000945";

    public int compare(RenderList p_147714_1_, RenderList p_147714_2_)
    {
        int var3 = p_147714_2_.field_78429_a * p_147714_2_.field_78429_a + p_147714_2_.field_78427_b * p_147714_2_.field_78427_b + p_147714_2_.field_78428_c * p_147714_2_.field_78428_c;
        int var4 = p_147714_1_.field_78429_a * p_147714_1_.field_78429_a + p_147714_1_.field_78427_b * p_147714_1_.field_78427_b + p_147714_1_.field_78428_c * p_147714_1_.field_78428_c;
        return ComparisonChain.start().compare(var3, var4).result();
    }

    public int compare(Object par1Obj, Object par2Obj)
    {
        return this.compare((RenderList)par1Obj, (RenderList)par2Obj);
    }
}
