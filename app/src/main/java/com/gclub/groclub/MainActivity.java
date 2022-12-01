package com.gclub.groclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gclub.groclub.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView comnametv , slogantv;
    private static int SPLASH_TIME_OUT = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        slogantv = findViewById(R.id.slogantv);
        comnametv = findViewById(R.id.comnametv);

        YoYo.with(Techniques.ZoomIn)
                .duration(3500)
                .playOn(findViewById(R.id.sign));

        YoYo.with(Techniques.Bounce)
                .duration(3500)
                .playOn(findViewById(R.id.comnametv));

        YoYo.with(Techniques.FadeInUp)
                .duration(3500)
                .playOn(findViewById(R.id.slogantv));


//        imageView.setY(-1000);
////        imageView.animate().scaleX(0.5f).scaleY(0.5f).alpha(0).setDuration(3000);
//        slogantv.setX(800);
//        comnametv.setY(400);
//        slogantv.animate().translationXBy(-800).setDuration(2000);
//        comnametv.animate().translationYBy(-400).setDuration(3000);
//        imageView.animate().translationYBy(1000).setDuration(3000);


        Runnable runn;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,WelcomeScreen.class);
                startActivity(intent);
                finish();
//                Pair[] pairs = new Pair[2];
//                pairs[0] = new Pair<View,String>(imageView,"logo_image");
//                pairs[0] = new Pair<View,String>(comnametv,"logo_text");
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
//                    startActivity(intent,options.toBundle());
//                }
            }
        },SPLASH_TIME_OUT);
    }

}