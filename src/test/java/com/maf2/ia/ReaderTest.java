package com.maf2.ia;

import com.maf2.entity.Board;
import com.maf2.entity.Pawn;
import com.maf2.utils.Util;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

// Tests: 12 - Testable methods: 21 - Coverage: 57%
public class ReaderTest {

    private final Util util;
    private final Reader reader;

    public ReaderTest() {
        util = new Util();
        reader = new Reader();
    }

    @Test
    public void testMakeBoardEtoM() {
        System.out.println("makeBoardEtoM");

        String side = "S";
        String board = board1ForTesting();

        Reader instance = new Reader();

        String expResult = " |F| | |G| | | | ||||||||||||||||| | | | | | | | | ||||||||||XXX|XXX | | | | | |A| | XXX|XXX|XXX|XXX|| | | | | | | | | ||||||||||||||||| | | | | | | | | ||||||||||||||||| | | | | | | | | ||XXX|||||||||||| | | | | |H| | | XXX|XXX|XXX|||||| | | | | | | | | ||||||||||||||||| | | | |B| | |C| ";

        String result = instance.makeBoardEtoM(side, board);
        assertEquals(expResult, result);
    }

    @Test
    public void testSimplifyBoard() {
        System.out.println("SimplifyBoard");

        String side = "S";
        String board = "  N     N                                                    -*- -*-            S    -*- -*- -*- -*-                                                                                         -*-                      N      -*- -*- -*-                                                S     S  ";

        Reader instance = new Reader();

        String expResult = " |E| | |E| | | | ||||||||||||||||| | | | | | | | | ||||||||||XXX|XXX | | | | | |M| | XXX|XXX|XXX|XXX|| | | | | | | | | ||||||||||||||||| | | | | | | | | ||||||||||||||||| | | | | | | | | ||XXX|||||||||||| | | | | |E| | | XXX|XXX|XXX|||||| | | | | | | | | ||||||||||||||||| | | | |M| | |M| ";

        String result = instance.SimplifyBoard(side, board);
        assertEquals(expResult, result);
    }

    @Test
    public void testRotateBoard() {
        System.out.println("rotateBoard");

        String side = "N";
        String board = board1ForTesting();

        Reader instance = new Reader();

        String expResult = "  S     S                                                -*- -*- -*-      N                      -*-                                                                                         -*- -*- -*- -*-    S            -*- -*-                                                    N     N  ";

        String result = instance.rotateBoard(side, board);
        assertEquals(expResult, result);
    }

    @Test
    public void testClearPawnsOnBoard() {
        System.out.println("clearPawnsOnBoard");

        String newBoard = " |A| | |B| | | | ||||||||||||||||| | | | | | | | | ||||||||||XXX|XXX | | | | | |C| | XXX|XXX|XXX|XXX|| | | | | | | | | ||||||||||||||||| | | | | | | | | ||||||||||||||||| | | | | | | | | ||XXX|||||||||||| | | | | |F| | | XXX|XXX|XXX|||||| | | | | | | | | ||||||||||||||||| | | | |G| | |H| ";

        Reader instance = new Reader();

        String expResult = " | | | | | | | | ||||||||||||||||| | | | | | | | | ||||||||||XXX|XXX | | | | | | | | XXX|XXX|XXX|XXX|| | | | | | | | | ||||||||||||||||| | | | | | | | | ||||||||||||||||| | | | | | | | | ||XXX|||||||||||| | | | | | | | | XXX|XXX|XXX|||||| | | | | | | | | ||||||||||||||||| | | | | | | | | ";

        String result = instance.clearPawnsOnBoard(newBoard);
        assertEquals(expResult, result);
    }

    @Test
    public void testConvertCell() {
        System.out.println("convertCell");

        String cell = " ";
        int x = 1;
        int y = 1;
        String side = "S";
        String enemy = "N";

        Reader instance = new Reader();

        String expResult = "|";

        String result = instance.convertCell(cell, x, y, side, enemy);
        assertEquals(expResult, result);
    }

    @Test
    public void testSetListOfPawns() {
        System.out.println("setListOfPawns");

        String team = "M";
        Board boardObj = boardOBJForTesting();
        boardObj.setMyPawns(new ArrayList());
        boardObj.setEnemyPawns(new ArrayList());

        Reader instance = new Reader();

        List<Pawn> expResult = boardOBJForTesting().getMyPawns();

        List<Pawn> result = instance.setListOfPawns(team, boardObj);
        assertEquals(expResult, result);
    }

    @Test
    public void testSetIndividualPawn() {
        System.out.println("setIndividualPawn");
        String name = "A";
        String team = "M";
        Board boardObj = boardOBJForTesting();
        boardObj.setMyPawns(new ArrayList());

        Reader instance = new Reader();

        Pawn expResult = boardOBJForTesting().getMyPawns().get(0);

        Pawn result = instance.setIndividualPawn(name, team, boardObj);
        assertEquals(expResult, result);
    }

    @Test
    public void testSetNextPositionXY() {
        System.out.println("setNextPositionXY");

        Pawn pawn = boardOBJForTesting().getMyPawns().get(0);
        pawn.setTox(null);
        pawn.setToy(null);
        Board boardObj = boardOBJForTesting();

        Reader instance = new Reader();

        Pawn expResult = boardOBJForTesting().getMyPawns().get(0);

        Pawn result = instance.setNextPositionXY(pawn, boardObj);
        assertEquals(expResult, result);
    }

    @Test
    public void testTrySide() {
        System.out.println("trySide");

        Pawn pawn = boardOBJForTesting().getMyPawns().get(0);
        String[][] path = boardOBJForTesting().getMyPawns().get(0).getMatrix();
        pawn.setTox(null);
        pawn.setToy(null);
        String[][] board = reader.turnStringIntoMatrix(boardOBJForTesting().getBoardEtoM());

        Reader instance = new Reader();

        Pawn expResult = boardOBJForTesting().getMyPawns().get(0);

        Pawn result = instance.trySide(pawn, path, board);
        assertEquals(expResult, result);
    }

    @Test
    public void testTryBack() {
        System.out.println("tryBack");

        Pawn pawn = boardOBJForTesting().getMyPawns().get(0);
        String[][] path = boardOBJForTesting().getMyPawns().get(0).getMatrix();
        pawn.setTox(null);
        pawn.setToy(null);
        String[][] board = reader.turnStringIntoMatrix(boardOBJForTesting().getBoardEtoM());

        Reader instance = new Reader();

        Pawn expResult = boardOBJForTesting().getMyPawns().get(0);
        expResult.setTox(null);
        expResult.setToy(null);

        Pawn result = instance.tryBack(pawn, path, board);
        assertEquals(expResult, result);
    }

    @Test
    public void testSetMatrixAndMovesToEnd() {
        System.out.println("setMatrixAndMovesToEnd");

        Pawn pawn = boardOBJForTesting().getMyPawns().get(0);
        pawn.setTox(null);
        pawn.setToy(null);
        pawn.setMatrix(null);
        Board boardObj = boardOBJForTesting();

        Reader instance = new Reader();

        Pawn expResult = boardOBJForTesting().getMyPawns().get(0);
        expResult.setTox(null);
        expResult.setToy(null);

        Pawn result = instance.setMatrixAndMovesToEnd(pawn, boardObj);
        assertEquals(expResult, result);
    }

    @Test
    public void testTurnStringIntoMatrix() {
        System.out.println("turnStringIntoMatrix");
        // imput
        String board = "1231231231231231212312312312312312123123123123123121231231231231231212312312312312312123123123123123121231231231231231212312312312312312123123123123123121231231231231231212312312312312312123123123123123121231231231231231212312312312312312123123123123123121231231231231231212312312312312312";
        // instancia de la clase a probar
        Reader instance = new Reader();
        // output esperado
        String[][] expResult = new String[][]{
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},
            {"1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2", "3", "1", "2"},};
        // define el resultado del metodo utilizando el imput 
        String[][] result = instance.turnStringIntoMatrix(board);

        // comparacion 
        assertArrayEquals(expResult, result);
    }

    public Board boardOBJForTesting() {
        String json = "{\n"
                + "  \"event\": \"your_turn\",\n"
                + "  \"data\": {\n"
                + "    \"score_1\": 143.0,\n"
                + "    \"side\": \"S\",\n"
                + "    \"player_1\": \"MAF\",\n"
                + "    \"player_2\": \"MAF\",\n"
                + "    \"score_2\": 143.0,\n"
                + "    \"remaining_moves\": 133.0,\n"
                + "    \"walls\": 6.0,\n"
                + "    \"board\": \"  N     N                                                    -*- -*-            S    -*- -*- -*- -*-                                                                                         -*-                      N      -*- -*- -*-                                                S     S  \",\n"
                + "    \"turn_token\": \"caf10ed6-ea16-4671-a751-be480148b930\",\n"
                + "    \"game_id\": \"713fe8f8-dc8e-11ec-aef0-7ecdf393f9cc\"\n"
                + "  }\n"
                + "}";
        return reader.fillBoardObj(util.JSONToObject(json));
    }

    public String board1ForTesting() {
        String json = "{\n"
                + "  \"event\": \"your_turn\",\n"
                + "  \"data\": {\n"
                + "    \"score_1\": 143.0,\n"
                + "    \"side\": \"S\",\n"
                + "    \"player_1\": \"MAF\",\n"
                + "    \"player_2\": \"MAF\",\n"
                + "    \"score_2\": 143.0,\n"
                + "    \"remaining_moves\": 133.0,\n"
                + "    \"walls\": 6.0,\n"
                + "    \"board\": \"  N     N                                                    -*- -*-            S    -*- -*- -*- -*-                                                                                         -*-                      N      -*- -*- -*-                                                S     S  \",\n"
                + "    \"turn_token\": \"caf10ed6-ea16-4671-a751-be480148b930\",\n"
                + "    \"game_id\": \"713fe8f8-dc8e-11ec-aef0-7ecdf393f9cc\"\n"
                + "  }\n"
                + "}";
        return util.JSONToObject(json).getData().getBoard();
    }

}
