package com.imusicplayer.imusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // give reference to ui element
    LinearLayout L1,L2;
    TextView tv;
    Animation Downtotop,Fade;
    FirebaseAuth mAuth;


    //xml file for animate...
    //here on tag line i will give fading animation and on linear layout 2 down to top animation



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        L1 = (LinearLayout)findViewById(R.id.l1);
        L2 = (LinearLayout)findViewById(R.id.l2);

        tv = (TextView)findViewById(R.id.tag);
        Downtotop = AnimationUtils.loadAnimation(this,R.anim.downtotop);
        Fade = AnimationUtils.loadAnimation(this,R.anim.fade);

        L2.setAnimation(Downtotop);
        tv.setAnimation(Fade);

        final Intent i = new Intent(MainActivity.this,LoginActivity.class);

        Thread thread =new Thread()
        {
            @Override
            public void run(){
                try{
                    sleep(3500);    //to stay on splash screen =>>>>> sleep for 5 second
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };thread.start();

    }
}