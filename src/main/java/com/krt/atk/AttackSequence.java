package com.krt.atk;

public class AttackSequence {

    public int normalNum = 0;
    public int chargedNum = 0;
    public int plungingNum = 0;
    public int elemSkillNum = 0;
    public int elemBurstNum = 0;

    public void add(AttackType atk, int num){
        switch (atk){
            case NORMAL -> {
                normalNum += num;
            }
            case CHARGED -> {
                chargedNum += num;
            }
            case PLUNGING -> {
                plungingNum += num;
            }
            case ELEMENT_SKILL -> {
                elemSkillNum += num;
            }
            case ELEMENT_BURST -> {
                elemBurstNum += num;
            }
        }
    }
    public void add(AttackType atk){
        add(atk, 1);
    }

    public void clear(){
        normalNum = 0;
        chargedNum = 0;
        plungingNum = 0;
        elemSkillNum = 0;
        elemBurstNum = 0;
    }

}
