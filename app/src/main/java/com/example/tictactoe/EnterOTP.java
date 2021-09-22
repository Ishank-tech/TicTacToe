package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class EnterOTP extends AppCompatActivity {

    EditText OTP;
    String getOtps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        OTP = findViewById(R.id.OTP);
        getOtps = getIntent().getStringExtra("s");

    }

    public void Submit(View view){
        String code = OTP.getText().toString();
        if(getOtps != null){
            if(code.isEmpty() || code.length()<6){
                OTP.setError("Wrong OTP...");
                OTP.requestFocus();
                return;
            }
            verifyCode(code);
        }else{
            Toast.makeText(EnterOTP.this,"Please check Internet connection",Toast.LENGTH_SHORT).show();
        }
    }

    private void verifyCode(String CodeByUser){

        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(getOtps,CodeByUser);
        signInTheUserByCredentials(credential);

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential){

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(EnterOTP.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(),UserProfile.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(EnterOTP.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}