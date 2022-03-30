package com.mrx.mmcsbot;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import static com.mrx.mmcsbot.keyboard.Keyboard.getKeyboard;

public class VKManager {
    private static VKCore vkCore;

    static {
        try {
            vkCore = new VKCore();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg, int peerId){
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
                    .unsafeParam("keyboard", getKeyboard())
                    .randomId(0)
                    .executeAsRaw();

        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
/*
    public MessagesSendQuery getSendQuery(){
        return vkCore.getVk().messages().send(vkCore.getActor());
    }
*/

    /*
     * Обращается к VK API и получает объект, описывающий пользователя.
     * @param id идентификатор пользователя в VK
     * @return {@link UserXtrCounters} информацию о пользователе
     * @see UserXtrCounters
     */


    /*
    public static UserXtrCounters getUserInfo(int id){
        try {
            return vkCore.getVk().users()
                    .get(vkCore.getActor())
                    .userIds(String.valueOf(id))
                    .execute()
                    .get(0);
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
    */
}
