package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

     TextInputLayout logPhoneNo, logPassword;
     ProgressBar progressBar;
     Button loginBtn,googleBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        logPhoneNo = findViewById(R.id.log_PhoneNo);
        logPassword = findViewById(R.id.log_password);
        loginBtn = (Button) findViewById(R.id.login_btn);
        googleBtn = findViewById(R.id.googlebtn);



    }

    private Boolean validatePhoneNo(){
        String val = logPhoneNo.getEditText().getText().toString();

        if(val.isEmpty()){
            logPhoneNo.setError("Field cannot be empty");
            return false;
        }
        else{
            logPhoneNo.setError(null);
            logPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = logPassword.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            logPassword.setError("Field cannot be empty");
            logPassword.requestFocus();
            return false;
        }
        else{
            logPassword.setError(null);
            logPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view) {

        if (!validatePhoneNo() | !validatePassword()) {
            return;
        }
        else {
            isUser();
        }
    }

    private void isUser() {

        final String _PhoneNo = logPhoneNo.getEditText().getText().toString().trim();
        final String _Password = logPassword.getEditText().getText().toString().trim();

        Query checkUser = FirebaseDatabase.getInstance().getReference("users").orderByChild("phoneNo").equalTo(_PhoneNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    logPhoneNo.setError(null);
                    logPhoneNo.setErrorEnabled(false);


                    String systemPassword = snapshot.child(_PhoneNo).child("password").getValue(String.class);

                    if (systemPassword.equals(_Password)) {

                        logPassword.setError(null);
                        logPassword.setErrorEnabled(false);

                        String nameFromDB = snapshot.child(_PhoneNo).child("name").getValue(String.class);
                        String usernameFromDB = snapshot.child(_PhoneNo).child("username").getValue(String.class);
                        String phoneNoFromDB = snapshot.child(_PhoneNo).child("phoneNo").getValue(String.class);
                        String emailFromDB = snapshot.child(_PhoneNo).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), UserProfile.class);

                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phoneNo", phoneNoFromDB);
                        intent.putExtra("password", systemPassword);

                        startActivity(intent);
                        finish();

                    } else {
                        //Toast.makeText(Login.this,"Password does not match!",Toast.LENGTH_SHORT).show();
                        logPassword.setError("Wrong Password");
                        logPassword.requestFocus();
                    }
                } else {
                    //Toast.makeText(Login.this,"No Such user exists!",Toast.LENGTH_SHORT).show();
                    logPhoneNo.setError("No such User exists");
                    logPhoneNo.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SignUp (View view){

        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
        finish();

    }

    public void google(View view){
        Intent intent = new Intent(Login.this, GoogleAuthentication.class);
        startActivity(intent);
        finish();
    }

}