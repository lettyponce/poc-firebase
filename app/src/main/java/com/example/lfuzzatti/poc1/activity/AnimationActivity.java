package com.example.lfuzzatti.poc1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lfuzzatti.poc1.R;

public class AnimationActivity
        extends AppCompatActivity
        implements View.OnClickListener, Animation.AnimationListener {

    ImageView dogImage;
    Button btFadeIn, btFadeOut, btBlink, btZoomIn, btZoomOut, btRotate, btMoveOut, btMoveIn;
    Button btSlideUp, btSlideDown, btBounce;
    Button btTogether, btSequencial;

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        setupWidgets();
    }

    private void setupWidgets() {
        dogImage = findViewById(R.id.dog_image);
        btFadeIn = findViewById(R.id.btFadeIn);
        btFadeOut = findViewById(R.id.btFadeOut);
        btBlink = findViewById(R.id.btBlink);
        btZoomIn = findViewById(R.id.btZoomIn);
        btZoomOut = findViewById(R.id.btZoomOut);
        btRotate = findViewById(R.id.btRotate);
        btMoveOut = findViewById(R.id.btMoveOut);
        btMoveIn = findViewById(R.id.btMoveIn);
        btSlideUp = findViewById(R.id.btSlideUp);
        btSlideDown = findViewById(R.id.btSlideDown);
        btBounce = findViewById(R.id.btBounce);
        btTogether = findViewById(R.id.btTogether);
        btSequencial = findViewById(R.id.btSequencial);

        btFadeIn.setOnClickListener(this);
        btFadeOut.setOnClickListener(this);
        btBlink.setOnClickListener(this);
        btZoomIn.setOnClickListener(this);
        btZoomOut.setOnClickListener(this);
        btRotate.setOnClickListener(this);
        btMoveOut.setOnClickListener(this);
        btMoveIn.setOnClickListener(this);
        btSlideUp.setOnClickListener(this);
        btSlideDown.setOnClickListener(this);
        btBounce.setOnClickListener(this);
        btTogether.setOnClickListener(this);
        btSequencial.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btFadeIn:
                executeAnimation(R.anim.fade_in, dogImage);
                break;

            case R.id.btFadeOut:
                executeAnimation(R.anim.fade_out, dogImage);
                break;

            case R.id.btBlink:
                executeAnimation(R.anim.blink, dogImage);
                break;

            case R.id.btZoomIn:
                executeAnimation(R.anim.zoom_in, dogImage);
                break;

            case R.id.btZoomOut:
                executeAnimation(R.anim.zoom_out, dogImage);
                break;

            case R.id.btRotate:
                executeAnimation(R.anim.rotate, dogImage);
                break;

            case R.id.btMoveOut:
                executeAnimation(R.anim.move_out, dogImage);
                break;

            case R.id.btMoveIn:
                executeAnimation(R.anim.move_in, dogImage);
                break;

            case R.id.btSlideUp:
                executeAnimation(R.anim.slide_up, dogImage);
                break;

            case R.id.btSlideDown:
                executeAnimation(R.anim.slide_down, dogImage);
                break;

            case R.id.btBounce:
                executeAnimation(R.anim.bounce, dogImage);
                break;

            case R.id.btSequencial:
                executeAnimation(R.anim.sequencial, dogImage);
                break;

            case R.id.btTogether:
                executeAnimation(R.anim.together, dogImage);
                break;

            default:
                break;
        }
    }

    private void executeAnimation(final int animationId, View viewAnimation) {
        //define animation
        animation = AnimationUtils.loadAnimation(getApplicationContext(), animationId);

        // set animation listener
        animation.setAnimationListener(this);

        //anchor animation with element
        viewAnimation.startAnimation(animation);
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
