package com.maf2.ia;

import com.maf2.DTO.DataMessageDTO;
import com.maf2.DTO.MessageDTO;
import com.maf2.entity.Board;
import com.maf2.entity.Pawn;
import com.maf2.utils.Util;
import org.junit.Test;
import static org.junit.Assert.*;

// Tests: 6 - Testable methods: 10 - Coverage: 60%
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

        Board board = boardOBJForTesting();

        Finder instance = new Finder();

        DataMessageDTO expResult = null;

        DataMessageDTO result = instance.tryDefendBase(board);
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

}
