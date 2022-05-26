package com.maf2.utils;

import com.maf2.DTO.DataMessageDTO;
import com.maf2.DTO.MessageDTO;
import com.maf2.enums.Action;
import com.maf2.enums.Event;
import org.junit.Test;
import static org.junit.Assert.*;

// Tests: 4 - Testable methods: 4 - Coverage: 100%
public class UtilTest {

    private Util util;

    public UtilTest() {
        util = new Util();
    }

    @Test
    public void testObjectToJSON() {
        System.out.println("objectToJSON");

        MessageDTO obj = new MessageDTO();
        DataMessageDTO data = new DataMessageDTO();
        data.setOpponent("MAF");
        data.setChallenge_id("1234");
        obj.setData(data);
        obj.setEvent(Event.CHALLENGE.getString());

        Util instance = new Util();

        String expResult = "{\n"
                + "  \"event\": \"challenge\",\n"
                + "  \"data\": {\n"
                + "    \"opponent\": \"MAF\",\n"
                + "    \"challenge_id\": \"1234\"\n"
                + "  }\n"
                + "}";

        String result = instance.objectToJSON(obj);
        assertEquals(expResult, result);
    }

    @Test
    public void testJSONToObject() {
        System.out.println("JSONToObject");

        String json = "{\n"
                + "  \"action\": \"accept_challenge\",\n"
                + "  \"data\": {\n"
                + "    \"challenge_id\": \"1234\"\n"
                + "  }\n"
                + "}";

        Util instance = new Util();

        MessageDTO expResult = new MessageDTO();
        DataMessageDTO data = new DataMessageDTO();
        data.setChallenge_id("1234");
        expResult.setData(data);
        expResult.setAction(Action.ACCEPT_CHALLENGE.getString());

        MessageDTO result = instance.JSONToObject(json);
        assertEquals(expResult, result);
    }

    @Test
    public void testAcceptChallenge() {
        System.out.println("acceptChallenge");

        MessageDTO msjObj = new MessageDTO();
        DataMessageDTO data = new DataMessageDTO();
        data.setOpponent("MAF");
        data.setChallenge_id("1234");
        msjObj.setData(data);
        msjObj.setEvent(Event.CHALLENGE.getString());

        Util instance = new Util();

        String expResult = "{\n"
                + "  \"action\": \"accept_challenge\",\n"
                + "  \"data\": {\n"
                + "    \"challenge_id\": \"1234\"\n"
                + "  }\n"
                + "}";

        String result = instance.acceptChallenge(msjObj);
        assertEquals(expResult, result);
    }

    @Test
    public void testMyTurnAnswer() {
        System.out.println("myTurnAnswer");

        MessageDTO msjObj = EventYourTurnOBJForTesting();

        Util instance = new Util();

        String expResult = ActionMoveJSONForTesting();

        String result = instance.myTurnAnswer(msjObj);
        assertEquals(expResult, result);
    }

    public String EventYourTurnJSONForTesting() {
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
        return json;
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

    public String ActionMoveJSONForTesting() {
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
        return json;
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
