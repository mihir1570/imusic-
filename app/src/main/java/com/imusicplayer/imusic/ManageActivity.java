package com.imusicplayer.imusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class ManageActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private Switch aSwitch;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);


        bottomNavigationView = findViewById(R.id.bottom_Navigation_manage);
        bottomNavigationView.setSelectedItemId(R.id.menu_manage);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
//                    case R.id.menu_playlist:
//                        startActivity(new Intent(getApplicationContext(), PlaylistActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
                    case R.id.menu_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu_manage:
                        return true;
                    case R.id.menu_pedometer:
                        startActivity(new Intent(getApplicationContext(), PedometerActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.menu_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }

                return false;
            }
        });


        Element adsElement = new Element();
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.ic_about_white)
                .setDescription(" If you're on the go, use a free music streaming app to listen to your tunes just about anywhere. We compiled a list of the best free music apps to help you enjoy your personal music library, discover new songs, listen to streaming music.\n" +
                        "\n" +
                        "These apps are completely free, and most are compatible with Android devices. Use the link to download the app or find it in the play store on your smartphone. You'll be up and running in no time. our extra feature is pedometer\n" +
                        "\n" +
                        "A pedometer is an accessory that will allow you to measure your physical activity when walking. You will be able to use it to find out the number of steps you make during each fitness walking session, as well as the distance covered, time spent and even the number of calories burnt with the more advanced pedometers." +
                        "\n" +
                        "\n" +
                        "To use the pedometer feature goto imusic App info -> goto Permission -> Allow Physical activity")
                .addItem(new Element().setTitle("App Version 1.0"))
                .addGroup("CONNECT WITH US!")
                .addEmail("imusicplayer07@gmail.com")
//                .addWebsite("Your website/")
//                .addYoutube("UCbekhhidkzkGryM7mi5Ys_w")   //Enter your youtube link here
                .addPlayStore("com.example.yourprojectname")   //Replace all this with your package name
                .addInstagram("imusic_app")    //Your instagram id
                .addItem(createCopyright())
                .create();
        setContentView(aboutPage);
    }

    private Element createCopyright() {
        Element copyright = new Element();
        @SuppressLint("DefaultLocale") final String copyrightString = String.format("Copyright %d by i music", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setIconDrawable(R.drawable.ic_person_white);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManageActivity.this, copyrightString, Toast.LENGTH_SHORT).show();
            }
        });
        return copyright;

    }
}