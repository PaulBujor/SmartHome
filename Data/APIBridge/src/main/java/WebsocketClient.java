import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class WebsocketClient implements WebSocket.Listener {
    private WebSocket server = null;
    private Bridge bridge;

    // Send down-link message to device
    // Must be in Json format according to https://github.com/ihavn/IoT_Semester_project/blob/master/LORA_NETWORK_SERVER.md
    public void sendDownLink(String jsonTelegram) {
        server.sendText(jsonTelegram, true);
    }

    public WebsocketClient(String url, Bridge bridge) {
        this.bridge = bridge;
        HttpClient client = HttpClient.newHttpClient();
        CompletableFuture<WebSocket> ws = client.newWebSocketBuilder()
                .buildAsync(URI.create(url), this);

        server = ws.join();
    }

    public void onOpen(WebSocket webSocket) {
        // This WebSocket will invoke onText, onBinary, onPing, onPong or onClose methods on the associated listener (i.e. receive methods) up to n more times
        webSocket.request(1);
        System.out.println("WebSocket Listener has been opened for requests.");
    }

    public void onError​(WebSocket webSocket, Throwable error) {
        System.out.println("A " + error.getCause() + " exception was thrown.");
        System.out.println("Message: " + error.getLocalizedMessage());
        webSocket.abort();
    }

    ;

    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("WebSocket closed!");
        System.out.println("Status:" + statusCode + " Reason: " + reason);
        return new CompletableFuture().completedFuture("onClose() completed.").thenAccept(System.out::println);
    }

    ;

    public CompletionStage<?> onPing​(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("Ping: Client ---> Server");
        System.out.println(message.asCharBuffer().toString());
        return new CompletableFuture().completedFuture("Ping completed.").thenAccept(System.out::println);
    }

    ;

    public CompletionStage<?> onPong​(WebSocket webSocket, ByteBuffer message) {
        webSocket.request(1);
        System.out.println("Pong: Client ---> Server");
        System.out.println(message.asCharBuffer().toString());
        return new CompletableFuture().completedFuture("Pong completed.").thenAccept(System.out::println);
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
            return new CompletableFuture().completedFuture("onText() completed.").thenAccept(System.out::println);
        }

        return new CompletableFuture().completedFuture("onText() not complete due to wrong cmd identifier.").thenAccept(System.out::println);
    }

    ;
}