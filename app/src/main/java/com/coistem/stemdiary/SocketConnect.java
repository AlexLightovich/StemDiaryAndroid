package com.coistem.stemdiary;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketConnect extends AsyncTask {
    private OnTaskComplete listener;

    public interface OnTaskComplete {
        void onTaskComplete();
    }
    public static String result;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dos;
    Socket socket;

    public String authorizate(String login, String password){
        try {
            try {
                socket = new Socket("192.168.1.100", 45654);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                dataInputStream = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("login",login);
                jsonObject.put("password",password);
                String s1 = jsonObject.toString();
                dos.writeUTF(s1);
                dos.flush();
                String s2 = dataInputStream.readUTF();
                Log.d("Server answer",s2);
                Log.d("Server input", s1);
                return s2;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                dataInputStream.close();
                dos.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            return "Connection error";
        }
        return "error";

    }

    public String takeShopItems(String token) {
        try {
            try {
                socket = new Socket("192.168.1.100", 45654);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                dataInputStream = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", token);
                jsonObject.put("command","getShop");
                String s1 = jsonObject.toString();
                dos.writeUTF(s1);
                dos.flush();
                String s2 = dataInputStream.readUTF();
                Log.d("Server answer",s2);
                Log.d("Server input", s1);
                return s2;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                dataInputStream.close();
                dos.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            return "Connection error";
        }
        return "error";
    }

    @Override
    protected void onPreExecute() {
//        LoginActivity.loadingDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        switch ((String) objects[0]) {
            case "auth" : {
                String authorizate = authorizate((String) objects[1], (String) objects[2]);
                result = authorizate;
                return authorizate;
            }
            case "shop" : {
                 String getShopItems = takeShopItems((String) objects[1]);
                 return getShopItems;
            }
        }
        return "unknown command";
    }

    @Override
    protected void onPostExecute(Object o) {
        LoginActivity.loadingDialog.cancel();
//        listener.onTaskComplete();
    }
}
