package com.coistem.stemdiary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class NewsFragment extends Fragment {

    private static ArrayList<Map<String, Object>> news = new ArrayList<>();
    private View view;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.listRecyclerView);
//        listView = view.findViewById(R.id.newsList);
        setToolbar(GetUserInfo.userName);
        vkRequest();

        return view;
    }

    private ArrayList<String> newsText = new ArrayList<>();
    private ArrayList<String> imageURLs = new ArrayList<>();
    private ArrayList<String> newsDates = new ArrayList<>();

    public void vkRequest() {
        VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID,-113376999,"domain","coistem",VKApiConst.COUNT,6));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    JSONArray items = response.json.getJSONObject("response").getJSONArray("items");
                    HashMap<String, Object> map = new HashMap<>();
                    for(int i = 1; i<6; i++) {
                        JSONObject jsonObject = items.getJSONObject(i);
                        JSONArray attachments = jsonObject.getJSONArray("attachments");
                        JSONObject jsonObject1 = attachments.getJSONObject(0);
                        String date = jsonObject.getString("date");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
                        String dateOfPost = sdf.format(Long.parseLong(date)*1000);
                        newsDates.add(dateOfPost);
                        if(jsonObject1.getString("type").equals("photo")) {
                            JSONObject photos = jsonObject1.getJSONObject("photo");
                            String url = photos.getString("photo_807");
                            System.out.println(url);
                            imageURLs.add(url);

                        } else {
                            imageURLs.add("nothing");
                        }
                        String text = jsonObject.getString("text");
                        System.out.println(text);
                        newsText.add(text);
                    }

                    OurData.title = new String[newsText.size()];
                    OurData.title = newsText.toArray(OurData.title);
                    OurData.imgUrls = new String[imageURLs.size()];
                    OurData.imgUrls = imageURLs.toArray(OurData.imgUrls);
                    OurData.dates = new String[newsDates.size()];
                    OurData.dates = newsDates.toArray(OurData.dates);

                    ListAdapter listAdapter = new ListAdapter();
                    recyclerView.setAdapter(listAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    for(int i  = 0; i<OurData.title.length; i++) {
                        System.out.println("START");
                        System.out.println(OurData.title[i]);
                        System.out.println("END");
                    }
                    for(int i  = 0; i<OurData.imgUrls.length; i++) {
                        System.out.println("START");
                        System.out.println(OurData.imgUrls[i]);
                        System.out.println("END");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    public void setToolbar(@NonNull String title) {
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        CollapsingToolbarLayout collapsingToolbar = view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbar.setTitle(title);
    }

}
