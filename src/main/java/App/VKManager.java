package App;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;

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
            String keyboard = "{\n" +
                    "  \"one_time\": false,\n" +
                    "  \"buttons\": [\n" +
                    "    [\n" +
                    "      {\n" +
                    "        \"action\": {\n" +
                    "          \"type\": \"text\",\n" +
                    "          \"payload\": \"{\\\"button\\\": \\\"1\\\"}\",\n" +
                    "          \"label\": \"Начать\"\n" +
                    "        },\n" +
                    "        \"color\": \"secondary\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"action\": {\n" +
                    "          \"type\": \"text\",\n" +
                    "          \"payload\": \"{\\\"button\\\": \\\"2\\\"}\",\n" +
                    "          \"label\": \"Расписание\"\n" +
                    "        },\n" +
                    "        \"color\": \"positive\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"action\": {\n" +
                    "          \"type\": \"text\",\n" +
                    "          \"payload\": \"{\\\"button\\\": \\\"3\\\"}\",\n" +
                    "          \"label\": \"ЧужоеРасписание\"\n" +
                    "        },\n" +
                    "        \"color\": \"primary\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"action\": {\n" +
                    "          \"type\": \"text\",\n" +
                    "          \"payload\": \"{\\\"button\\\": \\\"3\\\"}\",\n" +
                    "          \"label\": \"Возможности\"\n" +
                    "        },\n" +
                    "        \"color\": \"positive\"\n" +
                    "      }\n" +
                    "\t  \n" +
                    "    ]\n" +
                    "  ]\n" +
                    "} ";

            //187346214


            System.out.println("Отправка = " + msg);
            System.out.println("Пользователю = " + peerId);

            vkCore.getVk().messages()
                    .send(vkCore.getActor())
                    .peerId(peerId)
                    .message(msg)
                    .unsafeParam("keyboard", keyboard).randomId(0)
                    .execute();

        } catch (ApiException | ClientException e) {
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
