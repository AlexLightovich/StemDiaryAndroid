package com.coistem.stemdiary;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ShopFragment extends Fragment {

    public static ArrayList<Map<String, Object>> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        ListView shopList = view.findViewById(R.id.shopList);

        HashMap<String, Object> map = new HashMap<>();
        map.put("Image",R.drawable.ic_college_graduation);
        map.put("Text","Бесплатное занятие");
        list.add(map);
        map = new HashMap<>();
        map.put("Image",R.drawable.ic_tshirt);
        map.put("Text","Футболка");
        list.add(map);
        map = new HashMap<>();
        map.put("Image",R.drawable.ic_cap);
        map.put("Text","Кепка");
        list.add(map);
        map = new HashMap<>();
        map.put("Image",R.drawable.ic_sticker);
        map.put("Text","Наклейка");
        list.add(map);
        map = new HashMap<>();
        map.put("Image",R.drawable.ic_bag);
        map.put("Text","Сумка");
        list.add(map);




        SimpleAdapter simpleAdapter = new SimpleAdapter(view.getContext(), list, R.layout.listview_shop, new String[]{"Image", "Text"}, new int[]{R.id.icon, R.id.text1});
        shopList.setAdapter(simpleAdapter);
        return view;
    }

}
