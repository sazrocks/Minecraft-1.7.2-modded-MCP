package net.minecraft.block.material;

public class Material
{
    public static final Material field_151579_a = new MaterialTransparent(MapColor.field_151660_b);
    public static final Material field_151577_b = new Material(MapColor.field_151661_c);
    public static final Material field_151578_c = new Material(MapColor.field_151664_l);
    public static final Material field_151575_d = (new Material(MapColor.field_151663_o)).setBurning();
    public static final Material field_151576_e = (new Material(MapColor.field_151665_m)).setRequiresTool();
    public static final Material field_151573_f = (new Material(MapColor.field_151668_h)).setRequiresTool();
    public static final Material field_151574_g = (new Material(MapColor.field_151668_h)).setRequiresTool().setImmovableMobility();
    public static final Material field_151586_h = (new MaterialLiquid(MapColor.field_151662_n)).setNoPushMobility();
    public static final Material field_151587_i = (new MaterialLiquid(MapColor.field_151656_f)).setNoPushMobility();
    public static final Material field_151584_j = (new Material(MapColor.field_151669_i)).setBurning().setTranslucent().setNoPushMobility();
    public static final Material field_151585_k = (new MaterialLogic(MapColor.field_151669_i)).setNoPushMobility();
    public static final Material field_151582_l = (new MaterialLogic(MapColor.field_151669_i)).setBurning().setNoPushMobility().setReplaceable();
    public static final Material field_151583_m = new Material(MapColor.field_151659_e);
    public static final Material field_151580_n = (new Material(MapColor.field_151659_e)).setBurning();
    public static final Material field_151581_o = (new MaterialTransparent(MapColor.field_151660_b)).setNoPushMobility();
    public static final Material field_151595_p = new Material(MapColor.field_151658_d);
    public static final Material field_151594_q = (new MaterialLogic(MapColor.field_151660_b)).setNoPushMobility();
    public static final Material field_151593_r = (new MaterialLogic(MapColor.field_151659_e)).setBurning();
    public static final Material field_151592_s = (new Material(MapColor.field_151660_b)).setTranslucent().setAdventureModeExempt();
    public static final Material field_151591_t = (new Material(MapColor.field_151660_b)).setAdventureModeExempt();
    public static final Material field_151590_u = (new Material(MapColor.field_151656_f)).setBurning().setTranslucent();
    public static final Material field_151589_v = (new Material(MapColor.field_151669_i)).setNoPushMobility();
    public static final Material field_151588_w = (new Material(MapColor.field_151657_g)).setTranslucent().setAdventureModeExempt();
    public static final Material field_151598_x = (new Material(MapColor.field_151657_g)).setAdventureModeExempt();
    public static final Material field_151597_y = (new MaterialLogic(MapColor.field_151666_j)).setReplaceable().setTranslucent().setRequiresTool().setNoPushMobility();
    public static final Material field_151596_z = (new Material(MapColor.field_151666_j)).setRequiresTool();
    public static final Material field_151570_A = (new Material(MapColor.field_151669_i)).setTranslucent().setNoPushMobility();
    public static final Material field_151571_B = new Material(MapColor.field_151667_k);
    public static final Material field_151572_C = (new Material(MapColor.field_151669_i)).setNoPushMobility();
    public static final Material field_151566_D = (new Material(MapColor.field_151669_i)).setNoPushMobility();
    public static final Material field_151567_E = (new MaterialPortal(MapColor.field_151660_b)).setImmovableMobility();
    public static final Material field_151568_F = (new Material(MapColor.field_151660_b)).setNoPushMobility();
    public static final Material field_151569_G = (new Material(MapColor.field_151659_e)
    {
        private static final String __OBFID = "CL_00000543";
        public boolean blocksMovement()
        {
            return false;
        }
    }).setRequiresTool().setNoPushMobility();

    /** Pistons' material. */
    public static final Material piston = (new Material(MapColor.field_151665_m)).setImmovableMobility();

    /** Bool defining if the block can burn or not. */
    private boolean canBurn;

    /**
     * Determines whether blocks with this material can be "overwritten" by other blocks when placed - eg snow, vines
     * and tall grass.
     */
    private boolean replaceable;

    /** Indicates if the material is translucent */
    private boolean isTranslucent;

    /** The color index used to draw the blocks of this material on maps. */
    private final MapColor materialMapColor;

    /**
     * Determines if the material can be harvested without a tool (or with the wrong tool)
     */
    private boolean requiresNoTool = true;

    /**
     * Mobility information flag. 0 indicates that this block is normal, 1 indicates that it can't push other blocks, 2
     * indicates that it can't be pushed.
     */
    private int mobilityFlag;
    private boolean isAdventureModeExempt;
    private static final String __OBFID = "CL_00000542";

    public Material(MapColor par1MapColor)
    {
        this.materialMapColor = par1MapColor;
    }

    /**
     * Returns if blocks of these materials are liquids.
     */
    public boolean isLiquid()
    {
        return false;
    }

    public boolean isSolid()
    {
        return true;
    }

    /**
     * Will prevent grass from growing on dirt underneath and kill any grass below it if it returns true
     */
    public boolean getCanBlockGrass()
    {
        return true;
    }

    /**
     * Returns if this material is considered solid or not
     */
    public boolean blocksMovement()
    {
        return true;
    }

    /**
     * Marks the material as translucent
     */
    private Material setTranslucent()
    {
        this.isTranslucent = true;
        return this;
    }

    /**
     * Makes blocks with this material require the correct tool to be harvested.
     */
    protected Material setRequiresTool()
    {
        this.requiresNoTool = false;
        return this;
    }

    /**
     * Set the canBurn bool to True and return the current object.
     */
    protected Material setBurning()
    {
        this.canBurn = true;
        return this;
    }

    /**
     * Returns if the block can burn or not.
     */
    public boolean getCanBurn()
    {
        return this.canBurn;
    }

    /**
     * Sets {@link #replaceable} to true.
     */
    public Material setReplaceable()
    {
        this.replaceable = true;
        return this;
    }

    /**
     * Returns whether the material can be replaced by other blocks when placed - eg snow, vines and tall grass.
     */
    public boolean isReplaceable()
    {
        return this.replaceable;
    }

    /**
     * Indicate if the material is opaque
     */
    public boolean isOpaque()
    {
        return this.isTranslucent ? false : this.blocksMovement();
    }

    /**
     * Returns true if the material can be harvested without a tool (or with the wrong tool)
     */
    public boolean isToolNotRequired()
    {
        return this.requiresNoTool;
    }

    /**
     * Returns the mobility information of the material, 0 = free, 1 = can't push but can move over, 2 = total
     * immobility and stop pistons.
     */
    public int getMaterialMobility()
    {
        return this.mobilityFlag;
    }

    /**
     * This type of material can't be pushed, but pistons can move over it.
     */
    protected Material setNoPushMobility()
    {
        this.mobilityFlag = 1;
        return this;
    }

    /**
     * This type of material can't be pushed, and pistons are blocked to move.
     */
    protected Material setImmovableMobility()
    {
        this.mobilityFlag = 2;
        return this;
    }

    /**
     * @see #isAdventureModeExempt()
     */
    protected Material setAdventureModeExempt()
    {
        this.isAdventureModeExempt = true;
        return this;
    }

    /**
     * Returns true if blocks with this material can always be mined in adventure mode.
     */
    public boolean isAdventureModeExempt()
    {
        return this.isAdventureModeExempt;
    }

    public MapColor func_151565_r()
    {
        return this.materialMapColor;
    }
}