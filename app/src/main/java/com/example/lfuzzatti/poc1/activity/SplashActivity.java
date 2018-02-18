package com.example.lfuzzatti.poc1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lfuzzatti.poc1.R;

public class SplashActivity extends AppCompatActivity {

    Animation animationLogo, animationTextApp, animationRedirect;

    ImageView imgLogo;
    TextView tvLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgLogo = findViewById(R.id.pet_logo);
        imgLogo.setVisibility(View.VISIBLE);
        tvLogo = findViewById(R.id.tv_logo);

        animationRedirect = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_splash_redirect);

        animationRedirect.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                finish();
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //Define AnimationLogo
        //define animation
        animationLogo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_splash_logo);

        // set animation listener
        animationLogo.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //anchor animation with element
                tvLogo.startAnimation(animationTextApp);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //Define AnimationTextApp
        //define animation
        animationTextApp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_splash_text);

        // set animation listener
        animationTextApp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tvLogo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //anchor animation with element
        imgLogo.startAnimation(animationLogo);

    }

}
