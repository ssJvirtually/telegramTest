package org.shashi.tele;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Bot extends TelegramLongPollingBot {

    /**
     * Method for receiving messages.
     * @param update Contains a message from the user.
     */
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        //update.getMessage().

        System.out.println(message);


        if(isURL(message) || message.contains("https")){


            message = removeCharBeforeUrl(message);
            System.out.println(message);
            //GetReel getReel = new GetReel();
            String videoLink = null;
            try {
                if(getDomainName(message).contains("pin")){

                    videoLink = HttpClientSynchronous.getPintrestVideoLink(message);
                }
                if(getDomainName(message).contains("instagram")) {
                    videoLink = HttpClientSynchronous.getInstaVideoLink(message);
                }
                if(getDomainName(message).contains("youtube")){
                    videoLink = HttpClientSynchronous.getYTVideoLink(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }


            if(!videoLink.isEmpty() && videoLink != null) {
                InputFile file = new InputFile();
                File inputFile = MakeARestCall.getVideoFile(videoLink);

                //File inputFile = new File("reel.mp4");
                file.setMedia(inputFile);

                SendVideo sendVideo = new SendVideo();

                sendVideo.setChatId(update.getMessage().getChatId());
                sendVideo.setVideo(file);
                sendVideo.setReplyToMessageId(update.getMessage().getMessageId());

                try {
                    execute(sendVideo);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            else {
                SendMessage sendMessage = new SendMessage();
                sendMessage.enableMarkdown(true);
                sendMessage.setChatId(update.getMessage().getChatId());
                sendMessage.setText("No clue at all what is this");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

        }else{
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText("Not a Valid URL , cant get anything out of it");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }


       // SendDocument sendDocument = new SendDocument(update.getMessage().getChatId().toString(),file);

        //sendDocument.setDocument(file);
       // sendDocument.setChatId(update.getMessage().getChatId());




//
//        try {
//            sendApiMethod(sendMessage);
//
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
    }




//    /**
//     * Method for creating a message and sending it.
//     * @param chatId chat id
//     * @param s The String that you want to send as a message.
//     */
//    public synchronized void sendMsg(String chatId, String s) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
//        sendMessage.setChatId(chatId);
//        sendMessage.setText(s);
//        try {
//            sendMessage(sendMessage);
//        } catch (TelegramApiException e) {
//            log.log(Level.SEVERE, "Exception: ", e.toString());
//        }
//    }
//
//

    /**
     * This method returns the bot's name, which was specified during registration.
     * @return bot name
     */
    @Override
    public String getBotUsername() {
        return "photosalterBot";
    }

    /**
     * This method returns the bot's token for communicating with the Telegram server
     * @return the bot's token
     */
    @Override
    public String getBotToken() {
        return "5065568360:AAHGbdI88zjlpBJgSQWxd-MeLscVxNpAF3c";
    }


    /**
     * This method returns true if the given url is a valid url
     * @param url
     * @return
     */
    public boolean isURL(String url) {
        try {
            (new java.net.URL(url)).openStream().close();
            return true;
        } catch (Exception ex) { }
        return false;
    }


    /**
     * This method returns the domain of the link
     * @param url
     * @return
     * @throws URISyntaxException
     */
    public static String getDomainName(String url) throws URISyntaxException {
//        java.net.URL aURL;
//        try {
//            aURL = new java.net.URL(url);
//            System.out.println("host = " + aURL.getHost()); //example.com
//
//        } catch (MalformedURLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return "";

        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }


    public static String removeCharBeforeUrl(String link){
        return link.replaceAll("[\r\n]+", " ")
                .replaceAll(".*(?=https://)", "");
    }

}