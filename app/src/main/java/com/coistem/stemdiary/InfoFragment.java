package com.coistem.stemdiary;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class InfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ImageView avatar = view.findViewById(R.id.avatarImageView);
        avatar.setImageResource(R.drawable.ic_example_avatar);
        TextView nameTxt = view.findViewById(R.id.nameText);
        nameTxt.setText(GetUserInfo.userSurname+" "+GetUserInfo.userName+" "+GetUserInfo.userThirdName);
        TextView coinsTxt = view.findViewById(R.id.coinsText);
        coinsTxt.setText("Коины: "+GetUserInfo.userCoins);
        return view;
    }
}
