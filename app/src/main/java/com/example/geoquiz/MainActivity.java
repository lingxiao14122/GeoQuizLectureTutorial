package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // member variable
    private Button mBtnTrue;
    private Button mBtnFalse;
    private Button mBtnNext;
    private Button mBtnPrevious;

    private TextView mTvQuestion;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_america, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind variable to ui
        mBtnTrue = findViewById(R.id.btn_true);
        mBtnFalse = findViewById(R.id.btn_false);
        mBtnNext = findViewById(R.id.btnNext);
        mBtnPrevious = findViewById(R.id.btnPrevious);
        mTvQuestion = findViewById(R.id.question_text_view);

        // init state and checking for ui
        updateQuestion();
        checkPreviousNextButton();

        // init button listener
        mBtnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check user answer with current question
                checkAnswer(true);
            }
        });

        mBtnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check user answer with current question
                checkAnswer(false);
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update question index
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;

                updateQuestion();
                checkPreviousNextButton();


            }
        });

        mBtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update question index to previous
                mCurrentIndex = (mCurrentIndex - 1);

                updateQuestion();
                checkPreviousNextButton();
            }
        });

    }

    /**
     * Update the question on UI
     * note: need to update mCurrentIndex before using this
     */
    private void updateQuestion() {
        // get question according to index
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        // update ui
        mTvQuestion.setText(question);
    }

    /**
     * Display toast to tell user their choice is correct or incorrect
     *
     * @param userPressedChoice is value of, if user choose true or false
     */
    private void checkAnswer(boolean userPressedChoice) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        // pass toast message id based on user choice
        if (userPressedChoice == answerIsTrue) {
            messageResId = R.string.correctToast;
        } else {
            messageResId = R.string.incorrectToast;
        }

        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }


    /**
     * Check question index to determine enable or disable next button and previous button
     */
    private void checkPreviousNextButton() {
        // if question index is 0 then disable button, if not updateQuestion() will crash
        if (mCurrentIndex <= 0) {
            mBtnPrevious.setEnabled(false);
        } else {
            mBtnPrevious.setEnabled(true);
        }

        if (mCurrentIndex >= 4) {
            mBtnNext.setEnabled(false);
        } else {
            mBtnNext.setEnabled(true);
        }
    }
}