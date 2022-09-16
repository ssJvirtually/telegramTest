package org.shashi.tele;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientSynchronous {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static String getVideoLink(String videoLink) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.instavideosave.com/allinone"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .setHeader("url", videoLink)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print response headers
        HttpHeaders headers = response.headers();
        //headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

        // print status code
        //System.out.println(response.statusCode());

        // print response body
        //System.out.println(response.body());

        JSONObject jsonObject = new JSONObject(response.body());

        //System.out.println(jsonObject.toString(4));

        JSONArray videoArray = (JSONArray) jsonObject.get("video");

        JSONObject videoObject = (JSONObject) videoArray.get(0);

        String instaVideoLink = videoObject.get("video").toString();

        System.out.println(videoLink);


       return instaVideoLink;

    }

}
