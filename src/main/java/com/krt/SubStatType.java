package com.krt;

import static com.krt.Utils.match;

public enum SubStatType {
    ATK,
    DEF,
    HP,
    CRIT_RATE,
    CRIT_DMG,
    PHYS_BONUS,
    ELEM_BONUS,
    HEAL_BONUS,
    EM,
    ER;

    public static SubStatType parse(String str){
        if (match("atk", str))
            return SubStatType.ATK;
        if (match("def", str))
            return SubStatType.DEF;
        if (match("hp", str))
            return SubStatType.HP;
        if (match("ener", str))
            return SubStatType.ER;
        if (match("elem", str))
            return SubStatType.EM;
        if (match("crit", str))
            if (match("rate", str))
                return SubStatType.CRIT_RATE;
            else
                return SubStatType.CRIT_DMG;
        if (match("heal", str))
            return SubStatType.HEAL_BONUS;
        if (match("phys", str))
            return SubStatType.PHYS_BONUS;

        return ELEM_BONUS;
    }
}