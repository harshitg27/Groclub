package com.gclub.groclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gclub.groclub.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
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

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static com.gclub.groclub.Prevalent.Prevalent.userpasswordkey;
import static com.gclub.groclub.Prevalent.Prevalent.userphonekey;

public class WelcomeScreen extends AppCompatActivity {

    private ProgressDialog loadingbar;
    Button joinnowbutton , loginnowbutton;
    String verification;
    String indiannum;
    String cusname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        joinnowbutton = findViewById(R.id.joinnowbutton);
        loginnowbutton = findViewById(R.id.loginnowbutton);
        loadingbar = new ProgressDialog(this);

        Paper.init(this);

        String userphonekey = Paper.book().read(Prevalent.userphonekey);
        String userpasswordkey = Paper.book().read(Prevalent.userpasswordkey);

        if(userphonekey != "" && userpasswordkey != ""){
            if(!TextUtils.isEmpty(userphonekey) && !TextUtils.isEmpty(userpasswordkey)){
                AllowAccess(userphonekey,userpasswordkey);
                loadingbar.setTitle("Already login");
                loadingbar.setMessage("Please wait....");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();
            }
        }
        if(userphonekey != ""){
            if(!TextUtils.isEmpty(userphonekey)){
                AllowAccessbyotp(userphonekey);
                loadingbar.setTitle("Already login");
                loadingbar.setMessage("Please wait....");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();
            }
        }
    }

    private void AllowAccessbyotp(final String userenteredphonenum) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer Detail");
        Query checkuser = reference.orderByChild("phonenum").equalTo(userenteredphonenum);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    cusname = dataSnapshot.child(userenteredphonenum).child("fullname").getValue(String.class);

                    Prevalent.userphonekey =userenteredphonenum;

                    Intent intent = new Intent(getApplicationContext(),Homepage.class);
                    Prevalent.custumername = cusname;
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

//                    indiannum = "+91" + userenteredphonenum;
//                    sendVerificationCodeToUser(indiannum);
                    loadingbar.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void AllowAccess(final String userphone, final String userpassword) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer Detail");
        Query checkuser = reference.orderByChild("phonenum").equalTo(userphone);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String Passwordfromdb =  dataSnapshot.child(userphone).child("password").getValue(String.class);
                    cusname = dataSnapshot.child(userphone).child("fullname").getValue(String.class);
                    Prevalent.userphonekey = userphone;

                    if(Passwordfromdb.equals(userpassword)){
                        Intent intent = new Intent(getApplicationContext(),Homepage.class);
                        Prevalent.custumername = cusname;
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        loadingbar.dismiss();
                    }
                    else {
                        loadingbar.dismiss();
                        Toast.makeText(WelcomeScreen.this,"Password is Incorrect",Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(WelcomeScreen.this,"such User does not Exist",Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void loginnowbutton(View view) {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
        finish();

    }

    public void joinnoebutton(View view) {
        Intent intent = new Intent(this,Signup.class);
        startActivity(intent);
        finish();
    }
}