package com.krt;

import com.krt.artifact.Artifact;

import java.util.regex.Pattern;

public class Utils {

    public static boolean match(String find, String str){
        return Pattern.compile(find, Pattern.CASE_INSENSITIVE).matcher(str).find();
    }

    public static void normalize(Stats stats){
        stats.atkP /= 100;
        stats.defP /= 100;
        stats.hpP /= 100;
        stats.er /= 100;
        stats.critDMG /= 100;
        stats.critRate /= 100;
        stats.dmgBonus /= 100;
        stats.healBonus /= 100;
    }

    public static void normalize(Artifact art){
        normalize(art.getStats());
    }

}
