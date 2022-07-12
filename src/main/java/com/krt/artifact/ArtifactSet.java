package com.krt.artifact;

import com.krt.Elements;
import com.krt.Stats;
import com.krt.atk.AttackType;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;

import static com.krt.Utils.match;

public enum ArtifactSet {
    NONE,
    ADVENTURER,
    LUCKY_DOG,
    TRAVELING_DOCTOR,
    RESOLUTION_OF_SOJOURNER,
    TINY_MIRACLE,
    BERSERKER,
    INSTRUCTOR,
    THE_EXILE,
    DEFENDERS_WILL,
    BRAVE_HEART,
    MARTIAL_ARTIST,
    GAMBLER,
    SCHOLAR,
    GLADIATORS_FINALE,
    WANDERERS_TROUPE,
    THUNDERSOOTHER,
    THUNDERING_FURY,
    MAIDEN_BELOVED,
    VIRIDESCENT_VENERER,
    CRIMSON_WITCH_OF_FLAMES,
    LAVAWALKER,
    NOBLESSE_OBLIGE,
    BLOODSTAINED_CHIVALRY,
    ARCHAIC_PETRA,
    RETRACING_BOLIDE,
    BLIZZARD_STRAYER,
    HEART_OF_DEPTH,
    TENACITY_OF_THE_MILLELITH,
    PALE_FLAME,
    SHIMENAWAS_REMINISCENCE,
    EMBLEM_OF_SEVERED_FATE,
    HUSK_OF_OPULENT_DREAMS,
    OCEAN_HUED_CLAM;

    public static ArtifactSet parse(String str){
        for (ArtifactSet set : ArtifactSet.values()){
            if (match(set.name(), str))
                return set;
        }

        return ArtifactSet.NONE;
    }

    public static Stats getSetBonus(ArrayList<Artifact> arts, AttackType atkType, Elements elem){
        int[] arr = new int[ArtifactSet.values().length];

        for (Artifact art : arts)
            arr[art.set.ordinal()] += 1;

        ArrayList<Pair<Integer, Integer>> inds = new ArrayList<>();
        for (int i = 0; i < arr.length; ++i){
            if (arr[i] >= 2)
                inds.add(Pair.of(i, arr[i]));
        }

        Stats stats = new Stats();
        for (int i = 0; i < inds.size(); ++i)
            stats.add(getSetBonus(ArtifactSet.values()[inds.get(i).getLeft()], inds.get(i).getRight(), atkType, elem));

        return stats;
    }

    public static Stats getSetBonus(ArtifactSet set, int num, AttackType atkType, Elements elem){
        Stats stats = new Stats();

        if (num < 2) {
            return stats;
        }
        else if (num > 5){
            throw new RuntimeException("Number of set artifacts too much: " + num);
        }

        switch (set){
            case ADVENTURER -> {
                stats.hpF += 1000;
            }
            case LUCKY_DOG -> {
                stats.defF += 100;
            }
            case RESOLUTION_OF_SOJOURNER -> {
                stats.atkP += 0.18;
            }
            case BERSERKER -> {
                stats.critRate += 0.12;
                if (num >= 4)
                    stats.critRate += 0.24;
            }
            case INSTRUCTOR -> {
                stats.em += 80;
                if (num >= 4)
                    stats.em += 120;
            }
            case THE_EXILE -> {
                stats.er += 0.2;
            }
            case DEFENDERS_WILL -> {
                stats.defP += 0.3;
            }
            case BRAVE_HEART -> {
                stats.atkP += 0.18;
                if (num >= 4)
                    stats.dmgBonus += 0.3;
            }
            case MARTIAL_ARTIST -> {
                if (atkType == AttackType.NORMAL || atkType == AttackType.CHARGED) {
                    stats.dmgBonus += 0.15;
                    if (num >= 4)
                        stats.dmgBonus += 0.25;
                }
            }
            case GAMBLER -> {
                if (atkType == AttackType.ELEMENT_SKILL){
                    stats.dmgBonus += 0.2;
                }
            }
            case SCHOLAR -> {
                stats.er += 0.2;
            }
            case GLADIATORS_FINALE -> {
                stats.atkP += 0.18;
                if (atkType == AttackType.NORMAL){
                    // Sword, Claymore or Polearm
                    stats.dmgBonus += 0.3;
                }
            }
            case WANDERERS_TROUPE -> {
                stats.em += 80;
                if (atkType == AttackType.CHARGED){
                    // Catalyst or Bow
                    stats.dmgBonus += 0.35;
                }
            }
            case THUNDERSOOTHER -> {
                if (num >= 4)
                    stats.dmgBonus += 0.35;
            }
            case THUNDERING_FURY -> {
                if (elem == Elements.ELECTRO)
                    stats.dmgBonus += 0.15;

            }
            case MAIDEN_BELOVED -> {
                stats.healBonus += 0.15;
            }
            case VIRIDESCENT_VENERER -> {
                if (elem == Elements.ANEMO)
                    stats.dmgBonus += 0.15;

            }
            case CRIMSON_WITCH_OF_FLAMES -> {
                if (elem == Elements.PYRO)
                    stats.dmgBonus += 0.15;
            }
            case LAVAWALKER -> {
                if (num >= 4)
                    stats.dmgBonus += 0.35;
            }
            case NOBLESSE_OBLIGE -> {
                if (atkType == AttackType.ELEMENT_BURST)
                    stats.dmgBonus += 0.2;

            }
            case BLOODSTAINED_CHIVALRY -> {
                if (elem == Elements.PHYSICAL)
                    stats.dmgBonus += 0.2;

                if (num >= 4)
                    stats.dmgBonus += 0.5;
            }
            case ARCHAIC_PETRA -> {
                if (elem == Elements.GEO)
                    stats.dmgBonus += 0.15;

                if (num >= 4)
                    stats.dmgBonus += 0.35;
            }
            case RETRACING_BOLIDE -> {
                if (num >= 4 && (atkType == AttackType.NORMAL || atkType == AttackType.CHARGED))
                    stats.dmgBonus += 0.4;
            }
            case BLIZZARD_STRAYER -> {
                if (elem == Elements.CRYO){
                    stats.dmgBonus += 0.15;

                    if (num >= 4)
                        stats.critRate += 0.2;
                }
            }
            case HEART_OF_DEPTH -> {
                if (elem == Elements.HYDRO)
                    stats.dmgBonus += 0.15;

                if (num >= 4 && (atkType == AttackType.NORMAL || atkType == AttackType.CHARGED))
                    stats.dmgBonus += 0.3;
            }
            case TENACITY_OF_THE_MILLELITH -> {
                stats.hpP += 0.2;
            }
            case PALE_FLAME -> {
                if (elem == Elements.PHYSICAL)
                    stats.dmgBonus += 0.2;
            }
            case SHIMENAWAS_REMINISCENCE -> {
                stats.atkP += 0.18;

                if (num >= 4 &&
                        (atkType == AttackType.NORMAL
                        || atkType == AttackType.CHARGED
                        || atkType == AttackType.PLUNGING
                        )
                )
                    stats.dmgBonus += 0.5;
            }
            case EMBLEM_OF_SEVERED_FATE -> {
                stats.er += 0.18;

                if (num >= 4 && atkType == AttackType.ELEMENT_BURST){
                    stats.dmgBonus += stats.er / 4;
                }
            }
            case HUSK_OF_OPULENT_DREAMS -> {
                stats.defP += 0.3;

                if (num >= 4){
                    stats.defP += 0.24;

                    if (elem == Elements.GEO)
                        stats.dmgBonus += 0.24;
                }
            }
            case OCEAN_HUED_CLAM -> {
                stats.healBonus += 0.15;

                if (num >= 4){
                    stats.finalAtkAdd = 30000;
                }
            }
        }

        return stats;
    }

}
