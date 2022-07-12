package com.krt;

import com.google.gson.Gson;
import com.krt.json.ArtifactListJson;
import com.krt.json.ArtifactMainScalingJson;
import com.krt.json.CharListJson;
import com.krt.json.WeaponListJson;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class JsonFiles {

    public static CharListJson chars;
    public static WeaponListJson weapons;

    public static ArtifactMainScalingJson art_main_scal;

    public static ArtifactListJson arts_flower;
    public static ArtifactListJson arts_plume;
    public static ArtifactListJson arts_clock;
    public static ArtifactListJson arts_goblet;
    public static ArtifactListJson arts_crown;

    static {
        Gson g = new Gson();

        try {
            chars = g.fromJson(new FileReader("src/main/resources/chars.json"), CharListJson.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            weapons = g.fromJson(new FileReader("src/main/resources/weapons.json"), WeaponListJson.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            arts_flower = g.fromJson(new FileReader("src/main/resources/arts/arts_flower.json"), ArtifactListJson.class);
            arts_plume = g.fromJson(new FileReader("src/main/resources/arts/arts_plume.json"), ArtifactListJson.class);
            arts_clock = g.fromJson(new FileReader("src/main/resources/arts/arts_clock.json"), ArtifactListJson.class);
            arts_goblet = g.fromJson(new FileReader("src/main/resources/arts/arts_goblet.json"), ArtifactListJson.class);
            arts_crown = g.fromJson(new FileReader("src/main/resources/arts/arts_crown.json"), ArtifactListJson.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            art_main_scal = g.fromJson(new FileReader("src/main/resources/art_scaling.json"), ArtifactMainScalingJson.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> getNearChars(String name, int num){
        ArrayList<Pair<String, Integer>> names = new ArrayList<>();

        for (String n : chars.getNames()){
            names.add(Pair.of(n, StringUtils.getLevenshteinDistance(name, n)));
        }

        Collections.sort(names, Comparator.comparingInt(Pair::getRight));

        ArrayList<String> res = new ArrayList<>(num);
        for (int i = 0; i < num; ++i)
            res.add(names.get(i).getLeft());

        return res;
    }

}
