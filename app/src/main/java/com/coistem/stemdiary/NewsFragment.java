package com.coistem.stemdiary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NewsFragment extends Fragment {

    private ListView listView;
    private static ArrayList<Map<String, Object>> news = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        listView = view.findViewById(R.id.newsList);
        vkRequest();
        return view;
    }

    public void vkRequest() {
        VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID,-113376999,"domain","coistem",VKApiConst.COUNT,10));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    JSONArray items = response.json.getJSONObject("response").getJSONArray("items");
                    HashMap<String, Object> map = new HashMap<>();
                    for(int i = 1; i<10; i++) {
                        JSONObject jsonObject = items.getJSONObject(i);
                        JSONArray attachments = jsonObject.getJSONArray("attachments");
                        JSONObject jsonObject1 = attachments.getJSONObject(0);
                        if(jsonObject1.getString("type").equals("photo")) {
                            JSONObject photos = jsonObject1.getJSONObject("photo");
                            String url = photos.getString("photo_807");
                            System.out.println(url);
                            map.put("Image",url);

                        }
                        String text = jsonObject.getString("text");
                        System.out.println(text);
                        map.put("Text",text);
                        news.add(map);
                        map = new HashMap<>();
                    }
                    ImageListAdapter simpleAdapter = new ImageListAdapter(getContext(), news, R.layout.listview_vknews, new String[]{"Text"}, new int[]{R.id.newsText});
                    listView.setAdapter(simpleAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

}
