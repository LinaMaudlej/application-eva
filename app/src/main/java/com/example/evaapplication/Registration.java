package com.example.evaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Registration extends AppCompatActivity {
    private CountryCodePicker cpp;
    private EditText phoneText;
    private EditText codeText;
    private Button continueAndNextBtn;
    private String checker ="",phoneNumber="";
    private RelativeLayout relativeLayout;

    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private  String mVerficationId;
    private  PhoneAuthProvider.ForceResendingToken mResendToken;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth=FirebaseAuth.getInstance();
        loadingBar=new ProgressDialog(this);


        phoneText=findViewById(R.id.phoneText);
        codeText=findViewById(R.id.codeText);
        continueAndNextBtn=findViewById(R.id.continueNextButton);
        relativeLayout=findViewById(R.id.phoneAuth);
        cpp = (CountryCodePicker) findViewById(R.id.ccp);
        cpp.registerCarrierNumberEditText(phoneText);
        continueAndNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(continueAndNextBtn.getText().equals("Submit") || checker.equals("Code Sent")){
                        String verficationCode= codeText.getText().toString();
                        if(verficationCode.equals("")){
                            Toast.makeText(Registration.this,"Please write verfication code first", Toast.LENGTH_SHORT).show();

                        }else{
                            loadingBar.setTitle("Code Verfication");
                            loadingBar.setMessage("Please wait, while we are verifying your code.");
                            loadingBar.setCanceledOnTouchOutside(false);
                            loadingBar.show();
                            PhoneAuthCredential credintial=PhoneAuthProvider.getCredential(mVerficationId,verficationCode);
                            signInWithPhoneAuthCredential(credintial);
                        }
                }else{
                    phoneNumber=cpp.getFullNumberWithPlus();
                    if(!phoneNumber.equals("")){
                        loadingBar.setTitle("Phone Number Verfication");
                        loadingBar.setMessage("Please wait, while we are verifying your phone number.");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();
                        PhoneAuthOptions options =
                                PhoneAuthOptions.newBuilder(mAuth)
                                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                        .setActivity(Registration.this)                 // Activity (for callback binding)
                                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                        .build();
                        PhoneAuthProvider.verifyPhoneNumber(options);
                    }else {
                        Toast.makeText(Registration.this,"Please write valid phone number", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Registration.this,"Invalid phone number..", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                relativeLayout.setVisibility(View.VISIBLE);
                continueAndNextBtn.setText("Continue");
                codeText.setVisibility(View.GONE);

            }
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                mVerficationId=s;
                mResendToken=forceResendingToken;

                relativeLayout.setVisibility(View.GONE);
                checker = "Code Sent";
                continueAndNextBtn.setText("Submit");
                codeText.setVisibility(View.VISIBLE);
                loadingBar.dismiss();
                Toast.makeText(Registration.this,"Code has been sent, please check..", Toast.LENGTH_SHORT).show();


            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            Toast.makeText(Registration.this,"Cong!! you are logged in", Toast.LENGTH_SHORT).show();
                            sendUserToMainActivity();
                        } else {
                            loadingBar.dismiss();
                            String e=task.getException().toString();
                            Toast.makeText(Registration.this,"Error" + e, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    private  void sendUserToMainActivity(){
        Intent intent=new Intent(Registration.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
