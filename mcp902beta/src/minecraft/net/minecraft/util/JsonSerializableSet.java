package net.minecraft.util;

import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class JsonSerializableSet extends ForwardingSet implements IJsonSerializable
{
    private final Set field_151004_a = Sets.newHashSet();
    private static final String __OBFID = "CL_00001482";

    public JsonElement func_151003_a()
    {
        JsonArray var1 = new JsonArray();
        Iterator var2 = this.iterator();

        while (var2.hasNext())
        {
            String var3 = (String)var2.next();
            var1.add(new JsonPrimitive(var3));
        }

        return var1;
    }

    protected Set delegate()
    {
        return this.field_151004_a;
    }
}
