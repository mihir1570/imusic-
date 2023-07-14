package com.imusicplayer.imusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LogOutActivity extends AppCompatActivity {

    //    ImageView img;
//    TextView name,email;
//    Button logoutbtn;

//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);


//        img=(ImageView)findViewById(R.id.pimage);
//        name= (TextView) findViewById(R.id.nametext);
//        email= (TextView) findViewById(R.id.emailtext);
//        logoutbtn=(Button)findViewById(R.id.logout_btn);
//
//
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        name.setText(account.getDisplayName());
//        email.setText(account.getEmail());
//        Glide.with(this).load(account.getPhotoUrl()).into(img);
//
//        logoutbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
//                finish();
//            }
//        });
//    }
}