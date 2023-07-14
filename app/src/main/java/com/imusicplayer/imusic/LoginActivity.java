package com.imusicplayer.imusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private Button btn_phone;
    ImageView btnGoogle, btnPhone, btnFacebook;
    EditText LoginUser, LoginPassword;
    Button LoginButton;
    String UserEnterUsername, UserEnterPassword;         // to store Username and password that user enter in edittext
    String userPasswordInDB;                            // to store actual password from database
    ProgressDialog LoadingBar;
    boolean passwordvisible;

    CheckBox remember;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //****************************************Remember me Login*********************************

        remember = findViewById(R.id.checkBox);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");

        if (checkbox.equals("true")) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if (checkbox.equals("false")) {
//            Toast.makeText(LoginActivity.this, "Please Sign in", Toast.LENGTH_SHORT).show();
        }

        //****************************************Remember me Login*********************************


        btnGoogle = findViewById(R.id.btn_Google);
        btnPhone = findViewById(R.id.btn_phone);
//        btnFacebook = findViewById(R.id.btn_Facebook);

        btnGoogle.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, GoogleSignInActivity.class);
                startActivity(intent);
            }
        });

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, entermobilenumberone.class);
                startActivity(i);
            }
        });


        LoginUser = findViewById(R.id.Login_username);
        LoginPassword = findViewById(R.id.Login_password);

        // ********** Password Show on/of method ***********

        LoginPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= LoginPassword.getRight() - LoginPassword.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = LoginPassword.getSelectionEnd();
                        if (passwordvisible) {
                            // set drawable image here
                            LoginPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
                            // for hide password
                            LoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible = false;
                        } else {
                            // set drawable image here
                            LoginPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visibility, 0);
                            // for show password
                            LoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible = true;
                        }
                        LoginPassword.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });


        LoadingBar = new ProgressDialog(this);
        LoginUser = (EditText) findViewById(R.id.Login_username);
        LoginPassword = (EditText) findViewById(R.id.Login_password);
        LoginButton = (Button) findViewById(R.id.Login_btn);

        LoadingBar.setTitle("Login Account");
        LoadingBar.setMessage("Please wait,we are checking your info in our database");
        LoadingBar.setCanceledOnTouchOutside(false);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserEnterUsername = LoginUser.getText().toString();
                UserEnterPassword = LoginPassword.getText().toString();
                LoginAccount(UserEnterUsername, UserEnterPassword);

            }
            //*************************** Remember me login ***************************************************

        });
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
//                    Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                } else if (!compoundButton.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
//                    Toast.makeText(LoginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // *************************** Remember me login ***************************************************

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

    }


    @Override
    public void onBackPressed() {
        Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
    }

    private void LoginAccount(String UserEnterUsername, String UserEnterPassword) {

        // check edittext is empty or not
        if (TextUtils.isEmpty(UserEnterUsername)) {
            Toast.makeText(this, "Enter your username", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(UserEnterPassword)) {
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
        } else {
            LoadingBar.show();

            final DatabaseReference mRef;
            mRef = FirebaseDatabase.getInstance().getReference();

            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    // if edit text are not empty than we chick that the username that the user entered is exist in database or not
                    // if it in exist in our database than we redirect password in "user password" and then
                    // match the password with the password that user entered in edittext
                    // if it is match then we redirect user to home activity

                    if (snapshot.child("Users").child(UserEnterUsername).exists()) {

                        // if exist redirect password from database
                        userPasswordInDB = snapshot.child("Users").child(UserEnterUsername).child("password").getValue(String.class);   //.toString();
                        // now check
                        if (UserEnterPassword.equals(userPasswordInDB)) {


                            // go to HomeActivity
                            Toast.makeText(LoginActivity.this, "Welcome back", Toast.LENGTH_SHORT).show();
                            LoadingBar.dismiss();

//
//
//                            ***************************************Edit User Profile********************************************************************
//
//                            String Username1 = snapshot.child("Users").child(UserEnterUsername).child("name").getValue(String.class);
//                            String Password1 = snapshot.child("Users").child(UserEnterUsername).child("password").getValue(String.class);
//
//                            Intent intent = new Intent(getApplicationContext(),TestActivityForRedriveDataUserProfile.class);
//
//                            intent.putExtra("name",Username1);
//                            intent.putExtra("password",Password1);
//
//                            startActivity(intent);
//                            finish();

//                            **********************************************************************************************************************************


                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);




                            //  user exist with the number that they enter and with correct password then user will redirect to-
                            // next activity

                        } else {
                            // if user exist but password is not correct
                            LoadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {
                        // user not exist
                        LoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Enter correct username", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}