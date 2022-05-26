package com.maf2.ia;

import com.maf2.DTO.DataMessageDTO;
import com.maf2.DTO.MessageDTO;
import com.maf2.entity.Board;
import com.maf2.entity.Pawn;
import com.maf2.entity.Wall;

public class Finder {

    private Reader reader = new Reader();

    public DataMessageDTO nextMove(MessageDTO msjObj) {
        Board board = reader.fillBoardObj(msjObj);
        DataMessageDTO dataMsj = null;
        if (dataMsj == null) {
            dataMsj = tryJump(board);
        }
//        if (dataMsj == null && Double.valueOf(msjObj.getData().getRemaining_moves()) >= 180) {
//            dataMsj = tryClearBase(board);
//        }
        if (dataMsj == null) {
            dataMsj = tryDefendBase(board);
        }
        if (dataMsj == null) {
            dataMsj = tryNormalMove(board);
        }
        if (dataMsj == null) {
            dataMsj = loseTurn();
        }
        return dataMsj;
    }

    // if a pawn can jump, that move is prioritized
    public DataMessageDTO tryJump(Board board) {
        for (Pawn aux : board.getMyPawns()) {
            if ((!aux.isOptimalMoveObstructed()) && (aux.isJumpChance())) {
                return addMoveIntoDataMsj(aux);
            }
        }
        return null;
    }

    // if pawn is on start row, move to front
    public DataMessageDTO tryClearBase(Board board) {
        for (Pawn aux : board.getMyPawns()) {
            if ((!aux.isOptimalMoveObstructed()) && (aux.getFromx() == 16)) {
                return addMoveIntoDataMsj(aux);
            }
        }
        return null;
    }

    // if enemy is in row 1, cover
    public DataMessageDTO tryDefendBase(Board board) {
        for (int i = 0; i < 2; i++) {
            Pawn aux = board.getEnemyPawns().get(2-i);
            if (aux.getFromx() >= 2 && board.getNumberOfWalls() > 0) {
                Wall wall = tryToPutWall(board, aux);
                if (wall != null) {
                    return addWallIntoDataMsj(wall);
                }
            }
        }
        return null;
    }

    public Wall tryToPutWall(Board boardObj, Pawn enemy) {
        String[][] board = reader.turnStringIntoMatrix(boardObj.getBoardEtoM());
        int newx = enemy.getFromx() + 1;
        int fronty = enemy.getFromy();
        Wall wall = new Wall();
        wall.setOrientation("h");
        wall.setSide(boardObj.getSide());
        if (enemy.getFromy().equals(14)) {
            if (checkRightExtention(wall, board, newx, fronty) != null) {
                return checkRightExtention(wall, board, newx, fronty);
            }
        }
        if (checkLeftExtention(wall, board, newx, fronty) != null) {
            return checkLeftExtention(wall, board, newx, fronty);
        }
        if (checkRightExtention(wall, board, newx, fronty) != null) {
            return checkRightExtention(wall, board, newx, fronty);
        }
        return null;
    }

    public Wall checkLeftExtention(Wall wall, String[][] board, int newx, int fronty) {
        if (board[newx][fronty].equals("|")) {
            int newy = fronty - 1;
            int extray = fronty - 2;
            if (extray >= 0 && extray <= 16) {
                if (board[newx][newy].equals("|") && board[newx][extray].equals("|")) {
                    wall.setX(newx);
                    wall.setY(newy);
                    return wall;
                }
            }
        }
        return null;
    }

    public Wall checkRightExtention(Wall wall, String[][] board, int newx, int fronty) {
        if (board[newx][fronty].equals("|")) {
            int newy = fronty + 1;
            int extray = fronty + 2;
            if (extray >= 0 && extray <= 16) {
                if (board[newx][newy].equals("|") && board[newx][extray].equals("|")) {
                    wall.setX(newx);
                    wall.setY(newy);
                    return wall;
                }
            }
        }
        return null;
    }

    // move nearest piece to opposite side
    public DataMessageDTO tryNormalMove(Board board) {
        Pawn closerPawn = new Pawn();
        closerPawn.setMovesToEnd(99);
        for (Pawn aux : board.getMyPawns()) {
            if ((!aux.isOptimalMoveObstructed()) && (aux.getMovesToEnd() < closerPawn.getMovesToEnd())) {
                closerPawn = aux;
            }
        }
        return addMoveIntoDataMsj(closerPawn);
    }

    // last option as a safeguard, in case all other processes return null
    public DataMessageDTO loseTurn() {
        Pawn pawn = new Pawn();
        pawn.setFromx(0);
        pawn.setFromy(0);
        pawn.setTox(0);
        pawn.setToy(0);
        return addMoveIntoDataMsj(pawn);
    }

    // Take a Pawn Object to convert it into a DataMessageDTO Object
    public DataMessageDTO addMoveIntoDataMsj(Pawn move) {
        DataMessageDTO dataMsj = new DataMessageDTO();
        if (move.getSide().equals("N")) {
            dataMsj.setFrom_row(8 - (move.getFromx() / 2));
            dataMsj.setFrom_col(8 - (move.getFromy() / 2));
            dataMsj.setTo_row(8 - (move.getTox() / 2));
            dataMsj.setTo_col(8 - (move.getToy() / 2));
        } else {
            dataMsj.setFrom_row(move.getFromx() / 2);
            dataMsj.setFrom_col(move.getFromy() / 2);
            dataMsj.setTo_row(move.getTox() / 2);
            dataMsj.setTo_col(move.getToy() / 2);
        }
        return dataMsj;
    }

    // Take a Wall Object to convert it into a DataMessageDTO Object
    public DataMessageDTO addWallIntoDataMsj(Wall wall) {
        DataMessageDTO dataMsj = new DataMessageDTO();
        if (wall.getSide().equals("N")) {
            dataMsj.setRow(7 - ((wall.getX() - 1) / 2));
            dataMsj.setCol(7 - ((wall.getY() - 1) / 2));
        } else {
            dataMsj.setRow((wall.getX() - 1) / 2);
            dataMsj.setCol((wall.getY() - 1) / 2);
        }
        dataMsj.setOrientation(wall.getOrientation());
        return dataMsj;
    }

}
