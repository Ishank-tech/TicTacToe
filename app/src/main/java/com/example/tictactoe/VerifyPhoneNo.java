package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNo extends AppCompatActivity {

    String verificationCodeBySystem;
    Button OTP_btn;
    EditText phoneNoEnteredByTheUser;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_no);
        
        OTP_btn = findViewById(R.id.OTP_btn);
        phoneNoEnteredByTheUser = findViewById(R.id.verify_mobileNo);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        phoneNoEnteredByTheUser.setText(String.format(getIntent().getStringExtra("phoneNo")));

        /*String phoneNo = getIntent().getStringExtra("phoneNo");*/
        
        /*sendVerificationCodeToUser(phoneNo);

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = phoneNoEnteredByTheUser.getText().toString();

                if(code.isEmpty() || code.length()<6){
                    phoneNoEnteredByTheUser.setError("Wrong OTP...");
                    phoneNoEnteredByTheUser.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);

            }
        });*/
        
    }

    public void sendOTP(View view){

        progressBar.setVisibility(View.VISIBLE);

        sendVerificationCodeToUser(phoneNoEnteredByTheUser.getText().toString().trim());

        /*Intent intent = new Intent(getApplicationContext(),EnterOTP.class);
        intent.putExtra("phoneNo",phoneNoEnteredByTheUser.getText().toString());
        startActivity(intent);*/

        /*if(!phoneNoEnteredByTheUser.getText().toString().trim().isEmpty()){
            if((phoneNoEnteredByTheUser.getText().toString().trim()).length() == 10 ){
                Intent intent = new Intent(getApplicationContext(),EnterOTP.class);
                intent.putExtra("phoneNo",phoneNoEnteredByTheUser.getText().toString());
                startActivity(intent);
            }
            else{
                Toast.makeText(VerifyPhoneNo.this,"Please Enter Correct NUmber",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(VerifyPhoneNo.this,"Enter Mobile number",Toast.LENGTH_SHORT).show();
        }*/
    }

    private void sendVerificationCodeToUser(String phoneNo) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //mAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;
            Intent intent = new Intent(getApplicationContext(),EnterOTP.class);
            intent.putExtra("phoneNo",phoneNoEnteredByTheUser.getText().toString());
            intent.putExtra("s",s);
            startActivity(intent);

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                progressBar.setVisibility(View.VISIBLE);
                /*verifyCode(code);*/
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(VerifyPhoneNo.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    /*private void sendVerificationCodeToUser(String phoneNo) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(VerifyPhoneNo.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    private void verifyCode(String CodeByUser){

        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationCodeBySystem,CodeByUser);
        signInTheUserByCredentials(credential);

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential){

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPhoneNo.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(),UserProfile.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(VerifyPhoneNo.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }*/


}