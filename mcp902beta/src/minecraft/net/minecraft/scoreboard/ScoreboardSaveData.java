package net.minecraft.scoreboard;

import java.util.Collection;
import java.util.Iterator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.WorldSavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScoreboardSaveData extends WorldSavedData
{
    private static final Logger field_151481_a = LogManager.getLogger();
    private Scoreboard theScoreboard;
    private NBTTagCompound field_96506_b;
    private static final String __OBFID = "CL_00000620";

    public ScoreboardSaveData()
    {
        this("scoreboard");
    }

    public ScoreboardSaveData(String par1Str)
    {
        super(par1Str);
    }

    public void func_96499_a(Scoreboard par1Scoreboard)
    {
        this.theScoreboard = par1Scoreboard;

        if (this.field_96506_b != null)
        {
            this.readFromNBT(this.field_96506_b);
        }
    }

    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        if (this.theScoreboard == null)
        {
            this.field_96506_b = par1NBTTagCompound;
        }
        else
        {
            this.func_96501_b(par1NBTTagCompound.func_150295_c("Objectives", 10));
            this.func_96500_c(par1NBTTagCompound.func_150295_c("PlayerScores", 10));

            if (par1NBTTagCompound.func_150297_b("DisplaySlots", 10))
            {
                this.func_96504_c(par1NBTTagCompound.getCompoundTag("DisplaySlots"));
            }

            if (par1NBTTagCompound.func_150297_b("Teams", 9))
            {
                this.func_96498_a(par1NBTTagCompound.func_150295_c("Teams", 10));
            }
        }
    }

    protected void func_96498_a(NBTTagList par1NBTTagList)
    {
        for (int var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2)
        {
            NBTTagCompound var3 = par1NBTTagList.func_150305_b(var2);
            ScorePlayerTeam var4 = this.theScoreboard.func_96527_f(var3.getString("Name"));
            var4.func_96664_a(var3.getString("DisplayName"));
            var4.func_96666_b(var3.getString("Prefix"));
            var4.func_96662_c(var3.getString("Suffix"));

            if (var3.func_150297_b("AllowFriendlyFire", 99))
            {
                var4.setAllowFriendlyFire(var3.getBoolean("AllowFriendlyFire"));
            }

            if (var3.func_150297_b("SeeFriendlyInvisibles", 99))
            {
                var4.func_98300_b(var3.getBoolean("SeeFriendlyInvisibles"));
            }

            this.func_96502_a(var4, var3.func_150295_c("Players", 8));
        }
    }

    protected void func_96502_a(ScorePlayerTeam par1ScorePlayerTeam, NBTTagList par2NBTTagList)
    {
        for (int var3 = 0; var3 < par2NBTTagList.tagCount(); ++var3)
        {
            this.theScoreboard.func_151392_a(par2NBTTagList.func_150307_f(var3), par1ScorePlayerTeam.func_96661_b());
        }
    }

    protected void func_96504_c(NBTTagCompound par1NBTTagCompound)
    {
        for (int var2 = 0; var2 < 3; ++var2)
        {
            if (par1NBTTagCompound.func_150297_b("slot_" + var2, 8))
            {
                String var3 = par1NBTTagCompound.getString("slot_" + var2);
                ScoreObjective var4 = this.theScoreboard.getObjective(var3);
                this.theScoreboard.func_96530_a(var2, var4);
            }
        }
    }

    protected void func_96501_b(NBTTagList par1NBTTagList)
    {
        for (int var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2)
        {
            NBTTagCompound var3 = par1NBTTagList.func_150305_b(var2);
            IScoreObjectiveCriteria var4 = (IScoreObjectiveCriteria)IScoreObjectiveCriteria.field_96643_a.get(var3.getString("CriteriaName"));
            ScoreObjective var5 = this.theScoreboard.func_96535_a(var3.getString("Name"), var4);
            var5.setDisplayName(var3.getString("DisplayName"));
        }
    }

    protected void func_96500_c(NBTTagList par1NBTTagList)
    {
        for (int var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2)
        {
            NBTTagCompound var3 = par1NBTTagList.func_150305_b(var2);
            ScoreObjective var4 = this.theScoreboard.getObjective(var3.getString("Objective"));
            Score var5 = this.theScoreboard.func_96529_a(var3.getString("Name"), var4);
            var5.func_96647_c(var3.getInteger("Score"));
        }
    }

    /**
     * write data to NBTTagCompound from this MapDataBase, similar to Entities and TileEntities
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        if (this.theScoreboard == null)
        {
            field_151481_a.warn("Tried to save scoreboard without having a scoreboard...");
        }
        else
        {
            par1NBTTagCompound.setTag("Objectives", this.func_96505_b());
            par1NBTTagCompound.setTag("PlayerScores", this.func_96503_e());
            par1NBTTagCompound.setTag("Teams", this.func_96496_a());
            this.func_96497_d(par1NBTTagCompound);
        }
    }

    protected NBTTagList func_96496_a()
    {
        NBTTagList var1 = new NBTTagList();
        Collection var2 = this.theScoreboard.func_96525_g();
        Iterator var3 = var2.iterator();

        while (var3.hasNext())
        {
            ScorePlayerTeam var4 = (ScorePlayerTeam)var3.next();
            NBTTagCompound var5 = new NBTTagCompound();
            var5.setString("Name", var4.func_96661_b());
            var5.setString("DisplayName", var4.func_96669_c());
            var5.setString("Prefix", var4.getColorPrefix());
            var5.setString("Suffix", var4.getColorSuffix());
            var5.setBoolean("AllowFriendlyFire", var4.getAllowFriendlyFire());
            var5.setBoolean("SeeFriendlyInvisibles", var4.func_98297_h());
            NBTTagList var6 = new NBTTagList();
            Iterator var7 = var4.getMembershipCollection().iterator();

            while (var7.hasNext())
            {
                String var8 = (String)var7.next();
                var6.appendTag(new NBTTagString(var8));
            }

            var5.setTag("Players", var6);
            var1.appendTag(var5);
        }

        return var1;
    }

    protected void func_96497_d(NBTTagCompound par1NBTTagCompound)
    {
        NBTTagCompound var2 = new NBTTagCompound();
        boolean var3 = false;

        for (int var4 = 0; var4 < 3; ++var4)
        {
            ScoreObjective var5 = this.theScoreboard.func_96539_a(var4);

            if (var5 != null)
            {
                var2.setString("slot_" + var4, var5.getName());
                var3 = true;
            }
        }

        if (var3)
        {
            par1NBTTagCompound.setTag("DisplaySlots", var2);
        }
    }

    protected NBTTagList func_96505_b()
    {
        NBTTagList var1 = new NBTTagList();
        Collection var2 = this.theScoreboard.getScoreObjectives();
        Iterator var3 = var2.iterator();

        while (var3.hasNext())
        {
            ScoreObjective var4 = (ScoreObjective)var3.next();
            NBTTagCompound var5 = new NBTTagCompound();
            var5.setString("Name", var4.getName());
            var5.setString("CriteriaName", var4.getCriteria().func_96636_a());
            var5.setString("DisplayName", var4.getDisplayName());
            var1.appendTag(var5);
        }

        return var1;
    }

    protected NBTTagList func_96503_e()
    {
        NBTTagList var1 = new NBTTagList();
        Collection var2 = this.theScoreboard.func_96528_e();
        Iterator var3 = var2.iterator();

        while (var3.hasNext())
        {
            Score var4 = (Score)var3.next();
            NBTTagCompound var5 = new NBTTagCompound();
            var5.setString("Name", var4.getPlayerName());
            var5.setString("Objective", var4.func_96645_d().getName());
            var5.setInteger("Score", var4.getScorePoints());
            var1.appendTag(var5);
        }

        return var1;
    }
}