package net.minecraft.stats;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonSerializableSet;

public class AchievementList
{
    /** Is the smallest column used to display a achievement on the GUI. */
    public static int minDisplayColumn;

    /** Is the smallest row used to display a achievement on the GUI. */
    public static int minDisplayRow;

    /** Is the biggest column used to display a achievement on the GUI. */
    public static int maxDisplayColumn;

    /** Is the biggest row used to display a achievement on the GUI. */
    public static int maxDisplayRow;

    /** Holds a list of all registered achievements. */
    public static List achievementList = new ArrayList();

    /** Is the 'open inventory' achievement. */
    public static Achievement openInventory = (new Achievement("achievement.openInventory", "openInventory", 0, 0, Items.field_151122_aG, (Achievement)null)).initIndependentStat().registerStat();

    /** Is the 'getting wood' achievement. */
    public static Achievement mineWood = (new Achievement("achievement.mineWood", "mineWood", 2, 1, Blocks.field_150364_r, openInventory)).registerStat();

    /** Is the 'benchmarking' achievement. */
    public static Achievement buildWorkBench = (new Achievement("achievement.buildWorkBench", "buildWorkBench", 4, -1, Blocks.field_150462_ai, mineWood)).registerStat();

    /** Is the 'time to mine' achievement. */
    public static Achievement buildPickaxe = (new Achievement("achievement.buildPickaxe", "buildPickaxe", 4, 2, Items.field_151039_o, buildWorkBench)).registerStat();

    /** Is the 'hot topic' achievement. */
    public static Achievement buildFurnace = (new Achievement("achievement.buildFurnace", "buildFurnace", 3, 4, Blocks.field_150460_al, buildPickaxe)).registerStat();

    /** Is the 'acquire hardware' achievement. */
    public static Achievement acquireIron = (new Achievement("achievement.acquireIron", "acquireIron", 1, 4, Items.field_151042_j, buildFurnace)).registerStat();

    /** Is the 'time to farm' achievement. */
    public static Achievement buildHoe = (new Achievement("achievement.buildHoe", "buildHoe", 2, -3, Items.field_151017_I, buildWorkBench)).registerStat();

    /** Is the 'bake bread' achievement. */
    public static Achievement makeBread = (new Achievement("achievement.makeBread", "makeBread", -1, -3, Items.field_151025_P, buildHoe)).registerStat();

    /** Is the 'the lie' achievement. */
    public static Achievement bakeCake = (new Achievement("achievement.bakeCake", "bakeCake", 0, -5, Items.field_151105_aU, buildHoe)).registerStat();

    /** Is the 'getting a upgrade' achievement. */
    public static Achievement buildBetterPickaxe = (new Achievement("achievement.buildBetterPickaxe", "buildBetterPickaxe", 6, 2, Items.field_151050_s, buildPickaxe)).registerStat();

    /** Is the 'delicious fish' achievement. */
    public static Achievement cookFish = (new Achievement("achievement.cookFish", "cookFish", 2, 6, Items.field_151101_aQ, buildFurnace)).registerStat();

    /** Is the 'on a rail' achievement */
    public static Achievement onARail = (new Achievement("achievement.onARail", "onARail", 2, 3, Blocks.field_150448_aq, acquireIron)).setSpecial().registerStat();

    /** Is the 'time to strike' achievement. */
    public static Achievement buildSword = (new Achievement("achievement.buildSword", "buildSword", 6, -1, Items.field_151041_m, buildWorkBench)).registerStat();

    /** Is the 'monster hunter' achievement. */
    public static Achievement killEnemy = (new Achievement("achievement.killEnemy", "killEnemy", 8, -1, Items.field_151103_aS, buildSword)).registerStat();

    /** is the 'cow tipper' achievement. */
    public static Achievement killCow = (new Achievement("achievement.killCow", "killCow", 7, -3, Items.field_151116_aA, buildSword)).registerStat();

    /** Is the 'when pig fly' achievement. */
    public static Achievement flyPig = (new Achievement("achievement.flyPig", "flyPig", 9, -3, Items.field_151141_av, killCow)).setSpecial().registerStat();

    /** The achievement for killing a Skeleton from 50 meters aways. */
    public static Achievement snipeSkeleton = (new Achievement("achievement.snipeSkeleton", "snipeSkeleton", 7, 0, Items.field_151031_f, killEnemy)).setSpecial().registerStat();

    /** Is the 'DIAMONDS!' achievement */
    public static Achievement diamonds = (new Achievement("achievement.diamonds", "diamonds", -1, 5, Blocks.field_150482_ag, acquireIron)).registerStat();
    public static Achievement field_150966_x = (new Achievement("achievement.diamondsToYou", "diamondsToYou", -1, 2, Items.field_151045_i, diamonds)).registerStat();

    /** Is the 'We Need to Go Deeper' achievement */
    public static Achievement portal = (new Achievement("achievement.portal", "portal", -1, 7, Blocks.field_150343_Z, diamonds)).registerStat();

    /** Is the 'Return to Sender' achievement */
    public static Achievement ghast = (new Achievement("achievement.ghast", "ghast", -4, 8, Items.field_151073_bk, portal)).setSpecial().registerStat();

    /** Is the 'Into Fire' achievement */
    public static Achievement blazeRod = (new Achievement("achievement.blazeRod", "blazeRod", 0, 9, Items.field_151072_bj, portal)).registerStat();

    /** Is the 'Local Brewery' achievement */
    public static Achievement potion = (new Achievement("achievement.potion", "potion", 2, 8, Items.field_151068_bn, blazeRod)).registerStat();

    /** Is the 'The End?' achievement */
    public static Achievement theEnd = (new Achievement("achievement.theEnd", "theEnd", 3, 10, Items.field_151061_bv, blazeRod)).setSpecial().registerStat();

    /** Is the 'The End.' achievement */
    public static Achievement theEnd2 = (new Achievement("achievement.theEnd2", "theEnd2", 4, 13, Blocks.field_150380_bt, theEnd)).setSpecial().registerStat();

    /** Is the 'Enchanter' achievement */
    public static Achievement enchantments = (new Achievement("achievement.enchantments", "enchantments", -4, 4, Blocks.field_150381_bn, diamonds)).registerStat();
    public static Achievement overkill = (new Achievement("achievement.overkill", "overkill", -4, 1, Items.field_151048_u, enchantments)).setSpecial().registerStat();

    /** Is the 'Librarian' achievement */
    public static Achievement bookcase = (new Achievement("achievement.bookcase", "bookcase", -3, 6, Blocks.field_150342_X, enchantments)).registerStat();
    public static Achievement field_150962_H = (new Achievement("achievement.breedCow", "breedCow", 7, -5, Items.field_151015_O, killCow)).registerStat();
    public static Achievement field_150963_I = (new Achievement("achievement.spawnWither", "spawnWither", 7, 12, new ItemStack(Items.field_151144_bL, 1, 1), theEnd2)).registerStat();
    public static Achievement field_150964_J = (new Achievement("achievement.killWither", "killWither", 7, 10, Items.field_151156_bN, field_150963_I)).registerStat();
    public static Achievement field_150965_K = (new Achievement("achievement.fullBeacon", "fullBeacon", 7, 8, Blocks.field_150461_bJ, field_150964_J)).setSpecial().registerStat();
    public static Achievement field_150961_L = (new Achievement("achievement.exploreAllBiomes", "exploreAllBiomes", 4, 8, Items.field_151175_af, theEnd)).func_150953_b(JsonSerializableSet.class).setSpecial().registerStat();
    private static final String __OBFID = "CL_00001467";

    /**
     * A stub functions called to make the static initializer for this class run.
     */
    public static void init() {}
}
