import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class WebsocketClient implements WebSocket.Listener {
    private WebSocket server = null;
    private Bridge bridge;
    private PrintWriter logger;

    // Send down-link message to device
    // Must be in Json format according to https://github.com/ihavn/IoT_Semester_project/blob/master/LORA_NETWORK_SERVER.md
    public void sendDownLink(String jsonTelegram) {
        server.sendText(jsonTelegram, true);
    }

    public WebsocketClient(String url, Bridge bridge) {
        File file = new File("logs.txt");
        try {
            logger = new PrintWriter(file);
            new Thread(() -> {
                while(true) {
                    try {
                        Thread.sleep(5*60*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.flush();
                }
            }).start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.bridge = bridge;
        HttpClient client = HttpClient.newHttpClient();
        CompletableFuture<WebSocket> ws = client.newWebSocketBuilder()
                .buildAsync(URI.create(url), this);

        server = ws.join();
    }

    public void onOpen(WebSocket webSocket) {
        // This WebSocket will invoke onText, onBinary, onPing, onPong or onClose methods on the associated listener (i.e. receive methods) up to n more times
        webSocket.request(1);
        logger.println("WebSocket Listener has been opened for requests.");
    }

    public void onError​(WebSocket webSocket, Throwable error) {
        logger.println("A " + error.getCause() + " exception was thrown.");
        logger.println("Message: " + error.getLocalizedMessage());
        webSocket.abort();
        logger.close();
    }

    ;

    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        logger.println("WebSocket closed!");
        logger.println("Status:" + statusCode + " Reason: " + reason);
        return new CompletableFuture().completedFuture("onClose() completed.").thenAccept(logger::println);
    }

    ;

    public CompletionStage<?> onPing​(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        logger.println("Ping: Client ---> Server");
        logger.println(message.asCharBuffer().toString());
        return new CompletableFuture().completedFuture("Ping completed.").thenAccept(logger::println);
    }

    ;

    public CompletionStage<?> onPong​(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        logger.println("Pong: Client ---> Server");
        logger.println(message.asCharBuffer().toString());
        return new CompletableFuture().completedFuture("Pong completed.").thenAccept(logger::println);
    }

    ;

    //i think this is called when it receives data
    public CompletionStage<?> onText​(WebSocket webSocket, CharSequence data, boolean last) {
        webSocket.request(1);

        String dataString = data.toString();
        var parser = new JSONParser();
        JSONObject json;
        String hexData = "";
        String cmd = "";
        String deviceId = "";
        try {
            json = (JSONObject) parser.parse(dataString);
            hexData = (String) json.get("data");
            cmd = (String) json.get("cmd");
            deviceId = (String) json.get("EUI");
        } catch (net.minidev.json.parser.ParseException e) {
            e.printStackTrace();
        }

        if (cmd.equalsIgnoreCase("rx")) {
            bridge.addData(Long.parseLong(deviceId, 16), hexData);
            return new CompletableFuture().completedFuture("onText() completed.").thenAccept(logger::println);
        }

        return new CompletableFuture().completedFuture("onText() not complete due to wrong cmd identifier.").thenAccept(logger::println);
    }

    ;
}