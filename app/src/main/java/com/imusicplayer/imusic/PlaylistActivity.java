package com.imusicplayer.imusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PlaylistActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

//        bottomNavigationView = findViewById(R.id.bottom_Navigation_playlist);
//        bottomNavigationView.setSelectedItemId(R.id.menu_playlist);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.menu_playlist:
//                        return true;
//                    case R.id.menu_home:
//                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//                    case R.id.menu_manage:
//                        startActivity(new Intent(getApplicationContext(), ManageActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//                    case R.id.menu_pedometer:
//                        startActivity(new Intent(getApplicationContext(), PedometerActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//                    case R.id.menu_profile:
//                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;

//                }
//
//                return false;
//            }
//        });
    }
}