package com.krt.artifact;

import com.krt.Utils;

public enum ArtifactType {
    FLOWER,
    PLUME,
    CLOCK,  // Sands
    GOBLET, // Cup
    CROWN;

    public static ArtifactType parse(String str){
        if (Utils.match("FLOWER", str))
            return ArtifactType.FLOWER;
        if (Utils.match("PLUME", str))
            return ArtifactType.PLUME;
        if (Utils.match("CLOCK", str))
            return ArtifactType.CLOCK;
        if (Utils.match("GOBLET", str))
            return ArtifactType.GOBLET;
        if (Utils.match("CROWN", str))
            return ArtifactType.CROWN;

        throw new RuntimeException("Wrong ArtifactType name: " + str);
    }

}
