package org.shashi.tele;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;

public class Bot extends TelegramLongPollingBot {

    /**
     * Method for receiving messages.
     * @param update Contains a message from the user.
     */
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();


        System.out.println(message);


        if(isURL(message)){

            //GetReel getReel = new GetReel();
            String videoLink = null;
            try {
                videoLink =   HttpClientSynchronous.getVideoLink(message);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
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

    public boolean isURL(String url) {
        try {
            (new java.net.URL(url)).openStream().close();
            return true;
        } catch (Exception ex) { }
        return false;
    }

}