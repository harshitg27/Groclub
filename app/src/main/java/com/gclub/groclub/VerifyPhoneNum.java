package com.gclub.groclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gclub.groclub.Databasehelper.UserHelperclass;
import com.gclub.groclub.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class VerifyPhoneNum extends AppCompatActivity {
    EditText verificationcodeenterbyuser;
    Button Verifybtn;
    ProgressBar progressBar;
    String verifactioncodebysystem;
    private FirebaseAuth mAuth;
    TextView mobileno;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_num);
        verificationcodeenterbyuser= findViewById(R.id.VericationCodeEnterbyuser);
        Verifybtn = findViewById(R.id.Verifybtn);
        progressBar = findViewById(R.id.progressBar);
        mobileno = findViewById(R.id.textView7);
        mAuth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.GONE);

        String phonenum = getIntent().getStringExtra("Phonenum");
//        String fullname = getIntent().getStringExtra("Fullname");
//        String email = getIntent().getStringExtra("Email");
//        String password = getIntent().getStringExtra("Password");
//        String refral = getIntent().getStringExtra("Refral");
        phonenum = "+91" + phonenum ;
        sendVerificationCodeToUser(phonenum);
        mobileno.setText(String.valueOf(phonenum));

    }
    public void VerifyingOtpButton(View view) {
        String code = verificationcodeenterbyuser.getText().toString();

        if (code.isEmpty() || code.length() < 6) {
            verificationcodeenterbyuser.setError("Wrong OTP...");
            verificationcodeenterbyuser.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        verifycode(code);
    }

    private void sendVerificationCodeToUser(String phonenum) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenum,   // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks


    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verifactioncodebysystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                progressBar.setVisibility(View.VISIBLE);
                verificationcodeenterbyuser.setText(code);
                verifycode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyPhoneNum.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };
    private void verifycode(String codebyuser){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifactioncodebysystem , codebyuser);
        signinTheUserByCredential(credential);
    }
    private void signinTheUserByCredential(PhoneAuthCredential credential){
        FirebaseAuth firebaseAuth =  FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(VerifyPhoneNum.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    saveindatabase();

                    Intent intent = new Intent(getApplicationContext(),Homepage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(VerifyPhoneNum.this,"hello",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveindatabase(){

        reference = FirebaseDatabase.getInstance().getReference("Customer Detail");

        String fullname = getIntent().getStringExtra("Fullname");
        String phonenum = getIntent().getStringExtra("Phonenum");
        String email = getIntent().getStringExtra("Email");
        String password = getIntent().getStringExtra("Password");
        String refral = getIntent().getStringExtra("Refral");

        Prevalent.userphonekey = phonenum;
        Paper.book().write(Prevalent.userphonekey,phonenum);

        UserHelperclass helperclass = new UserHelperclass(fullname,phonenum,email,password,refral);
        reference.child(phonenum).setValue(helperclass);
    }
}