package com.krt.json;

import com.krt.SubStatType;

import java.util.List;

public class ArtifactMainStatJson {
    public List<Double> hpF;
    public List<Double>  hpP;
    public List<Double> atkF;
    public List<Double>  atkP;
    public List<Double>  def;
    public List<Double>  phys;
    public List<Double>  elem;
    public List<Double> em;
    public List<Double>  er;
    public List<Double>  crit_rate;
    public List<Double>  crit_dmg;
    public List<Double>  heal;

    public List<Double> getList(SubStatType type){
        switch (type){
            case ATK -> {
                return atkP;
            }
            case DEF -> {
                return def;
            }
            case HP -> {
                return hpP;
            }
            case CRIT_RATE -> {
                return crit_rate;
            }
            case CRIT_DMG -> {
                return crit_dmg;
            }
            case PHYS_BONUS -> {
                return phys;
            }
            case ELEM_BONUS -> {
                return elem;
            }
            case HEAL_BONUS -> {
                return heal;
            }
            case EM -> {
                return em;
            }
            case ER -> {
                return er;
            }
        }

        throw new RuntimeException();
    }
}
