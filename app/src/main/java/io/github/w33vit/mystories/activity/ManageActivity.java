package io.github.w33vit.mystories.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;

import io.github.w33vit.mystories.R;
import io.github.w33vit.mystories.dao.Stories;
import io.github.w33vit.mystories.dao.StoriesDao;
import io.github.w33vit.mystories.dao.DaoProvider;
import io.github.w33vit.mystories.utils.Theme;

public class ManageActivity extends AppCompatActivity {

    Button btnCancel;
    Button btnSave;
    EditText edtTitle;
    EditText edtContent;
    FloatingActionButton fab;

    final int REQ_CODE_SPEECH_INPUT = 100;
    boolean isAddMode;
    Stories story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTheme();
        setContentView(R.layout.activity_manage);

        initInstances();
        checkMode();
    }

    private void setupTheme() {
        Theme.getInstance().setupTheme(ManageActivity.this);
    }

    private void checkMode() {
        if (!isAddMode) {
            story = Parcels.unwrap(getIntent().getParcelableExtra("story"));
            edtTitle.setText(story.getTitle());
            edtContent.setText(story.getContent());
        }
    }

    private void initInstances() {
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSave = (Button) findViewById(R.id.btnSave);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtContent = (EditText) findViewById(R.id.edtContent);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        Intent intent = getIntent();
        isAddMode = intent.getBooleanExtra("add", false);

        btnCancel.setOnClickListener(onClickListener);
        btnSave.setOnClickListener(onClickListener);
        fab.setOnClickListener(onClickListener);
    }

    private void speechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "th-TH");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQ_CODE_SPEECH_INPUT == requestCode && data != null) {
            ArrayList<String> result = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String voiceMessage = result.get(0);
            setMessage(voiceMessage);
        }
    }

    private void setMessage(String voiceMessage) {
        if (edtTitle.isFocused()) {
            int originalMessageLength = edtTitle.length();
            int currentCursor = edtTitle.getSelectionStart();
            String leftMessage = edtTitle.getText().toString().substring(0, currentCursor);
            String rightMessage = edtTitle.getText().toString().substring(currentCursor, originalMessageLength); // start == end is "".
            String realMessage = leftMessage + voiceMessage + rightMessage;
            edtTitle.setText(realMessage);
        } else {
            int originalMessageLength = edtContent.length();
            int currentCursor = edtContent.getSelectionStart();
            String leftMessage = edtContent.getText().toString().substring(0, currentCursor);
            String rightMessage = edtContent.getText().toString().substring(currentCursor, originalMessageLength); // start == end is "".
            String realMessage = leftMessage + voiceMessage + rightMessage;
            edtContent.setText(realMessage);
        }
    }

    private void addStory() {
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        Stories story = new Stories(title, content);

        StoriesDao storiesDao = DaoProvider.getInstance().getDaoSession().getStoriesDao();
        storiesDao.insert(story);
        Toast.makeText(getApplicationContext(), getString(R.string.add_success), Toast.LENGTH_LONG).show();
    }

    private void updateStory() {
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        story.setTitle(title);
        story.setContent(content);
        story.setStories_date(new Date());

        StoriesDao storiesDao = DaoProvider.getInstance().getDaoSession().getStoriesDao();
        storiesDao.update(story);
        Toast.makeText(getApplicationContext(), getString(R.string.update_success), Toast.LENGTH_LONG).show();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnCancel) {
                finish();
            } else if (v == btnSave) {
                if (isAddMode) {
                    addStory();
                    finish();
                } else {
                    updateStory();
                    finish();
                }
            } else {
                speechToText();
            }
        }
    };

}
