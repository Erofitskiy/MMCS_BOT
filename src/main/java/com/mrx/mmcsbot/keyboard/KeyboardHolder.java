package com.mrx.mmcsbot.keyboard;

import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.KeyboardButton;
import com.vk.api.sdk.objects.messages.KeyboardButtonAction;
import com.vk.api.sdk.objects.messages.TemplateActionTypeNames;

import java.util.ArrayList;
import java.util.List;

public class KeyboardHolder {


    public static String getWorkKeyboard(){
        return "{\n" +
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
    }



    public static String getWelcomeKeyboard(){
        return "1";
    }




    public static String getKeyboard(){


        List<KeyboardButton> level1 = new ArrayList<>();
        level1.add(new KeyboardButton()
                .setAction(new KeyboardButtonAction()
                        .setPayload("1")
                        .setLabel("Начать")
                        .setType(TemplateActionTypeNames.TEXT)));

        List<List<KeyboardButton>> list = new ArrayList<>();
        list.add(level1);

        Keyboard test = new Keyboard()
                .setButtons(list)
                .setOneTime(true);

        String s =  "{\n" +
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

        System.out.println("=======");
        System.out.println(test);

        return test.toString();
    }


}
