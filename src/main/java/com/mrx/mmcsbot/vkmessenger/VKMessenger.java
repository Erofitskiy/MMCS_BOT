package com.mrx.mmcsbot.vkmessenger;

import com.mrx.mmcsbot.VKCore;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import static com.mrx.mmcsbot.keyboard.KeyboardHolder.getKeyboard;

public class VKMessenger {

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

            System.out.println("Отправка = " + msg);
            System.out.println("Пользователю = " + peerId);

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
