package com.krt.weapon;

import com.krt.JsonFiles;
import com.krt.Stats;
import com.krt.SubStatType;
import com.krt.Utils;
import com.krt.json.WeaponJson;

import java.io.FileNotFoundException;

public class Weapon {

    String name;
    WeaponType type;    // Bow, sword and etc.

    int baseATK;

    SubStatType subStatType;    // Type of weapon sub stat (ATK%, DEF%, HP% and etc)
    double subStat;

    private final Stats stats;

    public Weapon(String name, int lvl) throws FileNotFoundException {
        this.name = null;

        int ind_lvl = JsonFiles.weapons.lvl.indexOf(lvl);
        for (WeaponJson item : JsonFiles.weapons.weapons) {
            if (name.equals(item.name)) {
                this.name = item.name;
                type = WeaponType.parse(item.type);
                baseATK = item.baseATK.get(ind_lvl);

                subStatType = SubStatType.parse(item.subStatType);
                subStat = item.subStat.get(ind_lvl);

                break;
            }
        }

        if (this.name == null){
            throw new RuntimeException("Weapon " + name + " not found.");
        }

        stats = new Stats();
        stats.add(subStatType, subStat);
        Utils.normalize(stats);
    }

    public int getATK(){
        return baseATK;
    }

    public Stats getSubStat(){
        return stats;
    }
}
