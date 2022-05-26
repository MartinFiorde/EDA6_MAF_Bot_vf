package com.maf2.entity;

import com.google.common.base.MoreObjects;
import lombok.Data;

@Data
public class Pawn {
    
    private String name;
    private String side;
    private boolean myself;
    private Integer fromx;
    private Integer fromy;
    private Integer tox;
    private Integer toy;
    private Integer movesToEnd;
    private boolean jumpChance;
    private boolean optimalMoveObstructed;
    private String[][] matrix;
    
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("name", name)
                .add("side", side)
                .add("myself", myself)
                .add("fromx", fromx)
                .add("fromy", fromy)
                .add("tox", tox)
                .add("toy", toy)
                .add("movesToEnd", movesToEnd)
                .add("jumpChance", jumpChance)
                .add("optimalMoveObstructed", optimalMoveObstructed)
                /*.add("matrix", matrix)*/
                .toString();
    }
    
}
