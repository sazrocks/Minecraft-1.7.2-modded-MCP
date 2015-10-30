package net.minecraft.client.resources;

public class I18n
{
    private static Locale i18nLocale;
    private static final String __OBFID = "CL_00001094";

    static void setLocale(Locale par0Locale)
    {
        i18nLocale = par0Locale;
    }

    public static String getStringParams(String par0Str, Object ... par1ArrayOfObj)
    {
        return i18nLocale.formatMessage(par0Str, par1ArrayOfObj);
    }
}
