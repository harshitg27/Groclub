package com.gclub.groclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gclub.groclub.Prevalent.Prevalent;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {
    Button signupbutton;
    TextView logotv;
    TextInputLayout phonenum,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signupbutton = findViewById(R.id.signupbutton);
        logotv = findViewById(R.id.logotv);
        phonenum = findViewById(R.id.phonenum);
        password = findViewById(R.id.password);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,WelcomeScreen.class);
        startActivity(intent);
        finish();

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
    public Boolean Validatepassword(){
        String val = password.getEditText().getText().toString();
        if(val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
    public void loginbutton(View view){
        if(!Validatephonenum() | !Validatepassword()){
            return;
        }else {
            isUser();
        }
    }

    private void isUser() {
        final String userenteredphonenum = phonenum.getEditText().getText().toString().trim();
        final String userenteredpassword = password.getEditText().getText().toString().trim();

//        Paper.book().write(Prevalent.userphonekey,userenteredphonenum);
//        Paper.book().write(Prevalent.userpasswordkey,userenteredpassword);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer Detail");
        Query checkuser = reference.orderByChild("phonenum").equalTo(userenteredphonenum);


        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    phonenum.setError(null);
                    phonenum.setErrorEnabled(false);

                    String Passwordfromdb =  dataSnapshot.child(userenteredphonenum).child("password").getValue(String.class);
                    String cusname =  dataSnapshot.child(userenteredphonenum).child("fullname").getValue(String.class);

                    if(Passwordfromdb.equals(userenteredpassword)){
                        final String userenteredphonenum = phonenum.getEditText().getText().toString().trim();
                        final String userenteredpassword = password.getEditText().getText().toString().trim();

                        Paper.book().write(Prevalent.userphonekey,userenteredphonenum);
                        Paper.book().write(Prevalent.userpasswordkey,userenteredpassword);

                        Intent intent = new Intent(getApplicationContext(),Homepage.class);
                        Prevalent.custumername = cusname;
                        Prevalent.userphonekey = userenteredphonenum;
                        startActivity(intent);
                        finish();
                    }
                    else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
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


    public void signuppagebutton(View view) {
        Intent intent = new Intent(this,Signup.class);
        startActivity(intent);
        finish();

    }

    public void signbyotpbutton(View view) {
        Intent intent = new Intent(this,Loginbyotp.class);
        startActivity(intent);
    }
}