package com.maf2.connection;

import java.net.URI;
import com.maf2.DTO.MessageDTO;
import com.maf2.enums.Event;
import com.maf2.utils.Util;
import java.net.URISyntaxException;

public final class Connection {

    private Util util;
    private WebSocketClient clientEndPoint;
    private String url;
    // USE PERSONAL TOKEN DURING TOURNAMENTS
    private final String token = "INSERT_USER_TOKEN_HERE";
    private final String root = "wss://4yyity02md.execute-api.us-east-1.amazonaws.com/ws?token=";

    public Connection() {
        setUrl();
        start();
    }

    private void setUrl() {
        url = root.concat(token);
    }

    public void start() {
        try {
            clientEndPoint = new WebSocketClient(new URI(url));
            clientEndPoint.addMessageHandler(new WebSocketClient.MessageHandler() {

                @Override
                public void handleMessage(String msj) {
                    util = new Util();

                    MessageDTO msjObj = util.JSONToObject(msj);
                    String answer;

                    // Print relevant messages in console
                    if (msjObj != null
                            /*&& !msjObj.getEvent().equals(Event.LIST_USERS.getString())*/           // Comment to see in console "LIST_USERS"
                            /*&& !msjObj.getEvent().equals(Event.CHALLENGE.getString())*/           // Comment to see in console "CHALLENGE"
                            /*&& !msjObj.getEvent().equals(Event.YOUR_TURN.getString())*/           // Comment to see in console "YOUT_TURN"
                            ) {
                        System.out.println(msjObj);
                    }

                    // DISABLE FUNCTION DURING TOURNAMENTS
                    // Get Challenge --> Accept
                    if (msjObj.getEvent().equals(Event.CHALLENGE.getString())) {
                        answer = util.acceptChallenge(msjObj);
                        System.out.println(util.JSONToObject(answer).toString());
                        clientEndPoint.sendMessage(answer);
                    }

                    // Get Your_Turn --> Answer Next Move
                    if (msj.contains("event") && msj.contains("your_turn")) {
                        answer = util.myTurnAnswer(msjObj);
                        System.out.println(util.JSONToObject(answer).toString());
                        clientEndPoint.sendMessage(answer);
                    }

                }

            });
            Thread.sleep(1000);
        } catch (InterruptedException | URISyntaxException ex) {
            System.err.println(ex);
        }
    }

}
