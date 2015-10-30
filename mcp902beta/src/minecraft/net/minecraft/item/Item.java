package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockWall;
import net.minecraft.block.BlockWood;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Item
{
    public static final RegistryNamespaced field_150901_e = new RegistryNamespaced();
    protected static final UUID field_111210_e = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    private CreativeTabs tabToDisplayOn;

    /** The RNG used by the Item subclasses. */
    protected static Random itemRand = new Random();

    /** Maximum size of the stack. */
    protected int maxStackSize = 64;

    /** Maximum damage an item can handle. */
    private int maxDamage;

    /** If true, render the object in full 3D, like weapons and tools. */
    protected boolean bFull3D;

    /**
     * Some items (like dyes) have multiple subtypes on same item, this is field define this behavior
     */
    protected boolean hasSubtypes;
    private Item containerItem;
    private String potionEffect;

    /** The unlocalized name of this item. */
    private String unlocalizedName;

    /** Icon index in the icons table. */
    protected IIcon itemIcon;

    /** The string associated with this Item's Icon. */
    protected String iconString;
    private static final String __OBFID = "CL_00000041";

    public static int func_150891_b(Item p_150891_0_)
    {
        return p_150891_0_ == null ? 0 : field_150901_e.func_148757_b(p_150891_0_);
    }

    public static Item func_150899_d(int p_150899_0_)
    {
        return (Item)field_150901_e.func_148754_a(p_150899_0_);
    }

    public static Item func_150898_a(Block p_150898_0_)
    {
        return func_150899_d(Block.func_149682_b(p_150898_0_));
    }

    public static void func_150900_l()
    {
        field_150901_e.func_148756_a(256, "iron_shovel", (new ItemSpade(Item.ToolMaterial.IRON)).setUnlocalizedName("shovelIron").setTextureName("iron_shovel"));
        field_150901_e.func_148756_a(257, "iron_pickaxe", (new ItemPickaxe(Item.ToolMaterial.IRON)).setUnlocalizedName("pickaxeIron").setTextureName("iron_pickaxe"));
        field_150901_e.func_148756_a(258, "iron_axe", (new ItemAxe(Item.ToolMaterial.IRON)).setUnlocalizedName("hatchetIron").setTextureName("iron_axe"));
        field_150901_e.func_148756_a(259, "flint_and_steel", (new ItemFlintAndSteel()).setUnlocalizedName("flintAndSteel").setTextureName("flint_and_steel"));
        field_150901_e.func_148756_a(260, "apple", (new ItemFood(4, 0.3F, false)).setUnlocalizedName("apple").setTextureName("apple"));
        field_150901_e.func_148756_a(261, "bow", (new ItemBow()).setUnlocalizedName("bow").setTextureName("bow"));
        field_150901_e.func_148756_a(423, "machine_gun_bow", (new ItemMachineGunBow()).setUnlocalizedName("machineBow").setTextureName("bow"));
        field_150901_e.func_148756_a(262, "arrow", (new Item()).setUnlocalizedName("arrow").setCreativeTab(CreativeTabs.tabCombat).setTextureName("arrow"));
        field_150901_e.func_148756_a(263, "coal", (new ItemCoal()).setUnlocalizedName("coal").setTextureName("coal"));
        field_150901_e.func_148756_a(264, "diamond", (new Item()).setUnlocalizedName("diamond").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("diamond"));
        field_150901_e.func_148756_a(265, "iron_ingot", (new Item()).setUnlocalizedName("ingotIron").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("iron_ingot"));
        field_150901_e.func_148756_a(266, "gold_ingot", (new Item()).setUnlocalizedName("ingotGold").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("gold_ingot"));
        field_150901_e.func_148756_a(267, "iron_sword", (new ItemSword(Item.ToolMaterial.IRON)).setUnlocalizedName("swordIron").setTextureName("iron_sword"));
        field_150901_e.func_148756_a(268, "wooden_sword", (new ItemSword(Item.ToolMaterial.WOOD)).setUnlocalizedName("swordWood").setTextureName("wood_sword"));
        field_150901_e.func_148756_a(269, "wooden_shovel", (new ItemSpade(Item.ToolMaterial.WOOD)).setUnlocalizedName("shovelWood").setTextureName("wood_shovel"));
        field_150901_e.func_148756_a(270, "wooden_pickaxe", (new ItemPickaxe(Item.ToolMaterial.WOOD)).setUnlocalizedName("pickaxeWood").setTextureName("wood_pickaxe"));
        field_150901_e.func_148756_a(271, "wooden_axe", (new ItemAxe(Item.ToolMaterial.WOOD)).setUnlocalizedName("hatchetWood").setTextureName("wood_axe"));
        field_150901_e.func_148756_a(272, "stone_sword", (new ItemSword(Item.ToolMaterial.STONE)).setUnlocalizedName("swordStone").setTextureName("stone_sword"));
        field_150901_e.func_148756_a(273, "stone_shovel", (new ItemSpade(Item.ToolMaterial.STONE)).setUnlocalizedName("shovelStone").setTextureName("stone_shovel"));
        field_150901_e.func_148756_a(274, "stone_pickaxe", (new ItemPickaxe(Item.ToolMaterial.STONE)).setUnlocalizedName("pickaxeStone").setTextureName("stone_pickaxe"));
        field_150901_e.func_148756_a(275, "stone_axe", (new ItemAxe(Item.ToolMaterial.STONE)).setUnlocalizedName("hatchetStone").setTextureName("stone_axe"));
        field_150901_e.func_148756_a(276, "diamond_sword", (new ItemSword(Item.ToolMaterial.EMERALD)).setUnlocalizedName("swordDiamond").setTextureName("diamond_sword"));
        field_150901_e.func_148756_a(277, "diamond_shovel", (new ItemSpade(Item.ToolMaterial.EMERALD)).setUnlocalizedName("shovelDiamond").setTextureName("diamond_shovel"));
        field_150901_e.func_148756_a(278, "diamond_pickaxe", (new ItemPickaxe(Item.ToolMaterial.EMERALD)).setUnlocalizedName("pickaxeDiamond").setTextureName("diamond_pickaxe"));
        field_150901_e.func_148756_a(279, "diamond_axe", (new ItemAxe(Item.ToolMaterial.EMERALD)).setUnlocalizedName("hatchetDiamond").setTextureName("diamond_axe"));
        field_150901_e.func_148756_a(280, "stick", (new Item()).setFull3D().setUnlocalizedName("stick").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("stick"));
        field_150901_e.func_148756_a(281, "bowl", (new Item()).setUnlocalizedName("bowl").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("bowl"));
        field_150901_e.func_148756_a(282, "mushroom_stew", (new ItemSoup(6)).setUnlocalizedName("mushroomStew").setTextureName("mushroom_stew"));
        field_150901_e.func_148756_a(283, "golden_sword", (new ItemSword(Item.ToolMaterial.GOLD)).setUnlocalizedName("swordGold").setTextureName("gold_sword"));
        field_150901_e.func_148756_a(284, "golden_shovel", (new ItemSpade(Item.ToolMaterial.GOLD)).setUnlocalizedName("shovelGold").setTextureName("gold_shovel"));
        field_150901_e.func_148756_a(285, "golden_pickaxe", (new ItemPickaxe(Item.ToolMaterial.GOLD)).setUnlocalizedName("pickaxeGold").setTextureName("gold_pickaxe"));
        field_150901_e.func_148756_a(286, "golden_axe", (new ItemAxe(Item.ToolMaterial.GOLD)).setUnlocalizedName("hatchetGold").setTextureName("gold_axe"));
        field_150901_e.func_148756_a(287, "string", (new ItemReed(Blocks.field_150473_bD)).setUnlocalizedName("string").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("string"));
        field_150901_e.func_148756_a(288, "feather", (new Item()).setUnlocalizedName("feather").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("feather"));
        field_150901_e.func_148756_a(289, "gunpowder", (new Item()).setUnlocalizedName("sulphur").setPotionEffect(PotionHelper.gunpowderEffect).setCreativeTab(CreativeTabs.tabMaterials).setTextureName("gunpowder"));
        field_150901_e.func_148756_a(290, "wooden_hoe", (new ItemHoe(Item.ToolMaterial.WOOD)).setUnlocalizedName("hoeWood").setTextureName("wood_hoe"));
        field_150901_e.func_148756_a(291, "stone_hoe", (new ItemHoe(Item.ToolMaterial.STONE)).setUnlocalizedName("hoeStone").setTextureName("stone_hoe"));
        field_150901_e.func_148756_a(292, "iron_hoe", (new ItemHoe(Item.ToolMaterial.IRON)).setUnlocalizedName("hoeIron").setTextureName("iron_hoe"));
        field_150901_e.func_148756_a(293, "diamond_hoe", (new ItemHoe(Item.ToolMaterial.EMERALD)).setUnlocalizedName("hoeDiamond").setTextureName("diamond_hoe"));
        field_150901_e.func_148756_a(294, "golden_hoe", (new ItemHoe(Item.ToolMaterial.GOLD)).setUnlocalizedName("hoeGold").setTextureName("gold_hoe"));
        field_150901_e.func_148756_a(295, "wheat_seeds", (new ItemSeeds(Blocks.field_150464_aj, Blocks.field_150458_ak)).setUnlocalizedName("seeds").setTextureName("seeds_wheat"));
        field_150901_e.func_148756_a(296, "wheat", (new Item()).setUnlocalizedName("wheat").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("wheat"));
        field_150901_e.func_148756_a(297, "bread", (new ItemFood(5, 0.6F, false)).setUnlocalizedName("bread").setTextureName("bread"));
        field_150901_e.func_148756_a(298, "leather_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.CLOTH, 0, 0)).setUnlocalizedName("helmetCloth").setTextureName("leather_helmet"));
        field_150901_e.func_148756_a(299, "leather_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.CLOTH, 0, 1)).setUnlocalizedName("chestplateCloth").setTextureName("leather_chestplate"));
        field_150901_e.func_148756_a(300, "leather_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.CLOTH, 0, 2)).setUnlocalizedName("leggingsCloth").setTextureName("leather_leggings"));
        field_150901_e.func_148756_a(301, "leather_boots", (new ItemArmor(ItemArmor.ArmorMaterial.CLOTH, 0, 3)).setUnlocalizedName("bootsCloth").setTextureName("leather_boots"));
        field_150901_e.func_148756_a(302, "chainmail_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 0)).setUnlocalizedName("helmetChain").setTextureName("chainmail_helmet"));
        field_150901_e.func_148756_a(303, "chainmail_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 1)).setUnlocalizedName("chestplateChain").setTextureName("chainmail_chestplate"));
        field_150901_e.func_148756_a(304, "chainmail_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 2)).setUnlocalizedName("leggingsChain").setTextureName("chainmail_leggings"));
        field_150901_e.func_148756_a(305, "chainmail_boots", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 3)).setUnlocalizedName("bootsChain").setTextureName("chainmail_boots"));
        field_150901_e.func_148756_a(306, "iron_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 0)).setUnlocalizedName("helmetIron").setTextureName("iron_helmet"));
        field_150901_e.func_148756_a(307, "iron_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 1)).setUnlocalizedName("chestplateIron").setTextureName("iron_chestplate"));
        field_150901_e.func_148756_a(308, "iron_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 2)).setUnlocalizedName("leggingsIron").setTextureName("iron_leggings"));
        field_150901_e.func_148756_a(309, "iron_boots", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 3)).setUnlocalizedName("bootsIron").setTextureName("iron_boots"));
        field_150901_e.func_148756_a(310, "diamond_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 0)).setUnlocalizedName("helmetDiamond").setTextureName("diamond_helmet"));
        field_150901_e.func_148756_a(311, "diamond_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 1)).setUnlocalizedName("chestplateDiamond").setTextureName("diamond_chestplate"));
        field_150901_e.func_148756_a(312, "diamond_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 2)).setUnlocalizedName("leggingsDiamond").setTextureName("diamond_leggings"));
        field_150901_e.func_148756_a(313, "diamond_boots", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 3)).setUnlocalizedName("bootsDiamond").setTextureName("diamond_boots"));
        field_150901_e.func_148756_a(314, "golden_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 0)).setUnlocalizedName("helmetGold").setTextureName("gold_helmet"));
        field_150901_e.func_148756_a(315, "golden_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 1)).setUnlocalizedName("chestplateGold").setTextureName("gold_chestplate"));
        field_150901_e.func_148756_a(316, "golden_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 2)).setUnlocalizedName("leggingsGold").setTextureName("gold_leggings"));
        field_150901_e.func_148756_a(317, "golden_boots", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 3)).setUnlocalizedName("bootsGold").setTextureName("gold_boots"));
        field_150901_e.func_148756_a(318, "flint", (new Item()).setUnlocalizedName("flint").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("flint"));
        field_150901_e.func_148756_a(319, "porkchop", (new ItemFood(3, 0.3F, true)).setUnlocalizedName("porkchopRaw").setTextureName("porkchop_raw"));
        field_150901_e.func_148756_a(320, "cooked_porkchop", (new ItemFood(8, 0.8F, true)).setUnlocalizedName("porkchopCooked").setTextureName("porkchop_cooked"));
        field_150901_e.func_148756_a(321, "painting", (new ItemHangingEntity(EntityPainting.class)).setUnlocalizedName("painting").setTextureName("painting"));
        field_150901_e.func_148756_a(322, "golden_apple", (new ItemAppleGold(4, 1.2F, false)).setAlwaysEdible().setPotionEffect(Potion.regeneration.id, 5, 1, 1.0F).setUnlocalizedName("appleGold").setTextureName("apple_golden"));
        field_150901_e.func_148756_a(323, "sign", (new ItemSign()).setUnlocalizedName("sign").setTextureName("sign"));
        field_150901_e.func_148756_a(324, "wooden_door", (new ItemDoor(Material.field_151575_d)).setUnlocalizedName("doorWood").setTextureName("door_wood"));
        Item var0 = (new ItemBucket(Blocks.field_150350_a)).setUnlocalizedName("bucket").setMaxStackSize(16).setTextureName("bucket_empty");
        field_150901_e.func_148756_a(325, "bucket", var0);
        field_150901_e.func_148756_a(326, "water_bucket", (new ItemBucket(Blocks.field_150358_i)).setUnlocalizedName("bucketWater").setContainerItem(var0).setTextureName("bucket_water"));
        field_150901_e.func_148756_a(327, "lava_bucket", (new ItemBucket(Blocks.field_150356_k)).setUnlocalizedName("bucketLava").setContainerItem(var0).setTextureName("bucket_lava"));
        field_150901_e.func_148756_a(328, "minecart", (new ItemMinecart(0)).setUnlocalizedName("minecart").setTextureName("minecart_normal"));
        field_150901_e.func_148756_a(329, "saddle", (new ItemSaddle()).setUnlocalizedName("saddle").setTextureName("saddle"));
        field_150901_e.func_148756_a(330, "iron_door", (new ItemDoor(Material.field_151573_f)).setUnlocalizedName("doorIron").setTextureName("door_iron"));
        field_150901_e.func_148756_a(331, "redstone", (new ItemRedstone()).setUnlocalizedName("redstone").setPotionEffect(PotionHelper.redstoneEffect).setTextureName("redstone_dust"));
        field_150901_e.func_148756_a(332, "snowball", (new ItemSnowball()).setUnlocalizedName("snowball").setTextureName("snowball"));
        field_150901_e.func_148756_a(333, "boat", (new ItemBoat()).setUnlocalizedName("boat").setTextureName("boat"));
        field_150901_e.func_148756_a(334, "leather", (new Item()).setUnlocalizedName("leather").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("leather"));
        field_150901_e.func_148756_a(335, "milk_bucket", (new ItemBucketMilk()).setUnlocalizedName("milk").setContainerItem(var0).setTextureName("bucket_milk"));
        field_150901_e.func_148756_a(336, "brick", (new Item()).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("brick"));
        field_150901_e.func_148756_a(337, "clay_ball", (new Item()).setUnlocalizedName("clay").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("clay_ball"));
        field_150901_e.func_148756_a(338, "reeds", (new ItemReed(Blocks.field_150436_aH)).setUnlocalizedName("reeds").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("reeds"));
        field_150901_e.func_148756_a(339, "paper", (new Item()).setUnlocalizedName("paper").setCreativeTab(CreativeTabs.tabMisc).setTextureName("paper"));
        field_150901_e.func_148756_a(340, "book", (new ItemBook()).setUnlocalizedName("book").setCreativeTab(CreativeTabs.tabMisc).setTextureName("book_normal"));
        field_150901_e.func_148756_a(341, "slime_ball", (new Item()).setUnlocalizedName("slimeball").setCreativeTab(CreativeTabs.tabMisc).setTextureName("slimeball"));
        field_150901_e.func_148756_a(342, "chest_minecart", (new ItemMinecart(1)).setUnlocalizedName("minecartChest").setTextureName("minecart_chest"));
        field_150901_e.func_148756_a(343, "furnace_minecart", (new ItemMinecart(2)).setUnlocalizedName("minecartFurnace").setTextureName("minecart_furnace"));
        field_150901_e.func_148756_a(344, "egg", (new ItemEgg()).setUnlocalizedName("egg").setTextureName("egg"));
        field_150901_e.func_148756_a(345, "compass", (new Item()).setUnlocalizedName("compass").setCreativeTab(CreativeTabs.tabTools).setTextureName("compass"));
        field_150901_e.func_148756_a(346, "fishing_rod", (new ItemFishingRod()).setUnlocalizedName("fishingRod").setTextureName("fishing_rod"));
        field_150901_e.func_148756_a(347, "clock", (new Item()).setUnlocalizedName("clock").setCreativeTab(CreativeTabs.tabTools).setTextureName("clock"));
        field_150901_e.func_148756_a(348, "glowstone_dust", (new Item()).setUnlocalizedName("yellowDust").setPotionEffect(PotionHelper.glowstoneEffect).setCreativeTab(CreativeTabs.tabMaterials).setTextureName("glowstone_dust"));
        field_150901_e.func_148756_a(349, "fish", (new ItemFishFood(false)).setUnlocalizedName("fish").setTextureName("fish_raw").setHasSubtypes(true));
        field_150901_e.func_148756_a(350, "cooked_fished", (new ItemFishFood(true)).setUnlocalizedName("fish").setTextureName("fish_cooked").setHasSubtypes(true));
        field_150901_e.func_148756_a(351, "dye", (new ItemDye()).setUnlocalizedName("dyePowder").setTextureName("dye_powder"));
        field_150901_e.func_148756_a(352, "bone", (new Item()).setUnlocalizedName("bone").setFull3D().setCreativeTab(CreativeTabs.tabMisc).setTextureName("bone"));
        field_150901_e.func_148756_a(353, "sugar", (new Item()).setUnlocalizedName("sugar").setPotionEffect(PotionHelper.sugarEffect).setCreativeTab(CreativeTabs.tabMaterials).setTextureName("sugar"));
        field_150901_e.func_148756_a(354, "cake", (new ItemReed(Blocks.field_150414_aQ)).setMaxStackSize(1).setUnlocalizedName("cake").setCreativeTab(CreativeTabs.tabFood).setTextureName("cake"));
        field_150901_e.func_148756_a(355, "bed", (new ItemBed()).setMaxStackSize(1).setUnlocalizedName("bed").setTextureName("bed"));
        field_150901_e.func_148756_a(356, "repeater", (new ItemReed(Blocks.field_150413_aR)).setUnlocalizedName("diode").setCreativeTab(CreativeTabs.tabRedstone).setTextureName("repeater"));
        field_150901_e.func_148756_a(357, "cookie", (new ItemFood(2, 0.1F, false)).setUnlocalizedName("cookie").setTextureName("cookie"));
        field_150901_e.func_148756_a(358, "filled_map", (new ItemMap()).setUnlocalizedName("map").setTextureName("map_filled"));
        field_150901_e.func_148756_a(359, "shears", (new ItemShears()).setUnlocalizedName("shears").setTextureName("shears"));
        field_150901_e.func_148756_a(360, "melon", (new ItemFood(2, 0.3F, false)).setUnlocalizedName("melon").setTextureName("melon"));
        field_150901_e.func_148756_a(361, "pumpkin_seeds", (new ItemSeeds(Blocks.field_150393_bb, Blocks.field_150458_ak)).setUnlocalizedName("seeds_pumpkin").setTextureName("seeds_pumpkin"));
        field_150901_e.func_148756_a(362, "melon_seeds", (new ItemSeeds(Blocks.field_150394_bc, Blocks.field_150458_ak)).setUnlocalizedName("seeds_melon").setTextureName("seeds_melon"));
        field_150901_e.func_148756_a(363, "beef", (new ItemFood(3, 0.3F, true)).setUnlocalizedName("beefRaw").setTextureName("beef_raw"));
        field_150901_e.func_148756_a(364, "cooked_beef", (new ItemFood(8, 0.8F, true)).setUnlocalizedName("beefCooked").setTextureName("beef_cooked"));
        field_150901_e.func_148756_a(365, "chicken", (new ItemFood(2, 0.3F, true)).setPotionEffect(Potion.hunger.id, 30, 0, 0.3F).setUnlocalizedName("chickenRaw").setTextureName("chicken_raw"));
        field_150901_e.func_148756_a(366, "cooked_chicken", (new ItemFood(6, 0.6F, true)).setUnlocalizedName("chickenCooked").setTextureName("chicken_cooked"));
        field_150901_e.func_148756_a(367, "rotten_flesh", (new ItemFood(4, 0.1F, true)).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setUnlocalizedName("rottenFlesh").setTextureName("rotten_flesh"));
        field_150901_e.func_148756_a(368, "ender_pearl", (new ItemEnderPearl()).setUnlocalizedName("enderPearl").setTextureName("ender_pearl"));
        field_150901_e.func_148756_a(369, "blaze_rod", (new Item()).setUnlocalizedName("blazeRod").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("blaze_rod"));
        field_150901_e.func_148756_a(370, "ghast_tear", (new Item()).setUnlocalizedName("ghastTear").setPotionEffect(PotionHelper.ghastTearEffect).setCreativeTab(CreativeTabs.tabBrewing).setTextureName("ghast_tear"));
        field_150901_e.func_148756_a(371, "gold_nugget", (new Item()).setUnlocalizedName("goldNugget").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("gold_nugget"));
        field_150901_e.func_148756_a(372, "nether_wart", (new ItemSeeds(Blocks.field_150388_bm, Blocks.field_150425_aM)).setUnlocalizedName("netherStalkSeeds").setPotionEffect("+4").setTextureName("nether_wart"));
        field_150901_e.func_148756_a(373, "potion", (new ItemPotion()).setUnlocalizedName("potion").setTextureName("potion"));
        field_150901_e.func_148756_a(374, "glass_bottle", (new ItemGlassBottle()).setUnlocalizedName("glassBottle").setTextureName("potion_bottle_empty"));
        field_150901_e.func_148756_a(375, "spider_eye", (new ItemFood(2, 0.8F, false)).setPotionEffect(Potion.poison.id, 5, 0, 1.0F).setUnlocalizedName("spiderEye").setPotionEffect(PotionHelper.spiderEyeEffect).setTextureName("spider_eye"));
        field_150901_e.func_148756_a(376, "fermented_spider_eye", (new Item()).setUnlocalizedName("fermentedSpiderEye").setPotionEffect(PotionHelper.fermentedSpiderEyeEffect).setCreativeTab(CreativeTabs.tabBrewing).setTextureName("spider_eye_fermented"));
        field_150901_e.func_148756_a(377, "blaze_powder", (new Item()).setUnlocalizedName("blazePowder").setPotionEffect(PotionHelper.blazePowderEffect).setCreativeTab(CreativeTabs.tabBrewing).setTextureName("blaze_powder"));
        field_150901_e.func_148756_a(378, "magma_cream", (new Item()).setUnlocalizedName("magmaCream").setPotionEffect(PotionHelper.magmaCreamEffect).setCreativeTab(CreativeTabs.tabBrewing).setTextureName("magma_cream"));
        field_150901_e.func_148756_a(379, "brewing_stand", (new ItemReed(Blocks.field_150382_bo)).setUnlocalizedName("brewingStand").setCreativeTab(CreativeTabs.tabBrewing).setTextureName("brewing_stand"));
        field_150901_e.func_148756_a(380, "cauldron", (new ItemReed(Blocks.field_150383_bp)).setUnlocalizedName("cauldron").setCreativeTab(CreativeTabs.tabBrewing).setTextureName("cauldron"));
        field_150901_e.func_148756_a(381, "ender_eye", (new ItemEnderEye()).setUnlocalizedName("eyeOfEnder").setTextureName("ender_eye"));
        field_150901_e.func_148756_a(382, "speckled_melon", (new Item()).setUnlocalizedName("speckledMelon").setPotionEffect(PotionHelper.speckledMelonEffect).setCreativeTab(CreativeTabs.tabBrewing).setTextureName("melon_speckled"));
        field_150901_e.func_148756_a(383, "spawn_egg", (new ItemMonsterPlacer()).setUnlocalizedName("monsterPlacer").setTextureName("spawn_egg"));
        field_150901_e.func_148756_a(384, "experience_bottle", (new ItemExpBottle()).setUnlocalizedName("expBottle").setTextureName("experience_bottle"));
        field_150901_e.func_148756_a(385, "fire_charge", (new ItemFireball()).setUnlocalizedName("fireball").setTextureName("fireball"));
        field_150901_e.func_148756_a(386, "writable_book", (new ItemWritableBook()).setUnlocalizedName("writingBook").setCreativeTab(CreativeTabs.tabMisc).setTextureName("book_writable"));
        field_150901_e.func_148756_a(387, "written_book", (new ItemEditableBook()).setUnlocalizedName("writtenBook").setTextureName("book_written").setMaxStackSize(16));
        field_150901_e.func_148756_a(388, "emerald", (new Item()).setUnlocalizedName("emerald").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("emerald"));
        field_150901_e.func_148756_a(389, "item_frame", (new ItemHangingEntity(EntityItemFrame.class)).setUnlocalizedName("frame").setTextureName("item_frame"));
        field_150901_e.func_148756_a(390, "flower_pot", (new ItemReed(Blocks.field_150457_bL)).setUnlocalizedName("flowerPot").setCreativeTab(CreativeTabs.tabDecorations).setTextureName("flower_pot"));
        field_150901_e.func_148756_a(391, "carrot", (new ItemSeedFood(4, 0.6F, Blocks.field_150459_bM, Blocks.field_150458_ak)).setUnlocalizedName("carrots").setTextureName("carrot"));
        field_150901_e.func_148756_a(392, "potato", (new ItemSeedFood(1, 0.3F, Blocks.field_150469_bN, Blocks.field_150458_ak)).setUnlocalizedName("potato").setTextureName("potato"));
        field_150901_e.func_148756_a(393, "baked_potato", (new ItemFood(6, 0.6F, false)).setUnlocalizedName("potatoBaked").setTextureName("potato_baked"));
        field_150901_e.func_148756_a(394, "poisonous_potato", (new ItemFood(2, 0.3F, false)).setPotionEffect(Potion.poison.id, 5, 0, 0.6F).setUnlocalizedName("potatoPoisonous").setTextureName("potato_poisonous"));
        field_150901_e.func_148756_a(395, "map", (new ItemEmptyMap()).setUnlocalizedName("emptyMap").setTextureName("map_empty"));
        field_150901_e.func_148756_a(396, "golden_carrot", (new ItemFood(6, 1.2F, false)).setUnlocalizedName("carrotGolden").setPotionEffect(PotionHelper.goldenCarrotEffect).setTextureName("carrot_golden"));
        field_150901_e.func_148756_a(397, "skull", (new ItemSkull()).setUnlocalizedName("skull").setTextureName("skull"));
        field_150901_e.func_148756_a(398, "carrot_on_a_stick", (new ItemCarrotOnAStick()).setUnlocalizedName("carrotOnAStick").setTextureName("carrot_on_a_stick"));
        field_150901_e.func_148756_a(399, "nether_star", (new ItemSimpleFoiled()).setUnlocalizedName("netherStar").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("nether_star"));
        field_150901_e.func_148756_a(400, "pumpkin_pie", (new ItemFood(8, 0.3F, false)).setUnlocalizedName("pumpkinPie").setCreativeTab(CreativeTabs.tabFood).setTextureName("pumpkin_pie"));
        field_150901_e.func_148756_a(401, "fireworks", (new ItemFirework()).setUnlocalizedName("fireworks").setTextureName("fireworks"));
        field_150901_e.func_148756_a(402, "firework_charge", (new ItemFireworkCharge()).setUnlocalizedName("fireworksCharge").setCreativeTab(CreativeTabs.tabMisc).setTextureName("fireworks_charge"));
        field_150901_e.func_148756_a(403, "enchanted_book", (new ItemEnchantedBook()).setMaxStackSize(1).setUnlocalizedName("enchantedBook").setTextureName("book_enchanted"));
        field_150901_e.func_148756_a(404, "comparator", (new ItemReed(Blocks.field_150441_bU)).setUnlocalizedName("comparator").setCreativeTab(CreativeTabs.tabRedstone).setTextureName("comparator"));
        field_150901_e.func_148756_a(405, "netherbrick", (new Item()).setUnlocalizedName("netherbrick").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("netherbrick"));
        field_150901_e.func_148756_a(406, "quartz", (new Item()).setUnlocalizedName("netherquartz").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("quartz"));
        field_150901_e.func_148756_a(407, "tnt_minecart", (new ItemMinecart(3)).setUnlocalizedName("minecartTnt").setTextureName("minecart_tnt"));
        field_150901_e.func_148756_a(408, "hopper_minecart", (new ItemMinecart(5)).setUnlocalizedName("minecartHopper").setTextureName("minecart_hopper"));
        field_150901_e.func_148756_a(417, "iron_horse_armor", (new Item()).setUnlocalizedName("horsearmormetal").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc).setTextureName("iron_horse_armor"));
        field_150901_e.func_148756_a(418, "golden_horse_armor", (new Item()).setUnlocalizedName("horsearmorgold").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc).setTextureName("gold_horse_armor"));
        field_150901_e.func_148756_a(419, "diamond_horse_armor", (new Item()).setUnlocalizedName("horsearmordiamond").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc).setTextureName("diamond_horse_armor"));
        field_150901_e.func_148756_a(420, "lead", (new ItemLead()).setUnlocalizedName("leash").setTextureName("lead"));
        field_150901_e.func_148756_a(421, "name_tag", (new ItemNameTag()).setUnlocalizedName("nameTag").setTextureName("name_tag"));
        field_150901_e.func_148756_a(422, "command_block_minecart", (new ItemMinecart(6)).setUnlocalizedName("minecartCommandBlock").setTextureName("minecart_command_block").setCreativeTab((CreativeTabs)null));
        field_150901_e.func_148756_a(2256, "record_13", (new ItemRecord("13")).setUnlocalizedName("record").setTextureName("record_13"));
        field_150901_e.func_148756_a(2257, "record_cat", (new ItemRecord("cat")).setUnlocalizedName("record").setTextureName("record_cat"));
        field_150901_e.func_148756_a(2258, "record_blocks", (new ItemRecord("blocks")).setUnlocalizedName("record").setTextureName("record_blocks"));
        field_150901_e.func_148756_a(2259, "record_chirp", (new ItemRecord("chirp")).setUnlocalizedName("record").setTextureName("record_chirp"));
        field_150901_e.func_148756_a(2260, "record_far", (new ItemRecord("far")).setUnlocalizedName("record").setTextureName("record_far"));
        field_150901_e.func_148756_a(2261, "record_mall", (new ItemRecord("mall")).setUnlocalizedName("record").setTextureName("record_mall"));
        field_150901_e.func_148756_a(2262, "record_mellohi", (new ItemRecord("mellohi")).setUnlocalizedName("record").setTextureName("record_mellohi"));
        field_150901_e.func_148756_a(2263, "record_stal", (new ItemRecord("stal")).setUnlocalizedName("record").setTextureName("record_stal"));
        field_150901_e.func_148756_a(2264, "record_strad", (new ItemRecord("strad")).setUnlocalizedName("record").setTextureName("record_strad"));
        field_150901_e.func_148756_a(2265, "record_ward", (new ItemRecord("ward")).setUnlocalizedName("record").setTextureName("record_ward"));
        field_150901_e.func_148756_a(2266, "record_11", (new ItemRecord("11")).setUnlocalizedName("record").setTextureName("record_11"));
        field_150901_e.func_148756_a(2267, "record_wait", (new ItemRecord("wait")).setUnlocalizedName("record").setTextureName("record_wait"));
        HashSet var1 = Sets.newHashSet(new Block[] {Blocks.field_150350_a, Blocks.field_150382_bo, Blocks.field_150324_C, Blocks.field_150388_bm, Blocks.field_150383_bp, Blocks.field_150457_bL, Blocks.field_150464_aj, Blocks.field_150436_aH, Blocks.field_150414_aQ, Blocks.field_150465_bP, Blocks.field_150332_K, Blocks.field_150326_M, Blocks.field_150439_ay, Blocks.field_150416_aS, Blocks.field_150393_bb, Blocks.field_150472_an, Blocks.field_150455_bV, Blocks.field_150473_bD, Blocks.field_150374_bv, Blocks.field_150394_bc, Blocks.field_150437_az, Blocks.field_150441_bU, Blocks.field_150488_af, Blocks.field_150444_as, Blocks.field_150413_aR, Blocks.field_150454_av, Blocks.field_150466_ao});
        Iterator var2 = Block.field_149771_c.func_148742_b().iterator();

        while (var2.hasNext())
        {
            String var3 = (String)var2.next();
            Block var4 = (Block)Block.field_149771_c.getObject(var3);
            Object var5;

            if (var4 == Blocks.field_150325_L)
            {
                var5 = (new ItemCloth(Blocks.field_150325_L)).setUnlocalizedName("cloth");
            }
            else if (var4 == Blocks.field_150406_ce)
            {
                var5 = (new ItemCloth(Blocks.field_150406_ce)).setUnlocalizedName("clayHardenedStained");
            }
            else if (var4 == Blocks.field_150399_cn)
            {
                var5 = (new ItemCloth(Blocks.field_150399_cn)).setUnlocalizedName("stainedGlass");
            }
            else if (var4 == Blocks.field_150397_co)
            {
                var5 = (new ItemCloth(Blocks.field_150397_co)).setUnlocalizedName("stainedGlassPane");
            }
            else if (var4 == Blocks.field_150404_cg)
            {
                var5 = (new ItemCloth(Blocks.field_150404_cg)).setUnlocalizedName("woolCarpet");
            }
            else if (var4 == Blocks.field_150346_d)
            {
                var5 = (new ItemMultiTexture(Blocks.field_150346_d, Blocks.field_150346_d, BlockDirt.field_150009_a)).setUnlocalizedName("dirt");
            }
            else if (var4 == Blocks.field_150354_m)
            {
                var5 = (new ItemMultiTexture(Blocks.field_150354_m, Blocks.field_150354_m, BlockSand.field_149838_a)).setUnlocalizedName("sand");
            }
            else if (var4 == Blocks.field_150364_r)
            {
                var5 = (new ItemMultiTexture(Blocks.field_150364_r, Blocks.field_150364_r, BlockOldLog.field_150168_M)).setUnlocalizedName("log");
            }
            else if (var4 == Blocks.field_150363_s)
            {
                var5 = (new ItemMultiTexture(Blocks.field_150363_s, Blocks.field_150363_s, BlockNewLog.field_150169_M)).setUnlocalizedName("log");
            }
            else if (var4 == Blocks.field_150344_f)
            {
                var5 = (new ItemMultiTexture(Blocks.field_150344_f, Blocks.field_150344_f, BlockWood.field_150096_a)).setUnlocalizedName("wood");
            }
            else if (var4 == Blocks.field_150418_aU)
            {
                var5 = (new ItemMultiTexture(Blocks.field_150418_aU, Blocks.field_150418_aU, BlockSilverfish.field_150198_a)).setUnlocalizedName("monsterStoneEgg");
            }
            else if (var4 == Blocks.field_150417_aV)
            {
                var5 = (new ItemMultiTexture(Blocks.field_150417_aV, Blocks.field_150417_aV, BlockStoneBrick.field_150142_a)).setUnlocalizedName("stonebricksmooth");
            }
            else if (var4 == Blocks.field_150322_A)
            {
                var5 = (new ItemMultiTexture(Blocks.field_150322_A, Blocks.field_150322_A, BlockSandStone.field_150157_a)).setUnlocalizedName("sandStone");
            }
            else if (var4 == Blocks.field_150371_ca)
            {
                var5 = (new ItemMultiTexture(Blocks.field_150371_ca, Blocks.field_150371_ca, BlockQuartz.field_150191_a)).setUnlocalizedName("quartzBlock");
            }
            else if (var4 == Blocks.field_150333_U)
            {
                var5 = (new ItemSlab(Blocks.field_150333_U, Blocks.field_150333_U, Blocks.field_150334_T, false)).setUnlocalizedName("stoneSlab");
            }
            else if (var4 == Blocks.field_150334_T)
            {
                var5 = (new ItemSlab(Blocks.field_150334_T, Blocks.field_150333_U, Blocks.field_150334_T, true)).setUnlocalizedName("stoneSlab");
            }
            else if (var4 == Blocks.field_150376_bx)
            {
                var5 = (new ItemSlab(Blocks.field_150376_bx, Blocks.field_150376_bx, Blocks.field_150373_bw, false)).setUnlocalizedName("woodSlab");
            }
            else if (var4 == Blocks.field_150373_bw)
            {
                var5 = (new ItemSlab(Blocks.field_150373_bw, Blocks.field_150376_bx, Blocks.field_150373_bw, true)).setUnlocalizedName("woodSlab");
            }
            else if (var4 == Blocks.field_150345_g)
            {
                var5 = (new ItemMultiTexture(Blocks.field_150345_g, Blocks.field_150345_g, BlockSapling.field_149882_a)).setUnlocalizedName("sapling");
            }
            else if (var4 == Blocks.field_150362_t)
            {
                var5 = (new ItemLeaves(Blocks.field_150362_t)).setUnlocalizedName("leaves");
            }
            else if (var4 == Blocks.field_150361_u)
            {
                var5 = (new ItemLeaves(Blocks.field_150361_u)).setUnlocalizedName("leaves");
            }
            else if (var4 == Blocks.field_150395_bd)
            {
                var5 = new ItemColored(Blocks.field_150395_bd, false);
            }
            else if (var4 == Blocks.field_150329_H)
            {
                var5 = (new ItemColored(Blocks.field_150329_H, true)).func_150943_a(new String[] {"shrub", "grass", "fern"});
            }
            else if (var4 == Blocks.field_150327_N)
            {
                var5 = (new ItemMultiTexture(Blocks.field_150327_N, Blocks.field_150327_N, BlockFlower.field_149858_b)).setUnlocalizedName("flower");
            }
            else if (var4 == Blocks.field_150328_O)
            {
                var5 = (new ItemMultiTexture(Blocks.field_150328_O, Blocks.field_150328_O, BlockFlower.field_149859_a)).setUnlocalizedName("rose");
            }
            else if (var4 == Blocks.field_150431_aC)
            {
                var5 = new ItemSnow(Blocks.field_150431_aC, Blocks.field_150431_aC);
            }
            else if (var4 == Blocks.field_150392_bi)
            {
                var5 = new ItemLilyPad(Blocks.field_150392_bi);
            }
            else if (var4 == Blocks.field_150331_J)
            {
                var5 = new ItemPiston(Blocks.field_150331_J);
            }
            else if (var4 == Blocks.field_150320_F)
            {
                var5 = new ItemPiston(Blocks.field_150320_F);
            }
            else if (var4 == Blocks.field_150463_bK)
            {
                var5 = (new ItemMultiTexture(Blocks.field_150463_bK, Blocks.field_150463_bK, BlockWall.field_150092_a)).setUnlocalizedName("cobbleWall");
            }
            else if (var4 == Blocks.field_150467_bQ)
            {
                var5 = (new ItemAnvilBlock(Blocks.field_150467_bQ)).setUnlocalizedName("anvil");
            }
            else if (var4 == Blocks.field_150398_cm)
            {
                var5 = (new ItemDoublePlant(Blocks.field_150398_cm, Blocks.field_150398_cm, BlockDoublePlant.field_149892_a)).setUnlocalizedName("doublePlant");
            }
            else
            {
                if (var1.contains(var4))
                {
                    continue;
                }

                var5 = new ItemBlock(var4);
            }

            field_150901_e.func_148756_a(Block.func_149682_b(var4), var3, var5);
        }
    }

    public Item setMaxStackSize(int par1)
    {
        this.maxStackSize = par1;
        return this;
    }

    /**
     * Returns 0 for /terrain.png, 1 for /gui/items.png
     */
    public int getSpriteNumber()
    {
        return 1;
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public IIcon getIconFromDamage(int par1)
    {
        return this.itemIcon;
    }

    /**
     * Returns the icon index of the stack given as argument.
     */
    public final IIcon getIconIndex(ItemStack par1ItemStack)
    {
        return this.getIconFromDamage(par1ItemStack.getItemDamage());
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        return false;
    }

    public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
    {
        return 1.0F;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    /**
     * Returns the maximum size of the stack for a specific item. *Isn't this more a Set than a Get?*
     */
    public int getItemStackLimit()
    {
        return this.maxStackSize;
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return 0;
    }

    public boolean getHasSubtypes()
    {
        return this.hasSubtypes;
    }

    protected Item setHasSubtypes(boolean par1)
    {
        this.hasSubtypes = par1;
        return this;
    }

    /**
     * Returns the maximum damage an item can take.
     */
    public int getMaxDamage()
    {
        return this.maxDamage;
    }

    /**
     * set max damage of an Item
     */
    protected Item setMaxDamage(int par1)
    {
        this.maxDamage = par1;
        return this;
    }

    public boolean isDamageable()
    {
        return this.maxDamage > 0 && !this.hasSubtypes;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
        return false;
    }

    public boolean func_150894_a(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
    {
        return false;
    }

    public boolean func_150897_b(Block p_150897_1_)
    {
        return false;
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase)
    {
        return false;
    }

    /**
     * Sets bFull3D to True and return the object.
     */
    public Item setFull3D()
    {
        this.bFull3D = true;
        return this;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return this.bFull3D;
    }

    /**
     * Returns true if this item should be rotated by 180 degrees around the Y axis when being held in an entities
     * hands.
     */
    public boolean shouldRotateAroundWhenRendering()
    {
        return false;
    }

    /**
     * Sets the unlocalized name of this item to the string passed as the parameter, prefixed by "item."
     */
    public Item setUnlocalizedName(String par1Str)
    {
        this.unlocalizedName = par1Str;
        return this;
    }

    /**
     * Translates the unlocalized name of this item, but without the .name suffix, so the translation fails and the
     * unlocalized name itself is returned.
     */
    public String getUnlocalizedNameInefficiently(ItemStack par1ItemStack)
    {
        String var2 = this.getUnlocalizedName(par1ItemStack);
        return var2 == null ? "" : StatCollector.translateToLocal(var2);
    }

    /**
     * Returns the unlocalized name of this item.
     */
    public String getUnlocalizedName()
    {
        return "item." + this.unlocalizedName;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return "item." + this.unlocalizedName;
    }

    public Item setContainerItem(Item par1Item)
    {
        this.containerItem = par1Item;
        return this;
    }

    /**
     * If this returns true, after a recipe involving this item is crafted the container item will be added to the
     * player's inventory instead of remaining in the crafting grid.
     */
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack)
    {
        return true;
    }

    /**
     * If this function returns true (or the item is damageable), the ItemStack's NBT tag will be sent to the client.
     */
    public boolean getShareTag()
    {
        return true;
    }

    public Item getContainerItem()
    {
        return this.containerItem;
    }

    /**
     * True if this Item has a container item (a.k.a. crafting result)
     */
    public boolean hasContainerItem()
    {
        return this.containerItem != null;
    }

    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        return 16777215;
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {}

    /**
     * Called when item is crafted/smelted. Used only by maps so far.
     */
    public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {}

    /**
     * false for all Items except sub-classes of ItemMapBase
     */
    public boolean isMap()
    {
        return false;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.none;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 0;
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {}

    /**
     * Sets the string representing this item's effect on a potion when used as an ingredient.
     */
    protected Item setPotionEffect(String par1Str)
    {
        this.potionEffect = par1Str;
        return this;
    }

    public String func_150896_i(ItemStack p_150896_1_)
    {
        return this.potionEffect;
    }

    public boolean func_150892_m(ItemStack p_150892_1_)
    {
        return this.func_150896_i(p_150892_1_) != null;
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {}

    public String getItemStackDisplayName(ItemStack par1ItemStack)
    {
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(par1ItemStack) + ".name")).trim();
    }

    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return par1ItemStack.isItemEnchanted();
    }

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return par1ItemStack.isItemEnchanted() ? EnumRarity.rare : EnumRarity.common;
    }

    /**
     * Checks isDamagable and if it cannot be stacked
     */
    public boolean isItemTool(ItemStack par1ItemStack)
    {
        return this.getItemStackLimit() == 1 && this.isDamageable();
    }

    protected MovingObjectPosition getMovingObjectPositionFromPlayer(World par1World, EntityPlayer par2EntityPlayer, boolean par3)
    {
        float var4 = 1.0F;
        float var5 = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * var4;
        float var6 = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * var4;
        double var7 = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * (double)var4;
        double var9 = par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * (double)var4 + 1.62D - (double)par2EntityPlayer.yOffset;
        double var11 = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * (double)var4;
        Vec3 var13 = par1World.getWorldVec3Pool().getVecFromPool(var7, var9, var11);
        float var14 = MathHelper.cos(-var6 * 0.017453292F - (float)Math.PI);
        float var15 = MathHelper.sin(-var6 * 0.017453292F - (float)Math.PI);
        float var16 = -MathHelper.cos(-var5 * 0.017453292F);
        float var17 = MathHelper.sin(-var5 * 0.017453292F);
        float var18 = var15 * var16;
        float var20 = var14 * var16;
        double var21 = 5.0D;
        Vec3 var23 = var13.addVector((double)var18 * var21, (double)var17 * var21, (double)var20 * var21);
        return par1World.func_147447_a(var13, var23, par3, !par3, false);
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return 0;
    }

    public boolean requiresMultipleRenderPasses()
    {
        return false;
    }

    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public IIcon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return this.getIconFromDamage(par1);
    }

    public void func_150895_a(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
    }

    /**
     * gets the CreativeTab this item is displayed on
     */
    public CreativeTabs getCreativeTab()
    {
        return this.tabToDisplayOn;
    }

    /**
     * returns this;
     */
    public Item setCreativeTab(CreativeTabs par1CreativeTabs)
    {
        this.tabToDisplayOn = par1CreativeTabs;
        return this;
    }

    /**
     * Returns true if players can use this item to affect the world (e.g. placing blocks, placing ender eyes in portal)
     * when not in creative
     */
    public boolean canItemEditBlocks()
    {
        return true;
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return false;
    }

    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(this.getIconString());
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap getItemAttributeModifiers()
    {
        return HashMultimap.create();
    }

    protected Item setTextureName(String par1Str)
    {
        this.iconString = par1Str;
        return this;
    }

    /**
     * Returns the string associated with this Item's Icon.
     */
    protected String getIconString()
    {
        return this.iconString == null ? "MISSING_ICON_ITEM_" + field_150901_e.func_148757_b(this) + "_" + this.unlocalizedName : this.iconString;
    }

    public static enum ToolMaterial
    {
        WOOD("WOOD", 0, 0, 59, 2.0F, 0.0F, 15),
        STONE("STONE", 1, 1, 131, 4.0F, 1.0F, 5),
        IRON("IRON", 2, 2, 250, 6.0F, 2.0F, 14),
        EMERALD("EMERALD", 3, 3, 1561, 8.0F, 3.0F, 10),
        GOLD("GOLD", 4, 0, 32, 12.0F, 0.0F, 22);
        private final int harvestLevel;
        private final int maxUses;
        private final float efficiencyOnProperMaterial;
        private final float damageVsEntity;
        private final int enchantability;

        private static final Item.ToolMaterial[] $VALUES = new Item.ToolMaterial[]{WOOD, STONE, IRON, EMERALD, GOLD};
        private static final String __OBFID = "CL_00000042";

        private ToolMaterial(String par1Str, int par2, int par3, int par4, float par5, float par6, int par7)
        {
            this.harvestLevel = par3;
            this.maxUses = par4;
            this.efficiencyOnProperMaterial = par5;
            this.damageVsEntity = par6;
            this.enchantability = par7;
        }

        public int getMaxUses()
        {
            return this.maxUses;
        }

        public float getEfficiencyOnProperMaterial()
        {
            return this.efficiencyOnProperMaterial;
        }

        public float getDamageVsEntity()
        {
            return this.damageVsEntity;
        }

        public int getHarvestLevel()
        {
            return this.harvestLevel;
        }

        public int getEnchantability()
        {
            return this.enchantability;
        }

        public Item func_150995_f()
        {
            return this == WOOD ? Item.func_150898_a(Blocks.field_150344_f) : (this == STONE ? Item.func_150898_a(Blocks.field_150347_e) : (this == GOLD ? Items.field_151043_k : (this == IRON ? Items.field_151042_j : (this == EMERALD ? Items.field_151045_i : null))));
        }
    }
}
