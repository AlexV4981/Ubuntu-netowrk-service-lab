package bot.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MinecraftQueryService {

    private final HttpClient client;
    private final String controllerUrl;


    public MinecraftQueryService() {
        this.client = HttpClient.newHttpClient();
        this.controllerUrl = "http://minecraft-controller:5000";
    }


    public String getStatus() {

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(controllerUrl + "/status"))
                    .GET()
                    .build();


            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );


            return response.body();


        } catch (Exception e) {

            return "Server is Unknown (unreachable)";
        }
    }
}
