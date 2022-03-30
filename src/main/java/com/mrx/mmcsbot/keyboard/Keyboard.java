package com.mrx.mmcsbot.keyboard;

public class Keyboard {

    public static String getKeyboard(){
        return  "{\n" +
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


}
