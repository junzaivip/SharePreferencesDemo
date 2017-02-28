package com.junzaivip.sharepreferencesdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by junzaivip on 2017-2-24.
 */

public class LoginActivity  extends BaseActivity implements View.OnClickListener{
    private EditText userName;
    private EditText passWord;
    private Button login;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox remberPass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取SharedPreferences对象
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        userName = (EditText)findViewById(R.id.account);
        passWord = (EditText)findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        //调用getBoolean()方法去获取remember_password这个键对应的值, 如果不存在默认的值, 就是用的是false
        boolean isRemember = pref.getBoolean("remember_password", false);
        remberPass = (CheckBox)findViewById(R.id.remember_pass);

        if(isRemember){
            //账号和密码都设置到文本框
            String username = pref.getString("username","");
            String password = pref.getString("password","");

            userName.setText(username);
            passWord.setText(password);
            remberPass.setChecked(true);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                String username = userName.getText().toString();
                String password = passWord.getText().toString();
                if(username.equals("admin") && password.equals("123")){
                    // 将数据存储在SharedPreferences当中
                    editor = pref.edit();
                    if(remberPass.isChecked()){ // 检验复选框是否被选中
                        // 如果被选中, remember_password的值改为True
                        editor.putBoolean("remember_password", true);
                        editor.putString("username",username);
                        editor.putString("password", password);
                    } else{
                        editor.clear();
                    }
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else{
                    Toast.makeText(this, "账号或者密码错误", Toast.LENGTH_SHORT).show();
                }

        }

    }
}
