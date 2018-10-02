package com.techexchange.mobileapps.lab2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {

    static final String KEY_RESTART_QUIZ = "RetakeQuiz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Button againButton = findViewById(R.id.again_button);
        againButton.setOnClickListener(v -> onAgainButtonPressed());

        Button emailButton = findViewById(R.id.email_button);
        emailButton.setOnClickListener(v->onEmailButtonPressed());

        TextView scoreText = findViewById(R.id.score_text);
        int score = getIntent().getIntExtra(QuizFragment.KEY_SCORE, 0);
        scoreText.setText("Your Quiz Score is : " + score);
    }

    private void onAgainButtonPressed(){
        //creating a new intent instance\
        Intent data = new Intent();
        data.putExtra(KEY_RESTART_QUIZ, true);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    private void onEmailButtonPressed(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "My Geo App Quiz Scores");
        intent.putExtra(Intent.EXTRA_TEXT, ""); //enter a string for the email body
        intent.putExtra(Intent.EXTRA_CC, "");
        intent.putExtra(Intent.EXTRA_BCC, "");
        if(intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        } else {
            Toast.makeText(this, "The activity could not be resolved.", Toast.LENGTH_SHORT).show();
        }
    }
}
