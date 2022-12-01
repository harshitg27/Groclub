package com.gclub.groclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gclub.groclub.Prevalent.Prevalent;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity {
    Button loginback,signupnext;
    TextInputLayout regname, regphonenum, regemail , regpassword, referal;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        loginback = findViewById(R.id.loginback);
        signupnext = findViewById(R.id.signupnext);
        regname = findViewById(R.id.regName);
        regphonenum = findViewById(R.id.regPhonenum);
        regemail = findViewById(R.id.regEmail);
        regpassword = findViewById(R.id.regPassword);
        referal  = findViewById(R.id.referal);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,WelcomeScreen.class);
        startActivity(intent);
        finish();

    }

    public Boolean validatename(){
        String val = regname.getEditText().getText().toString();
        if(val.isEmpty()){
            regname.setError("Field cannot be empty");
            return false;
        } else {
            regname.setError(null);
            regname.setErrorEnabled(false);
            return true;
        }
    }

    public Boolean validatephone(){
        String val = regphonenum.getEditText().getText().toString();
        if(val.isEmpty()){
            regphonenum.setError("Field cannot be empty");
            return false;
        } else if(val.length() != 10){
            regphonenum.setError("Mobile No is InCorrect");
            return false;

        }  else {
            regphonenum.setError(null);
            regphonenum.setErrorEnabled(false);
            return true;
        }
    }

    public Boolean validateemail(){
        String val = regemail.getEditText().getText().toString();
        String emailPattern = "[A-Za-z0-9+_.-]+@(.+)$";
        if(val.isEmpty()){
            regemail.setError("Field cannot be empty");
            return false;
        } else if(!val.matches(emailPattern)){
            regemail.setError("Invalid Email Address");
            return false;
        } else {
            regemail.setError(null);
            regemail.setErrorEnabled(false);
            return true;
        }
    }

    public Boolean validatepassword(){
        String val = regpassword.getEditText().getText().toString();
        String passver = "^" +
//                "(?=.*[0-9])" +                 //contain 1 number
//                "(?=.*[a-z])" +                 //contain 1 lowercase letter
//                "(?=.*[A-Z])" +                 //contain 1 Uppercase letter
                "(?=.*[a-zA-Z])" +              //any letter
//                "(?=.*[@#$%^&+=])" +            //contain special character
                "(?=\\S+$)" +                   //no white space
                ".{6,15}";                     //min contain 6 character
//                "$";

        if(val.isEmpty()){
            regpassword.setError("Field Cannot be Empty");
            return false;
        }else if (!val.matches(passver)){
            regpassword.setError("Password is too weak");
            Toast.makeText(this,"Password Required Min 6 digit And Max 15 digit",Toast.LENGTH_SHORT).show();
            return false;
        }else  {
            regpassword.setError(null);
            regphonenum.setErrorEnabled(false);
            return true;
        }
    }

    public void signupNextbutton(View view){
        if(!validatename() | !validatephone() | !validateemail() | !validatepassword()){
            return;
        }
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("Customer Detail");

        final String fullname = regname.getEditText().getText().toString();
        final String phonenum = regphonenum.getEditText().getText().toString();
        final String email = regemail.getEditText().getText().toString();
        final String password = regpassword.getEditText().getText().toString();
        final String refral = referal.getEditText().getText().toString();

        Prevalent.custumername = fullname;

        Query checkuser = reference.orderByChild("phonenum").equalTo(phonenum);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    regphonenum.setError("Mobile Number Already Exist");
                    regphonenum.setErrorEnabled(true);
                    Toast.makeText(Signup.this,"Please Try Again Using Another Number",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(),VerifyPhoneNum.class);
                    intent.putExtra("Phonenum",phonenum);
                    intent.putExtra("Fullname",fullname);
                    intent.putExtra("Email",email);
                    intent.putExtra("Password",password);
                    intent.putExtra("Refral",refral);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




//        UserHelperclass helperclass = new UserHelperclass(fullname,phonenum,email,password,refral);
//        reference.child(phonenum).setValue(helperclass);


//        Intent intent = new Intent(this,Homepage.class);
//        startActivity(intent);


    }

    public void backinloginbutton(View view) {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
        finish();
    }
}