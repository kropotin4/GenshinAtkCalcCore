package com.krt;

import com.krt.artifact.Artifact;
import com.krt.artifact.ArtifactList;
import com.krt.atk.AttackType;
import com.krt.atk.CritType;
import com.krt.atk.Reaction;
import com.krt.character.Char;
import com.krt.weapon.Weapon;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        try {
            Char char1 = new Char("Kamisato Ayaka", 90);

            Weapon weapon1 = new Weapon("The Black Sword", 90);

            char1.setWeapon(weapon1);

            ArtifactList list = new ArtifactList();

            char1.setArtifact(list.flower.get(19));
            char1.setArtifact(list.plume.get(18));
            char1.setArtifact(list.clock.get(3));
            char1.setArtifact(list.goblen.get(15));
            char1.setArtifact(list.crown.get(17));

            double dmg = char1.calcDmg(1.09*3, 90, Reaction.NONE, CritType.AVERAGE, AttackType.NORMAL);

            System.out.println("Cur Damage: " + dmg);

            ArrayList<Artifact> res = ArtifactSelection.search(char1, list);

            for (Artifact art : res){
                char1.setArtifact(art);
            }

            dmg = char1.calcDmg(1.09*3, 90, Reaction.NONE, CritType.AVERAGE, AttackType.NORMAL);

            System.out.println("Search Damage: " + dmg);

            for (Artifact art : res){
                System.out.println(art);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
