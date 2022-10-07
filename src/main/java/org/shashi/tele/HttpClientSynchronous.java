package org.shashi.tele;

import com.google.common.net.MediaType;
import okhttp3.*;
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
    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .build();

    public static String getInstaVideoLink(String videoLink) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.instavideosave.com/allinone"))
                .setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Mobile Safari/537.36") // add request header
                .setHeader("url", videoLink)
                .setHeader("referer","https://instavideosave.net")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print response headers
        HttpHeaders headers = response.headers();
        //headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

        // print status code
        //System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

        JSONObject jsonObject = new JSONObject(response.body());

        //System.out.println(jsonObject.toString(4));

        JSONArray videoArray = (JSONArray) jsonObject.get("video");

        JSONObject videoObject = (JSONObject) videoArray.get(0);

        String instaVideoLink = videoObject.get("video").toString();

        System.out.println(videoLink);


        return instaVideoLink;

    }

    public static String getPintrestVideoLink(String videoLink) throws IOException {

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("url", videoLink)
                .build();
        Request request = new Request.Builder()
                .url("https://givefastlink.com/wp-json/aio-dl/video-data/")
                .method("POST", body)
                //.addHeader("Cookie", "PHPSESSID=9a20a4458987b49358eea573d6b8dcda")
                .build();
        Response response = client.newCall(request).execute();

        JSONObject responseJson = new JSONObject(response.body().string());
        JSONObject media = (JSONObject) responseJson.getJSONArray("medias").get(0);
        String pintrestVideoLink = (String) media.get("url");

        return pintrestVideoLink;
    }

    public static String getYTVideoLink(String videoLink) throws IOException {

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("url", videoLink)
                .build();
        Request request = new Request.Builder()
                .url("https://givefastlink.com/wp-json/aio-dl/video-data/")
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();

        JSONObject responseJson = new JSONObject(response.body().string());
        JSONObject media = (JSONObject) responseJson.getJSONArray("medias").get(0);
        String youtubeShortVideoLink = (String) media.get("url");

        return youtubeShortVideoLink;
    }



}
