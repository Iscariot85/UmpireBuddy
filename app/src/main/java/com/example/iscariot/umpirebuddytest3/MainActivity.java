package com.example.iscariot.umpirebuddytest3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int strike=0;
    int ball=0;
    int out;
    int result;
    String text;
    AlertDialog.Builder alert_dialog;
    SharedPreferences shared_pref;
    Button StrikeButton;
    Button BallButton;
    TextView StrikeCount;
    TextView BallCount;
    TextView OutCount;
    TextToSpeech toSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        StrikeButton = findViewById(R.id.StrikeButton);
        BallButton = findViewById(R.id.BallButton);
        StrikeCount = findViewById(R.id.StrikeCount);
        BallCount = findViewById(R.id.BallCount);
        OutCount = findViewById(R.id.OutCount);
        shared_pref = this.getPreferences(Context.MODE_PRIVATE);
        out = shared_pref.getInt(getString(R.id.OutCount), 0);
        OutCount.setText(String.format("%d",out));

        alert_dialog = new AlertDialog.Builder(MainActivity.this).setPositiveButton("OK", new AlertDialog.OnClickListener(){
            public void onClick(DialogInterface D, int I) {
                strike = 0;
                ball = 0;
                BallCount.setText(String.format("%d",ball));
                StrikeCount.setText(String.format("%d",strike));
            }
        });

//        toSpeech= new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if (status==TextToSpeech.SUCCESS)
//                {
//                    result = toSpeech.setLanguage(Locale.ENGLISH);
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        StrikeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                strike += 1;
                if(strike == 3) {
                    out+=1;
                    alert_dialog.setMessage("OUT!");
                    alert_dialog.show();
                    shared_pref.edit().putInt(getString(R.id.OutCount),out).commit();
                    OutCount.setText(String.format("%d",out));
                }
                else
                    StrikeCount.setText(String.format("%d",strike));
            }
        });
        BallButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ball += 1;
                if(ball == 4) {
                    alert_dialog.setMessage("WALK!");
                    alert_dialog.show();
                }
                else
                    BallCount.setText(String.format("%d",ball));
            }
        });

    }

    //    public void TTS(View view)
//    {
//        switch (view.getId())
//        {
//            case R.id.speechon:
//                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
//                {
//                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    if(ball == 4) {
//                        text = "Walk";
//                        toSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//                        //alert_dialog.show();
//                    }
//                    else
//                    {
//                        if(strike ==3){
//                    text = "Out";
//                    toSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//                    //alert_dialog.show();
//                    }}
//                }
//                break;
//            case R.id.speechoff:
//                if (toSpeech!=null)
//                {
//                    toSpeech.stop();
//                }
//                break;
//        }
//    }

    public void reset() {
        strike = 0;
        ball = 0;
        StrikeCount.setText(String.format("%d",strike));
        BallCount.setText(String.format("%d",ball));
    }

    public void about() {
        Intent intent = new Intent(this,About.class);
        startActivity(intent);
        //return;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
                case R.id.reset:
                    reset();
                    return true;
                case R.id.about:
                    about();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
    }
}
