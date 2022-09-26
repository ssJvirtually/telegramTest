//package org.shashi.tele;
//
//
//import com.google.common.net.MediaType;
//import okhttp3.*;
//import org.json.JSONObject;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.net.URI;
//import java.net.URLEncoder;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.nio.charset.StandardCharsets;
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.Map;
//
//public class TestClass {
//
////    private static final HttpClient httpClient = HttpClient.newBuilder()
////            .version(HttpClient.Version.HTTP_1_1)
////            .connectTimeout(Duration.ofSeconds(10))
////            .build();
//    public static void main(String[] args) throws IOException, InterruptedException {
//
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("url","https://pin.it/136j3ji")
//                .build();
//        Request request = new Request.Builder()
//                .url("https://givefastlink.com/wp-json/aio-dl/video-data/")
//                .method("POST", body)
//                //.addHeader("Cookie", "PHPSESSID=9a20a4458987b49358eea573d6b8dcda")
//                .build();
//        Response response = client.newCall(request).execute();
//
//        JSONObject responseJson = new JSONObject(response.body().string());
//        JSONObject media = (JSONObject) responseJson.getJSONArray("medias").get(0);
//        String videoLink = (String) media.get("url");
//        System.out.println(videoLink);
//        //System.out.println(responseJson.toString(4));
//    }
//
//    // Sample: 'password=123&custom=secret&username=abc&ts=1570704369823'
//    public static HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data) {
//        var builder = new StringBuilder();
//        for (Map.Entry<Object, Object> entry : data.entrySet()) {
//            if (builder.length() > 0) {
//                builder.append("&");
//            }
//            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
//            builder.append("=");
//            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
//        }
//        return HttpRequest.BodyPublishers.ofString(builder.toString());
//    }
//}
