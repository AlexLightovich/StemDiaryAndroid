package com.coistem.stemdiary;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class ShopFragment extends Fragment {
    private RecyclerView shopList;
    private View view;
    public ImageView backgroundImg;
    float[] hsv;
    int runColor;

    private String jsonList = "{\t\"items\":[{\n" +
            "  \"name\":\"Ослы самые отличные животные.\",\n" +
            "  \"img\":\"http://zoogalaktika.ru/assets/img-cache/ass_01.dc0455fd11ca8728fc67ab6ad7351ae0.jpg\"\n" +
            "},{\n" +
            "  \"name\":\"Cамые лучшие на планете земля.\",\n" +
            "  \"img\":\"https://ilike.pet/upload/iblock/768/768029221d01576d2bd705921c7dad99.jpg\"\n" +
            "},{\n" +
            "  \"name\":\"У них очень крутые уши!\",\n" +
            "  \"img\":\"https://ic.pics.livejournal.com/lev_dmitrich/32679866/221346/221346_original.jpg\"\n" +
            "},{\n" +
            "  \"name\":\"И зубы \uD83D\uDE02\",\n" +
            "  \"img\":\"https://v-meste.ru/wp-content/uploads/2015/07/imageedit_106_3550651546.jpg\"\n" +
            "},{\n" +
            "  \"name\":\"Вот и все. Рассказ об ослах на этом закончен.\",\n" +
            "  \"img\":\"https://heaclub.ru/tim/7cde4b61535988d8928923fab902e6b3.jpg\"\n" +
            "},{\n" +
            "  \"name\":\"Удачи тебе :)\",\n" +
            "  \"img\":\"https://novosti.az/media/2019/04/23/osel.jpg\"\n" +
            "}]\n" +
            "  \n" +
            "}";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shop, container, false);
        shopList = view.findViewById(R.id.shopList);
        backgroundImg = view.findViewById(R.id.backgroundShopImage);
        TextView balanceTxt = view.findViewById(R.id.balanceText);
        balanceTxt.setText("Ваш баланс: "+GetUserInfo.userCounterCoins+" коинов");
        takeItems("YA SOSAL MENYA EBALI");
//        Toast.makeText(getContext(), takeItems(GetUserInfo.userToken), Toast.LENGTH_SHORT).show();
//        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 100);
//        valueAnimator.setDuration(10);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                System.out.println(animation.getAnimatedValue());
//                backgroundImg.setAlpha((Float)animation.getAnimatedValue());
//            }
//        });
        changeColors();
        return view;
    }

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> imageURLs = new ArrayList<>();

    public void changeColors() {
        ValueAnimator anim = ValueAnimator.ofInt(0,360);
        anim.setDuration(2000);
        int hue = 0;
        hsv = new float[3]; // Transition color
        hsv[1] = 0.4f;
        hsv[2] = 1;
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                hsv[0] = 360 * animation.getAnimatedFraction();
                int i = (Integer) animation.getAnimatedValue();
                System.out.println(i);
                hsv[0] = (float) i;
                runColor = Color.HSVToColor(hsv);
                backgroundImg.setBackgroundColor(runColor);
            }
        });
        anim.setRepeatCount(Animation.INFINITE);

        anim.start();
    }

    @Override
    public void onResume() {
        ShopItemsListAdapter shopItemsListAdapter = new ShopItemsListAdapter();
        shopList.setAdapter(shopItemsListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        shopList.setLayoutManager(layoutManager);
        super.onResume();
    }

    public String takeItems(String token) {
//        SocketConnect socketConnect = new SocketConnect();
//        socketConnect.execute("shop",token);
        String o = null;
//        try {
//            o = (String) socketConnect.get(2, TimeUnit.SECONDS);
//        } catch (ExecutionException | TimeoutException | InterruptedException e) {
//            e.printStackTrace();
//        }
        parseItems(jsonList);
        return o;
    }

    private void parseItems(String jsonFile) {
        try {
            System.out.println(jsonFile);
            JSONObject object = new JSONObject(jsonFile);
            JSONArray items = object.getJSONArray("items");
            for (int i = 0; i < 6; i++) {
                JSONObject jsonObject = items.getJSONObject(i);
                String name = jsonObject.getString("name");
                String img = jsonObject.getString("img");
                names.add(name);
                imageURLs.add(img);
            }

            OurData.itemNames = new String[names.size()];
            OurData.itemNames = names.toArray(OurData.itemNames);
            OurData.itemImageUrls = new String[imageURLs.size()];
            OurData.itemImageUrls = imageURLs.toArray(OurData.itemImageUrls);

//            Toast.makeText(getContext(), "JSON Result. Name: "+name+" URL: "+img, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        names.clear();
        imageURLs.clear();

        super.onPause();
    }
}
