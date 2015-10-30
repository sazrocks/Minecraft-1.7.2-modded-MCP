package net.minecraft.util;

import com.mojang.authlib.GameProfile;

public class Session
{
    private final String username;
    private final String field_148257_b;
    private final String field_148258_c;
    private static final String __OBFID = "CL_00000659";

    public Session(String p_i45006_1_, String p_i45006_2_, String p_i45006_3_)
    {
        this.username = p_i45006_1_;
        this.field_148257_b = p_i45006_2_;
        this.field_148258_c = p_i45006_3_;
    }

    public String getSessionID()
    {
        return "token:" + this.field_148258_c + ":" + this.field_148257_b;
    }

    public String func_148255_b()
    {
        return this.field_148257_b;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String func_148254_d()
    {
        return this.field_148258_c;
    }

    public GameProfile func_148256_e()
    {
        return new GameProfile(this.func_148255_b(), this.getUsername());
    }
}
