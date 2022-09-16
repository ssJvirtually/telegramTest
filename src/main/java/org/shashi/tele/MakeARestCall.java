package org.shashi.tele;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class MakeARestCall {

    public static void main(String[] args) {
        String url = "https://www.journaldev.com/sitemap.xml";

        try {
            downloadUsingNIO(url, "/Users/pankaj/sitemap.xml");

            downloadUsingStream(url, "/Users/pankaj/sitemap_stream.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    private static void makeHttpCall(){
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create(mediaType,"");
//        Request request = new Request.Builder()
//                .url("https://api.instavideosave.com/allinone")
//                .method("GET",body)
//                .addHeader("url", "https://www.instagram.com/reel/CiIPoqYLbex/")
//                .build();
//        Response response = client.newCall(request).execute();
//    }

    public static File getVideoFile(String videoLink){
        try (BufferedInputStream in = new BufferedInputStream(new URL(videoLink).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("reel.mp4")) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            return new File("reel.mp4");
        } catch (IOException e) {
            // handle exception
        }
        return null;
    }


    public static void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);

        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();


    }

    public static InputStream downloadUsingInputStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);

        InputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();

        return bis;
    }

    public static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

}
