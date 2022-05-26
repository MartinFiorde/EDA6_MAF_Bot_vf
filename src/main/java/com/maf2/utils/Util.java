package com.maf2.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maf2.DTO.DataMessageDTO;
import com.maf2.DTO.MessageDTO;
import com.maf2.enums.Action;
import com.maf2.ia.Finder;

public class Util {

    Finder finder = new Finder();

    // Takes an Object to convert it to a JSON-formatted String
    public String objectToJSON(MessageDTO obj) {
        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        return GSON.toJson(obj, MessageDTO.class);
    }

    // Takes a JSON-formatted String to convert it to an Object
    public MessageDTO JSONToObject(String json) {
        Gson GSON = new GsonBuilder().serializeNulls().create();
        return GSON.fromJson(json, MessageDTO.class);
    }

    // Build a JSON to accept an incoming request
    public String acceptChallenge(MessageDTO msjObj) {
        DataMessageDTO dataMsj = new DataMessageDTO();
        dataMsj.setChallenge_id(msjObj.getData().getChallenge_id());

        MessageDTO msj = new MessageDTO();
        msj.setAction(Action.ACCEPT_CHALLENGE.getString());
        msj.setData(dataMsj);

        return objectToJSON(msj);
    }

    // Make a JSON to answer my turn
    public String myTurnAnswer(MessageDTO msjObj) {
        // Calls the "Finder" class to start parsing the board and find the next move
        DataMessageDTO dataMsj = finder.nextMove(msjObj);
        dataMsj.setGame_id(msjObj.getData().getGame_id());
        dataMsj.setTurn_token(msjObj.getData().getTurn_token());

        MessageDTO answerObj = new MessageDTO();
        answerObj.setData(dataMsj);
        if (dataMsj.getOrientation() == null) {
            answerObj.setAction(Action.MOVE.getString());
        } else {
            answerObj.setAction(Action.WALL.getString());
        }

        return objectToJSON(answerObj);
    }

}
