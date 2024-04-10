package com.siraapps.raul.tipandsplitrevb;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jumpToMain();
    }

    private void jumpToMain () {
        Intent jumpIntent = new Intent(this, MainActivity.class);
        startActivity(jumpIntent);
        finish();
    }
}
