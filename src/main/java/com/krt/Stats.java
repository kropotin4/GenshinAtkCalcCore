package com.krt;

import com.krt.json.ArtifactSubStatJson;

import java.util.Objects;

public class Stats {
    public double  atkP = 0;   // ATK %
    public int     atkF = 0;   // ATK + (Flat)
    public double  defP = 0;   // DEF %
    public int     defF = 0;   // DEF + (Flat)
    public double  hpP = 0;    // HP %
    public int     hpF = 0;    // HP + (Flat)
    public double  em = 0;     // Elemental Mastery
    public double  er = 0;     // Energy Recharge
    public double  critRate = 0.0;
    public double  critDMG = 0.0;
    public double  dmgBonus = 0.0;
    public double  healBonus = 0.0;

    public double  finalAtkAdd = 0.0;

    public Stats(){};
    public Stats(ArtifactSubStatJson stats){
        atkP = stats.atkP;
        atkF = stats.atkF;
        defP = stats.defP;
        defF = stats.defF;
        hpP = stats.hpP;
        hpF = stats.hpF;
        em = stats.em;
        er = stats.er;
        critRate = stats.crit_rate;
        critDMG = stats.crit_dmg;
    }

    public void add(Stats other){
        atkP += other.atkP;
        atkF += other.atkF;
        defP += other.defP;
        defF += other.defF;
        hpP += other.hpP;
        hpF += other.hpF;
        em += other.em;
        er += other.er;
        critRate += other.critRate;
        critDMG += other.critDMG;
        dmgBonus += other.dmgBonus;
    }
    public void add(SubStatType stat, double val){
        switch (stat){
            case ATK -> {
                atkP += val;
            }
            case DEF -> {
                defP += val;
            }
            case HP -> {
                hpP += val;
            }
            case CRIT_RATE -> {
                critRate += val;
            }
            case CRIT_DMG -> {
                critDMG += val;
            }
            case ELEM_BONUS -> {
                dmgBonus += val;
            }
            case HEAL_BONUS -> {
                healBonus += val;
            }
            case EM -> {
                em += val;
            }
            case ER -> {
                er += val;
            }
        }
    }

    public Stats swap(){
        Stats stats = new Stats();

        stats.atkP = -atkP;
        stats.atkF = -atkF;
        stats.defP = -defP;
        stats.defF = -defF;
        stats.hpP = -hpP;
        stats.hpF = -hpF;
        stats.em = -em;
        stats.er = -er;
        stats.critRate = -critRate;
        stats.critDMG = -critDMG;
        stats.dmgBonus = -dmgBonus;

        return stats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stats stats = (Stats) o;
        return Double.compare(stats.atkP, atkP) == 0 && atkF == stats.atkF && Double.compare(stats.defP, defP) == 0 && defF == stats.defF && Double.compare(stats.hpP, hpP) == 0 && hpF == stats.hpF && Double.compare(stats.em, em) == 0 && Double.compare(stats.er, er) == 0 && Double.compare(stats.critRate, critRate) == 0 && Double.compare(stats.critDMG, critDMG) == 0 && Double.compare(stats.dmgBonus, dmgBonus) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(atkP, atkF, defP, defF, hpP, hpF, em, er, critRate, critDMG, dmgBonus);
    }


    public String toString(boolean low) {
        StringBuilder strb = new StringBuilder();

        if (atkP != 0)
            strb.append("ATK%: " + atkP + "\n");
        if (atkF != 0 && !low)
            strb.append("ATK+: " + atkF + "\n");
        if (defP != 0)
            strb.append("DEF%: " + defP + "\n");
        if (defF != 0 && !low)
            strb.append("DEF+: " + defF + "\n");
        if (hpP != 0)
            strb.append("HP%: " + hpP + "\n");
        if (hpF != 0 && !low)
            strb.append("HP+: " + hpF + "\n");
        if (em != 0)
            strb.append("Elem mastery: " + em + "\n");
        if (er != 0)
            strb.append("Energy recharge: " + er + "\n");
        if (critRate != 0)
            strb.append("Crit rate: " + critRate + "\n");
        if (critDMG != 0)
            strb.append("Crit dmg: " + critDMG + "\n");
        if (dmgBonus != 0)
            strb.append("Dmg bonus: " + dmgBonus + "\n");
        if (healBonus != 0)
            strb.append("Heal bonus: " + healBonus + "\n");

        return strb.toString();
    }

    @Override
    public String toString() {
        return toString(true);
    }
}
