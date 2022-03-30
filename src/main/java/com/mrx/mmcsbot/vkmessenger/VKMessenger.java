package com.mrx.mmcsbot.vkmessenger;

import com.mrx.mmcsbot.VKCore;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;


import static com.mrx.mmcsbot.keyboard.KeyboardHolder.getKeyboard;


public class VKMessenger {

    private static final Logger logger = LoggerFactory.getLogger(VKMessenger.class);
    private static VKCore vkCore;

    static {

        try {
            vkCore = new VKCore();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg, int peerId, String keyboard){
        if (msg == null){
            System.out.println("null");
            return;
        }
        try {

            logger.info("[" + peerId + "] Отправка <<" + msg.replaceAll("\n", "/n") + ">>");

            vkCore.getVkApiClient().messages()
                    .send(vkCore.getGroupActor())
                    .peerId(peerId)
                    .message(msg)
                    .unsafeParam("keyboard", keyboard)
                    .randomId(0)
                    .executeAsRaw();

        } catch (ClientException e) {
            e.printStackTrace();
        }
    }


}
