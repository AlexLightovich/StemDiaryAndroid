package com.coistem.stemdiary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetUserInfo {

    public static String userName;
    public static String userSurname;
    public static String userThirdName;
    public static String userCoins;
    public static int userCounterCoins;
    public static String userAvatarResource; // url after adding internet

    private String jsonFile = "{\n" +
            "  \"eremin15\":[{\n" +
            "    \"name\":\"Vadim\",\n" +
            "    \"surname\": \"Eremin\",\n" +
            "    \"thirdname\":\"Kto-to tamovich\",\n" +
            "    \"coins\": \"12223\"}],\n" +
            "  \"yeliseyenko23\":[{\n" +
            "    \"name\":\"Yuriy\",\n" +
            "    \"surname\": \"Yeliseyenko\",\n" +
            "    \"thirdname\":\"Andreevich\",\n" +
            "    \"coins\": \"13242\"}],\n" +
            "  \"vasilev75\":[{\n" +
            "    \"name\":\"Alexey\",\n" +
            "    \"surname\": \"Vasilev\",\n" +
            "    \"thirdname\":\"Il'ich\",\n" +
            "    \"coins\": \"1337\"}],\n" +
            "  \"user\":[{\n" +
            "    \"name\":\"User\",\n" +
            "    \"surname\": \"Defoltnniy\",\n" +
            "    \"thirdname\":\"Alexeevich\",\n" +
            "    \"coins\": \"1337228322\"}]\n" +
            "}";

    public void parseJson(String userLogin) {
        try {
            JSONObject jsonObject = new JSONObject(jsonFile);
            JSONArray jsonArray = jsonObject.getJSONArray(userLogin);
            JSONObject userInfo = jsonArray.getJSONObject(0);
            userName = userInfo.getString("name");
            userCoins = userInfo.getString("coins");
            userSurname = userInfo.getString("surname");
            userThirdName = userInfo.getString("thirdname");
            userCounterCoins = Integer.parseInt(userCoins);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
