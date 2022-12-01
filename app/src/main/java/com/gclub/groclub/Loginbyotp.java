package com.gclub.groclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gclub.groclub.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class Loginbyotp extends AppCompatActivity {
    EditText  otpcodeET;
    TextInputLayout phonenum;
    Button sendotp , verifyotpbutton;
    ProgressBar progressBar;
    String verification;
    String Userenteredphonenum , cusname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginbyotp);

        phonenum = findViewById(R.id.phonenum);
        sendotp = findViewById(R.id.sendotpbutton);
        otpcodeET = findViewById(R.id.OtpcodeEdittext);
        verifyotpbutton = findViewById(R.id.Verifyingotpbutton);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        otpcodeET.setVisibility(View.INVISIBLE);
        verifyotpbutton.setVisibility(View.INVISIBLE);

    }

    public Boolean Validatephonenum(){
        String val = phonenum.getEditText().getText().toString();
        if(val.isEmpty()){
            phonenum.setError("Field cannot be empty");
            return false;
        }else {
            phonenum.setError(null);
            phonenum.setErrorEnabled(false);
            return true;
        }
    }
    public void sendotp(View view) {
        if(!Validatephonenum()){
            return;
        }else {
//            otpcodeET.setVisibility(View.VISIBLE);
//            verifyotpbutton.setVisibility(View.VISIBLE);

            isUser();
        }
    }
    public void checkingotp(View view) {
        String code = otpcodeET.getText().toString();
        if (code.isEmpty() || code.length() < 6) {
            otpcodeET.setError("Wrong OTP...");
            otpcodeET.requestFocus();
            return;
        }else {
            progressBar.setVisibility(View.VISIBLE);
            verifycode(code);
        }
    }

    private void isUser() {
//        final String Userenteredphonenum;
        Userenteredphonenum = phonenum.getEditText().getText().toString().trim();

//        Paper.book().write(Prevalent.userphoneforotpkey,Userenteredphonenum);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer Detail");
        Query checkuser = reference.orderByChild("phonenum").equalTo(Userenteredphonenum);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    cusname = dataSnapshot.child(Userenteredphonenum).child("fullname").getValue(String.class);

                    otpcodeET.setVisibility(View.VISIBLE);
                    verifyotpbutton.setVisibility(View.VISIBLE);

                    phonenum.setError(null);
                    phonenum.setErrorEnabled(false);

                    Userenteredphonenum = "+91" + Userenteredphonenum;
                    sendVerificationCodeToUser(Userenteredphonenum);

                }
                else {
                    phonenum.setError("No Such User Exist");
                    phonenum.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verification = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                progressBar.setVisibility(View.VISIBLE);
                otpcodeET.setText(code);
                verifycode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(Loginbyotp.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };
    private void verifycode(String codebyuser){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification, codebyuser);
        signinTheUserByCredential(credential);
    }
    private void signinTheUserByCredential(PhoneAuthCredential credential){
        FirebaseAuth firebaseAuth =  FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(Loginbyotp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Userenteredphonenum = phonenum.getEditText().getText().toString().trim();
                    Paper.book().write(Prevalent.userphonekey,Userenteredphonenum);
                    Intent intent = new Intent(getApplicationContext(),Homepage.class);
                    Prevalent.custumername = cusname;
                    Prevalent.userphonekey = Userenteredphonenum;
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(Loginbyotp.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}