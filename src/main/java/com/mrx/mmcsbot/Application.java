package com.mrx.mmcsbot;

import com.mrx.mmcsbot.bots.ScheduleBot;
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
        ScheduleBot scheduleBot = new ScheduleBot();


        Thread t1 = new Thread(scheduleBot, "Бот-расписание");

        t1.start();
        t1.join();


    }


}
