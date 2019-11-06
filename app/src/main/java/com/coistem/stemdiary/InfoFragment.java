package com.coistem.stemdiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;


public class InfoFragment extends Fragment {

    private ImageView avatar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ImageView avatar = view.findViewById(R.id.avatarImageView);
        avatar.setImageResource(R.drawable.ic_example_avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);

            }
        });
        TextView nameTxt = view.findViewById(R.id.nameText);
        nameTxt.setText(GetUserInfo.userSurname+" "+GetUserInfo.userName+" "+GetUserInfo.userThirdName);
        TextView coinsTxt = view.findViewById(R.id.coinsText);
        coinsTxt.setText("Коины: "+GetUserInfo.userCoins);
        Button vkAuthBtn = view.findViewById(R.id.vkAuth);
        vkAuthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VKApiExpirementsActivity.class);
                startActivity(intent);
            }
        });
        Button exitFromAccount = view.findViewById(R.id.exitButton);
        exitFromAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitFromAccount();

            }
        });
        return view;
    }

    private void exitFromAccount() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("logins", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("login",null);
        editor.putString("password",null);
        editor.putBoolean("isChecked",false);
        editor.apply();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case 1:
            {
                if (resultCode == Activity.RESULT_OK)
                {
                    Bitmap bitmap = null;
                    Uri selectedImage = data.getData();
                    String encodedPath = selectedImage.getEncodedPath();
//                    System.out.println(encodedPath);
//                    try {
//                        Picasso.
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
////                    avatar.setImageBitmap(bitmap);
                }
                break;
            }
        }
    }
}
