package com.example.truecitizenquiz;

public class Question {
    private int answerRedId;
    private boolean answerTrue;

    public Question(int answerRedId, boolean answerTrue) {
        this.answerRedId = answerRedId;
        this.answerTrue = answerTrue;
    }

    public int getAnswerRedId() {
        return answerRedId;
    }

    public void setAnswerRedId(int answerRedId) {
        this.answerRedId = answerRedId;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }
}
