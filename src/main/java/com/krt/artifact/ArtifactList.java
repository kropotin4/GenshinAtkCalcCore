package com.krt.artifact;

import com.krt.JsonFiles;
import com.krt.Utils;
import com.krt.json.ArtifactJson;

import java.util.ArrayList;

public class ArtifactList {
    public ArrayList<Artifact> flower;
    public ArrayList<Artifact> plume;
    public ArrayList<Artifact> clock;
    public ArrayList<Artifact> goblen;
    public ArrayList<Artifact> crown;

    /////////////////////////////////

    public ArtifactList(boolean norm){
        flower = new ArrayList<>(JsonFiles.arts_flower.list.size());
        plume = new ArrayList<>(JsonFiles.arts_plume.list.size());
        clock = new ArrayList<>(JsonFiles.arts_clock.list.size());
        goblen = new ArrayList<>(JsonFiles.arts_goblet.list.size());
        crown = new ArrayList<>(JsonFiles.arts_crown.list.size());

        for (ArtifactJson art : JsonFiles.arts_flower.list){
            flower.add(new Artifact(ArtifactType.FLOWER, art.set, art.lvl, art.main, art.subs));
        }
        for (ArtifactJson art : JsonFiles.arts_plume.list){
            plume.add(new Artifact(ArtifactType.PLUME, art.set, art.lvl, art.main, art.subs));
        }
        for (ArtifactJson art : JsonFiles.arts_clock.list){
            clock.add(new Artifact(ArtifactType.CLOCK, art.set, art.lvl, art.main, art.subs));
        }
        for (ArtifactJson art : JsonFiles.arts_goblet.list){
            goblen.add(new Artifact(ArtifactType.GOBLET, art.set, art.lvl, art.main, art.subs));
        }
        for (ArtifactJson art : JsonFiles.arts_crown.list){
            crown.add(new Artifact(ArtifactType.CROWN, art.set, art.lvl, art.main, art.subs));
        }

        if (norm) {
            for (Artifact art : flower)
                Utils.normalize(art);
            for (Artifact art : plume)
                Utils.normalize(art);
            for (Artifact art : clock)
                Utils.normalize(art);
            for (Artifact art : goblen)
                Utils.normalize(art);
            for (Artifact art : crown)
                Utils.normalize(art);
        }
    }
    public ArtifactList(){
        this(true);
    }

}
