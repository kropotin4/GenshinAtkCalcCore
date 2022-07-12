package com.krt.character;

import com.krt.*;
import com.krt.artifact.Artifact;
import com.krt.artifact.ArtifactList;
import com.krt.artifact.ArtifactSet;
import com.krt.atk.AttackSequence;
import com.krt.atk.AttackType;
import com.krt.atk.CritType;
import com.krt.atk.Reaction;
import com.krt.json.CharJson;
import com.krt.weapon.Weapon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Char {

    // Base stat
    String name;
    int lvl;
    Elements elem;
    int baseHp;
    int baseAtk;
    int baseDef;
    double subStat;

    // Special Stats
    SubStatType subStatType;
    Stats spec_stats;

    Weapon weapon; // Weapon stats -> in spec_stats

    ArrayList<Artifact> arts;
    Stats arts_stats;

    ///////////////////

    public Char(String name, int lvl) throws FileNotFoundException {
        this.lvl = lvl;

        this.name = null;

        int ind_lvl = JsonFiles.chars.lvl.indexOf(lvl);
        for (CharJson item : JsonFiles.chars.chars){
            if (name.equals(item.name)){
                this.name = item.name;
                elem = Elements.parse(item.element);
                subStatType = SubStatType.parse(item.subStatType);
                baseHp = item.baseHP.get(ind_lvl);
                baseAtk = item.baseATK.get(ind_lvl);
                baseDef = item.baseDEF.get(ind_lvl);
                subStat = item.subStat.get(ind_lvl);

                spec_stats = new Stats();
                spec_stats.add(subStatType, subStat);
                Utils.normalize(spec_stats);

                spec_stats.add(SubStatType.CRIT_RATE, 0.05);
                spec_stats.add(SubStatType.CRIT_DMG, 0.5);
                spec_stats.add(SubStatType.ER, 1);

                break;
            }
        }

        if (this.name == null){
            throw new RuntimeException("Character " + name + " not found.");
        }

        arts = new ArrayList<>(5);
        arts.add(null);
        arts.add(null);
        arts.add(null);
        arts.add(null);
        arts.add(null);
    }

    public void setWeapon(Weapon weapon){
        if (this.weapon != null){
            spec_stats.add(this.weapon.getSubStat().swap());
        }

        this.weapon = weapon;
        spec_stats.add(weapon.getSubStat());
    }

    public void setArtifact(Artifact art){
        arts.set(art.getType().ordinal(), art);

        artsStatsUpdate();
    }
    public void setArtifacts(Artifact flower, Artifact plume, Artifact clock, Artifact goblet, Artifact crown){
        arts.set(0, flower);
        arts.set(1, plume);
        arts.set(2, clock);
        arts.set(3, goblet);
        arts.set(4, crown);

        artsStatsUpdate();
    }
    public void setRandArtifacts(ArtifactList list){
        Random rand = new Random();

        arts.set(0, list.flower.get(rand.nextInt(list.flower.size())));
        arts.set(1, list.plume.get(rand.nextInt(list.plume.size())));
        arts.set(2, list.clock.get(rand.nextInt(list.clock.size())));
        arts.set(3, list.goblen.get(rand.nextInt(list.goblen.size())));
        arts.set(4, list.crown.get(rand.nextInt(list.crown.size())));

        artsStatsUpdate();
    }

    private void artsStatsUpdate(){
        arts_stats = new Stats();

        for (Artifact art : arts){
            if (art != null)
                arts_stats.add(art.getStats());
        }

        if (arts.get(3) != null)
            if (arts.get(3).isElementGoblet())
                if (arts.get(3).getElement() != elem)
                    arts_stats.dmgBonus = 0;

    }


    public double calcDmg(double skillDMG, int lvlEnemy, Reaction reaction, CritType critType, AttackType atkType){
        double atk, affix, crit, resist, react, reactDMG, def, res;
        double resistEnemy, resistReduction, defReduction;

        Stats stats = new Stats();
        stats.add(spec_stats);
        stats.add(arts_stats);

        stats.add(ArtifactSet.getSetBonus(arts, atkType, elem));

        atk = (baseAtk + weapon.getATK()) * (1 + stats.atkP) + stats.atkF;
        affix = 1 + stats.dmgBonus;

        if (critType == CritType.NONE)
            crit = 1;
        else if (critType == CritType.AVERAGE)
            crit = 1 + stats.critDMG * stats.critRate;
        else
            crit = 1 + stats.critDMG;

        if (reaction == Reaction.NONE){
            react = 1;
            reactDMG = 1;
        }
        else if (reaction == Reaction.MELT || reaction == Reaction.VAPORIZE){
            if (elem == Elements.PYRO){
                react = reaction == Reaction.MELT ? 2 : 1.5;
            }
            else if (elem == Elements.HYDRO) {
                react = 2;
            }
            else if (elem == Elements.CRYO){
                react = 1.5;
            }
            else {
                throw new RuntimeException("Wrong reaction type");
            }

            reactDMG = 1 + (2.78 * stats.em) / (stats.em + 1400);
        }
        else {
            // TODO
            react = 1;
            reactDMG = 1;
        }

        resistEnemy = 0;
        resistReduction = 0;;
        resist = 1 - resistEnemy + resistReduction;

        defReduction = 0;
        def = (lvl + 100) / ((lvlEnemy + 100) * (1 - defReduction) + lvl + 100);

        res = atk * affix * crit * skillDMG * react * reactDMG * resist * def + stats.finalAtkAdd;

        return res;
    }

    public double calcDmg(AttackSequence atkSeq, int lvlEnemy, Reaction reaction, CritType critType){
        double res = 0;

        if (atkSeq.normalNum != 0)
            res += calcDmg(1.0, lvlEnemy, reaction, critType, AttackType.NORMAL) * atkSeq.normalNum;
        if (atkSeq.chargedNum != 0)
            res += calcDmg(1.0, lvlEnemy, reaction, critType, AttackType.CHARGED) * atkSeq.chargedNum;
        if (atkSeq.plungingNum != 0)
            res += calcDmg(1.0, lvlEnemy, reaction, critType, AttackType.PLUNGING) * atkSeq.plungingNum;
        if (atkSeq.elemSkillNum != 0)
            res += calcDmg(1.0, lvlEnemy, reaction, critType, AttackType.ELEMENT_SKILL) * atkSeq.elemSkillNum;
        if (atkSeq.elemBurstNum != 0)
            res += calcDmg(1.0, lvlEnemy, reaction, critType, AttackType.ELEMENT_BURST) * atkSeq.elemBurstNum;

        return res;
    }

}
