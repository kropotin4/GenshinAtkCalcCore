package com.krt.artifact;

import com.krt.Elements;
import com.krt.JsonFiles;
import com.krt.Stats;
import com.krt.SubStatType;
import com.krt.json.ArtifactSubStatJson;

import static com.krt.Utils.match;

public class Artifact {
    ArtifactType type;
    ArtifactSet set;
    int lvl;

    SubStatType main;
    Elements elem;

    Stats stats;

    public Artifact(ArtifactType type, String set, int lvl, String mainStat, ArtifactSubStatJson subStats){
        this.type = type;
        this.set = ArtifactSet.parse(set);
        this.lvl = lvl;

        this.stats = new Stats(subStats);

        elem = null;
        if (type == ArtifactType.FLOWER || type == ArtifactType.PLUME){
            main = null;
        }
        else {
            if (match("atk", mainStat))
                main = SubStatType.ATK;
            else if (match("def", mainStat))
                main = SubStatType.DEF;
            else if (match("hp", mainStat))
                main = SubStatType.HP;
            else if (match("er", mainStat))
                main = SubStatType.ER;
            else if (match("em", mainStat))
                main = SubStatType.EM;
            else if (match("crit", mainStat))
                if (match("rate", mainStat))
                    main = SubStatType.CRIT_RATE;
                else
                    main = SubStatType.CRIT_DMG;
            else if (match("heal", mainStat))
                main = SubStatType.HEAL_BONUS;
            else if (match("phys", mainStat))
                main = SubStatType.PHYS_BONUS;
            else {
                main = SubStatType.ELEM_BONUS;
                elem = Elements.parse(mainStat);
            }
        }

        switch (type){
            case FLOWER -> {
                stats.hpF += JsonFiles.art_main_scal.star5.hpF.get(lvl);
            }
            case PLUME -> {
                stats.atkF += JsonFiles.art_main_scal.star5.atkF.get(lvl);
            }
            default -> {
                stats.add(main, JsonFiles.art_main_scal.star5.getList(main).get(lvl));
            }
        }
    }

    public ArtifactType getType() {
        return type;
    }
    public Stats getStats() {
        return stats;
    }
    public Elements getElement() {
        return elem;
    }

    public boolean isElementGoblet(){
        return type == ArtifactType.GOBLET && elem != null;
    }

    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder();

        strb.append(type);
        strb.append(" " + lvl + " " + set + "\n");
        if (elem != null)
            strb.append(elem);
        strb.append(stats);

        return strb.toString();
    }
}
