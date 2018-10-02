package com.techexchange.mobileapps.lab2;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Preconditions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment
{
    //private variables imported from main activity
    private static final String TAG = QuizFragment.class.getSimpleName();
    private TextView questionText;
    private TextView correctText;
    private TextView titleText;
    private Button leftButton;
    private Button rightButton;
    private Button nextButton;
    private int currentScore;
    static final String KEY_SCORE = "Score";
    //private Random r;

    private List<Question> questionList;
    private int currentQuestionIndex;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);


        questionList = initQuestionList();
        questionText = rootView.findViewById(R.id.question_text);
        leftButton = rootView.findViewById(R.id.left_button);
        titleText = rootView.findViewById(R.id.title_text);
        leftButton.setOnClickListener(v -> onAnswerButtonPressed(v));

        rightButton = rootView.findViewById(R.id.right_button);
        rightButton.setOnClickListener(v -> onAnswerButtonPressed(v));

        nextButton = rootView.findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> onNextButtonPressed(v));
        nextButton.setEnabled(false);

        correctText = rootView.findViewById(R.id.correct_incorrect_text);
        currentQuestionIndex = 0;

        //save the state of the activity
        if(savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt("CurrentIndex");
        }
        updateView();
        return rootView;
    }

//----------------------------ADDED CODE HERE---------------

    private void onAnswerButtonPressed(View v) {
        Button selectedButton = (Button) v;
        Question ques = questionList.get(currentQuestionIndex);
        if (ques.getCorrectAnswer().contentEquals(selectedButton.getText())) {

            correctText.setText("Correct!");
            currentScore++; //increment the current score

        } else {
            correctText.setText("Wrong!");
        }
        nextButton.setEnabled(true);
    }

    //create a method when pressing the next button
    private void onNextButtonPressed(View v) {
        currentQuestionIndex++;
        if (currentQuestionIndex < questionList.size()) {
            updateView();
        } else {

            // Intent is a representation of a
            // request that an application makes to the underlying OS.
            Intent scoreIntent = new Intent(v.getContext(), ScoreActivity.class);
            //attaching an "extra" to the intent.
            //adding an extra to intent
            scoreIntent.putExtra(KEY_SCORE, currentScore);
            startActivityForResult(scoreIntent, 0);
        }

    }

    private void updateView() {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        questionText.setText(currentQuestion.getQuestion());
        if (Math.random() < 0.5) {
            leftButton.setText(currentQuestion.getCorrectAnswer());
            rightButton.setText(currentQuestion.getWrongAnswer());
        } else {
            rightButton.setText(currentQuestion.getCorrectAnswer());
            leftButton.setText(currentQuestion.getWrongAnswer());
        }
        nextButton.setEnabled(false);
        correctText.setText(R.string.initial_correct_incorrect);
    }

    //implementing the linked list in Java
    private List<Question> initQuestionList() {
        Resources res = getResources();
        //three arrays designed
        String[] questions = res.getStringArray(R.array.questions);
        String[] correctAnswers = res.getStringArray(R.array.correct_answers);
        String[] wrongAnswers = res.getStringArray(R.array.incorrect_answers);

        // Make sure that all arrays have the same length.
        Preconditions.checkState(questions.length == correctAnswers.length);
        Preconditions.checkState(questions.length == wrongAnswers.length);

        List<Question> qList = new ArrayList<>();

        for (int i = 0; i < questions.length; ++i) {
            qList.add(new Question(questions[i], correctAnswers[i], wrongAnswers[i]));
        }
        return qList;
    }

    //----------------------------

    // the Activity before it is destroyed.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CurrentIndex", currentQuestionIndex);
        // ADDITIONAL
        //currentQuestionIndex = outState.getInt("CurrentIndex");
        //set current score to the same format above
        // currentScore = outState.getInt("CurrentIndex");
    }


    @Override
    public void onActivityResult(
            int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || requestCode != 0 || data == null) {
            getActivity().finish();

        } else {
            //write the code required to get the boolean value set in
            // ScoreActivity, and restart the quiz if it is true.
            getActivity().finish();
            startActivity(getActivity().getIntent());
        }
    }


}