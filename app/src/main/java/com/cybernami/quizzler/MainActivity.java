package com.cybernami.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // TODO: Declare member variables here:
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mResetButton;
    private TextView mQuestionTextView;
    private ProgressBar mProgressBar;
    private TextView mScoreTextView;
    private int mIndex = 0;
    private int mScore = 0;

    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13, true)
    };

    // TODO: Declare constants here
    public final int QUESTION_LENGTH = mQuestionBank.length;
    public static final String SCORE_KEY = "SCORE_KEY";
    public static final String INDEX_KEY = "INDEX_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(SCORE_KEY);
            mIndex = savedInstanceState.getInt(INDEX_KEY);
        }

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mResetButton = findViewById(R.id.reset_button);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mScoreTextView = findViewById(R.id.score);

        mProgressBar.setMax(QUESTION_LENGTH - 1);

        updateQuestionText();

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Quizzler", "Answer " + true);
                answerQuestion(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Quizzler", "Answer " + false);
                answerQuestion(false);
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Quizzler", "Reset Progress");
                resetQuestion();
            }
        });
    }

    public void answerQuestion(boolean answer) {
        final boolean questionAnswer = mQuestionBank[mIndex].getAnswer();
        if (questionAnswer == answer) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT)
                    .show();
            mScore++;
        } else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT)
                    .show();
        }
        nextQuestion();
    }

    public void nextQuestion() {
        // mIndex = (mIndex + 1) % QUESTION_LENGTH;
        mIndex++;
        if (mIndex == QUESTION_LENGTH) {
            // TODO: Show Reset Dialog and reset score
            new AlertDialog.Builder(this)
                    .setTitle("Game Over")
                    .setCancelable(false)
                    .setMessage(getString(R.string.score_format, mScore, QUESTION_LENGTH))
                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setPositiveButton(R.string.retry_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            resetQuestion();
                        }
                    })
                    .show();
        } else {
            updateQuestionText();
        }
    }

    public void resetQuestion() {
        mIndex = 0;
        mScore = 0;
        updateQuestionText();
    }

    public void updateQuestionText() {
        final int question = mQuestionBank[mIndex].getQuestionId();
        mQuestionTextView.setText(question);
        mProgressBar.setProgress(mIndex);
        mScoreTextView.setText(getString(R.string.score_format, mScore, QUESTION_LENGTH));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SCORE_KEY, mScore);
        outState.putInt(INDEX_KEY, mIndex);
    }
}
