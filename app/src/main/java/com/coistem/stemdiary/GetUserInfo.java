package com.coistem.stemdiary;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetUserInfo {

    public static String userName;
    public static String userSurname;
    public static String userThirdName;
    public static String userCoins;
    public static int userCounterCoins;
    public static String avatarUrl;
    public static String userAccessType;

    public static String moderationUserName;
    public static String moderationUserSurname;
    public static String moderationUserThirdName;
    public static String moderationUserCoins;
    public static int moderationUserCounterCoins;
    public static String moderationUserAccessType;

    private String jsonFile;

    public void parseJson(String userLogin) {
        try {
            JSONObject jsonObject = new JSONObject(jsonFile);
            JSONArray jsonArray = jsonObject.getJSONArray(userLogin);
            JSONObject userInfo = jsonArray.getJSONObject(0);
            userName = userInfo.getString("name");
            userCoins = userInfo.getString("coins");
            userSurname = userInfo.getString("surname");
            userThirdName = userInfo.getString("thirdname");
            userAccessType = userInfo.getString("accessType");
            System.out.println(userAccessType);
            userCounterCoins = Integer.parseInt(userCoins);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonFile);
    }

    public void moderateParseJson(String login) {
        try {
            JSONObject jsonObject = new JSONObject(jsonFile);
            JSONArray jsonArray = jsonObject.getJSONArray(login);
            JSONObject userInfo = jsonArray.getJSONObject(0);
            moderationUserName = userInfo.getString("name");
            moderationUserCoins = userInfo.getString("coins");
            moderationUserSurname = userInfo.getString("surname");
            JSONObject jo = new JSONObject();
//            userThirdName = userInfo.getString("thirdname");
            moderationUserCounterCoins = Integer.parseInt(userCoins);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonFile);
    }

    public void prepareJsonFile(Context context, String login) {
        JSONFileEditing jsonFileEditing = new JSONFileEditing();
        jsonFile = jsonFileEditing.getJsonData(context);
        System.out.println("JSOOOOOOOOOOOOOOOOOON"+jsonFile);
        parseJson(login);
    }





}
