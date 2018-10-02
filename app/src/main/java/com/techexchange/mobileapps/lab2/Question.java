package com.techexchange.mobileapps.lab2;

class Question {
    private String question;
    private String correctAnswer;
    private String wrongAnswer;
//constructor that initializes all 3 fields
    public Question(String question, String correctAnswer, String wrongAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer = wrongAnswer;
    }

    public final String getQuestion() {
        return question;
    }

    public final String getCorrectAnswer() {
        return correctAnswer;
    }

    public final String getWrongAnswer() {
        return wrongAnswer;
    }
}
