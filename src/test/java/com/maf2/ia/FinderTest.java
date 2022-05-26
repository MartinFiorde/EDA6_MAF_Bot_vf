package com.maf2.ia;

import com.maf2.DTO.DataMessageDTO;
import com.maf2.DTO.MessageDTO;
import com.maf2.entity.Board;
import com.maf2.entity.Pawn;
import com.maf2.entity.Wall;
import com.maf2.utils.Util;
import org.junit.Test;
import static org.junit.Assert.*;

// Tests: 10 - Testable methods: 10 - Coverage: 100%
public class FinderTest {

    private final Util util;
    private final Reader reader;

    public FinderTest() {
        util = new Util();
        reader = new Reader();
    }

    @Test
    public void testNextMove() {
        System.out.println("nextMove");

        MessageDTO msjObj = EventYourTurnOBJForTesting();

        Finder instance = new Finder();

        DataMessageDTO expResult = ActionMoveOBJForTesting().getData();
        expResult.setGame_id(null);
        expResult.setTurn_token(null);

        DataMessageDTO result = instance.nextMove(msjObj);
        assertEquals(expResult, result);
    }

    @Test
    public void testTryJump() {
        System.out.println("tryJump");

        Board board = boardOBJForTesting();

        Finder instance = new Finder();

        DataMessageDTO expResult = null;

        DataMessageDTO result = instance.tryJump(board);
        assertEquals(expResult, result);
    }

    @Test
    public void testTryClearBase() {
        System.out.println("tryClearBase");

        Board board = boardOBJForTesting();

        Finder instance = new Finder();

        DataMessageDTO expResult = new DataMessageDTO();
        expResult.setFrom_row(8);
        expResult.setFrom_col(4);
        expResult.setTo_row(7);
        expResult.setTo_col(4);

        DataMessageDTO result = instance.tryClearBase(board);
        assertEquals(expResult, result);
    }

    @Test
    public void testTryDefendBase() {
        System.out.println("tryDefendBase");

        Board board = boardOBJForTesting2();

        Finder instance = new Finder();

        DataMessageDTO expResult = ActionWallOBJForTesting2().getData();
        expResult.setGame_id(null);
        expResult.setTurn_token(null);

        DataMessageDTO result = instance.tryDefendBase(board);
        assertEquals(expResult, result);
    }

    @Test
    public void testTryToPutWall() {
        System.out.println("tryToPutWall");

        Board boardObj = boardOBJForTesting2();
        Pawn enemy = boardObj.getEnemyPawns().get(2);

        Finder instance = new Finder();

        Wall expResult = WallOBJForTesting2();

        Wall result = instance.tryToPutWall(boardObj, enemy);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckLeftExtention() {
        System.out.println("checkLeftExtention");

        Wall wall = WallOBJForTesting2();
        String[][] board = reader.turnStringIntoMatrix(boardOBJForTesting2().getBoardEtoM());
        int newx = 13;
        int fronty = 12;

        Finder instance = new Finder();

        Wall expResult = null;

        Wall result = instance.checkLeftExtention(wall, board, newx, fronty);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckRightExtention() {
        System.out.println("checkRightExtention");
        Wall wall = WallOBJForTesting2();
        String[][] board = reader.turnStringIntoMatrix(boardOBJForTesting2().getBoardEtoM());
        int newx = 13;
        int fronty = 14;

        Finder instance = new Finder();

        Wall expResult = WallOBJForTesting2();

        Wall result = instance.checkLeftExtention(wall, board, newx, fronty);
        assertEquals(expResult, result);
    }

    @Test
    public void testTryNormalMove() {
        System.out.println("tryNormalMove");

        Board board = boardOBJForTesting();

        Finder instance = new Finder();

        DataMessageDTO expResult = ActionMoveOBJForTesting().getData();
        expResult.setGame_id(null);
        expResult.setTurn_token(null);

        DataMessageDTO result = instance.tryNormalMove(board);
        assertEquals(expResult, result);
    }

    @Test
    public void testAddMoveIntoDataMsj() {
        System.out.println("addMoveIntoDataMsj");

        Pawn move = boardOBJForTesting().getMyPawns().get(0);

        Finder instance = new Finder();

        DataMessageDTO expResult = ActionMoveOBJForTesting().getData();
        expResult.setGame_id(null);
        expResult.setTurn_token(null);

        DataMessageDTO result = instance.addMoveIntoDataMsj(move);
        assertEquals(expResult, result);
    }

    @Test
    public void testAddWallIntoDataMsj() {
        System.out.println("addWallIntoDataMsj");

        Wall wall = WallOBJForTesting2();

        Finder instance = new Finder();

        DataMessageDTO expResult = ActionWallOBJForTesting2().getData();
        expResult.setGame_id(null);
        expResult.setTurn_token(null);

        DataMessageDTO result = instance.addWallIntoDataMsj(wall);
        assertEquals(expResult, result);
    }

    // server Event DTO, Board Object and Action DTO, where my bot should answer with a normal lateral move
    public MessageDTO EventYourTurnOBJForTesting() {
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
        return util.JSONToObject(json);
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

    public MessageDTO ActionMoveOBJForTesting() {
        String json = "{\n"
                + "  \"action\": \"move\",\n"
                + "  \"data\": {\n"
                + "    \"game_id\": \"713fe8f8-dc8e-11ec-aef0-7ecdf393f9cc\",\n"
                + "    \"turn_token\": \"caf10ed6-ea16-4671-a751-be480148b930\",\n"
                + "    \"from_row\": 2,\n"
                + "    \"from_col\": 6,\n"
                + "    \"to_row\": 2,\n"
                + "    \"to_col\": 5\n"
                + "  }\n"
                + "}";
        return util.JSONToObject(json);
    }

    // server Board Object, Wall Object and Action DTO, where my bot should answer blocking anenemy pawn with a wall
    public Board boardOBJForTesting2() {
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
                + "    \"board\": \"  N     N                                                    -*- -*-            S    -*- -*- -*- -*-                                                                                         -*-                        N    -*- -*- -*-                                                S     S  \",\n"
                + "    \"turn_token\": \"asdf\",\n"
                + "    \"game_id\": \"qwer\"\n"
                + "  }\n"
                + "}";
        return reader.fillBoardObj(util.JSONToObject(json));
    }

    public Wall WallOBJForTesting2() {
        Wall wall = new Wall();
        wall.setSide("S");
        wall.setX(13);
        wall.setY(13);
        wall.setOrientation("h");
        return wall;
    }

    public MessageDTO ActionWallOBJForTesting2() {
        String json = "{\n"
                + "  \"action\": \"wall\",\n"
                + "  \"data\": {\n"
                + "    \"game_id\": \"qwer\",\n"
                + "    \"turn_token\": \"asdf\",\n"
                + "    \"row\": 6,\n"
                + "    \"col\": 6,\n"
                + "    \"orientation\": \"h\"\n"
                + "  }\n"
                + "}";

        //DataMessageDTO{row=6, col=5, orientation=h}
        return util.JSONToObject(json);
    }
}
