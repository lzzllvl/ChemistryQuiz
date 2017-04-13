package com.example.greg.chemistryquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button start_button;
    private ImageButton settings_button;
    private boolean phoneDevice = true;
    private boolean preferencesChanged = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_button = (Button) findViewById(R.id.buttonStart);
        start_button.setOnClickListener(this);
        settings_button = (ImageButton) findViewById(R.id.settings_button);
        settings_button.setOnClickListener(this);

        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(preferencesChangeListener);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener preferencesChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    preferencesChanged = true;
                }
            };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonStart:
                Intent Game;
                Game = new Intent(this, GameActivity.class);
                startActivity(Game);
                break;
            case R.id.settings_button:
                Intent settings;
                settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                break;

        }
    }
}