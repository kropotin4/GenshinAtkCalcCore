package com.krt.weapon;

import com.krt.Utils;

public enum WeaponType {
    Bow,
    Catalyst,
    Claymore,
    Polearm,
    Sword;

    public static WeaponType parse(String str){
        if (Utils.match("Bow", str))
            return WeaponType.Bow;
        if (Utils.match("Catalyst", str))
            return WeaponType.Catalyst;
        if (Utils.match("Claymore", str))
            return WeaponType.Claymore;
        if (Utils.match("Polearm", str))
            return WeaponType.Polearm;
        if (Utils.match("Sword", str))
            return WeaponType.Sword;

        throw new RuntimeException("Wrong WeaponType name: " + str);
    }
}
