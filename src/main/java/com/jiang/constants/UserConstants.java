package com.jiang.constants;

/**
 * es创建用户的索引库的json文件
 * @author SmlingSea
 */
public class UserConstants {
    public static final String USER_MAPPING_TEMPLATE =
            "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"id\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"name\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"email\":{\n" +
            "        \"type\": \"text\"\n" +
            "      },\n" +
            "      \"phone\":{\n" +
            "        \"type\": \"text\"\n" +
            "      },\n" +
            "      \"profile\":{\n" +
            "        \"type\": \"text\"\n" +
            "      },\n" +
            "      \"blocked\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"identity\":{\n" +
            "        \"type\": \"text\"\n" +
            "      },\n" +
            "      \"avatar\":{\n" +
            "        \"type\": \"text\"\n" +
            "      },\n" +
            "      \"all\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
}
