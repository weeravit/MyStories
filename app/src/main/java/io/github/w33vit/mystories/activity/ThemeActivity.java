package io.github.w33vit.mystories.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import io.github.w33vit.mystories.R;
import io.github.w33vit.mystories.utils.Theme;

public class ThemeActivity extends AppCompatActivity {

    Toolbar toolbar;
    RelativeLayout rlPink, rlGreen, rlBlue;
    RadioButton rbPink, rbGreen, rbBlue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTheme();
        setContentView(R.layout.activity_theme);

        initInstances();
        checkedTheme();
    }

    private void setupTheme() {
        Theme.getInstance().setupTheme(ThemeActivity.this);
    }

    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rlPink = (RelativeLayout) findViewById(R.id.rlPink);
        rlGreen = (RelativeLayout) findViewById(R.id.rlGreen);
        rlBlue = (RelativeLayout) findViewById(R.id.rlBlue);
        rbPink = (RadioButton) findViewById(R.id.rbPink);
        rbGreen = (RadioButton) findViewById(R.id.rbGreen);
        rbBlue = (RadioButton) findViewById(R.id.rbBlue);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rlPink.setOnClickListener(onClickListener);
        rlGreen.setOnClickListener(onClickListener);
        rlBlue.setOnClickListener(onClickListener);

        rbPink.setOnClickListener(onClickListener);
        rbGreen.setOnClickListener(onClickListener);
        rbBlue.setOnClickListener(onClickListener);
    }

    private void checkedTheme() {
        int theme = Theme.getInstance().getCurrentTheme();
        switch (theme) {
            case Theme.PINK:
                rbPink.setChecked(true);
                break;
            case Theme.GREEN:
                rbGreen.setChecked(true);
                break;
            default:
                rbBlue.setChecked(true);
                break;
        }
    }

    private void restartApplication() {
        Intent launchIntent = new Intent(getApplicationContext(), MainActivity.class);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(launchIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == rlPink || v == rbPink) {
                rbPink.setChecked(true);
                rbGreen.setChecked(false);
                rbBlue.setChecked(false);
                Theme.getInstance().settingTheme(Theme.PINK);
            } else if (v == rlGreen || v == rbGreen) {
                rbPink.setChecked(false);
                rbGreen.setChecked(true);
                rbBlue.setChecked(false);
                Theme.getInstance().settingTheme(Theme.GREEN);
            } else {
                rbPink.setChecked(false);
                rbGreen.setChecked(false);
                rbBlue.setChecked(true);
                Theme.getInstance().settingTheme(Theme.BLUE);
            }

            restartApplication();
        }
    };

}
