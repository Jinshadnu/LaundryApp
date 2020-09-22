package com.example.laundryapp.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laundryapp.MainActivity;
import com.example.laundryapp.R;
import com.example.laundryapp.welcome.WelcomeActivity;

public class SplashActivity extends AppCompatActivity {
    // Splash screen timer
    public Animation topAnim,bottomAnim;
    private static int SPLASH_TIME_OUT = 3000;
    public ImageView imageView;
    public TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imageView=(ImageView)findViewById(R.id.imageView_logo);
        textView=(TextView) findViewById(R.id.text_location);

        imageView.setAnimation(topAnim);
        textView.setAnimation(bottomAnim);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(i);

                finish();

            }
        },SPLASH_TIME_OUT);


    }
}