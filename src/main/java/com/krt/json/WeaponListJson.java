package com.krt.json;

import java.util.ArrayList;
import java.util.List;

public class WeaponListJson {
    public List<Integer> lvl;
    public List<WeaponJson> weapons;

    public ArrayList<String> getNames(){
        ArrayList<String> res = new ArrayList<>(weapons.size());

        for (WeaponJson item : weapons)
            res.add(item.name);

        return res;
    }
}
