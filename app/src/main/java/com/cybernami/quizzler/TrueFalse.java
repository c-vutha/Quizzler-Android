package com.cybernami.quizzler;

public class TrueFalse {
    private int mQuestionId;
    private boolean mAnswer;

    public TrueFalse(int questionId, boolean answer) {
        mQuestionId = questionId;
        mAnswer = answer;
    }

    public int getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(int questionId) {
        mQuestionId = questionId;
    }

    public boolean getAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }
}
