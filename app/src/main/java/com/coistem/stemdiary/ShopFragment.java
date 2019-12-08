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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class ShopFragment extends Fragment {

    public static ArrayList<Map<String, Object>> list = new ArrayList<>();
    private ListView shopList;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shop, container, false);
        shopList = view.findViewById(R.id.shopList);
        TextView balanceTxt = view.findViewById(R.id.balanceText);
        balanceTxt.setText("Ваш баланс: "+GetUserInfo.userCounterCoins+" коинов");
        Toast.makeText(getContext(), takeItems(GetUserInfo.userToken), Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onResume() {
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
        super.onResume();
    }

    public String takeItems(String token) {
        SocketConnect socketConnect = new SocketConnect();
        socketConnect.execute("shop",token);
        String o = null;
        try {
            o = (String) socketConnect.get(2, TimeUnit.SECONDS);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public void onPause() {
        list.clear();

        super.onPause();
    }
}
