package com.krt.json;

import java.util.ArrayList;
import java.util.List;

public class CharListJson {
    public List<Integer> lvl;
    public List<CharJson> chars;

    public ArrayList<String> getNames(){
        ArrayList<String> res = new ArrayList<>(chars.size());

        for (CharJson item : chars)
            res.add(item.name);

        return res;
    }
}
