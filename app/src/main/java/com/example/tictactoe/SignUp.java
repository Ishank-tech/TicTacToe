package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();


        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_user);
        regEmail = findViewById(R.id.email);
        regPhoneNo = findViewById(R.id.PhoneNo);
        regPassword = findViewById(R.id.Password_signup);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);

        regBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                if(!validateName() | !validateUsername() | !validateEmail() | !validatePhoneNo() | !validatePassword()){
                    return;
                }

                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhoneNo.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
                reference.child(phoneNo).setValue(helperClass);


                Intent intent = new Intent(getApplicationContext(),VerifyPhoneNo.class);
                intent.putExtra("phoneNo",phoneNo);
                startActivity(intent);


                /*UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
                reference.child(email).setValue(helperClass);*/


                /*Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
                finish();*/

            }
        });

    }


    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();

        if(val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }
        else{
            regName.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername(){
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";


        if(val.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return false;
        } else if(val.length() >= 20){
            regUsername.setError("Username too long");
            return false;
        }
        else if(!val.matches(noWhiteSpace)){
            regUsername.setError("White Spaces are not allowed");
            return false;
        }
        else{
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9.+_-]+@[a-z]+\\.[a-z]+";

        if(val.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        } else if(!val.matches(emailPattern)){
            regEmail.setError("Invalid email address");
            return false;
        }
        else{
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo(){
        String val = regPhoneNo.getEditText().getText().toString();

        if(val.isEmpty()){
            regPhoneNo.setError("Field cannot be empty");
            return false;
        }
        else{
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" + "(?=.*[a-zA-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{4,}" + "$";

        if(val.isEmpty()){
            regPassword.setError("Field cannot be empty");
            return false;
        } else if(!val.matches(passwordVal)){
            regPassword.setError("Password is too weak");
            return false;
        }
        else{
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }



    public void SignIn(View view){

    }

    public void GoTo(View view){

        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
        finish();

    }

}