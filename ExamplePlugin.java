import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.CompletableFuture;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.stream.Collectors;
import java.util.Map;

@Subscribe
public void onChatMessage(ChatMessage chatMessage) {
    MessageNode messageNode = chatMessage.getMessageNode();

    final String message = messageNode.getValue();
    final String from = chatMessage.getName();
    final String fc = chatMessage.getSender();

    if (config.booleansendlocalhost()) {
        try {

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("from", cleanMessage(from));
            params.put("message", cleanMessage(message));
            if (fc != null) {
                params.put("fc", cleanMessage(fc));
            }

            System.out.println("sending message to localhost");
            sendPostRequest("http://localhost:5001/message", params);
        } catch (Exception e) {

        }
    }
}

private String cleanMessage(String message) {
    // clean all the spaces
    // clean <>
    String regex = "</?\\w+[^>]*>";
    // Create a Pattern object
    Pattern pattern = Pattern.compile(regex);

    // Create a Matcher object
    Matcher matcher = pattern.matcher(message);

    // Replace all occurrences of the pattern with an asterisk
    String result = matcher.replaceAll("");

    String newMessage = "";
    String allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 !@#$%^&*()-+=\\/.,;'\"[]";
    for (int i = 0; i<result.length(); i++) {
        String character = Character.toString(result.charAt(i));
        if (!allowedCharacters.contains(character)) {
            character = " ";
        }
        newMessage = newMessage + character;
    }
    result = newMessage;

    return result;
}

private void sendPostRequest(String serviceUrl, HashMap<String, String> params) {
    String mapString = convertWithStream(params);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serviceUrl))
            .POST(HttpRequest.BodyPublishers.ofString(mapString))
            .build();

    CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
}

// TO CONVERT MAP TO STRING
public String convertWithStream(Map<?, ?> map) {
    String mapAsString = map.keySet().stream()
            .map(key -> "\"" + key + "\": \"" + map.get(key) + "\"")
            .collect(Collectors.joining(", ", "{", "}"));
    return mapAsString;
}