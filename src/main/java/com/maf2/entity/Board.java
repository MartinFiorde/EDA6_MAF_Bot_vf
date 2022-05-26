package com.maf2.entity;

import java.util.List;
import lombok.Data;

@Data
public class Board {

    private String side;
    private double numberOfWalls;
    private String boardEtoM;
    private String boardWithoutPawns;
    private List<Pawn> myPawns;
    private List<Pawn> enemyPawns;

}
