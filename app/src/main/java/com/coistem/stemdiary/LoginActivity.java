package com.coistem.stemdiary;

import android.app.ProgressDialog;
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

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    HashMap<String,String> accounts = new HashMap<>();
    private EditText loginText;
    private EditText passwordTxt;
    private AlertDialog loginErrorDialog;
    private AlertDialog.Builder loginErrorBuilder;
    private AlertDialog errorConnectionDialog;
    private AlertDialog.Builder errorConnectionBuilder;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CheckBox rememberBox;
    private String login;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginText = findViewById(R.id.loginText);
        passwordTxt = findViewById(R.id.pswTxt);
        final Button signInButton = findViewById(R.id.loginInBtn);
        addAccounts(); // вносим аккаунты в бд
        sharedPreferences = getSharedPreferences("logins",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String loginSP = sharedPreferences.getString("login", null);
        String passwordSP = sharedPreferences.getString("password", null);
        boolean isChecked = sharedPreferences.getBoolean("isChecked",false);
        rememberBox = findViewById(R.id.rememberCheck);
        rememberBox.setChecked(false);

        errorConnectionBuilder = new AlertDialog.Builder(LoginActivity.this)
                .setMessage("Ошибка подключения к интернету.\nПроверьте подключение к интернету или повторите попытку позже.")
                .setPositiveButton("обновить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        errorConnectionDialog.cancel();
                        signInButton.performClick();
                    }
                })
                .setNegativeButton("закрыть", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                });
        errorConnectionDialog = errorConnectionBuilder.create();
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

                CheckingConnection checkingConnection = new CheckingConnection();
                boolean isOnline = false;
                try {
//                    ProgressDialog progressDialog = new ProgressDialog.show(LoginActivity.this,"Loading...",true);
                    isOnline = (Boolean) checkingConnection.execute(LoginActivity.this, "https://google.com/").get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isOnline) {
                    login = loginText.getText().toString();
                    password = passwordTxt.getText().toString();
//                    SocketConnect socketConnect = new SocketConnect();
//                    socketConnect.execute(login,password);
                    boolean isSuccesfulLogin = signIn(login, password);
                    if(isSuccesfulLogin) {
                        if(!VKSdk.isLoggedIn()) {
                            Toast.makeText(LoginActivity.this, "Пожалуйста, авторизуйтесь.", Toast.LENGTH_SHORT).show();
                            String[] scope = {VKScope.FRIENDS};
                            VKSdk.login(LoginActivity.this,scope);
                        } else {
                            logIn();

                        }
                    } else {
                        loginErrorDialog.show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "НЕТ ИНТЕРНЕТА КАШМАР КАКОЙТА", Toast.LENGTH_SHORT).show();
                    errorConnectionDialog.show();
                }

            }
        });

        if(loginSP!=null && passwordSP!=null) {
            loginText.setText(loginSP);
            passwordTxt.setText(passwordSP);
            signInButton.performClick();
        }

    }

    public void logIn() {
        Toast.makeText(LoginActivity.this, "запускаю активность...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        if (rememberBox.isChecked()){
            editor.putString("login",login);
            editor.putString("password",password);
            editor.putBoolean("isChecked",rememberBox.isChecked());
            editor.apply();
        }
    }

    private boolean signIn(String login, String password) {
        SocketConnect socketConnect = new SocketConnect();
        try {
            String execute = (String) socketConnect.execute(login, password).get();
            if(execute.equals("Go daleko!")){
                return false;
            } else {
                GetUserInfo getUserInfo = new GetUserInfo();
                getUserInfo.parseJSONFromServer(execute);
                return true;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
//        String pass = accounts.get(login);
//        System.out.println(pass);
//        if(password.equals(pass)){
//            MainActivity.userLogin = login;
//            return true;
//        } else if(pass!=null&&!password.equals(pass)) {
//            Toast.makeText(LoginActivity.this, "Неверный пароль.", Toast.LENGTH_SHORT).show();
//            return false;
//        } else {
//            Toast.makeText(LoginActivity.this, "Такого пользователя не существует.", Toast.LENGTH_SHORT).show();
//            return false;
//        }
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

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(final VKAccessToken res) {
                // Пользователь успешно авторизовался
                logIn();
            }
            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
