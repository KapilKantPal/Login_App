package com.programmingmanav.loginapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //Background Images
    ImageView iB,iiB,iiiB,ivB,mainLogo;
    TextView turnReality;
    ProgressBar pb;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Removing status bar.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialisation
        iB = (ImageView) findViewById(R.id.iB);
        iiB = (ImageView) findViewById(R.id.iiB);
        iiiB = (ImageView) findViewById(R.id.iiiB);
        ivB = (ImageView) findViewById(R.id.ivB);
        mainLogo = (ImageView) findViewById(R.id.MainLogo);
        turnReality = (TextView)findViewById(R.id.TurnReality);
        pb = (ProgressBar)findViewById(R.id.progressBar_horizontal);


        //Start Animation.
        firstActivityAnimation();

        //progress bar.
        progressBar();

    }

    protected void progressBar(){

        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter++;
                pb.setProgress(counter*2);

                if(counter == 100){
                    Intent loginIntent = new Intent(getApplicationContext() , Log_In.class);
                    startActivity(loginIntent);
                    finish();
                    t.cancel();
                }
            }
        };
        t.schedule(tt , 100 , 50);
    }

    protected void firstActivityAnimation(){

        //Setting Translation.
        iB.setTranslationY(1000);
        iiB.setTranslationY(900);
        iiiB.setTranslationX(-900);
        ivB.setTranslationX(-1000);
        turnReality.setTranslationX(-500);
        mainLogo.setAlpha(0F);

        //Animation of the BackgroundImages
        mainLogo.animate().alpha(1F).setDuration(1000);
        iB.animate().translationY(0F).setDuration(500);
        iiB.animate().translationY(0F).setDuration(500).setStartDelay(400L);
        iiiB.animate().translationX(0F).setDuration(500).setStartDelay(400L);
        ivB.animate().translationX(0F).setDuration(500);
        turnReality.animate().translationX(0F).setDuration(1000);

    }

}