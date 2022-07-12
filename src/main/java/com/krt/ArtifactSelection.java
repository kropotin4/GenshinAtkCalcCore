package com.krt;

import com.krt.artifact.Artifact;
import com.krt.artifact.ArtifactList;
import com.krt.atk.AttackType;
import com.krt.atk.CritType;
import com.krt.atk.Reaction;
import com.krt.character.Char;

import java.util.ArrayList;

public class ArtifactSelection {

    public static ArrayList<Artifact> search(Char ch, ArtifactList arts){
        ArrayList<Artifact> res = new ArrayList<>();
        res.add(null);
        res.add(null);
        res.add(null);
        res.add(null);
        res.add(null);

        double bestScore = 0, curScore = 0;

        for (int i1 = 0; i1 < arts.flower.size(); ++i1){
            ch.setArtifact(arts.flower.get(i1));
            for (int i2 = 0; i2 < arts.plume.size(); ++i2){
                ch.setArtifact(arts.plume.get(i2));
                for (int i3 = 0; i3 < arts.clock.size(); ++i3){
                    ch.setArtifact(arts.clock.get(i3));
                    for (int i4 = 0; i4 < arts.goblen.size(); ++i4){
                        ch.setArtifact(arts.goblen.get(i4));
                        for (int i5 = 0; i5 < arts.crown.size(); ++i5){
                            ch.setArtifact(arts.crown.get(i5));

                            curScore = ch.calcDmg(1.0, 90, Reaction.NONE, CritType.AVERAGE, AttackType.NORMAL);

                            if (curScore > bestScore){
                                bestScore = curScore;

                                res.set(0, arts.flower.get(i1));
                                res.set(1, arts.plume.get(i2));
                                res.set(2, arts.clock.get(i3));
                                res.set(3, arts.goblen.get(i4));
                                res.set(4, arts.crown.get(i5));
                            }

                        }
                    }
                }
            }
        }

        return res;
    }

}
