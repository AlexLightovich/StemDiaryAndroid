package com.coistem.stemdiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.vk.sdk.VKSdk;
import com.vk.sdk.util.VKUtil;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    HashMap<String,String> accounts = new HashMap<>();
    private EditText loginText;
    private EditText passwordTxt;
    private AlertDialog loginErrorDialog;
    private AlertDialog.Builder loginErrorBuilder;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CheckBox rememberBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginText = findViewById(R.id.loginText);
        passwordTxt = findViewById(R.id.pswTxt);
        Button signInButton = findViewById(R.id.loginInBtn);
        addAccounts(); // вносим аккаунты в бд
        sharedPreferences = getSharedPreferences("logins",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String loginSP = sharedPreferences.getString("login", null);
        String passwordSP = sharedPreferences.getString("password", null);
        boolean isChecked = sharedPreferences.getBoolean("isChecked",false);
        rememberBox = findViewById(R.id.rememberCheck);
        rememberBox.setChecked(false);




        loginErrorBuilder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Ошибка авторизации")
                .setMessage("Неверная комбинация логин/пароль")
                .setPositiveButton("ОK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loginErrorDialog.cancel();
                    }
                });
        loginErrorDialog = loginErrorBuilder.create();


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = loginText.getText().toString();
                String password = passwordTxt.getText().toString();
                boolean isSuccesfulLogin = signIn(login, password);
                if(isSuccesfulLogin) {
                    Toast.makeText(LoginActivity.this, "запускаю активность...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    if (rememberBox.isChecked()){
                        editor.putString("login",login);
                        editor.putString("password",password);
                        editor.putBoolean("isChecked",rememberBox.isChecked());
                        editor.apply();
                    }
                } else {
                    loginErrorDialog.show();
                }
            }
        });

        if(loginSP!=null && passwordSP!=null) {
            loginText.setText(loginSP);
            passwordTxt.setText(passwordSP);
            signInButton.performClick();
        }

    }

    private boolean signIn(String login, String password) {
        String pass = accounts.get(login);
        System.out.println(pass);
        if(password.equals(pass)){
            MainActivity.userLogin = login;
            return true;
        } else if(pass!=null&&!password.equals(pass)) {
            Toast.makeText(LoginActivity.this, "Неверный пароль.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(LoginActivity.this, "Такого пользователя не существует.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void addAccounts() {
        accounts.put("user","12345");
        accounts.put("eremin15","1337228");
        accounts.put("yeliseyenko23","56789");
        accounts.put("vasilev75","54321");
    }

    @Override
    protected void onPause() {
        super.onPause();
        loginText.setText("");
        passwordTxt.setText("");
        rememberBox.setChecked(false);
    }
}
