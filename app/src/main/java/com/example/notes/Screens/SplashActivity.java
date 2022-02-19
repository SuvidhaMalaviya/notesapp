package com.example.notes.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notes.MainActivity;
import com.example.notes.R;

/***
 * Created by Suvidha Malaviya - 01-02-2022
 */

public class SplashActivity extends AppCompatActivity {

    View view_line1,view_line2,view_line3,view_line4,view_line5,view_line6;
    TextView tv_title;
    ImageView iv_logo;

    Animation top_animation,middle_animation,bottom_animation;

    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        initComponent();

        setAnimation();

        goNext();
    }

    /***
     * Initialise all the component,animators and set listeners
     */
    private void initComponent() {
        view_line1 = findViewById(R.id.view_line1);
        view_line2 = findViewById(R.id.view_line2);
        view_line3 = findViewById(R.id.view_line3);
        view_line4 = findViewById(R.id.view_line4);
        view_line5 = findViewById(R.id.view_line5);
        view_line6 = findViewById(R.id.view_line6);

        tv_title = findViewById(R.id.tv_title);

        iv_logo = findViewById(R.id.iv_logo);

        top_animation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        middle_animation = AnimationUtils.loadAnimation(this,R.anim.middle_animation);
        bottom_animation = AnimationUtils.loadAnimation(this,R.anim.botton_animation);
    }

    /***
     * Set animation for the component
     */
    private void setAnimation() {
        view_line1.setAnimation(top_animation);
        view_line2.setAnimation(top_animation);
        view_line3.setAnimation(top_animation);
        view_line4.setAnimation(top_animation);
        view_line5.setAnimation(top_animation);
        view_line6.setAnimation(top_animation);
        iv_logo.setAnimation(middle_animation);
        tv_title.setAnimation(bottom_animation);
    }

    /***
     * Go to next activity after 5 second of delay
     */
    private void goNext() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}