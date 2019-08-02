package com.imooc.wechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_main:
                intent = new Intent(LaunchActivity.this, MainActivity.class);
                break;
            case R.id.btn_main_tab:
                intent = new Intent(LaunchActivity.this, MainActivityWithTab.class);
                break;
            case R.id.btn_splash:
                intent = new Intent(LaunchActivity.this, SplashActivity.class);
                break;
            case R.id.btn_banner:
                intent = new Intent(LaunchActivity.this, BannerActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
