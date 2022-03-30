package com.mrx.mmcsbot;

import com.mrx.mmcsbot.vkmessenger.MessageHandler;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    final static int RECONNECT_TIME = 10000;
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws InterruptedException, ClientException, ApiException {
        logger.info("Running server...");

        MessageHandler messageHandler = new MessageHandler();
        VKCore vkCore = new VKCore();

        while (true) {
            Thread.sleep(300); // запрос каждые 0.3 сек
            try {
                Message message = vkCore.getMessage();
                if (message != null) {
                    logger.info("[" + message.getFromId() + "] От пользователя <<" + message.getText() + ">>");
                    messageHandler.handle(message);
                }
            } catch (ClientException e) {
                System.out.println("Возникли проблемы");
                System.out.println("Повторное соединение через " + RECONNECT_TIME / 1000 + " секунд");
                System.out.println(e.getMessage());
                e.printStackTrace();
                Thread.sleep(RECONNECT_TIME);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }


}
