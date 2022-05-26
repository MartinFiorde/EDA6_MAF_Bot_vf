package com.maf2.ia;

import com.maf2.DTO.MessageDTO;
import com.maf2.entity.Board;
import com.maf2.entity.Pawn;
import java.util.ArrayList;
import java.util.List;

public class Reader {

    // main method of the class. makes a reading and interpretation of the board to find the best moves per pawn
    public Board fillBoardObj(MessageDTO msjObj) {
        Board board = new Board();
        board.setSide(msjObj.getData().getSide());
        board.setNumberOfWalls(Double.valueOf(msjObj.getData().getWalls()));
        board.setBoardEtoM(makeBoardEtoM(board.getSide(), msjObj.getData().getBoard()));
        board.setBoardWithoutPawns(clearPawnsOnBoard(board.getBoardEtoM()));
        board.setMyPawns(setListOfPawns("M", board));
        board.setEnemyPawns(setListOfPawns("E", board));
        return board;
    }

    public String makeBoardEtoM(String side, String board) {
        String newBoard = "";
        newBoard = SimplifyBoard(side, board);
        if (side.equals("N")) {
            newBoard = rotateBoard(side, newBoard);
        }
        newBoard = newBoard.replaceFirst("M", "A");
        newBoard = newBoard.replaceFirst("M", "B");
        newBoard = newBoard.replaceFirst("M", "C");
        newBoard = newBoard.replaceFirst("E", "F");
        newBoard = newBoard.replaceFirst("E", "G");
        newBoard = newBoard.replaceFirst("E", "H");

//        if (side.equals("S")) {                        // IF only if I'm playing against myself, to avoid duplicate boards
//        showBoardOnConsole(newBoard, side);            // SOUT - DISPLAY IN CONSOLE TO SEE THE DASHBOARD ANALYZED BY THE BOT
//        }

        return newBoard;
    }

    public String SimplifyBoard(String side, String board) {
        String newBoard = "";
        for (int i = 0; i < board.length(); i++) {
            int x = i / 17;
            int y = i % 17;
            String enemy = "S";
            if (side.equals("S")) {
                enemy = "N";
            }
            newBoard = newBoard + convertCell(board.substring(i, i + 1), x, y, side, enemy);
        }
        return newBoard;
    }

    public String rotateBoard(String side, String board) {
        String newBoard = "";
        for (int i = 0; i < board.length(); i++) {
            newBoard = newBoard + board.substring((board.length() - i - 1), (board.length() - i));
        }
        return newBoard;
    }

    public String clearPawnsOnBoard(String newBoard) {
        newBoard = newBoard.replaceFirst("A", " ");
        newBoard = newBoard.replaceFirst("B", " ");
        newBoard = newBoard.replaceFirst("C", " ");
        newBoard = newBoard.replaceFirst("F", " ");
        newBoard = newBoard.replaceFirst("G", " ");
        newBoard = newBoard.replaceFirst("H", " ");
        return newBoard;
    }

    public String convertCell(String cell, int x, int y, String side, String enemy) {
        if (cell.equals("*") || cell.equals("-") || cell.equals("|")) {
            cell = "X";
        }
        if (cell.equals(" ")) {
            if ((x % 2) == 1 || (y % 2) == 1) {
                cell = "|";
            }
        }
        if (cell.equals(" ")) {
            if ((x % 2) == 0 && (y % 2) == 0) {
                cell = " ";
            }
        }
        if (cell.equals(side)) {
            cell = "M";
        }
        if (cell.equals(enemy)) {
            cell = "E";
        }
        return cell;
    }

    public List<Pawn> setListOfPawns(String team, Board boardObj) {
        List<Pawn> pawns = new ArrayList();
        if (team.equals("M")) {
            pawns.add(setIndividualPawn("A", team, boardObj));
            pawns.add(setIndividualPawn("B", team, boardObj));
            pawns.add(setIndividualPawn("C", team, boardObj));
        }
        if (team.equals("E")) {
            pawns.add(setIndividualPawn("F", team, boardObj));
            pawns.add(setIndividualPawn("G", team, boardObj));
            pawns.add(setIndividualPawn("H", team, boardObj));
        }
        return pawns;
    }

    public Pawn setIndividualPawn(String name, String team, Board boardObj) {
        Pawn pawn = new Pawn();
        pawn.setName(name);
        pawn.setSide(boardObj.getSide());
        pawn.setMyself(team.equals("M"));
        pawn.setOptimalMoveObstructed(false);
        pawn.setJumpChance(false);

        for (int i = 0; i < boardObj.getBoardEtoM().length(); i++) {
            if (boardObj.getBoardEtoM().substring(i, i + 1).equals(name)) {
                pawn.setFromx(i / 17);
                pawn.setFromy(i % 17);
                break;
            }
        }
        if (team.equals("M")) {
            pawn = setMatrixAndMovesToEnd(pawn, boardObj);
            pawn = setNextPositionXY(pawn, boardObj);
        }
        return pawn;
    }

    public Pawn setNextPositionXY(Pawn pawn, Board boardObj) {
        String[][] path = pawn.getMatrix();
        String[][] board = turnStringIntoMatrix(boardObj.getBoardEtoM());
        mainLoop:
        for (int i = 0; i < 17; i = i + 2) {
            for (int j = 0; j < 17; j = j + 2) {
                if (path[i][j].equals("1")) {
                    if (board[i][j].equals(" ")) {
                        pawn.setTox(i);
                        pawn.setToy(j);
                    } else {
                        if (board[i][j].equals("F") || board[i][j].equals("G") || board[i][j].equals("H")) {
                            trySide(pawn, path, board);
                            int x0 = pawn.getFromx();
                            int y0 = pawn.getFromy();
                            int x1 = i;
                            int y1 = j;
                            if (x0 <= 6) {
                                tryBack(pawn, path, board);
                            }
                            tryJump(pawn, path, board, x0, y0, x1, y1);
                        }
                        break mainLoop;
                    }
                    if (board[i][j].equals("A") || board[i][j].equals("B") || board[i][j].equals("C")) {
                        trySide(pawn, path, board);
                        break mainLoop;
                    }
                }
            }
        }
        if (pawn.getTox() == null || pawn.getToy() == null) {
            pawn.setOptimalMoveObstructed(true);
        }
        return pawn;
    }

    public Pawn trySide(Pawn pawn, String[][] path, String[][] board) {
        int x = pawn.getFromx();
        int y = pawn.getFromy();
        if (y - 2 >= 0 && y - 2 <= 16) {
            if (board[x][y - 2].equals(" ") && board[x][y - 1].equals("|")) {
                pawn.setTox(x);
                pawn.setToy(y - 2);
                return pawn;
            }
        }
        if (y + 2 >= 0 && y + 2 <= 16) {
            if (board[x][y + 2].equals(" ") && board[x][y + 1].equals("|")) {
                pawn.setTox(x);
                pawn.setToy(y + 2);
                return pawn;
            }
        }
        return pawn;
    }

    public Pawn tryBack(Pawn pawn, String[][] path, String[][] board) {
        int x = pawn.getFromx();
        int y = pawn.getFromy();
        if (x + 2 >= 0 && x + 2 <= 16) {
            if (board[x + 2][y].equals(" ") && board[x + 1][y].equals("|")) {
                pawn.setTox(x + 2);
                pawn.setToy(y);
                return pawn;
            }
        }
        return pawn;
    }

    public Pawn tryJump(Pawn pawn, String[][] path, String[][] board, int x0, int y0, int x1, int y1) {
        int x2 = x1 + (x1 - x0);
        int pathx2 = x1 + (x1 - x0) / 2;
        int y2 = y1 + (y1 - y0);
        int pathy2 = y1 + (y1 - y0) / 2;
        if (x2 >= 0 && x2 <= 16 && y2 >= 0 && y2 <= 16) {
            if (board[x2][y2].equals(" ") && board[pathx2][pathy2].equals("|")) {
                pawn.setJumpChance(true);
                pawn.setTox(x2);
                pawn.setToy(y2);
                return pawn;
            }
        }
        return pawn;
    }

    public Pawn setMatrixAndMovesToEnd(Pawn pawn, Board boardObj) {
        String[][] matrix = turnStringIntoMatrix(boardObj.getBoardWithoutPawns());
        int maxIterationsForLoop = 100;
        int totalMoves = 0;
        String actualPosition = "0";
        matrix[pawn.getFromx()][pawn.getFromy()] = "0";
        while (maxIterationsForLoop > 1) {
            totalMoves++;
            maxIterationsForLoop--;
            matrix = setCloserMoves(matrix, actualPosition, totalMoves);
            // check if the destination row is reached to clear the path and end the loop
            for (int i = 0; i < 17; i = i + 2) {
                if (!matrix[0][i].equals(" ")) {
                    matrix = leaveSinglePathToEnd(matrix, pawn, boardObj, totalMoves);
                    maxIterationsForLoop = 0;
                    break;
                }
            }
            actualPosition = String.valueOf(totalMoves);
        }
        pawn.setMovesToEnd(totalMoves);
        pawn.setMatrix(matrix);
        return pawn;
    }

    public String[][] turnStringIntoMatrix(String board) {
        String[][] matrix = new String[17][17];
        for (int i = 0; i < board.length(); i++) {
            matrix[i / 17][i % 17] = board.substring(i, i + 1);
        }
        return matrix;
    }

    public String[][] leaveSinglePathToEnd(String[][] matrix, Pawn pawn, Board boardObj, int actualMove) {
        int x = 0;
        int y = 0;
        // position the arrival point as first coordinate [x][y]
        for (int j = 0; j < 17; j = j + 2) {
            if (matrix[0][j].equals(String.valueOf(actualMove))) {
                y = j;
                break;
            }
        }
        while (actualMove > 0) {
            // change the value from "actualMove" to " " except when it matches the path
            clearActualPosition(matrix, x, y, actualMove);
            actualMove--;
            // each if looks for where the path continues, and changes the [x][y] coordinate to allow for the next iteration of the loop
            if ((y - 2) >= 0 && (y - 2) <= 16) {
                if (matrix[x][y - 2].equals(String.valueOf(actualMove))) {
                    y = y - 2;
                }
            }
            if ((y + 2) >= 0 && (y + 2) <= 16) {
                if (matrix[x][y + 2].equals(String.valueOf(actualMove))) {
                    y = y + 2;
                }
            }
            if ((x + 2) >= 0 && (x + 2) <= 16) {
                if (matrix[x + 2][y].equals(String.valueOf(actualMove))) {
                    x = x + 2;
                }
            }
            if ((x - 2) >= 0 && (x - 2) <= 16) {
                if (matrix[x - 2][y].equals(String.valueOf(actualMove))) {
                    x = x - 2;
                }
            }
        }
        return matrix;
    }

    public String[][] clearActualPosition(String[][] matrix, int x, int y, int actualMove) {
        for (int i = 0; i < 17; i = i + 2) {
            for (int j = 0; j < 17; j = j + 2) {
                if (matrix[16 - i][16 - j].equals(String.valueOf(actualMove))) {
                    matrix[16 - i][16 - j] = " ";
                }
            }
        }
        matrix[x][y] = String.valueOf(actualMove);
        return matrix;
    }

    public String[][] setCloserMoves(String[][] matrix, String actualPosition, int totalMoves) {
        for (int i = 0; i < 17; i = i + 2) {
            for (int j = 0; j < 17; j = j + 2) {
                if (matrix[i][j].equals(actualPosition)) {
                    matrix = tryPathToFront(matrix, i, j, String.valueOf(totalMoves));
                    matrix = tryPathToBack(matrix, i, j, String.valueOf(totalMoves));
                    matrix = tryPathToLeft(matrix, i, j, String.valueOf(totalMoves));
                    matrix = tryPathToRight(matrix, i, j, String.valueOf(totalMoves));
                }
            }
        }
        return matrix;
    }

    public String[][] tryPathToFront(String[][] matrix, int x, int y, String newValue) {
        int newX = x - 2;
        int newY = y;
        int wallX = x - 1;
        int wallY = y;
        // controls that new position exists within the boundaries of the board
        if ((newX) >= 0 && (newX) <= 16 && (newY) >= 0 && (newY) <= 16) {
            // check that there is not a wall blocking the way
            if (matrix[wallX][wallY].equals("|")) {
                // check that there is no faster movement preset in the destination cell
                if (matrix[newX][newY].equals(" ")) {
                    matrix[newX][newY] = newValue;
                }
            }
        }
        return matrix;
    }

    public String[][] tryPathToBack(String[][] matrix, int x, int y, String newValue) {
        int newX = x + 2;
        int newY = y;
        int wallX = x + 1;
        int wallY = y;
        if ((newX) >= 0 && (newX) <= 16 && (newY) >= 0 && (newY) <= 16) {
            if (matrix[wallX][wallY].equals("|")) {
                if (matrix[newX][newY].equals(" ")) {
                    matrix[newX][newY] = newValue;
                }
            }
        }
        return matrix;
    }

    public String[][] tryPathToLeft(String[][] matrix, int x, int y, String newValue) {
        int newX = x;
        int newY = y - 2;
        int wallX = x;
        int wallY = y - 1;
        if ((newX) >= 0 && (newX) <= 16 && (newY) >= 0 && (newY) <= 16) {
            if (matrix[wallX][wallY].equals("|")) {
                if (matrix[newX][newY].equals(" ")) {
                    matrix[newX][newY] = newValue;
                }
            }
        }
        return matrix;
    }

    public String[][] tryPathToRight(String[][] matrix, int x, int y, String newValue) {
        int newX = x;
        int newY = y + 2;
        int wallX = x;
        int wallY = y + 1;
        if ((newX) >= 0 && (newX) <= 16 && (newY) >= 0 && (newY) <= 16) {
            if (matrix[wallX][wallY].equals("|")) {
                if (matrix[newX][newY].equals(" ")) {
                    matrix[newX][newY] = newValue;
                }
            }
        }
        return matrix;
    }

    public void showBoardOnConsole(String board, String side) {
        System.out.println("MAIN BOARD - Side: " + side);
        for (int i = 0; i < board.length(); i = i + 17) {
            System.out.println("=" + board.substring(i, i + 17) + "=");
        }
    }

    public void showMatrixOnConsole(String[][] matrix) {
        for (int i = 0; i < 17; i++) {
            System.out.print("=");
            for (int j = 0; j < 17; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.print("=");
            System.out.println("");
        }
    }

}
