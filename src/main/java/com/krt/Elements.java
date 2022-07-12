package com.krt;

import java.util.regex.Pattern;

public enum Elements {
    ADAPTIV,
    PHYSICAL,
    PYRO,
    HYDRO,
    ANEMO,
    ELECTRO,
    DENDRO,
    CRYO,
    GEO;

    public static Elements parse(String str){
        if (Pattern.compile("PYRO", Pattern.CASE_INSENSITIVE).matcher(str).find())
            return Elements.PYRO;
        if (Pattern.compile("HYDRO", Pattern.CASE_INSENSITIVE).matcher(str).find())
            return Elements.HYDRO;
        if (Pattern.compile("ANEMO", Pattern.CASE_INSENSITIVE).matcher(str).find())
            return Elements.ANEMO;
        if (Pattern.compile("ELECTRO", Pattern.CASE_INSENSITIVE).matcher(str).find())
            return Elements.ELECTRO;
        if (Pattern.compile("DENDRO", Pattern.CASE_INSENSITIVE).matcher(str).find())
            return Elements.DENDRO;
        if (Pattern.compile("CRYO", Pattern.CASE_INSENSITIVE).matcher(str).find())
            return Elements.CRYO;
        if (Pattern.compile("GEO", Pattern.CASE_INSENSITIVE).matcher(str).find())
            return Elements.GEO;

        return ADAPTIV;
    }
}
