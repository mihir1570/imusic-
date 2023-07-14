package com.imusicplayer.imusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         // at least 1 digit
                    "(?=.*[a-z])" +         // at least 1 lower case letter
                    "(?=.*[A-Z])" +         // at least 1 upper case letter
                    "(?=.*[@#$%^&+=_])" +    // at least 1 special character
                    "(?=\\S+$)" +           // no white space
                    ".{6,}" +               // at least 6 characters
                    "$");
    // private static final Pattern PHONE_PATTERN = Pattern.compile("(?=.*[6-9][0-9]{9})");

    EditText RegisterPhone,RegisterPassword,RegisterName;
    String Phone, Password,Name;                            // to save phone and name from edit text
    Button RegisterButton;
    ProgressDialog LoadingBar;
    EditText password;
    boolean passwordvisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        RegisterPhone=findViewById(R.id.register_password);
        password=findViewById(R.id.register_password);

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int Right=2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=password.getRight()-password.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=password.getSelectionEnd();
                        if(passwordvisible){
                            // set drawable image here
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_visibility_off,0);
                            // for hide password
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible=false;
                        }else {
                            // set drawable image here
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_visibility,0);
                            // for show password
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible=true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });




        RegisterPassword = (EditText)findViewById(R.id.register_password);
        RegisterName = (EditText)findViewById(R.id.register_name);
        RegisterButton = (Button)findViewById(R.id.register_btn);


        LoadingBar = new ProgressDialog(this);

        LoadingBar.setTitle("Creating Account...");
        LoadingBar.setMessage("Please wait,while we are checking our credentials.....");
        LoadingBar.setCanceledOnTouchOutside(false);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // click button to take text from edit text and validate it is empty or not
                // save edit text entries in string that we take in starting
                //Phone = RegisterPhone.getText().toString();


                Password = RegisterPassword.getText().toString();
                Name = RegisterName.getText().toString();

                CreateNewAccount(Password,Name);

            }
        });

    }


    @Override
    public void onBackPressed() {
        Toast.makeText(RegisterActivity.this, "Please Register", Toast.LENGTH_SHORT).show();
    }

    private void CreateNewAccount(String password, String name) {


        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
        }
        else if(!PASSWORD_PATTERN.matcher(password).matches())
        {
            Toast.makeText(this, "Weak Password", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Enter your username", Toast.LENGTH_SHORT).show();
        }
        else {
            // start creating account
            LoadingBar.show();

            final DatabaseReference mRef;
            mRef = FirebaseDatabase.getInstance().getReference();

            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    //check number that user enter is present in our database or not

                    if (!(snapshot.child("Users").child(name).exists())) {
                        //  if user not exist then create account in database

                        HashMap<String,Object> userdata = new HashMap<>();
                        // userdata.put("phone", phone);
                        userdata.put("password", password);
                        userdata.put("name", name);


                        mRef.child("Users").child(name).updateChildren(userdata)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful())
                                        {
                                            LoadingBar.dismiss();
                                            Toast.makeText(RegisterActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                                            startActivity(i);
                                        }
                                        else
                                        {
                                            LoadingBar.dismiss();
                                            Toast.makeText(RegisterActivity.this, "Please try again after some time", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                    }
                    else
                    {
                        // if user exist
                        LoadingBar.dismiss();
                        Toast.makeText(RegisterActivity.this, "User with this name already exist", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}