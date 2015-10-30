package net.minecraft.entity.passive;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowGolem;
import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPlay;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITradePlayer;
import net.minecraft.entity.ai.EntityAIVillagerMate;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Tuple;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class EntityVillager extends EntityAgeable implements IMerchant, INpc
{
    private int randomTickDivider;
    private boolean isMating;
    private boolean isPlaying;
    Village villageObj;

    /** This villager's current customer. */
    private EntityPlayer buyingPlayer;

    /** Initialises the MerchantRecipeList.java */
    private MerchantRecipeList buyingList;
    private int timeUntilReset;

    /** addDefaultEquipmentAndRecipies is called if this is true */
    private boolean needsInitilization;
    private int wealth;

    /** Last player to trade with this villager, used for aggressivity. */
    private String lastBuyingPlayer;
    private boolean field_82190_bM;
    private float field_82191_bN;

    /**
     * a villagers recipe list is intialized off this list ; the 2 params are min/max amount they will trade for 1
     * emerald
     */
    private static final Map villagerStockList = new HashMap();

    /**
     * Selling list of Blacksmith items. negative numbers mean 1 emerald for n items, positive numbers are n emeralds
     * for 1 item
     */
    private static final Map blacksmithSellingList = new HashMap();
    private static final String __OBFID = "CL_00001707";

    public EntityVillager(World par1World)
    {
        this(par1World, 0);
    }

    public EntityVillager(World par1World, int par2)
    {
        super(par1World);
        this.setProfession(par2);
        this.setSize(0.6F, 1.8F);
        this.getNavigator().setBreakDoors(true);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        this.tasks.addTask(1, new EntityAITradePlayer(this));
        this.tasks.addTask(1, new EntityAILookAtTradePlayer(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(6, new EntityAIVillagerMate(this));
        this.tasks.addTask(7, new EntityAIFollowGolem(this));
        this.tasks.addTask(8, new EntityAIPlay(this, 0.32D));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityVillager.class, 5.0F, 0.02F));
        this.tasks.addTask(9, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.5D);
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

    /**
     * main AI tick function, replaces updateEntityActionState
     */
    protected void updateAITick()
    {
        if (--this.randomTickDivider <= 0)
        {
            this.worldObj.villageCollectionObj.addVillagerPosition(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
            this.randomTickDivider = 70 + this.rand.nextInt(50);
            this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);

            if (this.villageObj == null)
            {
                this.detachHome();
            }
            else
            {
                ChunkCoordinates var1 = this.villageObj.getCenter();
                this.setHomeArea(var1.posX, var1.posY, var1.posZ, (int)((float)this.villageObj.getVillageRadius() * 0.6F));

                if (this.field_82190_bM)
                {
                    this.field_82190_bM = false;
                    this.villageObj.func_82683_b(5);
                }
            }
        }

        if (!this.isTrading() && this.timeUntilReset > 0)
        {
            --this.timeUntilReset;

            if (this.timeUntilReset <= 0)
            {
                if (this.needsInitilization)
                {
                    if (this.buyingList.size() > 1)
                    {
                        Iterator var3 = this.buyingList.iterator();

                        while (var3.hasNext())
                        {
                            MerchantRecipe var2 = (MerchantRecipe)var3.next();

                            if (var2.func_82784_g())
                            {
                                var2.func_82783_a(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                            }
                        }
                    }

                    this.addDefaultEquipmentAndRecipies(1);
                    this.needsInitilization = false;

                    if (this.villageObj != null && this.lastBuyingPlayer != null)
                    {
                        this.worldObj.setEntityState(this, (byte)14);
                        this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
                    }
                }

                this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
            }
        }

        super.updateAITick();
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        boolean var3 = var2 != null && var2.getItem() == Items.field_151063_bx;

        if (!var3 && this.isEntityAlive() && !this.isTrading() && !this.isChild())
        {
            if (!this.worldObj.isRemote)
            {
                this.setCustomer(par1EntityPlayer);
                par1EntityPlayer.displayGUIMerchant(this, this.getCustomNameTag());
            }

            return true;
        }
        else
        {
            return super.interact(par1EntityPlayer);
        }
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, Integer.valueOf(0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Profession", this.getProfession());
        par1NBTTagCompound.setInteger("Riches", this.wealth);

        if (this.buyingList != null)
        {
            par1NBTTagCompound.setTag("Offers", this.buyingList.getRecipiesAsTags());
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setProfession(par1NBTTagCompound.getInteger("Profession"));
        this.wealth = par1NBTTagCompound.getInteger("Riches");

        if (par1NBTTagCompound.func_150297_b("Offers", 10))
        {
            NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Offers");
            this.buyingList = new MerchantRecipeList(var2);
        }
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return false;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return this.isTrading() ? "mob.villager.haggle" : "mob.villager.idle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.villager.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.villager.death";
    }

    public void setProfession(int par1)
    {
        this.dataWatcher.updateObject(16, Integer.valueOf(par1));
    }

    public int getProfession()
    {
        return this.dataWatcher.getWatchableObjectInt(16);
    }

    public boolean isMating()
    {
        return this.isMating;
    }

    public void setMating(boolean par1)
    {
        this.isMating = par1;
    }

    public void setPlaying(boolean par1)
    {
        this.isPlaying = par1;
    }

    public boolean isPlaying()
    {
        return this.isPlaying;
    }

    public void setRevengeTarget(EntityLivingBase par1EntityLivingBase)
    {
        super.setRevengeTarget(par1EntityLivingBase);

        if (this.villageObj != null && par1EntityLivingBase != null)
        {
            this.villageObj.addOrRenewAgressor(par1EntityLivingBase);

            if (par1EntityLivingBase instanceof EntityPlayer)
            {
                byte var2 = -1;

                if (this.isChild())
                {
                    var2 = -3;
                }

                this.villageObj.setReputationForPlayer(par1EntityLivingBase.getCommandSenderName(), var2);

                if (this.isEntityAlive())
                {
                    this.worldObj.setEntityState(this, (byte)13);
                }
            }
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource par1DamageSource)
    {
        if (this.villageObj != null)
        {
            Entity var2 = par1DamageSource.getEntity();

            if (var2 != null)
            {
                if (var2 instanceof EntityPlayer)
                {
                    this.villageObj.setReputationForPlayer(var2.getCommandSenderName(), -2);
                }
                else if (var2 instanceof IMob)
                {
                    this.villageObj.endMatingSeason();
                }
            }
            else if (var2 == null)
            {
                EntityPlayer var3 = this.worldObj.getClosestPlayerToEntity(this, 16.0D);

                if (var3 != null)
                {
                    this.villageObj.endMatingSeason();
                }
            }
        }

        super.onDeath(par1DamageSource);
    }

    public void setCustomer(EntityPlayer par1EntityPlayer)
    {
        this.buyingPlayer = par1EntityPlayer;
    }

    public EntityPlayer getCustomer()
    {
        return this.buyingPlayer;
    }

    public boolean isTrading()
    {
        return this.buyingPlayer != null;
    }

    public void useRecipe(MerchantRecipe par1MerchantRecipe)
    {
        par1MerchantRecipe.incrementToolUses();
        this.livingSoundTime = -this.getTalkInterval();
        this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());

        if (par1MerchantRecipe.hasSameIDsAs((MerchantRecipe)this.buyingList.get(this.buyingList.size() - 1)))
        {
            this.timeUntilReset = 40;
            this.needsInitilization = true;

            if (this.buyingPlayer != null)
            {
                this.lastBuyingPlayer = this.buyingPlayer.getCommandSenderName();
            }
            else
            {
                this.lastBuyingPlayer = null;
            }
        }

        if (par1MerchantRecipe.getItemToBuy().getItem() == Items.field_151166_bC)
        {
            this.wealth += par1MerchantRecipe.getItemToBuy().stackSize;
        }
    }

    public void func_110297_a_(ItemStack par1ItemStack)
    {
        if (!this.worldObj.isRemote && this.livingSoundTime > -this.getTalkInterval() + 20)
        {
            this.livingSoundTime = -this.getTalkInterval();

            if (par1ItemStack != null)
            {
                this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());
            }
            else
            {
                this.playSound("mob.villager.no", this.getSoundVolume(), this.getSoundPitch());
            }
        }
    }

    public MerchantRecipeList getRecipes(EntityPlayer par1EntityPlayer)
    {
        if (this.buyingList == null)
        {
            this.addDefaultEquipmentAndRecipies(1);
        }

        return this.buyingList;
    }

    /**
     * Adjusts the probability of obtaining a given recipe being offered by a villager
     */
    private float adjustProbability(float par1)
    {
        float var2 = par1 + this.field_82191_bN;
        return var2 > 0.9F ? 0.9F - (var2 - 0.9F) : var2;
    }

    /**
     * based on the villagers profession add items, equipment, and recipies adds par1 random items to the list of things
     * that the villager wants to buy. (at most 1 of each wanted type is added)
     */
    private void addDefaultEquipmentAndRecipies(int par1)
    {
        if (this.buyingList != null)
        {
            this.field_82191_bN = MathHelper.sqrt_float((float)this.buyingList.size()) * 0.2F;
        }
        else
        {
            this.field_82191_bN = 0.0F;
        }

        MerchantRecipeList var2;
        var2 = new MerchantRecipeList();
        int var6;
        label50:

        switch (this.getProfession())
        {
            case 0:
                func_146091_a(var2, Items.field_151015_O, this.rand, this.adjustProbability(0.9F));
                func_146091_a(var2, Item.func_150898_a(Blocks.field_150325_L), this.rand, this.adjustProbability(0.5F));
                func_146091_a(var2, Items.field_151076_bf, this.rand, this.adjustProbability(0.5F));
                func_146091_a(var2, Items.field_151101_aQ, this.rand, this.adjustProbability(0.4F));
                func_146089_b(var2, Items.field_151025_P, this.rand, this.adjustProbability(0.9F));
                func_146089_b(var2, Items.field_151127_ba, this.rand, this.adjustProbability(0.3F));
                func_146089_b(var2, Items.field_151034_e, this.rand, this.adjustProbability(0.3F));
                func_146089_b(var2, Items.field_151106_aX, this.rand, this.adjustProbability(0.3F));
                func_146089_b(var2, Items.field_151097_aZ, this.rand, this.adjustProbability(0.3F));
                func_146089_b(var2, Items.field_151033_d, this.rand, this.adjustProbability(0.3F));
                func_146089_b(var2, Items.field_151077_bg, this.rand, this.adjustProbability(0.3F));
                func_146089_b(var2, Items.field_151032_g, this.rand, this.adjustProbability(0.5F));

                if (this.rand.nextFloat() < this.adjustProbability(0.5F))
                {
                    var2.add(new MerchantRecipe(new ItemStack(Blocks.field_150351_n, 10), new ItemStack(Items.field_151166_bC), new ItemStack(Items.field_151145_ak, 4 + this.rand.nextInt(2), 0)));
                }

                break;

            case 1:
                func_146091_a(var2, Items.field_151121_aF, this.rand, this.adjustProbability(0.8F));
                func_146091_a(var2, Items.field_151122_aG, this.rand, this.adjustProbability(0.8F));
                func_146091_a(var2, Items.field_151164_bB, this.rand, this.adjustProbability(0.3F));
                func_146089_b(var2, Item.func_150898_a(Blocks.field_150342_X), this.rand, this.adjustProbability(0.8F));
                func_146089_b(var2, Item.func_150898_a(Blocks.field_150359_w), this.rand, this.adjustProbability(0.2F));
                func_146089_b(var2, Items.field_151111_aL, this.rand, this.adjustProbability(0.2F));
                func_146089_b(var2, Items.field_151113_aN, this.rand, this.adjustProbability(0.2F));

                if (this.rand.nextFloat() < this.adjustProbability(0.07F))
                {
                    Enchantment var8 = Enchantment.enchantmentsBookList[this.rand.nextInt(Enchantment.enchantmentsBookList.length)];
                    int var10 = MathHelper.getRandomIntegerInRange(this.rand, var8.getMinLevel(), var8.getMaxLevel());
                    ItemStack var11 = Items.field_151134_bR.getEnchantedItemStack(new EnchantmentData(var8, var10));
                    var6 = 2 + this.rand.nextInt(5 + var10 * 10) + 3 * var10;
                    var2.add(new MerchantRecipe(new ItemStack(Items.field_151122_aG), new ItemStack(Items.field_151166_bC, var6), var11));
                }

                break;

            case 2:
                func_146089_b(var2, Items.field_151061_bv, this.rand, this.adjustProbability(0.3F));
                func_146089_b(var2, Items.field_151062_by, this.rand, this.adjustProbability(0.2F));
                func_146089_b(var2, Items.field_151137_ax, this.rand, this.adjustProbability(0.4F));
                func_146089_b(var2, Item.func_150898_a(Blocks.field_150426_aN), this.rand, this.adjustProbability(0.3F));
                Item[] var3 = new Item[] {Items.field_151040_l, Items.field_151048_u, Items.field_151030_Z, Items.field_151163_ad, Items.field_151036_c, Items.field_151056_x, Items.field_151035_b, Items.field_151046_w};
                Item[] var4 = var3;
                int var5 = var3.length;
                var6 = 0;

                while (true)
                {
                    if (var6 >= var5)
                    {
                        break label50;
                    }

                    Item var7 = var4[var6];

                    if (this.rand.nextFloat() < this.adjustProbability(0.05F))
                    {
                        var2.add(new MerchantRecipe(new ItemStack(var7, 1, 0), new ItemStack(Items.field_151166_bC, 2 + this.rand.nextInt(3), 0), EnchantmentHelper.addRandomEnchantment(this.rand, new ItemStack(var7, 1, 0), 5 + this.rand.nextInt(15))));
                    }

                    ++var6;
                }

            case 3:
                func_146091_a(var2, Items.field_151044_h, this.rand, this.adjustProbability(0.7F));
                func_146091_a(var2, Items.field_151042_j, this.rand, this.adjustProbability(0.5F));
                func_146091_a(var2, Items.field_151043_k, this.rand, this.adjustProbability(0.5F));
                func_146091_a(var2, Items.field_151045_i, this.rand, this.adjustProbability(0.5F));
                func_146089_b(var2, Items.field_151040_l, this.rand, this.adjustProbability(0.5F));
                func_146089_b(var2, Items.field_151048_u, this.rand, this.adjustProbability(0.5F));
                func_146089_b(var2, Items.field_151036_c, this.rand, this.adjustProbability(0.3F));
                func_146089_b(var2, Items.field_151056_x, this.rand, this.adjustProbability(0.3F));
                func_146089_b(var2, Items.field_151035_b, this.rand, this.adjustProbability(0.5F));
                func_146089_b(var2, Items.field_151046_w, this.rand, this.adjustProbability(0.5F));
                func_146089_b(var2, Items.field_151037_a, this.rand, this.adjustProbability(0.2F));
                func_146089_b(var2, Items.field_151047_v, this.rand, this.adjustProbability(0.2F));
                func_146089_b(var2, Items.field_151019_K, this.rand, this.adjustProbability(0.2F));
                func_146089_b(var2, Items.field_151012_L, this.rand, this.adjustProbability(0.2F));
                func_146089_b(var2, Items.field_151167_ab, this.rand, this.adjustProbability(0.2F));
                func_146089_b(var2, Items.field_151175_af, this.rand, this.adjustProbability(0.2F));
                func_146089_b(var2, Items.field_151028_Y, this.rand, this.adjustProbability(0.2F));
                func_146089_b(var2, Items.field_151161_ac, this.rand, this.adjustProbability(0.2F));
                func_146089_b(var2, Items.field_151030_Z, this.rand, this.adjustProbability(0.2F));
                func_146089_b(var2, Items.field_151163_ad, this.rand, this.adjustProbability(0.2F));
                func_146089_b(var2, Items.field_151165_aa, this.rand, this.adjustProbability(0.2F));
                func_146089_b(var2, Items.field_151173_ae, this.rand, this.adjustProbability(0.2F));
                func_146089_b(var2, Items.field_151029_X, this.rand, this.adjustProbability(0.1F));
                func_146089_b(var2, Items.field_151020_U, this.rand, this.adjustProbability(0.1F));
                func_146089_b(var2, Items.field_151023_V, this.rand, this.adjustProbability(0.1F));
                func_146089_b(var2, Items.field_151022_W, this.rand, this.adjustProbability(0.1F));
                break;

            case 4:
                func_146091_a(var2, Items.field_151044_h, this.rand, this.adjustProbability(0.7F));
                func_146091_a(var2, Items.field_151147_al, this.rand, this.adjustProbability(0.5F));
                func_146091_a(var2, Items.field_151082_bd, this.rand, this.adjustProbability(0.5F));
                func_146089_b(var2, Items.field_151141_av, this.rand, this.adjustProbability(0.1F));
                func_146089_b(var2, Items.field_151027_R, this.rand, this.adjustProbability(0.3F));
                func_146089_b(var2, Items.field_151021_T, this.rand, this.adjustProbability(0.3F));
                func_146089_b(var2, Items.field_151024_Q, this.rand, this.adjustProbability(0.3F));
                func_146089_b(var2, Items.field_151026_S, this.rand, this.adjustProbability(0.3F));
                func_146089_b(var2, Items.field_151157_am, this.rand, this.adjustProbability(0.3F));
                func_146089_b(var2, Items.field_151083_be, this.rand, this.adjustProbability(0.3F));
        }

        if (var2.isEmpty())
        {
            func_146091_a(var2, Items.field_151043_k, this.rand, 1.0F);
        }

        Collections.shuffle(var2);

        if (this.buyingList == null)
        {
            this.buyingList = new MerchantRecipeList();
        }

        for (int var9 = 0; var9 < par1 && var9 < var2.size(); ++var9)
        {
            this.buyingList.addToListWithCheck((MerchantRecipe)var2.get(var9));
        }
    }

    public void setRecipes(MerchantRecipeList par1MerchantRecipeList) {}

    private static void func_146091_a(MerchantRecipeList p_146091_0_, Item p_146091_1_, Random p_146091_2_, float p_146091_3_)
    {
        if (p_146091_2_.nextFloat() < p_146091_3_)
        {
            p_146091_0_.add(new MerchantRecipe(func_146088_a(p_146091_1_, p_146091_2_), Items.field_151166_bC));
        }
    }

    private static ItemStack func_146088_a(Item p_146088_0_, Random p_146088_1_)
    {
        return new ItemStack(p_146088_0_, func_146092_b(p_146088_0_, p_146088_1_), 0);
    }

    private static int func_146092_b(Item p_146092_0_, Random p_146092_1_)
    {
        Tuple var2 = (Tuple)villagerStockList.get(p_146092_0_);
        return var2 == null ? 1 : (((Integer)var2.getFirst()).intValue() >= ((Integer)var2.getSecond()).intValue() ? ((Integer)var2.getFirst()).intValue() : ((Integer)var2.getFirst()).intValue() + p_146092_1_.nextInt(((Integer)var2.getSecond()).intValue() - ((Integer)var2.getFirst()).intValue()));
    }

    private static void func_146089_b(MerchantRecipeList p_146089_0_, Item p_146089_1_, Random p_146089_2_, float p_146089_3_)
    {
        if (p_146089_2_.nextFloat() < p_146089_3_)
        {
            int var4 = func_146090_c(p_146089_1_, p_146089_2_);
            ItemStack var5;
            ItemStack var6;

            if (var4 < 0)
            {
                var5 = new ItemStack(Items.field_151166_bC, 1, 0);
                var6 = new ItemStack(p_146089_1_, -var4, 0);
            }
            else
            {
                var5 = new ItemStack(Items.field_151166_bC, var4, 0);
                var6 = new ItemStack(p_146089_1_, 1, 0);
            }

            p_146089_0_.add(new MerchantRecipe(var5, var6));
        }
    }

    private static int func_146090_c(Item p_146090_0_, Random p_146090_1_)
    {
        Tuple var2 = (Tuple)blacksmithSellingList.get(p_146090_0_);
        return var2 == null ? 1 : (((Integer)var2.getFirst()).intValue() >= ((Integer)var2.getSecond()).intValue() ? ((Integer)var2.getFirst()).intValue() : ((Integer)var2.getFirst()).intValue() + p_146090_1_.nextInt(((Integer)var2.getSecond()).intValue() - ((Integer)var2.getFirst()).intValue()));
    }

    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 12)
        {
            this.generateRandomParticles("heart");
        }
        else if (par1 == 13)
        {
            this.generateRandomParticles("angryVillager");
        }
        else if (par1 == 14)
        {
            this.generateRandomParticles("happyVillager");
        }
        else
        {
            super.handleHealthUpdate(par1);
        }
    }

    /**
     * par1 is the particleName
     */
    private void generateRandomParticles(String par1Str)
    {
        for (int var2 = 0; var2 < 5; ++var2)
        {
            double var3 = this.rand.nextGaussian() * 0.02D;
            double var5 = this.rand.nextGaussian() * 0.02D;
            double var7 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle(par1Str, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 1.0D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var3, var5, var7);
        }
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
    {
        par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);
        this.setProfession(this.worldObj.rand.nextInt(5));
        return par1EntityLivingData;
    }

    public void func_82187_q()
    {
        this.field_82190_bM = true;
    }

    public EntityVillager createChild(EntityAgeable par1EntityAgeable)
    {
        EntityVillager var2 = new EntityVillager(this.worldObj);
        var2.onSpawnWithEgg((IEntityLivingData)null);
        return var2;
    }

    public boolean allowLeashing()
    {
        return false;
    }

    static
    {
        villagerStockList.put(Items.field_151044_h, new Tuple(Integer.valueOf(16), Integer.valueOf(24)));
        villagerStockList.put(Items.field_151042_j, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
        villagerStockList.put(Items.field_151043_k, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
        villagerStockList.put(Items.field_151045_i, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        villagerStockList.put(Items.field_151121_aF, new Tuple(Integer.valueOf(24), Integer.valueOf(36)));
        villagerStockList.put(Items.field_151122_aG, new Tuple(Integer.valueOf(11), Integer.valueOf(13)));
        villagerStockList.put(Items.field_151164_bB, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
        villagerStockList.put(Items.field_151079_bi, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
        villagerStockList.put(Items.field_151061_bv, new Tuple(Integer.valueOf(2), Integer.valueOf(3)));
        villagerStockList.put(Items.field_151147_al, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
        villagerStockList.put(Items.field_151082_bd, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
        villagerStockList.put(Items.field_151076_bf, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
        villagerStockList.put(Items.field_151101_aQ, new Tuple(Integer.valueOf(9), Integer.valueOf(13)));
        villagerStockList.put(Items.field_151014_N, new Tuple(Integer.valueOf(34), Integer.valueOf(48)));
        villagerStockList.put(Items.field_151081_bc, new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
        villagerStockList.put(Items.field_151080_bb, new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
        villagerStockList.put(Items.field_151015_O, new Tuple(Integer.valueOf(18), Integer.valueOf(22)));
        villagerStockList.put(Item.func_150898_a(Blocks.field_150325_L), new Tuple(Integer.valueOf(14), Integer.valueOf(22)));
        villagerStockList.put(Items.field_151078_bh, new Tuple(Integer.valueOf(36), Integer.valueOf(64)));
        blacksmithSellingList.put(Items.field_151033_d, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
        blacksmithSellingList.put(Items.field_151097_aZ, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
        blacksmithSellingList.put(Items.field_151040_l, new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
        blacksmithSellingList.put(Items.field_151048_u, new Tuple(Integer.valueOf(12), Integer.valueOf(14)));
        blacksmithSellingList.put(Items.field_151036_c, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
        blacksmithSellingList.put(Items.field_151056_x, new Tuple(Integer.valueOf(9), Integer.valueOf(12)));
        blacksmithSellingList.put(Items.field_151035_b, new Tuple(Integer.valueOf(7), Integer.valueOf(9)));
        blacksmithSellingList.put(Items.field_151046_w, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
        blacksmithSellingList.put(Items.field_151037_a, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        blacksmithSellingList.put(Items.field_151047_v, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
        blacksmithSellingList.put(Items.field_151019_K, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        blacksmithSellingList.put(Items.field_151012_L, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
        blacksmithSellingList.put(Items.field_151167_ab, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        blacksmithSellingList.put(Items.field_151175_af, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
        blacksmithSellingList.put(Items.field_151028_Y, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        blacksmithSellingList.put(Items.field_151161_ac, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
        blacksmithSellingList.put(Items.field_151030_Z, new Tuple(Integer.valueOf(10), Integer.valueOf(14)));
        blacksmithSellingList.put(Items.field_151163_ad, new Tuple(Integer.valueOf(16), Integer.valueOf(19)));
        blacksmithSellingList.put(Items.field_151165_aa, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
        blacksmithSellingList.put(Items.field_151173_ae, new Tuple(Integer.valueOf(11), Integer.valueOf(14)));
        blacksmithSellingList.put(Items.field_151029_X, new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
        blacksmithSellingList.put(Items.field_151020_U, new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
        blacksmithSellingList.put(Items.field_151023_V, new Tuple(Integer.valueOf(11), Integer.valueOf(15)));
        blacksmithSellingList.put(Items.field_151022_W, new Tuple(Integer.valueOf(9), Integer.valueOf(11)));
        blacksmithSellingList.put(Items.field_151025_P, new Tuple(Integer.valueOf(-4), Integer.valueOf(-2)));
        blacksmithSellingList.put(Items.field_151127_ba, new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
        blacksmithSellingList.put(Items.field_151034_e, new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
        blacksmithSellingList.put(Items.field_151106_aX, new Tuple(Integer.valueOf(-10), Integer.valueOf(-7)));
        blacksmithSellingList.put(Item.func_150898_a(Blocks.field_150359_w), new Tuple(Integer.valueOf(-5), Integer.valueOf(-3)));
        blacksmithSellingList.put(Item.func_150898_a(Blocks.field_150342_X), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
        blacksmithSellingList.put(Items.field_151027_R, new Tuple(Integer.valueOf(4), Integer.valueOf(5)));
        blacksmithSellingList.put(Items.field_151021_T, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
        blacksmithSellingList.put(Items.field_151024_Q, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
        blacksmithSellingList.put(Items.field_151026_S, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
        blacksmithSellingList.put(Items.field_151141_av, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
        blacksmithSellingList.put(Items.field_151062_by, new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
        blacksmithSellingList.put(Items.field_151137_ax, new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
        blacksmithSellingList.put(Items.field_151111_aL, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
        blacksmithSellingList.put(Items.field_151113_aN, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
        blacksmithSellingList.put(Item.func_150898_a(Blocks.field_150426_aN), new Tuple(Integer.valueOf(-3), Integer.valueOf(-1)));
        blacksmithSellingList.put(Items.field_151157_am, new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
        blacksmithSellingList.put(Items.field_151083_be, new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
        blacksmithSellingList.put(Items.field_151077_bg, new Tuple(Integer.valueOf(-8), Integer.valueOf(-6)));
        blacksmithSellingList.put(Items.field_151061_bv, new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
        blacksmithSellingList.put(Items.field_151032_g, new Tuple(Integer.valueOf(-12), Integer.valueOf(-8)));
    }
}
